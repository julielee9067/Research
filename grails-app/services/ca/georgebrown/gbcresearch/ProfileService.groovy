package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.transaction.Transactional


@Transactional
class ProfileService {

    def settingService
    def userMatchService

    // This function creates a new profile for the app user.
    def addProfile(appuser) {
        if (!Profile.findByAppuser(appuser)) {
            Profile newUser = new Profile(appuser: appuser)
            newUser.save(flush:true, failOnError: true)
        }
    }

    // This function adds a current app user as a following to the profile user.
    // Or, if the user is already following the profile user, it removes the appuser from the list.
    def addFollowing (appuser, profileUser) {
        UserMatching userMatching = userMatchService.initialize(appuser, profileUser).userMatching
        if (appuser in profileUser.followedBy) {
            appuser.removeFromFollowing(profileUser)
            profileUser.removeFromFollowedBy(appuser)
            userMatching?.unfollowTime = new Date()
            userMatching?.following = false
            appuser.save(flush: true, failOnError: true)
            profileUser.save(flush: true, failOnError: true)
            userMatching.save(flush: true, failOnError: true)
        }
        else {
            appuser.addToFollowing(profileUser)
            profileUser.addToFollowedBy(appuser)
            userMatching?.followTime = new Date()
            userMatching?.following = true
            appuser.save(flush: true, failOnError: true)
            profileUser.save(flush: true, failOnError: true)
            userMatching.save(flush: true, failOnError: true)
        }
    }

    // This function updates the current user's profile setting.
    def updateSettings(appuser, params) {
        def showEmail = params?.showEmail ?: false
        def showInterests = params?.showInterests ?: false
        def showRecentlyViewed = params?.showRecentlyViewed ?: false
        def notifyInterestedPost = params?.notifyInterestedPost ?: false
        def notifyFollowedUserPost = params?.notifyFollowedUserPost ?: false
        def notifyDailyDigest = params?.notifyDailyDigest ?: false
        def scrollSetting = params?.scrollSetting ?: ProfileSetting.SCROLL_SETTING.SCROLL

        if (showEmail) showEmail = true
        if (showInterests) showInterests = true
        if (showRecentlyViewed) showRecentlyViewed = true
        if (notifyInterestedPost) notifyInterestedPost = true
        if (notifyFollowedUserPost) notifyFollowedUserPost = true
        if (notifyDailyDigest) notifyDailyDigest = true

        def newBiography = params?.newBiography ?: Profile.findByAppuser(appuser).biography ?: ' '
        ProfileSetting profileSetting = ProfileSetting.findByAppuser(appuser)
        profileSetting.showEmail = showEmail
        profileSetting.showInterests = showInterests
        profileSetting.showRecentlyViewed = showRecentlyViewed
        profileSetting.notifyInterestedPost=notifyInterestedPost
        profileSetting.notifyFollowedUserPost=notifyFollowedUserPost
        profileSetting.notifyDailyDigest=notifyDailyDigest
        profileSetting.scrollSetting = scrollSetting
        profileSetting.save(flush:true)
        Profile profile = Profile.findByAppuser(appuser)
        profile.biography = newBiography
        profile.save(flush:true, failOnError: true)
        return
    }

    // This function creates and sets the profile setting to the default if it doesn't exist on the database.
    def defaultSettings(appuser) {
        if (!ProfileSetting.findByAppuser(appuser)) {
            ProfileSetting newSettings = new ProfileSetting(appuser:appuser)
            newSettings.save(flush:true)
        }
    }

    // This function gets all the submissions by the current user.
    def getSubmissions(Appuser owner, def publicProfile) {
        def c = Submission.createCriteria()
        def submissionList = c.list() {
            order("submissionDate", "desc")
            eq("owner",owner)

            if (publicProfile) {
                eq("published", true)
            }
        }
        def newList = submissionList.findAll{ it.submitStatus != Submission.SUBMIT_STATUS.INITIATED}
        return newList
    }

    // This function gets the published submissions by the user.
    def getPublicSubmissions(Appuser owner) {
        def c = Submission.createCriteria()
        def submissionList = c.list() {
            order("submissionDate", "desc")
            eq("owner",owner)
            eq("published",true)
        }
        return submissionList
    }

    // This function gets the submissions by the Banner User.
    def getBannerUserSubmissions(Appuser owner, Boolean publicProfile) {
        def c = Submission.createCriteria()
        def submissionList = c.list() {
            or {
                principleInvestigators {
                    eq("appuser", owner)
                }
                coInvestigators {
                    eq("appuser", owner)
                }
            }

            if (publicProfile) {
                eq("submitStatus", Submission.SUBMIT_STATUS.PUBLISHED)
            }
        }
        def researchSubmission = getSubmissions(owner, publicProfile)
        submissionList.addAll(researchSubmission)
        submissionList.sort{a,b -> b.submissionDate <=> a.submissionDate}.unique()
        return submissionList
    }

    // This function gets the list of the recently viewed submissions.
    def getViewedSubmissions(Appuser user) {
        def c = SubmissionViewTracker.createCriteria()
        def viewedList = c.list(max: 10, offset: 0) {
            eq("user", user)
            order("lastViewDate", "desc")
            isNotNull("submission")
        }

        def finalList = []

        viewedList.each() { def view ->
            Submission submission = Submission.findById(view?.submission?.id)
            if (submission.published) {
                finalList.add(Submission.findById(view?.submission?.id))

            }
        }
        return finalList
    }

    // This function gets the settings for the pagination.
    def getScrollSettingsList() {
        def defaultSetting = settingService.get(Setting.CODE.SUBMISSION_SCROLL_SETTING)
        def list = ProfileSetting.SCROLL_SETTING_SELECT_LIST
        list[ProfileSetting.SCROLL_SETTING.DEFAULT] = "Default (${list[defaultSetting]})"
        return list
    }
}
