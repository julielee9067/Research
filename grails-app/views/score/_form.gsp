<%@ page import="ca.georgebrown.gbcresearch.ScoreWeightingFactors" %>

<table class="table center table-bordered">
    <tr>
        <th>Code</th>
        <td>${weightFactorInstance?.code}</td>
    </tr>
    <tr>
        <th><label for="description">Description</label></th>
        <td><input class="settingsTextbox" type="text" id="description" name="description"  value="${weightFactorInstance?.description}"/></td>
    </tr>
    <tr>
        <th><label for="value">Value</label></th>
        <td>
            <g:if test="${weightFactorInstance.type in [ScoreWeightingFactors.TYPE.INT]}">
                <g:field id="value" type="number" name="value" value="${weightFactorInstance?.value}" />
            </g:if>
            <g:elseif test="${weightFactorInstance.type in [ScoreWeightingFactors.TYPE.DOUBLE]}">
                <g:field id="value" type="double" name="value" value="${weightFactorInstance?.value}" />
            </g:elseif>
        </td>
    </tr>
</table>