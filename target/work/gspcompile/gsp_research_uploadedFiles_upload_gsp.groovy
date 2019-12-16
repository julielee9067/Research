import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_uploadedFiles_upload_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/uploadedFiles/_upload.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(grailsApplication.config.file.upload.allowedExtensions.join(', '))
printHtmlPart(1)
expressionOut.print((grailsApplication.config.file.upload.maxSize / 1048576))
printHtmlPart(2)
createTagBody(1, {->
printHtmlPart(3)
expressionOut.print(grailsApplication.config.file.upload.directory)
printHtmlPart(4)
expressionOut.print(source)
printHtmlPart(5)
expressionOut.print(objId)
printHtmlPart(6)
expressionOut.print(returnController)
printHtmlPart(7)
expressionOut.print(returnAction)
printHtmlPart(8)
expressionOut.print(params.createNew ?: 'false')
printHtmlPart(9)
})
invokeTag('form','g',58,['method':("POST"),'controller':("uploadFile"),'action':("upload"),'enctype':("multipart/form-data")],1)
printHtmlPart(10)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1576008110000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
