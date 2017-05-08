Chart.defaults.NegativeTransparentLine = Chart.helpers.clone(Chart.defaults.line);
Chart.controllers.NegativeTransparentLine = Chart.controllers.line.extend({
  update: function() {
    // Get the min and max values
    var min = Math.min.apply(null, this.chart.data.datasets[0].data.map((d)=>{return d.y}));
    var max = Math.max.apply(null, this.chart.data.datasets[0].data.map((d)=>{return d.y}));
    if (!isFinite(min) || !isFinite(max)) {
        min = -1;
        max = 1
    }

    var yScale = this.getScaleForId(this.getDataset().yAxisID);

    // Figure out the pixels for these and the value 0
    var top = yScale.getPixelForValue(max);
    var zero = yScale.getPixelForValue(0);
    var bottom = yScale.getPixelForValue(min-10); // -10 to make sure goes to x-axis

    // Build a gradient that switches color at the 0 point
    var ctx = this.chart.chart.ctx;
    var gradient = ctx.createLinearGradient(0, top, 0, bottom);
    var ratio = Math.min((zero - top) / (bottom - top), 1);
    gradient.addColorStop(0, 'rgba(0,200,0,0.4)');
    gradient.addColorStop(ratio, 'rgba(0,200,0,0.4)');
    gradient.addColorStop(ratio, 'rgba(200,0,0,0.4)');
    gradient.addColorStop(1, 'rgba(200,0,0,0.4)');
    this.chart.data.datasets[0].backgroundColor = gradient;

    return Chart.controllers.line.prototype.update.apply(this, arguments);
  }
});

class StewardGraph {
	constructor(lineLabel) {
        console.log("Creating graph " + lineLabel);
		this.lineLabel = lineLabel;
		this.defaultLineStyle = {
                yAxisID : 'y-axis-0',
                lineTension: 0,
                label: this.lineLabel,
                pointBorderColor: "rgba(0,0,0,0)",
                pointBackgroundColor: "rgba(0,0,0,0)",
                pointRadius: 10,
                cubicInterpolationMode: "monotone"
        };
        this.predictStyle = Object.assign({}, this.defaultLineStyle);
        this.predictStyle.pointBorderColor = "red";
        this.predictStyle.label = "Predicted";
        this.predictStyle.borderDash = [5,15];
        this.predictStyle.borderColor = "red";
	}

	makeGraph(callback) {
        this.getData(() => {
            const graphData = {
                type: this.redNegative ? 'NegativeTransparentLine' : 'line',
                data: {
                    datasets: this.makeDataSet()
                },
                options: {
                    animation : false,
                    legend: false,
                    responsive: true,
                    maintainAspectRatio: false,
                    title : {
                        display: true,
                        text: this.title
                    },
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
                                suggestedMin: this.min,
                                suggestedMax: this.max,
                                maxRotation: 45,
                                minRotation: 45
                            },
                        }],
                        yAxes: [{
                          scaleLabel: {
                            display: true,
                            labelString: this.yLabel
                          }
                        }]
                    },
                    tooltips: {
                        enabled: true,
                        mode: 'single',
                        callbacks: {
                            title: (info) => { 
                                return this.dateToString(this.labels[info[0].xLabel]);
                            },
                            label: (tooltipItems, data) => {
                                return this.makePretty(tooltipItems.yLabel);
                            }
                        }
                    }      
                }
            }

            this.graph = new Chart(this.ctx, graphData);
            if (callback) {
                callback();
            }
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
        if (this.predict && (this.timeseries == "FIVE_DAY")) {
            let last = this.data[this.data.length - 1];
            ret.push(Object.assign({data: [last, {x: last.x + 15, y: this.predict[1]}]}, this.predictStyle));
            for (let i = 0; i < 15;i++) {
                this.labels.push(new Date(this.predict[0]*1000));
            }
        }

        return ret;
    }

    update() {
        this.getData(() => {
            let callback = () => {
                this.graph.data.datasets = this.makeDataSet();
                if (this.setBounds) {
                    let max = this.max;
                    if (this.timeseries == "FIVE_DAY") {
                        max += 15;
                    }
                    this.graph.config.options.scales.xAxes[0].ticks.min = this.min;
                    this.graph.options.scales.xAxes[0].ticks.max = max;
                }
                this.graph.update(); 
            };
            if(!this.graph) {
                this.makeGraph(callback);
            } else {
                callback();
            }
        });
    }
}
