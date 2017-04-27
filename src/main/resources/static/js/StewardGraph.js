class StewardGraph {
	constructor(lineLabel) {
		this.lineLabel = lineLabel;
		this.defaultLineStyle = {
                label: this.lineLabel,
                pointBorderColor: "black",
                pointBackgroundColor: "rgba(0,0,0,0)",
                pointRadius: 2,
                cubicInterpolationMode: "monotone"
        }
	}
	makeGraph() {
        this.getData(() => {
            const graphData = {
                type: 'line',
                data: {
                    datasets: [this.makeDataSet()]
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

    makeDataSet() {
        return Object.assign({data: this.data}, this.defaultLineStyle);
    }
    update() {
        this.getData(() => {
            let dataset = this.graph.data.datasets.pop();
            this.graph.data.datasets.push(this.makeDataSet());
            this.graph.update();            
        });
    }
}
