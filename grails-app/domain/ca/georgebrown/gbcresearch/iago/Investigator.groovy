package ca.georgebrown.gbcresearch.iago


import ca.georgebrown.gbcresearch.Submission
import ca.georgebrown.gbcresearch.security.Appuser

class Investigator {
    int iagoStakeholderId
    String firstName
    String lastName
    String email
    Appuser appuser
    static constraints = {
        appuser nullable: true
        email nullable: true
    }
    static mapping = {
        version false
    }
    static belongsTo = [Submission]
    static hasMany = [submissions:Submission]

    def getDisplayName() {
        def lname = lastName
        def bracketPos = lname.lastIndexOf("(")
        if (bracketPos!=-1)  lname = lname.substring(0,bracketPos) //remove bracketed text
        return "${firstName} ${lname}"
    }

}
