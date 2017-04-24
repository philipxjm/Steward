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
    $.post('/getPortfolio', {name: e.target.innerText}, (resJson) => {
        let data = JSON.parse(resJson);
        $('#stocks').empty();
        for (let i = 0; i < data.length; i++) {
            let ticker = data[i]["ticker"];
            let shares = data[i]["shares"];
            $('#stocks').append('<div class="list-group-item list-group-item-action port <#if port_index == 0>active</#if>">${port.name}</div>')
        }
    });
    // TODO: Get stocks & update graph
});

// TODO initialize graph

// Add portfolio button
$('#addPort').click((e) => {
    $('#ports').append('<div class="list-group-item list-group-item-action port"><input id="newPort" type="text"></div>');
    $('#newPort').keydown((e) => {
        e.preventDefault();
        console.log(e);
        if (e.charCode == 13) { // Enter
            let name = $(e.target).val();
            if (name) {
                // Add new port w name
            } else {
                // Remove
            }
        } else if (e.charCode == 0) { // Escape
            // Remove
        }
    });
    $('#newPort').focus();
});

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