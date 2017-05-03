<#assign chart=true>
<#assign name="pools">
<#assign js>
  <script src="/js/UnrealizedGraph.js"></script>
  <script src="/js/dashboard.js"></script>
  <script src="/js/stockModal.js"></script>
</#assign>
<#assign css>
<link rel="stylesheet" href="/css/dashboard.css">
<link rel="stylesheet" href="/css/pools.css">
</#assign>
<#assign content>
<div class="container">
  <div class="row" id="main">
    <div class="col-3">
      <div class="header">
        Pools
        <button type="button" id="create" data-toggle="modal" data-target="#createPoolModal" class="btn float-right btn-secondary btn-sm">Create</button>
        <button type="button" id="join" data-toggle="modal" data-target="#joinPoolModal" class="btn float-right btn-secondary btn-sm">Join</button>
      </div>
    	<div id="ports" class="list-group expand">
        <#list pools as pool>
		   	 <div class="list-group-item list-group-item-action port <#if port_index == 0>active</#if>">         
              <span class="portName">${pool.name}</span>
              <a class="actionButton editPort float-right fa fa-pencil" aria-hidden="true"></a>
              <a class="actionButton deletePort float-right fa fa-trash" aria-hidden="true"></a>
          </div>
        </#list>
		  </div>
    </div>
    <div id="graphContainer" class="col-6">
      <#if pools?size == 0><h2 id="noPort" class="text-muted">Create or join a pool!</h2></#if>
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
    <#include "createPoolModal.ftl">
  </div>
</div>
</#assign>
<#include "main.ftl">