package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser

class UserMatching {
    Appuser user
    Appuser potentialMatch
    double score
    boolean blacklisted
    boolean following
    Date blacklistTime
    Date followTime
    Date unfollowTime

    static constraints = {
        score nullable: true, defaultValue: 0
        blacklisted defaultValue: false
        following defaultValue: false
        blacklistTime nullable: true, defaultValue: null
        followTime nullable: true, defaultValue: null
        unfollowTime nullable: true, defaultValue: null
    }
}
