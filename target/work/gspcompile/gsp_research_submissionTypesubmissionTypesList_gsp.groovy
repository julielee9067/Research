import ca.georgebrown.gbcresearch.SubmissionType
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_submissionTypesubmissionTypesList_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/submissionType/submissionTypesList.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',13,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',13,[:],2)
printHtmlPart(4)
createTagBody(2, {->
printHtmlPart(5)
if(true && (result?.errorMsg)) {
printHtmlPart(6)
}
else if(true && (result?.success)) {
printHtmlPart(7)
}
printHtmlPart(8)
expressionOut.print(result?.id)
printHtmlPart(9)
})
invokeTag('javascript','g',36,[:],2)
printHtmlPart(10)
})
invokeTag('captureHead','sitemesh',37,[:],1)
printHtmlPart(11)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',42,['template':("/layouts/navigation"),'model':([which: 'submissionType'])],-1)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(13)
if(true && (flash.message)) {
printHtmlPart(14)
expressionOut.print(flash.message)
printHtmlPart(15)
}
printHtmlPart(16)
for( type in (types) ) {
printHtmlPart(17)
expressionOut.print(type.id)
printHtmlPart(18)
expressionOut.print(type.name)
printHtmlPart(19)
expressionOut.print(type.id)
printHtmlPart(20)
expressionOut.print(type.description)
printHtmlPart(21)
expressionOut.print(type.id)
printHtmlPart(22)
}
printHtmlPart(23)
})
invokeTag('form','g',89,['method':("post")],2)
printHtmlPart(24)
})
invokeTag('captureBody','sitemesh',91,[:],1)
printHtmlPart(25)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574273523000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
