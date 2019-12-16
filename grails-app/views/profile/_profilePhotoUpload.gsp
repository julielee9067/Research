<script type="application/javascript">
    function validate() {
        var uploadedFile = document.getElementById("my-file-selector");
        if (uploadedFile.value == "") {
            $("#clientMsg").html("<strong>No file uploaded!</strong>");
            return false
        }
        else {
            $("#clientMsg").html("<strong>You have selected a file</strong>");
            return true;
        }
    }
    function setUploadName(el) {
        $('#upload-file-info').html(el.files[0].name);
    }
</script>

<div class="card-body center mt-3 form-group bg-light">
    <div class="card-title h6" style="color: black">Upload Profile Picture</div>
    <div class="mx-auto">
        <g:form url="[controller: 'uploadFile', action: 'upload']" name="profilePhoto" method="POST" controller="uploadFile" action="upload" enctype="multipart/form-data">
            <div id="fileInputAndSubmit" class="m-2 text-left bg-white">
                <label><g:checkBox id="showEmailCheck" style="color: black" name="showEmail" value="${settings?.showEmail}"/> Show my email to others </label>
                <label class="btn btn-sm btn-secondary" for="my-file-selector" id="lbFileSelector">
                    <input id="my-file-selector" name="myFile" type="file" style="display:none"
                           onchange="setUploadName(this)">Choose File
                </label>
                <span class='label label-info mx-3' id="upload-file-info"></span>
                <input type="hidden" name="storageDir" value="${grailsApplication.config.file.profilePhoto.directory}"/>
                <input type="hidden" name="source" value="${source}"/>
                <input type="hidden" name="sourceId" value="${objId}"/>
                <input type="hidden" name="returnController" value="${returnController}"/>
                <input type="hidden" name="returnAction" value="${returnAction}"/>
            </div>
            <input type="submit" name="send" class="btn btn-success btn-sm" value="Submit" onclick="return validate();" id="btnSubmitFile"/>
            <span id="clientMsg"></span>
        </g:form>
    </div>
</div>