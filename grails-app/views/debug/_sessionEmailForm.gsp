<g:if env="production"></g:if>
<g:else>
    <g:javascript>
        function updateSessionEmail(data) {
            sessionEmail = $("#sessionEmail").val(data.sessionEmail);
        }
        jQuery.noConflict();
        function setSessionEmail() {
         var sessionEmail = $("#sessionEmail").val()
         ${remoteFunction(controller: 'debug', action: 'ajaxSetSessionEmail', params: '\'sessionEmail=\' + escape(sessionEmail)',
                onSuccess: 'updateSessionEmail(data)')}
        }
   </g:javascript>
    <br/><br/>
    <table border="0">
        <tr>
            <td><label class="control-label">Please enter the email address you want the test emails to be sent to for this session:</label></td>
                <td><input type="email" id="sessionEmail" class="form-control" name="email" value="<g:sessionTestEmail/>"> </td>
            <td> <button type="submit" name="setEmail" onclick="setSessionEmail();" class="btn btn-default">Update</button></td>
        </tr>
    </table>
    <br>
</g:else>