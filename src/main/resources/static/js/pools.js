$('#createPool').click((e) => {
	let name = $('#name').val();
	let end = + new Date($('#end').val());
	let balance = $('#balance').val();

	if (!name) {
		$('#poolError').text('ERROR: Please give the pool a name.');
		return;
	}
	if (!end) {
		$('#poolError').text('ERROR: Please give the pool an end date.');
		return;
	}
	if (end <= + new Date()) {
		$('#poolError').text('ERROR: The pool end date must be after today.');
		return;
	}
	if (!balance) {
		$('#poolError').text('ERROR: Please give the pool a balance.');
		return;
	}
	let param = {
		name: name,
		end: end,
		balance: balance
	}

	$('#createPool').prop('disabled', true);
	$.post('/newPool', param, (res) => {
		
		// TODO Update pool sidebar
		$('#createPoolModal').modal('hide');
		$('#poolError').text('');
		$('#createPool').prop('disable', false);
	});
});