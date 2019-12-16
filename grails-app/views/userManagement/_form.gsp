<%@ page import="ca.georgebrown.gbcresearch.security.Appuser" %>

<div class="table-reponsive">
    <table class="mx-auto table-bordered table-sm" style="text-align: left">
        <g:if test="${user}">
            <tr><th>User</th><td>${user.authenticationType}</td></tr>
            <g:if test="${user.username == 'admin'}">
                <g:render template="adminForm"  model="[user: user]"/>
            </g:if>
            <g:else>
                <g:if test="${user.authenticationType == Appuser.AUTHENTICATION_TYPE.LDAP}">
                    <g:render template="ldapForm"  model="[user: user]"/>
                </g:if>
                <g:if test="${user.authenticationType == Appuser.AUTHENTICATION_TYPE.DAO}">
                    <g:render template="daoForm"  model="[user: user]"/>
                </g:if>
            </g:else>

            <g:render template='rolesForm' model="[user:user]"/>
        </g:if>
        <g:else>
            <g:if test="${which == 'create'}">
                 <g:render template='daoForm' model="[user:user]"/>
            </g:if>
        </g:else>
    </table>
</div>