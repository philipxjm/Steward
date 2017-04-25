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

let ctx = $('#gains');
let currPort = $('.port.active')[0].innerText;
// Initialize graph with default portfolio
let graph = new UnrealizedGraph(ctx, currPort)