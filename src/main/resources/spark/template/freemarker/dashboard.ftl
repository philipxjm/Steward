<#assign chart=true>
<#assign js="/js/dashboard.js">
<#assign css="/css/dashboard.css">
<#assign content>
<div class="container">
<div class="row" id="main">
        <div class="col-3">
          <div class="header">
            Portfolios <button type="button" class="btn float-right btn-secondary btn-sm">+</button>
          </div>
        	<div class="list-group expand">
	  		   	<div class="list-group-item list-group-item-action active">Portfolio 1</div>
	  		   	<div class="list-group-item list-group-item-action">Portfolio 2</div>
	  		   	<div class="list-group-item list-group-item-action">Portfolio 3</div>
  			  </div>
        </div>
        <div class="col-6">
			   <canvas id="gains"></canvas>
		    </div>
        <div class="col-3">
          <div class="header">
            Stocks <button type="button" class="btn float-right btn-secondary btn-sm">+</button>
          </div>
        	<div class="list-group expand">
	  			  <div class="list-group-item list-group-item-action">Stocks 1</div>
	  			  <div class="list-group-item list-group-item-action">Stocks 2</div>
	  			  <div class="list-group-item list-group-item-action">Stocks 3</div>
  			  </div>
        </div>
</div>
</div>
</#assign>
<#include "main.ftl">