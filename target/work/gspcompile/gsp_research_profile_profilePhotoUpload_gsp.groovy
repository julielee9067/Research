import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_profile_profilePhotoUpload_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/profile/_profilePhotoUpload.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('checkBox','g',23,['id':("showEmailCheck"),'style':("color: black"),'name':("showEmail"),'value':(settings?.showEmail)],-1)
printHtmlPart(2)
expressionOut.print(grailsApplication.config.file.profilePhoto.directory)
printHtmlPart(3)
expressionOut.print(source)
printHtmlPart(4)
expressionOut.print(objId)
printHtmlPart(5)
expressionOut.print(returnController)
printHtmlPart(6)
expressionOut.print(returnAction)
printHtmlPart(7)
})
invokeTag('form','g',37,['url':([controller: 'uploadFile', action: 'upload']),'name':("profilePhoto"),'method':("POST"),'controller':("uploadFile"),'action':("upload"),'enctype':("multipart/form-data")],1)
printHtmlPart(8)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272199000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
