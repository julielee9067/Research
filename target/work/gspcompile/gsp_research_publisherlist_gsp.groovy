import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_publisherlist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/publisher/list.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',11,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',11,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',12,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',19,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',24,['template':("/layouts/navigation"),'model':([which: 'publish'])],-1)
printHtmlPart(6)
if(true && (fsList && fsList?.size() > 0)) {
printHtmlPart(7)
expressionOut.print(total)
printHtmlPart(8)
for( submission in (fsList) ) {
printHtmlPart(9)
expressionOut.print(submission.title)
printHtmlPart(10)
expressionOut.print(submission?.owner?.displayName)
printHtmlPart(10)
expressionOut.print(submission?.submitter?.displayName)
printHtmlPart(10)
invokeTag('formatDate','g',52,['format':("yyyy-MM-dd HH:mm"),'date':(submission?.submissionDate)],-1)
printHtmlPart(10)
expressionOut.print(submission?.submitStatus)
printHtmlPart(10)
createClosureForHtmlPart(11, 4)
invokeTag('link','g',54,['class':("btn btn-sm btn-outline-primary"),'action':("linkToPage"),'controller':("submissions"),'params':(['type': 'Submission', 'id': submission.id, 'source': 'publisher'])],4)
printHtmlPart(12)
}
printHtmlPart(13)
invokeTag('paginate','g',62,['next':("Forward"),'prev':("Back"),'maxsteps':("5"),'controller':("publisher"),'action':("list"),'total':(total)],-1)
printHtmlPart(14)
}
else {
printHtmlPart(15)
}
printHtmlPart(16)
if(true && (rtList && rtList?.size() > 0)) {
printHtmlPart(17)
expressionOut.print(rtTotal)
printHtmlPart(18)
for( submission in (rtList) ) {
printHtmlPart(9)
expressionOut.print(submission.title)
printHtmlPart(10)
expressionOut.print(submission?.owner?.displayName)
printHtmlPart(10)
expressionOut.print(submission?.submitter?.displayName)
printHtmlPart(10)
invokeTag('formatDate','g',92,['format':("yyyy-MM-dd HH:mm"),'date':(submission?.submissionDate)],-1)
printHtmlPart(10)
expressionOut.print(submission?.submitStatus)
printHtmlPart(10)
createClosureForHtmlPart(11, 4)
invokeTag('link','g',94,['class':("btn btn-sm btn-outline-primary"),'action':("linkToPage"),'controller':("homePage"),'params':(['type': 'Submission', 'id': submission.id, 'source': 'publisher'])],4)
printHtmlPart(12)
}
printHtmlPart(13)
invokeTag('paginate','g',102,['next':("Forward"),'prev':("Back"),'maxsteps':("5"),'controller':("publisher"),'action':("list"),'total':(rtTotal)],-1)
printHtmlPart(14)
}
else {
printHtmlPart(19)
}
printHtmlPart(20)
})
invokeTag('captureBody','sitemesh',108,[:],1)
printHtmlPart(21)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272609000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
