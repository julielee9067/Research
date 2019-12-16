package ca.georgebrown.gbcresearch


import ca.georgebrown.gbcresearch.security.Appuser

class AppuserSearch {

    Appuser user
    String tags
    String search
    String types

    static belongsTo = [Appuser]

    static constraints = {
        tags maxSize: 4000
        search maxSize: 4000
        types maxSize: 4000
        tags nullable: true
        search nullable: true
        types nullable: true
    }

    static mapping = {
        version false
    }
}
