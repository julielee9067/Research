<%--
  Created by IntelliJ IDEA.
  User: tonym
  Date: 2019-09-19
  Time: 9:18 a.m.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name='layout' content='main'/>
        <title>Research</title>
        <style>
            .paginationBox {
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <div class="h-50" style="background-color: #0f0f0f;">
            <g:render template="/homePage/searchBox" model="[searchText: searchText, tagList: tagList, appuser: appuser, page: 'home', typesList: typesList]" />
        </div>
        <div id="submissionView" class="leftContent card-body center mt-3 border-0 bg-light">
            <g:if test="${resultList}">
                 <g:render template="submissions" model="[list: resultList, div_id: 'submissionView', action: 'listSubmissionsSearch', total: searchCount]"/>
             </g:if>
            <g:else>
                <div class ="submissionContent">
                    <div class="section">No results found.</div>
                </div>
            </g:else>
        </div>
    </body>
</html>