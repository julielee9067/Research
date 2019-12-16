package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.plugin.springsecurity.annotation.Secured


@Secured('permitAll')
class TagsController {

    def springSecurityService
    def tagService
    def databaseHelperService
    def submissionService

    def index() {
        redirect(action: "tagsList", params: params)
    }

    def tagsList() {
        Appuser appuser = springSecurityService.currentUser
        def result = [:]
        def tagName = ''
        def activatedTagList
        def inactiveTagList
        Tag tag

        if (params?.update) {
            tag = tagService.update(params)
            result.message = "Tags updated."
        }

        if (params?.add && params?.new && params?."new.name" != "") {
            tag = tagService.add(params)
            result.message = "Tag ${tag.name} added."
        }

        if (params?.delete) {
            try {
                tag = Tag.findById(params.delete.keySet().toArray()[0].toLong())
                def submissions = []

                for (temp in tag.submissions) {
                    submissions << temp
                }

                databaseHelperService.removeAssociated(tag,['appusers','submissions'])
                result.message = "Tag ${tag.name} deleted."
                tag.delete(flush: true, failOnError: true)

                for (submission in submissions) {
                    submissionService.save(submission)
                }
            }
            catch (Exception e) {
                log.error(e.message)
                print(e)
                result.message = "Unexpected error occurred. Please contact GBC IT support."
            }
        }

        tagName = params?.tagName
        def c = Tag.createCriteria()

        activatedTagList = c.list(max: params.max ?: 10, offset: params.offset ?: 0) {
            eq("status", Tag.STATUS.ACTIVE)
            if (tagName) ilike("name", '%' + tagName + '%')
        }

        def d = Tag.createCriteria()

        inactiveTagList = d.list(max: params.max ?: 10, offset: params.offset ?: 0) {
            eq("status", Tag.STATUS.INACTIVE)
            if (tagName) ilike("name", '%' + tagName + '%')
        }

        activatedTagList = activatedTagList.sort{-it.usageCnt}
        inactiveTagList = inactiveTagList.sort{-it.usageCnt}
        [activeList: activatedTagList, inactiveList: inactiveTagList, appuser: appuser, result: result, tagName: tagName]
    }

    def ajaxInterests() {
        if (params.id) {
            def tags = Appuser.get(Long.valueOf(params.id)).tags
            def tagIds = []

            tags.each() {
                tagIds.add(it.id.toString())
            }

            render(contentType: 'text/json') {
                delegate.tags = tagIds
            }
        }
    }

    def ajaxInterestsName() {
        if (params.id) {
            def tags = Appuser.get(Long.valueOf(params.id)).tags
            def tagNames = []

            tags.each() {
                tagNames.add(it.name.toString())
            }

            render(contentType: 'text/json') {
                delegate.tags = tagNames
            }
        }
    }

    def deleteTagPublisher() {
        def result = [:]
        try {
            def tag = Tag.findByName(params.title) ?: null

            if (tag != null) {
                databaseHelperService.removeAssociated(tag,['appusers','submissions'])
                result.message = "Tag ${tag.name} deleted."
                tag.delete(flush:true)
                def submissions = tag.submissions
                for (submission in submissions) {
                    submissionService.save(submission)
                }
            }

            result.message = "Tag ${params.title} deleted."
        }
        catch (Exception e) {
            log.error(e.message)
            result.message = "Unexpected error. Contact support."
        }
        finally {
            return result
        }
    }
}