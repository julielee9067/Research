package ca.georgebrown.gbcresearch

class SubmissionType {

    static final STATUS = [ACTIVE:'Active', INACTIVE:'Inactive']

    String name
    String description
    String status = STATUS.ACTIVE
    int sortOrder=0

    static constraints = {
        description nullable: true
        description maxSize: 4000
    }
    static mapping = {
        sortOrder defaultValue:'0'
    }
}

