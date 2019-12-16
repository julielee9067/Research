import ca.georgebrown.gbcresearch.Submission
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_profileindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/profile/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createTagBody(3, {->
printHtmlPart(2)
expressionOut.print(appuser.displayName)
printHtmlPart(3)
})
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',47,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
expressionOut.print(remoteFunction(controller: 'profile', action: 'addFollowing', params: '\'profile_id=\' + profile_id '))
printHtmlPart(7)
expressionOut.print(remoteFunction(controller: 'profile', action: 'ajaxGetFollowerCount', params: '\'id=\' + escape(profile_id) ', onSuccess: 'changeFollowersText(data, follow)'))
printHtmlPart(8)
expressionOut.print(profile.showPublished)
printHtmlPart(9)
expressionOut.print(profile.showCreated)
printHtmlPart(9)
expressionOut.print(profile.showSubmitted)
printHtmlPart(9)
expressionOut.print(profile.showRejected)
printHtmlPart(10)
expressionOut.print(remoteFunction(controller: 'profile', action: 'ajaxSetFilter', params: "'id='+escape(${profile.id})+'&profileSetting='+filterId+'&setting='+status", onSuccess: ''))
printHtmlPart(11)
if(true && (publicProfile)) {
printHtmlPart(12)
invokeTag('render','g',259,['template':("/layouts/navigation"),'model':("")],-1)
printHtmlPart(1)
}
else {
printHtmlPart(12)
invokeTag('render','g',262,['template':("/layouts/navigation"),'model':([which: 'profile'])],-1)
printHtmlPart(1)
}
printHtmlPart(13)
if(true && (image)) {
printHtmlPart(14)
expressionOut.print(createLink(controller: "profile", action: "displayImage", params: [user: profileUser.id]))
printHtmlPart(15)
expressionOut.print(assetPath(src: "default-profile.png"))
printHtmlPart(16)
}
else {
printHtmlPart(17)
invokeTag('image','asset',274,['src':("default-profile.png"),'id':("profilePicture"),'title':("profile-picture")],-1)
printHtmlPart(18)
}
printHtmlPart(19)
expressionOut.print(profileUser?.displayName)
printHtmlPart(20)
if(true && (!publicProfile || settings?.showEmail)) {
printHtmlPart(21)
expressionOut.print(profileUser?.email)
printHtmlPart(22)
expressionOut.print(profileUser?.email)
printHtmlPart(23)
}
printHtmlPart(24)
if(true && (publicProfile)) {
printHtmlPart(25)
if(true && (following)) {
printHtmlPart(26)
}
else {
printHtmlPart(27)
}
printHtmlPart(28)
if(true && (following)) {
printHtmlPart(29)
}
else {
printHtmlPart(30)
}
printHtmlPart(31)
expressionOut.print(profileUser.id)
printHtmlPart(9)
expressionOut.print(appuser.id)
printHtmlPart(32)
expressionOut.print(appuser.firstName)
printHtmlPart(33)
expressionOut.print(appuser.lastName)
printHtmlPart(34)
}
printHtmlPart(35)
if(true && (profileUser.id == appuser.id)) {
printHtmlPart(36)
}
printHtmlPart(37)
expressionOut.print(profile.biography ?: 'There is currently no biography for this user.')
printHtmlPart(38)
if(true && (!publicProfile)) {
printHtmlPart(39)
invokeTag('render','g',334,['template':("/profile/settingWindow"),'model':([returnAction: 'index', returnController: 'profile'])],-1)
printHtmlPart(12)
}
printHtmlPart(40)
if(true && (appuser == profileUser)) {
printHtmlPart(41)
}
else {
printHtmlPart(42)
expressionOut.print(profileUser.firstName)
printHtmlPart(43)
}
printHtmlPart(44)
if(true && (!publicProfile || settings?.showRecentlyViewed)) {
printHtmlPart(45)
if(true && (appuser == profileUser)) {
printHtmlPart(46)
}
else {
printHtmlPart(47)
expressionOut.print(profileUser.firstName)
printHtmlPart(48)
}
printHtmlPart(17)
}
printHtmlPart(49)
if(true && (submissions)) {
printHtmlPart(45)
if(true && (!publicProfile)) {
printHtmlPart(50)
createClosureForHtmlPart(51, 4)
invokeTag('checkBox','g',371,['name':("showPublished"),'value':(profile.showPublished),'onchange':("changeFilter(this);")],4)
printHtmlPart(52)
expressionOut.print(ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.PUBLISHED)
printHtmlPart(53)
createClosureForHtmlPart(51, 4)
invokeTag('checkBox','g',382,['name':("showSubmitted"),'value':(profile.showSubmitted),'onchange':("changeFilter(this);")],4)
printHtmlPart(54)
expressionOut.print(ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.SUBMITTED)
printHtmlPart(55)
createClosureForHtmlPart(51, 4)
invokeTag('checkBox','g',393,['name':("showRejected"),'value':(profile.showRejected),'onchange':("changeFilter(this);")],4)
printHtmlPart(56)
expressionOut.print(ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.RETURNED)
printHtmlPart(57)
createClosureForHtmlPart(51, 4)
invokeTag('checkBox','g',402,['name':("showCreated"),'value':(profile.showCreated),'onchange':("changeFilter(this);")],4)
printHtmlPart(52)
expressionOut.print(ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.IN_EDIT)
printHtmlPart(58)
}
printHtmlPart(59)
for( submission in (submissions) ) {
printHtmlPart(60)
expressionOut.print(ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.find {
                                        it.value == submission.submitStatus
                                    }.key)
printHtmlPart(61)
if(true && (!publicProfile && submission?.submitStatus)) {
printHtmlPart(62)
expressionOut.print(Submission.SUBMIT_STATUS.find {
                                                                it.value ==  submission?.submitStatus
                                                            }.key)
printHtmlPart(63)
expressionOut.print(submission?.submitStatus)
printHtmlPart(64)
}
printHtmlPart(65)
if(true && (!publicProfile && (submission?.submitStatus != Submission.SUBMIT_STATUS.PUBLISHED ||
                                                                ca.georgebrown.gbcresearch.security.Role.findByAuthority('ROLE_PUBLISHER') in appuser.authorities))) {
printHtmlPart(66)
createClosureForHtmlPart(67, 5)
invokeTag('link','g',429,['controller':("submissions"),'action':("edit"),'params':([source: source, profile_id: profile_id]),'id':(submission.id),'class':("pull-right btn btn-light"),'style':("text-decoration: none;")],5)
printHtmlPart(68)
}
printHtmlPart(69)
invokeTag('formatDate','g',434,['format':("MMM dd, yyyy"),'date':(submission?.submissionDate)],-1)
printHtmlPart(70)
expressionOut.print(submission?.type?.name)
printHtmlPart(71)
createTagBody(4, {->
expressionOut.print(submission?.title)
})
invokeTag('link','g',441,['params':(['type': 'tbd', 'id': submission?.id, 'source': source, 'profile_id': profile_id]),'controller':("homePage"),'action':("linkToPage")],4)
printHtmlPart(72)
expressionOut.print(submission?.description)
printHtmlPart(73)
}
printHtmlPart(17)
}
else {
printHtmlPart(74)
}
printHtmlPart(75)
if(true && (recentlyViewed)) {
printHtmlPart(45)
for( submission in (recentlyViewed) ) {
printHtmlPart(76)
expressionOut.print(submission?.type?.name)
printHtmlPart(77)
if(true && (submission?.submitter?.username == 'admin')) {
printHtmlPart(66)
if(true && (submission?.principleInvestigators?.size())) {
printHtmlPart(78)
expressionOut.print(submission.principleInvestigators.first().displayName)
printHtmlPart(66)
}
printHtmlPart(68)
}
else {
printHtmlPart(66)
createTagBody(5, {->
printHtmlPart(79)
expressionOut.print(submission?.submitter?.firstName)
printHtmlPart(79)
expressionOut.print(submission?.submitter?.lastName)
printHtmlPart(79)
})
invokeTag('link','g',475,['controller':("profile"),'params':([profile_id: submission?.submitter?.id, source: 'profile']),'action':("index")],5)
printHtmlPart(68)
}
printHtmlPart(80)
expressionOut.print(createLink(controller: "profile", action: "displayImage", params:[user:submission?.submitter?.id]))
printHtmlPart(81)
expressionOut.print(assetPath(src: "default-profile.png"))
printHtmlPart(82)
createTagBody(4, {->
expressionOut.print(submission?.title)
})
invokeTag('link','g',484,['params':(['type': 'tbd', 'id': submission?.id, 'source': source]),'controller':("homePage"),'action':("linkToPage")],4)
printHtmlPart(83)
invokeTag('formatDate','g',487,['format':("MMM dd, yyyy"),'date':(submission?.publicationDate)],-1)
printHtmlPart(84)
expressionOut.print(submission?.description)
printHtmlPart(73)
}
printHtmlPart(17)
}
else {
printHtmlPart(85)
}
printHtmlPart(86)
expressionOut.print(profileUser?.following?.size())
printHtmlPart(87)
invokeTag('include','g',514,['action':("following"),'controller':("profile"),'params':([profile_id: profileUser?.id, source: "profile"])],-1)
printHtmlPart(88)
expressionOut.print(profileUser?.followedBy?.size())
printHtmlPart(89)
invokeTag('include','g',523,['action':("follower"),'controller':("profile"),'params':([profile_id: profileUser?.id, source: "profile"])],-1)
printHtmlPart(90)
})
invokeTag('captureBody','sitemesh',531,[:],1)
printHtmlPart(91)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272586000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
