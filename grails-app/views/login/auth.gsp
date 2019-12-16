<html>
    <head>
        <meta name='layout' content='login'/>
        <title><g:message code="app.login.title"/></title>
        <asset:stylesheet src="research-portal.css"/>
    </head>
    <body class="container">
        <script type="text/javascript">
            function logout() {
                ${remoteFunction(controller:"login", action:"ajaxLogout")};
            }

            $(document).ready(function () {
                $('#username').focus();
                $('#currentController').val('login');
                $('#currentAction').val('auth');
            });

            $.jGrowl.defaults.position = 'center';
        </script>

        <div class="card bg-transparent border-0">
            <div class="container-fluid content scaffold-list" role = "main">
                <div class="w-25 ml-5 my-5 pull-left rounded-0" style="background-color: rgba(245, 245, 245, 1);opacity: .9; min-width: 330px;">
                    <div class="card-body">
                        <g:if test='${flash.message}'>
                            <div class='login_message'>${flash.message}</div>
                        </g:if>
                        <h3> Welcome to the GBC Research Portal</h3> <br/>
                        <form action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
                            <div class="form-group">
                                <label for='username'><g:message code="app.login.username"/></label>
                                <div style="margin-bottom: 25px;" class="input-group">
                                    <input type='text' style="height: 40px;box-shadow: none;" class='form-control' name='j_username' id='username' value="" placeholder="Enter your username"/>
                                    <span class="input-group-addon" style="width:40px;"><i class="glyphicon glyphicon-user"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for='password'><g:message code="app.login.password"/>:</label>
                                <div style="margin-bottom: 25px" class="input-group">
                                    <input type='password' style="height: 40px;box-shadow: none;" class='form-control' name='j_password' id='password' placeholder="Enter Your password">
                                    <span class="input-group-addon" style="width:40px"><i class="glyphicon glyphicon-lock"></i></span>
                                </div>
                            </div>
                            <div class="checkbox ml-1 pull-left mt-0" id="remember_me_holder">
                                <label>
                                    <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                                           <g:if test='${hasCookie}'>checked='checked'</g:if> style="zoom:2;margin:0 0 0 -12px;"/>
                                    &nbsp;<g:message code="app.login.rememberme"/>
                                </label>
                            </div>
                            <button class="btn btn-primary text-center w-100 rounded-0 mt-3" type='submit' style="background-color: #005AA5"><g:message code="app.login.button"/></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script type='text/javascript'>
            (function () {
                document.forms['loginForm'].elements['j_username'].focus();
            })();
        </script>
    </body>
</html>

