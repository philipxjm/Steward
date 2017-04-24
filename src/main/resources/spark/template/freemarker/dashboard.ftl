<#assign chart=true>
<#assign js="/js/dashboard.js">
<#assign css="/css/dashboard.css">
<#assign content>
<div class="container">
<div class="row" id="main">
        <div class="col-3">
          <div class="header">
            Portfolios <button type="button" id="addPort" class="btn float-right btn-secondary btn-sm">+</button>
          </div>
        	<div id="ports" class="list-group expand">
            <#list portfolios as port>
	  		   	 <div class="list-group-item list-group-item-action port <#if port_index == 0>active</#if>">${port.name}</div>
            </#list>
  			  </div>
        </div>
        <div class="col-6">
			   <canvas id="gains"></canvas>
		    </div>
        <div class="col-3">
          <div class="header">
            Stocks <button type="button" data-toggle="modal" data-target="#addStockModal" class="btn float-right btn-secondary btn-sm">+</button>
          </div>
        	<div id="stocks" class="list-group expand">
            <#list stocks as stock>
	  			    <a href="/stock/${stock.ticker}" class="list-group-item list-group-item-action">${stock.ticker} ${stock.shares}</a>
            </#list>
  			  </div>
        </div>
  
        <!-- Stock Modal -->  
        <div class="modal fade" id="addStockModal" tabindex="-1" role="dialog" aria-labelledby="addStockModal" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Add Stock</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                TODO: Add form here
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Add</button>
              </div>
            </div>
          </div>
        </div>              
</div>
</div>
</#assign>
<#include "main.ftl">