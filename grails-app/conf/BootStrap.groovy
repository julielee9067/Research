import ca.georgebrown.gbcresearch.BootstrapHelper
import ca.georgebrown.gbcresearch.FileContentType
import ca.georgebrown.gbcresearch.ScoreWeightingFactors
import ca.georgebrown.gbcresearch.Setting

import ca.georgebrown.gbcresearch.email.EmailDef
import ca.georgebrown.gbcresearch.email.EmailDefTemplate
import ca.georgebrown.gbcresearch.email.EmailTemplate

import ca.georgebrown.gbcresearch.SubmissionType

import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.AppuserRole
import ca.georgebrown.gbcresearch.security.Role


import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject
import grails.util.Environment
import java.text.SimpleDateFormat

class BootStrap {
    static final DATA_LOAD_MODE = [CREATE: 'create', UPDATE: 'update']
    static final DATEVAL = "DATEVAL~"
    def grailsApplication
    def dataSource

    def init = { servletContext ->
        createRoles()
        createAdminUser()
        loadJsonDomainData()
        createEmails()

    }
    def destroy = {
    }

    def createRoles() {
        def roleList = [
                'ROLE_USER_ADMIN':'User Admin',
                'ROLE_APP_MANAGER':'Application Manager',
                'ROLE_DEVELOPER': 'Developer',
                'ROLE_EMPLOYEE':'Employee',
                'ROLE_PUBLISHER':'Publisher'
        ]
        roleList.each() { authority, name ->
            Role approle = Role.findByAuthority(authority)
            if (!approle) {
                approle = new Role(authority: authority, name:name)
                approle.save(flush:true)
            }
        }
    }
    def createAdminUser() {

        Appuser user = Appuser.findByUsername('admin')
        if (!user) {
            Role approle = Role.findByAuthority("ROLE_USER_ADMIN")
            user = new Appuser(username: 'admin', password: 'admin', authenticationType: Appuser.AUTHENTICATION_TYPE.DAO, firstName: 'Admin', lastName: 'User')
            user.save(flush: true, failOnError: true)
            AppuserRole.create user, approle
        }

            Appuser fred = Appuser.findByUsername('Fred')
            fred.password = 'fred'
            fred.save(flush: true, failOnError: true)


    }


    def loadJsonDomainData() {
        loadJsonDataToDomain("ca.georgebrown.gbcresearch",'settings', Setting)
        loadJsonDataToDomain("ca.georgebrown.gbcresearch",'fileContentType', FileContentType, 'name')
        loadJsonDataToDomain("ca.georgebrown.gbcresearch",'weightingFactors', ScoreWeightingFactors)

        /* Lists */
        loadJsonDataToDomain("ca.georgebrown.gbcresearch",'lists/submissionType', SubmissionType, 'name')
         /* end of lists*/



    }

    def	loadJsonDataToDomain(String packageName, String jsonFileName, Class clazz, String key = 'code') {
        def obj = null
        def applicationContext = grailsApplication.mainContext
        String basePath = applicationContext.getResource("/").getFile().toString()
        String jsonText  = new File("${basePath}/bootstrapData/${jsonFileName}.json").text
        JSONObject jsonObj = JSON.parse(jsonText)
        jsonObj.each() {String keyValue,v->
            def k = loadConvert(keyValue)
            def mode = v.mode?:DATA_LOAD_MODE.CREATE
            obj = clazz."findBy${key.capitalize()}"(k)
            if (!obj) {
                obj = clazz.newInstance("${key}":k)
            } else {
                if (mode == DATA_LOAD_MODE.CREATE) return
            }

            def properties = [:]
            v.properties.each() { field , val ->
                val = loadConvert(val)
                properties.put(field,val)
                if (val instanceof org.codehaus.groovy.grails.web.json.JSONArray) {
                    properties[field] = BootstrapHelper.joinJSONArray(val,"")
                }
            }
            obj.properties = properties
            if (v?.domains) {
                v.domains.each() { domainField->
                    def instance =  grailsApplication.getDomainClass("${packageName}.${domainField.class}").clazz."findBy${domainField.key}"(domainField.value)
                    obj[domainField.field] = instance
                }
            }
            obj.save(flush: true, failOnError: true)
            if (v?.hasMany && mode == DATA_LOAD_MODE.UPDATE) {
                v.hasMany.each() { hasMany ->
                    hasMany.each() { manyProperty, val ->
                        if (obj."${manyProperty}") obj."${manyProperty}".clear()
                        val.list.each() { keyVal ->
                            def manyObj = grailsApplication.getDomainClass("${packageName}.${val.domain}").clazz."findBy${val.key.capitalize()}"(keyVal)
                            if (!manyObj) {
                                println "keyVal = ${keyVal}, val.domain=${val.domain}, package=${packageName}, val.key=${val.key}"
                            }
                            obj."addTo${manyProperty.capitalize()}"(manyObj)
                        }
                    }
                }
                obj.save(flush: true, failOnError: true)
            }
        }

    }
    def loadConvert(def val) {
        if (val instanceof String) val = val.trim()
        if (val instanceof String && val.startsWith(DATEVAL)) {
            def dsplit = val.split("~")
            val = new SimpleDateFormat("yyyy-MM-dd").parse(dsplit[1])
        }
        return val

    }

    def getPathList(String dir, String ext = '') {
        def applicationContext = grailsApplication.mainContext
        String basePath = applicationContext.getResource("/").getFile().toString()
        dir = "${basePath}/bootstrapData/${dir}/"
        def pathList = []

        new File(dir).eachFileMatch(~/.*${ext}/) { file ->
            pathList.add(file.getAbsolutePath())
        }
        return pathList
    }



    def createEmails() {
        createEmailTemplates()
        createEmailDefs()
    }


    def createEmailTemplates() {
        def txtFiles = getPathList('emailTemplates','.template')
        txtFiles.each() { String path ->
            def data = BootstrapHelper.loadEmailTemplate(path)
            def template = EmailTemplate.findByCode(data.code)
            if (!template) {
                template = new EmailTemplate(code:data.code, description: data.description,template:data.template).save(flush:true)
            }

            if (Environment.current != Environment.PRODUCTION && data.mode == 'update') {
                template.description = data.description
                template.template = data.template

                template.save(flush:true)
            }
        }
    }

    def createEmailDefs() {

        def txtFiles = getPathList('emailTemplates','.def')
        txtFiles.each() { String path ->
            def data = BootstrapHelper.loadEmailDef(path)
            def emailDef = EmailDef.findByCode(data.code)
            if (!emailDef) {
                emailDef = new EmailDef(code:data.code, ccList:data?.ccList, bccList: data?.bccList,
                        description: data.description, subject: data.subject,
                        sampleJsonData:data.sampleJsonData).save(flush:true)
            }
            if (Environment.current != Environment.PRODUCTION && data.mode == 'update') {
                emailDef.ccList = data?.ccList
                emailDef.bccList = data?.bccList
                emailDef.description = data.description
                emailDef.subject = data.subject
                emailDef.sampleJsonData = data?.sampleJsonData
                emailDef.templates?.clear()
                emailDef.save(flush:true)
            }
            emailDefSetTemplates(data.code,data.templates)
        }
    }

    def emailDefSetTemplates(String emailDefCode, def templateCodes) {
        int seq = 10
        EmailDef emailDef = EmailDef.findByCode(emailDefCode)
        templateCodes.each() { String code->
            EmailTemplate template = EmailTemplate.findByCode(code.trim())
            EmailDefTemplate edf = EmailDefTemplate.findByEmailDefAndEmailTemplate(emailDef, template)
            if (!edf) {
                edf = new EmailDefTemplate(emailDef: emailDef, emailTemplate: template)
            }
            edf.sortOrder = seq
            edf.save(flush:true)
            seq+=10
        }
    }

 }
