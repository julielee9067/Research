import ca.georgebrown.gbcresearch.ScoreWeightingFactors
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_score_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/score/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(weightFactorInstance?.code)
printHtmlPart(1)
expressionOut.print(weightFactorInstance?.description)
printHtmlPart(2)
if(true && (weightFactorInstance.type in [ScoreWeightingFactors.TYPE.INT])) {
printHtmlPart(3)
invokeTag('field','g',16,['id':("value"),'type':("number"),'name':("value"),'value':(weightFactorInstance?.value)],-1)
printHtmlPart(4)
}
else if(true && (weightFactorInstance.type in [ScoreWeightingFactors.TYPE.DOUBLE])) {
printHtmlPart(3)
invokeTag('field','g',19,['id':("value"),'type':("double"),'name':("value"),'value':(weightFactorInstance?.value)],-1)
printHtmlPart(4)
}
printHtmlPart(5)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1571844916000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
