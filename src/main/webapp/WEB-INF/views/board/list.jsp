<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>

<%@include file="../includes/header.jsp" %>
<style>
	a {
		color: black;
	}
	a:hover {
		color: black;
	}
	#title {
		font-size: 2.5rem;
	}
	#date {
		font-size: 0.5rem;
	}
	.mImgWrapper{
		/*width: 40%;*/
		max-height: 40%;
	}
	.mImgWrapper img{
		width: 100%;
		object-fit: cover;
	}
	.modal-dialog{
		max-width: 70% !important;
	}
	.b_Modal_Content {
		padding: 3%;
	}
	#regBtn{
		margin-right: 5% !important;
	}
	.mNumber {
		font-size: 0.5rem;
	}
	.mEmail {
		
	}
	.mTitle {
		font-size: 2.5rem;
	}
	.mText {
		margin-left: 5%;
	}
</style>
	<div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <button id="regBtn" class="btn btn-secondary btn-sm float-end mb-3 me-1" type="button" onclick="location.href='${context}/board/register'">새 글 작성</button>
                </div>
                <!-- /.panel-heading -->
                        <c:forEach items="${list}" var="board">
                        <div class="container" style="width:80% !important; margin-bottom: 10%;" id="custom-cards">
                        	<div class="row align-items-stretch g-4">
                        		<div class="card h-100 overflow-hidden rounded-4 shadow-lg">
                                <!-- BoardController.java의 37행 참고. model에 추가한 'list' attribute를 불러 온 것 -->
                                	<div class="card-content position-relative">
                                		<p style="display : none;"><c:out value="${board.b_number}" /></p>
                               			<a class="move" href='<c:out value="${board.b_number}" />' style="text-decoration: none;">
	                                		<p id="title" class="fw-bold mt-3 mb-2 ms-5">
	                                				<c:out value="${board.b_title}" />
	                                		</p>
	                                		
	                                		<c:if test= '${not empty board.b_img}'>
	                                		<div class="text-center">
	                                			<img id="thmbImg" src='/display?fileName=<c:out value="${board.b_img}" />'/>
                                			</div>
	                                		</c:if>
	                                		
	                               			<p class="text-end"><c:out value="${board.u_email}" /></p>
	                               			<p class="ms-3"><c:out value="${board.b_text}" /></p>
	                                		<p id="date" class="text-end text-muted"><fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_regDate}"/></p>
	                                		<p id="date" class="text-end text-muted"><fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_updateDate}"/></p>
                                		</a>
                                	</div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                            
                            <!-- pageNum가 parameter로 유지되어야 함.
                            	검색 버튼 클릭 시 1페이지로 이동
                            	한글data를 get method로 넘길 때 문제가 발생할 수 있음 -->
                            
                            <!-- Pagination -->
                            <div class="d-flex justify-content-center text-center">
                            	<div class="col-lg-12">
									<ul class="pagination">
										<c:if test="${pageMaker.prev}">
											<li class="paginate-item previous"><a class="page-link" href="${pageMaker.startPage - 1}">Previous</a></li>
										</c:if>
										
										<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
											<li class="paginate-item ${pageMaker.cri.pageNum == num ? 'active' : ''} ">
											<a class="page-link" href="${num}">${num}</a>
										</li>
										</c:forEach>
										
										<c:if test="${pageMaker.next}">
											<li class="paginate-item next"><a class="page-link" href="${pageMaker.endPage + 1}">Next</a></li>
										</c:if>
							   		</ul>
						   		</div>
							</div>
							<!--  /.Pagination -->
					        <form id='actionForm' action="${context}/board/list" method='get'>
					        	<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
					        	<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
					        	<input type="hidden" name="type" value="<c:out value='${pageMaker.cri.type}'/>">
					        	<input type="hidden" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>">
					        </form>
                            
                            <%--
                            <!-- Button trigger modal -->
							<button type="button" id="modalToggle" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#b_Modal">
							  Launch demo modal
							</button>
                            <!-- FOR TEST ONLY delete this button when publish -->
                            --%>
                            
                            <!-- Modal -->
				            <div class="modal fade" id="b_Modal" tabindex="-1" aria-labelledby="b_ModalLabel" aria-hidden="true">
            					<div class="modal-dialog">
					        		<div class="modal-content">
					                	<div class="modal-header">
						                    <h4 class="modal-title" id="b_ModalLabel">게시글 열람</h4>
						                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  						</div>
						                <div class="modal-body">
											<div class="b_Modal_Content">
												
											</div>
											<div class="b_Modal_Attach">
										</div>
						                <div class="modal-footer">
						                    <button type="button" class="btn btn-primary b_ModalModify" data-dismiss="modal">글 수정</button>
						                    <button type="button" class="btn btn-default b_ModalClose" data-dismiss="modal">닫기</button>
					               		</div>
				               		</div>
				               		<!-- /.modal-content -->
				            	</div>
				            	<!-- /.modal-dialog -->
				         	</div>
				         	<!-- /.modal -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
	</div>

<script type="text/javascript">
   	// 새로운 게시물 번호는 Board Controller의 addFlashAttribute()
   	// method로 저장되었기 때문에 한번도 사용된 적이 없다면 사용자가 
   	// "/board/list"를 호출하거나 새로고침을 통해 호출하는 경우 내용을 갖지 않게
   	// 됨. addFlashAttribute() method로는 일회성 data만 생성하므로
   	// 이를 이용해 경고창이나 modal등을 보여주는 방식으로 처리할 수 있음
   	
	var imgSelector = document.querySelectorAll("#thmbImg");
		
	$(document).ready(function() {
		
		for (var i = 0; i < imgSelector.length; i++) {
			var thmbImgSrc = imgSelector[i].getAttribute("src")
		
			var thmbSplit = thmbImgSrc.substring(thmbImgSrc.lastIndexOf('=') + 1);
		
			var thmbUploadPath = thmbSplit.split(('/'));
			
			console.log(thmbUploadPath[0]);
			console.log(thmbUploadPath[3]);
			
			var thmbEncodeU = thmbUploadPath[0] + '/' + thmbUploadPath[1] + '/' + thmbUploadPath[2];
			var thmbEncodeF = thmbUploadPath[3];
			
			console.log(imgSelector[i]);
			imgSelector[i].setAttribute('src', '/display?fileName=' + thmbEncodeU + "/sthmb_" + thmbEncodeF);
		};
		
		
		
		var ctx = getContextPath();
		
		function getContextPath() {
			return sessionStorage.getItem("contextpath");
		};
		// header.jsp 최하단 (385행) 참고. JS에서 contextpath 사용하는 법
		
		var modalTrigger = $("#modalToggle")
		
		var b_modal = $("#b_Modal") 
		
		
		var actionForm = $("#actionForm");
		$("a.page-link").on("click", function(e) {
			e.preventDefault();
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit(); // actionForm을 commit하여 기능 작동하도록 함
		});
		
		// Get content from Modal WIP
		
		function dateConvert(date){
			var cDate = new Date(date);
			
			var year = cDate.getFullYear();
			var month = cDate.getMonth() + 1;
			var day = cDate.getDate();
			
			var fDate = year + "/" + month + "/" + day;
			return fDate;
		}
		
		$('.move').on("click", function(e) {
			e.preventDefault();
			
			var b_number = $(this).attr("href");
			$.getJSON("/board/getModal", {b_number: b_number}, function(arr){
				console.log(arr);
				
				var str = "";
			    
				$(arr).each(function(i, entry){
					
					var cRDate = dateConvert(entry.b_regDate);
					var cUDate = dateConvert(entry.b_updateDate);
					
					
					str += "<p class='mNumber text-muted'>" + entry.b_number + "</p>";
					str += "<p class='mEmail text-end'>" + entry.u_email + "</p>";
					str += "<p class='mTitle text-center'>" + entry.b_title + "</p>";
					str += "<p class='mText'>" + entry.b_text + "</p>";
					if (entry.b_img != null) {
						str += "<div class='mImgWrapper'>";
						str += "<p class='mImg'><img id='modalImg' src='/display?fileName=" + entry.b_img + "'/></p>";
						str += "</div>";
					}
					if (entry.b_video != null) {
						str += "<p class='mVideo'>" + entry.b_video + "</p>";
					}
					str += "<p class='mRegDate text-muted text-end'>" + cRDate + "</p>";
					str += "<p class='mUpdateDate text-muted text-end'>" + cUDate + "</p>";
					
					
					str += "<form id='operForm' action='${context}/board/modify' method='get'>";
					var aNumber = entry.b_number;
					str += "<input type='hidden' id='b_number' name='b_number' value='" + aNumber + "'>";
					
					var pageNum = '${pageMaker.cri.pageNum}';
					str += "<input type='hidden' id='pageNum' name='pageNum' value='" + pageNum + "'>";
					var amount = '${pageMaker.cri.amount}';
					str += "<input type='hidden' id='amount' name='amount' value='" + amount + "'>";
					var type = '${pageMaker.cri.type}';
					str += "<input type='hidden' id='type' name='type' value='" + type + "'>";
					var keyword = '${pageMaker.cri.keyword}';
					str += "<input type='hidden' id='keyword' name='keyword' value='" + keyword + "'>";
					str += "</form>";
					
				});
				
				$(".b_Modal_Content").html(str);
				
			    $.getJSON("/board/getAttachList", {b_number: b_number}, function(array){
					console.log(arr);
					
					var str = "";
				    
					$(array).each(function(i, attach){
						var fileCallPath = encodeURIComponent(attach.b_uploadPath + "/sthmb_" + attach.b_uuid + "_" + attach.b_fileName);
						
						str += "<li data-path='" + attach.b_uploadPath + "' data-uuid='" + attach.b_uuid + "' data-filename='" + attach.b_fileName + "' style='list-style-type: none;' ><div>";
						// str += "<img src='/display?fileName=" + fileCallPath + "'>";
						str += "</div>";
						str += "</li>";
					});
					
					$(".b_Modal_Attach").html(str);
				    
				}); // getjson
			    
			}); // getjson
			b_modal.modal("show");
		});
		
		
		$('.b_ModalClose').on("click", function(e){
			e.preventDefault();
			b_modal.modal("hide");
		})
		
		$('.b_ModalModify').on("click", function(e){
			var operForm = $("#operForm");
			// gotta do something here
			e.preventDefault();
			operForm.attr("action", ctx + "/board/modify").submit();
		})
		
		
		var searchForm = $("#searchForm");
		$("#searchForm button").on("click", function(e) {
			if (!searchForm.find('option:selected').val()) {
				alert("검색 조건을 선택해주세요");
				return false;
			}
			if (!searchForm.find('input[name="keyword"]').val()) {
				alert("검색어를 입력하세요");
				return false;
			}
			
			searchForm.find("input[name='pageNum']").val("1");
			// 검색 수행 후 1 page로 이동
			e.preventDefault();
			
			searchForm.submit();
		});
		
	});
</script>
        

<%@include file="../includes/footer.jsp" %>