<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
       <meta name="layout" content="main"/>
</head>
<body>
    ${msg}
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
    <div class="panel">
    <g:form method="POST" action="uploadTest" enctype="multipart/form-data">
        <label>Upload File<span
                style="color: red;">*</span></label><br/>
        <div id="fileInputAndSubmit">
            <label class="btn btn-success" for="my-file-selector">
                <input id="my-file-selector" name="myFile" type="file" style="display:none"
                       onchange="setUploadName(this)">Choose File
            </label>
            <span class='label label-info' id="upload-file-info"></span>
            <input type="hidden" name="storageDir" value="${grailsApplication.config.file.upload.directory}"/>
            <input type="hidden" name="source" value="Project"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div><br>
        <input type="submit" name="send" class="btn btn-primary" value="Submit" onclick="return validate();"/><br><br>
        <span id="clientMsg"></span>
    </g:form>
    </div>
</body>
</html>