package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class PropertyService {

    def grailsApplication

    def list(String domainName) {
        def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.properties.${domainName}").clazz
        return clazz.list([sort: 'name'])
    }

    def update(String domainName, def params) {
        def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.properties.${domainName}").clazz
        def obj = null

        params.name.each() { id, name ->
            obj = clazz.get(id.toLong())
            obj.name = name
            obj.enabled = params.enabled[id] == 'on'
            obj.save()
        }
    }

    def add(String domainName, def name) {
        def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.properties.${domainName}").clazz
        def obj = clazz.findByName(name)

        if (!obj) {
            obj = clazz.newInstance(name: name)
            obj.save()
        }
    }
}
