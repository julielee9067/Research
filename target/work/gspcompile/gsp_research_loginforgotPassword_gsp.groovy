import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_loginforgotPassword_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/login/forgotPassword.gsp" }
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
invokeTag('stylesheet','asset',12,['src':("backgroundImage.css")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',14,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',14,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',14,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
invokeTag('message','g',20,['code':("spring.security.ui.forgotPassword.header")],-1)
printHtmlPart(7)
if(true && (emailSent)) {
printHtmlPart(8)
invokeTag('message','g',24,['code':("spring.security.ui.forgotPassword.sent")],-1)
printHtmlPart(9)
}
printHtmlPart(10)
if(true && (usesLdap)) {
printHtmlPart(8)
invokeTag('image','asset',29,['src':("warning.png")],-1)
printHtmlPart(11)
createClosureForHtmlPart(12, 3)
invokeTag('link','g',33,['controller':("login")],3)
printHtmlPart(9)
}
else {
printHtmlPart(8)
invokeTag('message','g',38,['code':("spring.security.ui.forgotPassword.description")],-1)
printHtmlPart(13)
if(true && (emailNotRegistered)) {
printHtmlPart(14)
}
printHtmlPart(15)
createTagBody(3, {->
printHtmlPart(16)
invokeTag('message','g',46,['code':("spring.security.ui.forgotPassword.username")],-1)
printHtmlPart(17)
})
invokeTag('form','g',54,['action':("forgotPassword"),'name':("forgotPasswordForm"),'autocomplete':("off")],3)
printHtmlPart(18)
}
printHtmlPart(19)
})
invokeTag('captureBody','sitemesh',57,[:],1)
printHtmlPart(20)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574275155000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
