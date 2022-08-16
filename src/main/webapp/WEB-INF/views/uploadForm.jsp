<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>
<html>
<head>
	<title>UploadForm</title>
</head>
<body>
	<!-- uploadFormAction 경로로 file 전송을 처리함. enctype을 multipart
	/form-data로 지정하여야 한다. input의 multiple 속성은 여러개의 파일을 업로드 할 수
	있도록 해주는 기능 -->
	<form action="uploadFormAction" method="post" enctype="multipart/form-data">
		<input type="file" name="uploadFile" multiple="multiple">
		<button>Submit</button>
	</form>
</body>
</html>
