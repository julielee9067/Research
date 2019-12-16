<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019-11-28
  Time: 15:08
--%>

<%@ page import="ca.georgebrown.gbcresearch.SubmissionType; ca.georgebrown.gbcresearch.Submission" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <title>Research - Help</title>

    <script type="application/javascript">

        function validate() {
            var uploadedFile = document.getElementById("my-file-selector");
            if (uploadedFile.value == "") {
                $("#clientMsg").html("<strong>No file uploaded!</strong>");
                return false
            }
            else {
                var button = document.getElementById("save");
                button.src = "Research/grails-app/views/uploadedFiles/edit.gsp";
                button.click();
                $("#clientMsg").html("<strong>You have selected a file</strong>");
            }
        }
        function setUploadName(el) {
            $('#upload-file-info').html(el.files[0].name);
        }
    </script>

    <style>
    .fileName {
        min-width: 300px;
    }

    .fileDownload {
        width: 70px;
    }
    </style>
</head>

<body>
    <g:render template='/layouts/navigation' model="[which: 'help']"/>
    <div id="list-metrics" class="container-fluid content scaffold-list" role="main">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title pageTitle">Help Documents</h1>
            </div>
                <table style="width: 90%; max-width: 1000px;" class="mx-auto my-3">
                    <tr style="border-bottom-style: groove; border-bottom-width: thick">
                        <td class="fileName" style="font-weight: bold"> Name </td>
                        <td class="fileDownload" style="text-align: center; font-weight: bold"> Download </td>
                    </tr>
                    <g:each in="${fileList}" var="uploadedFile">
                        <tr class ="rowTable" style="border-bottom-style: dotted; border-bottom-width: thin">
                            <td class="fileName"> ${uploadedFile.originalFilename} </td>
                            <td class="fileDownload" style="text-align: center">
                                <g:link controller="uploadFile" action="download" id="${uploadedFile.id}" target="_blank"
                                        class="btn btn-sm btn-success btn-sm" style="border-color: white; border-width: thin;"> Download </g:link>
                            </td>
                        </tr>
                    </g:each>
                    <tr><td class="fileName"><br></td><td class="fileDownload"><br></td></tr>
                    <tr class="rowTable">
                        <g:form method="POST" controller="help" action="uploadHelpDocument" enctype="multipart/form-data">
                            <td class="fileName" style="text-align: left">
                                <span id="upload-file-info"></span>
                                <label class="btn btn-success btn-sm" style="alignment: right" for="my-file-selector" id="lbFileSelector">
                                    <input id="my-file-selector" name="myFile" type="file" style="display:none;" onchange="setUploadName(this)">Choose File
                                </label>
                            </td>
                            <td class="fileDownload">
                                <input type="hidden" name="storageDir" value="${grailsApplication.config.file.upload.directory}"/>
                                <input type="submit" name="send" class="btn btn-success btn-sm" value="Upload Selected File" style="background-color: #005AA5;" onclick="return validate();" id="btnSubmitFile"/>
                            </td>
                        </g:form>
                    </tr>
                </table>
                <span style="color:#bd2130; text-align: center"> ** If you wish to delete the document, please delete it on the database. </span>
        </div>
    </div>
</body>
</html>

