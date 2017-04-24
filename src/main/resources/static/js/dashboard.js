function newPortfolio (int, str) {
	$.post("/addPortfolio", {index : int, name : str});
}

function getPortfolios () {
	$.post("/portfolios", {}, responseJSON => {
		console.log(responseJSON);
	});
}

$('.port').click((e) => {
    $('.port').removeClass("active");
    $(e.target).addClass("active");
    // TODO: Get stocks & update graph
});

// TODO initialize graph

let ctx = $('#gains');

const graphData = {
    type: 'line',
    data: {
        datasets: []
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
                    /*callback: (value) => { 
                        if (this.labels[value]) {
                            return this.dateToString(this.labels[value], false);
                        } else {
                            return "";
                        }
                    }*/
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

let graph = new Chart(ctx, graphData);