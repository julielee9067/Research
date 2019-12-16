package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class PublisherService {

    def getSubmissionList(def offset_, def max_) {
        def offset = offset_?.toInteger() ?: 0
        def max = max_?.toInteger() ?: 10
        def c = Submission.createCriteria()

        def submissionList = c.list(max: max, offset: offset) {
            order("publicationDate", "desc")
            eq("submitStatus", Submission.SUBMIT_STATUS.SUBMITTED)
        }
        return submissionList
    }

    def getReturnedList(def offset_, def max_) {
        def offset = offset_?.toInteger() ?: 0
        def max = max_?.toInteger() ?: 10
        def c = Submission.createCriteria()

        def returnedList = c.list(max: max, offset: offset) {
            order("submissionDate", "desc")
            eq("submitStatus", Submission.SUBMIT_STATUS.RETURNED)
        }
        return returnedList
    }
}
