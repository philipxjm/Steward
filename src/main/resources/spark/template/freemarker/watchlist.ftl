<#assign name="watchlist">
<#assign css>
<link rel="stylesheet" type="text/css" href="/css/watchlist.css">
</#assign>
<#assign js>
<script src="/js/watchlist.js"></script>
</#assign>
<#assign content>
<div class="container" id="demo">
    <h2>Stocks to Watch</h2>
    <div class="table-responsive-vertical shadow-z-1">
        <table id="table" class="table table-hover table-mc-light-blue">
            <thead>
                <tr>
                    <th>Symbol</th>
                    <th>Price</th>
                    <th>Sentiment</th>
                </tr>
            </thead>
            <tbody>
                <#list trending as stock>
                <tr>
                    <td data-title="Symbol">
                        <a href="/stock/${stock[0]}">${stock[0]}</a>
                    </td>
                    <td data-title="Price">$${stock[2]} (${stock[3]}%)
                    </td>
                    <td data-title="Sentiment">${stock[1]}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</#assign>
<#include "main.ftl">