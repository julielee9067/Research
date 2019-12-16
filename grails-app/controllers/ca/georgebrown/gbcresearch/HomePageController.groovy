package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.security.Appuser
import grails.plugin.springsecurity.annotation.Secured


@Secured('IS_AUTHENTICATED_FULLY')
class HomePageController {

    def springSecurityService
    def submissionTypeService
    def searchService
    def appuserTagService
    def settingService
    def userMatchService

    // This function directs the user to the submission view.
    def linkToPage() {
        Appuser appuser = springSecurityService.currentUser
        Submission submission = Submission.findById(params.id)

        if (params.type.replaceAll(' ', '').trim().toLowerCase() != 'submission' && Submission.get(params.id).owner != appuser) {
            userMatchService.viewSubmission(appuser, submission)
        }

        redirect(controller: "submissions", action: "view", params: [id: params.id, source: params?.source, searchInput: params?.searchInput, tagInput: params?.tagInput, profile_id: params?.profile_id])
    }

    // This function adds interest tag to the current user.
    def addInterests() {
        Appuser appuser = springSecurityService.currentUser
        appuserTagService.addInterest(appuser, params)
        redirect(controller: "profile", action: "index")
    }

    // This function returns a list of submissions depending on whether there are search input/tag input or not.
    @org.springframework.security.access.annotation.Secured('permitAll')
    def listSubmissionsSearch() {
        def searchCount
        def resultList
        Appuser appuser = springSecurityService.currentUser
        def defaultPaginationSetting = settingService.get(Setting.CODE.SUBMISSION_SCROLL_SETTING)
        def userPaginationSetting = null
        if(appuser) userPaginationSetting = appuser.setting.scrollSetting
        def typeName = params.typeName ?: null

        if (params.searchInput == "null") params.searchInput = null

        if (params?.tagInput && params?.tagInput != "null") {
            resultList = searchService.tagResultList(params)
            searchCount = resultList.size()
        }
        else {
            resultList = searchService.publishedList(params)
            searchCount = resultList.totalCount
        }

        if(typeName && typeName != "null" && typeName != 'All') {
            def result = searchService.publishedListByType(typeName, params)
            resultList = result.resultList
            searchCount = result.searchCount
        }

        render(template: "submissions", model: [searchText: params.searchInput ?: null, tagInput: params.tagInput ?: null,
                                                source: params.source ?: null, list: resultList, searchCount: searchCount,
                                                div_id: 'submissionView', action: 'listSubmissionsSearch', appuser: appuser,
                                                offset: params.offset, max: params.max, userPaginationSetting: userPaginationSetting,
                                                defaultPaginationSetting: defaultPaginationSetting, typeName: typeName])
    }

    def listFollowingsSubmissionsSearch() {
        Appuser appuser = springSecurityService.currentUser
        def defaultPaginationSetting = settingService.get(Setting.CODE.SUBMISSION_SCROLL_SETTING)
        def userPaginationSetting = appuser.setting.scrollSetting
        def followingUsersSubmissions = searchService.followingUsersSubmissionList(appuser, params)
        def followingUsersSubmissionsCount = followingUsersSubmissions.totalCount

        render(template: "submissions", model: [source: params.source ?: null, list: followingUsersSubmissions, searchCount: followingUsersSubmissionsCount,
                                                div_id: 'followingSubmissionsView', action: 'listFollowingsSubmissionsSearch', appuser: appuser,
                                                offset: params.offset, max: params.max, userPaginationSetting: userPaginationSetting,
                                                defaultPaginationSetting: defaultPaginationSetting])
    }

    def listInterestedSubmissionsSearch() {
        Appuser appuser = springSecurityService.currentUser
        def defaultPaginationSetting = settingService.get(Setting.CODE.SUBMISSION_SCROLL_SETTING)
        def userPaginationSetting = appuser.setting.scrollSetting
        def interestedSubmissionsResult = searchService.interestedSubmissionList(appuser, params)
        def interestedSubmissions = interestedSubmissionsResult.interestedSubmissions
        def interestedSubmissionsCount = interestedSubmissionsResult.interestedSubmissionsCount

        render(template: "submissions", model: [source: params.source ?: null, list: interestedSubmissions, searchCount: interestedSubmissionsCount,
                                                div_id: 'interestedSubmissionsView', action: 'listInterestedSubmissionsSearch', appuser: appuser,
                                                offset: params.offset, max: params.max, userPaginationSetting: userPaginationSetting,
                                                defaultPaginationSetting: defaultPaginationSetting])
    }

    // This function returns a list of people depending on the search text. (currently not in use, but maybe used later if we want to put user search box back.)
    def listPeopleSearch() {
        def searchTextPerson = params.userSearch
        def searchPeople = searchService.peopleSearch(searchTextPerson, params)
        render(template: "peopleSearch", model: [searchPeople: searchPeople.result, userSearch: searchTextPerson,
                                                 total: searchPeople.count, div_id: 'searchPeopleContent',
                                                 action: 'listPeopleSearch', controller: 'homePage'])
    }

    // This function returns the necessary variables to the homePage/index website. (dashboard)
    @org.springframework.security.access.annotation.Secured('permitAll')
    def index() {
        Appuser appuser = springSecurityService.currentUser

        if (!appuser && settingService.get(Setting.CODE.PUBLIC_ACCESS)) redirect controller: 'homePage', action: 'publicView'
        else if (!appuser && !settingService.get(Setting.CODE.PUBLIC_ACCESS)) redirect(controller: 'login', action: 'auth')

        def userProfile = Profile.findByAppuser(appuser)
        def types = submissionTypeService.get()
        def searchPeople
        def searchTextPerson = params.userSearch

        if (params.userSearch) searchPeople = searchService.peopleSearch(searchTextPerson, params)

        def tagList = params?.tags ?: null
        def searchText = params?.searchInput ?: null
        def searchTypes = params?.searchTypes ?: null
        def tagInput = params?.tagInput ?: null
        def tagSubmissionList
        def typeName = params?.typeName ?: null
        def lastSearch
        def recommended
        def maxResultForRecommendedUser = 10
        def defaultPaginationSetting = settingService.get(Setting.CODE.SUBMISSION_SCROLL_SETTING)
        def userPaginationSetting = null
        if(appuser) userPaginationSetting = appuser.setting.scrollSetting

        def resultList = searchService.publishedList(params)
        def searchCount = resultList.totalCount ?: 0
        def followingUsersSubmissions = searchService.followingUsersSubmissionList(appuser, params)
        def followingUsersSubmissionsCount = followingUsersSubmissions.totalCount
        def interestedSubmissionsResult = searchService.interestedSubmissionList(appuser, params)
        def interestedSubmissions = interestedSubmissionsResult.interestedSubmissions
        def interestedSubmissionsCount = interestedSubmissionsResult.interestedSubmissionsCount

        if (tagInput && tagInput != "null") {
            tagSubmissionList = searchService.tagResultList(params)
            if (tagSubmissionList) {
                resultList = tagSubmissionList
                searchCount = tagSubmissionList.size()
            }
            else {
                resultList = []
                searchCount = 0
            }
        }

        if(typeName && typeName != 'All') {
            def result = searchService.publishedListByType(typeName, params)
            resultList = result.resultList
            searchCount = result.searchCount
        }

        if (appuser) {
            def similarPeople = userMatchService.getMatchedUserList(appuser, maxResultForRecommendedUser)
            def settings = ProfileSetting.findByAppuser(appuser)
            [appuser: appuser, types: types, typesList: searchTypes, recommended: recommended, profile: Profile, userProfile: userProfile,
             countRecommended: recommended, searchText: searchText, tagList: tagList, similarPeople: similarPeople, tagInput: tagInput,
             settings: settings, resultList: resultList, searchCount: searchCount, source: 'homePage', defaultPaginationSetting: defaultPaginationSetting,
             userPaginationSetting: userPaginationSetting, lastSearch: lastSearch, searchPeople: searchPeople, searchTextPerson: searchTextPerson,
             followingUsersSubmissions: followingUsersSubmissions, followingUsersSubmissionsCount: followingUsersSubmissionsCount,
             interestedSubmissions: interestedSubmissions, interestedSubmissionsCount: interestedSubmissionsCount, typeName: typeName]
        }
        else {
            [appuser: appuser, types: types, typesList: searchTypes, recommended: recommended, countRecommended: recommended,
             searchText: searchText, tagList: tagList, tagInput: tagInput, resultList: resultList, searchCount: searchCount,
             source: 'homePage', defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting,
             lastSearch: lastSearch, searchPeople: searchPeople, searchTextPerson: searchTextPerson, followingUsersSubmissions: followingUsersSubmissions,
             followingUsersSubmissionsCount: followingUsersSubmissionsCount, interestedSubmissions: interestedSubmissions,
             interestedSubmissionsCount: interestedSubmissionsCount, typeName: typeName]
        }
    }

    // This function returns the published list for the public view (when the user is not logged in.)
    @org.springframework.security.access.annotation.Secured('permitAll')
    def publicView() {
        def resultList
        resultList = searchService.publishedList(params)
        return [resultList: resultList, searchCount: resultList.totalCount]
    }
    }
