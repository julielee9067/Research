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