package ca.georgebrown.gbcresearch.iago

import ca.georgebrown.gbcresearch.Submission

class Partner {
    String organization

    static constraints = {
        organization blank: false
    }
    static mapping = {
        version false
    }
    static belongsTo = [Submission]
    static hasMany = [submissions: Submission]
}
