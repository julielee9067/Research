<%@ page import="ca.georgebrown.gbcresearch.Submission" %>

<html>
    <head>
        <meta name='layout' content='main'/>
        <title>Research - ${appuser.displayName}'s Profile</title>
        <style type="text/css" media="screen">
            .center {
                text-align: center;
            }

            .recommendedProjects {
                width:100%;
            }

            .descriptionSection {
                max-height: 200px;
                overflow-y: auto;
            }

            .selectedType {
                background-color: #005293;
                color: white;
            }

            .viewCheck button {
                display: none;
            }

            .viewCheck input {
                margin-right: 5px;
                zoom: 2;
            }

            .followBtnColor {
                background-color: #005AA5;
            }

            .unfollowBtnColor{
                background-color: #999999;
            }

            .show-read-more .more-text{
                display: none;
            }
        </style>
    </head>

    <body>
        <script>
        function followUser(profile_id, appuser_id, appuser_fname, appuser_lname) {
            ${remoteFunction(controller: 'profile', action: 'addFollowing', params: '\'profile_id=\' + profile_id ')}
            var follow = 0;
            var following = $("#followBtn").val();

            if (following == 'FOLLOW') {
                follow = 1;
                $("#followBtn").val("UNFOLLOW");
                $("#followBtn").removeClass('btn followBtnColor');
                $("#followBtn").addClass('btn unfollowBtnColor');
            }
            else {
                follow = 0;
                $("#followBtn").val("FOLLOW");
                $("#followBtn").removeClass('btn unfollowBtnColor');
                $("#followBtn").addClass('btn followBtnColor');
                $("#follower-"+ appuser_id).remove();
            }
            ${remoteFunction(controller: 'profile', action: 'ajaxGetFollowerCount', params: '\'id=\' + escape(profile_id) ', onSuccess: 'changeFollowersText(data, follow)')}
        }

        function changeFollowersText(data, follow) {
            if (follow) {
                $("#followersText").text("Followers (" + (data.followers + 1) + ")");
            }
            else {
                $("#followersText").text("Followers (" + (data.followers - 1) + ")");
            }
        }

        function hideStatus(className) {
            var classname = document.getElementsByClassName(className);
            for (var i = 0; i < classname.length; i++) {
                if (classname[i].classList.contains('hidden')) {
                    classname[i].classList.remove('hidden');
                }
                else {
                    classname[i].classList.add('hidden');
                }
            }
        }

        function hideStatusLoad(className) {
            var classname = document.getElementsByClassName(className);
            for (var i = 0; i < classname.length; i++) {
                if (classname[i].classList.contains('hidden')) {
                    classname[i].classList.remove('hidden');
                }
                else {
                    classname[i].classList.add('hidden');
                }
            }
        }

        function setDefaultShowing(publish, edit, submitted, rejected) {
            if (!publish) {hideStatusLoad('PUBLISHED')}
            if (!edit) {hideStatusLoad('IN_EDIT')}
            if (!submitted) {hideStatusLoad('SUBMITTED')}
            if (!rejected) {hideStatusLoad('RETURNED')}
        }

        function customFitText(object, classname) {
            var heightText = ($(classname).css('max-height'));
            var height = heightText.substr(0, heightText.length - 2);
            var width = object.scrollWidth;
            var text = object.innerHTML.trim().length;
            var fontSize = Math.sqrt((height * width) / text);

            if (classname == '.emailCell') {
                fontSize = fontSize * 1.25;
            }
            else {
                fontSize = fontSize * 0.93;
            }

            object.style.fontSize = fontSize + 'px';
        }

        function windowResizeProfile () {
            if ($(window).width() < 558) {
                customFitText(document.querySelector('.nameCell'), '.nameCell');
                customFitText(document.querySelector('.emailCell'), '.emailCell');
            }
            else {
                document.querySelector('.nameCell').style.fontSize = '40px';
                document.querySelector('.emailCell').style.fontSize = '16px';
            }
        }

        function populateNewBio() {
            var newBio = $('#newBio').val();
            $('#profileBiography').text(newBio);
        }

        function populateInterestsProfile(data) {
            var interestsBox =  $('.interests .similarPersonInterests');
            interestsBox.empty();
            var tagList = data;

            for(var i = 0; i < tagList.length; i++) {
                var name = tagList[i].title;
                interestsBox.append("<div class='tagBox'>" + name + "</div>");
            }
            populateNewBio();
        }

        $(document).ready(function() {
            setDefaultShowing(${profile.showPublished}, ${profile.showCreated}, ${profile.showSubmitted}, ${profile.showRejected});
            $('#profile-settings-panel').css('display', 'none');
            $('#showPublished').click( function () {hideStatus('PUBLISHED');});
            $('#showRejected').click( function() {hideStatus("RETURNED")});
            $('#showSubmitted').click( function() {hideStatus("SUBMITTED")});
            $('#showCreated').click( function() {hideStatus("IN_EDIT")});
            $('#followingUsersDisplayBtn').click(function() {
                if ($('#following').css('display') == 'none') {
                    $('#following').slideDown();
                    $('#followingUsersDisplayBtn').val('⇓');
                }
                else {
                    $('#following').slideUp();
                    $('#followingUsersDisplayBtn').val('⇑');
                }
            });

            $('#followersUsersDisplayBtn').click(function() {
                if ($('#follower').css('display') == 'none') {
                    $('#follower').slideDown();
                    $('#followersUsersDisplayBtn').val('⇓');
                }
                else {
                    $('#follower').slideUp();
                    $('#followersUsersDisplayBtn').val('⇑');
                }
            });

            $("#clearButton").click(function() {
                $("#searchInput").val('');
                $('#tag').val(null).trigger('change');
            });

            $("#tag").select2();
            $("#tagInterest").select2({width: '90%'});
            $('#submissionTab').click(function() {
                $('#submissionTab').addClass("activeTab");
                $('#submissionView').removeClass("hidden");
                $('#viewTab').removeClass('activeTab');
                $('#viewedView').addClass('hidden');
            });

            $('#viewTab').click(function() {
                $('#submissionTab').removeClass("activeTab");
                $('#submissionView').addClass("hidden");
                $('#viewTab').addClass('activeTab');
                $('#viewedView').removeClass('hidden');
            });

            $(window).resize( function () {
                windowResizeProfile();
            });

            $(document).ready(function() {
                $('#btnSubmitFile').addClass('hidden');
                $('#editProfile').click ( function() {
                    if ($('#profile-settings-panel').css('display') == 'none') {
                        $('#profile-settings-panel').slideDown();
                    }
                    else {
                        $('#profile-settings-panel').slideUp();
                    }
                });

                $('#updateProfileBtn').click ( function() {
                    if ($('#profile-settings-panel').css('display') == 'none') {
                        $('#profile-settings-panel').slideDown();
                    }
                    else {
                        $('#profile-settings-panel').slideUp();
                    }
                });
            });

            var maxLength = 940;

            $(".show-read-more").each(function() {
                var myStr = $(this).text();
                if ($.trim(myStr).length > maxLength){
                    var newStr = myStr.substring(0, maxLength);
                    var removedStr = myStr.substring(maxLength, $.trim(myStr).length);
                    $(this).empty().html(newStr);
                    $(this).append('<a href="javascript:void(0);" class="read-more" style="font-size: 15px; font-weight: bolder;">&nbsp;read more...</a>');
                    $(this).append('<span class="more-text">' + removedStr + '</span>');
                }
            });

            $(".read-more").click(function() {
                $(this).siblings(".more-text").contents().unwrap();
                $(this).remove();
            });
        });

        function changeFilter(chk) {
            var filterId = chk.id;
            var status = $('#'+filterId).is(':checked');
            ${remoteFunction(controller: 'profile', action: 'ajaxSetFilter', params: "'id='+escape(${profile.id})+'&profileSetting='+filterId+'&setting='+status", onSuccess: '')}
        }

        </script>
        <g:if test="${publicProfile}">
            <g:render template='/layouts/navigation' model=""/>
        </g:if>
        <g:else>
            <g:render template='/layouts/navigation' model="[which: 'profile']"/>
        </g:else>
        <div class="h-50 my-0 ml-5 border-0" style="min-height:250px;background-color: white">
            <div class="card border-0" style="background-color: white">
                <div class="card-header newProfileHearder border-0" style="background-color: white">
                    <div class="profilePicture">
                        <g:if test="${image}">
                            <img src="${createLink(controller: "profile", action: "displayImage", params: [user: profileUser.id])}"
                                 id="profilePicture" title="profile-picture"
                                 onerror="this.src='${assetPath(src: "default-profile.png")}'"/>
                        </g:if>
                        <g:else>
                            <asset:image src="default-profile.png" id="profilePicture" title="profile-picture"/>
                        </g:else>
                    </div>

                    <div class="profileInfo" style="width: 300px;">
                        <table class="table" style="table-layout: fixed;width: 300px; background-color: white">
                            <tr class="textRow">
                                <td class="m-0 h1 text-primary" style="vertical-align: bottom;font-size: 1.5rem;font-weight: 600;width: 300px">
                                    <div>${profileUser?.displayName}</div>
                                </td>
                            </tr>
                            <tr class="textRow">
                                <td class="m-0 text-dark">
                                    <div class="emailCell">
                                        <g:if test="${!publicProfile || settings?.showEmail}">
                                            <a href="mailto:${profileUser?.email}"> ${profileUser?.email} </a>
                                        </g:if>
                                    </div>
                                </td>
                            </tr>
                            <g:if test="${publicProfile}">
                                <tr class="textRow">
                                    <td>
                                        <input id="followBtn" type="button"
                                               value="<g:if test="${following}">UNFOLLOW</g:if><g:else>FOLLOW</g:else>"
                                           class="<g:if test="${following}">btn unfollowBtnColor</g:if><g:else>btn followBtnColor</g:else> mx-auto text-white" style="box-shadow: none; width: 120px;"
                                           onclick="followUser(${profileUser.id}, ${appuser.id}, '${appuser.firstName}', '${appuser.lastName}')"/>
                                    </td>
                                </tr>
                            </g:if>
                            <tr class="textRow">
                                <td>
                                    <g:if test="${profileUser.id == appuser.id}">
                                        <button class="btn btn-sm btn-dark py-2 px-3 mt-1" style="color: #fff;box-shadow: none;" id="editProfile">EDIT PROFILE</button>
                                    </g:if>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="profileInfo w-50" >
                        <table class="table" style="min-width: 400px;background-color: white">
                            <tr class="textRow">
                                <td class="m-0 h1 text-primary" style="vertical-align: bottom;font-size: 1.5rem;font-weight: 600;">
                                    Profile
                                </td>
                            </tr>
                            <tr class="textRow">
                                <td rowspan="2" class="py-0">
                                    <div class="bio" style="color: black">
                                        ${profile.biography ?: 'There is currently no biography for this user.'}
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <g:if test="${!publicProfile}">
                <g:render template='/profile/settingWindow'
                          model="[returnAction: 'index', returnController: 'profile']"/>
            </g:if>
        </div>
        <br clear="all"/>
        <div id="list-users" class="container-fluid content scaffold-list" role="main">
            <div class="card border-0">
                <div class="profileContent">
                    <div class="submissionInfo">
                        <div class="recommendedProjects card-body center mt-3 border-0 bg-white">
                            <g:if test="${appuser == profileUser}">
                                <div id="submissionTab" class="tab activeTab">My Submissions</div>
                            </g:if>
                            <g:else>
                                <div id="submissionTab" class="tab activeTab">${profileUser.firstName}'s Submissions</div>
                            </g:else>

                            <g:if test="${!publicProfile || settings?.showRecentlyViewed}">
                                <g:if test="${appuser == profileUser}">
                                    <div id="viewTab" class="tab">My Recently Viewed</div>
                                </g:if>
                                <g:else>
                                    <div id="viewTab" class="tab">${profileUser.firstName}'s Recently Viewed</div>
                                </g:else>
                            </g:if>

                        <div id="submissionView" class="submissionList center border-0 bg-light">
                            <g:if test="${submissions}">
                                <g:if test="${!publicProfile}">
                                    <div class="viewCheck text-left mb-3 table-bordered py-2" style="border-radius: 5px;">
                                        <div class="my-3 col-md-2" style="white-space: nowrap;">
                                            <b>FILTER BY:</b>
                                        </div>
                                        <div class="mb-2 col-md-2" style="white-space: nowrap;">
                                            <g:checkBox
                                                    name="showPublished"
                                                    value="${profile.showPublished}"
                                                    onchange="changeFilter(this);">
                                            </g:checkBox>
                                            <span style="white-space: nowrap;">
                                                ${ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.PUBLISHED}
                                            </span>
                                        </div>

                                        <div class="mb-2 col-md-2" style="white-space: nowrap;">
                                            <g:checkBox
                                                    name="showSubmitted"
                                                    value="${profile.showSubmitted}"
                                                    onchange="changeFilter(this);">
                                            </g:checkBox>
                                            <span style="white-space: nowrap;">
                                                 ${ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.SUBMITTED}
                                            </span>
                                        </div>

                                        <div class="mb-2 col-md-3" style="white-space: nowrap;">
                                            <g:checkBox
                                                    name="showRejected"
                                                    value="${profile.showRejected}"
                                                    onchange="changeFilter(this);">
                                            </g:checkBox>
                                                ${ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.RETURNED}
                                        </div>

                                        <div class="mb-2 col-md-3" style="white-space: nowrap;">
                                            <g:checkBox
                                                    name="showCreated"
                                                    value="${profile.showCreated}"
                                                    onchange="changeFilter(this);">
                                            </g:checkBox>
                                            <span style="white-space: nowrap;">
                                                ${ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.IN_EDIT}
                                            </span>
                                        </div>
                                    </div>
                                </g:if>

                                <g:each in="${submissions}" var="submission">
                                    <div class="submissionContent border-bottom ${ ca.georgebrown.gbcresearch.Submission.SUBMIT_STATUS.find {
                                        it.value == submission.submitStatus
                                    }.key}">
                                        <div class="card rounded-0 mb-2 border-0 p-3">
                                            <table class="table">
                                                <tr>
                                                    <td class="subOwner pb-0">
                                                        <g:if test="${!publicProfile && submission?.submitStatus}">
                                                            <div class="submissionStatus ${Submission.SUBMIT_STATUS.find {
                                                                it.value ==  submission?.submitStatus
                                                            }.key}-color" style="width: fit-content; padding: 5px 25px; font-size: 12px; margin-bottom: 5px;box-shadow: 2px 2px lightgrey">
                                                                ${submission?.submitStatus}
                                                            </div>
                                                        </g:if>
                                                    </td>
                                                    <td rowspan="2" class="tagNames">
                                                        <g:if test="${!publicProfile && (submission?.submitStatus != Submission.SUBMIT_STATUS.PUBLISHED ||
                                                                ca.georgebrown.gbcresearch.security.Role.findByAuthority('ROLE_PUBLISHER') in appuser.authorities)}">
                                                            <g:link controller="submissions" action="edit" params="[source: source, profile_id: profile_id]" id="${submission.id}" class="pull-right btn btn-light" style="text-decoration: none;">Edit</g:link>
                                                        </g:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="subDate pt-0" style="color: black"> <g:formatDate format="MMM dd, yyyy" date="${submission?.submissionDate}"/> </td>
                                                </tr>
                                                <tr> <td colspan="3" class="subType pt-3 pb-0"> ${submission?.type?.name} </td></tr>
                                                <tr>
                                                    <td colspan="3" class="subTitle">
                                                        <div> <g:link
                                                                    params="${['type': 'tbd', 'id': submission?.id, 'source': source, 'profile_id': profile_id]}"
                                                                    controller="homePage" action="linkToPage">${submission?.title}</g:link> </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3" class="subDescription pt-0">
                                                        <div style="color: black" class="show-read-more"> ${submission?.description} </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </g:each>
                            </g:if>
                            <g:else>
                                <div class="submissionContent"><div class="section">No submissions.</div></div>
                            </g:else>
                        </div>
                        <div id="viewedView" class="viewedList center hidden border-0 bg-light">
                            <g:if test="${recentlyViewed}">
                                <g:each in="${recentlyViewed}" var="submission">
                                    <div class="submissionContent">
                                        <div class="card rounded-0 mb-2 border-0 p-3">
                                            <table class="table">
                                                <tr>
                                                    <td class="subType pt-3 pb-0">
                                                        ${submission?.type?.name}
                                                    </td>
                                                    <td class="subOwner pb-0">
                                                        <g:if test="${submission?.submitter?.username == 'admin'}">
                                                            <g:if test="${submission?.principleInvestigators?.size()}">
                                                                ${submission.principleInvestigators.first().displayName}
                                                            </g:if>
                                                        </g:if>
                                                        <g:else>
                                                            <g:link controller="profile" params="${[profile_id: submission?.submitter?.id, source: 'profile']}" action="index"> ${submission?.submitter?.firstName} ${submission?.submitter?.lastName} </g:link>
                                                        </g:else>
                                                    </td>
                                                    <td rowspan="2" class="subImage">
                                                        <img src="${createLink(controller: "profile", action: "displayImage", params:[user:submission?.submitter?.id])}" onerror="this.src='${assetPath(src: "default-profile.png")}'" class ="profilePicMini" title="profile-picture"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="subTitle" style="max-width: 50%">
                                                        <g:link params="${['type': 'tbd', 'id': submission?.id, 'source': source]}" controller="homePage" action="linkToPage">${submission?.title}</g:link>
                                                    </td>
                                                    <td class="subDateDashboard pt-0">
                                                        <g:formatDate format="MMM dd, yyyy" date="${submission?.publicationDate}"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3" class="subDescription pt-0">
                                                        <div style="color: black" class="show-read-more"> ${submission?.description} </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </g:each>
                            </g:if>
                            <g:else>
                                <div class="submissionContent"> <div class="section">No content has been viewed.</div> </div>
                            </g:else>
                        </div>
                    </div>
                </div>

                <div class="info" style="margin-top: 55px;">
                    <div class="following card-body center mt-3 bg-light border-bottom">
                        <div class="custom-dropdown-homepage">
                            <h4 id="followingText" style="font-size: 1.143rem;font-weight: 400; margin-left: -32px">Following (${profileUser?.following?.size()})</h4>
                        </div>
                        <div id="following" class="paginationResults">
                            <g:include action="following" controller="profile"
                                       params='[profile_id: profileUser?.id, source: "profile"]'/>
                        </div>
                    </div>
                    <div class="followers card-body center mt-3 border-bottom bg-light">
                        <div class="custom-dropdown-homepage">
                            <h4 id="followersText" style="font-size: 1.143rem;font-weight: 400; margin-left: -32px">Followers (${profileUser?.followedBy?.size()})</h4>
                        </div>
                        <div id="follower" class="paginationResults">
                            <g:include action="follower" controller="profile"
                                       params='[profile_id: profileUser?.id, source: "profile"]'/>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
        </div>
    </div>
    </body>
</html>