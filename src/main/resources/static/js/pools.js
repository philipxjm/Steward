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
              <!--<a class="actionButton editPort float-right fa fa-pencil" aria-hidden="true"></a>-->
              <a class="actionButton deletePool float-right fa fa-sign-out" aria-hidden="true"></a>
          </div>`);
   return ret;
}

// Handles whenever a pool is clicked on
function poolClickHandler(e) {
  let elm = $(e.target);

  if (elm.hasClass('deletePool')) {
    let name = elm.first().prev().text();
    let data = {name: name};
    log('/leavePool', name);
    $.post('/leavePool', data);
    elm.parent().remove();
    if ($('.pool').length == 0) {
      $('#poolGraph').hide();
      $('#poolInfo').hide();
      showEmptyMessage(false);
    } else {
      $('.pool').first().click();
    }
    return;
  }

  if (elm.hasClass('portName')) {
    elm = elm.parent();
  }


  // If already active we don't need to update
  if (elm.hasClass("active")) {
    return;
  }

  if(poolGraph.graph) {
      poolGraph.graph.clear();
  }

  let name = elm.children('.portName')[0].innerText;
  let poolId = elm.attr('poolId');

  // Set pool id in info section
  $('#poolId').text(poolId);
  poolGraph.update(name, poolId);

  // Remove active from old
 	$('.pool').removeClass('active');
  // Add active to new
  elm.addClass('active');
  // Remove old stocks
  $('#stocks').empty();
  // Get stocks for new pool portfolio
  getStocks(getCurrentPort());

  let data = { poolId: poolId };
  // Get leaderboard for pool
  log('/getLeaderboard', data);
  $.post('/getLeaderboard', data, (res) => {
    let data = JSON.parse(res);
    $leaderboard = $('#leaderboard')
    // Remove old leaderboard
    $('.position').remove();

    // Save last balance & place for when you have a tie
    let lastBalance = -1;
    let lastPlace;
    // Loop over each person in leaderboard
    for (var i = 0; i < data.length; i++) {
      let pic = data[i].pic;
      let name = data[i].user;
      let balance = Math.round(data[i].balance);
      let id = data[i].userId;
      let place;
      // Check for tie
      if (lastBalance == balance) {
        place = lastPlace;
      } else {
        place = i+1;
      }
      lastPlace = place;
      lastBalance = balance;
      // Add new person to leaderboard
      $leaderboard.append(`<li id="user${id}" class='position list-group-item'>
        <img class="leaderPic rounded" src='${pic}?sz=35'>
        <div>
          <div class="fullWidth">${place}. <a href="/user/${id}">${name}</a></div>
          <div><p class="balance">$${balance}</p><div>
        <div>
      </li>`);
      getPoolInfo(getCurrentPort(), poolId);
    }
  });
}

function getPoolInfo(name, poolId) {
  let data = {name: name, poolId: poolId};
  // Load in pool info (balance, etc.)
  log("/getPoolInfo", data);
  $.post('/getPoolInfo', data, (res) => {
    let data = JSON.parse(res);
    $('#currBalance').text('$' + Math.round(data.curr));
    $('#initBalance').text('$' + Math.round(data.init));
    let userId = $('#user').attr('userId');

    let value = parseFloat($(`#user${userId}`).find('p.balance').text().substr(1));
    let percentage = 100*(value-data.init) / data.init;
    let changeClass = '';
    if (percentage > 0) {
      changeClass = 'up';
    } else if (percentage < 0) {
      changeClass = 'down';
    }
    $('#change').text(Math.round(percentage*100)/100 + '%');
    $('#change').addClass(changeClass);
    $('#poolInfo').show();
  });  
}

// Add click handler to all pools
$('.pool').click(poolClickHandler);

// Add portfolio button
$('#joinPool').click((e) => {
    // Only add a newPool is you aren't already joining
    if($('#newPool').length == 0) {
        // Add input for join pool
        $('<div class="form-group list-group-item list-group-item-action pool newPool"><label for="newPool">Pool ID:</label><input class="form-control" id="newPool" type="text"><p id="poolErr" class="text-danger"></p></div>').insertAfter($('.pool:not(.inactive)').last());
        let inputDiv = $('#newPool').parent();
        $('#newPool').keydown((e) => {
            if (e.keyCode == 13) { // Enter
                e.preventDefault();
                let poolId = $(e.target).val();
                if (poolId) {
                    let data = {name: poolId};
                    log('/joinPool', data);
                    $.post('/joinPool', data, (res) => {
                        let resData = JSON.parse(res);
                        if (!resData) {
                            $('#poolErr')[0].innerText = "Bad pool ID";
                        } else {
                            if( $('.disabler').prop('disabled')) {
                                $('.disabler').prop('disabled', false);
                                $('#noPort').html(''); 
                            }
                            if (!poolGraph) {
                              poolGraph = new BalanceGraph(poolCtx, resData.name, poolId)
                              $('#poolGraph').show();
                            }
                            $('.port').removeClass('active');
                            let newPoolInput = $('.newPool');
                            let newPool = makeNewPool(resData.name);
                            newPool.attr("poolId", poolId);
                            newPoolInput.replaceWith(newPool);
                            newPool.click(poolClickHandler);                            
                            newPool.click();
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

// Clicking create cancels join
$('#create').click((e) => {
  $('#newPool').parent().remove();
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
  log('/newPool', param)
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

    if (!poolGraph) {
      poolGraph = new BalanceGraph(poolCtx, resData.name, resData.id);
    }
    $('#poolGraph').show();
    $('#gains').show();
    $newPool.click();
	});
});