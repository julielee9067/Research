import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_homePage_peopleSearch_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/homePage/_peopleSearch.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
if(true && (searchPeople)) {
printHtmlPart(1)
for( person in (searchPeople) ) {
printHtmlPart(2)
expressionOut.print(createLink(controller: "profile", action: "displayImage", params:[user:person?.user?.id]))
printHtmlPart(3)
expressionOut.print(assetPath(src: "default-profile.png"))
printHtmlPart(4)
createTagBody(3, {->
expressionOut.print(person?.user?.getDisplayName())
})
invokeTag('link','g',10,['controller':("profile"),'params':([profile_id: person?.user?.id]),'action':("index")],3)
printHtmlPart(5)
createTagBody(3, {->
expressionOut.print(person?.user?.getDisplayName())
})
invokeTag('link','g',13,['controller':("profile"),'params':([profile_id: person?.user?.id]),'action':("index")],3)
printHtmlPart(6)
if(true && (person?.user?.tags && person?.settings?.showInterests)) {
printHtmlPart(7)
loop:{
int i = 0
for( tag in (person?.user?.tags) ) {
printHtmlPart(8)
createTagBody(5, {->
expressionOut.print(tag.name)
})
invokeTag('link','g',20,['controller':("homePage"),'action':("index"),'params':([tagInput: tag.name, source: 'homePage']),'class':("btn btn-sm btn-outline-primary")],5)
printHtmlPart(9)
i++
}
}
printHtmlPart(10)
}
printHtmlPart(11)
}
printHtmlPart(0)
}
else if(true && (userSearch)) {
printHtmlPart(12)
}
printHtmlPart(13)
if(true && (action != 'none')) {
printHtmlPart(14)
invokeTag('remotePaginate','util',39,['controller':(controller),'action':(action),'params':([userSearch: userSearch]),'total':(total?:0),'update':(div_id),'max':("10"),'maxsteps':("5")],-1)
printHtmlPart(15)
}
printHtmlPart(13)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574357834000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
