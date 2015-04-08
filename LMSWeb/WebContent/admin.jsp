
<%
	String result = (String) request.getAttribute("result");
%>

<%@ include file="template.jsp"%>
<body>
	<section style="margin-left: 10px;">
		<%
			if (result != null) {
		%>
		<h4>
			<%=result%>
		</h4>
		<%
			}
		%>
		<h2>Authors</h2>
		<a href="addAuthor.html" data-target="#myModal" data-toggle="modal">Add
			Author</a><br /> <a href="listAuthors.jsp" data-target="#myModal"
			data-toggle="modal">List All Authors</a><br />
		<h2>Books</h2>
		<a href="addBook.jsp" data-target="#myModal" data-toggle="modal">Add
			Book</a><br /> <a href="listBooks.jsp" data-target="#myModal"
			data-toggle="modal">List All Books</a><br />
		<h2>Publishers</h2>
		<a href="addPublisher.jsp" data-target="#myModal" data-toggle="modal">Add
			Publisher</a><br /> <a href="listPublishers.jsp" data-target="#myModal"
			data-toggle="modal">List All Publishers</a><br />
		<h2>LibraryBranch</h2>
		<a href="addLibraryBranch.jsp" data-target="#myModal"
			data-toggle="modal">Add LibraryBranch</a><br /> <a
			href="listLibraryBranches.jsp" data-target="#myModal"
			data-toggle="modal">List All Library Branches</a><br />
		<h2>Borrowers</h2>
		<a href="addBorrower.jsp" data-target="#myModal" data-toggle="modal">Add
			Borrower</a><br /> <a href="listPBorrowers.jsp" data-target="#myModal"
			data-toggle="modal">List All Borrowers</a><br />
		<h2>Book Copy</h2>
		<a href="addBookCopies.jsp" data-target="#myModal" data-toggle="modal">Add
			Book Copies</a><br />
	</section>




	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./scripts/jquery.min.js"></script>
	<script src="./scripts/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="./scripts/ie10-viewport-bug-workaround.js"></script>

	<script type="text/javascript">
		$('body').on('hidden.bs.modal', '.modal', function() {
			$(this).removeData('bs.modal');
		});
	</script>


	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>

</body>