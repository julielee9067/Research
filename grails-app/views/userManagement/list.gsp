<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>Research - Users</title>
        <style>
            .resTable tbody tr{
                border: 1px solid lightgray;
            }
        </style>
    </head>
    <body>
        <g:render template='/layouts/navigation' model="[which: 'userManagement']"/>
        <div id="list-users" class="container-fluid content scaffold-list" role="main">
            <div class="card">
                <div class="card-header">
                    <h1 class="card-title pageTitle">List of Users</h1>
                </div>
                <g:if test="${flash.message}">
                    <div class="warningMsg" role="status">${flash.message}</div>
                </g:if>
                <g:form method="post" action="newUser">
                    <div class="card-body center mt-3 text-center container">
                        <div class="col-5 mx-auto my-3 p-2 bg-light center border-bottom">
                            <div class="h5 text-center">New user</div>
                            <label>GBC ID </label><input type="text" value="" name="spridenId" class="input col-6 mx-2">
                            <input type="submit" value="Add New User" name="add" class="btn btn-success btn-sm"><br/>
                            <g:link action="create" id="addNew" class="small text-muted">Not a George Brown Account?</g:link>
                        </div>
                </g:form>
                <g:form method="post" action="list">
                    <div class="col-5 mx-auto my-3 p-2 bg-light center border-bottom">
                        <div class="h5 text-center">User Search</div>
                                <label style="width: 90px">First Name</label><input type="text" value="${firstName}" name="firstName" class="input col-9 mx-2">
                        <br/>
                                <label style="width: 90px">Last Name</label><input type="text" value="${lastName}" name="lastName" class="input col-9 mx-2">
                        <br/>
                                <label style="width: 90px">User ID</label><input type="text" value="${username}" name="username" class="input col-9 mx-2" >
                        <br/>
                            <input type="submit" value="Search" name="search" class="btn btn-success btn-sm mt-2"><br/>
                        </div>
                    </div>
                </g:form>
                <div class="container-fluid mx-auto mb-5 center">
                    <div class="table-responsive">
                        <div id="userList">
                            <table class="resTable table table-sm table-hover table-bordered table-striped border-bottom center mb-0 ">
                                <thead class="text-center">
                                <tr>
                                    <th>User ID</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Email</th>
                                    <th>Enabled</th>
                                    <th>Roles</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${userList}" status="i" var="user">
                                        <tr>
                                            <td class="text-center">${user.username}</td>
                                            <td class="text-center">${user.firstName}</td>
                                            <td class="text-center">${user.lastName}</td>
                                            <td class="text-center"><a href="mailto:${user.email}">${user.email}</a></td>
                                            <td class="text-center">${user.enabled ? 'YES' : 'NO'}</td>
                                            <td class="text-center">${user.authorities*.name.join(", ")}</td>
                                            <td class="text-center"><g:link class="userManagement btn btn-sm btn-outline-primary" action="edit" id="${user.id}">Edit</g:link></td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                            <br clear="all"/>
                            <div class="paginationBox">
                                <g:paginate action="list" total="${total}" update="userList"  next="Forward" prev="Back" max="10" maxsteps="5" controller="userManagement" loadingHTML="submissionLoader"
                                                     params="[firstName: params.firstName ?: '', lastName: params.lastName ?: '', username: params.username ?: '', list: userList, div_id: 'submissionView', action: 'list', total: total]"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
