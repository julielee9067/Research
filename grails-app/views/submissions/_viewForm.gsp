<%@ page import="ca.georgebrown.gbcresearch.Tag" %>
<%@ page import="ca.georgebrown.gbcresearch.security.Role; ca.georgebrown.gbcresearch.security.AppuserRole" contentType="text/html;charset=UTF-8" %>

<g:javascript>
    $(document).ready(function () {
        var classname = document.getElementsByClassName("select2-selection__choice__remove");

        for (var i = 0; i < classname.length; i++) {
            classname[i].addEventListener('click', deleteTag, false);
        }

        $("#clearButton").click(function() {
            $("#searchInput").val('');
            $('#tagMKT').val(null).trigger('change');
            $('#typeSelectBarMKT').val(null).trigger('change');
        });
    });

    function searchTags() {
        $("#tagSearch").click();
    }
</g:javascript>

<div class="col-sm-7">
    <div class="form-group mb-3 viewProject">
        <label class="m-0" for="projSummary">Summary:</label>
        <textarea readonly class="form-control editField bg-light" name="submission.description" id="projSummary"
                  rows="10" cols="120" style="color: black; box-shadow: none">${submission?.description}</textarea>
    </div>
    <div class="w-100 pl-3 py-3 mb-3 pull-left" style="background-color: white; color: #005AA5; min-width: 400px;">
        <div class="form-group mb-3">
            <label class="m-0 mb-0">TAGS:</label> <br/>
            <div class="similarPersonInterests">
                <g:each in="${submission?.tags?.toList()}" var="tag">
                    <g:if test="${tag?.status == Tag.STATUS.ACTIVE}">
                        <div class="tagBox">
                            <g:link controller="homePage" action="index" params="[tagInput: tag.name, source: 'homePage']"
                                    class="btn btn-sm btn-outline-primary">${tag.name}</g:link>
                        </div>
                    </g:if>
                </g:each>
            </div>
        </div>
    </div>
</div>

<div class="col-sm-5">
    <br>
    <div class="form-group mb-3 viewProject">
        <label class="m-0" for="type">Type:</label>
        <span id="type" style="color: black">${submission?.type?.name}</span>
    </div>
    <g:if test="${submission.source == ca.georgebrown.gbcresearch.Submission.SOURCE.IAGO}">
        <div class="form-group mb-3 viewProject">
            <label class="m-0">Priciple Investigator(s):</label>
            <span style="color: black">${submission.principleInvestigators*.displayName.join(", ")}</span>
        </div>
        <g:if test="${submission.coInvestigators.size()}">
            <div class="form-group mb-3 viewProject">
                <label class="m-0">Co Investigator(s):</label>
                <span style="color: black">${submission.coInvestigators*.displayName.join(", ")}</span>
            </div>
        </g:if>
        <g:if test="${submission.partners.size()}">
            <div class="form-group mb-3 viewProject">
                <label class="m-0">Partners:</label>
                <span style="color: black">${submission.partners*.organization.join(", ")}</span>
            </div>
        </g:if>
        <g:if test="${submission.researchThemes.size()}">
            <div class="form-group mb-3 viewProject">
                <label class="m-0">Research Themes:</label>
                <span style="color: black">${submission.researchThemes*.theme.join(", ")}</span>
            </div>
        </g:if>
    </g:if>
    <g:else>
        <div class="form-group mb-3 viewProject">
            <label class="m-0">Submitted by:</label>
            <span style="color: black">
                <g:if test="${submission?.submitter?.username == 'admin'}">
                    <g:if test="${submission?.principleInvestigators?.size()}">
                        ${submission.principleInvestigators.first().displayName}
                    </g:if>
                </g:if>
                <g:else>
                    <g:link controller="profile" params="${[profile_id: submission?.submitter?.id]}"
                            action="index">${submission?.submitter?.firstName + ' ' + submission?.submitter?.lastName}
                    </g:link>
                </g:else>
            </span>
        </div>
    </g:else>
</div>

