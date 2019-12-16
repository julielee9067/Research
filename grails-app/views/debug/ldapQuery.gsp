<%--
  Created by IntelliJ IDEA.
  User: tonym
  Date: 2017-05-09
  Time: 4:30 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title></title>
    </head>
    <body>
        <g:each in="${result}" var="k,v">
            ${k}:${v}<br/>
        </g:each>
    </body>
</html>