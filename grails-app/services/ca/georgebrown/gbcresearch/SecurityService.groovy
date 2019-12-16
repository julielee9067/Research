package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.RegistrationCode
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.mapping.LinkGenerator


@Transactional
class SecurityService {

    LinkGenerator grailsLinkGenerator

    def resetPasswordLink(Appuser appuser) {
        def registrationCode = new RegistrationCode(username: appuser.username)
        registrationCode.save(flush: true)
        return grailsLinkGenerator.link(controller: 'login', action: 'resetPassword', params:[t: registrationCode.token], absolute: true)
    }

    def checkSubmissionStatus(Submission submission) {
        grailsLinkGenerator.link(controller: 'submissions', action: 'view', params:[id: submission.id], absolute: true)
    }
}
