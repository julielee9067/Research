import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_userManagementedit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/edit.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(1)
createClosureForHtmlPart(3, 2)
invokeTag('javascript','g',10,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',11,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',14,['template':("/layouts/navigation"),'model':([which:'userManagement'])],-1)
printHtmlPart(6)
createClosureForHtmlPart(7, 2)
invokeTag('form','g',18,['controller':("userManagement"),'action':("list")],2)
printHtmlPart(8)
expressionOut.print(user.displayName)
printHtmlPart(9)
if(true && (flash.message)) {
printHtmlPart(10)
expressionOut.print(flash.message)
printHtmlPart(11)
}
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(13)
createTagBody(3, {->
printHtmlPart(14)
if(true && (error in org.springframework.validation.FieldError)) {
printHtmlPart(15)
expressionOut.print(error.field)
printHtmlPart(16)
}
printHtmlPart(17)
invokeTag('message','g',33,['error':(error)],-1)
printHtmlPart(18)
})
invokeTag('eachError','g',34,['bean':(user),'var':("error")],3)
printHtmlPart(19)
})
invokeTag('hasErrors','g',36,['bean':(user)],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(20)
invokeTag('hiddenField','g',38,['name':("id"),'value':(user?.id)],-1)
printHtmlPart(20)
invokeTag('hiddenField','g',39,['name':("version"),'value':(user?.version)],-1)
printHtmlPart(21)
invokeTag('render','g',40,['template':("form"),'model':([which: 'edit'])],-1)
printHtmlPart(22)
invokeTag('actionSubmit','g',42,['class':("save btn btn-primary"),'style':("background-color: #005AA5"),'action':("update"),'value':("Update"),'id':("update")],-1)
printHtmlPart(23)
})
invokeTag('form','g',44,['method':("post")],2)
printHtmlPart(24)
})
invokeTag('captureBody','sitemesh',48,[:],1)
printHtmlPart(25)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574273890000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
