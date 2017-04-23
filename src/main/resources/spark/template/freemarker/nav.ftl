<!-- Nav -->
<nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
  <a class="navbar-brand" href="/">Steward</a>
  <div class="collapse navbar-collapse">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/about">About</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <span id="login" class="nav-link g-signin2" data-onsuccess="onSignIn"></span>
          <div id="user" class="dropdown" style="display:none">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Username
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <a class="dropdown-item" href="#">Dashboard</a>
              <a class="dropdown-item" href="/" onclick="onSignOut()" href="#">Sign Out</a>
            </div>
          </div>
        </li>
    </ul>
  </div>
</nav>