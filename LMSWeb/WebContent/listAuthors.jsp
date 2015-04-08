<%@page import="com.gcit.training.library.domain.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<%
	int pageCount = new AdministratorService().countAuthors();
	List<Author> authors = new AdministratorService().pageAuthors(1);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Authors</h4>
</div>
<nav>
  <ul class="pagination">
  	<% for(int i=1; i<=pageCount/5; i++) { %>
    	<li><a href="javascript:pageAuthor(<%=i%>);"><%=i%></a></li>
    <% } %>
  </ul>
</nav>
<div class="modal-body">
<!-- Default panel contents -->
    	<input type="text" placeholder="Search Authors by Name"
    		id="authorToSearch" 
    			class="col-lg-6 col-md-12 col-sm-12 col-xs-12" 
    				onkeyup="javascript:searchAuthors();"/>
	<div id="authorList">
	  	<table class="table">
			<tr>
				<th>Id</th>	
				<th>Name</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<% for(Author a : authors) { %>
			<tr>
				<td><%=a.getAuthorId() %></td>
				<td><%=a.getAuthorName() %></td>
				<td>
					<a href="editAuthor.jsp?authorIdToEdit=<%=a.getAuthorId()%>" 
						data-target="#myModal1" data-toggle="modal">
							<i class="fa fa-remove"></i>
						 Edit
					</a>
				</td>
				<td><button type="button" class="btn btn-danger btn-md" 
					onclick="javascript:deleteAuthor('<%=a.getAuthorId()%>');">
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

<form name="deleteAuthorFrm" action="deleteAuthor" method="post">
	<input type="hidden" name="authorIdToDelete" />
</form>
<script>
	function deleteAuthor(id) {
		$.post("deleteAuthor", {authorIdToDelete: id}, 
			function(result){
				$.post("pageAuthor", {pageNo: $("#currentPageNo").val()}, 
						function(result){
				        	$("#authorList").html(result);
				    });
	    	});
	}
	
	function searchAuthors() {
		$.post("searchAuthors", {authorToSearch: $("#authorToSearch").val()}, 
			function(result){
			
			        	$("#authorList").html(result);
			
	    });
	}

	function pageAuthor(pageNo) {
		if($("#noOfAuthorsInPage").val() == 0) {
			$("#currentPageNo").val(pageNo-1);
		} else {
			$("#currentPageNo").val(pageNo);
		}
		$.post("pageAuthor", {pageNo: $("#currentPageNo").val()}, 
			function(result){
	        	$("#authorList").html(result);
	    });
	}
</script>

