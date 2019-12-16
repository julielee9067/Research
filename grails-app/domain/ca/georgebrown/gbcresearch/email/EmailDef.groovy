package ca.georgebrown.gbcresearch.email

class EmailDef {
    static final TEMPLATE_CODE = [
            NEW_ACCOUNT:'NEW_ACCOUNT',
            ACTIVATE_ACCOUNT:'ACTIVATE_ACCOUNT',
            PASSWORD_RESET:'PASSWORD_RESET',
            DIGEST: 'DIGEST',
            PUBLISHED: 'PUBLISHED_SUBMISSION',
            RETURNED: 'RETURNED_SUBMISSION',
            NEW_FOLLOWING: 'GET_FOLLOWED'
    ]
    String code
    String description
    String subject
    String ccList
    String bccList
    String sampleJsonData
    static hasMany = [templates:EmailDefTemplate]

    static constraints = {
        code (maxSize:32)
        description(blank:true, nullable:true, html:'noscript', maxSize:1000)
        subject(blank:true, nullable:true, html:'noscript', maxSize:250)
        ccList(blank:true, nullable:true, html:'noscript', maxSize:250)
        bccList(blank:true, nullable:true, html:'noscript', maxSize:250)
        sampleJsonData (nullable: true, maxSize:4000)

    }
}
