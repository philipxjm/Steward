
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
          <div class="btn-group" data-toggle="buttons">
            <label class="btn btn-primary active time">
              <input type="radio" name="timespan" id="ONE_DAY" autocomplete="off" checked>One Day
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="FIVE_DAY" autocomplete="off">Five Day
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="ONE_MONTH" autocomplete="off">One Month
            </label>
            <label class="btn btn-primary time">
              <input type="radio" name="timespan" id="SIX_MONTH" autocomplete="off">Six Month
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
</html>
</#assign>
<#include "main.ftl">
