package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser

class Profile {

    Appuser appuser
    static hasMany = [following: Appuser ]

    String biography
    Boolean showPublished = true
    Boolean showSubmitted = true
    Boolean showCreated = true
    Boolean showRejected = true
    Boolean showProject = true
    Boolean showConference = true
    Boolean showResearchPaper = true
    Boolean showWorkshop = true

    Boolean newUser = true

    static constraints = {
        biography nullable: true, maxSize: 4096, defaultValue: null
        showPublished defaultValue: 'true'
        showSubmitted defaultValue: 'true'
        showCreated defaultValue: 'true'
        showRejected defaultValue: 'true'
        showProject defaultValue: 'true'
        showConference defaultValue: 'true'
        showResearchPaper defaultValue: 'true'
        showWorkshop defaultValue: 'true'
        newUser defaultValue:'true'
    }

    static mapping = {
        version false
    }
}
