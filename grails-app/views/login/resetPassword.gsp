<%--
  Created by IntelliJ IDEA.
  User: staff
  Date: 2017-11-27
  Time: 4:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title><g:message code='spring.security.ui.resetPassword.title'/></title>
        <meta name='layout' content='main'/>
        <asset:stylesheet src="security-ui.css"/>
        <asset:stylesheet src="resetPassword.css"/>
        <asset:stylesheet src="backgroundImage.css"/>
    </head>
    <body>
        <div class="card w-75 my-5 text-center">
        <g:form action='resetPassword' name='resetPasswordForm' autocomplete='off' onsubmit="return validateForm()">
            <g:hiddenField name='t' value='${token}'/>
            <div class="card-header">
                <div class="card-title pageTitle m-0">Set New Password</div>
            </div>
            <div class="card-body">
                <div class="h6"><span class="text-info">${user?.displayName}</span>, please enter your new password.</div>
                <div class="mx-auto my-4">
                    <div class="form-group">
                        <label class="col-lg-3 col-xl-4 mb-2 text-lg-right" for="password">Password</label>
                        <input type="password" class="col-7  mb-2" id="password" placeholder="Password" name="password" value="${command?.password}">
                        <div class="fun">
                            <span id="passwordCheck" hidden>
                                <div class="checkAndX">
                                    <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                                        <circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none"/><path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/></svg>
                                </div>
                            </span>
                            <span id="passwordX" hidden>
                                <div class="checkAndX">
                                    <svg class="checkmark_X" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                                        <circle class="checkmark__circle_X" cx="26" cy="26" r="25" fill="none" /><path class="checkmark__check_X" fill="none" d="M16 16 36 36 M36 16 16 36" /></svg>
                                </div>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 col-xl-4 mb-2 text-lg-right" for="passwordConfirm">Confirm Password</label>
                        <input type="password" class="col-7 mb-2" id="passwordConfirm" placeholder="Confirm Password" name="password2" value="${command?.password2}">
                        <div class="fun">
                            <span id="confirmPasswordCheck" hidden>
                                <div class="checkAndX">
                                    <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                                        <circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none"/><path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/></svg>
                                </div>
                            </span>
                            <span id="confirmPasswordX" hidden>
                                <div class="checkAndX">
                                    <svg class="checkmark_X" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                                        <circle class="checkmark__circle_X" cx="26" cy="26" r="25" fill="none" /><path class="checkmark__check_X" fill="none" d="M16 16 36 36 M36 16 16 36" /></svg>
                                </div>
                            </span>
                            <span class="warningMsg" id="confirmPasswordError">${message (error:(command.errors.getFieldError('password2')))}</span>
                        </div>
                    </div>
                </div>
                <div class="submitButton">
                    <button type="submit" class="btn btn-primary">Reset Password</button>
                </div>
            </div>

            <div class="card-footer">
                <div class="text-danger font-weight-bold" id="passwordError">${message (error:(command.errors.getFieldError('password')))}</div>
                <div id="pswd_info">
                    <div class="h6">Password must meet the following requirements:</div>
                    <ul class="w-75 mx-auto">
                        <li id="letter" class="invalid mb-1">At least <strong>one lowercase letter</strong></li>
                        <li id="capital" class="invalid mb-1">At least <strong>one capital letter</strong></li>
                        <li id="number" class="invalid mb-1">At least <strong>one number</strong></li>
                        <li id="length" class="invalid mb-1">Be at least <strong>8 characters or more</strong></li>
                    </ul>
                </div>
            </div>
        </g:form>
        </div>

        <script>
            $(document).ready(function(){
                $("#password").on({
                    focus: function(){
                        $("#passwordCheck").attr("hidden",true);
                        $("#passwordX").attr("hidden",true);
                        $("#passwordError").empty();
                        $("#confirmPasswordError").empty();
                    },
                    blur: function(){
                            if (validatePassword()==="valid"){//if password is correct
                                $("#passwordCheck").attr("hidden",false);
                            }
                            else if (validatePassword()==="empty"){//if empty do nothing
                            }
                            else{//not correct
                                $("#passwordX").attr("hidden",false);
                            }
                            confirmPassword();
                    },
                    keyup: function(){
                        //check all conditions
                        validatePassword()
                    }
                });
                $("#passwordConfirm").on({
                    focus: function(){
                        $("#confirmPasswordCheck").attr("hidden",true);
                        $("#confirmPasswordX").attr("hidden",true);
                        $("#confirmPasswordError").empty();
                    },
                    blur: function(){
                        confirmPassword();
                    },
                    keyup: function(){
                        var state=confirmPassword();
                        if (state==='valid'){
                            $("#confirmPasswordCheck").attr("hidden",false);

                        }
                        else {
                            $("#confirmPasswordCheck").attr("hidden",true);
                            $("#confirmPasswordX").attr("hidden",true);
                        }
                    }
                });
            });

            function validateForm(){
                var isValid=true;
                var errorMsg=null;
                var passwordState=validatePassword();
                var confirmPasswordState=confirmPassword();
                //check the password first
                if (passwordState==='invalid'){
                    isValid=false;
                    errorMsg='The password is invalid';
                }
                else if (passwordState==='empty'){
                    isValid=false;
                    errorMsg='The password cannot be empty';
                }
                if (errorMsg)//if error message exists
                {
                    $("#passwordX").attr("hidden",false);
                    $("#passwordError").text(errorMsg);
                    errorMsg=null;
                }

                //check the confirm password
                if (confirmPasswordState==='invalid'){
                    isValid=false;
                    errorMsg='The passwords do not match';
                }
                else if (confirmPasswordState==='empty'){
                    isValid=false;
                    errorMsg='This cannot be empty';
                }

                if (errorMsg){
                    $("#confirmPasswordX").attr("hidden",false);
                    $("#confirmPasswordError").text(errorMsg);
                }
                return isValid;
            }
            function isEmpty(val){
                return (val==="");
            }
            function validatePassword(){
                var password=$("#password").val();
                var state="valid";
                if ((password.toUpperCase()===password)||password.length===0){//no lowercase letter or length 0
                    $("#letter").attr('class',"invalid");
                    state="invalid";
                }
                else{
                    $("#letter").attr('class',"valid")
                }
                if ((password.toLowerCase()===password)||password.length===0){//no uppercase letter or length 0
                    $("#capital").attr('class',"invalid");
                    state="invalid";
                }
                else{
                    $("#capital").attr('class',"valid")
                }

                if (!(/\d/.test(password))){//does not have a number
                    $("#number").attr('class',"invalid");
                    state="invalid";
                }
                else {
                    $("#number").attr('class',"valid")
                }
                if (password.length<8){
                    $("#length").attr('class',"invalid");
                    state="invalid";
                }
                else{
                    $("#length").attr('class',"valid");
                }
                if (password==="")
                {
                    state="empty";
                }
                return state;
            }

            function confirmPassword(){
                var state="invalid";
                if ($("#password").val()===$("#passwordConfirm").val()) {//if the values match
                    if (isEmpty($("#passwordConfirm").val().trim())){//if matches but empty
                        state="empty";
                    }
                    else{//matches and not empty
                        state="valid";
                        if (!$("#confirmPasswordX").attr("hidden")){
                            $("#confirmPasswordX").attr("hidden",true);
                        }
                        $("#confirmPasswordCheck").attr("hidden",false);
                    }
                }
                else//does not match
                {
                    if (isEmpty($("#passwordConfirm").val().trim())){
                        state="empty";
                    }
                    else
                    {
                        if (!$("#confirmPasswordCheck").attr("hidden")){
                            $("#confirmPasswordCheck").attr("hidden",true);
                        }
                        $("#confirmPasswordX").attr("hidden",false);
                    }
                }
                return state;
            }
        </script>
    </body>
</html>