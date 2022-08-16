<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- taglib for Security authentication -->
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>

<%@include file="../includes/header.jsp"%>
<style>
		.uploadResult {
		   width: 100%;
		   background-color: #F5F5F5;
		}
		
		.uploadResult ul {
		   display: flex;
		   flex-flow: row;
		   justify-content: center;
		   align-items: center;
		}
		
		.uploadResult ul li {
		   list-style: none;
		   padding: 10px;
		}
		
		.uploadResult ul li img.icon {
		   width: 100px;
		}
		
		.uploadResult ul li img.thumbnail {
		   width: 100px;
		}
		.uploadResult ul li img {
		   cursor: pointer;
		}
		.btn-icon {
			margin-left: 5%;
		}
</style>
<style>
		.bigPictureWrapper {
			position: absolute;
			display: none;
			justify-content: center;
			align-items: center;
			top:0%;
			left:0%;
			width:100%;
			height:100%;
			background: rgba(0,0,0,0.2);
			z-index: 9999;
			margin: 0;
		}
		
		.bigPicture {
			position: relative;
			display:flex;
			justify-content: center;
			align-items: center;
			/*overflow: hidden;*/
		}
		
		.bigPicture img {
			max-width: 60%;
			/*object-fit: contain;*/
			cursor: pointer;
		}
</style>

<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Read</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">Board Read Page</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

        <div class="form-group">
          <label>Bno</label> <input class="form-control" name='bno' value='<c:out value="${board.bno}" />' readonly="readonly">
        </div>

        <div class="form-group">
          <label>Title</label> <input class="form-control" name='title' value='<c:out value="${board.title}" />' readonly="readonly">
        </div>

        <div class="form-group">
          <label>Text area</label>
          <textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}" /></textarea>
        </div>

        <div class="form-group">
          <label>Writer</label>
          <input class="form-control" name='writer' value='<c:out value="${board.writer}" />' readonly="readonly">
        </div>
        
        <!-- author가 로그인 된 userid와 일치하는 경우에만 Modify 되도록 함 -->
        <sec:authentication property="principal" var="pinfo"/>

        <sec:authorize access="isAuthenticated()">
	        <c:if test="${pinfo.username eq board.writer}">
	        	<button data-oper='modify' class="btn btn-default">Modify</button>
	        </c:if>
        </sec:authorize>

		<!-- 
		<button data-oper='modify' class="btn btn-default" onclick="location.href='${context}/board/modify?bno=<c:out value="${board.bno}" />'">Modify</button>
		-->
		<%--
		<button data-oper='list' class="btn btn-info" onclick="location.href='${context}/board/list'">List</button>
		--%>
		<button data-oper='list' class="btn btn-info">List</button>
		
		
		<form id='operForm' action="${context}/board/modify" method="get">
			<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
			<input type='hidden' id='pageNum' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
			<input type='hidden' id='amount' name='amount' value='<c:out value="${cri.amount}"/>'>
			<!-- 345 page code added -->
			<input type='hidden' id='type' name='type' value='<c:out value="${cri.type}"/>'>
			<input type='hidden' id='keyword' name='keyword' value='<c:out value="${cri.keyword}"/>'>
		</form>
		<!-- 317 page -->

      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->
<div class='bigPictureWrapper d-flex'>
	<div class='bigPicture d-flex'>
		
	</div>
</div>

<!-- page414 댓글 목록 box -->
<div class='row'>
	<div class="col-lg-12">
		<div class="panel panel-default">
			<!-- Page 419 coding 시 주석처리됨 -->
			<!--
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Reply
			</div>
			-->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
				      	<div class="panel-heading">Files</div>
				      	<!-- /.panel-heading -->
				      	<div class="panel-body">
				        
				        	<div class='uploadResult'> 
				          		<ul>
				          		</ul>
				        	</div>
				      	</div>
				      	<!--  end panel-body -->
			    	</div>
			    	<!--  end panel-body -->
			  	</div>
			  	<!-- end panel -->
			</div>
			<!-- /.row -->
			
			<!-- new entry button added -->
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Reply
				<!-- Page718 authentication으로 Log in 한 회원만 새 댓글을 쓸
					수 있도록 함 -->
				<sec:authorize access="isAuthenticated()">
					<button id="addReplyBtn" class="btn btn-default btn-xs pull-right">New Reply</button>
				</sec:authorize>
			</div>
			
			<!-- /.panel-heading -->
			<div class="panel-body">        
				<ul class="chat">
					<!-- reply entries -->
					
					<!-- 댓글 출력 부분 js에서 html출력으로 대체하기 때문에 주석처리됨 -->
					<!-- 댓글 번호를 받기 위한 data-rno attribute -->
					<!--
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong>
								<small class="pull-right text-muted">2022-08-04 14:56</small>
							</div>
							<p>yeah baby that's what i'm talkin about</p>
						</div>
					</li>
					-->
					<!-- /reply entries -->
				</ul>
				<!-- /ul.chat -->
			</div>
			<!-- /.panel-body -->
			<!-- Page 439에서 추가한 source -->
			<div class="panel-footer">
			
			</div>
			<!-- /.panel-footer -->
		</div>
		<!-- /.panel -->
	</div>
</div>
<!-- /.row -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
				<div class="form-group">
					<label>Reply</label> 
					<input class="form-control" name='reply' value='New Reply'>
				</div>      
				<div class="form-group">
					<label>Replyer</label> 
					<input class="form-control" name='replyer' value='replyer' readonly="readonly">
				</div>
				<div class="form-group">
					<label>Reply Date</label> 
					<input class="form-control" name='replyDate' value='2018-01-01 13:13'>
				</div>
      
			</div>
			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
			</div>
		</div>
        <!-- /.modal-content -->
	</div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript" src="${context}/resources/js/reply.js"></script>

<script type="text/javascript">
	// page 415 reply event handler
	$(document).ready(function() {
		var bnoValue = '<c:out value="${board.bno}"/>';
		var replyUL = $(".chat");
		
		showList(1);
		// param : Page Number (defualt 1)
		
		function showList(page) {
			console.log("show list : " + page);
			// getList(param, callback, error). error function은 지정되지 않음
			replyService.getList({bno: bnoValue, page: page || 1},
				function(replyCnt, list){
				// paging 처리를 위해 callback 함수에 parameter replyCnt 추가
					console.log("replyCnt : " + replyCnt);
					console.log("list: " + list);
					
					if (page == -1) {
						pageNum = Math.ceil(replyCnt / 10.0);
						showList(pageNum);
						return;
					}
					
					var str = "";
					
					// Page427, Page442 Page733 댓글 삭제할 때 get.jsp 파일의 자바스크립트에서
					// 댓글 삭제 시 2개 이상일 때는 1개 댓글을 삭제해도 문제가 없는데,
					// 댓글이 1개 남아 있는 경우에는 삭제 후 새로고침(F5) 해줘야 갱신됨.
					// 이것은 다음의 if 조건문 때문인데,
					// 문제 해결을 위하여 아래와 같이  "자바스크립트 페이지 한번만 새로고침 Reload"를 적용했음.  
					if(list == null || list.length == 0){

						// Page 438 소스 코딩할 때 아래 1줄 구문은 주석 처리함
						// replyUL.html("");

						// 자바스크립트 페이지 한번만 새로고침 Reload(소스 코딩 시작)
						if (self.name != 'reload') {
							self.name = 'reload';
							self.location.reload(true);
						}
						else self.name = ''; 
						// 자바스크립트 페이지 한번만 새로고침 Reload(소스 코딩 끝)
						
						return;
					}
					
					for(var i = 0, len = list.length || 0; i < len; i++){
						str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
						str += "<div><div class='header'><strong class='primary-font'>" + list[i].replyer + "</strong>";
						str += "<small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small></div>";
						str += "<p>" + list[i].reply + "</p></div></li>";
					}
					replyUL.html(str);
					
					showReplyPage(replyCnt);
				});
			// getList(param, callback, error)
		}
		// showList(page) page : path variable
		
		// Page 440 : paging을 위한 method
		var pageNum = 1;
		var replyPageFooter = $(".panel-footer");
		
		function showReplyPage(replyCnt){
			var endNum = Math.ceil(pageNum / 10.0) * 10;
			var startNum = endNum - 9;
			
			var prev = startNum != 1;
			var next = false;
			
			if(endNum * 10 >= replyCnt) {
				endNum = Math.ceil(replyCnt/10.0);
			}
			
			if(endNum * 10 < replyCnt) {
				next = true;
			}
			
			var str = "<ul class='pagination pull-right'>";
			
			if(prev) {
				str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'>Previous</a></li>";
			}
			
			for(var i = startNum; i <= endNum; i++) {
				var active = pageNum == i ? "active" : "";
				
				str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>";
			}
			
			if(next) {
				str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'>Next</a></li>";
			}
			
			str += "</ul></div>";
			
			console.log(str);
			replyPageFooter.html(str);
		}
		
		replyPageFooter.on("click", "li a", function(e){
			e.preventDefault();
			console.log("Page click");
			
			var targetPageNum = $(this).attr("href");
			
			console.log("targetPageNum : " + targetPageNum);
			
			pageNum = targetPageNum;
			
			showList(pageNum);
		});
		
		// page 422 modal handler
		var modal = $(".modal");
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		
		var replyer = null;

		<sec:authorize access="isAuthenticated()">
			replyer = '<sec:authentication property="principal.username"/>';
		</sec:authorize>
		 
		var csrfHeaderName ="${_csrf.headerName}"; 
		var csrfTokenValue="${_csrf.token}";
		
		$("#addReplyBtn").on("click", function(e){
			modal.find("input").val("");
			// Page728 : replyer가 현재 로그인 한 user로 고정되도록 함
			modal.find("input[name='replyer']").val(replyer);
			
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id !='modalCloseBtn']").hide();
			
			modalRegisterBtn.show();
			
			$(".modal").modal("show");
		});
		
		// Page728 모든 ajax 전송 시 CSRF token header beforeSend
		$(document).ajaxSend(function(e, xhr, options) { 
	    	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue); 
	    });
		
		// page 423 modal registerbtn
		modalRegisterBtn.on("click", function(e){
			var reply = {
					reply: modalInputReply.val(),
					replyer: modalInputReplyer.val(),
					bno: bnoValue
				};
			replyService.add(reply, function(result){
				alert(result);
				
				modal.find("input").val("");
				modal.modal("hide");
				
				// showList(1);
				// 댓글을 추가한 후 댓글 목록을 1 page로 갱신
				showList(-1);
				// showList method에 추가된 기능으로 마지막 페이지를 보여주게 됨
				// 서버에서 data를 여러번 호출하게 되긴 하지만 댓글의 등록이 조회, paging
				// 에 비해 빈도수가 적기 때문에 이러한 방식을 사용함
			});
		});
		
		// page 425 event handler (Shows modify, remove btn)
		$(".chat").on("click", "li", function(e){
			var rno = $(this).data("rno");
			
			replyService.get(rno, function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
				modal.data("rno", reply.rno);
				
				modal.find("button[id != 'modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				
				$(".modal").modal("show");
			});
		})
		
		// page 427
		modalModBtn.on("click", function(e){
			// remove와 마찬가지로 검증절차를 추가 (page733)
			var originalReplyer = modalInputReplyer.val();
			
			// 검증을 위해 replyer data를 추가
			// var reply = {rno:modal.data("rno"), reply: modalInputReply.val()};
			var reply = {rno: modal.data("rno"),
					reply: modalInputReply.val(),
					replyer: originalReplyer};
			if(!replyer) {
				alert("You have to be logged in to modify");
				modal.modal("hide");
				return;
			}
			
			console.log("Original Replyer: " + originalReplyer);
			
			if(replyer != originalReplyer) {
				alert("You can only modify your replies");
				modal.modal("hide");
				return;
			}
			
			replyService.update(reply, function(result){
				alert(result);
				modal.modal("hide");
				// showList(1);
				showList(pageNum);
			});
		});
		
		modalRemoveBtn.on("click", function(e){
			var rno = modal.data("rno");
			
			// 댓글의 작성자를 함께 전송하여 검증절차를 걸친 후 작성자가 일치하지 않는 경우
			// 삭제 작업을 할 수 없게 함 (page730)
			console.log("RNO: " + rno);
	        console.log("REPLYER: " + replyer);
	        
	        if(!replyer){
	           alert("You have to be logged in to remove");
	           modal.modal("hide");
	           return;
	        }
	        
	        var originalReplyer = modalInputReplyer.val();
	        
	        console.log("Original Replyer: " + originalReplyer);
	        
	        if(replyer != originalReplyer){
	           
	           alert("You can only remove your replies");
	           modal.modal("hide");
	           return;
	           
	        }
			
			// originalReplyer를 검증하여 삭제할 수 있도록 함
			// replyService.remove(rno, function(result){
			replyService.remove(rno, originalReplyer, function(result){
				alert(result);
				modal.modal("hide");
				// showList(1);
				showList(pageNum);
			});
		});
		
		$("#modalCloseBtn").on("click", function(){
			modal.modal("hide");
		});
		// why the heck book did not do this
		
	});
	// document.ready function
</script>

<!--
<script type="text/javascript">
/*
	$(document).ready(function() {
		console.log(replyService);
		// variable from reply.js
	});
*/
/*
	console.log("=======================");
	console.log("JS REPLY TEST")
	
	var bnoValue = '<c:out value="${board.bno}"/>';
*/
/*
	replyService.add(
			{reply: "JS Test", replyer: "Randolph", bno: bnoValue},
			// reply
			function (result) {
				alert("Result: " + result);
			}
			// callback
	);
*/
/*
	replyService.getList({bno:bnoValue, page:1}, function(list){
		for(var i = 0, len = list.length || 0; i < len; i++){
			console.log(list[i]);
		}
	});
*/
	// function(list)가 getList 함수의 callback 함수가 된다. reply.js 참고
/*
	replyService.remove(10, function(count){
		console.log(count);
		
		if (count === 'Success') {
			alert("Successfully removed");
		}
	}, function(error){
		alert("Error occurred. reply is not exist or synthetic error");
	});
*/
	// if (count === 'string')에 들어가는 문자열은 ReplyController의 81행의 log
	// 문구와 일치해야 한다. log의 text를 검증하여 alert를 띄우는 방식이기 때문

/*
	replyService.update({
		rno: 11,
		bno: bnoValue,
		reply: "Modify test via javascript"
	}, function(result){
		alert("Successfully modified reply");
	});
*/
	/* update(reply, callback, error)
	 * reply에 rno, bno, reply속성값을 대입하여 callback 함수인 function(result)
	 * 를 유효성 검증 이후 실행. error scenario는 생성하지 않음
	 */
	 
/*
	 replyService.get(21, function(data){
		 console.log(data);
	 });
	 // simple as that
*/	 
</script>
-->

<script type="text/javascript">
	$(document).ready(function(){
		var ctx = getContextPath();
		
		function getContextPath() {
			return sessionStorage.getItem("contextpath");
		};
		// header.jsp 최하단 (385행) 참고. JS에서 contextpath 사용하는 법
		
		var operForm = $("#operForm");
		
		$("button[data-oper='modify']").on("click", function(e) {
			operForm.attr("action", ctx + "/board/modify").submit();
			// 수정 버튼 클릭 시 bno값을 같이 전달하고 form tag를 submit 처리
		});
		// button 클릭 시 #operForm form tag를 전송
		
		$("button[data-oper='list']").on("click", function(e) {
			// js에서 속성을 명시할 때 [] 사용
			operForm.find("#bno").remove();
			// list로 이동 시 form tag 내의 #bno 삭제?
			operForm.attr("action", ctx + "/board/list");
			operForm.submit();
		});
	});
	
</script>

<script type="text/javascript">
	$(document).ready(function(){
		(function(){
			var bno = '<c:out value="${board.bno}"/>';
			
		    $.getJSON("/board/getAttachList", {bno: bno}, function(arr){
				console.log(arr);
				
				var str = "";
			    
				$(arr).each(function(i, attach){
					//image type
					if(attach.fileType){
						var fileCallPath = encodeURIComponent(attach.uploadPath + "/sthmb_" + attach.uuid + "_" + attach.fileName);
						
						str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
						str += "<img src='/display?fileName=" + fileCallPath + "'>";
						str += "</div>";
						str += "</li>";
					} else {
						str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
						str += "<span> " + attach.fileName + "</span><br/>";
						str += "<img src='/resources/img/folder.png'></a>";
						str += "</div>";
						str += "</li>";
					}
				});
				
				$(".uploadResult ul").html(str);
			    
			}); // getjson
			
		})(); // function
		
		$(".uploadResult").on("click","li", function(e){
			console.log("view image");
			
			var liObj = $(this);
			
			var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+"_" + liObj.data("filename"));
			
			if(liObj.data("type")){
				showImage(path.replace(new RegExp(/\\/g),"/"));
			} else {
				//download 
				self.location ="/download?fileName="+path
			}
		});
		
		function showImage(fileCallPath){
			console.log(fileCallPath);
			
			$(".bigPictureWrapper").css("display","flex").show();
			
			$(".bigPicture")
			.html("<img src='/display?fileName=" + fileCallPath + "' >")
			.animate({width:'100%', height: '100%'}, 150);
		}
		
		$(".bigPictureWrapper").on("click", function(e){
			$(".bigPicture").animate({width:'0%', height: '0%'}, 150);
			setTimeout(function(){
				$('.bigPictureWrapper').hide();
			}, 150);
		});
	}); // document ready
</script>

<%@include file="../includes/footer.jsp"%>
