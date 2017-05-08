// Ticker to graph
const ticker = $('#ticker').text();

// Make chart
const ctx = $("#graph");
let stockGraph = new StockGraph(ctx, ticker, "SIX_MONTH"); // Default to showing FIVE_DAY

// Change timeseries on button click
$('.time').click((e) => {
    const timeseries = e.currentTarget.children[0].id;
    stockGraph.update(timeseries);
});

// $.post('/getSentiment', {ticker: ticker}, (res) => {
// 	let sentiment = Math.round(parseFloat(res)*100)/100;
// 	$('#sentiment').append(`Sentiment: <span id='sentimentValue'>${sentiment}</span>`);
//     if (sentiment < 0.5) {
//        $('#sentimentValue').addClass("down");
//     } else if(sentiment > 0.5) {
//         $('#sentimentValue').addClass("up");
//     }
// });

$(document).ready(function() {
    $('.tooltips').tooltip();
});

// Stock add
$('#addStock').click((e) => {
    let action;
    if($('#buy').hasClass("active")) {
        action = "buy";
    } else {
        action = "sell";
    }
    /* Get timestamp
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
    }*/
    let time = + new Date();
    /*
    let ticker = $('#ticker').val().toUpperCase();
    if (!ticker) {
        $('#stockError')[0].innerText = "ERROR: Enter a ticker for the action.";
        return;        
    }*/
    let ticker = $('#ticker').text();

    let shares = $('#shares').val();
    if (!shares) {
        $('#stockError')[0].innerText = "ERROR: Enter an amount for the action.";
        return;        
    }
    let elm = $(':selected');
    let port = elm.val(); 
    if (port == "Buy for") {
        $('#stockError')[0].innerText = "ERROR: Select a portfolio/pool to buy for.";
        return;      	
    }
    console.log(elm);
    let isPool = elm.parent().attr('label') == 'Pools';
    let data = {
        current: true,// !$('#pastAction').prop("checked"),
        port: port,
        time: time,
        action: action,
        ticker: ticker,
        shares: shares,
        isPool: isPool
    }

    $('#addStock').prop('disabled', true);
    console.log(data);
    $.post('/stockAction', data, (res) => {
        let resData = JSON.parse(res);
        if (resData["success"]) {
            $('#stockError')[0].innerText = "";
            $('#stockPageModal').modal('hide');
        } else {
            $('#stockError')[0].innerText = "ERROR: " + resData["error"];
        }
        $('#addStock').prop('disabled', false);
    });

    return false;
});

// Hacky but makes max right initially
$(()=>{$('#SIX_MONTH').click();});