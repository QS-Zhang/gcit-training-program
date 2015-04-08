<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Add Borrower</h4>
</div>

<div class="modal-body">

	<form action="addBorrower" name="addBorrowerForm" method="post">
		Borrower Name:<input type="text" name="name" /><br/>
		Borrower Address:<input type="text" name="address" /><br/>
		Borrower Phone:<input type="text" name="phone" /><br/>
		
	</form>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary"
		onclick="javascript:submitBorrower();">Save changes</button>
</div>

<script>

    function submitBorrower(){
        document.addBorrowerForm.submit();
    }
</script>