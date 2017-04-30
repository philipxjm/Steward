$('#searchButton').click((e) => {
	let search = $('#searchText').val();
	if (search) {
		window.location = "/stock/"+search;
	}
});	