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
         background-color: #F5F5F5; 
          background:rgba(255,255,255,0.5);
         z-index: 100;
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
	    <h1 class="page-header">새 글 쓰기</h1>
	  </div>
	  <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	
	<div class="row">
	  <div class="col-lg-12">
	    <div class="panel panel-default">
	
	        <form role="form" action="${context}/board/register" method="post">
		        <!-- Page714 CSRF Token을 hidden input으로 추가함 -->
		        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		        
		        <div class="form-group">
					<label>제목</label>
					<input class="form-control" name='b_title'>
				</div>
		
				<div class="form-group">
					<label>내용</label>
					<textarea class="form-control" rows="3" name='b_text' style="resize: none;"></textarea>
				</div>
		
				<div class="form-group">
					<label>글쓴이</label> 
		            <input class="form-control" name='u_email' value='<sec:authentication property="principal.username" />' disabled readonly>
		            
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-secondary">등록</button>
					<button type="reset" class="btn btn-default" onclick="javascript:history.go(-1);">취소</button>
				</div>
	        </form>
	
	      </div>
	      <!--  end panel-body -->
	
	    </div>
	    <!--  end panel-body -->
	  </div>
	  <!-- end panel -->
	
	<!-- file upload form -->
	<div class="row">
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
		<!-- col-lg-12 -->
	</div>
	<!-- /.row -->
</div>

<script
  src="https://code.jquery.com/jquery-3.6.0.min.js"
  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
  crossorigin="anonymous"></script>
<script type="text/javascript">
   // file upload handle
   $(document).ready(function(e){
      var formObj = $("form[role='form']");
      
      $("button[type='submit']").on("click", function(e){
         e.preventDefault();
         console.log("Submit Button Clicked");
         
         // Page564 
         var str = "";
            
         $(".uploadResult ul li").each(function(i, obj){
            var jobj = $(obj);
            
            console.dir(jobj);
            console.log("===========================");
            console.log(jobj.data("filename"));
            
            str += "<input type='hidden' name='attachList[" + i + "].b_fileName' value='" + jobj.data("filename") + "'>";
            str += "<input type='hidden' name='attachList[" + i + "].b_uuid' value='" + jobj.data("uuid") + "'>";
            str += "<input type='hidden' name='attachList[" + i + "].b_uploadPath' value='" + jobj.data("path") + "'>";
         }); // uploadResult ul li.each func
         console.log(str);
         formObj.append(str).submit();
      }); // submit button on click
      
      var regex = new RegExp("(.*?)\.(jpg|jpeg|gif|png|bmp|webp)$");
      var maxSize = 5242880; // 5MB
      
      var formFile = $("input[type='file']");
      
	
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
			
			console.log(files[i]);
			
			if(!checkExtension(files[i].name, files[i].size) ){
				return false;
			}
			
			if(parseInt(files[i].files.length) > 1){
				alert("하나의 이미지만 업로드 할 수 있습니다");
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
            // csrf token을 data 전송 전에 header로 전송
            data: formData,
            type: 'POST',
            dataType:'json',
            success: function(result){
               console.log(result);
               showUploadResult(result);
            }
         }); // ajax
       }); // input.change
       
      function showUploadResult(uploadResultArr){
         if(!uploadResultArr || uploadResultArr.length == 0){
            return;
         }
          
         var uploadUL = $(".uploadResult ul");
         
         var str = "";
         
         $(uploadResultArr).each(function(i, obj){
           	var filePath = obj.b_uploadPath + "/sthmb_" + obj.b_uuid + "_" + obj.b_fileName;
           	var fileLink = filePath.replace(new RegExp(/\\/g),"/");
			
			str += "<li data-path='" + obj.b_uploadPath + "' data-uuid='" + obj.b_uuid + "' data-filename='" + obj.b_fileName + "' ><div>";
			str += "<span> "+ obj.b_fileName + "</span>";
			str += "<img class='thumbnail' src='/display?fileName=" + fileLink + "'>";
			str += "<button type='button' data-file=\'" + fileLink + "\' class='btn btn-secondary'><i class='bi bi-x-circle'></i></button><br>";
			str += "</div></li>";
           }); // uploadResultArr.each
         uploadUL.append(str);
      } // showUploadResult func
      
      // delete btn handle
      $(".uploadResult").on("click", "button", function(e){
         
         console.log("delete file");
         
         var targetFile = $(this).data("file");
         // var type = $(this).data("type");
         
         var targetLi = $(this).closest("li");
         
         $.ajax({
            url: '/deleteFile',
            beforeSend: function(xhr){
               xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
            },
            // csrf token을 data 전송 전에 header로 전송
            data: {fileName: targetFile},
            dataType:'text',
            type: 'POST',
            success: function(result){
               alert(result);
               
               targetLi.remove();
               // ListItem을 삭제하여 업로드한 file이 보이지 않도록 함
            }
         }); //$.ajax
      }); // uploadResult.onclick func
      
   }); // document ready
</script>

<%@include file="../includes/footer.jsp"%>