// Set default date for create modal
$(() => {
    let d = new Date();
    d.setDate(d.getDate() + 10);
    $('#end').val(d.toISOString().substr(0,10));
});

// Returns new jQuery obj for the name of a pool
function makeNewPool(name) {
   let ret = $(`<div class="list-group-item list-group-item-action pool">         
              <span class="portName">${name}</span>
              <!--<a class="actionButton editPort float-right fa fa-pencil" aria-hidden="true"></a>
              <a class="actionButton deletePort float-right fa fa-trash" aria-hidden="true"></a>-->
          </div>`);
   return ret;
}

function poolClickHandler(e) {
  let elm = $(e.target);
  let name = elm.children('.portName')[0].innerText;
  let id = elm.attr('poolId');
  graph.poolId = id;
  graph.update(name);
  $('#info').empty();
 	$('.pool').removeClass('active');
  $('#stocks').empty();
 	elm.addClass('active');
  getStocks(getCurrentPort());
  $.post('/getPoolInfo', {name: name}, (res) => {
    let data = JSON.parse(res);
    $('#currBalance').text(res.curr);
    $('#initBalance').text(res.init);
  });
  console.log({poolId:id});
  $.post('/getLeaderboard', {poolId:id}, (res) => {
    let data = JSON.parse(res);
    $('#poolId').text(id);
    $leaderboard = $('#leaderboard')
    $('.position').remove();
    let lastBalance = -1;
    let lastPlace;
    for (var i = 0; i < data.length; i++) {
      let pic = data[i].pic;
      let name = data[i].user;
      let balance = data[i].balance;
      let place;
      if (lastBalance == balance) {
        place = lastPlace;
      } else {
        place = i+1;
      }
      lastPlace = place;
      lastBalance = balance;
      $leaderboard.append(`<li class='position list-group-item'><img class="leaderPic rounded" src='${pic}?sz=35'>${place}. ${name}<br>$${balance}</li>`);
    }
  });
}

$('.pool').click(poolClickHandler);

// Add portfolio button
$('#joinPool').click((e) => {
    if($('#newPool').length == 0) {
        $('#pools').append('<div class="form-group list-group-item list-group-item-action pool newPool"><label for="newPool">Pool ID:</label><input class="form-control" id="newPool" type="text"><p id="poolErr" class="text-danger"></p></div>');
        let inputDiv = $('#newPool').parent();
        $('#newPool').keydown((e) => {
            if (e.keyCode == 13) { // Enter
                e.preventDefault();
                let poolId = $(e.target).val();
                if (poolId) {
                    $.post('/joinPool', {name: poolId}, (res) => {
                        let resData = JSON.parse(res);
                        if (!resData) {
                            $('#poolErr')[0].innerText = "Bad pool ID";
                        } else {
                            if( $('.disabler').prop('disabled')) {
                                $('.disabler').prop('disabled', false);
                                $('#noPool').html(''); 
                                $('#gains').show();
                            }
                            $('.port').removeClass('active');
                            let newPoolInput = $('.newPool');
                            let newPool = makeNewPool(resData.name);
                            newPoolInput.replaceWith(newPool);
                            newPool.click(poolClickHandler);
                            newPool.click();                            
                            $('#stocks').empty();
                        }
                    });
                } else {
                    // Remove
                   inputDiv.remove();
                }
            } else if (e.keyCode == 27) { // Escape
                e.preventDefault();
                // Remove
                inputDiv.remove();
            }
        });
        $('#newPool').focus();
    }
});

$('#createPool').click((e) => {
	let name = $('#name').val();
	let end = + new Date($('#end').val());
	let balance = $('#balance').val();
  let ai = $('#ai').is(':checked');

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
		balance: balance,
    ai: ai
	};

	$('#createPool').prop('disabled', true);
	$.post('/newPool', param, (res) => {
    let resData = JSON.parse(res);

		// TODO Update pool sidebar
		$('#createPoolModal').modal('hide');
		$('#poolError').text('');
		$('#createPool').prop('disabled', false);
    $('#addButton').prop('disabled', false);
		$newPool = makeNewPool(name);
    $newPool.click(poolClickHandler);
		$('#pools').append($newPool);

    $newPool.attr("poolId", resData.id);
    $('#noPort').hide();
    $('#poolInfo').show();
    if (!graph) {
      graph = new UnrealizedGraph(ctx, resData.name);
    }
    $('#gains').show();
    $newPool.click();
		// TODO add click handlers
	});
});