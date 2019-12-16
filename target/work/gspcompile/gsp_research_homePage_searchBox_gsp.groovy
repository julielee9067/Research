import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_homePage_searchBox_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/homePage/_searchBox.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(tagList?.size() > 0 || typesList?.size() > 0)
printHtmlPart(1)
expressionOut.print(typesList.toString())
printHtmlPart(2)
expressionOut.print(typesList.toString())
printHtmlPart(3)
expressionOut.print(typesList.toString())
printHtmlPart(4)
expressionOut.print(tagList.toString())
printHtmlPart(2)
expressionOut.print(tagList.toString())
printHtmlPart(5)
expressionOut.print(tagList.toString())
printHtmlPart(6)
expressionOut.print(remoteFunction(controller: 'tags', action: 'ajaxInterests', params: '\'id=\' + escape(appuserId)',
                    onSuccess: 'populateInterests(data)'))
printHtmlPart(7)
createTagBody(1, {->
printHtmlPart(8)
expressionOut.print(searchText)
printHtmlPart(9)
invokeTag('actionSubmit','g',74,['value':("Search"),'controller':("homePage"),'action':("index"),'class':("btn btn-sm btn-primary w-100 h-100 rounded-0"),'name':("search"),'id':("search"),'style':("background-color: #005AA5; box-shadow: none;")],-1)
printHtmlPart(10)
})
invokeTag('form','g',74,['controller':("homePage"),'action':("index")],1)
printHtmlPart(11)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574367440000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
