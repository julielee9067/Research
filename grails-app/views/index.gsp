<%@ page import="ca.georgebrown.gbcresearch.security.Appuser" %>

<!DOCTYPE html>
	<g:set var="appuser" value="${Appuser.findByUsername(sec.loggedInUserInfo(field:'username') as String)}"/>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Research</title>
	</head>
	<body>
		<g:render template='/layouts/navigation' model="[which:'dashboard']"/>
	</body>
</html>
