<div class="modal fade" id="stockPageModal" tabindex="-1" role="dialog" aria-labelledby="stockPageModal" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Buy/Sell Stock</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <form id="stockForm">
            <!-- Buy/Sell Toggle -->
            <div class="btn-group" data-toggle="buttons">
              <label id="buy" class="btn btn-primary active">
                <input type="radio" name="action" autocomplete="off" checked>Buy
              </label>
              <label id="sell" class="btn btn-primary">
                <input type="radio" name="action" autocomplete="off">Sell
              </label>
            </div>
            <br/>

            <!-- Ticker -->
            <div class="row">
              Ticker: ${ticker}
            </div>

            <!-- Portfolio -->
            <select class="custom-select">
              <option>Buy for</option>
              <optgroup label="Portfolios">
                <option>Port 1</option>
                <option>Port 2</option>
                <option>Port 3</option>
              </optgroup>
              <optgroup label="Pools">
                <option>Pool 1</option>
                <option>Pool 2</option>
                <option>Pool 3</option>
              </optgroup>
            </select>



            <!-- TODO Re-enable for non-pools?
            <label id="pastActionLabel" class="custom-control custom-checkbox">
              <input id="pastAction" type="checkbox" class="custom-control-input">
              <span class="custom-control-indicator"></span>
              <span class="custom-control-description">Past action</span>
            </label>            

            <div id="time" class="form-control row">
              <input id="actionDate" class="form-control" type="date">
            </div>-->

            <!-- Shares -->
            <div class="row">
              <!-- TODO move validation into JS? -->
              <label for="shares">Shares <input class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="shares"  autocomplete="off"></label>
            </div>

          </form>
          <p id="stockError" class="text-danger"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <!-- TODO: Actually make this buy -->
        <button id="addStock" type="button" class="btn btn-primary">Add</button>
      </div>
    </div>
  </div>
</div>   