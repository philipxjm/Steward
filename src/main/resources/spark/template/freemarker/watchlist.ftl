<#assign name="watchlist">
<#assign content>
<link rel="stylesheet" type="text/css" href="/css/watchlist.css">
<h2>Stocks to Watch</h2>
<br/>
<div class = "stocks">
<#list trending as stock>
<div class="stock">
<a href="/stock/${stock[0]}" class = "list-group-item
list-group-item-action">${stock[0]} ${stock[1]}</a>
</div>
</#list>
</div>
</#assign>
<#include "main.ftl">