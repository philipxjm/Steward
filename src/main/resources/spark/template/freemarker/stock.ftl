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
              
            </div>
            <div class="col col-sm">
              <table>
                  <#list fundamentals as fund>
                    <tr><td>${fund.type}</td> <td>${fund.value}</td></tr>
                  </#list>
              </table>
            </div>
          </div>
        </main>
      </div>
    </div>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js"></script>
    <script src="/js/graph.js"></script>
    <script src="/js/login.js"></script>
  </body>
</html>
</#assign>
<#include "main.ftl">
