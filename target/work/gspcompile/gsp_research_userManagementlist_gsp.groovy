import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_userManagementlist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/list.gsp" }
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
invokeTag('captureHead','sitemesh',11,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',13,['template':("/layouts/navigation"),'model':([which: 'userManagement'])],-1)
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
createClosureForHtmlPart(10, 3)
invokeTag('link','g',28,['action':("create"),'id':("addNew"),'class':("small text-muted")],3)
printHtmlPart(11)
})
invokeTag('form','g',30,['method':("post"),'action':("newUser")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(12)
expressionOut.print(firstName)
printHtmlPart(13)
expressionOut.print(lastName)
printHtmlPart(14)
expressionOut.print(username)
printHtmlPart(15)
})
invokeTag('form','g',43,['method':("post"),'action':("list")],2)
printHtmlPart(16)
loop:{
int i = 0
for( user in (userList) ) {
printHtmlPart(17)
expressionOut.print(user.username)
printHtmlPart(18)
expressionOut.print(user.firstName)
printHtmlPart(18)
expressionOut.print(user.lastName)
printHtmlPart(19)
expressionOut.print(user.email)
printHtmlPart(20)
expressionOut.print(user.email)
printHtmlPart(21)
expressionOut.print(user.enabled ? 'YES' : 'NO')
printHtmlPart(18)
expressionOut.print(user.authorities*.name.join(", "))
printHtmlPart(18)
createClosureForHtmlPart(22, 3)
invokeTag('link','g',68,['class':("userManagement btn btn-sm btn-outline-primary"),'action':("edit"),'id':(user.id)],3)
printHtmlPart(23)
i++
}
}
printHtmlPart(24)
invokeTag('paginate','g',76,['action':("list"),'total':(total),'update':("userList"),'next':("Forward"),'prev':("Back"),'max':("10"),'maxsteps':("5"),'controller':("userManagement"),'loadingHTML':("submissionLoader"),'params':([firstName: params.firstName ?: '', lastName: params.lastName ?: '', username: params.username ?: '', list: userList, div_id: 'submissionView', action: 'list', total: total])],-1)
printHtmlPart(25)
})
invokeTag('captureBody','sitemesh',83,[:],1)
printHtmlPart(26)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574273907000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
