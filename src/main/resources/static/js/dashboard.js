// Click handler for potfolio
const portfolioClickHandler = (e) => {
    $('.port').removeClass("active");
    $(e.target).addClass("active");
    $.post('/getPortfolio', {name: e.target.innerText}, (resJson) => {
        let data = JSON.parse(resJson);
        $('#stocks').empty();
        // Add stocks
        for (let i = 0; i < data.length; i++) {
            let ticker = data[i]["ticker"];
            let shares = data[i]["shares"];
            $('#stocks').append(`<a href="" class="list-group-item list-group-item-action stock">${ticker} ${shares}</a>`)
        }
    });
    graph.update(e.target.innerText);
}
$('.port').click(portfolioClickHandler);

// Add portfolio button
$('#addPort').click((e) => {
    if($('#newPort').length == 0) {
        $('#ports').append('<div class="list-group-item list-group-item-action port newPort"><input id="newPort" type="text"><p id="portErr" class="text-danger"></p></div>');
        let inputDiv = $('#newPort').parent();
        $('#newPort').keydown((e) => {
            if (e.keyCode == 13) { // Enter
                e.preventDefault();
                let name = $(e.target).val();
                if (name) {
                    $.post('/newPortfolio', {name: name}, (res) => {
                        let resData = JSON.parse(res);
                        if (!resData) {
                            $('#portErr')[0].innerText = "That portfolio already exists";
                        } else {
                            $('.port').removeClass("active");
                            let newPort = $('.newPort');
                            newPort.click(portfolioClickHandler);
                            newPort.removeClass('newPort');
                            newPort.addClass('active');
                            newPort.empty();
                            newPort.append(name);

                            $('#stocks').empty();
                        }
                    });
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

// Stock add
$('#addStock').click((e) => {
    let action;
    if($('#buy').hasClass("active")) {
        action = "buy";
    } else {
        action = "sell";
    }
    let ticker = $('#ticker').val();
    let shares = $('#shares').val();
    let port = $('.port.active')[0].innerText;
    let data = {
        port: port,
        action: action,
        ticker: ticker,
        shares: shares
    }
    let valid = true; // TODO actually validate
    if (valid) {
        $.post('/stockAction', data, (res) => {
            let success = JSON.parse(res);
            if (success) {
                $('#addStockModal').modal('hide');
                // TODO update stocks
            } else {
                // TODO: Show error
            }
        });
    } else {

    }
    return false;
});

class UnrealizedGraph {
    constructor(ctx, port) {
        this.port = port;
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

    update(name) {
        this.port = name;
        this.getData(() => {
            let dataset = this.graph.data.datasets.pop();
            this.graph.data.datasets.push(this.makeDataSet());
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

    getData(callback) {
        $.post('/getUnrealizedData', {name:this.port}, (res) => {            
            let data = JSON.parse(res);
            let labels = [];
            let chartData = [];
            let c = 0;
            for (let i = 0; i < data.length; i++) {
                let p = data[i];
                labels.push(new Date(p[0]*1000));
                chartData.push({x: c, y: p[1]});
                c += 1;
            }
            this.data = chartData;
            this.labels = labels;
            this.min = chartData[0].x;
            this.max = chartData[chartData.length - 1].x;
            callback();
        });
    }

    makeDataSet() {
        return {
                label: 'Unrealized Gains',
                data: this.data,
                pointBorderColor: "black",
                pointBackgroundColor: "rgba(0,0,0,0)",
                pointRadius: 2,
                cubicInterpolationMode: "monotone"
        };
    }
}

let ctx = $('#gains');
let currPort = $('.port.active')[0].innerText;
// Initialize graph with default portfolio
let graph = new UnrealizedGraph(ctx, currPort)