package ca.georgebrown.gbcresearch

class ScoreWeightingFactors {

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
        VIEW_SCORE_A_TO_B: 'viewScoreAToB',
        VIEW_SCORE_B_TO_A: 'viewScoreBToA',
        VIEW_SCORE_SAME_CONTENT: 'viewScoreSameContent',
        VIEW_RECENCY_FACTOR: 'viewRecencyFactor',
        VIEW_SCORE_GENERAL: 'viewScoreGeneral',
        FOLLOWING_SCORE_B_FOLLOWS_A: 'followingScoreBFollowsA',
        FOLLOWING_SCORE_MUTUAL_FOLLOWERS: 'followingScoreMutualFollowers',
        FOLLOWING_SCORE_MUTUAL_FOLLOWINGS: 'followingScoreMutualFollowings',
        FOLLOWING_RECENCY_FACTOR: 'followingRecencyFactor',
        FOLLOWING_SCORE_GENERAL: 'followingScoreGeneral',
        INTEREST_SCORE_SAME_INTEREST: 'interestScoreSameInterest',
        INTEREST_SCORE_SAME_TAGS: 'interestScoreSameTags',
        INTEREST_SCORE_INTEREST_FROM_A_MATCHES_TAG_FROM_B: 'interestScoreInterestFromAMatchesTagFromB',
        INTEREST_SCORE_GENERAL: 'interestScoreGeneral',
        PROFILE_ACCESS_SCORE_A_CLICKING_B: 'profileAccessScoreAClickingB',
        PROFILE_ACCESS_SCORE_B_CLICKING_A: 'profileAccessScoreBClickingA',
        PROFILE_ACCESS_RECENCY_FACTOR: 'profileAccessRecencyFactor',
        PROFILE_ACCESS_SCORE_GENERAL: 'profileAccessScoreGeneral',
        END_USER_ID: 'endUserId'
    ]

    String code
    String description
    String value
    String type
    int seq = 0
    static constraints = {
        description nullable: true, maxSize: 4000
        value maxSize: 4000, nullable: true
    }
}
