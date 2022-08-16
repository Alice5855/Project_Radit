<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<!-- all -->
	<h1>/sample/all page</h1>
	
	<div>
		<sec:authorize access="isAnonymous()">
			<a href="/customLogin">Log in</a>
		</sec:authorize>
		
		<sec:authorize access="isAuthenticated()">
			<a href="/customLogout">Log Out</a>
		</sec:authorize>
	</div>
</body>
</html>