const ctx = $("#graph");

function makeGraph() {
    const data = {
        type: 'line',
        data: {
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
                        max: max
                    }
                }]
            }
        }
    }

    const scatterChart = new Chart(ctx, data);
}

let n = 100;
let c = 50;
const ticker = $('h2')[0].innerText;

let params = {
    "ticker" : ticker,
    "timeseries" : "ONE_DAY"
};

let pastData = null;
let predict = null;
let min = null;
let max = null;

$.post('/getGraphData', params, (res) => {
    let resData = JSON.parse(res);
    pastData = [];
    for(let p of resData) {
        pastData.push({x: p[0], y: p[1]});
    }

    min = pastData[0].x;
    max = pastData[pastData.length-1].x;

    makeGraph();
});