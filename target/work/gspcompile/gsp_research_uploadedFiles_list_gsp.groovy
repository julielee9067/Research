import ca.georgebrown.gbcresearch.Submission
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_uploadedFiles_list_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/uploadedFiles/_list.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
if(true && (uploadedFileList?.size())) {
printHtmlPart(1)
if(true && (accessRights[Submission.RIGHT.EDIT] && !view)) {
printHtmlPart(2)
}
printHtmlPart(3)
for( uploadedFile in (uploadedFileList) ) {
printHtmlPart(4)
createTagBody(3, {->
expressionOut.print(uploadedFile.originalFilename)
})
invokeTag('link','g',54,['controller':("uploadFile"),'action':("download"),'target':("_blank"),'id':(uploadedFile.id)],3)
printHtmlPart(5)
invokeTag('formatDate','g',55,['date':(uploadedFile.dateCreated),'format':("yyyy-MM-dd")],-1)
printHtmlPart(5)
expressionOut.print(uploadedFile.uploadedBy?.displayName)
printHtmlPart(6)
if(true && (accessRights[Submission.RIGHT.EDIT] && !view)) {
printHtmlPart(7)
createTagBody(4, {->
printHtmlPart(8)
expressionOut.print(uploadedFile.id)
printHtmlPart(9)
expressionOut.print(uploadedFile.id)
printHtmlPart(10)
expressionOut.print(uploadedFile.id)
printHtmlPart(11)
})
invokeTag('form','g',62,['method':("post"),'controller':(returnController),'action':(returnAction),'params':([createNew: params.createNew ?: 'false', submissionId: uploadedFile.objId])],4)
printHtmlPart(12)
}
printHtmlPart(13)
}
printHtmlPart(14)
}
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574690413000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
