<%--
  Created by IntelliJ IDEA.
  User: staff
  Date: 2019-07-10
  Time: 11:07
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name='layout' content='main'/>
        <title>Search Results</title>
    </head>

    <body>
        <style>
        /* Add this attribute to the element that needs a tooltip */
        div [data-tooltip] {
            position: relative;
            z-index: 2;
            cursor: pointer;
        }

        /* Hide the tooltip content by default */
        div [data-tooltip]:before,
        div [data-tooltip]:after {
            visibility: hidden;
            -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
            opacity: 0;
            pointer-events: none;
        }

        /* Position tooltip above the element */
        div [data-tooltip]:before {
            position: absolute;
            bottom: 150%;
            left: 50%;
            margin-bottom: 5px;
            margin-left: -80px;
            padding: 7px;
            width: 160px;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px;
            background-color: #000;
            background-color: hsla(0, 0%, 20%, 0.9);
            color: #fff;
            content: attr(data-tooltip);
            text-align: center;
            font-size: 14px;
            line-height: 1.2;
        }

        /* Triangle hack to make tooltip look like a speech bubble */
        div [data-tooltip]:after {
            position: absolute;
            bottom: 150%;
            left: 50%;
            margin-left: -5px;
            width: 0;
            border-top: 5px solid #000;
            border-top: 5px solid hsla(0, 0%, 20%, 0.9);
            border-right: 5px solid transparent;
            border-left: 5px solid transparent;
            content: " ";
            font-size: 0;
            line-height: 0;
        }

        /* Show tooltip content on hover */
        div [data-tooltip]:hover:before,
        div [data-tooltip]:hover:after {
            visibility: visible;
            -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
            opacity: 1;
        }
        </style>
        <script>
            $(document).ready(function() {
                $("#tag").select2();
                $("#clearButton").click(function() {
                    $("#searchInput").val('');
                    $('#tag').val(null).trigger('change');
                });
            });
        </script>

        <g:render template='/layouts/navigation' model=""/>
        <div class="card-body center mt-3 border border-secondary bg-light profile" style="width: 80%">
            <g:render template="searchBox" model="[searchText: searchText, tagList: tagList, appuser: appuser, page: 'search', typeList: typeList]" />
            <br>
            <g:if test="${resultList.size()!=0}">
                <div class="h4 card-title left">Results: ${resultList.size()}</div>
                <div class="table-responsive border border-secondary">
                    <table class="table table-hover table-striped table-bordered mb-0">

                        <tr>
                            <th>Title</th>
                            <th>Domain</th>
                            <th>Description</th>
                            <th>Owner</th>
                            <th>Tags</th>
                            <th></th>
                        </tr>

                        <g:each in="${resultList}" var="result">
                            <tr>
                                <td>${result?.title}</td>
                                <td>${result?.domain}</td>
                                <td><div style="height: 200px; overflow: auto">${result?.description}</div></td>
                                <td> <g:link controller="profile" params="${[profile_id: result?.owner?.id, source: 'profile']}" action="index"> ${result?.owner?.displayName} </g:link>  </td>
                                <td>${result?.tag_arr}</td>
                                <td><g:link params="${['type': 'tbd', 'id': result.id, source: 'homePage']}" controller="homePage" action ="linkToPage">VIEW</g:link></td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </g:if>
            <g:else>
                <div class="h4 card-title left">This search term returned 0 results. </div>
            </g:else>
        </div>
    </body>
</html>​