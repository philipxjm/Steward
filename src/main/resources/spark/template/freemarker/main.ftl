<!DOCTYPE html>

<html>
  <head>
      <title>${title}</title>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
      <link rel="stylesheet" type="text/css" href="/css/main.css">
      <#if chart??>
	      <link rel="stylesheet" href="//cdn.jsdelivr.net/chartist.js/latest/chartist.min.css">
  	  </#if>
  	  <#if css??>
  	      ${css}
  	  </#if>
      <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
      <meta name="google-signin-client_id" content="993833341053-f3ks9cqj041b1uvj8an5omd7rdmu16j7.apps.googleusercontent.com">
  </head>
  <body>
  	<#include "nav.ftl">
    ${content}
	<div id="footer" class="container">
		<hr>
		<footer>
		  <p>&copy; Steward 2017</p>
		</footer>
	</div>
    <script src="https://apis.google.com/js/platform.js" async defer></script>  
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <script src="/js/Autocorrect.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/login.js"></script>
    <#if chart??>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.bundle.min.js"></script>
        <script src="/js/StewardGraph.js"></script>
    </#if>
    <#if js??>${js}</#if>
  </body>
</html>