<%@ page import="ca.georgebrown.gbcresearch.SubmissionType" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main">
        <title>Research - Submission Type</title>
        <style>
            .resTable tbody tr{
                border: 1px solid lightgray;
            }
        </style>
        <g:javascript>

        $(document).ready(function () {
           <g:if test="${result?.errorMsg}">
                bootbox.confirm({
                    message: "<p style='margin-top: 20px'>This type is being used. Would you like to disable it instead?</p>",
                    callback: function(result){
                    if (result)  deleteType();
                   }
                });
            </g:if>
            <g:elseif test="${result?.success}">
                bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Type successfully deleted</p>");
            </g:elseif>
        });

         function deleteType() {
            $("#status_${result?.id}").prop("checked", false);
            $("#update").click();
            }
        </g:javascript>
    </head>

    <body>
        <g:render template='/layouts/navigation' model="[which: 'submissionType']"/>
        <div id="list-metrics" class="container-fluid content scaffold-list" role="main">
            <div class="card">
                <div class="card-header">
                    <h1 class="card-title pageTitle">Submission Type</h1>
                </div>
                <g:form method="post">
                    <div class="card-body mx-auto mt-3 text-center">
                        <g:if test="${flash.message}">
                            <div class="warningMsg" role="status">${flash.message}</div>
                        </g:if>

                        <div class="col-5 mx-auto my-3 p-2 border-bottom bg-light">
                            <div class="h5 text-center">New Submission Type</div>
                            <div class="row">
                                <div class="col-12 text-right">
                                    <label for="newType">Name</label><input type="text" name="new.type" id="newType" class="input col-9 mx-2"><br/>
                                    <label for="newDescription">Description</label><input type="text" name="new.description" id="newDescription" class="input col-9 mx-2" style="margin-right: 10px">
                                </div>
                                <div class="mx-auto my-2">
                                    <input type="submit" name="add" class="btn btn-success btn-sm" value="Add To List"/>
                                </div>
                            </div>
                        </div>

                        <div class="table-responsive mx-auto w-50 table-bordered">
                            <table class="resTable table table-bordered table-sm mx-auto table-striped mb-0">
                                <thead class="text-center">
                                <tr>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${types}"  var="type">
                                    <tr>
                                        <td style="min-width: 150px;"><input type="text" name="name.${type.id}" size="50" value="${type.name}" class="form-control"></td>
                                        <td style="min-width: 150px;"><input type="text" name="description.${type.id}" size="100" value="${type.description}" class="form-control"></td>
                                        <td class="align-middle"><button type="submit" name="delete.${type.id}" class="btn btn-sm btn-outline-danger">Delete</button></td>
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