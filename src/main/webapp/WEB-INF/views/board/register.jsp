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

<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Register</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">Board Register</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

        <form role="form" action="${context}/board/register" method="post">
        <!-- Page714 CSRF Token을 hidden input으로 추가함 -->
<%--         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
        
          <div class="form-group">
            <label>Title</label> <input class="form-control" name='b_title'>
          </div>

          <div class="form-group">
            <label>Text area</label>
            <textarea class="form-control" rows="3" name='b_text'></textarea>
          </div>

          <div class="form-group">
            <label>Writer</label> 
             <!-- Added value and ro for log in feature -->
            <input class="form-control" name='u_email'>
<%--             <input class="form-control" name='writer' value='<sec:authentication property="principal.username" />' readonly="readonly"> --%>
          </div>
          <button type="submit" class="btn btn-default">Submit</button>
          <button type="reset" class="btn btn-default" onclick="javascript:history.go(-1);">Cancel</button>
        </form>

      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->

<!-- file upload form -->
<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
      
        <div class="panel-heading">File Attach</div>
        <!-- /.panel-heading -->
        <div class="panel-body">
          <div class="form-group uploadDiv">
             <label for="formFile" class="form-label">Select file to upload</label>
              <input id="formFile" type="file" name='uploadFile' multiple>
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
            // str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("b_type") + "'>";
            // file을 전송할 때에 type이나 uuid와 같은 정보를 함께 전달하기 위한
            // hidden input tag를 추가
         }); // uploadResult ul li.each func
         console.log(str);
         formObj.append(str).submit();
      }); // submit button on click
      
      var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|7z|rar)$");
      // 보안상의 이유로 첨부파일의 확장자를 제한
      var maxSize = 5242880; // 5MB
      
      function checkExtension(fileName, fileSize) {
         if(fileSize >= maxSize) {
            alert("Exeeded max size");
            return false;
         }
         if(regex.test(fileName)){
            // RegEx(정규표현식)으로 file의 이름을 검증
            alert("Invalid type");
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
//             beforeSend: function(xhr){
//                xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
//             },
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
            // true : image
            //if(obj.image){
            	var filePath = obj.b_uploadPath + "/sthmb_" + obj.b_uuid + "_" + obj.b_fileName;
            	var fileLink = filePath.replace(new RegExp(/\\/g),"/");
				// var fileCallPath = encodeURIComponent(fileLink);
				
				// str += "<li><div>";
				// Page563 : 첨부 file 정보를 tag로 함께 전달
				str += "<li data-path='" + obj.b_uploadPath + "' data-uuid='" + obj.b_uuid + "' data-filename='" + obj.b_fileName + "' ><div>";
				str += "<span> "+ obj.b_fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileLink + "\' class='btn btn-warning btn-circle btn-icon'><i class='fa fa-times'></i></button><br>";
				str += "<img class='thumbnail' src='/display?fileName=" + fileLink + "'>";
				str += "</div></li>";
               /*
            } else {
               var fileCallPath = encodeURIComponent(obj.b_uploadPath + "/" + obj.b_uuid + "_" + obj.b_fileName);            
               var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
               
               // str += "<li><div>";
               // Page563 : 첨부 file 정보를 tag로 함께 전달
               str += "<li data-path='" + obj.b_uploadPath + "' data-uuid='" + obj.b_uuid + "' data-filename='" + obj.b_fileName + "' data-type='" + obj.b_image + "' ><div>";
               str += "<span> " + obj.b_fileName + "</span>";
               str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle btn-icon'><i class='fa fa-times'></i></button><br>";
               str += "<img class='icon' src='/resources/img/folder.png'></a>";
               str += "</div></li>";
            }
         	*/
           }); // uploadResultArr.each
         uploadUL.append(str);
      } // showUploadResult func
      
      // delete btn handle
      $(".uploadResult").on("click", "button", function(e){
         
         console.log("delete file");
         
         var targetFile = $(this).data("file");
         var type = $(this).data("type");
         
         var targetLi = $(this).closest("li");
         
         $.ajax({
            url: '/deleteFile',
//             beforeSend: function(xhr){
//                xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
//             },
            // csrf token을 data 전송 전에 header로 전송
            data: {fileName: targetFile, type:type},
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