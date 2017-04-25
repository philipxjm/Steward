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
            <label for="ticker">Ticker</label>
            <input class="form-control" type="text" id="ticker">
          </div>

          <!-- Shares -->
          <div class="row">
            <label for="shares">Shares</label>
            <input type="number" id="shares">
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button id="addStock" type="button" class="btn btn-primary">Add</button>
      </div>
    </div>
  </div>
</div>   