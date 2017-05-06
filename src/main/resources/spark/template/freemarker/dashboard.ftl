<#assign chart=true>
<#assign name="index">
<#assign js>
  <script src="/js/UnrealizedGraph.js"></script>
  <script src="/js/dashboard.js"></script>
  <script src="/js/pools.js"></script>
  <script src="/js/stockModal.js"></script>
</#assign>

<#assign css>
  <link rel="stylesheet" type="text/css" href="/css/dashboard.css">
</#assign>

<#assign content>
<div class="container">
  <div class="row" id="main">
    <div class="col-3">
      <ul class="nav nav-tabs" role="tablist">
        <li class="nav-item">
          <a class="tabToggle nav-link active" data-toggle="tab" href="#portTab" role="tab">Portfolios</a>
        </li>
        <li class="nav-item">
          <a class="tabToggle nav-link" data-toggle="tab" href="#poolTab" role="tab">Pools</a>
        </li>
      </ul>
      <div class="tab-content">
      <div id="portTab" class="tab-pane active" role="tabpanel">
        <div class="buttonBar btn-group float-right">
          <button type="button" id="addPort" class="btn btn-secondary btn-sm">+</button>
        </div>
      	<div id="ports" class="list-group expand">
          <#list portfolios as port>
  		   	 <div class="list-group-item list-group-item-action port <#if port_index == 0>active</#if>">         
                <span class="portName">${port.name}</span>
                <a class="actionButton editPort float-right fa fa-pencil" aria-hidden="true"></a>
                <a class="actionButton deletePort float-right fa fa-trash" aria-hidden="true"></a>
            </div>
          </#list>
  		  </div>
      </div>
      <div id="poolTab" class="tab-pane" role="tabpanel">
        <div class="buttonBar btn-group float-right">
          <button type="button" id="joinPool" data-toggle="modal" data-target="#joinPoolModal" class="btn btn-secondary btn-sm">Join</button>
          <button type="button" id="create" data-toggle="modal" data-target="#createPoolModal" class="btn btn-secondary btn-sm">Create</button>          
        </div>
        <div id="pools" class="list-group expand">
          <#list pools as pool>
           <div poolId="${pool.pool.id}" class="list-group-item list-group-item-action pool <#if pool_index == 0>active</#if>">         
                <span class="portName">${pool.name}</span>
                <!--<a class="actionButton editPort float-right fa fa-pencil" aria-hidden="true"></a>
                <a class="actionButton deletePort float-right fa fa-trash" aria-hidden="true"></a>-->
            </div>
          </#list>
        </div>
      </div>
      </div>
    </div>
    <div id="graphContainer" class="col-6">
      <div id="noPort" class="text-muted"></div>
	   <canvas height=200 id="gains"></canvas>
     <div id="poolInfo" class="row">
      <div class="col">
        <ul id="infoList" class="list-group">
          <li class="list-group-item active">Info</li>
          <li class="list-group-item"><span class="col">Pool ID</span> <span class="col text-right" id="poolId"></span></li>
          <li class="list-group-item"><span class="col">Current balance <span class="col text-right" id="currBalance"></span></li>
          <li class="list-group-item"><span class="col">Initial balance <span class="col text-right" id="initBalance"></span></li>
          <li class="list-group-item"><span class="col">Percent gain <span class="col text-right" id="gain"></span></li>
        </ul>      
      </div>
      <div class="col">
        <ul id="leaderboard" class="list-group">
          <li class="list-group-item active">Leaderboard</li>
        </ul>
      </div>
     </div>
    </div>
    <div class="col-3">
      <div class="header">
        Stocks
        <div class="btn-group float-right">
        <button id="historyButton" type="button" class="disabler btn float-right btn-secondary btn-sm"><i class="fa fa-history" aria-hidden="true"></i></button>
        <button id="addButton" type="button" data-toggle="modal" data-target="#addStockModal" class="disabler btn float-right btn-secondary btn-sm">+</button>
        </div>
      </div>
      <hr/>
    	<div id="stocks" class="list-group expand">
        <#list stocks as stock>
			    <a href="/stock/${stock.ticker}" class="stocks
			    list-group-item list-group-item-action">${stock.ticker}
			    ${stock.shares} shares ${}</a>
        </#list>
		  </div>
    </div>

    <#include "stockModal.ftl">
    <#include "createPoolModal.ftl">         
  </div>
</div>
</#assign>
<#include "main.ftl">