package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.iago.Investigator
import ca.georgebrown.gbcresearch.iago.Partner
import ca.georgebrown.gbcresearch.iago.ResearchTheme
import ca.georgebrown.gbcresearch.security.Appuser

class Submission {
    def submissionService

    static final AVAILABILITY_CODES = [INTERNAL: 'Internal']
    static final STATUS = [ENABLED: 'enabled', DISABLED: 'disabled']
    static final SUBMIT_STATUS = [INITIATED: 'Initiated', IN_EDIT: 'Draft', SUBMITTED: 'Submitted', RETURNED: 'Revision Required', PUBLISHED: 'Published']
    static final SOURCE = [RESEARCH: 'research', IAGO: 'iago']
    static final RIGHT = [READ: 'read', EDIT: 'edit', SUBMIT: 'submit', PUBLISH: 'publish', RECALL: 'recall', RETURN: 'return', DELETE: 'delete']
    static final TYPE = [PROJECT: 'Project', RESEARCH_PAPER: 'Research Paper', WORKSHOP: 'Workshop', CONFERENCE: 'Conference']
    Appuser owner
    Appuser submitter
    Appuser publisher
    Appuser updatedBy

    Date submissionDate = new Date()
    String title
    String description
    String searchText
    String source = SOURCE.RESEARCH
    boolean published
    String rejectionReason
    Date publicationDate
    Date lastUpdated
    String submitStatus = SUBMIT_STATUS.IN_EDIT
    SubmissionType type
    Date expectedStartDate = new Date()
    Date expectedEndDate = new Date()
    String activeStatus = STATUS.ENABLED
    String availability = AVAILABILITY_CODES.INTERNAL
    Boolean needsExternalPublish
    boolean allowExternal = false

    int iagoId
    Date iagoImportDate

    static belongsTo = [owner: Appuser]

    static hasMany = [tags: Tag,
                      partners: Partner,
                      researchThemes: ResearchTheme,
                      principleInvestigators: Investigator,
                      coInvestigators: Investigator,
    ]

    static Submission findByTag (Tag tag) {

        def c = Submission.createCriteria()
        def result = c.get {
            tags {
                idEq(tag.id)
            }
        }
        return result
    }

    static constraints = {
        title                nullable: true, maxSize: 4096
        type                 nullable: true
        searchText           nullable: true
        description          nullable: true, maxSize: 4096
        tags                 nullable: true
        owner                nullable: true
        publisher            nullable: true
        publicationDate      nullable: true
        submitter            nullable: true
        submissionDate       nullable: true
        activeStatus         defaultValue: "'${STATUS.ENABLED}'",nullable: true
        rejectionReason      nullable: true, maxSize: 4096
        expectedEndDate      nullable: true
        expectedStartDate    nullable: true
        availability         nullable: true
        needsExternalPublish nullable: true
        iagoImportDate       nullable: true
    }

    static mapping = {
        availability            defaultValue: "'Internal'"
        needsExternalPublish    defaultValue: 'false'
        allowExternal defaultValue: 'false'
        source defaultValue: "'${SOURCE.RESEARCH}'"
        iagoId defaultValue: '0'
        submitStatus defaultValue: "'${SUBMIT_STATUS.IN_EDIT}'"
        searchText type:'text'
    }



}
