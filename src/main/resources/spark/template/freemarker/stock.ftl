<#assign chart=true>
<#assign js="/js/graph.js">
<#assign content>
    <div class="expand container-fluid">
      <div class="expand row">
        <!-- Main -->
        <main class="content col-md-8 offset-md-2">
          <div class="row row-first">
            <h2>${ticker}</h2>
          </div>
          <div class="btn-group" data-toggle="buttons">
            <label class="btn btn-primary active time">
              <input type="radio" name="timespan" portfolioId="ONE_DAY" autocomplete="off" checked>One Day
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="FIVE_DAY" autocomplete="off">Five Day
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="ONE_MONTH" autocomplete="off">One Month
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="SIX_MONTH" autocomplete="off">Six Month
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="ONE_YEAR" autocomplete="off">One Year
            </label>   
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="TWO_YEAR" autocomplete="off">Two Year
            </label>                   
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="FIVE_YEAR" autocomplete="off">Five Year
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" portfolioId="TEN_YEAR" autocomplete="off">Ten Year
            </label>            
          </div>
          <div class="row row-second">
            <div portfolioId="graph-wrapper">
              <canvas portfolioId="graph"></canvas>
            </div>
          </div>
          <div class="row row-third justify-content-start">
            <div class="col col-sm-2">
              <h2>$${price.value} <span class=${color}>(${change.value}%)</span> </h2>
            </div>
            <div class="col col-sm">
              <table>
                 <tr>
                 <#list fundamentals as fund>
                      <#if fund_index % 2 == 0>
                       </tr><tr>
                      </#if>
                      <td>${fund.type}</td> <td>${fund.niceValue}</td>
                 </#list>
                </tr>
              </table>
            </div>
          </div>
        </main>
      </div>
    </div>
</html>
</#assign>
<#include "main.ftl">
