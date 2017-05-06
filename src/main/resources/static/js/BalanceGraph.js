class BalanceGraph extends StewardGraph {
	constructor(ctx, port, poolId) {
		super("Balance");
		this.ctx = ctx;
		this.poolId = poolId;
		this.port = port;
        this.timeseries = "none";
        this.redNegative = false;
        super.makeGraph();
        this.yLabel = "Total Value ($)";
        this.title = "Total Unrealized Value of Portfolio";
	}

	static makePretty(v) {
        return '$' + Math.round(v*100)/100;
    }

    update(name, poolId) {
        this.port = name;
        this.poolId = poolId;
        super.update();      
    }

    getData(callback) {
        let url = '/getNetWorthGraph';
        let data = { poolId: this.poolId };
        console.assert(this.poolId != null);

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
            }
            callback();
        });
    }

    getPredict(){}
}