<#assign name="stock">
<#assign chart=true>
<#assign js>
  <script src="/js/StockGraph.js"></script>
  <script src="/js/stock.js"></script>
</#assign>
<#assign css>
<link rel="stylesheet" type="text/css" href="/css/graph.css">
</#assign>
<#assign content>
    <div class="expand container-fluid">
      <div class="expand row">
        <!-- Main -->
        <main class="content col-md-8 offset-md-2">
          <div class="row row-first">
            <h2><span id="ticker">${ticker}</span> - ${company}
              <#if user??>
                <button class="btn btn-secondary float-right" data-toggle="modal" data-target="#stockPageModal">Buy/Sell</button>
              </#if>
            </h2>
          </div>
          <div id="btnBar" class="btn-group" data-toggle="buttons">
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="ONE_DAY" autocomplete="off">One Day
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="FIVE_DAY" autocomplete="off">Five Day
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="ONE_MONTH" autocomplete="off">One Month
            </label>
            <label class="btn btn-primary active time">
              <input type="radio" name="timespan" id="SIX_MONTH" autocomplete="off" checked>Six Month
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="ONE_YEAR" autocomplete="off">One Year
            </label>   
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="TWO_YEAR" autocomplete="off">Two Year
            </label>                   
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="FIVE_YEAR" autocomplete="off">Five Year
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="TEN_YEAR" autocomplete="off">Ten Year
            </label>            
          </div>
          <div class="row row-second">
            <div id="graph-wrapper">
              <canvas height=300 id="graph"></canvas>
            </div>
          </div>
          <div class="row row-third justify-content-start">
            <div class="col col-sm-4">
              <h3><span id="price">$${price.value}</span> <span class=${color}>(${change.value}%)</span> </h2>
              <h3 id="predicted"></h3>
              <h3 id="sentiment"></h3>
            </div>
            <div class="col col-sm">
              <table>
                 <tr>
                 <#list fundamentals as fund>
                      <#if fund_index % 2 == 0>
                       </tr><tr>
                      </#if>
                      <td>${fund[0]}</td> <td>${fund[1]}</td>
                 </#list>
                </tr>
              </table>
            </div>
          </div>
        </main>
      </div>
    </div>
    <#include "stockPageModal.ftl">    
</html>
</#assign>
<#include "main.ftl">
