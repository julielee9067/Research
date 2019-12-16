<%@ page import="ca.georgebrown.gbcresearch.security.Role" %>

<tr>
    <th>Enabled</th>
    <td><g:checkBox name="enabled" value="${user.enabled}">Enabled</g:checkBox></td>
</tr>
<tr>
    <th>Roles</th>
    <td>
        <g:each in="${Role.list(['sort': 'id'])}" var="role">
            <g:checkBox   name="role.${role.id}" value="${role in user?.authorities}"></g:checkBox> ${role.name}<br/>
        </g:each>
    </td>
</tr>