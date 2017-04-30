<!-- Nav -->
<nav class="navbar navbar-toggleable-md navbar-inverse bg-inverse">
  <a class="navbar-brand" href="/">Steward</a>
  <div class="collapse navbar-collapse">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/about">About</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <div id="searchBox" class="input-group">
            <input id="searchText" type="text" class="form-control" placeholder="Search for...">
              <span class="input-group-btn">
              <button id="searchButton" class="btn btn-secondary" type="button"><img id="searchIcon" src="/img/search.png"></button>
            </span>
          </div>    
        </li>
        <li class="nav-item">
            <#if user??>
            <div id="user" class="dropdown">
              <button id="username" class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${user}
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
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