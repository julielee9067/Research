package ca.georgebrown.gbcresearch

class Setting {

    static final TYPE = [
            INT:'integer',
            LONG:'long',
            BOOLEAN:'boolean',
            STRING:'string',
            DATE:'date',
            DATETIME:'datetime',
            DOUBLE:'double',
            LIST:'list'
    ]

    static final CODE = [
            DUPLICATE_RECORD_SECONDS:'duplicateRecordSeconds',
            ADDITIONAL_NOTIFICATIONS: 'additionalNotificationSettings',
            EMAIL_DIGEST_RECENT_DAYS: 'emailDigestRecentDays',
            PUBLIC_ACCESS: 'publicAccess',
            SUBMISSION_SCROLL_SETTING: 'submissionScrollSetting',
    ]

    String code
    String description
    String value
    String type
    int seq = 0
    static constraints = {
        description nullable:true, maxSize: 4000
        value maxSize: 4000, nullable:true
    }

    def scrollPickList() {

    }
}
