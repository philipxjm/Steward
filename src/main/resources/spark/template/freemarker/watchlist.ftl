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
            <div class="grid-item" id=${stock[1]}>
                <a href="/stock/${stock[0]}">${stock[0]}</a>
                <p>$${stock[2]}</p>
                <p>(${stock[3]}%)</p>
            </div>
        </#list>
    </div>
</div>
</#assign>
<#include "main.ftl">