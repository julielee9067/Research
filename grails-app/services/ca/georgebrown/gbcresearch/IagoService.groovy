package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.iago.IagoImportStats
import ca.georgebrown.gbcresearch.iago.Investigator
import ca.georgebrown.gbcresearch.iago.Partner
import ca.georgebrown.gbcresearch.iago.ResearchTheme
import ca.georgebrown.gbcresearch.security.Appuser
import grails.transaction.Transactional
import groovy.sql.Sql


@Transactional
class IagoService {

    def bannerService
    def dataSource_iago
    def submissionService

    static final IAGO_LIST = [PRICIPLE_INVESTIGATORS:'PI', CO_INVESTIGATORS:'CI',PARTNERS:'P',RESEARCH_THEMES:'RT']
    static final IAGO_LIST_QUERIES = [
             (IAGO_LIST.PRICIPLE_INVESTIGATORS):
                     """
                          select s.id iago_stakeholder_id, s.first_name fname, s.last_name lname, s.email email from public_project_principle_investgators pppi
                             join stakeholder s on s.id = pppi.stakeholder_id where public_project_id = :id
                     """,
             (IAGO_LIST.CO_INVESTIGATORS):
                     """
                          select s.id iago_stakeholder_id, s.first_name fname, s.last_name lname, s.email email from public_project_co_investigators pppi
                             join stakeholder s on s.id = pppi.stakeholder_id where public_project_id = :id
                     """,
             (IAGO_LIST.PARTNERS):
                     """
                          select distinct o.name organization from public_project_partners ppp 
                        join stakeholder s on s.id = ppp.stakeholder_id
                        join organization o on o.id = s.organization_id
                        where ppp.public_project_id = :id
                     """,
             (IAGO_LIST.RESEARCH_THEMES):
                     """
                          select aoe.name theme from public_project_area_of_excellence ppai
                            join area_of_excellence aoe on aoe.id = ppai.area_of_excellence_id
                                where ppai.public_project_research_themes_id = :id
                     """
    ]

    // This function is used to import iago projects.
    def importIagoProjects() {
        Date now = new Date()
        Appuser adminUser = Appuser.findByUsername('admin')
        IagoImportStats stats = getImportStatRec()
        Sql sql = Sql.newInstance(dataSource_iago)
        def query = "select * from public_project where public_view = true and last_updated > to_timestamp(:lastImportDate, 'YYYY-MM-DD HH24:MI:SS.FF')"

        try {
            sql.eachRow(query, [lastImportDate:stats.lastImportDate.format('yyyy-MM-dd HH:mm:ss.SSSSSS')]) { rec->
               Submission submission = Submission.findBySourceAndIagoId(Submission.SOURCE.IAGO,rec.id)

               if (!submission) {
                   stats.insertRecCnt++
                   submission = new Submission(source:Submission.SOURCE.IAGO, iagoId: rec.id)
               }
               else {
                   stats.updateRecCnt++
               }

               submission.title = rec.title
               submission.description = rec.summary
               submission.publisher = adminUser
               submission.published = true
               submission.submitter = adminUser
               submission.type = SubmissionType.findByName('Project')
               submission.submitStatus = Submission.SUBMIT_STATUS.PUBLISHED
               submission.updatedBy = adminUser
               submission.iagoImportDate = stats.lastImportDate
               submission.publicationDate = getImportStatRec().lastImportDate
               submission = submissionService.save(submission)
               addPartnersToProject(submission, sql)
               addResearchThemesToProject(submission,sql)
               addInvestigators(submission,sql)
               submission.principleInvestigators.each() {Investigator i->
                   if (submission.submitter == adminUser) {
                       if (i.appuser) submission.submitter = i.appuser
                   }
               }
               submission = submissionService.save(submission)
            }
        }
        catch (Exception e) {
            stats.status = IagoImportStats.STATUS.FAIL
            log.error(e.message)
        }
        finally {
            sql.close()
        }

        stats.lastImportDate = new Date()
        stats.runtime = stats.lastImportDate.time - now.time
        stats.save(flush:true)
        return stats
    }

    // This function adds investigators to the submission.
    def addInvestigators(Submission fs, Sql sql = false) {
       fs.principleInvestigators.clear()
       fs.principleInvestigators = getInvestigatorsList(IAGO_LIST.PRICIPLE_INVESTIGATORS, fs.iagoId, sql)
       fs.coInvestigators.clear()
       fs.coInvestigators = getInvestigatorsList(IAGO_LIST.CO_INVESTIGATORS, fs.iagoId, sql)
    }

    // This function gets the investigators list for specific submission.
    def getInvestigatorsList(def which, def iagoId, sql) {
        def investigators = getIagoProjectList(which,iagoId, sql)
        def investigatorList = []

        investigators.each() { i->
            Investigator investigator = Investigator.findByIagoStakeholderId(i.iago_stakeholder_id.toLong())
            if (!investigator) {
                investigator = new Investigator(iagoStakeholderId: i.iago_stakeholder_id)
            }
            investigator.email = i.email
            investigator.firstName = i.fname
            investigator.lastName = i.lname
            def bannerId =  bannerService.getBannerIdUsingEmail(i.email)
            investigator.appuser = Appuser.findByUsername(bannerId)
            investigator.save(flush:true)
            investigatorList.add(investigator)
        }
       return investigatorList
    }

    // This function adds partners to the specific submission
    def addPartnersToProject(Submission fs, Sql sql = false) {
        fs.refresh()
        def partners = getIagoProjectList(IAGO_LIST.PARTNERS,fs.iagoId, sql)
        fs.partners.clear()

        partners.each() { p->
            Partner partner = Partner.findByOrganization(p.organization)
            if (!partner) {
                partner = new Partner(organization: p.organization)
                partner.save(flush:true)
            }
            fs.addToPartners(partner)
        }

        fs.save(flush:true)
    }

    // This function adds research themes to the submission.
    def addResearchThemesToProject(Submission fs, Sql sql = false) {
        fs.refresh()
        def themes = getIagoProjectList(IAGO_LIST.RESEARCH_THEMES,fs.iagoId,sql)
        fs.researchThemes.clear()

        themes.each() { t->
            ResearchTheme theme = ResearchTheme.findByTheme(t.theme)
            if (!theme) {
                theme = new ResearchTheme(theme: t.theme)
                theme.save(flush:true)
            }
            fs.addToResearchThemes(theme)
        }
        fs.save(flush:true)
    }

    // This function gets the import status records.
    def getImportStatRec() {
        IagoImportStats stats = IagoImportStats.findByStatus(IagoImportStats.STATUS.SUCCESS,[sort:'lastImportDate',order: 'desc',limit:1])

        if (!stats || (stats.insertRecCnt+stats.updateRecCnt)) {
            Date lastImportDate = stats?.lastImportDate
            if (!lastImportDate) lastImportDate = new Date()
            stats = new IagoImportStats(lastImportDate:lastImportDate)
            stats.save(flush: true)
        }

        return stats
    }

    // This function returns the list of iago projects.
    def getIagoProjectList(def which, def publicProjectId, Sql sql = null) {
        return getIagoList(IAGO_LIST_QUERIES[which],publicProjectId,sql)
    }

    def getIagoList(def query, def publicPojectId, Sql sql = null) {
        def list =[]
        boolean localSql = false

        if (!sql) {
            sql =  Sql.newInstance(dataSource_iago)
            localSql = true
        }

        try {
            list = sql.rows(query, [id:publicPojectId.toLong()])
        }
        catch (Exception e) {
            list = []
            log.error(e.message)
        }
        finally {
            if (localSql) sql.close()
        }
        return list
    }
}
