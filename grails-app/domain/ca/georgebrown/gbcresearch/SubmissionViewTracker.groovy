package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser

class SubmissionViewTracker {
    Appuser user
    Appuser profileUser
    Submission submission
    Date lastViewDate
    int viewCount = 1

    static constraints = {
        lastViewDate nullable: true
        user nullable: true
        profileUser nullable: true
        submission nullable: true
        viewCount nullable: false, defaultValue: 1
    }
}
