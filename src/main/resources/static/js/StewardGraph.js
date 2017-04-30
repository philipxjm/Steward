class StewardGraph {
	constructor(lineLabel) {
		this.lineLabel = lineLabel;
		this.defaultLineStyle = {
                lineTension: 0,
                label: this.lineLabel,
                pointBorderColor: "black",
                pointBackgroundColor: "rgba(0,0,0,0)",
                pointRadius: 2,
                cubicInterpolationMode: "monotone"
        };
        this.predictStyle = Object.assign({}, this.defaultLineStyle);
        this.predictStyle.pointBorderColor = "red";
        this.predictStyle.pointBackgroundColor = "red";
        this.predictStyle.label = "Predicted";
	}
	makeGraph() {
        this.getData(() => {
            const graphData = {
                type: 'line',
                data: {
                    datasets: this.makeDataSet()
                },
                options: {
                    animation : false,
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
                                },
                            },
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

        this.getPredict(() => {
            if(!this.graph) {
                return;
            }
            let dataset = this.graph.data.datasets.pop();
            this.graph.data.datasets = this.makeDataSet();
            this.graph.update();            
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

    makeDataSet() {
        let ret = [];
        ret.push(Object.assign({data: this.data}, this.defaultLineStyle));
        if (this.predict && (this.timeseries == "ONE_DAY" || this.timeseries == "FIVE_DAY")) {
            let last = this.data[this.data.length - 1];
            ret.push(Object.assign({data: [last, {x: last.x + 1, y: this.predict[1]}]}, this.predictStyle));
            this.labels.push(new Date(this.predict[0]*1000));
        }

        return ret;
    }
    update() {
        this.getData(() => {
            let dataset = this.graph.data.datasets.pop();
            this.graph.data.datasets = this.makeDataSet();
            this.graph.update();            
        });
    }
}
