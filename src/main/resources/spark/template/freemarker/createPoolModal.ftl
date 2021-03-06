<div class="modal fade" id="createPoolModal" tabindex="-1" role="dialog" aria-labelledby="createPoolModal" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Create New Pool</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <form class="form-horizontal" id="stockForm">
            <!-- Name -->
            <div class="form-group row">
              <label for="name">Pool Name <input class="form-control" type="text" id="name"  autocomplete="off"></label>
            </div>

            <!-- AI -->
            <div class="form-group row">
              <div class="checkbox">
                <input id="ai" type="checkbox"> Include AI in game?
              </div>
            </div>

            <!-- End Date -->
            <div class="form-group row">
              <label for="end">End Date <input class="form-control" type="date" id="end"  autocomplete="off"></label>
            </div>

            <!-- Start Balance -->
            <div class="form-group row">
              <label for="balance">Start Balance <input class="form-control" value=10000 onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="balance"  autocomplete="off"></label>
            </div>

            <!-- TODO: Add date -->
          </form>
          <p id="poolError" class="text-danger"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button id="createPool" type="button" class="modal-submit btn btn-primary">Create</button>
      </div>
    </div>
  </div>
</div>   