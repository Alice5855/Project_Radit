<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>

<%@include file="../includes/header.jsp" %>

            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                    	<!-- Added New entry button -->
                        <div class="panel-heading">
                            Board List Page
                            <button id="regBtn" class="btn btn-default btn-xs pull-right" type="button" onclick="location.href='${context}/board/register'">Register New Entry</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>#번호</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>작성일</th>
                                        <th>수정일</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${list}" var="board">
                                <!-- BoardController.java의 37행 참고. model에 추가한 'list' attribute를 불러 온 것 -->
                                	<tr>
                                		<td><c:out value="${board.b_number}" /></td>
                                		<td>
                                			<!-- Added .move -->
                                			<a class="move" href='<c:out value="${board.b_number}" />'>
                                				<c:out value="${board.b_title}" />
                                			</a>
                                		</td>
                               			<td><c:out value="${board.u_email}" /></td>
                                		<td><fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_regDate}"/></td>
                                		<td><fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_updateDate}"/></td>
                                	</tr>
                                </c:forEach>
                            </table>
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
                            <div class='pull-right'>
                            	<div class="col-lg-12">
									<ul class="pagination">
										<c:if test="${pageMaker.prev}">
											<li class="paginate_button previous"><a href="${pageMaker.startPage - 1}">Previous</a></li>
										</c:if>
										
										<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
											<li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active' : ''} ">
											<%-- MAKE THE CODE ACTUALLY READABLE N SHIT THIS IS IMPORTANT N SHIT --%>
											<a href="${num}">${num}</a>
											<%-- <a href="${context}/board/list?pageNum=${num}"> this works no problem but okay --%>
										</li>
										</c:forEach>
										
										<c:if test="${pageMaker.next}">
											<li class="paginate_button next"><a href="${pageMaker.endPage + 1}">Next</a></li>
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
							<button type="button" id="modalToggle" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
							  Launch demo modal
							</button>
                            <!-- FOR TEST ONLY delete this button when publish -->
                            
                            
                            <!-- Modal -->
				            <div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
            					<div class="modal-dialog">
					        		<div class="modal-content">
					                	<div class="modal-header">
						                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                  						</div>
						                <div class="modal-body">
						                <%-- 
											<%@include file="get.jsp" %>
										--%>
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

<script type="text/javascript">
   	// 새로운 게시물 번호는 Board Controller의 addFlashAttribute()
   	// method로 저장되었기 때문에 한번도 사용된 적이 없다면 사용자가 
   	// "/board/list"를 호출하거나 새로고침을 통해 호출하는 경우 내용을 갖지 않게
   	// 됨. addFlashAttribute() method로는 일회성 data만 생성하므로
   	// 이를 이용해 경고창이나 modal등을 보여주는 방식으로 처리할 수 있음
	$(document).ready(function() {
		var ctx = getContextPath();
		
		function getContextPath() {
			return sessionStorage.getItem("contextpath");
		};
		// header.jsp 최하단 (385행) 참고. JS에서 contextpath 사용하는 법
		/*
		var result = "<c:out value='${result}' />";
		// BoardController의  addAttribute() method로 추가된 result
		
		checkModal(result);
		
		history.replaceState({}, null, null);
		
		function checkModal(result) {
			if (result === '' || history.state) {
				return;
			}
			if (parseInt(result) > 0) {
				$(".modal-body").html("게시글 " + parseInt(result) + "번이 등록 되었습니다")
			}
			
			$("#myModal").modal("show");
		}
		// #myModal에 'show'가 호출되면 modal창을 표시. 게시글을 작성하면
		// list로 redirection 될 때 modal 표시를 해줌
		
		// #regBtn에 등록 기능 추가
//		$('#regBtn').on("click", function(){
//			self.location = ctx + "/board/register";
//		})
		// ${context} 사용하기 위해 inline으로 처리함. 21행 참고
		*/
		
		var modalTrigger = $("#modalToggle")
		
		modalTrigger.on("click", function(e){
			
		})
		
		var actionForm = $("#actionForm");
		$(".paginate_button a").on("click", function(e) {
			e.preventDefault();
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit(); // actionForm을 commit하여 기능 작동하도록 함
		});
		
		$('.move').on("click", function(e) {
			e.preventDefault();
			actionForm.append("<input type='hidden' name='b_number' value='" + $(this).attr("href") + "'>");
			actionForm.attr("action", ctx + "/board/get");
			actionForm.submit();
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