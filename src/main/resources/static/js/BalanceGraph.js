class BalanceGraph extends StewardGraph {
	constructor(ctx, port, poolId) {
		super("Balance");
        console.assert(poolId != null);
		this.ctx = ctx;
		this.poolId = poolId;
		this.port = port;
        this.timeseries = "none";
        this.redNegative = false;
        super.makeGraph();
        this.yLabel = "Total Value ($)";
        this.title = "Total Unrealized Value of Portfolio";
        this.setBounds = true;
	}

	makePretty(v) {
        return '$' + Math.round(v*100)/100;
    }

    getColors(n) {
        let ret = [];
        for (let i = 0; i < n; i++) {
            let val = 360 * i / n;
            let color = `hsl(${val}, 70%, 50%)`;
            ret.push(color);
            let bg = `hsla(${val}, 70%, 50%, 0.5)`;
            let id = this.users[i];
            console.log(`#user${id} ` + `${color}`);
            var style = $(`<style>#user${id} { background-color: ${bg}; }</style>`);
            $('html > head').append(style);
        } 
        return ret;
    }

    makeDataSet() {
        let ret = [];
        let colors = this.getColors(this.data.length);
        for (let i = 0; i < this.data.length; i++) {
            console.log(colors);
            let style = Object.assign({data: this.data[i]}, this.defaultLineStyle);
            style.borderColor = colors[i];
            style.pointBackgroundColor = colors[i];
            ret.push(style);
        }

        return ret;
    }

    update(name, poolId) {
        this.port = name;
        if (poolId != null) {
            this.poolId = poolId;
        }
        super.update();      
    }

    getData(callback) {
        let url = '/getNetWorthGraph';
        let data = { poolId: this.poolId };

        console.assert(this.poolId != null);
        if (this.lastRequest) {
            this.lastRequest.abort();
        }
        log(url, data);
        this.lastRequest = $.post(url, data, (res) => {
            let data = JSON.parse(res);
            let users = [];
            let datasets = [];
            let labels = [];
            let first = true;
            let c;
            for (let hist of data) {
                users.push(hist.user);
                c = 0;
                let chartData = []
                for (let p of hist.balance) {
                    if (first) {
                        labels.push(new Date(p[0]*1000));
                    }
                    chartData.push({x: c, y: p[1]});
                    c += 1;
                }
                first = false;
                datasets.push(chartData);
            }
            this.users = users;
            this.data = datasets;
            this.labels = labels;
            if (this.data[0].length > 0) {
                this.min = 0;
                this.max = c-1;
            }
            callback();
        });
    }

    getPredict(){}
}