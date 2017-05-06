<#assign name="watchlist">
<#assign css>
<link rel="stylesheet" type="text/css" href="/css/watchlist.css">
</#assign>
<#assign js>
<script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.min.js"></script>
<script src="/js/watchlist.js"></script>
</#assign>
<#assign content>
<div class="container" id="stocks">
    <h2>Stocks to Watch</h2>
    <div class="grid">
        <#list trending as stock>
            <div class="grid-item">
                <div data-toggle="tooltip" data-placement="top"
                     class="card"
                     id=${stock[1]} data-original-title="Sentiment: ${stock[1]}">
                    <div class="card-block">
                        <h6 class="card-title"><a href="/stock/${stock[0]}">${stock[0]}</a></h6>
                        <p class="card-text">Price: $${stock[2]}</p>
                        <p class="card-text">Change: (${stock[3]}%)</p>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</#assign>
<#include "main.ftl">