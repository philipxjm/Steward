class StockGraph {
    constructor(ctx, ticker, timeseries) {
        this.timeseries = timeseries;
        this.ticker = ticker;
        const params = {
            "ticker" : this.ticker,
            "timeseries" : this.timeseries
        }
        this.getData(params, () => {
            const graphData = {
                type: 'line',
                data: {
                    datasets: [{
                        label: 'Stock Prices',
                        data: this.data,
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
                                    if (this.labels[value]) {
                                        return this.dateToString(this.labels[value], false);
                                    } else {
                                        return "";
                                    }
                                }
                            }
                        }]
                    },
                    tooltips: {
                        enabled: true,
                        mode: 'single',
                        callbacks: {
                            title: (info) => { 
                                return this.dateToString(this.labels[info[0].index], true);
                            }
                        }
                    }                    
                }
            }
            this.graph = new Chart(ctx, graphData);
        });
    }

    dateToString(D, full) {
        const month = D.getMonth() + 1;
        const day = (D.getDate()+"").padStart(2,"0");
        const year = D.getFullYear() % 1000;
        const hour = D.getHours();
        const min = (D.getMinutes()+"").padStart(2,"0");
        if (full || this.timeseries == "ONE_DAY" || this.timeseries == "FIVE_DAY") {
            return `${month}/${day}/${year} ${hour}:${min}`;
        } else {
           return `${month}/${day}/${year}`;
        }
    }

    getData(params, callback) {
        $.post('/getGraphData', params, (res) => {
            const resData = JSON.parse(res);
            let pastData = [];
            let labels = [];
            let last = resData[0];
            let c = 0;

            for(let p of resData) {
                if(p[0] - last > 3000 && (this.timeseries == "ONE_DAY" || this.timeseries == "FIVE_DAY")) {
                    pastData.push({x: NaN, y: NaN})
                }
                last = p[0];
                labels.push(new Date(p[0]*1000));
                pastData.push({x: c, y: p[1]});
                c += 1;
            }

            this.data = pastData;
            this.labels = labels;
            this.min = pastData[0].x;
            this.max = pastData[pastData.length-1].x;
            callback();       
        });
    }

    changeTimeseries(timeseries) {
        this.timeseries = timeseries;
        const params = {
            "ticker" : this.ticker,
            "timeseries" : this.timeseries
        }
        this.getData(params, data => {
            this.graph.data.datasets.pop();
            this.graph.data.datasets.push({
                label: 'Stock Prices',
                data: this.data,
                borderColor: 'red'
            });
            this.graph.update();            
        });
    }
}

// Ticker to graph
const ticker = $('h2')[0].innerText;

// Make chart
const ctx = $("#graph");
let stockGraph = new StockGraph(ctx, ticker, "ONE_DAY"); // Default to showing ONE_DAY

// Change timeseries on button click
$('.time').click((e) => {
    const timeseries = e.currentTarget.children[0].id;
    stockGraph.changeTimeseries(timeseries);
});