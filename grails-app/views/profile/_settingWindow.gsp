<%@ page import="ca.georgebrown.gbcresearch.security.Appuser; ca.georgebrown.gbcresearch.Tag; ca.georgebrown.gbcresearch.Setting" %>

%{--<asset:stylesheet src="research-portal.css"/>--}%

<script type="text/javascript">
    function toggleDisplay() {
        if ($('#collapseProfileSettings').hasClass('show')) {
            $('#collapseProfileSettings').removeClass('show');
            $('#collapseProfileSettings').addClass('hide');
        } else {
            $('#collapseProfileSettings').removeClass('hide');
            $('#collapseProfileSettings').addClass('show');
        }
    }

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

    function getInterestsNames() {
        var appuserId = '${appuser.id}';

        populateInterestsProfile($('#tagInterest~span li.select2-selection__choice'));

        ${remoteFunction(controller: 'tags', action: 'ajaxInterestsName', params: '\'id=\' + escape(appuserId)')}
    }

    function clickUpdateProfilePhoto() {

        var uploadedFile = document.getElementById("my-file-selector");
        if (uploadedFile.value != "") {
            $('#btnSubmitFile').click();
        }
        else{
            $('#updateProfileBtn').click();
        }

        if ("${returnController == 'profile'}" == 'true') {

            getInterestsNames();
        }

        $('#collapseProfileSettings').addClass('hide');
        $('#collapseProfileSettings').removeClass('show');
    }

</script>
<div id ="profileSettingWindow" class="card  mb-1 hideThis border-0" role="tablist">
    <div id="profile-settings-panel">
        <div class="card-body  text-secondary ">
                <div class = "settingsForm">
                    <div class="settingsBoxes">
                        <g:form name="settings" url="[controller: 'profile', action: 'updateProfile']" method="POST" controller="uploadFile" action="upload" enctype="multipart/form-data">
                            <div class = "leftSettings">
                                <h4>Profile Picture</h4>
                                <div class="card-body center mt-3 form-group bg-light border-bottom">
                                    <div class="card-title h6">Upload Profile Picture</div>
                                    <div class="mx-auto">
                                            <div id="fileInputAndSubmit" class="m-2 text-left bg-white">
                                                <label class="btn btn-sm btn-secondary" for="my-file-selector" id="lbFileSelector">
                                                    <input id="my-file-selector" name="myFile" type="file" style="display:none"
                                                           onchange="setUploadName(this)">Choose File
                                                </label>
                                                <span class='label label-info mx-3' id="upload-file-info"></span>
                                                <input type="hidden" name="storageDir" value="${grailsApplication.config.file.profilePhoto.directory}"/>
                                                <input type="hidden" name="source" value="Profile"/>
                                                <input type="hidden" name="sourceId" value="${appuser?.id}"/>
                                                <input type="hidden" name="returnController" value="${returnController}"/>
                                                <input type="hidden" name="returnAction" value="${returnAction}"/>
                                            </div>
                                            <input type="submit" name="send" class="btn btn-success btn-sm" value="Submit" onclick="return validate();" id="btnSubmitFile"/>
                                            <span id="clientMsg"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="rightSettings">
                                <h4>Privacy Settings</h4>
                                <label><g:checkBox id="showEmailCheck" name="showEmail" value="${settings?.showEmail}" /> Show my email to others </label>
                                <label><g:checkBox id="showInterestsCheck" name="showInterests" value="${settings?.showInterests}" /> Show my interests to others </label>
                                <label><g:checkBox id="showRecentlyViewedCheck" name="showRecentlyViewed" value="${settings?.showRecentlyViewed}" /> Show my recently viewed to others</label>
                                <h4>Notification Settings</h4>
                                <g:if test="${setting.get(code: Setting.CODE.ADDITIONAL_NOTIFICATIONS) == 'true'}">
                                    <label><g:checkBox id="notifyInterestedCheck" name="notifyInterestedPost" value="${settings?.notifyInterestedPost}" /> Email me when someone makes a submission tagged with my interests:  </label>
                                    <label><g:checkBox id="notifyFollowedUserCheck" name="notifyFollowedUserPost" value="${settings?.notifyFollowedUserPost}" /> Email me when someone I follow makes a submission:  </label>
                                </g:if>
                                <label> <g:checkBox id="notifyDailyDigestCheck" name="notifyDailyDigest" value="${settings?.notifyDailyDigest}" /> Email me a daily digest:</label>
                                <h4>General Settings</h4>
                                <label> Select a method to load more publications on Dashboard.</label>
                                <g:select name="scrollSetting" from="${scrollSettingsList}"  optionKey='key' optionValue="value" value="${settings?.scrollSetting}"  class="form-control" style="width: 90%;box-shadow: none;"/>
                                <div class="form-group" id="editInterestsForm">
                                    <h4>Interests</h4>
                                    <g:select from="${Tag.findAllByStatus('Active')}"
                                              optionKey="id"
                                              optionValue="name"
                                              multiple="multiple"
                                              id="tagInterest"
                                              value ="${appuser?.tags}"
                                              name="tagsInterest"
                                              class="form-control editField tagSelect"
                                              aria-describedby="tagHelp">
                                    </g:select>
                                </div>
                            </div>
                            <div class="leftSettings">
                                    <h4>Biography</h4>
                                    <g:textArea id="newBio" name="newBiography" value = "${biography}"></g:textArea>
                            </div>
                            <g:submitButton id="updateProfileBtn" name="updateProfileBtn" value="Update" class="btn btn-success hidden"></g:submitButton>
                        </g:form>
                    </div>
                    <div class ="centerSettings">
                        <input type ="button" onclick="clickUpdateProfilePhoto()" value = "Update Profile" class ="btn btn-success"/>
                    </div>
                </div>
        </div>
    </div>
</div>