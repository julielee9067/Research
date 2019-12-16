package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.AppuserRole
import ca.georgebrown.gbcresearch.security.Role
import grails.plugin.springsecurity.annotation.Secured

import java.text.SimpleDateFormat


@Secured('ROLE_EMPLOYEE')

class SubmissionsController {

    def springSecurityService
    def projectService
    def listManagerService
    def databaseHelperService
    def submissionService
    def userMatchService

    def getFilter() {
        def filter = session['project.searchFilter']

        if (filter == null) {
            filter = [
                    keyword               : '',
                    title                 : '',
                    organization          : [],
                    stakeholder           : [],
                    grant                 : [],
                    status                : [],
                    aoe                   : [],
                    department            : [],
                    school                : []]
        }

        if (params.containsKey("search")) {
            filter['keyword'] = params['filter.keyword']
            filter['title'] = params['filter.title']
            filter['stakeholder'] = listManagerService.filterParam(params, 'filter.stakeholder')
            filter['organization'] = listManagerService.filterParam(params, 'filter.organization')
            filter['grant'] = listManagerService.filterParam(params, 'filter.grant')
            filter['status'] = listManagerService.filterParam(params, 'filter.status')
            filter['aoe'] = listManagerService.filterParam(params, 'filter.aoe')
            filter['department'] = listManagerService.filterParam(params, 'filter.department')
            filter['school'] = listManagerService.filterParam(params, 'filter.school')
            session['project.searchFilter'] = filter
        }
        return filter
    }

    def submissionList() {
        Appuser appuser = springSecurityService.currentUser
        params.max = Math.min(params.max?.toInteger() ?: 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0
        def keyword = null
        def keywordList = []
        def title = null
        def stakeholderList = null
        def grantList = null
        def projectBudgetList = []
        def projectStakeholderList = []
        def grantBudgetList = []
        def organizationList = null
        def departmentList = []
        def statusList = null
        def aoeList = []
        def aoeProjectList = []
        def projects = [:]
        def deleteSuccess = params.deleteSuccess
        def schoolList = []
        def schoolPsList = []

        if (params.containsKey('clear')) {
            session['project.searchFilter'] = null
        }

        if (params.containsKey('createNew')) {
            redirect action: 'edit', params: [id: params.id, createNew: true, source: 'profile']
            return
        }

        keyword = filter['keyword']
        title = filter['title']
        organizationList = listManagerService.filterList('project.searchFilter','organization', Organization)
        stakeholderList = listManagerService.filterList('project.searchFilter','stakeholder', Stakeholder)
        statusList = listManagerService.filterList('project.searchFilter','status', ProjectStatus)
        grantList = listManagerService.filterList('project.searchFilter', 'grant', GrantDetail)
        departmentList = listManagerService.filterList('project.searchFilter', 'department', Department)
        aoeList = listManagerService.filterList('project.searchFilter', 'aoe', AreaOfExcellence)
        schoolList = listManagerService.filterList('project.searchFilter', 'school', School)

        if (keyword) {
            keywordList = projectService.getProjectsWithText(keyword)
        }

        def c = ProjectBudget.createCriteria()

        if (organizationList) {
            projectBudgetList = c.list () {
                if (!organizationList.size()) organizationList.add(null)
                inList('organization',organizationList)
            }
        }

        c = ProjectBudget.createCriteria()

        if (grantList) {
            grantBudgetList = c.list () {
                if (!grantList.size()) grantList.add(null)
                inList('grant',grantList)
            }
        }

        c = ProjectStakeholder.createCriteria()

        if (stakeholderList)  {
            projectStakeholderList = c.list() {
                if (!stakeholderList.size()) stakeholderList.add(null)
                inList('stakeholder', stakeholderList)
            }
        }

        if (aoeList) {
            Project.list().each { project ->
                if (project.areasOfExcellence.size()) {
                    project.areasOfExcellence?.each { aoe ->
                        if (aoe in aoeList) {
                            aoeProjectList.add(project)
                        }
                    }
                }
            }
        }

        c = Stakeholder.createCriteria()

        if (schoolList) {
            def schoolSList = c.list() {
                if (!schoolList.size()) schoolList.add(null)
                inList ('school', schoolList)
            }
            if (schoolSList) {
                schoolSList.each { stakeholder ->
                    ProjectStakeholder.findAllByStakeholder(stakeholder).each { ps ->
                        schoolPsList.add(ps)
                    }
                }
            }
        }

        c = Project.createCriteria()

        def projectList = c.list (max: params.max, offset: params.offset) {
            order("title", "asc")

            if (keyword) {
                inList('id', keywordList?.id?:0L)
            }

            if (title) ilike("title", '%' + title + '%')
            if (statusList) 'in' ('status', statusList)
            if (departmentList) 'in' ('department', departmentList)

            if (organizationList) {
                def pIdList = projectBudgetList.project?.id
                if (!pIdList.size()) pIdList.add(0L)
                inList('id',pIdList)
            }

            if (stakeholderList) {
                def sIdList = projectStakeholderList.project?.id
                if (!sIdList.size()) sIdList.add(0L)
                inList('id', sIdList)
            }

            if (grantList) {
                def pIdList = grantBudgetList.project?.id
                if (!pIdList.size()) pIdList.add(0L)
                inList('id',pIdList)
            }
            if (aoeList) {
                def pIdList = aoeProjectList.id
                if (!pIdList.size()) pIdList.add(0L)
                inList ('id', pIdList)
            }

            if (schoolList) {
                def pIdList = schoolPsList.project?.id
                if (!pIdList.size()) pIdList.add(0L)
                inList ('id', pIdList)
            }
        }
        [appuser: appuser, projects: projects, total: projectList.totalCount, filter: filter, deleteSuccess:deleteSuccess]
    }

    def edit () {
        Appuser appuser = springSecurityService.currentUser
        def submission
        def disabled = "false"
        def mode = "edit"

        if (params.submission_id) {
            session.setAttribute('submissionId', params.submission_id.toLong())
            submission = Submission.get(params.submission_id)
        }
        else if (params.id) {
            session.setAttribute('submissionId', params.id.toLong())
            submission = Submission.get(params.id)
        }
        else if (session.getAttribute('submissionId') && !params.createNew && !params.containsKey('saveNew') ) {
            submission = Submission.get(session.getAttribute('submissionId') as Serializable)
        }

        def createFail = false
        def created = false
        def updateSuccess = false
        def recordExist = false
        def nameMatch = false
        def anchor = params.anchor
        def projectRefList = []
        def createNew = params.createNew
        def result = [:]
        def dateResult = ['success': true, 'errorMsg': ""]
        def uploadFileListNewSubmission =  UploadedFile.findAllBySourceAndUploadedByAndObjId('Submission', appuser, 0)

        if (params?.createNew == 'true') {
            Submission newSubmission = new Submission(submitStatus: Submission.SUBMIT_STATUS.INITIATED, submitter: appuser, title: '', description: '', updatedBy: appuser, owner: appuser)
            newSubmission.save(flush: true, failOnError: true)
            if (params.msg == 'File successfully uploaded' && params.id == '0') {
                def fileList = UploadedFile.findAllByObjIdAndUploadedBy(0, appuser)

                fileList.each() { UploadedFile uploadFile ->
                    uploadFile.objId = newSubmission.id
                    uploadFile.save(flush: true, failOnError: true)
                }
            }
            redirect(controller: 'submissions', action: 'edit', id: newSubmission.id, params: [source: 'homePage'])
        }
        else if (params.msg == 'File successfully uploaded' && params?.createNew == 'false') {
            redirect(controller: 'submissions', action: 'edit', id: params.id.toInteger(), params: [source: 'homePage'])
        }

        if (params?._action_edit in ['Save', 'SaveModePublish']) {
            if (!params?.id) {
                def exists = submissionService.alreadyExists(params)
                switch (exists.found) {
                    case submissionService.FOUND.NAMEMATCH:
                        nameMatch = true
                    case submissionService.FOUND.NO:
                        submission = new Submission()
                        created = true
                        break
                    case submissionService.FOUND.YES:
                        createFail = true
                        recordExist = true
                        break
                }
            }
        }

        if ((submission && !createFail) && (params?._action_edit in ['Save', 'updatePublish', 'updateSubmit', 'reject', 'SaveModePublish'])) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
            submission.lastUpdated = new Date()

            if (!submission?.submitter) submission.submitter = appuser
            if (!submission?.owner) submission.owner = appuser

            submission.updatedBy = appuser

            if (params?.submission?.description) submission.description = params.submission.description
            if (params?.submission?.title) submission.title = params.submission.title
            if (params?.submission?.type != 'null') submission.type = SubmissionType.get(params.submission.type)
            if (params?.submission?.expectedStartDate) submission.expectedStartDate = Date.parse('dd/MM/yyyy', params.submission.expectedStartDate)
            if (params?.submission?.expectedEndDate) submission.expectedEndDate = Date.parse('dd/MM/yyyy', params.submission.expectedEndDate)
            if (params?._action_edit == 'Save' && submission.submitStatus in [Submission.SUBMIT_STATUS.IN_EDIT, Submission.SUBMIT_STATUS.INITIATED]) submission.submitStatus = Submission.SUBMIT_STATUS.IN_EDIT

            submission.activeStatus = Submission.STATUS.ENABLED
            listManagerService.select2ListProcess(submission, Tag, "tags", params.tags, appuser)
            submission = submissionService.save(submission)
            updateSuccess = true
            params."submission.id" = submission.id

            if (uploadFileListNewSubmission.size()) {
                uploadFileListNewSubmission.each() { def uploadedFile ->
                    uploadedFile.objId = submission?.id ?: 0
                    uploadedFile.saved = true
                    uploadedFile.save(flush: true, failOnError: true)
                }
            }
        }

        if (params?._action_edit == 'Cancel') {
            def canceledFiles = UploadedFile.findAllByUploadedByAndSavedAndSource(appuser, false, 'Submission')

            canceledFiles.each() { UploadedFile canceledFile ->
                canceledFile.delete(flush: true)
            }

            if (params?.source == 'profile') redirect controller: 'profile', action: 'index', params: [profile_id: params.profile_id]
            else if (params?.source == 'publisher') redirect controller: 'publisher', action: 'list'
            else redirect controller: 'homePage', action: 'index'
        }

        if (params.containsKey('deleteFile')) {
            anchor = "fileAnchor"
            def file = UploadedFile.get(params.deleteFile.keySet().toArray()[0].toLong())

            if (file) {
                file.delete(flush: true)
                result.put('deleteFileSuccess', true)
                redirect(controller: 'submissions', action: 'edit', id: params?.submissionId)
            }
            else result.put('deleteFileErrorMsg', 'File not Found')
        }

        if (submission && params.containsKey('delete')) {
            def returnController = 'homePage'
            def returnAction = 'index'

            if (params?.source == 'profile') {
                returnController = 'profile'
                returnAction = 'index'
            }

            userMatchService.deleteAllSubmissionView(submission)
            result = databaseHelperService.delete(Submission, submission)
            def deleteFile = UploadedFile.findAllByObjIdAndSource(submission.id, 'Submission')

            deleteFile.each() { UploadedFile uploadFile ->
                uploadFile.delete(flush: true)
            }

            if (result.success) {
                redirect controller: returnController, action: returnAction,  params: [source: returnController]
                return
            }
            else submission.refresh()
        }

        if (params.containsKey('disable')) {
            def returnController = 'profile'
            def returnAction = 'index'

            if (AppuserRole.findByAppuserAndRole(appuser, Role.findByAuthority('ROLE_PUBLISHER')) && submission?.owner != appuser) {
                returnController = 'publisher'
                returnAction = 'list'
            }

            userMatchService.deleteAllSubmissionView(submission)
            submission.activeStatus = Submission.STATUS.DISABLED
            submission = submissionService.save(submission)
        }

        if (params.containsKey('enable')) {
            submission.activeStatus =    Submission.STATUS.ENABLED
            submission = submissionService.save(submission)
        }

        if (params?._action_edit == 'updatePublish') {
            redirect(controller: "publisher", action: "publish", params: [id: params.id])
        }

        if (params?._action_edit == 'updateSubmit') {
            submission.submitStatus = Submission.SUBMIT_STATUS.SUBMITTED
            submission = submissionService.save(submission)
        }

        if (params?._action_edit == 'recall') {
            submission.submitStatus = Submission.SUBMIT_STATUS.IN_EDIT
            submission = submissionService.save(submission)
        }

        if (params?._action_edit == 'reject') {
            redirect(controller: "publisher", action: "reject", params: [id: params.id, reason: params.reason, source: params?.source])
        }

        if (params?._action_edit == 'SaveModePublish') {
            redirect(controller: "submissions", action: "edit", params: [submission_id: params.id, appuser: appuser, mode: 'publish', source: params?.source])
        }

        def types = SubmissionType.getAll()
        def uploadFileList = UploadedFile.findAllBySourceAndObjId('Submission', submission?.id)
        uploadFileListNewSubmission =  UploadedFile.findAllBySourceAndUploadedByAndObjId('Submission', appuser, 0)

        [appuser: appuser, submission: submission, createSuccess: created,
         recordExists: recordExist, createNew: createNew, updateSuccess: updateSuccess, nameMatch:nameMatch, anchor: anchor,
         projectRefList: projectRefList,   result: result, uploadFileList: uploadFileList, uploadFileListNewSubmission: uploadFileListNewSubmission,
         source: params?.source, profile_id: submission?.submitter?.id, accessRights: submissionService.accessRights(submission,appuser),
         dateResult: dateResult, types: types, disabled: disabled, mode:mode]
    }

    @org.springframework.security.access.annotation.Secured('permitAll')
    def view () {
        Appuser appuser = springSecurityService.currentUser
        def submission

        if (!appuser) redirect(controller: 'login', action: 'auth', params: params)

        if (params.submission_id) {
            session.setAttribute('submissionId', params.submission_id.toLong())
            submission = Submission.get(params.submission_id)
        }
        else if (params.id) {
            session.setAttribute('submissionId', params.id.toLong())
            submission = Submission.get(params.id)
        }
        else if (session.getAttribute('submissionId') ) submission = Submission.get(session.getAttribute('submissionId') as Serializable)

        if (!submission) redirect(controller: 'homePage', action: 'index', source: 'homePage')

        def nameMatch = false
        def anchor = params.anchor
        def projectRefList = []
        def result = [:]
        def dateResult = ['success': true, 'errorMsg': ""]
        def uploadedFileList = UploadedFile.findAllBySourceAndObjId('Submission', submission.id)
        def accessRights = submissionService.accessRights(submission, appuser)

        [appuser: appuser, submission: submission, accessRights: accessRights, nameMatch: nameMatch, anchor: anchor,
         searchInput: params?.searchInput, tagInput: params?.tagInput, uploadFileList: uploadedFileList,
         projectRefList: projectRefList, result: result, source: params?.source, profile_id: params?.profile_id,
         dateResult: dateResult]
    }

    def linkToPage() {
        Appuser appuser = springSecurityService.currentUser
        redirect(controller: "submissions", action: "edit", params: [submission_id: params.id, appuser: appuser, mode: 'publish', source: params?.source, profile_id: params?.profile_id])
    }
}