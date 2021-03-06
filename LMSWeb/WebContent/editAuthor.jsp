<%@page import="com.gcit.training.library.domain.Author"%>
<%@page import="com.gcit.training.library.service.AdministratorService"%>
<%
	String authorIdToEdit = request.getParameter("authorIdToEdit");
	Author author = new AdministratorService().getOneAuthor(Integer.parseInt(authorIdToEdit));
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Edit Author</h4>
</div>
<div class="modal-body">
	<form action="editAuthor" name="editAuthorFrm" method="post">
		Author Name: <input type="text" name="authorNameToEdit" value="<%=author.getAuthorName()%>"/>
		<input type="hidden" name="authorIdToEdit" value="<%=authorIdToEdit %>" />
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary" onclick="javascript:submitAuthor();">Save changes</button>
</div>

<script>
	function submitAuthor() {
		document.editAuthorFrm.submit();
	}

</script>
