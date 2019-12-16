<%@ page import="ca.georgebrown.gbcresearch.ProfileSetting; ca.georgebrown.gbcresearch.Submission" %>

<style>
    .show-read-more .more-text{
        display: none;
    }
</style>

<script>
    $(document).ready(function() {
        maybeHideLoader(${offset ?: 0}, ${searchCount}, ${max ?: 10});
        <g:if test="${appuser?.setting?.scrollSetting == ProfileSetting.SCROLL_SETTING.PAGING}">
            window.scrollTo(0,0);
        </g:if>
    });

    function maybeHideLoader(offset, total, max) {
        if (!offset || !max){
            offset = 0;
            max = 10;
        }
        if (total < offset + max) {
            hideLoader();
        }
    }

    function hideLoader() {
        $('#submissionLoader').remove();
    }

</script>
<g:if test="${list.size() == 0 && !params.searchInput && !params.tagInput}">
    <div class="card rounded-0 mb-3 p-3 bg-light" style="border: 0; border-bottom: solid 1px lightgrey;">
        There is no submission to display.
    </div>
</g:if>
<g:each in="${list}" var = "submission">
    <div class="card rounded-0 mb-3 p-3 bg-light" style="border: 0; border-bottom: solid 1px lightgrey;">
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
                    <g:link controller="profile" params="${[profile_id: submission?.submitter?.id, source: 'profile']}" action="index"> ${submission?.submitter?.firstName + ' ' + submission?.submitter?.lastName} </g:link>
                </g:else>
            </td>
                <td rowspan="2" class="subImage">
                    <img src="${createLink(controller: "profile", action: "displayImage", params:[user:submission?.submitter?.id])}" onerror="this.src='${assetPath(src: "default-profile.png")}'" class ="profilePicMini" title="profile-picture"/>
                </td>
            </tr>
            <tr>
                <td class="subTitle" style="max-width: 50%">
                    <g:link params="${['type': submission.source, 'id': submission.id, 'source': source, searchInput: searchText, tagInput: tagInput]}" controller ="HomePage" action ="linkToPage">${submission.title}</g:link>
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
</g:each>
<g:if test="${userPaginationSetting == ProfileSetting.SCROLL_SETTING.SCROLL || (userPaginationSetting == ProfileSetting.SCROLL_SETTING.DEFAULT && defaultPaginationSetting == ProfileSetting.SCROLL_SETTING.SCROLL)}">
    <g:javascript plugin="remote-pagination" library="remoteNonStopPageScroll"/>
    <div id="submissionLoader" class="loader"></div>
    <util:remotePageScroll action="${action}" total="${searchCount}" update="${div_id}"
                           max="10" heightOffset="1000" loadingHTML="submissionLoader" onLoading ="maybeHideLoader(${offset}, ${searchCount}, ${max})" onLoaded = "hideLoader()"
                           params="[searchInput: params.searchInput ?: null, tagInput: params.tagInput ?: null, list: list, div_id: div_id, action: action, searchCount: searchCount, source: params?.source, typeName: params?.typeName]"/>
</g:if>
<g:elseif test="${userPaginationSetting == ProfileSetting.SCROLL_SETTING.PAGING || (userPaginationSetting == ProfileSetting.SCROLL_SETTING.DEFAULT && defaultPaginationSetting == ProfileSetting.SCROLL_SETTING.PAGING)}">
     <util:remotePaginate action="${action}" total="${searchCount}" update="${div_id}" maxsteps="10" max="10"
                           params="[searchInput: params.searchInput ?: null, tagInput: params.tagInput ?: null, list: list, div_id: div_id, action: action, searchCount: searchCount, source: params?.source, typeName: params?.typeName]"/>
</g:elseif>
