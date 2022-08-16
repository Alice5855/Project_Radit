<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>
<html>
<head>
	<title>UploadAjax</title>
	<style>
		/*
		body{
			margin: 0;
		}
		*/
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
		   width: 1.5rem;
		}
		
		.uploadResult ul li img.thumbnail {
		   width: 100px;
		}
		
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
			object-fit: contain;
			cursor: pointer;
		}
		
		.uploadResult span {
			color: red;
			cursor: pointer;
		}
	</style>
</head>
<body>
	<!-- Ajax는 js로 활용할 수도 있지만 jquery를 이용하는 것이 훨씬 편리하다 -->
	<h1>File upload utilizing Ajax</h1>
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple="multiple">
	</div>
	<!-- Page 522 / .uploadResult 추가 -->
	<div class="uploadResult">
		<ul>
			
		</ul>
	</div>
	<button id="uploadBtn">Upload</button>
	<div class="bigPictureWrapper">
		<div class="bigPicture">
		
		</div>
	</div>
	
	<script
	  src="https://code.jquery.com/jquery-3.6.0.min.js"
	  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	  crossorigin="anonymous"></script>
	  
	<script type="text/javascript">
		// thumbnail click 시 원본 image를 보여주는 method
		function showImage(fileCallPath) {
			// alert(fileCallPath);
			$(".bigPictureWrapper").css("display", "flex").show();
			
			$(".bigPicture")
			.html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>")
			.animate({width: '100%', height: '100%'}, 150);
		}
		// 현재 단계는 정의 단계이고 function이 call되지 않았으므로 fileCallPath가 정의
		// 되어 있지 않아도 문제될 것은 없다. 다시 말해 이 함수가 call 되는 위치는
		// fileCallPath 변수가 정의된 이후가 되겠다
		
		$(".bigPictureWrapper").on("click", function(e){
			$(".bigPicture").animate({width: '0%', height: '0%'}, 150);
			
			setTimeout(() => {
				$(this).hide();
			}, 300);
			// '=>' lambda expression not works in IE but IE is dead so
			// if you rly care so much bout IE then here the code is
			/*
			setTimeout(function(){
				$(this).hide();
			}, 300);
			*/
		});
		
		// Page547 'x' mark event handler
		$(".uploadResult").on("click", "span", function(e){
			var targetFile = $(this).data("file");
			var type = $(this).data("type");
			console.log(targetFile);
			console.log(type);
			
			$.ajax({
				url: '/deleteFile',
				data: {fileName: targetFile, type: type},
				dataType: 'text',
				type: 'POST',
				success: function(result) {
					alert(result);
				}
			}); // ajax
		}); // uploadResult onclick
		
	
		$(document).ready(function(){
			var cloneObj = $(".uploadDiv").clone();
			// Added(page520)
			
			var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|7z|rar)$");
			// 보안상의 이유로 첨부파일의 확장자를 제한
			var maxSize = 5242880; // 5MB
			
			function checkExtension(fileName, fileSize) {
				if(fileSize >= maxSize) {
					alert("The file is too big");
					return false;
				}
				if(regex.test(fileName)){
					// RegEx(정규표현식)으로 file의 이름을 검증
					alert("Invalid type");
					return false;
				}
				return true;
			};
			
			var uploadResult = $(".uploadResult ul");
			
			function showUploadedFile(uploadResultArr) {
				var str = "";
				
				$(uploadResultArr).each(function(i, obj){
					
					if (!obj.image) {
						// str += "<li><img src='/resources/img/folder.png'>" + obj.fileName + "</li>";
						
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/")
						
						// Page 537 anchor to download when clicked
						// str += "<li><a href='/download?fileName=" + fileCallPath + "'>" + "<img class='icon' src='/resources/img/folder.png'>" + obj.fileName + "</a></li>";
						
						// Page 546
						str += "<li><div><a href='/download?fileName=" + fileCallPath + "'>" + "<img class='icon' src='/resources/img/folder.png'>" + obj.fileName + "</a>" + "<span data-file=\'" + fileCallPath + "\' data-type='file'> x </span>" + "</div></li>";
					} else {
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
						
						// Page 528 adds Thumbnail
						// str += "<li><img class='thumbnail' src='/display?fileName=" + fileCallPath + "'></li>";
						
						var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
						
						originPath = originPath.replace(new RegExp(/\\/g), "/");
						// str += "<li><a href=\"javascript:showImage(\'" + originPath + "\')\"><img src='/display?fileName=" + fileCallPath + "' class='thumbnail'></a></li>";
						str += "<li><a href=\"javascript:showImage(\'" + originPath + "\')\"><img src='/display?fileName=" + fileCallPath + "' class='thumbnail'></a>" + "<span data-file=\'" + fileCallPath + "\' data-type='image'> x </span>" + "</li>";
					}
				});
				
				uploadResult.append(str);
			};
			
			$("#uploadBtn").on("click", function(e){
				
				var formData = new FormData();
				var inputFile = $("input[name='uploadFile']")
				var files = inputFile[0].files;
				console.log(files);
				// jQuery를 활용하는 경우 file upload는 FormData 객체를 활용.
				// FormData는 가상 form tag와 같은 역할을 한다
				
				for (var i = 0; i < files.length; i++) {
					// Page 507 checkExtension() method 적용하여
					// file 이름과 크기를 검증하여 false가 return 될 경우(검증에 
					// 걸리게 되는 경우) false를 return하여 upload를 중지
					if(!checkExtension(files[i].name, files[i].size)){
						return false;
					}
					
					formData.append("uploadFile", files[i]);
				}
				
				// 첨부 파일 data를 for문으로 formData에 append한 뒤 ajax로
				// formData를 전송. processData와 contentType을 false로 지정
				// 해야만 성공적으로 전송된다. file handle은 Controller에서
				// MultipartFile type을 이용하여 Handle 하게 됨
				
				$.ajax({
					url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData,
					type: 'POST',
					dataType: 'JSON',
					// Added (page517)
					success: function(result){
						alert("Successfully Uploaded");
						console.log(result);
						
						showUploadedFile(result);
						
						$(".uploadDiv").html(cloneObj.html());
						// Added(page521)
					}
				}); // ajax
			}); // onclick
		}); // doc ready
	</script>
	
</body>
</html>