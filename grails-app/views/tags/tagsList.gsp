<%--
  Created by IntelliJ IDEA.
  User: staff
  Date: 2019-07-09
  Time: 10:01
--%>

<%@ page import="ca.georgebrown.gbcresearch.Tag" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main">
        <title>Research - Tags</title>
        <style>
            .resTable tbody tr{
                border: 1px solid lightgray;
            }
        </style>
        <g:javascript>
            $(document).ready(function () {
                    <g:if test="${result.message}">
                        bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>${result.message}</p>");
                    </g:if>
            });
            function deleteDisableMsg(buttonid, usages) { // change to confirmation and display changes
                var statusId = "status_" + buttonid
                var deleteButtonId = "delete." + buttonid
                var dialog = bootbox.dialog({
                    title: 'Error: Tag is in use',
                    message: "<p style='margin-top: 20px'>Deleting this tag will remove it from all associated content permanently. Disabling this tag will hide it from being displayed on associated content. Would you like to delete or disable this tag?</p>",
                    size: 'large',
                    closeButton: false,
                    buttons: {
                        cancel: {
                            label: "Cancel",
                            className: 'btn btn-default',
                            callback: function(){
                                console.log('cancel');
                            }
                        },
                        noclose: {
                            label: "Disable",
                            className: 'btn-primary',
                            callback: function(){
                                console.log('disable');
                                $("#" + statusId).prop('checked', false);
                                $("#update").click();
                            }
                        },
                        ok: {
                            label: "Delete",
                            className: 'btn-danger',
                            callback: function(){
                                console.log('delete: ' + deleteButtonId);
                                document.getElementById(deleteButtonId).click();
                                $("#delete").click();
                            }
                        }
                    }
                });
            }

            function deleteNoDisableMsg(buttonid) {
                var statusId = "status_" + buttonid
                var deleteButtonId = "delete." + buttonid
                bootbox.confirm({
                    message: "<p style='white-space: nowrap; margin-top: 20px'>Are you sure you want to delete this tag?</p>",
                    buttons: {
                        confirm: {
                            label: 'Delete',
                            className: 'btn-danger'
                        },
                        cancel: {
                            label: 'Cancel',
                            className: 'btn-default'
                        }
                    },
                    callback: function (result) {
                        if ( result ) {
                            console.log('delete: ' + deleteButtonId);
                            document.getElementById(deleteButtonId).click();
                            $("#delete").click();

                        }
                    }
                });
            }

            function deleteDispatch(buttonid, usages) {
                if (!usages) {
                    usages = 0
                }

                if ( usages > 0 ) {
                    deleteDisableMsg(buttonid, usages)
                }
                else {
                    deleteNoDisableMsg(buttonid)
                }
            }
        </g:javascript>
    </head>

    <body>
        <g:render template='/layouts/navigation' model="[which: 'tags']"/>
        <div id="list-metrics" class="container-fluid content scaffold-list" role="main">
            <div class="card">
                <div class="card-header">
                    <h1 class="card-title pageTitle">Manage Tags</h1>
                </div>

                <g:form method="post" action="tagsList">
                    <div class="card-body mx-auto mt-3 text-center">
                        <g:if test="${flash.message}">
                            <div class="warningMsg" role="status">${flash.message}</div>
                        </g:if>
                        <div class="col-4 mx-auto my-3 p-2 border-bottom bg-light">
                            <div class="h5 text-center">New Tag</div>
                            <div class="row">
                                <div class="col-12 text-center">
                                    <label for="newTag">Name</label><input type="text" name="new.name" id="newTag" class="input col-9 mx-2"><br/>
                                </div>
                                <div class="mx-auto my-2">
                                    <input type="submit" name="add" class="btn btn-success btn-sm" value="Add To List"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-4 mx-auto my-3 p-2 border-bottom bg-light">
                            <div class="h5 text-center">Tag Search</div>
                            <label style="width: 90px">Name</label><input type="text" value="${tagName}" name="tagName" class="input col-9 mx-2">
                            <br/>
                            <input type="submit" value="Search" name="search" class="btn btn-success btn-sm mt-2"><br/>
                        </div>

                        <div class="table-responsive mx-auto table-bordered" style="width: 55%">
                            <table class="resTable table table-bordered table-sm mx-auto table-striped mb-0 ">
                                <thead>
                                <tr>
                                    <th class="align-middle">Name</th>
                                    <th class="align-middle">Enabled</th>
                                    <g:if test="${activeList?.size()}">
                                        <g:if test="${activeList?.first()?.respondsTo("getUsageCnt")} == 'true'">
                                            <th class="align-middle" style="white-space: nowrap;">Usage Count</th>
                                        </g:if>
                                    </g:if>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${inactiveList}"  var="tag">
                                    <tr>
                                        <td style="min-width: 150px;"><input type="text" name="name.${tag.id}" size="50" value="${tag.name}" class="form-control"></td>
                                        <td class="text-center align-middle"><input type="checkbox" name="status.${tag.id}" id="status_${tag.id}" <g:if test="${tag.status == Tag.STATUS.ACTIVE}">checked</g:if> style="zoom:1.5;"></td>
                                        <g:if test="${tag.respondsTo("getUsageCnt")}">
                                            <td class="text-center align-middle">${tag.usageCnt}</td>
                                        </g:if>
                                        <g:if test="${inactiveList?.size()}">
                                            <g:if test="${inactiveList?.first()?.respondsTo("getUsageCnt")} == 'true'">
                                                <td class="text-center align-middle"><input type="button" value="Delete" class="btn btn-sm btn-outline-danger" onclick="deleteDispatch('${tag.id}', '${tag.usageCnt}')"></td>
                                            </g:if>
                                        </g:if>
                                        <g:else>
                                            <td class="text-center align-middle" ><input type="button" value="Delete" class="btn btn-sm btn-outline-danger" onclick="deleteDispatch('${tag.id}')"></td>
                                        </g:else>
                                        <button class="save hidden" type="submit" id="delete.${tag.id}" name="delete.${tag.id}" class="btn btn-outline-danger">Delete</button>
                                    </tr>
                                </g:each>
                                <g:each in="${activeList}"  var="tag">
                                    <tr>
                                        <td style="min-width: 150px;"><input type="text" name="name.${tag.id}" size="50" value="${tag.name}" class="form-control"></td>
                                        <td class="text-center align-middle"><input type="checkbox" name="status.${tag.id}" id="status_${tag.id}" <g:if test="${tag.status == Tag.STATUS.ACTIVE}">checked</g:if> style="zoom:1.5;"></td>
                                        <g:if test="${tag.respondsTo("getUsageCnt")}">
                                            <td class="text-center align-middle">${tag.usageCnt}</td>
                                        </g:if>
                                        <g:if test="${activeList?.size()}">
                                            <g:if test="${activeList?.first()?.respondsTo("getUsageCnt")} == 'true'">
                                                <td class="text-center align-middle"><input type="button" value="Delete" class="btn btn-sm btn-outline-danger" onclick="deleteDispatch('${tag.id}', '${tag.usageCnt}')"></td>
                                            </g:if>
                                        </g:if>
                                        <g:else>
                                            <td class="text-center align-middle" ><input type="button" value="Delete" class="btn btn-sm btn-outline-danger" onclick="deleteDispatch('${tag.id}')"></td>
                                        </g:else>
                                        <button class="save hidden" type="submit" id="delete.${tag.id}" name="delete.${tag.id}" class="btn btn-outline-danger">Delete</button>
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