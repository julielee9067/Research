package ca.georgebrown.gbcresearch

import ca.georgebrown.gbcresearch.email.EmailDef
import ca.georgebrown.gbcresearch.security.Appuser
import ca.georgebrown.gbcresearch.security.RegistrationCode
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.util.Environment
import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.WebAttributes
import javax.servlet.http.HttpServletResponse


@Secured('permitAll')
class LoginController {

    def mailerService
    def securityService
    def authenticationTrustResolver
    def springSecurityService

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    def auth() {
        if (Environment.current != Environment.PRODUCTION) {
            def sessionEmail = session.getAttribute('sessionEmail')

            if (!sessionEmail) {
                session.setAttribute('sessionEmail', grailsApplication.config.grails.mail.overrideAddress)
            }
        }

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
           // redirect uri: config.successHandler.defaultTargetUrl
            redirect uri: '/loginSuccess'
            return
        }

        String view = grailsApplication.config.template.authPage?: 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl            : postUrl,
                                   rememberMeParameter: config.rememberMe.parameter]
    }

    def authAjax() {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    def denied() {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SecurityContextHolder.context?.authentication)) {
            println "auth - denied, redirect action 'full'"
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }

    }

    def full() {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SecurityContextHolder.context?.authentication),
                        postUrl  : "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    def authfail() {
        println "auth - authfail"
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]

        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "springSecurity.errors.login.fail")
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    def ajaxSuccess() {
        println "auth ajaxSuccess"
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    def ajaxDenied() {
        println "auth ajaxDenied"
        render([error: 'access denied'] as JSON)
    }

    def forgotPassword () {
        def usesLdap = false
        def emailNotRegistered = false

        if (!request.post) {
            // show the form
            return
        }

        def emailSent = false
        String username = params.username

        if (!username) {//if the user doesn't exist, do nothing
            flash.error = message(code: 'spring.security.ui.forgotPassword.username.missing')
            redirect action: 'forgotPassword'
            return
        }

        def user = Appuser.findByEmailIlike(username)

        if (!user) {//if the user doesn't exist
            flash.error = message(code: 'spring.security.ui.forgotPassword.user.notFound')
            emailNotRegistered = true
        }
        else {
            if (user.authenticationType == Appuser.AUTHENTICATION_TYPE.LDAP) {
               usesLdap = true
            }
            else {
                mailerService.sendEmailUsingTemplate(EmailDef.TEMPLATE_CODE.PASSWORD_RESET, user.email, [appuser: user, url: securityService.resetPasswordLink(user)])
                emailSent = true
            }
        }
        [emailSent: emailSent, usesLdap: usesLdap, emailNotRegistered: emailNotRegistered]
    }

    def resetPassword(ResetPasswordCommand command) {
        String token = params.t
        def registrationCode = token ? RegistrationCode.findByToken(token) : null

        if (!registrationCode) {
            flash.error = 'There was a problem resetting your password.'
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            return
        }

        def user = Appuser.findByUsername(registrationCode.username)

        if (!request.post) {
            return [token: token, command: new ResetPasswordCommand(), user:user]
        }

        command.username = registrationCode.username
        command.validate()

        if (command.hasErrors()) {
            return [token: token, command: command, user:user]
        }

   //     String salt = saltSource instanceof NullSaltSource ? null : registrationCode.username
        RegistrationCode.withTransaction { status ->
            user.password = command.password //springSecurityService.encodePassword(command.password, salt)
            user.save(flush: true)
            registrationCode.delete()
        }

        springSecurityService.reauthenticate registrationCode.username
        flash.message ='Password has been successfully reset'

        def conf = SpringSecurityUtils.securityConfig
        redirect controller: 'loginSuccess', action: 'index'
    }

    static boolean checkPasswordRegex(String password, def command) {
        String passValidationRegex ='^.*(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$'
        password && password.matches(passValidationRegex)
    }

    static boolean checkPasswordMinLength(String password, command) {
        int minLength =  8
        password && password.length() >= minLength
    }

    static boolean checkPasswordMaxLength(String password, command) {
        int maxLength =  64
        password && password.length() <= maxLength
    }

    static myPasswordValidator =  {String password, command ->
        if (command.username && command.username.equals(password)) {
            return 'command.password.error.username'
        }
        if (!checkPasswordMinLength(password, command) ||
                !checkPasswordMaxLength(password, command)) {
            return 'registerCommand.password.sizenotmet'
        }
        if (!checkPasswordRegex(password, command)) {
            return 'command.password.error.strength'
        }
    }

    static final password2Validator = { value, command ->
        if (command.password != command.password2) {
            return 'command.password2.error.mismatch'
        }
    }
}

class ResetPasswordCommand  {
    String username
    String password
    String password2

    static constraints = {
        username nullable: false
        password blank: false, nullable: false, validator: LoginController.myPasswordValidator
        password2 validator: LoginController.password2Validator
    }
}
