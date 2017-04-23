class StockGraph {
    constructor(ctx, ticker, timeseries) {
        this.timeseries = timeseries;
        this.ticker = ticker;
        const params = {
            "ticker" : this.ticker,
            "timeseries" : this.timeseries
        }
        $.post('/getGraphData', params, (res) => {
            const resData = JSON.parse(res);
            let pastData = [];
            let labels = [];
            let last = resData[0];
            let c = 0;

            for(let p of resData) {
                if(p[0] - last > 1000) {
                    pastData.push({x: NaN, y: NaN})
                }
                last = p[0];
                labels.push(new Date(p[0]*1000));
                pastData.push({x: c, y: p[1]});
                c += 1;
            }

            const min = pastData[0].x;
            const max = pastData[pastData.length-1].x;

            const data = {
                type: 'line',
                data: {
                    datasets: [{
                        label: 'Stock Prices',
                        data: pastData,
                        borderColor: 'red'
                    }]
                },
                options: {
                    legend: false,
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        xAxes: [{
                            type: 'linear',
                            position: 'bottom',
                            ticks: {
                                callback: (value) => { 
                                    if (labels[value]) {
                                        return labels[value].toDateString();
                                    } else {
                                        return "";
                                    }
                                }
                            }
                        }]
                    },
                }
            }
            this.graph = new Chart(ctx, data);
        });
    }

    changeTimeseries(timeseries) {
        this.timeseries = timeseries;
        const params = {
            "ticker" : this.ticker,
            "timeseries" : this.timeseries
        }
        $.post('/getGraphData', params, (res) => {
            const resData = JSON.parse(res);
            let pastData = [];
            let labels = [];
            let last = resData[0];
            let c = 0;

            for(let p of resData) {
                if((this.timeseries == "ONE_DAY" || this.timeseries == "FIVE_DAY")
                    && p[0] - last > 1000) {
                    pastData.push({x: NaN, y: NaN});
                }
                last = p[0];
                labels.push(new Date(p[0]*1000));
                pastData.push({x: c, y: p[1]});
                c += 1;
            }

            const min = pastData[0].x;
            const max = pastData[pastData.length-1].x;

            this.graph.data.datasets.pop();
            this.graph.data.datasets.push({
                label: 'Stock Prices',
                data: pastData,
                borderColor: 'red'
            });
            this.graph.options.scales.xAxes[0].ticks.callback = (value) => {
                if (labels[value]) {
                    return labels[value].toDateString();
                } else {
                    return "";
                }                
            }
            this.graph.update();
        });
    }
}

function setGraph(graph, ticker, timeseries) {
    let pastData = null;
    let min = null;
    let max = null;

    let labels = [];
    $.post('/getGraphData', params, (res) => {
        let resData = JSON.parse(res);
        pastData = [];
        let last = resData[0];
        let c = 0;

        for(let p of resData) {
            if(p[0] - last > 1000) {
                pastData.push({x: NaN, y: NaN})
            }
            last = p[0];
            labels.push(new Date(p[0]*1000));
            pastData.push({x: c, y: p[1]});
            c += 1;
        }

        setDataset(scatterChart, labels, pastData);
    });
}

// Ticker to graph
const ticker = $('h2')[0].innerText;
// Make chart
const ctx = $("#graph");
let stockGraph = new StockGraph(ctx, ticker, "ONE_DAY");

$('.time').click((e) => {
    const timeseries = e.currentTarget.children[0].id;
    stockGraph.changeTimeseries(timeseries);
});