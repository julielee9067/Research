package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import org.springframework.security.access.annotation.Secured


@Secured(['ROLE_APP_MANAGER','ROLE_DEVELOPER'])
class PropertyController {

    def propertyService
    def springSecurityService
    def listManagerService

    def index() {
        Appuser appuser = springSecurityService.currentUser
        def result = [:]

        if (params?.update) {
            propertyService.update(listDomain, params)
        }

        if (params?.add && params?.new) {
            propertyService.add(listDomain, params.new)
        }

        if (params?.delete) {
            def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.properties.${listDomain}").clazz
            result = listManagerService.deleteEntry(clazz, params.delete.keySet().toArray()[0])
        }

        def list = propertyService.list(listDomain)
        [listDomain: listDomain, list: list, appuser: appuser, result: result]
    }
}