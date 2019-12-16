<div style="background-color: #303030;  min-height: 170px;">
    <div class="card border-0">
        <div style="background-color: #303030;">
            <table class="table mt-3 ml-5">
                <tbody>
                    <tr>
                        <td rowspan="2" class="text-left"
                            style="border: none; width: 150px; margin-right: 0;margin-left: 0;">
                            <div><asset:image src="gbcLogo2.png" title='GBC Footer' class="img-fluid"
                                              width="150"/></div>
                        </td>
                        <td style="border: none; vertical-align: bottom; padding-bottom: 0; margin-bottom: 0">
                            <div class="text-light text-left">© 2019 George Brown College</div>
                        </td>
                    </tr>
                    <tr>
                        <td style="border: none">
                            <div class="text-light text-left"><a href="https://www.georgebrown.ca/privacy_policy/"
                                                                 class="text-light">Privacy</a></div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <g:if env="production">
            </g:if>
            <g:else>
                <sec:ifLoggedIn>
                    <p class="mb-2 ml-5">
                        <button class="badge badge-pill badge-primary nav-pills"
                                onclick="$('#debugSection').toggle();
                                window.scrollTo(0, document.body.scrollHeight);">Toggle Debug</button>
                    </p>
                </sec:ifLoggedIn>
            </g:else>

            <g:if env="production">
                <!--<g:render template="/version"/>-->
            </g:if>
            <g:else>
                <div id="debugSection" style="display:none">
                    <div>
                        <div class="card">
                            <div class="card-body">
                                <h3>Debug information -- does not appear on production release</h3>
                                <g:render template="/version"/>
                                <span class="debug">${grailsApplication.config.dataSource.url}</span>
                                <g:render template="/debug/sessionEmailForm"></g:render>
                            </div>
                        </div>
                    </div>
                </div>
            </g:else>
        </div>
    </div>
</div>
