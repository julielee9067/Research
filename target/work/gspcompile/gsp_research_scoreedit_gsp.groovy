import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_scoreedit_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/score/edit.gsp" }
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
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',6,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',9,['template':("/layouts/navigation"),'model':([which: 'weightingFactors'])],-1)
printHtmlPart(5)
expressionOut.print(weightFactorInstance.code)
printHtmlPart(6)
if(true && (flash.message)) {
printHtmlPart(7)
expressionOut.print(flash.message)
printHtmlPart(8)
}
printHtmlPart(9)
createTagBody(2, {->
printHtmlPart(10)
createTagBody(3, {->
printHtmlPart(11)
if(true && (error in org.springframework.validation.FieldError)) {
printHtmlPart(12)
expressionOut.print(error.field)
printHtmlPart(13)
}
printHtmlPart(14)
invokeTag('message','g',23,['error':(error)],-1)
printHtmlPart(15)
})
invokeTag('eachError','g',24,['bean':(weightFactorInstance),'var':("error")],3)
printHtmlPart(16)
})
invokeTag('hasErrors','g',26,['bean':(weightFactorInstance)],2)
printHtmlPart(9)
createTagBody(2, {->
printHtmlPart(17)
invokeTag('hiddenField','g',28,['name':("id"),'value':(weightFactorInstance?.id)],-1)
printHtmlPart(17)
invokeTag('hiddenField','g',29,['name':("version"),'value':(weightFactorInstance?.version)],-1)
printHtmlPart(18)
invokeTag('render','g',31,['template':("form")],-1)
printHtmlPart(19)
invokeTag('actionSubmit','g',34,['class':("save btn btn-primary"),'action':("update"),'value':(message(code: 'default.button.update.label', default: 'Update'))],-1)
printHtmlPart(20)
})
invokeTag('form','g',36,['method':("post")],2)
printHtmlPart(21)
})
invokeTag('captureBody','sitemesh',40,[:],1)
printHtmlPart(22)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272643000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
