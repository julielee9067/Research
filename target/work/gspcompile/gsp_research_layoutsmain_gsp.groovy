import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_layoutsmain_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/main.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',9,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',10,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("X-UA-Compatible"),'content':("IE=edge,chrome=1")],-1)
printHtmlPart(1)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('layoutTitle','g',11,['default':("Grails")],-1)
})
invokeTag('captureTitle','sitemesh',11,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',11,[:],2)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',13,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("viewport"),'content':("width=device-width, initial-scale=1, shrink-to-fit=no")],-1)
printHtmlPart(3)
expressionOut.print(assetPath(src: 'favicon.ico'))
printHtmlPart(4)
expressionOut.print(assetPath(src: 'apple-touch-icon.png'))
printHtmlPart(5)
expressionOut.print(assetPath(src: 'apple-touch-icon-retina.png'))
printHtmlPart(6)
invokeTag('stylesheet','asset',16,['src':("application.css")],-1)
printHtmlPart(1)
invokeTag('stylesheet','asset',16,['src':("jquery-ui.css")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',17,['src':("application.js")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',18,['src':("bootbox.min.js")],-1)
printHtmlPart(7)
invokeTag('javascript','asset',23,['src':("js/util.js")],-1)
printHtmlPart(8)
invokeTag('stylesheet','asset',23,['src':("css/bootstrap.min.css")],-1)
printHtmlPart(8)
invokeTag('javascript','asset',24,['src':("js/bootstrap.min.js")],-1)
printHtmlPart(8)
invokeTag('javascript','asset',25,['src':("jquery.are-you-sure.js")],-1)
printHtmlPart(1)
invokeTag('javascript','asset',26,['src':("tableHeadFixer.js")],-1)
printHtmlPart(9)
invokeTag('layoutHead','g',29,[:],-1)
printHtmlPart(10)
invokeTag('stylesheet','asset',990,['src':("research-portal.css")],-1)
printHtmlPart(11)
})
invokeTag('captureHead','sitemesh',992,[:],1)
printHtmlPart(11)
createTagBody(1, {->
printHtmlPart(12)
invokeTag('render','g',994,['template':("/layouts/banner")],-1)
printHtmlPart(13)
invokeTag('layoutBody','g',996,[:],-1)
printHtmlPart(14)
invokeTag('render','g',999,['template':("/layouts/footer")],-1)
printHtmlPart(15)
})
invokeTag('captureBody','sitemesh',1000,[:],1)
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1576005852000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
