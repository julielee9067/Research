package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.Role
import grails.transaction.Transactional
import groovy.time.TimeCategory


@Transactional
class SubmissionService {

    static final FOUND = [NO:'No',YES:'Yes',NAMEMATCH:'NameMatch']

    // This function checks if the submission already exist or not.
    def alreadyExists(def params) {
        def result = [found:FOUND.NO,submission:null]

        if (params.submission?.title) {
            result.submission = Submission.findAllByTitle(params.submission?.title)
            if (result.submission) {
                result.found = FOUND.NAMEMATCH
                return result
            }
        }
        return result
    }

    // This function determines which rights the appuser has on the specific submission.
    def accessRights(Submission submission, Appuser appuser) {
        def rights = [:]
        Submission.RIGHT.each() { right, name->
           rights.put(name, false)
        }

        if (!submission) {
            rights[Submission.RIGHT.EDIT] = true
            return rights
        }

        if (!appuser) { // If the user is not logged in, READ-ONLY
            if (submission.published) rights[Submission.RIGHT.READ] = true
            return rights
        }

        def isOwner = (appuser == submission.submitter)
        def isPublisher = (Role.findByAuthority('ROLE_PUBLISHER') in appuser?.authorities)

        if (submission.source == Submission.SOURCE.IAGO && !isPublisher) { // If the project is from IAGO, READ-ONLY
            rights[Submission.RIGHT.READ] = true
            return rights
        }

        if (submission.published) rights[Submission.RIGHT.READ] = true

        if (isOwner) { // If owner and the project is in EDIT or RETURNED, can EDIT and DELETE
            if (submission.submitStatus in [Submission.SUBMIT_STATUS.IN_EDIT, Submission.SUBMIT_STATUS.RETURNED]) {
                rights[Submission.RIGHT.EDIT] = true
                rights[Submission.RIGHT.DELETE] = true
                rights[Submission.RIGHT.SUBMIT] = true
//                rights[Submission.RIGHT.RECALL] = false
                rights[Submission.RIGHT.READ] = true
            }
            else if (submission.submitStatus == Submission.SUBMIT_STATUS.INITIATED) {
                rights[Submission.RIGHT.EDIT] = true
                rights[Submission.RIGHT.DELETE] = false
                rights[Submission.RIGHT.SUBMIT] = false
//                rights[Submission.RIGHT.RECALL] = false
                rights[Submission.RIGHT.READ] = true
            }
            else if (submission.submitStatus == Submission.SUBMIT_STATUS.SUBMITTED) {
                rights[Submission.RIGHT.RECALL] = true
                rights[Submission.RIGHT.EDIT] = false
                rights[Submission.RIGHT.DELETE] = false
                rights[Submission.RIGHT.SUBMIT] = false
                rights[Submission.RIGHT.READ] = true
            }
        }

        if (isPublisher) { // If the role is publisher, can EDIT, PUBLISH, RETURN, and DELETE
            rights[Submission.RIGHT.EDIT] = true
            rights[Submission.RIGHT.PUBLISH] = true
            rights[Submission.RIGHT.RETURN] = true
            rights[Submission.RIGHT.DELETE] = true
            rights[Submission.RIGHT.READ] = true
            if (!isOwner) {
                rights[Submission.RIGHT.SUBMIT] = false
                rights[Submission.RIGHT.READ] = true
            }
        }
        return rights
    }

    def save(Submission submission) {
        updateSearchText(submission)
        submission.save(flush:true, failOnError: true)
        return submission
    }

    def updateSearchText(Submission submission) {
        submission.searchText = submission.title + " " + submission.description

        if (submission.submitter.username != 'admin') submission.searchText += ' ' + submission.submitter.displayName

        submission.tags.each() { Tag tag ->
            submission.searchText += ' ' + tag.name
        }

        if (submission.source == Submission.SOURCE.IAGO) {
            submission.principleInvestigators.each() {
                submission.searchText += ' ' + it.displayName
            }
            submission.coInvestigators.each() {
                submission.searchText += ' ' + it.displayName
            }
            submission.partners.each() {
                submission.searchText += ' ' + it.organization
            }
            submission.researchThemes.each() {
                submission.searchText += ' ' + it.theme
            }
        }
        return submission
    }

    def deleteInitiated() {
        def initiatedList = Submission.findAllBySubmitStatus(Submission.SUBMIT_STATUS.INITIATED)
        Date now = new Date()
        use(TimeCategory) {
            initiatedList.each() { Submission submission ->
                if (submission.submissionDate < now - 1.day) submission.delete(flush: true)
            }
        }
    }
}
