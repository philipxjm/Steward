const ctx = $("#graph");

let n = 100;
let c = 50;
let params = {
    "ticker" : 'AAPL',
    "start" : 0,
    "end" : n
};

$.post('/getStockData', params, (res) => {
    let resData = JSON.parse(res);
    let myData = [];
    for(let p of resData) {
        myData.push({x: p[0], y: p[1]});
    }
    
    var predict = [];
    start = myData[myData.length - 1];
    for (var i = 0; i < n / 10; i++) {
        let newPoint = {x: c, y: start.y + 1};
        predict.push(start);
        start = newPoint;
        c += 1
    }

    const data = {
        type: 'line',
        data: {
            datasets: [{
                label: 'Stock Prices',
                data: myData,
                borderColor: 'red'
            }, {
                label: 'Predict',
                data: predict,
                borderColor: 'grey'
            }]
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
                        min: 0,
                        max: c
                    }
                }]
            }
        }
    }

    const scatterChart = new Chart(ctx, data);
});