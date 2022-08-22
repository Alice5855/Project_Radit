<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>

<%@include file="../includes/header.jsp" %>
<style>
	#title {
		font-size: 2rem;
	}
	#date {
		font-size: 0.5rem;
	}
</style>
<div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1>/r/Radit</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <button id="regBtn" class="btn btn-secondary btn-sm float-end mb-3 me-1" type="button" onclick="location.href='${context}/board/register'">Register New Entry</button>
                </div>
                <!-- /.panel-heading -->
                        <c:forEach items="${list}" var="board">
                        <div class="container p-3" id="custom-cards">
                        	<div class="row align-items-stretch g-4">
                        		<div class="card h-100 overflow-hidden rounded-4 shadow-lg">
                                <!-- BoardController.java의 37행 참고. model에 추가한 'list' attribute를 불러 온 것 -->
                                	<div class="card-content position-relative">
                                		<p style="display : none;"><c:out value="${board.b_number}" /></p>
                                		<p id="title" class="fw-bold mt-3 mb-2 ms-5">
                                			<!-- Added .move -->
                                			<a class="move" href='<c:out value="${board.b_number}" />' style="text-decoration: none;">
                                				<c:out value="${board.b_title}" />
                                			</a>
                                		</p>
                                		
                                		<c:if test= '${not empty board.b_img}'>
                                		
                                			<img id="thmbImg" src='/display?fileName=<c:out value="${board.b_img}" />'/>
                                		
                                		</c:if>
                                		
                               			<p class="text-end"><c:out value="${board.u_email}" /></p>
                               			<p class="ms-2"><c:out value="${board.b_text}" /></p>
                                		<p id="date" class="text-end text-muted"><fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_regDate}"/></p>
                                		<p id="date" class="text-end text-muted"><fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_updateDate}"/></p>
                                	</div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                            <!-- /.table-responsive -->
                            
                            <div class="row">
                            	<div class="col-lg-12">
                            		<form id="searchForm" action="${context}/board/list" method="get">
                            			<select name="type">
                            				<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>> </option>
                            				<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : ''}"/>>Title</option>
                            				<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : ''}"/>>Context</option>
                            				<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : ''}"/>>Author</option>
                            				<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : ''}"/>>Title, Context</option>
                            				<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : ''}"/>>Title, Author</option>
                            				<option value="CW" <c:out value="${pageMaker.cri.type eq 'CW' ? 'selected' : ''}"/>>Context, Author</option>
                            				<option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC' ? 'selected' : ''}"/>>Title, Context, Author</option>
                            			</select>
                            			<input type="text" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>" />
                            			<input type="hidden" name="pageNum" value="<c:out value='${pageMaker.cri.pageNum}'/>" />
                            			<input type="hidden" name="amount" value="<c:out value='${pageMaker.cri.amount}'/>" />
                            			<button class="btn btn-primary">Search</button>
                            		</form>
                            	</div>
                            </div>
                            <!-- pageNum가 parameter로 유지되어야 함.
                            	검색 버튼 클릭 시 1페이지로 이동
                            	한글data를 get method로 넘길 때 문제가 발생할 수 있음 -->
                            
                            <!-- Pagination -->
                            <div class='text-center'>
                            	<div class="col-lg-12">
									<ul class="pagination">
										<c:if test="${pageMaker.prev}">
											<li class="paginate-item previous"><a class="page-link" href="${pageMaker.startPage - 1}">Previous</a></li>
										</c:if>
										
										<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
											<li class="paginate-item ${pageMaker.cri.pageNum == num ? 'active' : ''} ">
											<%-- MAKE THE CODE ACTUALLY READABLE N SHIT THIS IS IMPORTANT N SHIT --%>
											<a class="page-link" href="${num}">${num}</a>
											<%-- <a href="${context}/board/list?pageNum=${num}"> this works no problem but okay --%>
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
                            
                            
                            <!-- Button trigger modal -->
							<button type="button" id="modalToggle" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#b_Modal">
							  Launch demo modal
							</button>
                            <!-- FOR TEST ONLY delete this button when publish -->
                            
                            
                            <!-- Modal -->
				            <div class="modal fade" id="b_Modal" tabindex="-1" aria-labelledby="b_ModalLabel" aria-hidden="true">
            					<div class="modal-dialog">
					        		<div class="modal-content">
					                	<div class="modal-header">
						                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						                    <h4 class="modal-title" id="b_ModalLabel">Modal title</h4>
                  						</div>
						                <div class="modal-body">
						                <%-- 
											<%@include file="get.jsp" %>
										--%>
										<div class="b_Modal_Content">
											
										</div>
						                <div class="modal-footer">
						                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						                    <button type="button" class="btn btn-primary" data-dismiss="modal">Save changes</button>
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
		
		$('.move').on("click", function(e) {
			e.preventDefault();
			$.getJSON("/board/getModal", {b_number: $(this).attr("href")}, function(arr){
				console.log(arr);
				
				var str = "";
			    
				$(arr).each(function(i, entry){
						
						str += "<p> b_number : " + entry.b_number + "</p>";
						str += "<p> u_email : " + entry.u_email + "</p>";
						str += "<p> b_title : " + entry.b_title + "</p>";
						str += "<p> b_text : " + entry.b_text + "</p>";
						str += "<p> b_img : <img id='modalImg' src='/display?fileName=" + entry.b_img + "'/></p>";
						str += "<p> b_video : " + entry.b_video + "</p>";
						str += "<p> b_regDate : " + entry.b_regDate + "</p>";
						str += "<p> b_updateDate : " + entry.b_updateDate + "</p>";
				});
				
				$(".b_Modal_Content").html(str);
			    
			}); // getjson
			b_modal.modal("show");
			
			// Get content from Modal WIP
			
			/*
			actionForm.append("<input type='hidden' name='b_number' value='" + $(this).attr("href") + "'>");
			actionForm.attr("action", ctx + "/board/getModal");
			actionForm.submit();
			*/
		});
		
		
		var searchForm = $("#searchForm");
		$("#searchForm button").on("click", function(e) {
			if (!searchForm.find('option:selected').val()) {
				alert("Invalid search option");
				return false;
			}
			if (!searchForm.find('input[name="keyword"]').val()) {
				alert("Invalid keyword");
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