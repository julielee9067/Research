package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.transaction.Transactional
import groovy.sql.Sql
import groovy.time.TimeCategory


@Transactional
class UserMatchService {

    def dataSource

    // Get weight factor by code from the domain
    def getWeightFactor(def code) {
        ScoreWeightingFactors scoreWeightingFactors = ScoreWeightingFactors.findByCode(code)

        if (!scoreWeightingFactors || !(scoreWeightingFactors.value)) return null

        scoreWeightingFactors.refresh()

        switch (scoreWeightingFactors.type) {
            case Setting.TYPE.INT:
                return scoreWeightingFactors.value.toInteger()
            case Setting.TYPE.DOUBLE:
                return scoreWeightingFactors.value.toDouble()
        }

        return scoreWeightingFactors.value
    }

    // Set weight factor by code
    def setWeightFactor(String code, def val) {
        ScoreWeightingFactors scoreWeightingFactors = ScoreWeightingFactors.findByCode(code)

        if (!scoreWeightingFactors) return false

        scoreWeightingFactors.value = val
        scoreWeightingFactors.save(flush: true, failOnError: true)
        return true
    }

    // This function is should only run when we truncate the "User_matching" table.
    // This function would set "following" column to true if the user is in the following list.
    def setFollowingToTrueInInitialization() {
        def userList = Appuser.findAllByActive(true)

        userList.each() { Appuser user ->
            if (user.following) {
                def followingList = getFollowingList(user)

                followingList.each() { Appuser followingPerson ->
                    UserMatching userMatching = initialize(user, followingPerson).userMatching
                    userMatching.following = true
                }
            }
        }
    }

    // Create userMatching if it doesn't exist already
    def initialize(Appuser userA, Appuser userB) {
        UserMatching userMatching = UserMatching.findByUserAndPotentialMatch(userA, userB)
        def totalScore = calculateScores(userA, userB)

        if (!userMatching && totalScore > 0) {
            userMatching = new UserMatching(user: userA, potentialMatch: userB, score: totalScore)
            userMatching.save(flush: true, failOnError: true)
        }
        else if (userMatching) {
            userMatching.score = totalScore
        }

        return [userMatching: userMatching, totalScore: totalScore]
    }

    // Apply recency factor to the score
    double applyRecency (double score, def recencyFactor, Date date) {
        def dateDifference
        use(TimeCategory) {
            Date now = new Date()
            dateDifference = now[Calendar.DAY_OF_MONTH] - date[Calendar.DAY_OF_MONTH]
        }
        return score * (recencyFactor ** dateDifference)
    }

    // Apply weight factor to the score
    double applyWeight (double score, def weightFactor) {
        return score * weightFactor
    }

    // Add a count if the submission/profile has been viewed again on the next day.
    // EX) If a submission/profile was viewed at 11:59 PM and 12:01 AM on the next day, the view count would increment.
    def checkViewInOneDayAndAddAViewCount(SubmissionViewTracker submissionViewTracker) {
        Date now = new Date()
        use(TimeCategory) {
            if (submissionViewTracker?.lastViewDate) {
                def viewDate = submissionViewTracker?.lastViewDate?.getAt(Calendar.DAY_OF_MONTH) + 1 < now[Calendar.DAY_OF_MONTH]
                if (viewDate) submissionViewTracker.viewCount++
            }
            submissionViewTracker.lastViewDate = now
        }
    }

    // Track view for the submission
    def viewSubmission(Appuser user, Submission submission) {
        if (submission?.submitter != user && submission.published) {
            SubmissionViewTracker submissionViewTracker = SubmissionViewTracker.findByUserAndSubmission(user, submission)

            if (!submissionViewTracker) {
                submissionViewTracker = new SubmissionViewTracker(user: user, submission: submission)
            }

            checkViewInOneDayAndAddAViewCount(submissionViewTracker)
            submissionViewTracker.save(flush: true, failOnError: true)
        }
    }

    // This function records the view to the user's profile.
    def viewProfile(Appuser user, Appuser profileUser) {
        if (user != profileUser) {
            SubmissionViewTracker submissionViewTracker = SubmissionViewTracker.findByUserAndProfileUser(user, profileUser)

            if (!submissionViewTracker) {
                submissionViewTracker = new SubmissionViewTracker(user: user, profileUser: profileUser)
            }

            checkViewInOneDayAndAddAViewCount(submissionViewTracker)
            submissionViewTracker.save(flush: true, failOnError: true)
        }
    }

    // This function deletes every record related to specific submission.
    def deleteAllSubmissionView(Submission submission) {
        print("Deleting the submission record: ${submission.id}")
        SubmissionViewTracker.executeUpdate("delete SubmissionViewTracker submissionViewTracker where submission = :submission", [submission: submission])
    }

    // Add a score if both users viewed same content
    def viewScoreSameContent(Appuser userA, Appuser userB) {
        def viewA = SubmissionViewTracker.findAllByUser(userA)
        def viewB = SubmissionViewTracker.findAllByUser(userB)
        double viewScore = 0
        viewA.each() { SubmissionViewTracker A ->
            viewB.each() { SubmissionViewTracker B ->
                if (A.submission != null && A.submission == B.submission) {
                    viewScore += applyRecency(A.viewCount, getWeightFactor(ScoreWeightingFactors.CODE.VIEW_RECENCY_FACTOR), A.lastViewDate)
                }
            }
        }
        print("view score on same content: ${viewScore}")
        return viewScore
    }

    // Add scores as much as the view count if A viewed B's content
    def viewScoreAViewedB(Appuser userA, Appuser userB) {
        def viewA = SubmissionViewTracker.findAllByUser(userA)
        double viewScore = 0
        use(TimeCategory) {
            viewA.each() { SubmissionViewTracker A ->
                if (A.submission?.submitter == userB) {
                    viewScore += applyRecency(A.viewCount, getWeightFactor(ScoreWeightingFactors.CODE.VIEW_RECENCY_FACTOR), A.lastViewDate)
                }
            }
        }
        print("view score on ${userA.firstName} viewed ${userB.firstName}: ${viewScore}")
        return viewScore
    }

    // This function gets the list of followers for the user
    def getFollowerList(Appuser user) {
        def followerUserMatchingList = UserMatching.findAllByFollowingAndPotentialMatch(true, user)
        def followerList = []
        followerUserMatchingList.each() { UserMatching userMatching ->
            followerList.add(userMatching.user)
        }
        return followerList
    }

    // This function gets the list of followings for the user
    def getFollowingList(Appuser user) {
        def followingUserMatchingList = UserMatching.findAllByFollowingAndUser(true, user)
        def followingList = []
        followingUserMatchingList.each() { UserMatching userMatching ->
            followingList.add(userMatching.potentialMatch)
        }
        return followingList
    }

    // Add score if B is following A
    def followingScoreBFollowingA(Appuser userA, Appuser userB) {
        double followingScore = 0
        def followersA = getFollowerList(userA)

        followersA.each() { Appuser follower ->
            if (follower == userB) {
                followingScore++
            }
        }

        print("following score on ${userB.firstName} following ${userA.firstName}: ${followingScore}")
        return followingScore
    }

    // Add scores if A and B have mutual followers or followings
    def followingScoreMutualFollowersAndFollowings(Appuser userA, Appuser userB) {
        double followingScore = 0
        double followerScore = 0
        def followersA = getFollowerList(userA)
        def followersB = getFollowerList(userB)
        def followingsA = getFollowingList(userA)
        def followingsB = getFollowingList(userB)
        UserMatching userMatching

        followersA.each() { Appuser A ->
            followersB.each() { Appuser B ->
                if (A == B) {
                    userMatching = UserMatching.findByPotentialMatchAndUser(userA, A)
                    followerScore += applyRecency(1, getWeightFactor(ScoreWeightingFactors.CODE.FOLLOWING_RECENCY_FACTOR), userMatching.followTime)
                }
            }
        }
        followingsA.each() { Appuser A ->
            followingsB.each() { Appuser B ->
                if (A == B) {
                    userMatching = UserMatching.findByUserAndPotentialMatch(userA, A)
                    followingScore += applyRecency(1, getWeightFactor(ScoreWeightingFactors.CODE.FOLLOWING_RECENCY_FACTOR), userMatching.followTime)
                }
            }
        }
        print("following score on mutual followers: ${followerScore}")
        print("following score on mutual followings: ${followingScore}")

        return applyWeight(followingScore, getWeightFactor(ScoreWeightingFactors.CODE.FOLLOWING_SCORE_MUTUAL_FOLLOWINGS)) +
                applyWeight(followerScore, getWeightFactor(ScoreWeightingFactors.CODE.FOLLOWING_SCORE_MUTUAL_FOLLOWERS))
    }

    // This function gets Tag List from a list of submissions
    def getTagList(def submissionList) {
        def tagList = []
        submissionList.each { Submission submission ->
            if (submission.tags) {
                submission.tags.each() { Tag tag ->
                    if (!(tagList?.contains(tag))) {
                        tagList.add(tag)
                    }
                }
            }
        }
        return tagList
    }

    // Add a score if both A and B have same interest on their profiles
    def interestScoreSameInterest(Appuser userA, Appuser userB) {
        def tagA = userA.tags
        def tagB = userB.tags
        double interestScore = 0
        tagA.each() { Tag A ->
            tagB.each() { Tag B ->
                if (A == B) {
                    interestScore++
                }
            }
        }
        print("interest score on same interests: ${interestScore}")
        return interestScore
    }

    // Add a score if A's interest and B's submission have same tags
    def interestScoreInterestUserASubmissionTagUserB(Appuser userA, Appuser userB) {
        def interestTagA = userA.tags
        def submissionB = userB.submissions
        def tagListB = []
        double interestScore = 0
        tagListB = getTagList(submissionB)
        interestTagA.each() { Tag A ->
            tagListB.each() { Tag B ->
                if (A == B) {
                    interestScore++
                }
            }
        }
        print("interest score on same interests from ${userA.firstName} and tag from ${userB.firstName}: ${interestScore}")
        return interestScore
    }

    // Add a score if both A and B put same tags on their submissions
    def interestScoreSameTagsOnSubmissions(Appuser userA, Appuser userB) {
        def submissionA = userA.submissions
        def submissionB = userB.submissions
        def tagListA = []
        def tagListB = []
        double interestScore = 0

        tagListA = getTagList(submissionA)
        tagListB = getTagList(submissionB)

        tagListA.each() { Tag A ->
            tagListB.each() { Tag B ->
                if (A == B) {
                    interestScore++
                }
            }
        }
        print("interest score on same tags: ${interestScore}")
        return interestScore
    }

    // Add score if user A clicked on B's profile
    def profileAccessScoreAClickOnB(Appuser userA, Appuser userB) {
        def viewA = SubmissionViewTracker.findAllByUser(userA)
        double profileAccessScore = 0
        viewA.each() { SubmissionViewTracker view ->
            if (view.profileUser?.id == userB?.id) {
                profileAccessScore += applyRecency(view.viewCount, getWeightFactor(ScoreWeightingFactors.CODE.PROFILE_ACCESS_RECENCY_FACTOR), view.lastViewDate)
            }
        }
        print("profile access score if ${userA.firstName} clicked on ${userB.firstName}: ${profileAccessScore}")
        return profileAccessScore
    }

    // Calculate total view score
    def viewScore(Appuser userA, Appuser userB) {
        double viewScore = 0
        def viewA = SubmissionViewTracker.findAllByUser(userA)
        def viewB = SubmissionViewTracker.findAllByUser(userB)
        if (viewA && viewB) {
            viewScore = applyWeight(viewScoreSameContent(userA, userB), getWeightFactor(ScoreWeightingFactors.CODE.VIEW_SCORE_SAME_CONTENT)) +
                    applyWeight(viewScoreAViewedB(userA, userB), getWeightFactor(ScoreWeightingFactors.CODE.VIEW_SCORE_A_TO_B)) +
                    applyWeight(viewScoreAViewedB(userB, userA), getWeightFactor(ScoreWeightingFactors.CODE.VIEW_SCORE_B_TO_A))
        }
        return applyWeight(viewScore, getWeightFactor(ScoreWeightingFactors.CODE.VIEW_SCORE_GENERAL))
    }

    // Calculate total following score
    def followingScore(Appuser userA, Appuser userB) {
        double followingScore = applyWeight(followingScoreBFollowingA(userA, userB), getWeightFactor(ScoreWeightingFactors.CODE.FOLLOWING_SCORE_B_FOLLOWS_A)) +
                followingScoreMutualFollowersAndFollowings(userA, userB) // note that the weighting for mutual followers and followings are already calculated within the function
        return applyWeight(followingScore, getWeightFactor(ScoreWeightingFactors.CODE.FOLLOWING_SCORE_GENERAL))
    }

    // Calculate total interest score
    def interestScore(Appuser userA, Appuser userB) {
        double interestScore = applyWeight(interestScoreSameInterest(userA, userB), getWeightFactor(ScoreWeightingFactors.CODE.INTEREST_SCORE_SAME_INTEREST)) +
                applyWeight(interestScoreSameTagsOnSubmissions(userA, userB), getWeightFactor(ScoreWeightingFactors.CODE.INTEREST_SCORE_SAME_TAGS)) +
                applyWeight(interestScoreInterestUserASubmissionTagUserB(userA, userB), getWeightFactor(ScoreWeightingFactors.CODE.INTEREST_SCORE_INTEREST_FROM_A_MATCHES_TAG_FROM_B)) +
                applyWeight(interestScoreInterestUserASubmissionTagUserB(userB, userA), getWeightFactor(ScoreWeightingFactors.CODE.INTEREST_SCORE_INTEREST_FROM_A_MATCHES_TAG_FROM_B))
        return applyWeight(interestScore, getWeightFactor(ScoreWeightingFactors.CODE.INTEREST_SCORE_GENERAL))
    }

    // Calculate total profile access score
    def profileAccessScore(Appuser userA, Appuser userB) {
        double profileAccessScore = applyWeight(profileAccessScoreAClickOnB(userA, userB), 10) +
                applyWeight(profileAccessScoreAClickOnB(userB, userA), 1)
        return applyWeight(profileAccessScore, getWeightFactor(ScoreWeightingFactors.CODE.PROFILE_ACCESS_SCORE_GENERAL))
    }

    // Calculate total final score and save it in the database
    def calculateScores(Appuser userA, Appuser userB) {
        double finalViewScore = viewScore(userA, userB)
        double finalFollowingScore = followingScore(userA, userB)
        double finalInterestScore = interestScore(userA, userB)
        double finalProfileAccessScore = profileAccessScore(userA, userB)

        println("view score between ${userA.firstName} and ${userB.firstName}: ${finalViewScore}")
        println("following score between ${userA.firstName} and ${userB.firstName}: ${finalFollowingScore}")
        println("interest score between ${userA.firstName} and ${userB.firstName}: ${finalInterestScore}")
        println("profile access score between ${userA.firstName} and ${userB.firstName}: ${finalProfileAccessScore}")

        double totalScore = finalViewScore + finalFollowingScore + finalInterestScore + finalProfileAccessScore
        println("total score between ${userA.firstName} and ${userB.firstName}: ${totalScore}")
        println(" ")
        return totalScore
    }

    // Update score for the specific user
    def updateUserListForOne(Appuser A) {
        double totalScore = 0
        Appuser userA = Appuser.findById(A.id)
        def userList = Appuser.findAllByActive(true).sort{it.id}

        if (userA.active) {
            userList.each() { Appuser B ->
                Appuser userB = Appuser.findById(B.id)

                if (userA != userB && userB.active) {
                    def matchingInfo = initialize(userA, userB)
                    UserMatching userMatching = matchingInfo.userMatching

                    if (userMatching) {
                        print(userB.id)
                        userMatching?.potentialMatch = userB
                        blacklistUser(userA, userB)
                        userMatching.save(flush: true, failOnError: true)
                    }

                }
            }
        }
    }

    // Update everyone's record on the table
    def updateUserListForAll(boolean recursiveCall, def recursiveCallStartId, Date startTime) {
        def startUserId = getWeightFactor(ScoreWeightingFactors.CODE.END_USER_ID) ?: 0
        def endUserId
        print("recursive call start id: ${recursiveCallStartId}")
        println("Starting an update with the user after ${startUserId}. ")
        def userList = Appuser.findAllByIdGreaterThanAndActive(startUserId, true).sort{ it.id } // Modify it when running the test, so it does not run for entire population
        print("user list size: ${userList.size()}")

        if((userList.size() == 0) && recursiveCall) { // If it has reached the end, it starts updating from the beginning again.
            userList = Appuser.findAllByActiveAndIdGreaterThan(true, 0).sort{ it.id }
        }
        else if (!recursiveCall) {
            startTime = new Date()
            if (userList.size() == 0) {
                userList = Appuser.findAllByActiveAndIdGreaterThan(true, 0).sort{ it.id }
                startUserId = 0
            }
        }

        Date now = new Date()
        def updatedUserCount = 0

        use(TimeCategory) { // Update the scores for 5 hours and save the last user we updated.
            for(Appuser userA in userList) {
                if (startTime + 5.hour > now) {
                    if((!recursiveCall && recursiveCallStartId < userA.id) || (recursiveCall && recursiveCallStartId >= userA.id)) {
                        print("${userA.firstName} is being updated. ")
                        updateUserListForOne(userA)
                        endUserId = userA.id
                        now = new Date()
                        updatedUserCount++
                    }
                    else {
                        print("recursive call start ID : ${recursiveCallStartId}")
                        print("user ID: ${userA.id}")
                        break
                    }
                }
                else break
            }
            if (endUserId) {
                setWeightFactor(ScoreWeightingFactors.CODE.END_USER_ID, endUserId)
                println("Ending an update with the user ${endUserId}. ")
            }
            print("updatedUserCount: ${updatedUserCount}")

            // Calls the function if it reached the end of the list and still have left time. recursiveCall = true
            if((startTime + 5.hour > now) && !recursiveCall) {
                updateUserListForAll(true, startUserId, startTime)
            }
        }
    }

    // Get the top ten users depending on the score
    def getMatchedUserList(Appuser user, def max) {
        def matchedUserList = []
        if (user.active) {
            def c = UserMatching.createCriteria()
            def matchList = c.list(max: max, offset: 0) {
                eq("user", user)
                ne("potentialMatch", user)
                order("score", "desc")
                eq("blacklisted", false)
                eq("following", false)
            }
            if (matchList) {
                matchList.each() { UserMatching matchedUser ->
                        matchedUserList.add(matchedUser.potentialMatch)
                }

                if (matchedUserList.size() < max) {
                    defaultUserList(user).each() { def defaultUser ->
                        if (matchedUserList.size() < max && !matchedUserList.contains(defaultUser)) {
                            matchedUserList.add(defaultUser)
                        }
                    }
                }
            }
            else {
                matchedUserList = defaultUserList(user).take(max)
            }
        }
        else {
            matchedUserList = defaultUserList(user).take(max)
        }

        def finalList = []
        matchedUserList.each { Appuser matchedUser ->
            def match = [:]
            match.user = matchedUser
            match.settings = ProfileSetting.findByAppuser(matchedUser)
            finalList.add(match)
        }
        return finalList
    }

    // This function returns the default list for the users who don't have any score with other people
    // At this point, the default list is determined by the number of followers
    def defaultUserList(Appuser user) {
        Sql sql = Sql.newInstance(dataSource)
        def defaultList = sql.rows("select appuser_id, count(appuser_following_id) as follower_count from appuser_appuser where appuser_following_id notnull group by appuser_id order by follower_count desc")
        sql.close()
        def matchList = []
        def followList = getFollowingList(user)

        defaultList.each() { def defaultUserId ->
            Appuser defaultUser = Appuser.findById(defaultUserId.appuser_id)
            if (user != defaultUser && !followList.contains(defaultUser)) {
                UserMatching match = UserMatching.findByUserAndPotentialMatch(user, defaultUser)
                if (match && !match.blacklisted) {
                    matchList.add(defaultUser)
                }
                else if (!match) {
                    matchList.add(defaultUser)
                }
            }
        }
        return matchList
    }

    // Make the user blacklisted if he/she fits in certain criteria (FOLLOW/UNFOLLOW TIMESTAMP)
    def blacklistUser(Appuser userA, Appuser userB) {
        UserMatching userMatching = UserMatching.findByUserAndPotentialMatch(userA, userB)
        if (userMatching?.unfollowTime && !userMatching?.following) {
            use(TimeCategory) {
                if (userMatching.followTime < userMatching.unfollowTime - 1.hour) { // if user followed and unfollowed the same person in more than an hour
                    userMatching.blacklisted = true
                    userMatching.blacklistTime = new Date()
                }
                else {
                    userMatching.followTime = null
                    userMatching.unfollowTime = null
                }
            }
        }
        else if (userMatching?.following) {
            userMatching.blacklisted = false
            userMatching.blacklistTime = null
        }

        userMatching.save(flush: true, failOnError: true)
        if (userMatching?.blacklisted) {
            unblockUserDependingOnRecency(userMatching)
        }
    }

    // Unblock userB if it has been 100 days since the person has been blacklisted
    def unblockUserDependingOnRecency(UserMatching userMatching) {
        Date now = new Date()
        use(TimeCategory) {
            def blacklistedDays = (now - userMatching.blacklistTime).days
            if (userMatching.blacklisted && (blacklistedDays > 100)) {
                userMatching.blacklisted = false
                userMatching.blacklistTime = null
                userMatching.save(flush: true, failOnError: true)
            }
        }
    }
}
