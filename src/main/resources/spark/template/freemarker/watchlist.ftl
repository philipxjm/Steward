<#assign name="watchlist">
<#assign content>
<#list trending as stock>
	<p>${stock[0]} ${stock[1]}</p>
	<br/>
</#list>
</#assign>
<#include "main.ftl">