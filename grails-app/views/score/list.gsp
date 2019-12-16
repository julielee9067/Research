<%@ page import="ca.georgebrown.gbcresearch.ScoreWeightingFactors" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <title>Research - Weighting Factors</title>
        <style>
        .resTable tbody tr{
            border: 1px solid lightgray;
        }
        </style>
    </head>
    <body>
        <g:render template='/layouts/navigation' model="[which: 'weightingFactors']"/>
        <div id="list-appSettings" class="container-fluid content scaffold-list" role="main">
            <div class="card">
                <div class="card-header">
                    <h1 class="card-title pageTitle">Weighting Factors</h1>
                </div>
                <div class="card-body mx-auto mt-3 text-center">
                    <g:if test="${flash.message}">
                        <div class="warningMsg" role="status">${flash.message}</div>
                    </g:if>
                    <div class="table-responsive table-bordered">
                        <table class="resTable table table-hover table-bordered table-sm mx-auto table-striped mb-0">
                            <thead>
                            <tr>
                                <th>Code</th>
                                <th>Description</th>
                                <th>Type</th>
                                <th>Value</th>
                            </tr>
                            </thead>
                            <tbody>
                                <g:each in="${weightFactorInstanceList.sort { [it.seq] }}" status="i" var="weightFactorInstance">
                                    <g:if test="${weightFactorInstance.code != ScoreWeightingFactors.CODE.END_USER_ID}">
                                        <tr class="settingRec">
                                            <td  class="font-weight-bold"><g:link class="edit" action="edit" id="${weightFactorInstance.id}">${fieldValue(bean: weightFactorInstance, field: "code")}</g:link></td>
                                            <td>${fieldValue(bean: weightFactorInstance, field: "description")}</td>
                                            <td class="text-center">${fieldValue(bean: weightFactorInstance, field: "type")}</td>
                                            <td class="text-center">${fieldValue(bean: weightFactorInstance, field: "value")}</td>
                                        </tr>
                                    </g:if>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
