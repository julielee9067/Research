<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019-07-09
  Time: 09:59
--%>
<%@ page import="ca.georgebrown.gbcresearch.SubmissionType; ca.georgebrown.gbcresearch.Submission" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name='layout' content='main'/>
        <title>Research - Dashboard</title>
        <style>
            .paginationBox {
                display: inline-block;
            }
            .checkType input {
                zoom: 1.7;
            }
        </style>
    </head>
    <body>
    <script>
        function hideType(className) {
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

        function hideTypeLoad(className) {
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

        function setDefaultShowing(project, workshop, conference, researchPaper) {
            if (!project) {hideTypeLoad('PROJECT')}
            if (!workshop) {hideTypeLoad('WORKSHOP')}
            if (!conference) {hideTypeLoad('CONFERENCE')}
            if (!researchPaper) {hideTypeLoad('RESEARCH_PAPER')}
        }

        function checkWindowSizeHome(win) {
            if (win.width() < 850) {
                $('#searchUsersWindow').slideUp();
                $('#recommendedPeopleContent').slideUp();
                $('#recommendedUsersDisplayBtn').val('⇑');
                $('#searchUsersDisplayBtn').val('⇑');
            }
            else {
                $('#searchUsersWindow').slideDown();
                $('#recommendedPeopleContent').slideDown();
            }
        }

        function checkWindowSizeLoadHome(win) {
            if (win.width() < 850) {
                if ('${searchTextPerson}'.trim().size > 0){
                    $('#searchUsersWindow').slideDown();
                } else {
                    $('#searchUsersWindow').slideUp();
                }

                $('#recommendedPeopleContent').slideUp();
                $('#recommendedUsersDisplayBtn').val('⇑');
                $('#searchUsersDisplayBtn').val('⇑');
            }
            else {
                $('#searchUsersWindow').slideDown();
                $('#recommendedPeopleContent').slideDown();
            }
        }

        $(document).ready(function() {
            checkWindowSizeLoadHome($(window));
            $(window).on('resize', function(){
                var win = $(this);
                checkWindowSizeHome(win);
            });

            $('#searchUsersDisplayBtn').click(function() {

                if ($('#searchUsersWindow').css('display') == 'none') {
                    $('#searchUsersWindow').slideDown();
                    $('#searchUsersDisplayBtn').val('⇓');
                }
                else {
                    $('#searchUsersWindow').slideUp();
                    $('#searchUsersDisplayBtn').val('⇑');
                }
            });

            $('#latestArticleTab').click(function() {
                $('#latestArticleTab').addClass("activeTab");
                $('#submissionView').removeClass("hidden");
                $('#followingSubmissionsTab').removeClass('activeTab');
                $('#followingSubmissionsView').addClass('hidden');
                $('#interestedSubmissionsTab').removeClass("activeTab");
                $('#interestedSubmissionsView').addClass('hidden');
            });

            $('#followingSubmissionsTab').click(function() {
                $('#latestArticleTab').removeClass("activeTab");
                $('#submissionView').addClass("hidden");
                $('#followingSubmissionsTab').addClass('activeTab');
                $('#followingSubmissionsView').removeClass('hidden');
                $('#interestedSubmissionsTab').removeClass("activeTab");
                $('#interestedSubmissionsView').addClass('hidden');
            });

            $('#interestedSubmissionsTab').click(function() {
                $('#interestedSubmissionsTab').addClass('activeTab');
                $('#interestedSubmissionsView').removeClass('hidden');
                $('#latestArticleTab').removeClass("activeTab");
                $('#submissionView').addClass("hidden");
                $('#followingSubmissionsTab').removeClass("activeTab");
                $('#followingSubmissionsView').addClass("hidden");
            });

            $('#recommendedUsersDisplayBtn').click(function() {
                if ($('#recommendedPeopleContent').css('display') == 'none') {
                    $('#recommendedPeopleContent').slideDown();
                    $('#recommendedUsersDisplayBtn').val('⇓');
                }
                else {
                    $('#recommendedPeopleContent').slideUp();
                    $('#recommendedUsersDisplayBtn').val('⇑');
                }
            });

            if (${profile.findByAppuser(appuser).newUser}){
                console.log($('#profileSettingWindow  a'));
                $('#profileSettingWindow a').click();
            }

            $("#clearButtonPeople").click(function() {
                $("#userSearch").val('');
            });

            $(window).scroll(function() {
                if ($(window).scrollTop() + $(window).height() == $(document).height()) {

                    $('.paginateButtons > a').click();
                }
            });

            var maxLength = 1000;
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
    </script>

    <g:render template='/layouts/navigation'/>
        <div id="list-users" class="container-fluid content scaffold-list" role="main">
            <div class="card border-0">
                <div class = ".inline-flex">
                    <div class="rightSide">
                        <div class="peopleWindow card-body mt-3 bg-light border-bottom" style="min-height: 100px">
                            <h4 style="font-size: 1.143rem;font-weight: 400; text-align: left">Filter by Types</h4>
                            <g:link class="btn btn-sm btn-outline-primary mt-2" controller="homePage" action="index" params="[typeName: 'All']">All</g:link>
                            <g:each in="${SubmissionType.getAll()}" var="type">
                                <g:link class="btn btn-sm btn-outline-primary mt-2" controller="homePage" action="index" params="[typeName: type.name]">${type.name}</g:link>
                            </g:each>
                        </div>
                        <div class="peopleWindow card-body center bg-light border-bottom" style="margin-top: 15px">
                            <div class ="custom-dropdown-homepage">
                                <h4 style="font-size: 1.143rem;font-weight: 400;">Recommended Connections</h4>
                                <input type="button" class="btn btn-sm" value ='&#8657' id="recommendedUsersDisplayBtn">
                            </div>
                            <div id="recommendedPeopleContent">
                                    <g:render template="peopleSearch" model="[searchPeople: similarPeople, userSearch: searchTextPerson, total: similarPeople?.size(), div_id: 'recommendedPeopleContent', action: 'none', controller: 'homePage']"/>
                            </div>
                        </div>
                    </div>
                    <div class="leftHome">
                        <g:if test="${searchText}">
                            <h3 style="font-weight: 700; font-size: 20px; margin-top: 15px;">Search Results</h3>
                            <div class="leftContent card-body center mt-3 bg-light border-bottom">
                                <span>The search term "${searchText}" returned ${searchCount} results.</span>
                            </div>
                        </g:if>
                        <g:elseif test="${tagInput}">
                            <h3 style="font-weight: 700; font-size: 20px; margin-top: 15px;">Search Results</h3>
                            <div class="leftContent card-body center mt-3 bg-light border-bottom">
                                <span>The tag "${tagInput}" returned ${searchCount} results.</span>
                            </div>
                        </g:elseif>
                        <g:else>
                            <div class="card rounded-0 mb-3 mt-3 p-3 bg-light" style="border: 0; border-bottom: solid 1px lightgrey; height: 100px; text-align: center;">
                                The Research Portal is a place for George Brown employees to share their work with their colleagues
                                and connect with each other based on common interests. Submit your projects, research papers and workshop and conference summaries,
                                follow specific topics and individuals, and receive updates straight to your inbox when new content relevant to you is posted.
                            </div>
                            <div id="latestArticleTab" class="tab activeTab" style="margin-top: 10px; margin-bottom: 0">
                                <span>Latest Articles</span>
                            </div>
                            <g:if test="${0}">
                                <div id="followingSubmissionsTab" class="tab" style="margin-top: 10px; margin-bottom: 0">
                                    <span>Publications From Your Connections</span>
                                </div>
                                <div id="interestedSubmissionsTab" class="tab" style="margin-top: 10px; margin-bottom: 0">
                                    <span>Articles You Might Be Interested In</span>
                                </div>
                            </g:if>
                        </g:else>
                        <div id="submissionView" class="leftContent card-body center mt-0 bg-white p-0">
                            <g:render template="submissions" model="[list: resultList, div_id: 'submissionView', action: 'listSubmissionsSearch',
                                                                     searchCount: searchCount, searchInput: searchText, tagInput: tagInput,
                                                                     appuser: appuser, userProfile: userProfile, source: 'homePage', typeName: typeName,
                                                                     defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting]"/>
                        </div>
                        <g:if test="${!publicView}">
                            <div id="followingSubmissionsView" class="leftContent card-body center bg-white p-0 hidden">
                                <g:render template="submissions" model="[list: followingUsersSubmissions, div_id: 'followingSubmissionsView', action: 'listFollowingsSubmissionsSearch',
                                                                         searchCount: followingUsersSubmissionsCount, searchInput: searchText, tagInput: tagInput,
                                                                         appuser: appuser, userProfile: userProfile, source: 'homePage',
                                                                         defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting]"/>
                            </div>

                            <div id="interestedSubmissionsView" class="leftContent card-body center bg-white p-0 hidden">
                                <g:render template="submissions" model="[list: interestedSubmissions, div_id: 'interestedSubmissionsView', action: 'listInterestedSubmissionsSearch',
                                                                         searchCount: interestedSubmissionsCount, searchInput: searchText, tagInput: tagInput,
                                                                         appuser: appuser, userProfile: userProfile, source: 'homePage',
                                                                         defaultPaginationSetting: defaultPaginationSetting, userPaginationSetting: userPaginationSetting]"/>
                            </div>
                        </g:if>
                    </div>
                    <div class="leftHome">
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>