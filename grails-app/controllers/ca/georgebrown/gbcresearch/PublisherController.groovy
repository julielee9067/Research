package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.email.EmailDef
import ca.georgebrown.gbcresearch.security.Appuser
import grails.plugin.springsecurity.annotation.Secured


@Secured('ROLE_PUBLISHER')
class PublisherController {

    def springSecurityService
    def publisherService
    def mailerService
    def securityService
    def submissionService

    def list() {
        Appuser appuser = springSecurityService.currentUser
        def submissionList = publisherService.getSubmissionList(params.offset, params.max)
        def returnedList = publisherService.getReturnedList(params.offset, params.max)
        [fsList: submissionList, total: submissionList.totalCount, rtList: returnedList, rtTotal: returnedList.totalCount, appuser:appuser, source: 'publisher']
    }

    def publish() {
        Appuser appuser = springSecurityService.currentUser
        Submission submission
        submission = Submission.findById(params.id.toLong())
        submission.publisher = appuser
        submission.published = true
        submission.publicationDate = new Date()
        submission.submitStatus = Submission.SUBMIT_STATUS.PUBLISHED
        submission.availability = Submission.AVAILABILITY_CODES.INTERNAL
        submission = submissionService.save(submission)
        sendEmailSubmissionStatus(submission)
        redirect(controller: "publisher", action: "list")
    }

    def reject() {
        Submission submission
        submission = Submission.findById(params.id)
        submission.rejectionReason = params.reason
        submission.published = false
        submission.submitStatus = Submission.SUBMIT_STATUS.RETURNED
        submission = submissionService.save(submission)
        sendEmailSubmissionStatus(submission)
        redirect(controller: "publisher", action: "list")
    }

    def update () {
        Submission submission
        Appuser appuser = springSecurityService.currentUser
        submission = Submission.findById(params.id)

        if (submission) {
            if (params._action_update == 'Publish') {
                submission.publisher = appuser
                submission.published = true
                submission.publicationDate = new Date()
                submission.submitStatus = Submission.SUBMIT_STATUS.PUBLISHED
            }
            else {
                submission.rejectionReason = params.reason
                submission.published = false
                submission.submitStatus = Submission.SUBMIT_STATUS.RETURNED
            }
            submission = submissionService.save(submission)
        }
        redirect(controller: "publisher", action: "list")
    }

    def sendEmailSubmissionStatus(Submission submission) {
        def emailSent = false
        def submitter = submission?.submitter
        def email = submission?.submitter?.email

        if (!email) {
            flash.error = message(code: 'spring.security.ui.forgotPassword.user.notFound')
        }
        else {
            if (submission?.submitStatus == Submission.SUBMIT_STATUS.RETURNED) {
                mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.RETURNED, email, [appuser: submitter, submission: submission, url: securityService.checkSubmissionStatus(submission)])
                emailSent = true
            }
            else if (submission?.submitStatus == Submission.SUBMIT_STATUS.PUBLISHED) {
                mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.PUBLISHED, email, [appuser: submitter, submission: submission, url: securityService.checkSubmissionStatus(submission)])
                emailSent = true
            }
        }

        return [emailSent: emailSent]
    }
}
