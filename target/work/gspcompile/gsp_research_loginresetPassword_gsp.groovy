import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_loginresetPassword_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/login/resetPassword.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('message','g',11,['code':("spring.security.ui.resetPassword.title")],-1)
})
invokeTag('captureTitle','sitemesh',11,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',11,[:],2)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
invokeTag('stylesheet','asset',12,['src':("security-ui.css")],-1)
printHtmlPart(2)
invokeTag('stylesheet','asset',13,['src':("resetPassword.css")],-1)
printHtmlPart(2)
invokeTag('stylesheet','asset',14,['src':("backgroundImage.css")],-1)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',14,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
createTagBody(2, {->
printHtmlPart(5)
invokeTag('hiddenField','g',19,['name':("t"),'value':(token)],-1)
printHtmlPart(6)
expressionOut.print(user?.displayName)
printHtmlPart(7)
expressionOut.print(command?.password)
printHtmlPart(8)
expressionOut.print(command?.password2)
printHtmlPart(9)
expressionOut.print(message (error:(command.errors.getFieldError('password2'))))
printHtmlPart(10)
expressionOut.print(message (error:(command.errors.getFieldError('password'))))
printHtmlPart(11)
})
invokeTag('form','g',78,['action':("resetPassword"),'name':("resetPasswordForm"),'autocomplete':("off"),'onsubmit':("return validateForm()")],2)
printHtmlPart(12)
})
invokeTag('captureBody','sitemesh',238,[:],1)
printHtmlPart(13)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272128000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
