const ctx = $("#graph");
let scatterChart;
function makeGraph() {    
    const data = {
        type: 'line',
        data: {
            //labels: labels,
            datasets: [{
                label: 'Stock Prices',
                data: pastData,
                borderColor: 'red'
            }/*, {
                label: 'Predict',
                data: predict,
                borderColor: 'grey'
            }*/]
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
                        min: min,
                        max: max,
                        callback: function(value) { 
                            console.log()
                            return labels[value].toDateString();
                        }
                    }
                }]
            },
        }
    }

    scatterChart = new Chart(ctx, data);
}

let n = 100;
let c = 50;
const ticker = $('h2')[0].innerText;

let params = {
    "ticker" : ticker,
    "timeseries" : "FIVE_DAY"
};

let pastData = null;
let predict = null;
let min = null;
let max = null;

let labels = [];
$.post('/getGraphData', params, (res) => {
    let resData = JSON.parse(res);
    pastData = [];
    let last = resData[0];
    let c = 0;

    for(let p of resData) {
        if(p[0] - last > 1000) {
            pastData.push({x: NaN, y: NaN})
        }
        last = p[0];
        labels.push(new Date(p[0]*1000));
        pastData.push({x: c, y: p[1]});
        c += 1;
    }

    min = pastData[0].x;
    max = pastData[pastData.length-1].x;

    makeGraph();
});