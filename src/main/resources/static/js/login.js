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


}	

function onSignOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		console.log('User signed out.');
	});
	$.get("/logout");
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
}