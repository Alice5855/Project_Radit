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
	<!-- admin -->
	<h1>/sample/admin page</h1>
	<p>principal : <sec:authentication property="principal"/></p>
	<p>MemberVO : <sec:authentication property="principal.member"/></p>
	<p>사용자이름 : <sec:authentication property="principal.member.userName"/></p>
	<p>사용자아이디 : <sec:authentication property="principal.username"/></p>
	<p>사용자 권한 리스트  : <sec:authentication property="principal.member.authList"/></p>
	
	<%-- jsp taglib security를 활용하여 사용자 정보 보여줌 --%>
	<%-- authentication, principal 속성 사용 시 UserDetailsService에서 객체를
		가져와서 사용하게 됨. principal이 CustomUser class의 instance객체처럼 
		활용되는 것. 각각 field에서 value를 가져오는 데에는 get method를 사용한다 --%>
	
	<!-- page642 log out test용  -->
	<a href="/customLogout">Logout</a>
</body>
</html>