import ca.georgebrown.gbcresearch.ProfileSetting
import  ca.georgebrown.gbcresearch.Submission
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_homePage_submissions_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/homePage/_submissions.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(offset ?: 0)
printHtmlPart(1)
expressionOut.print(searchCount)
printHtmlPart(1)
expressionOut.print(max ?: 10)
printHtmlPart(2)
if(true && (appuser?.setting?.scrollSetting == ProfileSetting.SCROLL_SETTING.PAGING)) {
printHtmlPart(3)
}
printHtmlPart(4)
if(true && (list.size() == 0 && !params.searchInput && !params.tagInput)) {
printHtmlPart(5)
}
printHtmlPart(6)
for( submission in (list) ) {
printHtmlPart(7)
expressionOut.print(submission?.type?.name)
printHtmlPart(8)
if(true && (submission?.submitter?.username == 'admin')) {
printHtmlPart(9)
if(true && (submission?.principleInvestigators?.size())) {
printHtmlPart(10)
expressionOut.print(submission.principleInvestigators.first().displayName)
printHtmlPart(9)
}
printHtmlPart(11)
}
else {
printHtmlPart(9)
createTagBody(3, {->
printHtmlPart(12)
expressionOut.print(submission?.submitter?.firstName + ' ' + submission?.submitter?.lastName)
printHtmlPart(12)
})
invokeTag('link','g',51,['controller':("profile"),'params':([profile_id: submission?.submitter?.id, source: 'profile']),'action':("index")],3)
printHtmlPart(11)
}
printHtmlPart(13)
expressionOut.print(createLink(controller: "profile", action: "displayImage", params:[user:submission?.submitter?.id]))
printHtmlPart(14)
expressionOut.print(assetPath(src: "default-profile.png"))
printHtmlPart(15)
createTagBody(2, {->
expressionOut.print(submission.title)
})
invokeTag('link','g',60,['params':(['type': submission.source, 'id': submission.id, 'source': source, searchInput: searchText, tagInput: tagInput]),'controller':("HomePage"),'action':("linkToPage")],2)
printHtmlPart(16)
invokeTag('formatDate','g',63,['format':("MMM dd, yyyy"),'date':(submission?.publicationDate)],-1)
printHtmlPart(17)
expressionOut.print(submission?.description)
printHtmlPart(18)
}
printHtmlPart(6)
if(true && (userPaginationSetting == ProfileSetting.SCROLL_SETTING.SCROLL || (userPaginationSetting == ProfileSetting.SCROLL_SETTING.DEFAULT && defaultPaginationSetting == ProfileSetting.SCROLL_SETTING.SCROLL))) {
printHtmlPart(19)
invokeTag('javascript','g',75,['plugin':("remote-pagination"),'library':("remoteNonStopPageScroll")],-1)
printHtmlPart(20)
invokeTag('remotePageScroll','util',79,['action':(action),'total':(searchCount),'update':(div_id),'max':("10"),'heightOffset':("1000"),'loadingHTML':("submissionLoader"),'onLoading':("maybeHideLoader(${offset}, ${searchCount}, ${max})"),'onLoaded':("hideLoader()"),'params':([searchInput: params.searchInput ?: null, tagInput: params.tagInput ?: null, list: list, div_id: div_id, action: action, searchCount: searchCount, source: params?.source, typeName: params?.typeName])],-1)
printHtmlPart(6)
}
else if(true && (userPaginationSetting == ProfileSetting.SCROLL_SETTING.PAGING || (userPaginationSetting == ProfileSetting.SCROLL_SETTING.DEFAULT && defaultPaginationSetting == ProfileSetting.SCROLL_SETTING.PAGING))) {
printHtmlPart(21)
invokeTag('remotePaginate','util',83,['action':(action),'total':(searchCount),'update':(div_id),'maxsteps':("10"),'max':("10"),'params':([searchInput: params.searchInput ?: null, tagInput: params.tagInput ?: null, list: list, div_id: div_id, action: action, searchCount: searchCount, source: params?.source, typeName: params?.typeName])],-1)
printHtmlPart(6)
}
printHtmlPart(6)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574884287000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
