<%@page import="com.gcit.training.library.domain.Book"%>
<%@page import="com.gcit.training.library.domain.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<%
	int pageCount = new AdministratorService().countBooks();
	List<Book> books = new AdministratorService().pageBooks(1);
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Books</h4>
</div>
<nav>
  <ul class="pagination">
  	<% for(int i=0; i<=pageCount/5; i++) { %>
    	<li><a href="javascript:pageBook(<%=i+1%>);"><%=i+1%></a></li>
    <% } %>
  </ul>
</nav>
<div class="modal-body">
<!-- Default panel contents -->
    	<input type="text" placeholder="Search Books by Title"
    		id="bookToSearch" 
    			class="col-lg-6 col-md-12 col-sm-12 col-xs-12" 
    				onkeyup="javascript:searchBooks();"/>
	<div id="bookList">
	  	<table class="table">
			<tr>
				<th>Id</th>	
				<th>Title</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<% for(Book a : books) { %>
			<tr>
				<td><%=a.getBookId() %></td>
				<td><%=a.getTitle() %></td>
				<td>
					<a href="editBook.jsp?bookIdToEdit=<%=a.getBookId()%>" class="btn btn-info btn-md" data-target="#myModal1" data-toggle="modal">
						<span class="glyphicon glyphicon-edit"></span> Edit
					</a>
				</td>
				<td><button type="button" class="btn btn-danger btn-md" 
					onclick="javascript:deleteBook('<%=a.getBookId()%>');">
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

<form name="deleteBookFrm" action="deleteBook" method="post">
	<input type="hidden" name="bookIdToDelete" />
</form>
<script>
	function deleteBook(id) {
		$.post("deleteBook", {bookIdToDelete: id}, 
			function(result){
				$.post("pageBook", {pageNo: $("#currentPageNo").val()}, 
						function(result){
				        	$("#bookList").html(result);
				    });
	    	});
	}
	
	function searchBooks() {
		$.post("searchBooks", {bookToSearch: $("#bookToSearch").val()}, 
			function(result){
	        	$("#bookList").html(result);
	    });
	}

	function pageBook(pageNo) {
		if($("#noOfBooksInPage").val() == 0) {
			$("#currentPageNo").val(pageNo-1);
		} else {
			$("#currentPageNo").val(pageNo);
		}
		$.post("pageBook", {pageNo: $("#currentPageNo").val()}, 
			function(result){
	        	$("#bookList").html(result);
	    });
	}
</script>

