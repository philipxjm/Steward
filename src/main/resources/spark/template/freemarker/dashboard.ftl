<#assign chart=true>
<#assign name="index">
<#assign js>
  <script src="/js/UnrealizedGraph.js"></script>
  <script src="/js/dashboard.js"></script>
  <script src="/js/stockModal.js"></script>
</#assign>
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
		   	 <div class="list-group-item list-group-item-action port <#if port_index == 0>active</#if>">         
              <span class="portName">${port.name}</span>
              <a class="editPort float-right"><i class="fa fa-pencil" aria-hidden="true"></i></a>
              <a class="deletePort float-right"><i class="fa fa-trash" aria-hidden="true"></i></a>
          </div>
        </#list>
		  </div>
    </div>
    <div class="col-6">
      <#if portfolios?size == 0><h2 id="noPort" class="text-muted">Make a new portfolio!</h2></#if>
	   <canvas id="gains"></canvas>
    </div>
    <div class="col-3">
      <div class="header">
        Stocks <button id="addButton" type="button" data-toggle="modal" data-target="#addStockModal" class="btn float-right btn-secondary btn-sm">+</button>
      </div>
    	<div id="stocks" class="list-group expand">
        <#list stocks as stock>
			    <a href="/stock/${stock.ticker}" class="list-group-item list-group-item-action">${stock.ticker} ${stock.shares}</a>
        </#list>
		  </div>
    </div>

    <#include "stockModal.ftl">           
  </div>
</div>
</#assign>
<#include "main.ftl">