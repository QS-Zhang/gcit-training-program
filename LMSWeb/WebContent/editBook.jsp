<%@page import="com.gcit.training.library.domain.Genre"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.training.library.domain.Book"%>
<%@page import="com.gcit.training.library.domain.Author"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<%
	String bookIdToEdit = request.getParameter("bookIdToEdit");
	Book book = new AdministratorService().getOneBook(Integer.parseInt(bookIdToEdit));
	
%>
<% List<Author> authorList = new AdministratorService().getAllAuthors(); %>
<% List<Genre> genreList = new AdministratorService().getAllGenres();%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Edit Book</h4>
</div>
<div class="modal-body">
	<form action="editBook" name="editBookFrm" method="post">
		Book Title: <input type="text" name="titleToEdit" value="<%=book.getTitle()%>"/>
		<input type="hidden" name="bookIdToEdit" value="<%=bookIdToEdit %>" />
		
		Author: <select
			name="authorToEdit">
			<%for(Author au: authorList){ %>
			<option value="<%=au.getAuthorId()%>"><%=au.getAuthorName()%></option>


			<%} %>
		</select><br /> 
		Genre: <select name="genreToEdit">
			<%for (Genre ge : genreList){ %>
			<option value="<%=ge.getGenreId()%>"><%=ge.getGenreName() %></option>
			<%} %>
		</select><br/>
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary" onclick="javascript:submitBook();">Save changes</button>
</div>

<script>
	function submitBook() {
		document.editBookFrm.submit();
	}

</script>