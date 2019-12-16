<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>Research - User Edit</title>
        <g:javascript>
        function updateProfile() {
            $("#update").click();
        }
    </g:javascript>
    </head>

    <body>
        <g:render template='/layouts/navigation' model="[which:'userManagement']"/>
        <div class="container-fluid">
            <g:form controller='userManagement' action="list">
                <button type="submit" class="btn text-black btn-xs" style="background-color: #005AA5; color: white"> Return to Users List</button>
            </g:form>
        </div>
        <div id="edit-appSettings" class="container-fluid content scaffold-list" role="main">
                <div class="card border-bottom">
                    <div class=" text-center text-white bg-warning ">Edit User</div>
                    <div class="card-header">
                        <div class="pageTitle px-lg-3 ">${user.displayName}</div>
                    </div>
                <div class="card-body mx-auto mt-3" style="text-align: left">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${user}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${user}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form method="post">
                        <g:hiddenField name="id" value="${user?.id}" />
                        <g:hiddenField name="version" value="${user?.version}" />
                            <g:render template="form" model="[which: 'edit']"/>
                            <div class="mx-auto my-2" style="text-align: center">
                            <g:actionSubmit class="save btn btn-primary" style="background-color: #005AA5" action="update" value="Update" id="update"/>
                            </div>
                    </g:form>
                </div>
            </div>
        </div>
    </body>
</html>
