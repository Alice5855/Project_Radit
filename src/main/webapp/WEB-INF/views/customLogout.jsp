<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<title>Insert title here</title>
	<!-- Bootstrap Core CSS -->
	<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- MetisMenu CSS -->
	<link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
	
	<!-- Custom CSS -->
	<link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">
	
	<!-- Custom Fonts -->
	<link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<%--
	<h1>Custom Logout Page</h1>
	
	<form method='post' action="/customLogout">
	--%>
	<%--
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<!-- name=_csrf, value=random value. CSRF 토큰으로 특정 값의 토큰을
			발행하여 사전 검증하는 절차를 거침. post 방식의 경우에는 기본적으로 CSRF 토큰을
			사용한다 -->
		<!-- Cross-Site Request Forgery : 사이트간 요청 위조. Server에서 받아들이
			는 정보가 사전 조건을 검증하지 않는다는 취약점을 이용한 공격방식 -->
		<button>Log out</button>
	</form>
	--%>
	
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Logout Page</h3>
					</div>
					<div class="panel-body">
						<form role="form" method='post' action="/customLogout">
							<fieldset>
		                        
		                        <!-- Change this to a button or input when using this as a form -->
								<a href="index.html" class="btn btn-lg btn-success btn-block">Logout</a>
							</fieldset>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<!-- name=_csrf, value=random value. CSRF 토큰으로 특정 값의 토큰을
								발행하여 사전 검증하는 절차를 거침. post 방식의 경우에는 기본적으로 CSRF 토큰을
								사용한다 -->
							<!-- Cross-Site Request Forgery : 사이트간 요청 위조. Server에서 받아들이
								는 정보가 사전 조건을 검증하지 않는다는 취약점을 이용한 공격방식 -->
	                  </form>
					</div>
				</div>
			</div>
		</div>
   </div>

   <!-- jQuery -->
   <script src="/resources/vendor/jquery/jquery.min.js"></script>

   <!-- Bootstrap Core JavaScript -->
   <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

   <!-- Metis Menu Plugin JavaScript -->
   <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

   <!-- Custom Theme JavaScript -->
   <script src="/resources/dist/js/sb-admin-2.js"></script>
   
   <script>
   
   $(".btn-success").on("click", function(e){
      
      e.preventDefault();
      $("form").submit();
      
   });
   
   </script>
</body>
</html>
