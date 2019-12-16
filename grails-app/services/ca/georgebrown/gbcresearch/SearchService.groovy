package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.transaction.Transactional
import groovy.sql.Sql


@Transactional
class SearchService {

    def dataSource

    // This function returns the list of the people by the search string. (currently not in use)
    def peopleSearch(String searchString, params) {
        def peopleList = []
        def offset = params?.offset?.toInteger() ?: 0
        def max = params?.max?.toInteger() ?: 10
        Sql sql = Sql.newInstance(dataSource)

        def queryMatching = """Select * from (select id, text || ' ' || tag_arr as search_text from (
                                    select a.id, a.first_name || ' ' || a.last_name as "text",
                                        ( Select CASE WHEN (select show_interests from profile_setting ps where appuser_id = a.id) = true then array_to_string(array(select t.name from tag t join appuser_tags at on t.id = at.tag_id WHERE at.appuser_id = a.id), ', ' )
                                                        Else ' '
                                                 END
                                        ) as tag_arr
                                    from appuser a) x) y
                               where true"""

        if (searchString) {
            def search = searchString.split(' ')
            search.each { searchWord ->
                def searchWordString = "'%" + searchWord + "%'"
                queryMatching += " and search_text ilike" + searchWordString
            }
        }

        def query = queryMatching + """ OFFSET :offset FETCH next :max rows only"""
        def count = sql.rows(queryMatching)?.size() ?: 0
        def result = sql.rows(query, [searchString:searchString, max: max, offset: offset])

        result.each { id->
            def match = [:]
            match.user = Appuser.findById(id.values()[0])
            match.settings = ProfileSetting.findByAppuser(match.user)
            peopleList.add(match)
        }

        [result: peopleList, count: count]
    }

    // This function is for the public view of the projects (Julie)
    def publishedList(params) {
        def searchText = params.searchInput?"%${params.searchInput}%":null
        def c = Submission.createCriteria()
        def submissionList = c.list(max: params.max ?: 10, offset: params.offset ?: 0) {
            order("publicationDate", "desc")
            eq("published", true)
            if (searchText) {
                or {
                    ilike("searchText",searchText)
                }
            }
        }
        return submissionList
    }

    def publishedListByType(def typeName, def params) {
        SubmissionType submissionType = SubmissionType.findByName(typeName)
        def resultList = Submission.findAllByTypeAndPublished(submissionType, true, [offset: params.offset ?: 0, max: params.max ?: 10])
        resultList.sort{a,b -> b.submissionDate <=> a.submissionDate}.unique()
        def searchCount = Submission.findAllByTypeAndPublished(submissionType, true).size()
        return [resultList: resultList, searchCount: searchCount]
    }

    // This function returns the following users' submissions.
    def followingUsersSubmissionList(Appuser appuser, def params) {
        def submissionList = []
        if(!appuser?.following?.size()) return submissionList
        def c = Submission.createCriteria()
        submissionList = c.list(max: params?.max ?: 10, offset: params?.offset ?: 0) {
                eq("published", true)
                'in'("submitter", appuser.following)
                order("publicationDate", "desc")
        }
        return submissionList
    }

    // This function gets the list of the submissions that has the same tags as the user's interest tags.
    def interestedSubmissionList(Appuser appuser, def params) {
        if(appuser) {
            Sql sql = Sql.newInstance(dataSource)
            def countQuery = "select submission_id from submission_tags where tag_id in (select tag_id from appuser_tags where appuser_id = ${appuser.id})" +
                    " and (select published from submission where id = submission_id) = true and ((select submitter_id from submission where id = submission_id) != ${appuser.id})" +
                    "group by submission_id order by (select publication_date from submission where id = submission_id) desc "

            def result = countQuery + "offset :offset fetch next :max rows only"

            def totalCountRows = sql.rows(countQuery)
            def submissionList = sql.rows(result, [max: params?.max?.toInteger() ?: 10, offset: params?.offset?.toInteger() ?: 0])
            sql.close()

            def interestedSubmissions = []
            def totalCount = totalCountRows.size()

            submissionList.each() { def submissionId ->
                interestedSubmissions.add(Submission.findById(submissionId.submission_id.value))
            }
            return [interestedSubmissions: interestedSubmissions, interestedSubmissionsCount: totalCount]
        }
        return [interestedSubmissions: [], interestedSubmissionsCount: 0]
    }

    // This function returns the list of submissions that contains the specific tag. (invoked when the user clicks on the tag.)
    def tagResultList(params) {
        def tagInput = params.tagInput?"${params.tagInput}":null
        tagInput = tagInput.replaceAll('\\+', ' ')
        def tagFound = Tag.findAllByName(tagInput)[0]
        def tagSubmissionList = tagFound?.submissions
        if (tagSubmissionList) {
            tagSubmissionList = tagSubmissionList.sort{a,b -> b.publicationDate <=> a.publicationDate}
            tagSubmissionList = tagSubmissionList.unique()
        }
        return tagSubmissionList
    }
}
