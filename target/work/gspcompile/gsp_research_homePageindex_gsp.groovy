import ca.georgebrown.gbcresearch.SubmissionType
import  ca.georgebrown.gbcresearch.Submission
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_homePageindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/homePage/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',10,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',11,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',11,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',16,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
expressionOut.print(searchTextPerson)
printHtmlPart(7)
expressionOut.print(profile.findByAppuser(appuser).newUser)
printHtmlPart(8)
invokeTag('render','g',173,['template':("/layouts/navigation")],-1)
printHtmlPart(9)
createClosureForHtmlPart(10, 2)
invokeTag('link','g',184,['class':("btn btn-sm btn-outline-primary mt-2"),'controller':("homePage"),'action':("index"),'params':([typeName: 'All'])],2)
printHtmlPart(11)
for( type in (SubmissionType.getAll()) ) {
printHtmlPart(12)
createTagBody(3, {->
expressionOut.print(type.name)
})
invokeTag('link','g',186,['class':("btn btn-sm btn-outline-primary mt-2"),'controller':("homePage"),'action':("index"),'params':([typeName: type.name])],3)
printHtmlPart(11)
}
printHtmlPart(13)
invokeTag('render','g',195,['template':("peopleSearch"),'model':([searchPeople: similarPeople, userSearch: searchTextPerson, total: similarPeople?.size(), div_id: 'recommendedPeopleContent', action: 'none', controller: 'homePage'])],-1)
printHtmlPart(14)
if(true && (searchText)) {
printHtmlPart(15)
expressionOut.print(searchText)
printHtmlPart(16)
expressionOut.print(searchCount)
printHtmlPart(17)
}
else if(true && (tagInput)) {
printHtmlPart(18)
expressionOut.print(tagInput)
printHtmlPart(16)
expressionOut.print(searchCount)
printHtmlPart(17)
}
else {
printHtmlPart(19)
if(true && (0)) {
printHtmlPart(20)
}
printHtmlPart(21)
}
printHtmlPart(22)
invokeTag('render','g',234,['template':("submissions"),'model':([list: resultList, div_id: 'submissionView', action: 'listSubmissionsSearch',
                                                                     searchCount: searchCount, searchInput: searchText, tagInput: tagInput,
                                                                     appuser: appuser, userProfile: userProfile, source: 'homePage', typeName: typeName,
                                                                     defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting])],-1)
printHtmlPart(23)
if(true && (!publicView)) {
printHtmlPart(24)
invokeTag('render','g',241,['template':("submissions"),'model':([list: followingUsersSubmissions, div_id: 'followingSubmissionsView', action: 'listFollowingsSubmissionsSearch',
                                                                         searchCount: followingUsersSubmissionsCount, searchInput: searchText, tagInput: tagInput,
                                                                         appuser: appuser, userProfile: userProfile, source: 'homePage',
                                                                         defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting])],-1)
printHtmlPart(25)
invokeTag('render','g',248,['template':("submissions"),'model':([list: interestedSubmissions, div_id: 'interestedSubmissionsView', action: 'listInterestedSubmissionsSearch',
                                                                         searchCount: interestedSubmissionsCount, searchInput: searchText, tagInput: tagInput,
                                                                         appuser: appuser, userProfile: userProfile, source: 'homePage',
                                                                         defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting])],-1)
printHtmlPart(26)
}
printHtmlPart(27)
})
invokeTag('captureBody','sitemesh',253,[:],1)
printHtmlPart(28)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574959675000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
