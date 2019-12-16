<%@ page import="ca.georgebrown.gbcresearch.Submission;  ca.georgebrown.gbcresearch.security.Role; ca.georgebrown.gbcresearch.security.AppuserRole" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name='layout' content='main'/>
    <title>Research - Submission</title>
    <g:javascript>
            var dateChanged = false;
            function validateFormPublish() {
                 var isValid = true;

                 $(".requiredInput").each(function () {
                      if ($(this).val() === '' || $(this).val() === 'null') {
                        isValid = false;
                        $(this).css({'border-color': '#FF0000'});
                      }
                 });

                if (isValid && dateChanged && ${createNew != "true"}) {
                    bootbox.confirm({
                        message: "<p style='white-space: nowrap; margin-top: 20px'>Do you wish to publish this submission?</p>",
                        callback: function (result) {
                            if (result)  {
                                    bootbox.dialog({
                                        message: '<p class="text-center mb-0 p-5"><i class="fa fa-spin fa-spinner"></i> Please wait while the submission is published...</p>',
                                        closeButton: false
                                    });
                                    $("#savePublish").trigger('click');
                                    return false;
                            }
                        }
                    });
                }
                else if (isValid) {
                   bootbox.confirm({
                       message: "<p style='margin-top: 20px'>Are you sure you want to publish this submission? All tags will be " +
    "published in addition to the submission. </p>",
                       callback: function(result) {
                           if (result) {
                                bootbox.dialog({
                                    message: '<p class="text-center mb-0 p-5"><i class="fa fa-spin fa-spinner"></i> Please wait while the submission is published...</p>',
                                    closeButton: false
                                });
                               $("#savePublish").trigger('click');
                               return false;
                           }
                       }
                   });
                }
                else {
                    bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                }
            }

            function validateFormSubmit(action) {
                 var isValid = true;
                 $(".requiredInput").each(function () {
                      if ($(this).val() === '' || $(this).val() === 'null') {
                        isValid = false;
                        $(this).css({'border-color': '#FF0000'});
                      }
                 });
                if (isValid) {
                    bootbox.confirm({
                        message: "<p style='margin-top: 20px;'>Your submission will be reviewed and you will be notified when it is published, or if it requires further revisions.</p>",
                        callback: function (result) {
                            if (result) $("#updateSubmit").click();
                        }
                    });
                } else {
                    bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                }
            }

            function validateForm() {
                var isValid = true;
                var publisherButton = $("input[name='publish']:checked").val();

                $(".requiredInput").each(function () {
                    if ($(this).val() === '' || $(this).val() === 'null') {
                        isValid = false;
                        if (publisherButton === 'Yes') {
                            isValid = true;
                        }
                        $(this).css({'border-color': '#FF0000'});
                    }
                });
                if (isValid && dateChanged && ${createNew != "true"}) {
                    bootbox.confirm({
                    message: "<p style='white-space: nowrap; margin-top: 20px'>Do you wish to save?</p>",
                    callback: function (result) {
                        if (result) $("#save").click();
                    }
                    });
                }
                else if (isValid) {
                    if (publisherButton) {
                        $("#publisherSubmit").click();
                    }
                    else {
                        $("#save").click();
                    }
                }
                else {
                    bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                }
            }

        $.noConflict();

        $(function() {
            var hash = window.location.hash;
            hash ? $('ul.nav a[href="' + hash + '"]').tab('show'): $('#metric-tab').tab('show');

            $('.nav-tabs a').click(function (e) {
                $(this).tab('show');
                var scrollmem = $('body').scrollTop() || $('html').scrollTop();
                window.location.hash = this.hash;
                $('html,body').scrollTop(scrollmem);
            });

        <g:if test="${anchor == 'stepAnchor'}">
            $('#steps-tab').tab('show');
            window.location.href = "#${anchor}";
        </g:if>
        <g:elseif test="${anchor == 'fileAnchor'}">
            $("#documents-tab").tab('show');
            window.location.href = "#${anchor}";
        </g:elseif>
        });

        $(document).ready(function () {
            $("#actualStartDate").change(function () {
                dateChanged = true;
            });

            $("#actualEndDate").keydown(function () {
                dateChanged = true;
            });

        <g:if test="${project}">
            $('.nav-tabs a').click(function() {
                 $(this).tab('show');
            })
        </g:if>
        $("#actualStartDate").datepicker({ dateFormat: 'dd/mm/yy'});
        $("#actualEndDate").datepicker({ dateFormat: 'dd/mm/yy'});
        $("#expectedStartDate").datepicker({ dateFormat: 'dd/mm/yy'});
        $("#expectedEndDate").datepicker({ dateFormat: 'dd/mm/yy'});
        $(".project").areYouSure();
        $("#department").select2();
        $("#excellence").select2();
        $("#categories").select2();
        $("#property").select2();
        $("#lab").select2();
        $("#tag").select2({tags:true});

        <g:if test="${dateResult?.errorMsg}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'><asset:image src='warning.png'/> ${dateResult?.errorMsg}</p>");
        </g:if>
        <g:elseif test="${createSuccess}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Submission Created Successfully.</p><g:if
                test="${nameMatch}"><p style='white-space: nowrap; color: red;'>WARNING: Name matches existing project.</p></g:if>");
        </g:elseif>
        <g:elseif test="${updateSuccess}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Project Updated successfully</p>");
        </g:elseif>
        <g:elseif test="${recordExists}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Project already exists</p>");
        </g:elseif>
        <g:if test="${result?.deleteFileSuccess}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>File successfully deleted");
        </g:if>
        <g:elseif test="${result?.deleFileErrerMsg}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>${result?.deleFileErrerMsg}</p>");
        </g:elseif>

                $(".requiredInput").on('input click', function () {
                    $(this).css({'border': '1px solid #CCCCCC'});
                });

        $(".saveBtn").hide();
        $("#saveChangeMessage").hide();

                $(".editField").keydown(function () {
                      $(".saveBtn").show();
                      $("#saveChangeMessage").show();
                      $("#btnBudget").attr('disabled', 'disabled');
                      // $("#btnSubmitFile").attr('disabled', 'disabled');
                      // $("#my-file-selector").attr('disabled', 'disabled');
                      // $("#lbFileSelector").addClass('disabled');
                      $("#btnStakeholder").attr('disabled', 'disabled');
                });

                $(".editField").change(function () {
                      $(".saveBtn").show();
                      $("#saveChangeMessage").show();
                      $("#btnBudget").attr('disabled', 'disabled');
                      // $("#btnSubmitFile").attr('disabled', 'disabled');
                      // $("#my-file-selector").attr('disabled', 'disabled');
                      // $("#lbFileSelector").addClass('disabled');
                      $("#btnStakeholder").attr('disabled', 'disabled');
                });

        $("#newStep").keydown(function() {
            if ($(".saveBtn").is(':visible')) {
                bootbox.alert('<p style="white-space: nowrap; margin-top: 20px">Please save your changes before adding a step</p>');
                        $("#newStepSave").hide();
                    }
                    else {
                        $("#newStepSave").show();
                    }
                });

                $("#newUpdate").keydown(function() {
                   if ($(".saveBtn").is(':visible')) {
                        bootbox.alert('<p style="white-space: nowrap; margin-top: 20px">Please save your changes before adding an update</p>');
                        $("#newUpdateSave").hide();
                    }
                   else {
                       console.log("new update")
                        $("#newUpdateSave").show();
                    }
                });

                $("#fixTable").tableHeadFixer({
                    left: 7,
                    'z-index': 1
                });

        <g:if test="${result?.errorMsg}">
            bootbox.confirm({
                   message: '<p style="white-space: nowrap; margin-top: 20px"><asset:image src="warning.png"/>This project is referenced elsewhere and cannot be deleted. Would you like to disable it instead?</p>',
                           callback: function(result) {
                               if (result) {
                                  $("#disable").click();
                               }
                           }
                    });
        </g:if>

        <g:if test="${projectRefList?.size()}">
            var refList = "";
            <g:each in="${projectRefList}" var="ref">
                refList+="<li>${ref}</li>";
            </g:each>
            bootbox.confirm({
               buttons: {
                   confirm: {
                        label: 'Disable Instead',
                  }
               },
               message: '<p style="white-space: nowrap; margin-top: 20px"><asset:image src="warning.png"/> This project has the following references and cannot be deleted.<br/><br/><ul>' + refList + '</ul>Would you like to disable it instead? (To delete this project please remove the references listed above and then try again) </p>',
                       callback: function(result) {
                               if (result) {
                                  $("#disable").click();
                                }
                       }
                    });
        </g:if>

        <g:if test="${budgetDeleteSuccess}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Project budget successfully deleted</p>");
        </g:if>

        <g:if test="${stakeholderDeleteSuccess}">
            bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>Project stakeholder successfully deleted</p>");
        </g:if>

        $('input[type="radio"]').click(function () {
                if ($(this).attr("value") == "Yes") {
                    $("#reason").hide('fast');
                }
                if ($(this).attr("value") == "No") {
                    $("#reason").show('fast');
                }
        });
    });

    function recallConfirm(id) {
        bootbox.confirm({
           message: "<p style='margin-top: 20px'>Are you sure you want to recall this submission? Your submissions will be removed from review to publication queue.</p>",
                   callback: function(result) {
                       if (result) {
                         $("#recall").click();
                       }
                   }
                });
            }

            function confirmPublish(id) {
                validateFormPublish();
            }

            function confirmSubmit(id) {
                    validateFormSubmit();
            }

            function validateFormSubmit2() {
                     var isValid = true;
                     $(".requiredInput2").each(function () {
                          if ($(this).val() === '' || $(this).val() === 'null') {
                            isValid = false;
                            $(this).css({'border-color': '#FF0000'});
                          }
                     });
                    if (isValid) {
                        bootbox.confirm({
                            message: "<p style='white-space: nowrap; margin-top: 20px'>Do you wish to save?</p>",
                            callback: function (result) {
                                if (result) $("#save").click();
                            }
                        })
                    } else {
                     bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                  }
            }
            function validateFormSubmit3() {
                     var isValid = true;
                     $(".requiredInput").each(function () {
                          if ($(this).val() === '' || $(this).val() === 'null') {
                            isValid = false;
                            $(this).css({'border-color': '#FF0000'});
                          }
                     });
                    if (isValid) {
                        bootbox.confirm({
                            message: "<p style='white-space: nowrap; margin-top: 20px'>Do you wish to save?</p>",
                            callback: function (result) {
                                if (result) $("#saveModePublish").click();
                            }
                        });
                    } else {
                        bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                    }
            }

            function validateFormSubmit4() {
                bootbox.confirm({
                    message: "<p style='white-space: nowrap; margin-top: 20px'>Do you wish to cancel? Changes you made may not be saved. </p>",
                    callback: function (result) {
                        if (result) $("#cancel").click();
                    }
                });
            }

            function confirmSave(id) {
                    validateFormSubmit2();
            }

            function confirmCancel(id) {
                    validateFormSubmit4();
            }

            function confirmPublishSave(id) {
                validateFormSubmit3();
            }

            function confirmReject(id) {
                var isValid = true;
                 $(".requiredInput").each(function () {
                      if ($(this).val() === '' || $(this).val() === 'null') {
                        isValid = false;
                        $(this).css({'border-color': '#FF0000'});
                      }
                 });
                if (isValid) {
                    var dialog = bootbox.dialog({
                       message:
                    "<p style='white-space: nowrap; margin-top: 20px'>Why would you like to reject this submission?</p>" +
                        "<textarea cols='40' rows='6' name='editComment' id='reasonForRejection' class='requiredInput'></textarea>",
                        buttons: {
                            cancel: {
                                label: "Cancel",
                                className: 'btn-danger',
                                callback: function() {

                                }
                            },

                            reject: {
                                label: "Return for Revision",
                                className: 'btn-success',
                                callback: function() {

                                    var reason = $('#reasonForRejection').val();

                                    $("#reasonEntry").val(reason);
                                    //document.getElementById('reasonEntry').value
                                    $("#publisherReject").click();
                                     bootbox.dialog({
                                        message: '<p class="text-center mb-0 p-5"><i class="fa fa-spin fa-spinner"></i> Please wait while the submission is returned for revision...</p>',
                                        closeButton: false
                                     });
                                     return false;
                                }
                            }
                        }

                    });
                }
                else {
                    bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                }
            }

            function removeStepUpdate(data, section) {
                if (section === 'step') {
                    $("#trStep"+data.id).remove();
                }
                else {
                    $("#trUpdate"+data.id).remove();
                }
            }
            function deleteConfirm() {
                bootbox.confirm({
                    message: "<p style='white-space: nowrap; margin-top: 20px'>Are you sure you want to delete this submission?</p>",
                    callback: function(result) {
                          if (result) {
                                $("#delete").click();
                          }
                    }
                });
            }

            function enableConfirm() {
                bootbox.confirm({
                    message: "<p style='white-space: nowrap; margin-top: 20px'>Are you sure you want to enable this submission?</p>",
                    callback: function(result) {
                        if (result) {
                        $("#enable").click();
                        }
                    }
                });
            }
    </g:javascript>
    <style type="text/css" media="screen">
    .center {
        text-align: center;
    }

    .required label:before {
        content: " *";
        display: inline;
        color: red;
        padding-right: 10px;
    }

    .requiredInput {
        border: 1px solid lightgray;
    !important;
    }

    .requiredInput2 {
        border: 1px solid lightgray;
    !important;
    }

    #email:invalid {
        color: red;
    }

    td .metricInput {
        text-align: right;
        width: 50px;
    !important;
    }

    td .payPeriodInput {
        text-align: right;
        width: 100px;
    !important;
    }

    .date {
        width: 200px;
    !important;
    }

    .saveBtn {
        margin-bottom: 10px;
    }
    </style>
</head>

<body>
<g:render template='/layouts/navigation'/>
<div class="card border-0 ">
    <g:form controller='submissions' name="update" params='[id: submission?.id, source: params?.source, profile_id: profile_id]' id="${submission?.id}" action="edit"
            method="post" class="project">
        <div class="card border-0 ">
            <div class="h-50 my-0 border-bottom border-top" style="min-height:70px;background-color: #f0f0f0">
                <div class="card-header newProfileHearder pt-4">
                    <g:link controller="submissions" action="view" params="[id: params.id, source: params?.source, profile_id: profile_id]"
                            style="font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px" >< Back</g:link>
                    <input type="hidden" name="id" value="${submission?.id}"/>
                    <g:if test="${submission?.title}">
                        <div class="w-75 font-weight-bold my-4" style="font-size: 25px; color: #1b5ca3; margin-left: 75px;"> ${submission?.title}
                            <g:if test="${submission?.activeStatus == Submission.STATUS.DISABLED}">
                                <span style="color: red;font-size: 15px">STATUS: DISABLED</span>
                            </g:if>
                        </div>
                    </g:if>
                </div>
            </div>

                    <div class="card-body mx-5">
                        <g:if test="${submission?.submitStatus == Submission.SUBMIT_STATUS.RETURNED}">
                            <div class="col-10 mx-auto my-2 bg-light border border-warning">
                                <div class="h6"><b>Revision Required</b></div>
                                <g:if test="${!submission?.rejectionReason}">
                                    <p>Publisher did not give reason for requiring revision</p>
                                </g:if>
                                <g:elseif test="${submission?.rejectionReason?.trim() == ""}">
                                    <p>Publisher did not give reason for requiring revision</p>
                                </g:elseif>
                                <g:else>
                                    <p>${submission?.rejectionReason}</p>
                                </g:else>
                            </div>
                        </g:if>
                        <g:render template='editForm' model="[status: submission?.submitStatus, types: types, submission: submission, appuser: appuser, accessRights: accessRights]"/>
                        <br clear="all"/>
                    </div>
                </div>
                <g:actionSubmit value="Save" id="save" name="save" action="edit" class="btn text-white rounded-0"
                                style="display: none"
                                params="[id: submission?.id, source: 'profile', profile_id: profile_id]" controller="submissions" />
                <g:actionSubmit value="Cancel" id="cancel" name="cancel" action="edit" class="btn text-white rounded-0"
                                style="display:none"
                                params="[id: submission?.id, source: 'profile', profile_id: profile_id]" controller="submissions" />
                <g:actionSubmit style="display:none" id="recall" value="recall" action="edit"
                                params="[id: submission?.id, profile_id: profile_id]"/>
                <g:actionSubmit id="publisherReject" value="reject" name="reject" action="edit"
                                params="[id: submission?.id]" controller="submissions" class="hidden"/>
                <g:actionSubmit value="SaveModePublish" id="saveModePublish" name="saveModePublish" action="edit" class="btn text-white rounded-0"
                                style="display:none"
                                params="[id: submission?.id, profile_id: profile_id]" controller="submissions" />
                <g:actionSubmit value="Cancel" id="cancel" name="cancel" action="edit" class="btn text-white rounded-0"
                                style="display:none"
                                params="[id: submission?.id, source: 'profile', profile_id: profile_id]" controller="submissions" />
                <g:actionSubmit id="updateSubmit" value="updateSubmit" name="updateSubmit" action="edit"
                                params="[id: submission?.id, submit: true, profile_id: profile_id]" controller="submissions"
                                class="hidden"/>
                <g:actionSubmit id="savePublish" value="updatePublish" name="updatePublish" action="edit"
                                params="[id: submission?.id]" controller="submissions" class="hidden"/>
                <button type="submit" name="delete" id="delete" style="display: none"></button>
                <button type="submit" name="disable" id="disable" style="display: none"></button>
                <button type="submit" name="enable" id="enable" style="display: none"></button>
            </g:form>

            <g:if test="${uploadFileList.size() > 0 || uploadFileListNewSubmission.size() > 0 || (accessRights[Submission.RIGHT.EDIT])}">
                <input type="hidden" value="submission.title" name="title">
                <div class="col-sm-8 pull-left ml-5" style="background-color: white;">
                    <label class="col-sm-8" style="color: #005AA5">DOCUMENTS:</label> <br/>
                    <div class="col-12 my-0">
                        <g:if test="${view == 'true'}">
                            <g:render template='/uploadedFiles/list'
                                      model="[returnController: 'submissions', returnAction: 'edit', view: 'true', uploadedFileList: uploadFileList]"/>
                        </g:if>
                        <g:else>
                            <g:if test="${params.createNew == 'true'}">
                                <g:render template='/uploadedFiles/upload'
                                          model="[returnController: 'submissions', returnAction: 'edit', source: 'Submission', objId: 0, createNew: 'true', params: params]"/>
                                <g:render template='/uploadedFiles/list'
                                          model="[returnController: 'submissions', returnAction: 'edit', uploadedFileList: uploadFileListNewSubmission]"/>
                            </g:if>
                            <g:else>
                                <g:render template='/uploadedFiles/upload'
                                          model="[returnController: 'submissions', returnAction: 'edit', source: 'Submission', objId: submission?.id, params: params]"/>
                                <g:render template='/uploadedFiles/list'
                                          model="[returnController: 'submissions', returnAction: 'edit', uploadedFileList: uploadFileList]"/>
                            </g:else>
                        </g:else>
                    </div>
                </div>
            </g:if>
            <div style="position: absolute; width: 65%; bottom: -5%; text-align: center">
                <g:if test="${!createNew && submission?.submitStatus != Submission.SUBMIT_STATUS.SUBMITTED && accessRights[Submission.RIGHT.DELETE]}">
                    <input type="button" class="btn btn-danger rounded-0" value="Delete Submission"
                           onclick="deleteConfirm();">
                </g:if>
                <g:elseif test="${submission?.activeStatus == Submission.STATUS.DISABLED}">
                    <div class="center my-3">
                        <input type="button" class="btn text-white rounded-0" value="Enable Submission"
                               style="background-color: #1b5ca3;" onclick="enableConfirm();">
                    </div>
                </g:elseif>
                <g:if test="${accessRights[Submission.RIGHT.RECALL] && params.mode != "publish"}">
                    <input type="button" class="btn btn-warning text-dark rounded-0" style="width: 100px"
                           value="Recall" onclick="recallConfirm(${submission?.id})"/>
                </g:if>
                <g:else>
                    <g:if test="${accessRights[Submission.RIGHT.EDIT] && params.mode != "publish"}">
                        <input type="button" class="btn btn-secondary rounded-0" style="width: 80px;"
                               value="Cancel" onclick="confirmCancel(${submission?.id})"/>

                        <input type="button" class="btn btn-primary rounded-0" style="width: 80px; background-color: #005AA5;"
                               value="Save" onclick="confirmSave(${submission?.id})"/>
                    </g:if>
                    <g:elseif test="${accessRights[Submission.RIGHT.EDIT] && params.mode == "publish"}">
                        <input type="button" class="btn btn-secondary rounded-0" style="width: 80px;"
                               value="Cancel" onclick="confirmCancel(${submission?.id})"/>

                        <input type="button" class="btn btn-primary rounded-0" style="width: 80px; background-color: #005AA5"
                               value="Save" onclick="confirmPublishSave(${submission?.id})"/>
                    </g:elseif>
                    <g:if test="${!createNew && accessRights[Submission.RIGHT.SUBMIT] && params.mode != "publish"}">
                        <input type="button" onclick="confirmSubmit(${submission?.id ?: 0})" id="submitToPublication"
                               class="btn text-white rounded-0" value="Submit to publication"
                               style="background-color: #4CAF50"/>
                    </g:if>
                </g:else>
                <g:if test="${accessRights[Submission.RIGHT.PUBLISH] && params.mode == "publish"}">
                    <input type="button" onclick="confirmPublish(${submission?.id});" id = "publish"
                           class="btn text-white rounded-0" style="width: 100px; background-color: #4CAF50" value="Publish" />
                </g:if>
                <g:if test="${accessRights[Submission.RIGHT.RETURN] && submission?.submitStatus != Submission.SUBMIT_STATUS.RETURNED &&
                        (submission?.submitStatus == Submission.SUBMIT_STATUS.PUBLISHED || params.mode == "publish")}">
                    <input type="button" onclick="confirmReject(${submission?.id})" id = "return"
                           class="btn btn-warning text-dark rounded-0" style="width: 150px;" value="Return for Revision" />
                    <div class="reason" id="reason" style="display:none">
                        <input type="text" class="form-control" id="reasonEntry" name="reason" value=""/>
                    </div>
                </g:if>
            </div>
        </div>
        <br clear="all" />
    </body>
</html>