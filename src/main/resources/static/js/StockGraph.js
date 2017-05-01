class StockGraph extends StewardGraph {
    constructor(ctx, ticker, timeseries) {
        super("Stock Price");
        this.timeseries = timeseries;
        this.ticker = ticker;
        super.makeGraph();
    }

    getData(callback) {
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
                if(p[0] - last > 3000 && (this.timeseries == "ONE_DAY" || this.timeseries == "FIVE_DAY")) {
                    pastData.push({x: NaN, y: NaN})
                    labels.push(new Date(p[0]*1000));
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

    getPredict(callback) {
        let params = {
            "ticker": this.ticker
        }
        $.post('/getStockPrediction', params, (res) => {
            let data = JSON.parse(res);
            if (data) {
                this.predict = data;
                $('#predicted')[0].innerText = "Predicted: " + data[1];
                let curPrice = Number.parseFloat($('#price')[0].innerText.substr(1));
                if (curPrice > data[1]) {
                   $('#predicted').addClass("down");
                } else {
                    $('#predicted').addClass("up");
                }
                callback();
            }
        });
    }

    update(timeseries) {
        this.timeseries = timeseries;
        super.update();
    }
}