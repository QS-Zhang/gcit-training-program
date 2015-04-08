<%@page import="com.gcit.training.library.domain.Genre"%>
<%@page import="com.gcit.training.library.domain.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<% List<Author> authorList = new AdministratorService().getAllAuthors(); %>
<% List<Genre> genreList = new AdministratorService().getAllGenres();%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Add Book</h4>
</div>

<div class="modal-body">

	<form action="addBook" name="addBookForm" method="post">
		Book Title:<input type="text" name="title" /><br /> 
		Author: <select
			name="author">
			<%for(Author au: authorList){ %>
			<option value="<%=au.getAuthorId()%>"><%=au.getAuthorName()%></option>


			<%} %>
		</select><br /> 
		Genre: <select name="genre">
			<%for (Genre ge : genreList){ %>
			<option value="<%=ge.getGenreId()%>"><%=ge.getGenreName() %></option>
			<%} %>

		</select><br/>



	</form>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary"
		onclick="javascript:submitBook();">Save changes</button>
</div>

<script>

    function submitBook(){
        document.addBookForm.submit();
    }
</script>