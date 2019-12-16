
<g:if test="${searchPeople}">
    <g:each in="${searchPeople}" var="person">
        <div class="personBox">
            <div class="personSection similarPersonLink">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div class="personSection similarPersonProfilePhoto">
                <img src="${createLink(controller: "profile", action: "displayImage", params:[user:person?.user?.id])}" onerror="this.src='${assetPath(src: "default-profile.png")}'" class ="profilePicMini" title="profile-picture"/>
            </div>

            <div class="personSection similarPersonName namePositionWide" style="font-size: 16px;font-weight: 700;"><g:link controller="profile" params="${[profile_id: person?.user?.id]}" action="index">${person?.user?.getDisplayName()}</g:link></div>

            <div class="similarNameInterests mt-2">
                <div class="personSection similarPersonName namePositionNarrow"><g:link controller="profile" params="${[profile_id: person?.user?.id]}" action="index">${person?.user?.getDisplayName()}</g:link></div>

                <g:if test="${person?.user?.tags && person?.settings?.showInterests}">
                    <div class="personSection similarPersonInterests">
                        <g:each in="${person?.user?.tags}" var = "tag" status = "i">
                            <div class="tagBox">
                                <g:link controller="homePage" action="index" params="[tagInput: tag.name, source: 'homePage']"
                                        class="btn btn-sm btn-outline-primary">${tag.name}</g:link>
                            </div>
                        </g:each>
                    </div>
                </g:if>
            </div>

        </div>
    </g:each>
</g:if>
<g:elseif test="${userSearch}">
    <div class="personBox">
        No results
    </div>
</g:elseif>


<g:if test="${action != 'none'}">
    <div class = "paginationBox">
%{--    <g:paginate controller="homePage" action="index" params="[userSearch: searchTextPerson, div_id: 'searchPeopleContent', searchPeople: searchPeople?.result]" total="${total?:0}" update="${div_id}" max="10" maxsteps="5"/>--}%
        <util:remotePaginate controller="${controller}" action="${action}" params="[userSearch: userSearch]" total="${total?:0}" update="${div_id}" max="10" maxsteps="5"/>
    </div>
</g:if>


