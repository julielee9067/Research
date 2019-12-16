import ca.georgebrown.gbcresearch.SubmissionType
import  ca.georgebrown.gbcresearch.Submission
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_helplist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/help/list.gsp" }
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
invokeTag('captureHead','sitemesh',36,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',45,['template':("/layouts/navigation"),'model':([which: 'help'])],-1)
printHtmlPart(5)
for( uploadedFile in (fileList) ) {
printHtmlPart(6)
expressionOut.print(uploadedFile.originalFilename)
printHtmlPart(7)
createClosureForHtmlPart(8, 3)
invokeTag('link','g',63,['controller':("uploadFile"),'action':("download"),'id':(uploadedFile.id),'target':("_blank"),'class':("btn btn-sm btn-success btn-sm"),'style':("border-color: white; border-width: thin;")],3)
printHtmlPart(9)
}
printHtmlPart(10)
createTagBody(2, {->
printHtmlPart(11)
expressionOut.print(grailsApplication.config.file.upload.directory)
printHtmlPart(12)
})
invokeTag('form','g',78,['method':("POST"),'controller':("help"),'action':("uploadHelpDocument"),'enctype':("multipart/form-data")],2)
printHtmlPart(13)
})
invokeTag('captureBody','sitemesh',81,[:],1)
printHtmlPart(14)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1576008122000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
