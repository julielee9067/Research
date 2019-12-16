package ca.georgebrown.gbcresearch.email

class EmailDefTemplate {

    static belongsTo = [emailTemplate:EmailTemplate, emailDef:EmailDef]
    String sortOrder
    Boolean pageBreak = false

    static constraints = {
        sortOrder (nullable: false)
    }

    static mapping = {
        version false

        pageBreak defaultValue:'false'
    }
}
