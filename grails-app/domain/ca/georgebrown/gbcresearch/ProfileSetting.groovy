package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser

class ProfileSetting {
    Appuser appuser

    boolean showEmail
    boolean showRecentlyViewed
    boolean showInterests
    boolean showFollowing
    boolean notifyInterestedPost
    boolean notifyFollowedUserPost
    boolean notifyDailyDigest
    static final SCROLL_SETTING = [DEFAULT: 'DEFAULT', SCROLL: 'SCROLL', PAGING: 'PAGING']
    static final SCROLL_SETTING_SELECT_LIST = [(SCROLL_SETTING.DEFAULT): 'DEFAULT', (SCROLL_SETTING.SCROLL): 'Infinite Scroll', (SCROLL_SETTING.PAGING): 'Paging']

    String scrollSetting = SCROLL_SETTING.SCROLL
    static belongsTo = Appuser

    static constraints = {
        showEmail defaultValue: false
        showRecentlyViewed defaultValue: false
        showInterests defaultValue: false
        showFollowing defaultValue: false
        notifyInterestedPost defaultValue: false
        notifyFollowedUserPost defaultValue: false
        notifyDailyDigest defaultValue: false
    }

    static mapping = {
        scrollSetting defaultValue: "'${SCROLL_SETTING.DEFAULT}'"
        version false
    }

}
