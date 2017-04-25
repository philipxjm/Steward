// Ticker to graph
const ticker = $('h2')[0].innerText;

// Make chart
const ctx = $("#graph");
let stockGraph = new StockGraph(ctx, ticker, "ONE_DAY"); // Default to showing ONE_DAY

// Change timeseries on button click
$('.time').click((e) => {
    const timeseries = e.currentTarget.children[0].id;
    stockGraph.update(timeseries);
});