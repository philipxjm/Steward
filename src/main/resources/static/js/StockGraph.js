class StockGraph extends StewardGraph {
    constructor(ctx, ticker, timeseries) {
        super("Stock Price");
        this.ctx = ctx;
        this.timeseries = timeseries;
        this.ticker = ticker;
        this.redNegative = false;
        super.makeGraph();
        this.yLabel = "Share Price ($)";
        this.title = "";
        this.setBounds = true;
        this.predictRequest = false;
    }

    makePretty(v) {
        return '$' + Math.round(v*100)/100;
    }

    getData(callback) {
        const params = {
            "ticker" : this.ticker,
            "timeseries" : this.timeseries
        }

        if (this.lastRequest) {
            this.lastRequest.abort();
        }
        log('/getGraphData', params);
        this.lastRequest = $.post('/getGraphData', params, (res) => {
            const resData = JSON.parse(res);

            let pastData = [];
            let labels = [];
            let last = resData[0];
            let c = 0;

            for(let p of resData) {
                if(p[0] - last > 3000 && (this.timeseries == "ONE_DAY" || this.timeseries == "FIVE_DAY")) {
                    c += 1;
                    pastData.push({x: NaN, y: NaN})
                    labels.push(new Date(p[0]*1000));
                }
                last = p[0];
                labels.push(new Date(p[0]*1000));
                pastData.push({x: c, y: p[1]});
                c += 1;
            }
            let curPrice =  parseFloat($('#price').text().substring(1));
            pastData.push({x: c, y: curPrice})
            labels.push(new Date());

            this.data = pastData;
            this.labels = labels;
            this.min = pastData[0].x;
            this.max = pastData[pastData.length-1].x;
            callback();       
        });
    }

    getPredict(callback) {
        if (predictCalled) {
            return;
        }
        predictCalled = true;
        let params = {
            "ticker": this.ticker
        }
        log('/getStockPrediction', params);

        if (this.predictRequest) {
            return;
        }

        this.predictRequest = $.post('/getStockPrediction', params, (res) => {
            let data = JSON.parse(res);
            if (data) {
                this.predict = data;
                $('#predicted').append("Predicted: <span id='predictedPrice'>$" + data[1] + '</span>');
                let curPrice = Number.parseFloat($('#price')[0].innerText.substr(1));
                if (curPrice > data[1]) {
                   $('#predictedPrice').addClass("down");
                } else {
                    $('#predictedPrice').addClass("up");
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