package ca.georgebrown.gbcresearch.email

import ca.georgebrown.gbcresearch.security.Appuser

class EmailLog {

    EmailDef emailDef
    Appuser recipient
    Date sendDate
    String toList
    String ccList
    Long objId
    static constraints = {
        recipient (nullable:true)
        ccList (nullable: true)
        toList (nullable: true)
        objId (nullable:true)
    }
}
