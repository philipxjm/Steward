class UnrealizedGraph extends StewardGraph {
    constructor(ctx, port) {
        super("Unrealized Gains");
        this.port = port;
        this.timeseries = "none";
        this.redNegative = true;
        super.makeGraph();
    }

    static makePretty(v) {
        return Math.round(v*100)/100 + '%';
    }

    update(name) {
        this.port = name;
        super.update();      
    }

    getData(callback) {
        let url, data;
        if (activeTabIsPort) {
            url = '/getUnrealizedData';
            data = { name: this.port };
        } else {
            url = '/getNetWorthGraph';
            data = { id: this.poolId };
        }
        console.log(data);
        $.post(url, data, (res) => {  
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
                this.min = -1;
                this.max = 1;
            }
            callback();
        });
    }

    getPredict(){}
}