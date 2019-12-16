import ca.georgebrown.gbcresearch.Submission
import  ca.georgebrown.gbcresearch.SubmissionsController
import  ca.georgebrown.gbcresearch.Tag
import ca.georgebrown.gbcresearch.security.Role
import  ca.georgebrown.gbcresearch.security.AppuserRole
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_submissions_editForm_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/submissions/_editForm.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
expressionOut.print(Tag.findAllByStatus(Tag.STATUS.ACTIVE).id)
printHtmlPart(3)
expressionOut.print(AppuserRole.findByAppuserAndRole(appuser, Role.findByAuthority('ROLE_PUBLISHER')).toString())
printHtmlPart(4)
expressionOut.print(remoteFunction(controller: 'tags', action: 'deleteTagPublisher', params: '\'title=\' + tagName'))
printHtmlPart(5)
})
invokeTag('javascript','g',114,[:],1)
printHtmlPart(6)
if(true && (status && status!= Submission.SUBMIT_STATUS.INITIATED)) {
printHtmlPart(7)
expressionOut.print(Submission.SUBMIT_STATUS.find { it.value ==  status}.key)
printHtmlPart(8)
expressionOut.print(status)
printHtmlPart(9)
}
printHtmlPart(10)
expressionOut.print(submission?.title)
printHtmlPart(11)
expressionOut.print(submission?.description)
printHtmlPart(12)
if(true && (accessRights[Submission.RIGHT.EDIT])) {
printHtmlPart(13)
createClosureForHtmlPart(13, 2)
invokeTag('select','g',164,['from':(Tag.findAllByStatus(Tag.STATUS.ACTIVE)),'optionKey':("id"),'optionValue':("name"),'value':(submission?.tags?.toList()),'multiple':("multiple"),'id':("tag"),'name':("tags"),'class':("form-control editField tagSelect requiredInput"),'aria-describedby':("tagHelp")],2)
printHtmlPart(14)
}
else if(true && (accessRights[Submission.RIGHT.EDIT])) {
printHtmlPart(13)
createClosureForHtmlPart(13, 2)
invokeTag('select','g',177,['from':(Tag.findAllByStatusOrCreatedById(Tag.STATUS.ACTIVE, appuser.id)),'optionKey':("id"),'optionValue':("name"),'value':(submission?.tags?.toList()),'multiple':("multiple"),'id':("tag"),'name':("tags"),'class':("form-control editField tagSelect requiredInput"),'aria-describedby':("tagHelp")],2)
printHtmlPart(14)
}
else {
printHtmlPart(15)
for( tag in (submission?.tags?.toList()) ) {
printHtmlPart(16)
expressionOut.print(tag.name)
printHtmlPart(17)
}
printHtmlPart(18)
}
printHtmlPart(19)
if(true && (submission?.submitter)) {
printHtmlPart(20)
expressionOut.print(submission.submitter.displayName)
printHtmlPart(21)
}
else {
printHtmlPart(20)
expressionOut.print(appuser.displayName)
printHtmlPart(21)
}
printHtmlPart(22)
if(true && (accessRights[Submission.RIGHT.EDIT])) {
printHtmlPart(23)
}
printHtmlPart(24)
if(true && (!accessRights[Submission.RIGHT.EDIT])) {
printHtmlPart(20)
expressionOut.print(submission?.type?.name)
printHtmlPart(25)
}
else {
printHtmlPart(26)
createClosureForHtmlPart(26, 2)
invokeTag('select','g',215,['from':(types),'optionKey':("id"),'optionValue':("name"),'noSelection':(['null': 'Select...']),'value':(submission?.type?.id),'name':("submission.type"),'id':("type"),'class':("form-control editField departmentSelect requiredInput w-100")],2)
printHtmlPart(13)
}
printHtmlPart(27)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574455158000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
