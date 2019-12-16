import ca.georgebrown.gbcresearch.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_layouts_navigation_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/_navigation.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
createClosureForHtmlPart(2, 2)
invokeTag('ifAnyGranted','sec',25,['roles':(" ROLE_APP_MANAGER,  ROLE_DEVELOPER")],2)
printHtmlPart(3)
if(true && (appuser)) {
printHtmlPart(4)
if(true && (which == 'settings')) {
printHtmlPart(5)
}
printHtmlPart(6)
createClosureForHtmlPart(7, 3)
invokeTag('link','g',29,['controller':("settings"),'action':("list")],3)
printHtmlPart(8)
if(true && (which == 'wightingFactors')) {
printHtmlPart(5)
}
printHtmlPart(6)
createClosureForHtmlPart(9, 3)
invokeTag('link','g',32,['controller':("score"),'action':("list")],3)
printHtmlPart(8)
if(true && (which == 'submissionType')) {
printHtmlPart(5)
}
printHtmlPart(6)
createClosureForHtmlPart(10, 3)
invokeTag('link','g',35,['controller':("submissionType"),'action':("submissionTypesList")],3)
printHtmlPart(8)
if(true && (which == 'tags')) {
printHtmlPart(5)
}
printHtmlPart(6)
createClosureForHtmlPart(11, 3)
invokeTag('link','g',38,['controller':("tags"),'action':("tagsList")],3)
printHtmlPart(8)
if(true && (which == 'help')) {
printHtmlPart(5)
}
printHtmlPart(6)
createClosureForHtmlPart(12, 3)
invokeTag('link','g',41,['controller':("help"),'action':("list")],3)
printHtmlPart(13)
}
printHtmlPart(14)
})
invokeTag('ifAnyGranted','sec',1,['roles':("ROLE_APP_MANAGER, ROLE_DEVELOPER")],1)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574971645000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
