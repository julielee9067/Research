import ca.georgebrown.gbcresearch.Tag
import ca.georgebrown.gbcresearch.security.Role
import  ca.georgebrown.gbcresearch.security.AppuserRole
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_submissions_viewForm_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/submissions/_viewForm.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createClosureForHtmlPart(2, 1)
invokeTag('javascript','g',22,[:],1)
printHtmlPart(3)
expressionOut.print(submission?.description)
printHtmlPart(4)
for( tag in (submission?.tags?.toList()) ) {
printHtmlPart(5)
if(true && (tag?.status == Tag.STATUS.ACTIVE)) {
printHtmlPart(6)
createTagBody(3, {->
expressionOut.print(tag.name)
})
invokeTag('link','g',38,['controller':("homePage"),'action':("index"),'params':([tagInput: tag.name, source: 'homePage']),'class':("btn btn-sm btn-outline-primary")],3)
printHtmlPart(7)
}
printHtmlPart(8)
}
printHtmlPart(9)
expressionOut.print(submission?.type?.name)
printHtmlPart(10)
if(true && (submission.source == ca.georgebrown.gbcresearch.Submission.SOURCE.IAGO)) {
printHtmlPart(11)
expressionOut.print(submission.principleInvestigators*.displayName.join(", "))
printHtmlPart(12)
if(true && (submission.coInvestigators.size())) {
printHtmlPart(13)
expressionOut.print(submission.coInvestigators*.displayName.join(", "))
printHtmlPart(14)
}
printHtmlPart(15)
if(true && (submission.partners.size())) {
printHtmlPart(16)
expressionOut.print(submission.partners*.organization.join(", "))
printHtmlPart(14)
}
printHtmlPart(15)
if(true && (submission.researchThemes.size())) {
printHtmlPart(17)
expressionOut.print(submission.researchThemes*.theme.join(", "))
printHtmlPart(14)
}
printHtmlPart(18)
}
else {
printHtmlPart(19)
if(true && (submission?.submitter?.username == 'admin')) {
printHtmlPart(5)
if(true && (submission?.principleInvestigators?.size())) {
printHtmlPart(20)
expressionOut.print(submission.principleInvestigators.first().displayName)
printHtmlPart(5)
}
printHtmlPart(8)
}
else {
printHtmlPart(5)
createTagBody(3, {->
expressionOut.print(submission?.submitter?.firstName + ' ' + submission?.submitter?.lastName)
printHtmlPart(5)
})
invokeTag('link','g',89,['controller':("profile"),'params':([profile_id: submission?.submitter?.id]),'action':("index")],3)
printHtmlPart(8)
}
printHtmlPart(21)
}
printHtmlPart(22)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574357986000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
