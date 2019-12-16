import ca.georgebrown.gbcresearch.ScoreWeightingFactors
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_scorelist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/score/list.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',6,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',7,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',7,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',13,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',15,['template':("/layouts/navigation"),'model':([which: 'weightingFactors'])],-1)
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
loop:{
int i = 0
for( weightFactorInstance in (weightFactorInstanceList.sort { [it.seq] }) ) {
printHtmlPart(9)
if(true && (weightFactorInstance.code != ScoreWeightingFactors.CODE.END_USER_ID)) {
printHtmlPart(10)
createTagBody(4, {->
expressionOut.print(fieldValue(bean: weightFactorInstance, field: "code"))
})
invokeTag('link','g',39,['class':("edit"),'action':("edit"),'id':(weightFactorInstance.id)],4)
printHtmlPart(11)
expressionOut.print(fieldValue(bean: weightFactorInstance, field: "description"))
printHtmlPart(12)
expressionOut.print(fieldValue(bean: weightFactorInstance, field: "type"))
printHtmlPart(12)
expressionOut.print(fieldValue(bean: weightFactorInstance, field: "value"))
printHtmlPart(13)
}
printHtmlPart(14)
i++
}
}
printHtmlPart(15)
})
invokeTag('captureBody','sitemesh',52,[:],1)
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272698000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
