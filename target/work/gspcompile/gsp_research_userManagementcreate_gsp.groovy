import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_userManagementcreate_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/create.gsp" }
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
invokeTag('wrapTitleTag','sitemesh',12,[:],2)
printHtmlPart(4)
createTagBody(2, {->
printHtmlPart(5)
if(true && (createSuccess)) {
printHtmlPart(6)
}
printHtmlPart(7)
})
invokeTag('javascript','g',17,[:],2)
printHtmlPart(8)
})
invokeTag('captureHead','sitemesh',23,[:],1)
printHtmlPart(9)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',29,['template':("/layouts/navigation"),'model':([which: 'userManagement'])],-1)
printHtmlPart(10)
createClosureForHtmlPart(11, 2)
invokeTag('form','g',33,['controller':("userManagement"),'action':("list")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(13)
invokeTag('render','g',44,['template':("form"),'model':([which: 'create'])],-1)
printHtmlPart(14)
invokeTag('actionSubmit','g',50,['class':("save btn btn-success"),'action':("create"),'value':("Create")],-1)
printHtmlPart(15)
})
invokeTag('form','g',51,['method':("post")],2)
printHtmlPart(16)
})
invokeTag('captureBody','sitemesh',53,[:],1)
printHtmlPart(17)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574273886000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
