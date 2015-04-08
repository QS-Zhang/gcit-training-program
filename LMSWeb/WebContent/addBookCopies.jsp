<%@page import="com.gcit.training.library.domain.Book"%>
<%@page import="com.gcit.training.library.domain.LibraryBranch"%>
<%@page import="com.gcit.training.library.domain.Genre"%>
<%@page import="com.gcit.training.library.domain.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<% List<Book> bookList = new AdministratorService().getAllBooks(); %>
<% List<LibraryBranch> branchList = new AdministratorService().getAllBranches();%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Add BookCopies</h4>
</div>

<div class="modal-body">

	<form action="addBookCopies" name="addBookCopiesForm" method="post">
		
		Book: <select
			name="book">
			<%for(Book book: bookList){ %>
			<option value="<%=book.getBookId()%>"><%=book.getTitle()%></option>


			<%} %>
		</select><br /> 
		Branch: <select name="branch">
			<%for (LibraryBranch lb : branchList){ %>
			<option value="<%=lb.getBranchId()%>"><%=lb.getBranchName() %></option>
			<%} %>

		</select><br/>
		
		Number of Copies:<input type="text" name="noOfCopies" /><br /> 



	</form>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary"
		onclick="javascript:submitBookCopies();">Save changes</button>
</div>

<script>

    function submitBookCopies(){
        document.addBookCopiesForm.submit();
    }
</script>