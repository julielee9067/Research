package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.email.EmailDef
import ca.georgebrown.gbcresearch.email.EmailDefTemplate
import ca.georgebrown.gbcresearch.email.EmailTemplate
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.support.WebApplicationContextUtils


@Transactional
class MailerService {

    def mailService
    def grailsApplication
    def SettingService
    static int templateSeq = 1
    GroovyPagesTemplateEngine groovyPagesTemplateEngine

    private String renderTemplate(String template, Map model) {
        StringWriter output = new StringWriter()
        def webRequest = RequestContextHolder.getRequestAttributes()

        if (!webRequest) {
            def servletContext  = ServletContextHolder.getServletContext()
            def applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
            webRequest = grails.util.GrailsWebUtil.bindMockWebRequest(applicationContext)
        }

        def pageName = "t_${templateSeq}"
        templateSeq++
        groovyPagesTemplateEngine.createTemplate(template,pageName).make(model).writeTo(output)
        return output.toString()
    }

    def sendEmailUsingTemplate(def templateCode, def emailList, def model = []) {
        EmailDef emailDef = EmailDef.findByCode(templateCode)
        def templates  = EmailDefTemplate.findAllByEmailDef(emailDef,[sort:'sortOrder'])
        def body = ""

        templates*.emailTemplate.each() { EmailTemplate template ->
            body += template.template
        }

        body = renderTemplate(body, model)
        return send(emailList, emailDef.subject, body, emailDef.ccList, emailDef.bccList)
    }

    def send(def emailList,  String theSubject,String body, def ccList = null, def bccList = null) {
        def fromEmail = grailsApplication.config.grails.mail.from

        if (grails.util.Environment.current != grails.util.Environment.PRODUCTION) {
            def session = RequestContextHolder.currentRequestAttributes().getSession()
            def sessionEmail = session.getAttribute('sessionEmail')
            if (sessionEmail) {
                grailsApplication.config.grails.mail.overrideAddress = session.getAttribute('sessionEmail')
            }
        }

        def sendMsg = "Email toList = ${emailList}, override email = ${grailsApplication.config.grails.mail?.overrideAddress?:'no override'}, from = ${fromEmail}"
        log.info(sendMsg)

        try {
            mailService.sendMail {
                to emailList
                if (ccList) cc ccList
                if (ccList) bcc bccList
                from fromEmail
                subject theSubject
                html body.toString()
            }
        }
        catch (Exception e) {
            log.error(e.message)
            return e.message
        }
        return sendMsg
    }

    def sendEmailDigest() {
        def recency = (new Date() - SettingService.get(Setting.CODE.EMAIL_DIGEST_RECENT_DAYS)).toTimestamp()
        def people = ProfileSetting.findAllByNotifyDailyDigest(true)
//        def people = ProfileSetting.findAllByAppuser(Appuser.findByUsername("M19080603")) // Julie
        def newTags = Tag.findAllByStatusAndDateActivatedGreaterThanEquals(Tag.STATUS.ACTIVE, recency)
//        def url = createLink(controller: 'login', absolute: true)
        def researchUrl = 'http://dmzmsa02.georgebrown.ca/Research/login/auth'
        def searchUrl = 'http://dmzmsa02.georgebrown.ca/Research/homePage/index?tagInput='

        people.each { profile ->
            def user = profile.appuser
            def newSubmissionsByFollowing = []

            user?.following?.each { followingPerson ->
                def newContent = Submission.findAllByOwnerAndPublicationDateIsNotNullAndPublicationDateGreaterThan(followingPerson, recency)
                newContent.each { content ->
                    content.putAt('owner', followingPerson)
                    newSubmissionsByFollowing.add(content)
                }
            }

            def newSubmissionsByInteresting = []
            def newSubmissions = Submission.findAllByPublicationDateIsNotNullAndPublicationDateGreaterThan(recency)

            newSubmissions.each { newContent ->
                def similarTags = user.tags.intersect(newContent.tags)
                if (similarTags?.size()) {
                    newContent.putAt('tags', similarTags)
                    newSubmissionsByInteresting.add(newContent)
                }
            }

            def submissionUrl = 'http://dmzmsa02.georgebrown.ca/Research/submissions/view/'
            def interestSource = '?source=homePage'
            def interestTagInput = '&tagInput='

            if (newSubmissionsByFollowing?.size() > 0 || newSubmissionsByInteresting?.size() > 0 || newTags) {
                sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.DIGEST, user.email, [appuser: user, researchUrl: researchUrl,
                                                                                   searchUrl: searchUrl, submissionUrl: submissionUrl,
                                                                                   interestSource: interestSource, newTags: newTags, interestTagInput: interestTagInput,
                                                                                   interests:newSubmissionsByInteresting, following: newSubmissionsByFollowing])
            }
        }
    }
}
