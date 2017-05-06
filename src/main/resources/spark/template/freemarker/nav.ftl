<!-- Nav -->
<nav class="navbar navbar-toggleable-md navbar-inverse bg-inverse">
  <a class="navbar-brand" href=<#if name=="index">"#"<#else>"/"</#if>>Steward</a>
  <div class="collapse navbar-collapse">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href=<#if name=="about">"#"<#else>"/about"</#if>>About</a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href=<#if name=="watchlist">"#"<#else>"/watchlist"</#if>>Watchlist</a>
      </li>           
    </ul>
    <ul class="navbar-nav ml-auto">
        <li id="searchBox" class="nav-item input-group">
          <input class="autoInp form-control" placeholder="Search for..." type="text" autocomplete="off">
          <button id="searchButton" class="btn btn-secondary" type="button">
            <i class="fa fa-search" aria-hidden="true"></i>
          </button>
        </li>
        <li class="nav-item">
            <#if user??>
            <div id="user" class="dropdown">
              <button id="username" class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <img id="navProfPic" class="rounded" src="${pic}?sz=30"> ${user}
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="/account">Account</a>              
                <a class="dropdown-item" onclick="onSignOut()" href="#">Sign Out</a>
              </div>
            </div>
            <span id="login" style="display:none" class="nav-link g-signin2" data-onsuccess="onSignIn"></span>
            <#else>
              <div id="login" style="padding:0;" class="nav-link g-signin2" data-onsuccess="onSignIn"></div>
            </#if>
        </li>
    </ul>
  </div>
</nav>