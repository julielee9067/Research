package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.plugin.springsecurity.annotation.Secured


@Secured('permitAll')
class SubmissionTypeController {

    def springSecurityService
    def listManagerService
    def submissionTypeService

    def submissionTypesList() {
        Appuser appuser = springSecurityService.currentUser
        def result = [:]

        if (params?.update) {
            submissionTypeService.update(params)
        }

        if (params?.add && params?.new) {
            submissionTypeService.add(params)
        }

        if (params?.delete) {
            result = listManagerService.deleteEntry(SubmissionType, params.delete.keySet().toArray()[0])
        }

        def typeList = SubmissionType.list(['sort': 'name'])
        [types:typeList, appuser:appuser, result: result]
    }
}