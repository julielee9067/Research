<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Research - Create New User</title>

        <g:javascript>
            $(document).ready(function () {
               <g:if test="${createSuccess}">
            bootbox.alert("<p style='margin-top: 20px'>Your account has been created. Please check your activate your account.</p>");
                </g:if>
            });
        </g:javascript>

        <style type="text/css" media="screen">
            #email:invalid {
                color: red;
            }
        </style>
    </head>

    <body>
        <g:render template='/layouts/navigation' model="[which: 'userManagement']"/>
        <div class="container-fluid">
            <g:form controller='userManagement' action="list">
                <button type="submit" class="btn text-black btn-xs" style="background-color: #005AA5; color: white"> Return to Users List</button>
            </g:form>
        </div>
        <div id="edit-appSettings" class="container-fluid content scaffold-list" role="main">
            <div class="card border-bottom">
                <div class=" text-center text-white bg-success ">Create</div>
                <div class="card-header">
                    <div class="pageTitle px-lg-3 "><h1 class="pageTitle">New User</h1></div>
                </div>
                <div class="card-body mx-auto mt-3 text-center">
                    <g:form method="post">
                        <fieldset class="form">
                            <g:render template="form"  model="[which: 'create']"/>
                        </fieldset>
                        <fieldset class="buttons">
                            <br>
                            <div style="text-align: center">
                                <g:actionSubmit class="save btn btn-success" action="create" value="Create"/>
                            </div>
                        </fieldset>
                    </g:form>
                </div>
            </div>
        </div>
    </body>
</html>