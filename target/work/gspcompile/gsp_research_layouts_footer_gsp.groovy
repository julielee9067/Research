import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_layouts_footer_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/_footer.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
invokeTag('image','asset',10,['src':("gbcLogo2.png"),'title':("GBC Footer"),'class':("img-fluid"),'width':("150")],-1)
printHtmlPart(1)
if((grails.util.Environment.current.name == 'production') && true) {
printHtmlPart(2)
}
else {
printHtmlPart(3)
createClosureForHtmlPart(4, 2)
invokeTag('ifLoggedIn','sec',33,[:],2)
printHtmlPart(2)
}
printHtmlPart(5)
if((grails.util.Environment.current.name == 'production') && true) {
printHtmlPart(6)
invokeTag('render','g',37,['template':("/version")],-1)
printHtmlPart(7)
}
else {
printHtmlPart(8)
invokeTag('render','g',45,['template':("/version")],-1)
printHtmlPart(9)
expressionOut.print(grailsApplication.config.dataSource.url)
printHtmlPart(10)
invokeTag('render','g',47,['template':("/debug/sessionEmailForm")],-1)
printHtmlPart(11)
}
printHtmlPart(12)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574271727000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
