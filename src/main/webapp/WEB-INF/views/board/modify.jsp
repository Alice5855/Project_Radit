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
			width: 600px;
			/*object-fit: contain;*/
			cursor: pointer;
		}
</style>

<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Modify</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
	
			<div class="panel-heading">Board Modify Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
		
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
					  <label>Bno</label> 
					  <input class="form-control" name='bno' value='<c:out value="${board.bno}" />' readonly="readonly">
					</div>
					
					<div class="form-group">
					  <label>Title</label> 
					  <input class="form-control" name='title' value='<c:out value="${board.title}" />' >
					</div>
					
					<div class="form-group">
					  <label>Text area</label>
					  <textarea class="form-control" rows="3" name='content' ><c:out value="${board.content}" /></textarea>
					</div>
					
					<div class="form-group">
					  <label>Writer</label> 
					  <input class="form-control" name='writer' value='<c:out value="${board.writer}" />' readonly="readonly">            
					</div>
					
					<div class="form-group">
					  <label>RegDate</label> 
					  <input class="form-control" name='regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regDate}"/>' readonly="readonly">            
					</div>
					
					<div class="form-group">
						<label>Update Date</label> 
						<input class="form-control" name='updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate}"/>' readonly="readonly">            
					</div>
					
					<!-- author가 로그인 된 userid와 일치하는 경우에만 Modify 되도록 함 -->
					<sec:authentication property="principal" var="pinfo"/>

			        <sec:authorize access="isAuthenticated()">
				        <c:if test="${pinfo.username eq board.writer}">
							<button type="submit" data-oper='modify' class="btn btn-default">Modify</button>
							<button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
						</c:if>
			        </sec:authorize>
					<button type="submit" data-oper='list' class="btn btn-info">List</button>
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

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-heading">Files</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="form-group uploadDiv">
					<input type="file" name='uploadFile' multiple="multiple">
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
<!-- Page590 coding 시 주석처리됨 -->
<%--
<script type="text/javascript">
	$(document).ready(function(){
		var ctx = getContextPath();
		
		function getContextPath() {
			return sessionStorage.getItem("contextpath");
		};
		// header.jsp 최하단 (385행) 참고. JS에서 contextpath 사용하는 법
		
		var formObj = $("form");
		
		$('button').on("click", function(e) {
			e.preventDefault();
			// button type이 submit이기 때문에 기본 동작을 해제
			
			var operation = $(this).data("oper");
			
			console.log(operation);
			
			if (operation === 'remove') {
				formObj.attr("action", ctx + "/board/remove");
			} else if (operation === 'list') {
				formObj.attr("action", ctx + "/board/list").attr("method", "get");
				
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				// page 321
				var typeTag = $("input[name='type']").clone();
				var keywordTag = $("input[name='keyword']").clone();
				// page 347
				
				formObj.empty();
				// list button 클릭 시 action, method 속성을 변경
				// form에 입력되었던 data를 삭제하고 submit() 처리함
				
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				
				formObj.append(typeTag);
				formObj.append(keywordTag);
			};
			// button tag의 'data-oper' attribute를 활용하여 동작을 골라
			// 수행함
			formObj.submit();
			// formObj를 submit 처리하여 기능이 할당되어있지 않은 modify 버튼에
			// submit()을 할 수 있도록 하는것. if문이 실행 될 때에도(각각의 
			// 시나리오에 따라), 실행되지 않을 때에도(modify) submit()이 실행됨
		});
	});
</script>
--%>
<!-- Page 590 수정 된 script -->
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
				
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				var keywordTag = $("input[name='keyword']").clone();
				var typeTag = $("input[name='type']").clone();      
				
				formObj.empty();
				
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				formObj.append(keywordTag);
				formObj.append(typeTag);     
			  
			} else if(operation === 'modify'){
			    
				console.log("submit clicked");
				
				var str = "";
				
				$(".uploadResult ul li").each(function(i, obj){
					
					var jobj = $(obj);
					
					console.dir(jobj);
					
					str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].fileType' value='"+ jobj.data("type") + "'>";

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
			var bno = '<c:out value="${board.bno}"/>';
			
			$.getJSON("/board/getAttachList", {bno: bno}, function(arr){
				console.log(arr);
				
				var str = "";
				
				$(arr).each(function(i, attach){
				    
				    //image type
				    if(attach.fileType){
						var fileCallPath = encodeURIComponent(attach.uploadPath + "/sthmb_" + attach.uuid + "_" + attach.fileName);
						
						str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
						str += "<span>" + attach.fileName + "</span>";
						str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/display?fileName=" + fileCallPath + "'>";
						str += "</div></li>";
				    } else {
				        
						str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
						str += "<span>" + attach.fileName + "</span><br/>";
						str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/resources/img/folder.png'></a>";
						str += "</div></li>";
				    }
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
		
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|7z|rar)$");
		var maxSize = 5242880; //5MB
		
		function checkExtension(fileName, fileSize){
		  
			if(fileSize >= maxSize){
				alert("Exeeded max file size");
				return false;
			}
		  
			if(regex.test(fileName)){
				alert("Invalid type of file");
				return false;
			}
			return true;
		}
		
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
			  
				if(obj.image){
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/sthmb_" + obj.uuid + "_" + obj.fileName);
					str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "' ><div>";
					str += "<span>"+ obj.fileName + "</span>";
					str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/display?fileName=" + fileCallPath + "'>";
					str += "</div></li>";
				} else {
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);               
					var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
					
					str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "' ><div>";
					str += "<span>" + obj.fileName + "</span>";
					str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/resources/img/folder.png'></a>";
					str += "</div></li>";
				}
			
			});
			
			uploadUL.append(str);
		}
	
	});
</script>

<%@include file="../includes/footer.jsp"%>
