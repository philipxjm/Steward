<#assign name="watchlist">
<#assign content>
<div class="container">
<div class="row">
<h2>Stocks to Watch</h2>
<p>
<#list trending as stock>
<div class="stock">
<a href="/stock/${stock[0]}">${stock[0]} ${stock[1]}</a>
</div>
</#list>
</p>
</div>
</div>
</#assign>
<#include "main.ftl">