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

$.post('/getSentiment', {ticker: ticker}, (res) => {
	let sentiment = Math.round(parseFloat(res)*100)/100;
	$('#sentiment').append(`Sentiment: <span id='sentimentValue'>${sentiment}</span>`);
    if (sentiment < 0.5) {
       $('#sentimentValue').addClass("down");
    } else if(sentiment > 0.5) {
        $('#sentimentValue').addClass("up");
    }
});