<%@ page import="ca.georgebrown.gbcresearch.Setting" %>

<script>
    jQuery.noConflict();
    $(document).ready(function () {
        var url = window.location;
        // Will also work for relative and absolute hrefs

        $('ul.nav a').filter(function () {
            return this.href == url;
        }).parent().addClass('active');

        $('.nav-tabs').on('shown.bs.tab', 'a', function (e) {
            if (e.relatedTarget) {
                $(e.relatedTarget).removeClass('active');
            }
        });
        $('.dropdown-toggle').dropdown();
    });
</script>

<sec:ifAnyGranted roles="ROLE_APP_MANAGER, ROLE_DEVELOPER">
    <div class="card border-0">
        <nav class="navbar navbar-expand-sm navbar-light bg-white mb-0 px-0 py-2">
            <div <sec:ifAnyGranted roles=" ROLE_APP_MANAGER,  ROLE_DEVELOPER">class="collapse navbar-collapse"</sec:ifAnyGranted> id="navbarNav">
                <g:if test="${appuser}">
                    <ul class="nav justify-content-center nav-pills">
                        <li class="<g:if test="${which == 'settings'}">active</g:if>">
                            <g:link controller="settings" action="list">Settings</g:link>
                        </li>
                        <li class="<g:if test="${which == 'wightingFactors'}">active</g:if>">
                            <g:link controller="score" action="list">Weighting Factors</g:link>
                        </li>
                        <li class="<g:if test="${which == 'submissionType'}">active</g:if>">
                            <g:link controller="submissionType" action="submissionTypesList">Submission Types</g:link>
                        </li>
                        <li class="<g:if test="${which == 'tags'}">active</g:if>">
                            <g:link controller="tags" action="tagsList">Tags</g:link>
                        </li>
                        <li class="<g:if test="${which == 'help'}">active</g:if>">
                            <g:link controller="help" action="list">Help</g:link>
                        </li>
                    </ul>
                </g:if>
            </div>
        </nav>
    </div>
</sec:ifAnyGranted>