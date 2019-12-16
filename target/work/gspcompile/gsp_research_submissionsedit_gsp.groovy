import ca.georgebrown.gbcresearch.Submission
import   ca.georgebrown.gbcresearch.security.Role
import  ca.georgebrown.gbcresearch.security.AppuserRole
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_submissionsedit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/submissions/edit.gsp" }
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
invokeTag('captureTitle','sitemesh',12,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',13,[:],2)
printHtmlPart(2)
createTagBody(2, {->
printHtmlPart(4)
expressionOut.print(createNew != "true")
printHtmlPart(5)
expressionOut.print(createNew != "true")
printHtmlPart(6)
if(true && (anchor == 'stepAnchor')) {
printHtmlPart(7)
expressionOut.print(anchor)
printHtmlPart(8)
}
else if(true && (anchor == 'fileAnchor')) {
printHtmlPart(9)
expressionOut.print(anchor)
printHtmlPart(8)
}
printHtmlPart(10)
if(true && (project)) {
printHtmlPart(11)
}
printHtmlPart(12)
if(true && (dateResult?.errorMsg)) {
printHtmlPart(13)
invokeTag('image','asset',166,['src':("warning.png")],-1)
printHtmlPart(14)
expressionOut.print(dateResult?.errorMsg)
printHtmlPart(15)
}
else if(true && (createSuccess)) {
printHtmlPart(16)
if(true && (nameMatch)) {
printHtmlPart(17)
}
printHtmlPart(18)
}
else if(true && (updateSuccess)) {
printHtmlPart(19)
}
else if(true && (recordExists)) {
printHtmlPart(20)
}
printHtmlPart(21)
if(true && (result?.deleteFileSuccess)) {
printHtmlPart(22)
}
else if(true && (result?.deleFileErrerMsg)) {
printHtmlPart(13)
expressionOut.print(result?.deleFileErrerMsg)
printHtmlPart(15)
}
printHtmlPart(23)
if(true && (result?.errorMsg)) {
printHtmlPart(24)
invokeTag('image','asset',240,['src':("warning.png")],-1)
printHtmlPart(25)
}
printHtmlPart(26)
if(true && (projectRefList?.size())) {
printHtmlPart(27)
for( ref in (projectRefList) ) {
printHtmlPart(28)
expressionOut.print(ref)
printHtmlPart(29)
}
printHtmlPart(30)
invokeTag('image','asset',260,['src':("warning.png")],-1)
printHtmlPart(31)
}
printHtmlPart(26)
if(true && (budgetDeleteSuccess)) {
printHtmlPart(32)
}
printHtmlPart(26)
if(true && (stakeholderDeleteSuccess)) {
printHtmlPart(33)
}
printHtmlPart(34)
})
invokeTag('javascript','g',439,[:],2)
printHtmlPart(35)
})
invokeTag('captureHead','sitemesh',484,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(36)
invokeTag('render','g',491,['template':("/layouts/navigation")],-1)
printHtmlPart(37)
createTagBody(2, {->
printHtmlPart(38)
createClosureForHtmlPart(39, 3)
invokeTag('link','g',502,['controller':("submissions"),'action':("view"),'params':([id: params.id, source: params?.source, profile_id: profile_id]),'style':("font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px")],3)
printHtmlPart(40)
expressionOut.print(submission?.id)
printHtmlPart(41)
if(true && (submission?.title)) {
printHtmlPart(42)
expressionOut.print(submission?.title)
printHtmlPart(43)
if(true && (submission?.activeStatus == Submission.STATUS.DISABLED)) {
printHtmlPart(44)
}
printHtmlPart(45)
}
printHtmlPart(46)
if(true && (submission?.submitStatus == Submission.SUBMIT_STATUS.RETURNED)) {
printHtmlPart(47)
if(true && (!submission?.rejectionReason)) {
printHtmlPart(48)
}
else if(true && (submission?.rejectionReason?.trim() == "")) {
printHtmlPart(48)
}
else {
printHtmlPart(49)
expressionOut.print(submission?.rejectionReason)
printHtmlPart(50)
}
printHtmlPart(51)
}
printHtmlPart(52)
invokeTag('render','g',529,['template':("editForm"),'model':([status: submission?.submitStatus, types: types, submission: submission, appuser: appuser, accessRights: accessRights])],-1)
printHtmlPart(53)
invokeTag('actionSubmit','g',535,['value':("Save"),'id':("save"),'name':("save"),'action':("edit"),'class':("btn text-white rounded-0"),'style':("display: none"),'params':([id: submission?.id, source: 'profile', profile_id: profile_id]),'controller':("submissions")],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',538,['value':("Cancel"),'id':("cancel"),'name':("cancel"),'action':("edit"),'class':("btn text-white rounded-0"),'style':("display:none"),'params':([id: submission?.id, source: 'profile', profile_id: profile_id]),'controller':("submissions")],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',539,['style':("display:none"),'id':("recall"),'value':("recall"),'action':("edit"),'params':([id: submission?.id, profile_id: profile_id])],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',542,['id':("publisherReject"),'value':("reject"),'name':("reject"),'action':("edit"),'params':([id: submission?.id]),'controller':("submissions"),'class':("hidden")],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',545,['value':("SaveModePublish"),'id':("saveModePublish"),'name':("saveModePublish"),'action':("edit"),'class':("btn text-white rounded-0"),'style':("display:none"),'params':([id: submission?.id, profile_id: profile_id]),'controller':("submissions")],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',548,['value':("Cancel"),'id':("cancel"),'name':("cancel"),'action':("edit"),'class':("btn text-white rounded-0"),'style':("display:none"),'params':([id: submission?.id, source: 'profile', profile_id: profile_id]),'controller':("submissions")],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',550,['id':("updateSubmit"),'value':("updateSubmit"),'name':("updateSubmit"),'action':("edit"),'params':([id: submission?.id, submit: true, profile_id: profile_id]),'controller':("submissions"),'class':("hidden")],-1)
printHtmlPart(54)
invokeTag('actionSubmit','g',553,['id':("savePublish"),'value':("updatePublish"),'name':("updatePublish"),'action':("edit"),'params':([id: submission?.id]),'controller':("submissions"),'class':("hidden")],-1)
printHtmlPart(55)
})
invokeTag('form','g',556,['controller':("submissions"),'name':("update"),'params':([id: submission?.id, source: params?.source, profile_id: profile_id]),'id':(submission?.id),'action':("edit"),'method':("post"),'class':("project")],2)
printHtmlPart(56)
if(true && (uploadFileList.size() > 0 || uploadFileListNewSubmission.size() > 0 || (accessRights[Submission.RIGHT.EDIT]))) {
printHtmlPart(57)
if(true && (view == 'true')) {
printHtmlPart(43)
invokeTag('render','g',566,['template':("/uploadedFiles/list"),'model':([returnController: 'submissions', returnAction: 'edit', view: 'true', uploadedFileList: uploadFileList])],-1)
printHtmlPart(52)
}
else {
printHtmlPart(43)
if(true && (params.createNew == 'true')) {
printHtmlPart(58)
invokeTag('render','g',571,['template':("/uploadedFiles/upload"),'model':([returnController: 'submissions', returnAction: 'edit', source: 'Submission', objId: 0, createNew: 'true', params: params])],-1)
printHtmlPart(58)
invokeTag('render','g',573,['template':("/uploadedFiles/list"),'model':([returnController: 'submissions', returnAction: 'edit', uploadedFileList: uploadFileListNewSubmission])],-1)
printHtmlPart(43)
}
else {
printHtmlPart(58)
invokeTag('render','g',577,['template':("/uploadedFiles/upload"),'model':([returnController: 'submissions', returnAction: 'edit', source: 'Submission', objId: submission?.id, params: params])],-1)
printHtmlPart(58)
invokeTag('render','g',579,['template':("/uploadedFiles/list"),'model':([returnController: 'submissions', returnAction: 'edit', uploadedFileList: uploadFileList])],-1)
printHtmlPart(43)
}
printHtmlPart(52)
}
printHtmlPart(59)
}
printHtmlPart(60)
if(true && (!createNew && submission?.submitStatus != Submission.SUBMIT_STATUS.SUBMITTED && accessRights[Submission.RIGHT.DELETE])) {
printHtmlPart(61)
}
else if(true && (submission?.activeStatus == Submission.STATUS.DISABLED)) {
printHtmlPart(62)
}
printHtmlPart(54)
if(true && (accessRights[Submission.RIGHT.RECALL] && params.mode != "publish")) {
printHtmlPart(63)
expressionOut.print(submission?.id)
printHtmlPart(64)
}
else {
printHtmlPart(65)
if(true && (accessRights[Submission.RIGHT.EDIT] && params.mode != "publish")) {
printHtmlPart(66)
expressionOut.print(submission?.id)
printHtmlPart(67)
expressionOut.print(submission?.id)
printHtmlPart(68)
}
else if(true && (accessRights[Submission.RIGHT.EDIT] && params.mode == "publish")) {
printHtmlPart(66)
expressionOut.print(submission?.id)
printHtmlPart(69)
expressionOut.print(submission?.id)
printHtmlPart(68)
}
printHtmlPart(65)
if(true && (!createNew && accessRights[Submission.RIGHT.SUBMIT] && params.mode != "publish")) {
printHtmlPart(70)
expressionOut.print(submission?.id ?: 0)
printHtmlPart(71)
}
printHtmlPart(54)
}
printHtmlPart(54)
if(true && (accessRights[Submission.RIGHT.PUBLISH] && params.mode == "publish")) {
printHtmlPart(72)
expressionOut.print(submission?.id)
printHtmlPart(73)
}
printHtmlPart(54)
if(true && (accessRights[Submission.RIGHT.RETURN] && submission?.submitStatus != Submission.SUBMIT_STATUS.RETURNED &&
                        (submission?.submitStatus == Submission.SUBMIT_STATUS.PUBLISHED || params.mode == "publish"))) {
printHtmlPart(74)
expressionOut.print(submission?.id)
printHtmlPart(75)
}
printHtmlPart(76)
})
invokeTag('captureBody','sitemesh',632,[:],1)
printHtmlPart(77)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574883415000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
