import ca.georgebrown.gbcresearch.Tag
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_tagstagsList_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/tags/tagsList.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',11,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',11,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',12,[:],2)
printHtmlPart(4)
createTagBody(2, {->
printHtmlPart(5)
if(true && (result.message)) {
printHtmlPart(6)
expressionOut.print(result.message)
printHtmlPart(7)
}
printHtmlPart(8)
})
invokeTag('javascript','g',97,[:],2)
printHtmlPart(9)
})
invokeTag('captureHead','sitemesh',97,[:],1)
printHtmlPart(10)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('render','g',103,['template':("/layouts/navigation"),'model':([which: 'tags'])],-1)
printHtmlPart(11)
createTagBody(2, {->
printHtmlPart(12)
if(true && (flash.message)) {
printHtmlPart(13)
expressionOut.print(flash.message)
printHtmlPart(14)
}
printHtmlPart(15)
expressionOut.print(tagName)
printHtmlPart(16)
if(true && (activeList?.size())) {
printHtmlPart(17)
if(true && ("${activeList?.first()?.respondsTo("getUsageCnt")} == 'true'")) {
printHtmlPart(18)
}
printHtmlPart(19)
}
printHtmlPart(20)
for( tag in (inactiveList) ) {
printHtmlPart(21)
expressionOut.print(tag.id)
printHtmlPart(22)
expressionOut.print(tag.name)
printHtmlPart(23)
expressionOut.print(tag.id)
printHtmlPart(24)
expressionOut.print(tag.id)
printHtmlPart(25)
if(true && (tag.status == Tag.STATUS.ACTIVE)) {
printHtmlPart(26)
}
printHtmlPart(27)
if(true && (tag.respondsTo("getUsageCnt"))) {
printHtmlPart(28)
expressionOut.print(tag.usageCnt)
printHtmlPart(29)
}
printHtmlPart(17)
if(true && (inactiveList?.size())) {
printHtmlPart(30)
if(true && ("${inactiveList?.first()?.respondsTo("getUsageCnt")} == 'true'")) {
printHtmlPart(31)
expressionOut.print(tag.id)
printHtmlPart(32)
expressionOut.print(tag.usageCnt)
printHtmlPart(33)
}
printHtmlPart(17)
}
else {
printHtmlPart(34)
expressionOut.print(tag.id)
printHtmlPart(35)
}
printHtmlPart(36)
expressionOut.print(tag.id)
printHtmlPart(37)
expressionOut.print(tag.id)
printHtmlPart(38)
}
printHtmlPart(39)
for( tag in (activeList) ) {
printHtmlPart(21)
expressionOut.print(tag.id)
printHtmlPart(22)
expressionOut.print(tag.name)
printHtmlPart(23)
expressionOut.print(tag.id)
printHtmlPart(24)
expressionOut.print(tag.id)
printHtmlPart(25)
if(true && (tag.status == Tag.STATUS.ACTIVE)) {
printHtmlPart(26)
}
printHtmlPart(27)
if(true && (tag.respondsTo("getUsageCnt"))) {
printHtmlPart(28)
expressionOut.print(tag.usageCnt)
printHtmlPart(29)
}
printHtmlPart(17)
if(true && (activeList?.size())) {
printHtmlPart(30)
if(true && ("${activeList?.first()?.respondsTo("getUsageCnt")} == 'true'")) {
printHtmlPart(31)
expressionOut.print(tag.id)
printHtmlPart(32)
expressionOut.print(tag.usageCnt)
printHtmlPart(33)
}
printHtmlPart(17)
}
else {
printHtmlPart(34)
expressionOut.print(tag.id)
printHtmlPart(35)
}
printHtmlPart(36)
expressionOut.print(tag.id)
printHtmlPart(37)
expressionOut.print(tag.id)
printHtmlPart(38)
}
printHtmlPart(40)
})
invokeTag('form','g',190,['method':("post"),'action':("tagsList")],2)
printHtmlPart(41)
})
invokeTag('captureBody','sitemesh',192,[:],1)
printHtmlPart(42)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574273711000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
