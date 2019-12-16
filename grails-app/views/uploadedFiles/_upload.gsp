<script type="application/javascript">

    function validate() {
        var uploadedFile = document.getElementById("my-file-selector");
        if (uploadedFile.value == "") {
            $("#clientMsg").html("<strong>No file uploaded!</strong>");
            return false
        }
        else {
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
                $("#clientMsg").html("<strong>You have selected a file</strong>");
                return true;
            }
            else {
                bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                return false;
            }

        }
    }

    function setUploadName(el) {
        $('#upload-file-info').html(el.files[0].name);
    }
</script>

<div class="card-body center mt-3 form-group border-bottom bg-light">
    <div class="card-title h6">Upload document</div>
    <span colspan="3" class="pull-left ml-2" style="font-size: 12px; font-style: italic;">. Allowed File Extensions: ${grailsApplication.config.file.upload.allowedExtensions.join(', ')}</span> <br>
    <span colspan="3" class="pull-left mb-2 ml-2" style="font-size: 12px; font-style: italic;">. Maximum File Size: ${(grailsApplication.config.file.upload.maxSize / 1048576)} MB</span>
    <br clear="all" />
    <div class="mx-auto">
        <g:form method="POST" controller="uploadFile" action="upload" enctype="multipart/form-data">
            <div id="fileInputAndSubmit" class="m-2 text-left border border-bottom bg-white">
                <label class="btn btn-sm btn-secondary" for="my-file-selector" id="lbFileSelector">
                    <input id="my-file-selector" name="myFile" type="file" style="display:none" onchange="setUploadName(this)">Choose File
                </label>
                <span class='label label-info mx-3' id="upload-file-info"></span>
                <input type="hidden" name="storageDir" value="${grailsApplication.config.file.upload.directory}"/>
                <input type="hidden" name="source" value="${source}"/>
                <input type="hidden" name="sourceId" value="${objId}"/>
                <input type="hidden" name="returnController" value="${returnController}"/>
                <input type="hidden" name="returnAction" value="${returnAction}"/>
                <input type="hidden" name="createNew" value="${params.createNew ?: 'false'}"/>
            </div>
            <input type="submit" name="send" class="btn btn-success btn-sm" value="Upload Selected File" style="background-color: #005AA5;" onclick="return validate();" id="btnSubmitFile"/>
            <span id="clientMsg"></span>
        </g:form>
    </div>
</div>