import ca.georgebrown.gbcresearch.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_settings_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/settings/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(settingsInstance?.code)
printHtmlPart(1)
expressionOut.print(settingsInstance?.description)
printHtmlPart(2)
if(true && (settingsInstance.type in [Setting.TYPE.INT,Setting.TYPE.LONG])) {
printHtmlPart(3)
invokeTag('field','g',16,['id':("value"),'type':("number"),'name':("value"),'value':(settingsInstance?.value)],-1)
printHtmlPart(4)
}
else if(true && (settingsInstance.type in [Setting.TYPE.DOUBLE])) {
printHtmlPart(3)
invokeTag('field','g',19,['id':("value"),'type':("double"),'name':("value"),'value':(settingsInstance?.value)],-1)
printHtmlPart(4)
}
else if(true && (settingsInstance.type==Setting.TYPE.DATE)) {
printHtmlPart(3)
invokeTag('datePicker','g',22,['id':("value"),'name':("value"),'relativeYears':([-2..2]),'precision':("day"),'value':(settingsInstance.value),'noSelection':(['':'none'])],-1)
printHtmlPart(4)
}
else if(true && (settingsInstance.type==Setting.TYPE.DATETIME)) {
printHtmlPart(3)
invokeTag('datePicker','g',25,['id':("value"),'name':("value"),'relativeYears':([-2..2]),'precision':("minute"),'value':(settingsInstance.value),'noSelection':(['':'none'])],-1)
printHtmlPart(4)
}
else if(true && (settingsInstance.type==Setting.TYPE.BOOLEAN)) {
printHtmlPart(3)
invokeTag('select','g',28,['id':("value"),'name':("value"),'from':(["true","false"]),'value':(settingsInstance.value)],-1)
printHtmlPart(4)
}
else if(true && (settingsInstance.type in [Setting.TYPE.STRING,Setting.TYPE.LIST])) {
printHtmlPart(5)
invokeTag('select','g',31,['id':("value"),'name':("value"),'from':(["SCROLL","PAGING"]),'value':(settingsInstance.value)],-1)
printHtmlPart(4)
}
printHtmlPart(6)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574272711000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
