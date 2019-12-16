<%@ page import="ca.georgebrown.gbcresearch.Submission" %>
<style>
    .resTable tbody tr{
        border: 1px solid lightgray;
    }
</style>

<script>
    function deleteFileConfirm(id) {
        bootbox.confirm({
            message: "<p style='white-space: nowrap; margin-top: 20px'>Are you sure you want to delete this file?</p>",
            callback: function(result) {
                if (result) {
                    var isValid = true;
                    $(".requiredInput2").each(function () {
                        if ($(this).val() === '' || $(this).val() === 'null') {
                            isValid = false;
                            $(this).css({'border-color': '#FF0000'});
                        }
                    });
                    if (isValid) {
                        var button = document.getElementById("save");
                        button.src = "Research/grails-app/views/uploadedFiles/edit.gsp";
                        button.click();
                        $("#deleteFile_"+id).click();
                    }
                    else {
                        bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                    }
                }
            }
        });
    }
</script>

<a id="fileAnchor"></a>
<g:if test="${uploadedFileList?.size()}">
    <div class="table-responsive border-top">
        <div id="table-scroll" class="table-responsive">
            <table class="resTable table table-sm table-hover table-bordered table-striped border-bottom mb-0 ">
                <thead>
                  <tr>
                      <th>Name</th>
                      <th>Upload Date</th>
                      <th>Uploaded By</th>
                      <g:if test="${accessRights[Submission.RIGHT.EDIT] && !view}">
                         <th></th>
                      </g:if>
                  </tr>
                </thead>
                <tbody>
                  <g:each in="${uploadedFileList}" var="uploadedFile">
                  <tr>
                      <td style="min-width: 150px"><g:link controller="uploadFile" action="download" target="_blank" id="${uploadedFile.id}">${uploadedFile.originalFilename}</g:link></td>
                      <td><g:formatDate date="${uploadedFile.dateCreated}" format="yyyy-MM-dd"/></td>
                      <td>${uploadedFile.uploadedBy?.displayName}</td>
                      <g:if test="${accessRights[Submission.RIGHT.EDIT] && !view}">
                          <td style="text-align: center; width: 80px;">
                              <g:form method="post" controller="${returnController}" action="${returnAction}" params="[createNew: params.createNew ?: 'false', submissionId: uploadedFile.objId]">
                                  <button type="button" class="btn btn-sm btn-outline-danger" onclick="deleteFileConfirm(${uploadedFile.id});">Delete</button>
                                  <button type="submit" name="deleteFile.${uploadedFile.id}" style="display: none;" id="deleteFile_${uploadedFile.id}"></button>
                              </g:form>
                           </td>
                      </g:if>
                  </tr>
                  </g:each>
                </tbody>
            </table>
        </div>
    </div>
</g:if>