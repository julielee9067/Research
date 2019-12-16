<%--
  Created by IntelliJ IDEA.
  User: tonym
  Date: 2018-06-15
  Time: 10:49 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Research - Properties Manager</title>
        <g:javascript>
            $(document).ready(function () {
                <g:if test="${result?.errorMsg}">
                    bootbox.confirm({
                        message: "<p style='margin-top: 20px'>This Entry is being used. Would you like to disable it instead?</p>",
                        callback: function(result){
                        if (result)  deleteEntry();
                       }
                    });
                </g:if>
                <g:elseif test="${result?.success}">
                    bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Entry successfully deleted</p>");
                </g:elseif>
            });

            function deleteEntry() {
                $("#enabled_${result?.id}").prop("checked", false);
                $("#update").click();
            }
        </g:javascript>
    </head>

    <body>
        <g:render template="/layouts/navigation" model="[which: 'manage']"/>
        <div class="container-fluid">
            <div class="card">
                <div class="card-header">
                    <h1 class="card-title pageTitle">Properties Manager</h1>
                </div>
                <g:form method="post">
                    <div class="card-body mx-auto mt-3 text-center">
                        <label class="h5">Select List </label>

                        <div class="col-8 mx-auto my-3 p-2 border border-success bg-light">
                            <div class="h5 text-center">New</div>
                            <label>Name</label><input size="100" type="text" name="new" class="input col-9 mx-2">
                            <input type="submit" name="add" class="btn btn-success btn-sm" value="Add To List"/>
                        </div>

                        <div class="table-responsive">
                            <table class=" table-bordered table-sm mx-auto">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Enabled</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody class="bg-light">
                                <g:each var="entry" in="${list}">
                                    <tr>
                                        <td><input size="100" type="text" name="name.${entry.id}" value="${entry.name}"
                                                   class="form-control"></td>
                                        <td><g:checkBox name="enabled.${entry.id}" value="${entry.enabled}" id="enabled_${entry.id}"/></td>
                                        <td><button type="submit" name="delete.${entry.id}" class="btn btn-sm btn-outline-danger">Delete</button></td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                        <div class="mx-auto my-2">
                            <input type="submit" class="btn btn-primary" name="update" value="Update" id="update"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </body>
</html>