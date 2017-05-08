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


            <!-- Add time input? -->
            <label id="pastActionLabel" class="custom-control custom-checkbox">
              <input id="pastAction" type="checkbox" class="custom-control-input">
              <span class="custom-control-indicator"></span>
              <span class="custom-control-description">Past action</span>
            </label>            

            <div id="time" class="form-control row">
              <input id="actionDate" class="form-control" type="date">
            </div>

            <!-- Ticker -->
            <div id="stockModalTickerDiv" class="row">
              <!--<label for="ticker">--><div class='fullWidth'>Ticker</div> <input class="autoInp form-control" type="text" id="ticker"  autocomplete="off"><!--</label>-->
            </div>

            <!-- Shares -->
            <div class="row">
              <!-- TODO move validation into JS? -->
              <label for="shares">Shares <input class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="shares"  autocomplete="off"></label>
            </div>
          </form>
          <!-- Total Table -->
          <div>Price: <span id="priceTotal"></span></div>
          <div>Total Price: <span id="totalCost"></span></div>
          <div id="modalCB">Current Balance: <span id="currBalanceTotal"></span></div>
          <div id="modalB">Balance After: <span id="afterTotal"></span></div>
          <!--
          <div id="total">
              <div class="row">
                  <div class="zero col"></div>
                  <div id="priceTotal" class="col text-right pull-right"></div>
              </div>
              <div class="row">
                  <div class="col text-left">x</div>
                  <div id="sharesTotal" class="col text-right pull-right"></div>
              </div>
              <div class="bottom row">
                  <div class="zero col"></div>
                  <div id="totalCost" class="col text-right pull-right"></div>
              </div>
          </div>-->
          <p id="stockError" class="text-danger"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button id="addStock" type="button" class="modal-submit btn btn-primary">Add</button>
      </div>
    </div>
  </div>
</div>   