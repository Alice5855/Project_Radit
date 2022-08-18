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

</head>

<body>

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
						<li><a class="dropdown-item" href="#">Sign in/Sign up</a></li>
						<!-- c:if logged in -->
						<li><a class="dropdown-item" href="#">UserSettings</a></li>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="#">Sign out</a></li>
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