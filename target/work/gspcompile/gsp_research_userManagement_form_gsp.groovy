import ca.georgebrown.gbcresearch.security.Appuser
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_userManagement_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
if(true && (user)) {
printHtmlPart(1)
expressionOut.print(user.authenticationType)
printHtmlPart(2)
if(true && (user.username == 'admin')) {
printHtmlPart(3)
invokeTag('render','g',8,['template':("adminForm"),'model':([user: user])],-1)
printHtmlPart(4)
}
else {
printHtmlPart(3)
if(true && (user.authenticationType == Appuser.AUTHENTICATION_TYPE.LDAP)) {
printHtmlPart(5)
invokeTag('render','g',12,['template':("ldapForm"),'model':([user: user])],-1)
printHtmlPart(3)
}
printHtmlPart(3)
if(true && (user.authenticationType == Appuser.AUTHENTICATION_TYPE.DAO)) {
printHtmlPart(5)
invokeTag('render','g',15,['template':("daoForm"),'model':([user: user])],-1)
printHtmlPart(3)
}
printHtmlPart(4)
}
printHtmlPart(6)
invokeTag('render','g',19,['template':("rolesForm"),'model':([user:user])],-1)
printHtmlPart(7)
}
else {
printHtmlPart(4)
if(true && (which == 'create')) {
printHtmlPart(8)
invokeTag('render','g',23,['template':("daoForm"),'model':([user:user])],-1)
printHtmlPart(4)
}
printHtmlPart(7)
}
printHtmlPart(9)
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
