<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name='layout' content='main'/>
        <asset:stylesheet src="backgroundImage.css"/>
        <title>Research - Forgot Password</title>
    </head>

    <body>
        <div class="card w-75 my-5 text-center">
            <div class="card-header">
                <div class="card-title pageTitle m-0"><g:message code="spring.security.ui.forgotPassword.header"/></div>
            </div>
            <div class="card-body">
                <g:if test='${emailSent}'>
                    <div class="instruction my-2">
                        <g:message code='spring.security.ui.forgotPassword.sent'/>
                    </div>
                </g:if>
                <g:if test='${usesLdap}'>
                    <div class="instruction my-2">
                        <asset:image src="warning.png"/>
                        <span class="error">PASSWORD NOT RESET<br/><br/>Your account is accessed using your George Brown College account. Use your GBC id/pwd to login.</span><br/>
                        <br/>If you need to reset your GBC password, go to <a href="https://service.georgebrown.ca">https://service.georgebrown.ca</a>.
                        <br/><br/>
                        <g:link controller="login">Login using my GBC Account</g:link>
                    </div>
                </g:if>
                <g:else>
                    <div class="instruction my-2">
                        <g:message code='spring.security.ui.forgotPassword.description'/>
                        <g:if test="${emailNotRegistered}">
                            <p style="color: red">* We can't find your email address on our system. Please try again. </p>
                        </g:if>
                    </div>
                    <g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>
                    <div class="form-group">
                        <label for="username" id="usernameLabel" class="mr-5"><g:message code='spring.security.ui.forgotPassword.username'/> </label>
                        <input class="input-lg w-50"  type="email" id="username" name="username">
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn my-3 mx-auto" style="background-color: #005AA5; color: white" id="submitButton">Reset my Password</button>
                    </div>
                        <div class="text-center">
                            <span class="notice">This is only for users who do not log in with a George Brown College id.<br/>To reset your GBC account password go to <a href="https://service.georgebrown.ca">https://service.georgebrown.ca</a>.</span>
                        </div>
                        </g:form>
                 </g:else>
            </div>
            <br/>
        </div>
    </body>
</html>