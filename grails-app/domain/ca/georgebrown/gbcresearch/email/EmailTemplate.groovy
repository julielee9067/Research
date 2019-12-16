package ca.georgebrown.gbcresearch.email

class EmailTemplate {

    String code
    String description
    String template

    static hasMany = [emails:EmailDefTemplate]

    static constraints = {
        code(blank:false, maxSize:32, unique: true)
        template(blank:false, html:'noscript', maxSize: 8000)
        description(blank:true, nullable:true, html:'noscript',maxSize:1000)
    }

    static mapping = {

    }
}
