<div class="modal fade" id="addStockModal" tabindex="-1" role="dialog" aria-labelledby="addStockModal" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Buy/Sell Stock</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <form>
            <!-- Buy/Sell Toggle -->
            <div class="btn-group" data-toggle="buttons">
              <label id="buy" class="btn btn-primary active">
                <input type="radio" name="action" autocomplete="off" checked>Buy
              </label>
              <label id="sell" class="btn btn-primary">
                <input type="radio" name="action" autocomplete="off">Sell
              </label>
            </div>

            <!-- Ticker -->
            <div class="row">
              <label for="ticker">Ticker <input class="form-control" type="text" id="ticker"></label>
            </div>

            <!-- Shares -->
            <div class="row">
              <!-- TODO move validation into JS? -->
              <label for="shares">Shares <input class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="shares"></label>
            </div>
          </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button id="addStock" type="button" class="btn btn-primary">Add</button>
      </div>
    </div>
  </div>
</div>   