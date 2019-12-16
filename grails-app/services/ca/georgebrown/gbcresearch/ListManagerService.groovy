package ca.georgebrown.gbcresearch

import grails.transaction.Transactional
import org.springframework.web.context.request.RequestContextHolder


@Transactional
class ListManagerService {

    def grailsApplication
    def databaseHelperService

    def list(String domainName) {
        def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.lists.${domainName}").clazz
        return clazz.list([sort: 'name'])
    }

    def update(String domainName, def params, def hasDescription = false) {
        def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.lists.${domainName}").clazz
        def obj = null
        params.name.each() { id, name ->
            obj = clazz.get(id.toLong())
            obj.name = name
            if (hasDescription) obj.description = name
            obj.enabled = params.enabled[id] == 'on'
            obj.save()
        }
    }

    def add(String domainName, def name) {
        def clazz = grailsApplication.getDomainClass("ca.georgebrown.gbcresearch.lists.${domainName}").clazz
        def obj = clazz.findByName(name)
        if (!obj) {
            obj = clazz.newInstance(name: name)
            obj.save()
        }
    }

    def select2ListProcess(def parentObj, Class optionClazz, String targetList, def selection, user) {
        if (parentObj?."${targetList}"!=null) {
            parentObj?."${targetList}".clear()
        }

        if (!selection) return

        if (selection instanceof String[]) {
            selection.each { selected ->
                if (optionClazz.name == "ca.georgebrown.gbcresearch.Tag") {
                    if (selected.isNumber() && Tag.get(selected.toLong())) {
                        def option = optionClazz.get(selected.toLong())
                        parentObj."addTo${targetList.capitalize()}"(option)
                    }
                    else if (Tag.findByName(selected)) {
                        Tag addTag = Tag.findByName(selected)
                        parentObj."addTo${targetList.capitalize()}"(addTag)
                    }
                    else {
                        Tag newTag = new Tag(name:selected, status:Tag.STATUS.INACTIVE, createdById: user.id)
                        newTag.save(flush: true)
                        parentObj."addTo${targetList.capitalize()}"(newTag)
                    }
                }
                else {
                    def option = optionClazz.get(selected.toLong())
                    parentObj."addTo${targetList.capitalize()}"(option)
                }
            }
        }
        else {
            def selected = selection

            if (optionClazz.name == "ca.georgebrown.gbcresearch.Tag") {
                if (selected.isNumber() && Tag.get(selected.toLong())) {
                    def option = optionClazz.get(selected.toLong())
                    parentObj."addTo${targetList.capitalize()}"(option)
                }
                else if (Tag.findByName(selected)) {
                    Tag addTag = Tag.findByName(selected)
                    parentObj."addTo${targetList.capitalize()}"(addTag)
                }
                else {
                    Tag newTag = new Tag(name:selected, status:Tag.STATUS.INACTIVE, createdById: user.id)
                    newTag.save(flush: true)
                    parentObj."addTo${targetList.capitalize()}"(newTag)
                }
            }
            else {
                def option = optionClazz.get(selected.toLong())
                parentObj."addTo${targetList.capitalize()}"(option)
            }
        }
    }

    def filterList (String attribute, String varName, Class listClass) {
        def list = []
        def session = RequestContextHolder.currentRequestAttributes().getSession()
        def filter = session[attribute]

        if (filter) {
            filter[varName].each {
                list.add(listClass.get(it))
            }
        }

        return list
    }

    def filterParam(params, String paramAttribute) {
        def list = []
        if (params[paramAttribute]) {
            if (params[paramAttribute] instanceof String[]) {
                params[paramAttribute].each {
                    list.add(it.toLong())
                }
            }
            else {
                list.add(params[paramAttribute].toLong())
            }
        }
        return list
    }

    def deleteEntry(Class clazz, def deleteId) {
        def c = clazz.get(deleteId.toLong())
        def result = databaseHelperService.delete(clazz, c)
        result.put('id', deleteId)
        return result
    }
}
