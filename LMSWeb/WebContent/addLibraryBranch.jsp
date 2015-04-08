<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Add LibraryBranch</h4>
</div>

<div class="modal-body">

	<form action="addLibraryBranch" name="addLibraryBranchForm" method="post">
		Branch Name:<input type="text" name="branchName" /><br/>
		Branch Address:<input type="text" name="branchAddress" /><br/>
		
	</form>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary"
		onclick="javascript:submitLibraryBranch();">Save changes</button>
</div>

<script>

    function submitLibraryBranch(){
        document.addLibraryBranchForm.submit();
    }
</script>