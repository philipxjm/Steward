<#assign name="account">
<#assign css>
<link rel="stylesheet" type="text/css" href="/css/account.css">
</#assign>
<#assign content>
<div id="main" class="container">
<div class="row">
	<img id="pic" src="${pic}?sz=700">
</div>
<div id="inner" class="row">
	<div class="col">
	Name <br/>
	Email <br/>
	User ID
	</div>
	<div class="col">
	${user} <br/>
	${email} <br/>
	${id}
	</div>	
</div>
</div>
</#assign>
<#include "main.ftl">