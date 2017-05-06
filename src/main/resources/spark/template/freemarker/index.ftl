<#assign name="index">
<#assign css>
<link rel="stylesheet" href="/css/index.css">
</#assign>
<#assign content>
<!-- Main jumbotron for a primary marketing message or call to action -->
<div id="filter"></div>
<div id="jumbo" class="jumbotron">
  <div class="container">
    <h1 class="display-3">Lorum ipsum!</h1>
    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
  </div>
</div>

<!-- Example row of columns -->
<div class="container">
  <div class="row">
    <div class="col">
      <h2>Portfolio<br/>Tracking</h2>
      <p>Create portfolios to track your stocks' gains and losses over time.
      Compare between sectors and the market as a whole. See real-time and
      historical data to find trends in the market before anyone else. For
      the more financially-minded, we offer a broad selection of technical
      indicators for further analysis on stocks.
      </p>
      <p><a class="btn btn-secondary" href="/about#portfolio" role="button">View details &raquo;</a></p>
    </div>
    <div class="col">
      <h2>Short-Term Prediction</h2>
      <p>Our recurrent neural network trained with historical data provides
      a peek into the near future, suggesting stocks to buy and sell from
      your portfolios. Every day, a watchlist of hot stocks is provided for
      the opportunist capitalist. </p>
      <p><a class="btn btn-secondary" href="/about#prediction" role="button">View details &raquo;</a></p>
    </div>
    <div class="col">
      <h2>Sentiment Analysis</h2>
      <p>In today's ever-changing market, it is more important than ever to
      stay on top of what the market is feeling in real-time about a stock.
      With industrial level sentiment analysis on tweets and news, you will
      always have the edge on the market.</p>
      <p><a class="btn btn-secondary" href="/about#sentiment" role="button">View details &raquo;</a></p>
    </div>
    <div class="col">
      <h2>Stock Market Game</h2>
      <p></p>
      <p><a class="btn btn-secondary" href="/about#pools" role="button">View
      details &raquo;</a></p>
    </div>  
</div>
</#assign>
<#include "main.ftl">