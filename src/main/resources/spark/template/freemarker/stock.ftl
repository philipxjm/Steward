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
          <div class="row row-second">
            <div id="graph-wrapper">
              <canvas id="graph"></canvas>
            </div>
          </div>
          <div class="row row-third justify-content-start">
            <div class="col col-sm-2">
              <h3>$923.43</h3>
            </div>
            <div class="col col-sm">
              <table>
                <tr>
                  <td>Range</td> <td>140.23 - 141.50</td>
                  <td>52 week</td> <td>89.47 - 141.50</td>
                </tr>

                <tr>
                  <td>Open</td> <td>140.40</td>
                  <td>Vol / Avg.</td> <td>20.71M/24.49M</td>
                </tr>

                <tr>
                  <td>Mkt cap</td> <td>738.48B</td>
                  <td>P/E</td> <td>16.94</td>
                </tr>

                <tr>
                  <td>Div/yield</td> <td>0.57/1.61</td>
                  <td>EPS</td> <td>8.35</td>
                </tr>

                <tr>
                  <td>Shares</td> <td>5.25B</td>
                  <td>Beta</td> <td>1.25</td>
                </tr>
                <tr>
                  <td>Inst. own</td> <td>61%</td>
                </tr>
              </table>
            </div>
          </div>
        </main>
      </div>
    </div>
</#assign>
<#include "main.ftl">