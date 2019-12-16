<g:if test="${users}">
    <g:each in="${users}" var="person">
        <div class="personBox">
            <div class="personSection similarPersonLink">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div class="personSection similarPersonProfilePhoto">
                <img src="${createLink(controller: "profile", action: "displayImage", params:[user:person?.user?.id])}" onerror="this.src='${assetPath(src: "default-profile.png")}'" class ="profilePicMini" title="profile-picture"/>
            </div>
            <div class="personSection similarPersonName namePositionWide" style="font-size: 16px;font-weight: 700"><g:link controller="profile" params="${[profile_id: person?.user?.id, source: "profile"]}" action="index">${person?.user?.displayName}</g:link></div>
            <div class="similarNameInterests mt-2">
                <div class="personSection similarPersonName namePositionNarrow"><g:link controller="profile" params="${[profile_id: person?.user?.id, source: "profile"]}" action="index">${person?.user?.displayName}</g:link></div>
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

    <div class="pagination mt-0">
        <g:javascript plugin="remote-pagination" library="remoteNonStopPageScroll"/>
        <util:remotePaginate controller="profile" action="${action}" total="${total}" update="${div_id}" params="[profile_id: profile_id]"
                             max="5" maxsteps="5"/>
    </div>
</g:if>


