
<!DOCTYPE html>

<html>
  <head>
      <title>Stocks</title>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
      <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
      <link rel="stylesheet" type="text/css" href="/css/graph.css">
  </head>
  <body>
    <!-- Nav -->
    <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand" href="/">Stock Tracker</a>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/about.html">About <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item inactive">
            <a class="nav-link" href="#">Stocks <span class="sr-only">(current)</span></a>
          </li>
        </ul>

        <a href="#" onclick="signOut();">Sign out</a>
        <script>
  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }
</script>
      </div>
    </nav>
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
              <h2>$${price.value} <span class=${color}>(${change.value}%)</span> </h2>
            </div>
            <div class="col col-sm">
              <table>
                 <tr>
                 <#list fundamentals as fund>
                      <#if fund_index % 2 == 0>
                       </tr><tr>
                      </#if>
                      <td>${fund.type}</td> <td>${fund.value}</td>
                 </#list>
                </tr>
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
