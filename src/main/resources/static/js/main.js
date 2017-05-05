$(function(){
  $(document).keypress(function(e){
    if(e.which == 13) { // Enter
      let elm = $(e.target);
      $('.modal.show').find('.modal-submit').click();
    }
  })


console.log($('#searchButton'));
$('#searchButton').click((e) => {
	let search = $('#searchButton').prev().val();

	if (search) {
		window.location = "/stock/"+search;
	}
});	

let x = new Autocorrect($('#stockModalTickerDiv'));
let inp = new Autocorrect($('#searchBox'), true);
});