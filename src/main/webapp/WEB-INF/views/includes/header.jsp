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
    		z-index: 3;
    	}
    	
    	.imgWrapper{
    		width: 125px;
    		background-color: #646383;
    	}
    	.imgWrapper img{
    		width: 100%;
			object-fit: cover;
    	}
    	.hTitleBox{
    		width: 90%;
		    margin: 1em auto;
		    height: 300px;
		    margin-bottom: 1%;
		    
    	}
    	.hTitle{
    		height: 100%;
			background-image: url(/resources/img/carina_nebula.jpg);
			background-size: 100%;
			-moz-box-shadow: 1px 2px 3px rgba(0,0,0,.5);
			-webkit-box-shadow: 1px 2px 3px rgba(0,0,0,.5);
			box-shadow: 1px 2px 3px rgba(0,0,0,.5);
    	}
    	.hTitle h1{
    		font-weight: bold;
    		font-size: 3.5rem;
    		padding-top: 3%;
    		padding-left: 4%;
    		color: white;
    	}
    </style>

</head>

<body>

	<section id="SignUPModal">
		<form action="/user/regist" method="post">
				<p>
					Email : <br />
<%-- 					<input id="title_box" type="text" name="u_Email" value="${Param.u_Email}">  --%>
					<input type="email" placeholder="Email" name="u_Email" id="mail" maxlength="30" value="${Param.u_Email}">
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
				  		<option value="Male">Male
				  		<option value="Female">Female
				  		<option value="Non-Binary">Non-Binary
				  	
				  	</select>
				</p>
				<p>
					ProfileImage : <br />
					<input id="Profile_box" name="u_profile_path" value="${Param.u_profile_path}">
				</p>				
							
				<button type="submit">회원가입</button>
				<button type="button" class="ModalClose" onclick="">취소</button>		
		
		</form>
		
	 </section>
	 <div id="ModalBG">
	 
	 </div>

    <header class="py-3 mb-3 border-bottom align-items-center">
		<div class="container-fluid d-grid" style="grid-template-columns: 1fr 2fr;">
			<div class="ms-3">
				<a href="/board/list" class="d-flex align-items-center col-lg-4 mb-2 mb-lg-0 link-dark text-decoration-none" aria-expanded="false">
					<!-- Logo -->
					<div class="imgWrapper rounded">
						<img src="${Context}/resources/img/logoh.png" alt="Logo">
					</div>
				</a>
			</div>
			
			<div class="d-flex align-items-center">
				<!-- Search form -->
				<!--
				<form class="w-100 me-3" role="search">
					<input type="search" class="form-control" placeholder="Search..." aria-label="Search">
				</form>
				-->
				<form class="row me-3" style="width: 80%;" id="searchForm" action="${context}/board/list" method="get">
           			<select name="type" class="form-select" style="width: 20% !important;">
           				<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>> </option>
           				<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : ''}"/>>제목</option>
           				<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : ''}"/>>내용</option>
           				<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : ''}"/>>글쓴이</option>
           				<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : ''}"/>>제목, 내용</option>
           				<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : ''}"/>>제목, 글쓴이</option>
           				<option value="CW" <c:out value="${pageMaker.cri.type eq 'CW' ? 'selected' : ''}"/>>내용, 글쓴이</option>
           				<option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC' ? 'selected' : ''}"/>>제목, 내용, 글쓴이</option>
           			</select>
           			<input class="form-control" style="width: 45% !important;" type="text" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>" />
           			<input type="hidden" name="pageNum" value="<c:out value='${pageMaker.cri.pageNum}'/>" />
           			<input type="hidden" name="amount" value="<c:out value='${pageMaker.cri.amount}'/>" />
           			<button class="btn btn-secondary" style="width: 15% !important">검색</button>
           		</form>
           		
				<div class="dropdown">
					<!-- Profile picture -->
					<a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
						<img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">
					</a>
					<ul class="dropdown-menu text-small shadow">
						<!-- c:if not logged in -->
						<li><a class="dropdown-item signIn" href="#">로그인</a></li>
						<li><a class="dropdown-item signUp" href="#">회원가입</a></li>
						<!-- c:if logged in -->
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item userUpdate" href="#">설정</a></li>
						<li><a class="dropdown-item SignOut" href="#">로그아웃</a></li>
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

	<div class="container-fluid">
	    <div class="col-lg-12 hTitleBox">
	    	<div class="hTitle rounded">
	        	<h1>/r/Radit</h1>
	        </div>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<!-- js contextpath ctx -->
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/resources/js/UserRegist.js"></script>
<script type="text/javascript">
$(".signUp").on("click", function () {
	$("#SignUPModal").css("display", "block")

})



$(".ModalClose").on("click" , function () {
	$("#SignUPModal").css("display", "none")
	
});




</script>