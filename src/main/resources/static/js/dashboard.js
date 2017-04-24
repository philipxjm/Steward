// Click handler for potfolio
$('.port').click((e) => {
    $('.port').removeClass("active");
    $(e.target).addClass("active");
    $.post('/getPortfolio', {name: e.target.innerText}, (resJson) => {
        let data = JSON.parse(resJson);
        $('#stocks').empty();
        // Add stocks
        for (let i = 0; i < data.length; i++) {
            let ticker = data[i]["ticker"];
            let shares = data[i]["shares"];
            $('#stocks').append(`<div class="list-group-item list-group-item-action stock">${ticker} ${shares}</div>`)
        }
    });
    // TODO: Get stocks & update graph
});

// TODO: initialize graph

// Add portfolio button
$('#addPort').click((e) => {
    if($('#newPort').length == 0) {
        $('#ports').append('<div class="list-group-item list-group-item-action port"><input id="newPort" type="text"></div>');
        let inputDiv = $('#newPort').parent();
        $('#newPort').keydown((e) => {
            if (e.keyCode == 13) { // Enter
                e.preventDefault();
                let name = $(e.target).val();
                if (name) {
                    // if name in list => error
                    // else create
                    // TODO: Add new port w name

                } else {
                    // Remove
                   inputDiv.remove();
                }
            } else if (e.keyCode == 27) { // Escape
                e.preventDefault();
                // Remove
                inputDiv.remove();
            }
        });
        $('#newPort').focus();
    }
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