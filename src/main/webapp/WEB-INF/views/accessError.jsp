<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Error Occurred</title>
</head>
<body>
	<h1>ERROR OCCURRED</h1>
	<h2>Access denied</h2>
	<h3><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}" /></h3>
	<h4><c:out value="${msg}"/></h4>
	<!-- CommonController에 정의된 msg attribute -->
	<h5>ya hear access is denied</h5>
</body>
</html>