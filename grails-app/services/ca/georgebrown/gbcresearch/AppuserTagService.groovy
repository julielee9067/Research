package ca.georgebrown.gbcresearch

import grails.transaction.Transactional


@Transactional
class AppuserTagService {

    // This function adds interest tag to a specific user.
    def addInterest(appuser, params) {
        appuser.tags = null
        def tagsToAdd = params.tagsInterest

        if (tagsToAdd instanceof String) {
            def completeTag = Tag.get(tagsToAdd)
            try {
                appuser.addToTags(completeTag)
                appuser.save(failOnError:true)
            }
            catch(Exception e) {
                println(e)
            }
        }
        else {
            tagsToAdd.each { tagToAdd ->
                def completeTag = Tag.get(tagToAdd)
                appuser.addToTags(completeTag)
                appuser.save(failOnError:true)
            }
        }
    }
}
