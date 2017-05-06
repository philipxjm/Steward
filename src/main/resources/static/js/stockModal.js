$(() => {
    let d = new Date();
    d.setDate(d.getDate() - 10);
    $('#actionDate').val(d.toISOString().substr(0,10));
});

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

    if(time > + new Date) {
        $('#stockError')[0].innerText = "ERROR: You can't buy a stock in the future.";
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

    let data = {
        current: !$('#pastAction').prop("checked"),
        port: port,
        time: time,
        action: action,
        ticker: ticker,
        shares: shares,
        isPool: !activeTabIsPort
    }
    $('#addStock').prop('disabled', true);
    console.log("Sending data to /stockAction:");
    console.log(data);
    $.post('/stockAction', data, (res) => {
        let resData = JSON.parse(res);
        if (resData["success"]) {
            $('#stockError')[0].innerText = "";
            $('#addStockModal').modal('hide');
            getStocks(getCurrentPort(), ()=>{
                if(activeTabIsPort) {
                    portGraph.update(port);
                } else {
                    poolGraph.update(port);
                }
            });
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

