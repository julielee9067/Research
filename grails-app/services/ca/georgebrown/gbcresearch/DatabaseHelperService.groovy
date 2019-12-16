package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class DatabaseHelperService {

    def delete(Class clazz,def instance) {
        def result = [success:true, errorMsg:null]
        clazz.withTransaction { status ->
            try {
                instance.delete(flush: true)
            }
            catch (Exception e) {
                result.errorMsg = e.message
                status.setRollbackOnly()
            }

            if (status.isRollbackOnly()) {
                instance.discard()
                result.success = false
            }
        }
        return result
    }

    // This function helps to delete certain element with all the associated element.
    def removeAssociated(def obj, def associatedLists) {
        associatedLists.each() { list->
            def alObjList = obj[list].collect()
            alObjList.each() {
                obj."removeFrom${list.capitalize()}"(it)
            }
        }

        obj.save(flush:true)
        obj.refresh()
        return obj
    }
}
