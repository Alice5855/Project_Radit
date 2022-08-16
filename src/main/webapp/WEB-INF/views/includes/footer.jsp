<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var ="context"><%=request.getContextPath()%></c:set>

</div>
        <!-- /#page-wrapper -->
		</div>
		    <!-- /#wrapper -->
		
		    <!-- jQuery -->
		    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
		
		    <!-- Bootstrap Core JavaScript -->
		    <script src="${context}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
		
		    <!-- Metis Menu Plugin JavaScript -->
		    <script src="${context}/resources/vendor/metisMenu/metisMenu.min.js"></script>
		
		    <!-- DataTables JavaScript -->
		    <script src="${context}/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
		    <script src="${context}/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
		    <script src="${context}/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>
		
		    <!-- Custom Theme JavaScript -->
		    <script src="${context}/resources/dist/js/sb-admin-2.js"></script>
		
		    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
		    <script>
		    $(document).ready(function() {
		        $('#dataTables-example').DataTable({
		            responsive: true
		        });
		    	// Mobile page menu fix after import higher version of jquery
			    $(".slidebar-nav")
		    	.attr("class", "slidebar-nav navbar-collapse collapse")
				.attr("aria-expanded", "false")
		    	.attr("style", "height: 1px");
		    });
		    </script>

</body>

</html>