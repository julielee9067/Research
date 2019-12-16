package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.email.EmailDef
import ca.georgebrown.gbcresearch.security.Appuser
import grails.converters.JSON
import grails.util.Environment
import org.springframework.security.access.annotation.Secured
import groovy.json.JsonSlurper


@Secured('ROLE_DEVELOPER')
class DebugController {

    def ldapService
    def mailerService
    def fileUploadService
    def integrationService
    def profileService
    def iagoService

    /* This is required to set the session email for debugging */
    @Secured('permitAll')
    def ajaxSetSessionEmail() {
        if (Environment.current != Environment.PRODUCTION) {
            def sessionEmail = params?.sessionEmail
            if (sessionEmail) {
                session.setAttribute('sessionEmail', sessionEmail)
            }
            render([sessionEmail: session.getAttribute('sessionEmail')] as JSON)
        }
    }

    def url() {
        def url = createLink(controller: 'login', action: 'auth', absolute: true)
        println url
    }

    def ldap () {
        def result = ldapService.getAttributes("M18031301")
        [result:result]
    }

    def emailTest() {
       // mailerService.send("tony.minichillo@gmail.com",null,"Test Subject","This is the body")
        Appuser appuser = Appuser.findByLastName('Minichillo')
        mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.NEW_ACCOUNT,"tony.minichillo@georgebrown.ca", [appuser:appuser])
    }

    def uploadTest() {
        println params
        if (params.containsKey("send")) {
            UploadedFile uploadedFile = fileUploadService.uploadFile(params, request)
            if (!uploadedFile) {
                //file fail to upload
            }
        }
        def myCustomMsg = ""
        [ msg:myCustomMsg]
    }

    def ldapQuery() {
        //  def result = ca.georgebrown.bai.LdapQuery.getAttributes(params.id)
        def result = ldapService.getAttributes(params.id)
        [result:result]
    }

    def importActiveEmployees() {
        def result = integrationService.importActiveEmployees()
        render (result as JSON)
    }

    def tagCreator() {
        def map = []
        String tag = params.tag
        Tag finder = Tag.findByName(tag)
        if ( !finder ) {
            finder = new Tag(name: tag)
            finder.save(flush: true, failOnError: true)
        }
        map.add(tag)
        render (map as JSON)
    }

    def tagDelete() {
        Tag tag = Tag.findByName(params.id)
        tag.submissions.each() {tag.removeFromSubmissions(it)}
        tag.delete(failOnError:true,flush:true)
        render ([tagList:Tag.list()] as JSON)
    }

    def assignRandomInterests() {
        def tagList = Tag.list()
        Appuser.list().each() { Appuser u->
            if (!(u.id%4)) {
                if (!u.tags.size()) {
                    tagList.each() { Tag t->
                        if (Math.random() < 0.1) {
                            u.addToTags(t)
                        }
                    }
                    u.save(flush: true, failOnError: true)
                }
            }
        }
        render (['done'] as JSON)
    }

    def makeProfiles() {
        Appuser.list().each() { user ->
            profileService.addProfile(user)
            profileService.defaultSettings(user)
        }
    }

    def sendEmailDigestTest() {
        def recency = (new Date() - 40).toTimestamp()
        def people = ProfileSetting.findAllByNotifyDailyDigest(true)
        def newTags = Tag.findAllByStatusAndDateActivatedGreaterThanEquals(Tag.STATUS.ACTIVE, recency)
        def url = createLink(controller: 'login', absolute: true)
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
            mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.DIGEST, user.email, [appuser: user, url: url, newTags: newTags, interests:newSubmissionsByInteresting, following: newSubmissionsByFollowing])
        }
    }

    def testBenchmark() {
        def jsonSlurper = new JsonSlurper()
        def jsonS = jsonSlurper.parseText('{"201901":"2019-01-01","201902":"2019-02-02"}')
        render ( jsonS as JSON)
    }

    def importIagoProjects() {
        def result = iagoService.importIagoProjects()
        render (result as JSON)
    }

    def getIagoList() {
        def result = [:]
        IagoService.IAGO_LIST.each() {k,v->
            result.put(k,iagoService.getIagoProjectList(v,params.id))
        }
        render (result as JSON)
    }

    def doit() {
        def g = grailsApplication.mainContext.getBean('ca.georgebrown.gbcresearch.ProfileSettingTagLib')
        def result = g.scrollSettingList()
        render (result as JSON)
    }
}
