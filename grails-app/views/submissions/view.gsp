<%@ page import="ca.georgebrown.gbcresearch.UploadedFile; ca.georgebrown.gbcresearch.Submission;  ca.georgebrown.gbcresearch.security.Role; ca.georgebrown.gbcresearch.security.AppuserRole" contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name='layout' content='main'/>
        <title>Research - Submission</title>
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
            <g:form controller='submissions' name="update" params = '[id: submission?.id]' id="${submission?.id}" action="edit" method="post" class="project">
                <div class="card border-0 ">
                    <div class="h-50 my-0 border-bottom border-top" style="min-height:70px;background-color: #f0f0f0">
                        <div class="card-header newProfileHearder pt-4">
                        <g:if test="${source=='publisher'}">
                            <g:link controller="${source}" action="list" params="[source: source, searchInput: searchInput, tagInput: tagInput]"
                            style="font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px">< Back</g:link>
                        </g:if>
                        <g:elseif test="${source=='homePage'}">
                            <g:link controller="${source}" action="index" params="[source: source, searchInput: searchInput, tagInput: tagInput]"
                                    style="font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px">< Back</g:link>
                        </g:elseif>
                        <g:elseif test="${source=='profile'}">
                            <g:link controller="${source}" action="index" params="[source: source, searchInput: searchInput, tagInput: tagInput, profile_id: profile_id]"
                                    style="font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px">< Back</g:link>
                        </g:elseif>
                        <g:else>
                            <g:link uri="${request.getHeader('referer')}"
                                    style="font-size: 16px;text-decoration: none; margin-left: 75px; margin-top: 20px">< Back</g:link>
                        </g:else>
                            <input type="hidden" name="id" value="${submission?.id}"/>
                                 <div class="w-75 my-4" style="font-size: 25px; color: #1b5ca3; margin-left: 75px; font-weight: 700;">
                                    ${submission?.title}
                                </div>
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
                    <div class="card-body mx-5">
                        <g:render template='viewForm' model="[status: submission?.submitStatus]"/>
                    </div>
                </div>
            </g:form>
            <g:if test="${submission && accessRights[Submission.RIGHT.READ]}">
                <div style="margin: 0 150px">
                    <div class="w-100 pl-0 pb-3 pt-0 mb-3 pull-left" style="background-color: white;min-width: 400px;">
                        <label  class="m-0 mb-0" style="background-color: white; color: #005AA5">DOCUMENTS:</label> <br/><br/>
                        <div class="col-0 my-0">
                            <g:render template='/uploadedFiles/list'
                                      model="[view: 'true', returnController: 'submissions', returnAction: 'edit', uploadedFileList: uploadFileList]"/>
                        </div>
                    </div>
                </div>
            </g:if>
            <div class="center my-3">
                <g:if test="${Role.findByAuthority('ROLE_PUBLISHER') in appuser.authorities &&
                        submission.source != Submission.SOURCE.IAGO && submission.submitStatus != Submission.SUBMIT_STATUS.RETURNED}">
                    <g:link controller="submissions" action="edit" id="${submission.id}" params="[source: source, profile_id: profile_id]" style="text-decoration: none;width: 80px; color: white; background-color: #005AA5" class="btn">Edit</g:link>
                </g:if>
            </div>
        </div>
        <br clear="all"/>
    </body>
</html>