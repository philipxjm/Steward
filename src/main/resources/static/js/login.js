let userName;
let userId;

function onSignIn(googleUser) {
	let profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId());
  console.log('Name: ' + profile.getName());
  userName = profile.getName();
  userId = profile.getId();
  $.get("/login", {name: userName, id: userId}, responseJSON => {
  	console.log(responseJSON);
  });
  if ($('#login')[0].style.display != "none") {
    window.location = window.location;
  }
}	

function onSignOut() {
	let auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		console.log('User signed out.');
	});
 $.get("/logout", {});
  window.location = window.location;
}