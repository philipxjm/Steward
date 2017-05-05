// Stock add
$('#addStock').click((e) => {
    let action;
    if($('#buy').hasClass("active")) {
        action = "buy";
    } else {
        action = "sell";
    }
    // Get timestamp
    let time;
    if ($('#pastAction').prop("checked")) {
        time = + new Date($('#actionDate').val());
    } else {   
        time = + new Date();
    }

    if (isNaN(time)) {
        $('#stockError')[0].innerText = "ERROR: Enter a past date for the action.";
        return;
    }

    let ticker = $('#ticker').val().toUpperCase();
    if (!ticker) {
        $('#stockError')[0].innerText = "ERROR: Enter a ticker for the action.";
        return;        
    }

    let shares = $('#shares').val();
    if (!shares) {
        $('#stockError')[0].innerText = "ERROR: Enter an amount for the action.";
        return;        
    }
    let port = getCurrentPort();
    console.log(port);
    let data = {
        current: !$('#pastAction').prop("checked"),
        port: port,
        time: time,
        action: action,
        ticker: ticker,
        shares: shares
    }
    $('#addStock').prop('disabled', true);
    $.post('/stockAction', data, (res) => {
    console.log("happening");
        let resData = JSON.parse(res);
        console.log(resData);
        if (resData["success"]) {
            $('#stockError')[0].innerText = "";
            $('#addStockModal').modal('hide');
            getStocks(getCurrentPort(), ()=>{graph.update(port);});
        } else {
            $('#stockError')[0].innerText = "ERROR: " + resData["error"];
        }
        $('#addStock').prop('disabled', false);
    });

    return false;
});

$(function () {
  $('#pastAction').change(function () {                
     $('#time').toggle(this.checked);
  }).change(); //ensure visible state matches initially
});

