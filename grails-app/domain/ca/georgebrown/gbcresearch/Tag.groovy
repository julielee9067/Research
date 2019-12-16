package ca.georgebrown.gbcresearch



import ca.georgebrown.gbcresearch.security.Appuser

class Tag {

    static final STATUS = [ACTIVE:'Active', INACTIVE:'Inactive']

    String name
    String status = STATUS.INACTIVE
    Date dateActivated
    Long createdById

    static hasMany = [submissions: Submission, appusers: Appuser]

    static belongsTo = [Submission, Appuser]

    static mapping = {
        version false
    }

    static constraints = {
        dateActivated nullable: true
        status defaultValue: 'Tag.STATUS.INACTIVE'
        createdById nullable: true
      }


    def getUsageCnt() {
        if (submissions || appusers) {
            return submissions?.size()?.toInteger() + appusers?.size()?.toInteger()
        }
        else {
            return 0
        }
    }


    def setTagStatus(def stat) {
        this.status = stat
        this.dateActivated = new Date()
        this.save(flush:true)
    }

}
