import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_homePagesearch_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/homePage/search.gsp" }
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
invokeTag('captureTitle','sitemesh',11,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',12,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',12,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
invokeTag('render','g',85,['template':("/layouts/navigation"),'model':("")],-1)
printHtmlPart(7)
invokeTag('render','g',90,['template':("searchBox"),'model':([searchText: searchText, tagList: tagList, appuser: appuser, page: 'search', typeList: typeList])],-1)
printHtmlPart(8)
if(true && (resultList.size()!=0)) {
printHtmlPart(9)
expressionOut.print(resultList.size())
printHtmlPart(10)
for( result in (resultList) ) {
printHtmlPart(11)
expressionOut.print(result?.title)
printHtmlPart(12)
expressionOut.print(result?.domain)
printHtmlPart(13)
expressionOut.print(result?.description)
printHtmlPart(14)
createTagBody(4, {->
printHtmlPart(15)
expressionOut.print(result?.owner?.displayName)
printHtmlPart(15)
})
invokeTag('link','g',111,['controller':("profile"),'params':([profile_id: result?.owner?.id, source: 'profile']),'action':("index")],4)
printHtmlPart(16)
expressionOut.print(result?.tag_arr)
printHtmlPart(12)
createClosureForHtmlPart(17, 4)
invokeTag('link','g',113,['params':(['type': 'tbd', 'id': result.id, source: 'homePage']),'controller':("homePage"),'action':("linkToPage")],4)
printHtmlPart(18)
}
printHtmlPart(19)
}
else {
printHtmlPart(20)
}
printHtmlPart(21)
})
invokeTag('captureBody','sitemesh',120,[:],1)
printHtmlPart(22)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574271636000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
