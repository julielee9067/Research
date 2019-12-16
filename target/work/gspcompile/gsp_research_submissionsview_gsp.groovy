import ca.georgebrown.gbcresearch.UploadedFile
import  ca.georgebrown.gbcresearch.Submission
import   ca.georgebrown.gbcresearch.security.Role
import  ca.georgebrown.gbcresearch.security.AppuserRole
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_submissionsview_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/submissions/view.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',12,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',13,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',52,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',56,['template':("/layouts/navigation")],-1)
printHtmlPart(6)
createTagBody(2, {->
printHtmlPart(7)
if(true && (source=='publisher')) {
printHtmlPart(8)
createClosureForHtmlPart(9, 4)
invokeTag('link','g',67,['controller':(source),'action':("list"),'params':([source: source, searchInput: searchInput, tagInput: tagInput]),'style':("font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px")],4)
printHtmlPart(10)
}
else if(true && (source=='homePage')) {
printHtmlPart(8)
createClosureForHtmlPart(9, 4)
invokeTag('link','g',71,['controller':(source),'action':("index"),'params':([source: source, searchInput: searchInput, tagInput: tagInput]),'style':("font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px")],4)
printHtmlPart(10)
}
else if(true && (source=='profile')) {
printHtmlPart(8)
createClosureForHtmlPart(9, 4)
invokeTag('link','g',75,['controller':(source),'action':("index"),'params':([source: source, searchInput: searchInput, tagInput: tagInput, profile_id: profile_id]),'style':("font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px")],4)
printHtmlPart(10)
}
else {
printHtmlPart(8)
createClosureForHtmlPart(9, 4)
invokeTag('link','g',79,['uri':(request.getHeader('referer')),'style':("font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px")],4)
printHtmlPart(10)
}
printHtmlPart(11)
expressionOut.print(submission?.id)
printHtmlPart(12)
expressionOut.print(submission?.title)
printHtmlPart(13)
if(true && (submission?.submitStatus == Submission.SUBMIT_STATUS.RETURNED)) {
printHtmlPart(14)
if(true && (!submission?.rejectionReason)) {
printHtmlPart(15)
}
else if(true && (submission?.rejectionReason?.trim() == "")) {
printHtmlPart(15)
}
else {
printHtmlPart(16)
expressionOut.print(submission?.rejectionReason)
printHtmlPart(17)
}
printHtmlPart(18)
}
printHtmlPart(19)
invokeTag('render','g',103,['template':("viewForm"),'model':([status: submission?.submitStatus])],-1)
printHtmlPart(20)
})
invokeTag('form','g',103,['controller':("submissions"),'name':("update"),'params':([id: submission?.id]),'id':(submission?.id),'action':("edit"),'method':("post"),'class':("project")],2)
printHtmlPart(21)
if(true && (submission && accessRights[Submission.RIGHT.READ])) {
printHtmlPart(22)
invokeTag('render','g',113,['template':("/uploadedFiles/list"),'model':([view: 'true', returnController: 'submissions', returnAction: 'edit', uploadedFileList: uploadFileList])],-1)
printHtmlPart(23)
}
printHtmlPart(24)
if(true && (Role.findByAuthority('ROLE_PUBLISHER') in appuser.authorities &&
                        submission.source != Submission.SOURCE.IAGO && submission.submitStatus != Submission.SUBMIT_STATUS.RETURNED)) {
printHtmlPart(25)
createClosureForHtmlPart(26, 3)
invokeTag('link','g',121,['controller':("submissions"),'action':("edit"),'id':(submission.id),'params':([source: source, profile_id: profile_id]),'style':("text-decoration: none;width: 80px; color: white; background-color: #005AA5"),'class':("btn")],3)
printHtmlPart(27)
}
printHtmlPart(28)
})
invokeTag('captureBody','sitemesh',122,[:],1)
printHtmlPart(29)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574273385000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
