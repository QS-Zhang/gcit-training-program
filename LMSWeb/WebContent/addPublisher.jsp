<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">&times;</button>
	<h4 class="modal-title">Add Publisher</h4>
</div>

<div class="modal-body">

	<form action="addPublisher" name="addPublisherForm" method="post">
		Publisher Name:<input type="text" name="publisherName" /><br/>
		Publisher Address:<input type="text" name="publisherAddress" /><br/>
		Publisher Phone:<input type="text" name="publisherPhone" /><br/>
		
	</form>
</div>

<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	<button type="button" class="btn btn-primary"
		onclick="javascript:submitPublisher();">Save changes</button>
</div>

<script>

    function submitPublisher(){
        document.addPublisherForm.submit();
    }
</script>