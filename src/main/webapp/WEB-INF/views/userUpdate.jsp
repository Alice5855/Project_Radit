<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- taglib for Security authentication -->
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
<style type="text/css">
	#updateModal{
		position: fixed;
    		top: 0;
    		left: 0;
    		bottom: 0;
    		right: 0;
    		background: rgba(0,0,0,0.8);
    		z-index:1;
	
	}
	
	
    	.ModalStyle{
    		position: absolute;
		   	top: calc(50vh - 200px);
		    left: calc(50vw - 200px);
		    background-color: white;
		    display: block; 
		    border-radius: 10px;
		    width: 400px;
		    height: 450px;
    	}
    	#deleteModal{
    		display:none;
    		z-index:999999;
    		position: fixed;
    		top: 0;
    		left: 0;
    		bottom: 0;
    		right: 0;
    	}
    	.deleteAccount{
    		position: absolute;
		   	top: calc(50vh - 200px);
		    left: calc(50vw - 200px);
		    background-color: white;
		    display: block; 
		    border-radius: 10px;
		    width: 400px;
		    height: 450px;
    	}
    	
    	@media(max-width:720px){
    		.ModalStyle{
    			top: calc(50vh - 200px);
		   		left: calc(50vw - 200px);
    			width: 70%;
    			margin-left: 20%;
    		}
    	}
</style>
</head>
<body>


		<section id="deleteModal"> 
			<form action="/user/deleteAccount" method="post" class="deleteAccount ModalStyle">
				<input type="hidden" name="u_Email" id="mail" maxlength="30" value="<sec:authentication property="principal.username"/>" readonly="readonly">
				<textarea rows="" cols=""> 정말 회원탈퇴 하시겠습니까?</textarea>
				<button type="submit">확인</button>
				<button type="button" class="UndoDelete">취소</button>
				
				<input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
								
			
			</form>
		</section>

		<section id="updateModal" class="ModalOff ModalOn">
			<form action="/user/update" method="post" class="ModalStyle">
				<p>
					이메일 : <br />
<%-- 					<input id="title_box" type="text" name="u_Email" value="${Param.u_Email}">  --%>
					<input type="email" placeholder="Email" name="u_Email" id="mail" maxlength="30" value="<sec:authentication property="principal.username"/>" readonly="readonly">
					<div id="error_mail" class="result-email result-check"></div>
					
				</p>
				<p>
					닉네임 : <br />
						<input id="Name_box" name="u_Name" value =""  placeholder="${user.u_Name}" required="required">
				</p>				
				<p>
					비밀번호 : <br />
					<input id="Password_box" type="password" name="u_pw" required="required">
				</p>				
				<p>
					주소 : <br />
<%-- 					<input id="Address_box" name="u_Address" value="${Param.u_Address}"> --%>
						<input type="text" id="address_kakao" name="u_Address" readonly ="readonly" required="required"/>
						<input id="Address_box" name="u_Address" value="${Param.u_Address}" placeholder="상세 주소" required="required">
						
				</p>				
				<p>
					성별 : <br />
<%-- 				<input id="Gender_box" name="u_gender" value="${Param.u_gender}"> --%>
				  	<select name= "u_gender" required="required">
				  		<option value="Male">Male
				  		<option value="Female">Female
				  		<option value="Non-Binary">Non-Binary
				  		<option value="Attack-Helicopter">Attack-Helicopter
				  	
				  	</select>
				</p>				
				<p>
					프로필 이미지 : <br />
<%-- 					<input id="Profile_box" name="u_profile_path" value="${Param.u_profile_path}"> --%>
					<select name= "u_profile_path" required="required">
				  		<option value="Image1">이미지1
				  		<option value="Image2">이미지2
				  		<option value="Image3">이미지3
				  	
			  		</select>
				</p>
				<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />				
							
				<button type="submit">정보수정</button>
				<button type="button" class="ModalClose" onclick="location.href = '/board/list'">취소</button>		
				<button type="button" class="deleteTrigger" onclick="">회원탈퇴</button>
				<button type="button" onclick="fn_buy()">결제하기</button>
		</form>
		
	 </section>
	
	


<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<!-- iamport.payment.js -->
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script type="text/javascript">
	$(".ModalClose").on("click" , function () {
		$(".ModalOff").css("display", "none");
		
	});
	
	
	$(".userUpdate").on("click", function () {
		$("#updateModal").css("display", "block");
	
	});
	
	$(".deleteTrigger").on("click", function () {
		$("#deleteModal").css("display" , "block");
	})
	$(".UndoDelete").on("click" , function () {
		$("#deleteModal").css("display" , "none")
	})
	

</script>
<script>
	function fn_buy() {
		var IMP = window.IMP;
		var UE = ${user.u_Name};
		IMP.init("imp56843516"); // Insert your Code 부분에 자신의 아임포트 "가맹점 식별코드" 입력 바랍니다.
		IMP.request_pay({
			pg : "html5_inicis",
			pay_method : "card",
			merchant_uid : 'merchant_' + new Date().getTime(),
			name : "Snack",
			amount : 1,
			buyer_email : "test@naver.com",  // buyer_email도 수정하기 바랍니다.
			buyer_name : UE,
			buyer_tel : "010-1111-2222",
			//buyer_addr : "서울특별시 강남구 역삼동",
			//buyer_postcode : "11111",
			m_redirect_url : "/paymentDone.do"
		}, function(rsp) {
			if (rsp.success) {
				var paymentInfo = {
						imp_uid : rsp.imp_uid,
						merchant_uid : rsp.merchant_uid,
						paid_amount : rsp.paid_amount,
						apply_num : rsp.apply_num,
						paid_at : new Date()
					};
				$.ajax({
					url : "/paymentProcess.do",
					method : "POST",
					contentType : "application/json",
					data : JSON.stringify(paymentInfo), 
					success:function (data,textStatus){
				    	 console.log(paymentInfo);
				    	 location.href = "/paymentDone.do";
				     },
					error : function(e) {
						console.log(e);
					}
				})
			} else {
				alert("결제 실패 : " + rsp.error_msg);
			}
		});
	}
</script>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/resources/js/UserRegist.js"></script>

</body>
</html>