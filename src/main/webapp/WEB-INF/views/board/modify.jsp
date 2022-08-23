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
		.form-group {
      	margin-bottom: 1rem;
      	margin-top: 1rem;
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
			width: 600px;
			/*object-fit: contain;*/
			cursor: pointer;
		}
</style>
<div class="container-fluid">
	<div class="row">
	  <div class="col-lg-12">
	    <h1 class="page-header">글 수정</h1>
	  </div>
	  <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
		
				<form role="form" action="${context}/board/modify" method="post">
				<!-- Page719 CSRF Token을 hidden input으로 추가함 -->
        		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        
					<input type='hidden' id='pageNum' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
					<input type='hidden' id='amount' name='amount' value='<c:out value="${cri.amount}"/>'>
			    	<!-- 319 page modify code from get.jsp 49, 50 row -->
			    	<input type='hidden' id='type' name='type' value='<c:out value="${cri.type}"/>'>
					<input type='hidden' id='keyword' name='keyword' value='<c:out value="${cri.keyword}"/>'>
					<!-- get.jsp 52, 53 row -->
			    	
					<div class="form-group">
					  <label>게시글 번호</label>
					  <input class="form-control" name='b_number' value='<c:out value="${board.b_number}" />' readonly="readonly">
					</div>
					
					<div class="form-group">
					  <label>제목</label>
					  <input class="form-control" name='b_title' value='<c:out value="${board.b_title}" />' >
					</div>
					
					<div class="form-group">
					  <label>내용</label>
					  <textarea class="form-control" rows="3" name='b_text' style="resize: none;"><c:out value="${board.b_text}" /></textarea>
					</div>
					
					<div class="form-group">
					  <label>글쓴이</label>
					  <input class="form-control" name='u_email' value='<c:out value="${board.u_email}" />' readonly>            
					</div>
					
					<div class="form-group">
					  <label>등록일</label>
					  <input class="form-control" name='b_regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_regDate}"/>' readonly="readonly">            
					</div>
					
					<div class="form-group">
						<label>수정일</label>
						<input class="form-control" name='b_updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_updateDate}"/>' readonly="readonly">            
					</div>
					
					<!-- author가 로그인 된 userid와 일치하는 경우에만 Modify 되도록 함 -->
					
					<sec:authentication property="principal" var="pinfo"/>

			        <sec:authorize access="isAuthenticated()">
				        <c:if test="${pinfo.username eq board.u_email}">
				        
							<button type="submit" data-oper='modify' class="btn btn-secondary">수정</button>
							<button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>
							
						</c:if>
			        </sec:authorize>
			        
					<button type="submit" data-oper='list' class="btn btn-info">리스트</button>
				</form>
	
		    </div>
		    <!--  end panel-body -->

		</div>
		<!--  end panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class='bigPictureWrapper'>
	<div class='bigPicture'>
		
	</div>
</div>

<div class="row container-fluid">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="">
				<div class="form-group uploadDiv">
					<label for="formFile" class="form-label">이미지 하나를 선택하세요</label>
					<input id="formFile" type="file" name='uploadFile' class="form-control" accept="image/*">
				</div>
        
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
<script
  src="https://code.jquery.com/jquery-3.6.0.min.js"
  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
  crossorigin="anonymous"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var formObj = $("form");
	
		$('button').on("click", function(e){

			e.preventDefault(); 
			
			var operation = $(this).data("oper");
			
			console.log(operation);
			
			if(operation === 'remove'){
				formObj.attr("action", "/board/remove");
			
			} else if(operation === 'list'){
				//move to list
				formObj.attr("action", "/board/list").attr("method","get");
			  
			} else if(operation === 'modify'){
			    
				console.log("submit clicked");
				
				var str = "";
				
				$(".uploadResult ul li").each(function(i, obj){
					
					var jobj = $(obj);
					
					console.dir(jobj);
					
					str += "<input type='hidden' name='attachList[" + i + "].b_fileName' value='" + jobj.data("filename") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].b_uuid' value='" + jobj.data("uuid") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].b_uploadPath' value='" + jobj.data("path") + "'>";

				});
				formObj.append(str).submit();
			}
		
		  formObj.submit();
		});

	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		(function(){
			var b_number = '<c:out value="${board.b_number}"/>';
			
			$.getJSON("/board/getAttachList", {b_number: b_number}, function(arr){
				console.log(arr);
				
				var str = "";
				
				$(arr).each(function(i, attach){
				    
					var filePath = attach.b_uploadPath + "/sthmb_" + attach.b_uuid + "_" + attach.b_fileName;
					var fileLink = filePath.replace(new RegExp(/\\/g),"/");
					
					str += "<li data-path='" + attach.b_uploadPath + "' data-uuid='" + attach.b_uuid + "' data-filename='" + attach.b_fileName + "' ><div>";
					str += "<span>" + attach.b_fileName + "</span>";
					str += "<button type='button' data-file=\'" + fileLink + "\' class='btn btn-secondary'><i class='bi bi-x-circle'></i></button><br>";
					str += "<img src='/display?fileName=" + fileLink + "'>";
					str += "</div></li>";
				    
				}); // arr.each
			
			
			$(".uploadResult ul").html(str);
		    
			});//end getjson
		})();//end function
		
		
		$(".uploadResult").on("click", "button", function(e){
			console.log("delete file");
			
			if(confirm("Remove this file? ")){
			
				var targetLi = $(this).closest("li");
				targetLi.remove();
				
			}
		});  
		
		var regex = new RegExp("(.*?)\.(jpg|jpeg|gif|png|bmp|webp)$");
		var maxSize = 5242880; //5MB
		
		function checkExtension(fileName, fileSize) {
			if(fileSize >= (maxSize * 4)) { // Up to 20MB
			   alert("업로드 파일은 20MB를 초과할 수 없습니다");
			   return false;
			}
			if(!regex.test(fileName)){
			   // RegEx(정규표현식)으로 file의 이름을 검증
			   alert("올바르지 않은 유형의 파일입니다");
			   return false;
			}
			return true;
		};
		
		// Page 721 : CSRF token을 Header에 전달하기 위하여 변수선언. ajax
		// 에서 data 전달 시 token과 headername을 함께 전달하게 된다
		var csrfHeaderName ="${_csrf.headerName}"; 
		var csrfTokenValue="${_csrf.token}";
		
		$("input[type='file']").change(function(e){
		
			var formData = new FormData();
			
			var inputFile = $("input[name='uploadFile']");
			
			var files = inputFile[0].files;
			
			for(var i = 0; i < files.length; i++){
			
				if(!checkExtension(files[i].name, files[i].size) ){
					return false;
				}
				
				formData.append("uploadFile", files[i]);
			}
			
			$.ajax({
				url: '/uploadAjaxAction',
				processData: false, 
				contentType: false,
				beforeSend: function(xhr){
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
				},
				data: formData,
				type: 'POST',
				dataType:'json',
				success: function(result){
					console.log(result); 
					showUploadResult(result); //업로드 결과 처리 함수 
				}
			}); //$.ajax
		  
		});    
		
		function showUploadResult(uploadResultArr){
			if(!uploadResultArr || uploadResultArr.length == 0){ return; }
			
			var uploadUL = $(".uploadResult ul");
			
			var str = "";
			
			$(uploadResultArr).each(function(i, obj){
			  
				var filePath = obj.b_uploadPath + "/sthmb_" + obj.b_uuid + "_" + obj.b_fileName;
	           	var fileLink = filePath.replace(new RegExp(/\\/g),"/");
				
				str += "<li data-path='" + obj.b_uploadPath + "' data-uuid='" + obj.b_uuid + "' data-filename='" + obj.b_fileName + "' ><div>";
				str += "<span> "+ obj.b_fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileLink + "\' class='btn btn-secondary'><i class='bi bi-x-circle'></i></button><br>";
				str += "<img class='thumbnail' src='/display?fileName=" + fileLink + "'>";
				str += "</div></li>";
				
			});
			
			uploadUL.append(str);
		}
	
	});
</script>

<%@include file="../includes/footer.jsp"%>
