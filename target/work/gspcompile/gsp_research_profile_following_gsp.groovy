import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_profile_following_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/profile/_following.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
if(true && (users)) {
printHtmlPart(0)
for( person in (users) ) {
printHtmlPart(1)
expressionOut.print(createLink(controller: "profile", action: "displayImage", params:[user:person?.user?.id]))
printHtmlPart(2)
expressionOut.print(assetPath(src: "default-profile.png"))
printHtmlPart(3)
createTagBody(3, {->
expressionOut.print(person?.user?.displayName)
})
invokeTag('link','g',8,['controller':("profile"),'params':([profile_id: person?.user?.id, source: "profile"]),'action':("index")],3)
printHtmlPart(4)
createTagBody(3, {->
expressionOut.print(person?.user?.displayName)
})
invokeTag('link','g',10,['controller':("profile"),'params':([profile_id: person?.user?.id, source: "profile"]),'action':("index")],3)
printHtmlPart(5)
if(true && (person?.user?.tags && person?.settings?.showInterests)) {
printHtmlPart(6)
loop:{
int i = 0
for( tag in (person?.user?.tags) ) {
printHtmlPart(7)
createTagBody(5, {->
expressionOut.print(tag.name)
})
invokeTag('link','g',16,['controller':("homePage"),'action':("index"),'params':([tagInput: tag.name, source: 'homePage']),'class':("btn btn-sm btn-outline-primary")],5)
printHtmlPart(8)
i++
}
}
printHtmlPart(9)
}
printHtmlPart(10)
}
printHtmlPart(11)
invokeTag('javascript','g',26,['plugin':("remote-pagination"),'library':("remoteNonStopPageScroll")],-1)
printHtmlPart(12)
invokeTag('remotePaginate','util',28,['controller':("profile"),'action':(action),'total':(total),'update':(div_id),'params':([profile_id: profile_id]),'max':("5"),'maxsteps':("5")],-1)
printHtmlPart(13)
}
printHtmlPart(14)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574357973000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
