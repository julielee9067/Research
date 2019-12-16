import ca.georgebrown.gbcresearch.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_layouts_banner_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/_banner.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
invokeTag('image','asset',78,['src':("gbcLogo2.png"),'title':("GBC Logo"),'width':("120"),'class':("img-fluid ml-5"),'style':("margin-right: -20px;")],-1)
printHtmlPart(1)
if(true && (appuser)) {
printHtmlPart(2)
createTagBody(2, {->
printHtmlPart(3)
createClosureForHtmlPart(4, 3)
invokeTag('link','g',98,['controller':("publisher"),'action':("list")],3)
printHtmlPart(5)
})
invokeTag('ifAnyGranted','sec',99,['roles':("ROLE_PUBLISHER")],2)
printHtmlPart(2)
createTagBody(2, {->
printHtmlPart(3)
createClosureForHtmlPart(6, 3)
invokeTag('link','g',101,['controller':("userManagement"),'action':("list")],3)
printHtmlPart(7)
})
invokeTag('ifAnyGranted','sec',102,['roles':("ROLE_USER_ADMIN")],2)
printHtmlPart(8)
createClosureForHtmlPart(9, 2)
invokeTag('link','g',103,['controller':("homePage"),'action':("index"),'params':([source: 'homePage'])],2)
printHtmlPart(10)
createClosureForHtmlPart(11, 2)
invokeTag('link','g',104,['controller':("submissions"),'action':("edit"),'params':(['createNew': 'true', source: 'homePage'])],2)
printHtmlPart(12)
createClosureForHtmlPart(13, 2)
invokeTag('link','g',108,['controller':("profile"),'params':([profile_id: appuser.id, source: 'profile']),'action':("index"),'style':("display: inline; margin-right: 3px;")],2)
printHtmlPart(14)
expressionOut.print(createLink(controller: "profile", action: "displayImage", params:[user:appuser.id]))
printHtmlPart(15)
expressionOut.print(assetPath(src: "default-profile.png"))
printHtmlPart(16)
createClosureForHtmlPart(17, 2)
invokeTag('link','g',110,['controller':("logout"),'class':("ml-5")],2)
printHtmlPart(18)
}
else if(true && (params.controller != 'login')) {
printHtmlPart(8)
createClosureForHtmlPart(19, 2)
invokeTag('link','g',113,['controller':("login"),'class':("ml-5")],2)
printHtmlPart(18)
}
printHtmlPart(20)
if(true && (params.controller == 'homePage' && params.action == 'index')) {
printHtmlPart(21)
}
printHtmlPart(22)
if(true && (appuser)) {
printHtmlPart(23)
invokeTag('render','g',135,['template':("/homePage/searchBox"),'model':([searchText: searchText, tagList: tagList, appuser: appuser, page: 'home', typesList: typesList, source: 'homePage'])],-1)
printHtmlPart(24)
}
printHtmlPart(25)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574960321000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
