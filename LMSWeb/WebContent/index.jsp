<%@ include file = "template.jsp"%>

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h1>Hello, world!</h1>
        <p>This is Library Management System website.</p>
        <p><a class="btn btn-primary btn-lg" href="javascript:aboutLMS()" role="button">Learn more</a></p>
      </div>
    </div>

    <div class="container">
      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-4">
          <h2>Administrator</h2>
          <p>Create/Update/Delete Resources </p>
          <p><a class="btn btn-default" href="admin.jsp" role="button">Proceed</a></p>
        </div>
        <div class="col-md-4">
          <h2>Librarian</h2>
          <p>Library Branch Management</p>
          <p><a class="btn btn-default" href="librarian.jsp" role="button">Proceed</a></p>
       </div>
        <div class="col-md-4">
          <h2>Borrower</h2>
          <p>Check Out/ Return Books</p>
          <p><a class="btn btn-default" href="borrower.jsp" role="button">Proceed</a></p>
        </div>
      </div>

      <hr>

      <footer>
        <p> Company 2014</p>
      </footer>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./scripts/jquery.min.js"></script>
    <script src="./scripts/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="./scripts/ie10-viewport-bug-workaround.js"></script>
  

</body></html>