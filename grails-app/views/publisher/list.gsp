<%--
  Created by IntelliJ IDEA.
  User: staff
  Date: 2019-07-12
  Time: 09:05
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name='layout' content='main'/>
        <title>Research - Publish</title>
        <style>
            .table thead tr th, .table tbody tr td {
                text-align: center;
                vertical-align: middle;
            }
            .resTable tbody tr{
                border: 1px solid lightgray;
            }
        </style>
    </head>

    <body>
        <g:render template='/layouts/navigation' model="[which: 'publish']"/>
        <div class="container-fluid content scaffold-list bg-white border-0" role = "main">
            <div class="card ">
                <div class="card-header">
                    <h1 class="card-title pageTitle">Publish Submissions</h1>
                </div>
                <div class="card-body mt-3 bg-white">
                    <g:if test="${fsList && fsList?.size() > 0}">
                        <div class="h4 card-title left">submissions available to review for publication ( <span class="btn btn-sm btn-warning font-weight-bold" style="font-size: 16px"> ${total} </span> )</div>
                        <div class="table-responsive table-bordered">
                            <table class="resTable table table-hover table-striped table-bordered border-bottom mb-0">
                                <thead  class="text-center">
                                    <tr>
                                        <th>Title</th>
                                        <th>Owner</th>
                                        <th>Submitted By</th>
                                        <th>Submission Date</th>
                                        <th>Submission Status</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${fsList}" var="submission">
                                        <tr>
                                            <td style="text-align:left; max-width: 600px;">${submission.title}</td>
                                            <td>${submission?.owner?.displayName}</td>
                                            <td>${submission?.submitter?.displayName}</td>
                                            <td><g:formatDate format="yyyy-MM-dd HH:mm" date="${submission?.submissionDate}"/></td>
                                            <td>${submission?.submitStatus}</td>
                                            <td><g:link class="btn btn-sm btn-outline-primary" action ="linkToPage" controller="submissions" params="${['type': 'Submission', 'id': submission.id, 'source': 'publisher']}">VIEW</g:link></td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                        <div class="paginationBox inline-flex">
                            <g:paginate next="Forward" prev="Back"
                                        maxsteps="5" controller="publisher"
                                        action="list" total="${total}" />
                        </div>
                    </g:if>
                    <g:else>
                        <div class="h4 card-title left">There are no submissions to publish at this time</div>
                    </g:else>
                </div>
                <hr style="border: 1px dashed lightgrey; width: 95%;" class="mx-auto"/>
                <div class="card-body mt-3 bg-white">
                    <g:if test="${rtList && rtList?.size() > 0}">
                        <div class="h4 card-title left">submissions returned for revision ( <span class="btn btn-sm btn-danger font-weight-bold" style="font-size: 16px;"> ${rtTotal} </span> ) </div>
                        <div class="table-responsive table-bordered">
                            <table class="resTable table table-hover table-striped table-bordered border-bottom mb-0">
                                <thead class="text-center">
                                    <tr>
                                        <th>Title</th>
                                        <th>Owner</th>
                                        <th>Submitted By</th>
                                        <th>Submission Date</th>
                                        <th>Submission Status</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${rtList}" var="submission">
                                        <tr>
                                            <td style="text-align:left; max-width: 600px;">${submission.title}</td>
                                            <td>${submission?.owner?.displayName}</td>
                                            <td>${submission?.submitter?.displayName}</td>
                                            <td><g:formatDate format="yyyy-MM-dd HH:mm" date="${submission?.submissionDate}"/></td>
                                            <td>${submission?.submitStatus}</td>
                                            <td><g:link class="btn btn-sm btn-outline-primary" action ="linkToPage" controller="homePage" params="${['type': 'Submission', 'id': submission.id, 'source': 'publisher']}">VIEW</g:link></td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                        <div class="paginationBox inline-flex">
                            <g:paginate next="Forward" prev="Back"
                                        maxsteps="5" controller="publisher"
                                        action="list" total="${rtTotal}" />
                        </div>
                    </g:if>
                    <g:else>
                        <div class="h4 card-title left">There are no returned projects at this time</div>
                    </g:else>
                </div>
            </div>
        </div>
    </body>
</html>