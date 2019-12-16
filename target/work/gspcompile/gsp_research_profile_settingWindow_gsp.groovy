import ca.georgebrown.gbcresearch.security.Appuser
import  ca.georgebrown.gbcresearch.Tag
import  ca.georgebrown.gbcresearch.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_research_profile_settingWindow_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/profile/_settingWindow.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(appuser.id)
printHtmlPart(1)
expressionOut.print(remoteFunction(controller: 'tags', action: 'ajaxInterestsName', params: '\'id=\' + escape(appuserId)'))
printHtmlPart(2)
expressionOut.print(returnController == 'profile')
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
expressionOut.print(grailsApplication.config.file.profilePhoto.directory)
printHtmlPart(5)
expressionOut.print(appuser?.id)
printHtmlPart(6)
expressionOut.print(returnController)
printHtmlPart(7)
expressionOut.print(returnAction)
printHtmlPart(8)
invokeTag('checkBox','g',91,['id':("showEmailCheck"),'name':("showEmail"),'value':(settings?.showEmail)],-1)
printHtmlPart(9)
invokeTag('checkBox','g',92,['id':("showInterestsCheck"),'name':("showInterests"),'value':(settings?.showInterests)],-1)
printHtmlPart(10)
invokeTag('checkBox','g',93,['id':("showRecentlyViewedCheck"),'name':("showRecentlyViewed"),'value':(settings?.showRecentlyViewed)],-1)
printHtmlPart(11)
if(true && (setting.get(code: Setting.CODE.ADDITIONAL_NOTIFICATIONS) == 'true')) {
printHtmlPart(12)
invokeTag('checkBox','g',96,['id':("notifyInterestedCheck"),'name':("notifyInterestedPost"),'value':(settings?.notifyInterestedPost)],-1)
printHtmlPart(13)
invokeTag('checkBox','g',97,['id':("notifyFollowedUserCheck"),'name':("notifyFollowedUserPost"),'value':(settings?.notifyFollowedUserPost)],-1)
printHtmlPart(14)
}
printHtmlPart(15)
invokeTag('checkBox','g',99,['id':("notifyDailyDigestCheck"),'name':("notifyDailyDigest"),'value':(settings?.notifyDailyDigest)],-1)
printHtmlPart(16)
invokeTag('select','g',102,['name':("scrollSetting"),'from':(scrollSettingsList),'optionKey':("key"),'optionValue':("value"),'value':(settings?.scrollSetting),'class':("form-control"),'style':("width: 90%;box-shadow: none;")],-1)
printHtmlPart(17)
createClosureForHtmlPart(18, 2)
invokeTag('select','g',113,['from':(Tag.findAllByStatus('Active')),'optionKey':("id"),'optionValue':("name"),'multiple':("multiple"),'id':("tagInterest"),'value':(appuser?.tags),'name':("tagsInterest"),'class':("form-control editField tagSelect"),'aria-describedby':("tagHelp")],2)
printHtmlPart(19)
invokeTag('textArea','g',119,['id':("newBio"),'name':("newBiography"),'value':(biography)],-1)
printHtmlPart(20)
invokeTag('submitButton','g',121,['id':("updateProfileBtn"),'name':("updateProfileBtn"),'value':("Update"),'class':("btn btn-success hidden")],-1)
printHtmlPart(21)
})
invokeTag('form','g',121,['name':("settings"),'url':([controller: 'profile', action: 'updateProfile']),'method':("POST"),'controller':("uploadFile"),'action':("upload"),'enctype':("multipart/form-data")],1)
printHtmlPart(22)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574263369000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
