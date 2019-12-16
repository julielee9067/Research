package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.plugin.springsecurity.annotation.Secured


@Secured('IS_AUTHENTICATED_FULLY')
class ProfileController {

    def springSecurityService
    def profileService
    def appuserTagService
    def fileUploadService
    def userMatchService

    def download() {
        return fileUploadService.file(response, params)
    }

    def updateProfile() {
        def appuser = springSecurityService.currentUser

        if (Profile.findByAppuser(appuser)?.newUser) {
            def profile = Profile.findByAppuser(appuser)
            profile.newUser = false
            profile.save(flush:true)
        }

        profileService.updateSettings(appuser, params)
        appuserTagService.addInterest(appuser, params)

        def msg=''
        def anchor = "fileAnchor"
        if (params.containsKey("send")) {
            UploadedFile uploadedFile = fileUploadService.uploadFile(params, request, appuser)
            if (!uploadedFile) {
                msg = "File failed to upload"
                [msg: msg]
            }
            else msg = "File successfully uploaded"
        }
        def contr = getParams().returnController
        def actn = getParams().returnAction
        def srId = getParams().sourceId
        redirect (controller: contr , action: actn)
    }

    def addFollowing() {
        Appuser appuser = springSecurityService.currentUser
        profileService.addFollowing(appuser, Appuser.get(params.profile_id))
        userMatchService.blacklistUser(appuser, Appuser.get(params.profile_id))
    }

    def index() {
        Appuser appuser = springSecurityService.currentUser
        Appuser profileUser
        def publicProfile = true
        def following = false

        if (!params.profile_id) {
            publicProfile = false
            profileUser = appuser
        }
        else {
            if (params.profile_id && appuser.id == Long.valueOf(params.profile_id)) publicProfile = false
            profileUser = Appuser.findById(params.profile_id)
            userMatchService.viewProfile(appuser, profileUser)
        }

        def profile = Profile.findByAppuser(profileUser)
        if (profile.biography == " ") profile.biography = null
        profile.save(flush: true, failOnError: true)

        def submissions

        if (profileUser.source == Appuser.SOURCE.BANNER) submissions = profileService.getBannerUserSubmissions(profileUser, publicProfile)
        else submissions = profileService.getSubmissions(profileUser, publicProfile)

        def settings = ProfileSetting.findByAppuser(profileUser)
        def recentlyViewed = null

        if (settings?.showRecentlyViewed || !publicProfile) recentlyViewed = profileService.getViewedSubmissions(profileUser)

        def image = UploadedFile.findByUploadedByAndSource(profileUser, 'Profile')

        if (profileUser in appuser.following) following = true

        [profile: profile, submissions: submissions, recentlyViewed: recentlyViewed, profile_id: params?.profile_id,
         settings: settings, publicProfile: publicProfile, profileUser: profileUser, source: 'profile',
         appuser:appuser, following: following, image:image, scrollSettingsList: profileService.getScrollSettingsList()]
    }

    def following() {
        def offset = params?.offset?.toInteger() ?: 0
        def max = params?.max?.toInteger() ?: 5
        def user = Appuser.get(params.profile_id?.toLong())
        def followingList = userMatchService.getFollowingList(user)
        def total = followingList.size()
        followingList = followingList.sort{it.id}
        followingList = followingList.drop(offset)

        if (max < total) followingList = followingList.take(max)

        def followingUsers = []

        followingList?.each { Appuser following ->
            def settings = ProfileSetting.findByAppuser(following)
            def followingMap = [:]
            followingMap.settings = settings
            followingMap.user = following
            followingUsers.add(followingMap)
        }
        render template:  'following', model: [users: followingUsers, total: total, action: 'following', div_id: 'following', profile_id: params.profile_id]
    }

    def follower() {
        def offset = params?.offset?.toInteger() ?: 0
        def max = params?.max?.toInteger() ?: 5
        def user = Appuser.get(params.profile_id?.toLong())
        def followerList = userMatchService.getFollowerList(user)
        def total = followerList.size()
        followerList = followerList.sort{it.id}
        followerList = followerList.drop(offset)

        if (max < total) followerList = followerList.take(5)

        def followedUsers = []

        followerList?.each { Appuser follower->
            def settings = ProfileSetting.findByAppuser(follower)
            def followedMap = [:]
            followedMap.settings = settings
            followedMap.user = follower
            followedUsers.add(followedMap)
        }
        render template: 'following', model: [users: followedUsers, total: total, action: 'follower', div_id: 'follower', profile_id: params.profile_id]
    }


    @org.springframework.security.access.annotation.Secured('permitAll')
    def displayImage() {
        def user = Appuser.get(params.user)
        def image2 = UploadedFile.findByUploadedByAndSource(user, 'Profile')

        if (image2) {
            File SIGNATURE_IMAGES_DIR = new File(grailsApplication.config.file.profilePhoto.directory.toString())
            def picFile = image2.newFilename
            if (!picFile) picFile = "default-profile.png"

            File image = new File(SIGNATURE_IMAGES_DIR.getAbsolutePath() + File.separator + picFile)

            if (!image.exists()) response.status = 404
            else {
                response.setContentType("application/jpg")
                OutputStream out = response.getOutputStream()
                out.write(image.bytes)
                out.close()
            }
        }
    }

    def ajaxSetFilter() {
        Profile profile = Profile.get(params.id)
        if (!profile) return
        profile[params.profileSetting] = (params.setting == 'true')
        profile.save(flush: true)
        render(contentType: 'text/json') {
                delegate.result = params
        }
    }

    def ajaxGetFollowerCount() {
        Appuser profileUser
        if (params?.id) {
            profileUser = Appuser.get(Long.valueOf(params?.id))
            println profileUser.dump()
        }

        render(contentType: 'text/json') {
            if (profileUser) delegate.followers = profileUser.followedBy.size()
        }
    }
}
