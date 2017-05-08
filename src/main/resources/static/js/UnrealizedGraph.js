class UnrealizedGraph extends StewardGraph {
    constructor(ctx, port) {
        console.log("MAKING NEW UNREALIZED GRAPH");
        super("Unrealized Gains");
        this.ctx = ctx;
        this.port = port;
        this.timeseries = "none";
        this.redNegative = true;
        super.makeGraph();
        this.yLabel = "% Gain";
        this.title = "Unrealized Gains";
        this.setBounds = true;
    }

    makePretty(v) {
        return Math.round(v*100)/100 + '%';
    }

    update(name) {
        this.port = name;
        super.update();      
    }

    getData(callback) {
        let data = { name: this.port };

        if (this.lastRequest) {
            this.lastRequest.abort();
        }
        log('/getUnrealizedData', data);
        this.lastRequest = $.post('/getUnrealizedData', data, (res) => {  
            let data = JSON.parse(res);
            let labels = [];
            let chartData = [];
            let c = 0;
            for (let i = 0; i < data.length; i++) {
                let p = data[i];
                labels.push(new Date(p[0]*1000));
                chartData.push({x: c, y: p[1]});
                c += 1;
            }
            this.data = chartData;
            this.labels = labels;
            if (chartData.length > 0) {
                this.min = chartData[0].x;
                this.max = chartData[chartData.length - 1].x;
            } else {
                //this.min = 0-1;
                //this.max = 1;
            }
            callback();
        });
    }

    getPredict(){}
}