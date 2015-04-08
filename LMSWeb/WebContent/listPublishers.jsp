<%@page import="com.gcit.training.library.domain.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<%
	int pageCount = new AdministratorService().countAuthors();
	List<Publisher> publishers = new AdministratorService().pagePublisher(1);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Publishers</h4>
</div>
<nav>
  <ul class="pagination">
  	<% for(int i=1; i<=pageCount/5; i++) { %>
    	<li><a href="javascript:pagePublisher(<%=i%>);"><%=i%></a></li>
    <% } %>
  </ul>
</nav>
<div class="modal-body">
<!-- Default panel contents -->
    	<input type="text" placeholder="Search Publishers by Name"
    		id="publisherToSearch" 
    			class="col-lg-6 col-md-12 col-sm-12 col-xs-12" 
    				onkeyup="javascript:searchPublishers();"/>
	<div id="publisherList">
	  	<table class="table">
			<tr>
				<th>Id</th>	
				<th>Name</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<% for(Publisher a : publishers) { %>
			<tr>
				<td><%=a.getPublisherId() %></td>
				<td><%=a.getPublisherName() %></td>
				<td>
					<a href="editPublisher.jsp?publisherIdToEdit=<%=a.getPublisherId()%>" class="btn btn-info btn-md" data-target="#myModal1" data-toggle="modal">
						<span class="glyphicon glyphicon-edit"></span> Edit
					</a>
				</td>
				<td><button type="button" class="btn btn-danger btn-md" 
					onclick="javascript:deletePublisher('<%=a.getPublisherId()%>');">
					<span class="glyphicon glyphicon-remove"></span> Remove </button></td>
			</tr>
			<% } %>
		</table>
	</div>
</div>
	<div id="myModal1" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
<input type="hidden" id="currentPageNo" value="1" />

<form name="deletePublisherFrm" action="deletePublisher" method="post">
	<input type="hidden" name="publisherIdToDelete" />
</form>
<script>
	function deletePublisher(id) {
		$.post("deletePublisher", {publisherIdToDelete: id}, 
			function(result){
				$.post("pagePublisher", {pageNo: $("#currentPageNo").val()}, 
						function(result){
				        	$("#publisherList").html(result);
				    });
	    	});
	}
	
	function searchPublishers() {
		$.post("searchPublishers", {publisherToSearch: $("#publisherToSearch").val()}, 
			function(result){
	        	$("#publisherList").html(result);
	    });
	}

	function pagePublisher(pageNo) {
		if($("#noOfPublishersInPage").val() == 0) {
			$("#currentPageNo").val(pageNo-1);
		} else {
			$("#currentPageNo").val(pageNo);
		}
		$.post("pagePublisher", {pageNo: $("#currentPageNo").val()}, 
			function(result){
	        	$("#publisherList").html(result);
	    });
	}
</script>

