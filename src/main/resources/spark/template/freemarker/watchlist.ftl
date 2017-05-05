<#assign name="watchlist">
<#assign content>
<div class="row">
<div class="col">
<p>
<#list good as stock>
${stock[0]} ${stock[1]}<br/>
</#list>
</p>
</div>

<div class="col">
<p>
<#list bad as stock>
${stock[0]} ${stock[1]}
	<br/>
</#list>
</p>
</div>
</div>
</#assign>
<#include "main.ftl">