<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- taglib for Security authentication -->
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>
<!DOCTYPE html>
<html lang="ko">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome to Radit</title>
    <!-- Bootstrap Core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
	
	<%--
    <!-- MetisMenu CSS -->
    <link href="${context}/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="${context}/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="${context}/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${context}/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${context}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	--%>
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    	#SignUPModal{
    		display:none;
    		position: fixed;
    		margin-left: 50%;
    		margin-top: 15%;
    		
    	}
    </style>

</head>

<body>
	
	<section id="SignUPModal">
		<form action="/user/regist" method="post">
				<p>
					Email : <br />
<%-- 					<input id="title_box" type="text" name="u_Email" value="${Param.u_Email}">  --%>
					<input type="email" placeholder="이메일" name="u_Email" id="mail" maxlength="30" value="${Param.u_Email}">
					<div id="error_mail" class="result-email result-check"></div>
					
				</p>
				<p>
					NickName : <br />
						<input id="Name_box" name="u_Name" value="${Param.u_Name}">
				</p>				
				<p>
					Password : <br />
					<input id="Password_box" type="password" name="u_pw" value="${Param.u_pw}">
				</p>				
				<p>
					Address : <br />
<%-- 					<input id="Address_box" name="u_Address" value="${Param.u_Address}"> --%>
						<input type="text" id="address_kakao" name="u_Address" readonly ="readonly" value="${Param.u_Address}"/>
						<input id="Address_box" name="u_Address" value="${Param.u_Address}" placeholder="better address">
						
				</p>				
				<p>
					Gender : <br />
<%-- 				<input id="Gender_box" name="u_gender" value="${Param.u_gender}"> --%>
				  	<select name= "u_gender">
				  		<option value="male">male
				  		<option value="female">female
				  		<option value="Non-Binary">Non-Binary
				  	
				  	</select>
				</p>				
				<p>
					ProfileImage : <br />
					<input id="Profile_box" name="u_profile_path" value="${Param.u_profile_path}">
				</p>				
							
				<button type="submit">회원가입</button>
				<button type="button" class="ModalClose" onclick="javascript:history.go(-1);">취소</button>		
		
		</form>
		
	 </section>
	 <div id="ModalBG">
	 
	 </div>
	 

    <header class="py-3 mb-3 border-bottom">
		<div class="container-fluid d-grid gap-3 align-items-center" style="grid-template-columns: 1fr 2fr;">
			<div class="dropdown">
				<a href="#" class="d-flex align-items-center col-lg-4 mb-2 mb-lg-0 link-dark text-decoration-none" aria-expanded="false">
					<!-- Logo -->
					<svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"/></svg>
				</a>
			</div>
			
			<div class="d-flex align-items-center">
				<!-- Search form -->
				<form class="w-100 me-3" role="search">
					<input type="search" class="form-control" placeholder="Search..." aria-label="Search">
				</form>
				
				<div class="flex-shrink-0 dropdown">
					<!-- Profile picture -->
					<a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
						<img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">
					</a>
					<ul class="dropdown-menu text-small shadow">
						<!-- c:if not logged in -->
						<li><a class="dropdown-item signIn" href="#">Sign in</a></li>
						<li><a class="dropdown-item signUp" href="#">Sign up</a></li>
						<!-- c:if logged in -->
						<li><a class="dropdown-item userUpdate" href="#">UserSettings</a></li>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item SignOut" href="#">Sign out</a></li>
					</ul>
				</div>
			</div>
		</div>
	</header>
	
<%-- we *NEED* this
<ul class="dropdown-menu dropdown-user">
	<li>
		<a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
    </li>
    <li>
    	<a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
    </li>
    <li class="divider"></li>
	<sec:authorize access="isAuthenticated()">
	<li>
		<a href="/customLogout"><i class="fa fa-sign-out fa-fw"></i>
	    Logout</a>
	</li>
	</sec:authorize>
	
	<sec:authorize access="isAnonymous()">
	<li>
		<a href="/customLogin"><i class="fa fa-sign-out fa-fw"></i>
	    Login</a>
	</li>
	</sec:authorize>
</ul>
<!-- /.dropdown-user -->
--%>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<!-- js contextpath ctx -->
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<script type="text/javascript">
	$(document).ready(function () {
		$(".signUp").on("click", function () {
			$("#SignUPModal").css("display", "block");
			$("#ModalBG").css("background-color", "rgba(0,0,0,0.5)");
			$("#ModalBG").css("width", "100%");
			$("#ModalBG").css("height", "100%");
		});
		
		
		
		$(".ModalClose").on("click" , function () {
			$("#SignInModal").css("display", "none");
			$("#ModalBG").css("display", "none");
		})
		
		
// 		$(".SignInSuccess").on("click", function(e){
		    
// 		    e.preventDefault();
// 		    $("form").submit();
		    
// 		  });


		
		
	});
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
window.onload = function(){
    document.getElementById("address_kakao").addEventListener("click", function(){
        new daum.Postcode({
            oncomplete: function(data) { 
                document.getElementById("address_kakao").value = data.address; 
                document.querySelector("input[name=address_detail]").focus(); 
            }
        }).open();
    });
}
</script>


<script type="text/javascript">
function email_check( email ) {    
    var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email)); 
}

$("input[type=email]").blur(function(){
  var email = $(this).val();
  if( email == '' || email == 'undefined') return;
  if(! email_check(email) ) {
  	$(".result-email").text('이메일 형식으로 적어주세요');
    $(this).focus();
    return false;
  }else {
	$(".result-email").text('');
  }
});

</script>

