const ctx = $("#graph");

var myData = [];
let c = 0;
const n = 100;
let start = {x: c, y: 50};
for (var i = 0; i < n; i++) {
	c += 1
	let newPoint = {x: c, y: start.y + Math.random()*10 - 5};
	myData.push(start);
	start = newPoint;
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