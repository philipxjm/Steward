function getStocks(ticker) {
    $.post('/getPortfolio', {name: ticker}, (resJson) => {
        let data = JSON.parse(resJson);
        $('#stocks').empty();
        // Add stocks
        for (let i = 0; i < data.length; i++) {
            let ticker = data[i]["ticker"];
            let shares = data[i]["shares"];
            $('#stocks').append(`<a href="" class="list-group-item list-group-item-action stock">${ticker} ${shares}</a>`)
        }
    });
}

// Click handler for potfolio
const portfolioClickHandler = (e) => {
    if ($(e.target).hasClass("active")) {
        return;
    }

    $('.port').removeClass("active");
    $(e.target).addClass("active");
    const portName = e.target.innerText;
    getStocks(portName);
    graph.update(portName);
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

let ctx = $('#gains');
let currPort = $('.port.active')[0].innerText;
// Initialize graph with default portfolio
let graph = new UnrealizedGraph(ctx, currPort)