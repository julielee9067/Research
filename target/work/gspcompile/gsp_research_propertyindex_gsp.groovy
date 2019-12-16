import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_propertyindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/property/index.gsp" }
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
printHtmlPart(2)
createTagBody(2, {->
printHtmlPart(4)
if(true && (result?.errorMsg)) {
printHtmlPart(5)
}
else if(true && (result?.success)) {
printHtmlPart(6)
}
printHtmlPart(7)
expressionOut.print(result?.id)
printHtmlPart(8)
})
invokeTag('javascript','g',29,[:],2)
printHtmlPart(9)
})
invokeTag('captureHead','sitemesh',30,[:],1)
printHtmlPart(10)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',35,['template':("/layouts/navigation"),'model':([which: 'manage'])],-1)
printHtmlPart(11)
createTagBody(2, {->
printHtmlPart(12)
for( entry in (list) ) {
printHtmlPart(13)
expressionOut.print(entry.id)
printHtmlPart(14)
expressionOut.print(entry.name)
printHtmlPart(15)
invokeTag('checkBox','g',66,['name':("enabled.${entry.id}"),'value':(entry.enabled),'id':("enabled_${entry.id}")],-1)
printHtmlPart(16)
expressionOut.print(entry.id)
printHtmlPart(17)
}
printHtmlPart(18)
})
invokeTag('form','g',74,['method':("post")],2)
printHtmlPart(19)
})
invokeTag('captureBody','sitemesh',76,[:],1)
printHtmlPart(20)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272586000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
