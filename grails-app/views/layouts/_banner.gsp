<%@ page import="ca.georgebrown.gbcresearch.Setting" %>

<style>
    .headerUL {
        list-style: none;
        padding-right: 20px;
    }

    .headerUL li {
        display: inline-block;
        vertical-align: middle;
        margin-left: 20px;
        font-size: 14px;
        margin-top: auto;
        margin-bottom: auto;
    }

    .headerUL li a {
        color: white !important;
        text-decoration: none;
    }

    .headerUL li.active {
        border-bottom: 2px white solid;
    }

    .headerUL li.active a {
        background-color: #005AA5 !important;
    }
</style>

<script>
    function checkWindowSize( win) {
        if (win.width() < 950 && win.width() >= 628) {
            $('.navbar-title').text('Research Portal');
            $('.navbar-title').show();
        }
        else if (win.width() < 628) {
            $('.navbar-title').hide();
            $('.navbar-title').text('Research');
        }
        else {
            $('.navbar-title').text('Research');
            $('.navbar-title').show();
        }
    }
    $(document).ready(function() {
        checkWindowSize($(window));
        $('.clickProfile').click( function() {
            if ($('#menu3').hasClass('show')) {
                $('#drop3').attr('aria-expanded', false);
                $('#menu3').removeClass('show');
            }
            else {
                $('#drop3').attr('aria-expanded', true);
                $('#menu3').addClass('show');
            }
        });

        $(window).on('resize', function(){
            var win = $(this);
            checkWindowSize(win);
        });
    });

    jQuery.noConflict();
    $(document).ready(function () {
        var url = window.location;
        $('.dropdown-toggle').dropdown();
    });
</script>

<div style="background-color: #005AA5;">
    <div class="card border-0">
        <nav class=" navbar navbar-expand-sm navbar-light mb-0 py-0" style="background-color: #005AA5; min-height: 100px;">
            <div class="">
                <div class="row mr-5">
                    <asset:image src="gbcLogo2.png" title='GBC Logo' width="120" class="img-fluid ml-5" style="margin-right: -20px;"/>
                    <span class="text-light ml-5 my-auto" style="line-height: 3; white-space: nowrap;"> <b>RESEARCH PORTAL</b></span>
                </div>
            </div>
            <div id="navbarBanner" style="height: 100px;"></div>
            <div id="headerUL1">
                <nav class="navbar navbar-default border-0 text-white p-0 mb-0" style="background-color:#005AA5; ">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                        </div>
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="headerUL nav navbar-nav">
                                <g:if test="${appuser}">
                                    <sec:ifAnyGranted roles="ROLE_PUBLISHER">
                                        <li><g:link controller="publisher" action="list">PUBLISH</g:link> </li>
                                    </sec:ifAnyGranted>
                                    <sec:ifAnyGranted roles="ROLE_USER_ADMIN">
                                        <li><g:link controller="userManagement" action="list">MANAGE USERS</g:link></li>
                                    </sec:ifAnyGranted>
                                    <li><g:link controller="homePage" action="index" params="${[source: 'homePage']}">DASHBOARD</g:link> </li>
                                    <li><g:link controller="submissions" action="edit" params="['createNew': 'true', source: 'homePage']">NEW SUBMISSION</g:link> </li>
                                    <li style="white-space: nowrap">
                                            <g:link controller="profile" params="${[profile_id: appuser.id, source: 'profile']}" action="index" style="display: inline; margin-right: 3px;">
                                                    PROFILE
                                            </g:link><img src="${createLink(controller: "profile", action: "displayImage", params:[user:appuser.id])}" onerror="this.src='${assetPath(src: "default-profile.png")}'" class ="profilePicMini ml-2 mr-0" id="profilePicMiniCustom" title="profile-picture"/>
                                    </li>
                                    <li><g:link controller="logout" class="ml-5">LOGOUT</g:link></li>
                                </g:if>
                                <g:elseif test="${params.controller != 'login'}">
                                    <li><g:link controller="login" class="ml-5">LOG IN</g:link></li>
                                </g:elseif>
                            </ul>
                        </div>
                    </div>
                </nav>
            </div>
        </nav>
    </div>
</div>
<g:if test="${params.controller == 'homePage' && params.action == 'index'}">
    <div style="background-color: #005AA5; min-height: 70px;border-top: 3px solid #1563b8">
        <div class="card border-0" style="background-color: #005AA5;">
            <h3 style="color: white; font-weight: 700; background-color: #005AA5; min-height: 60px; padding-top: 15px;padding-left: 15px">Welcome to the George Brown College Research Portal</h3>
        </div>
    </div>
</g:if>

<div style="background-color: #000000">
    <div class="card border-0">
        <g:if test="${appuser}">
            <div class="h-50" style="background-color: #000000">
                <g:render template="/homePage/searchBox" model="[searchText: searchText, tagList: tagList, appuser: appuser, page: 'home', typesList: typesList, source: 'homePage']" />
            </div>
        </g:if>
    </div>
</div>