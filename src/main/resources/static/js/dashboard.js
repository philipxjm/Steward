function getStocks(ticker) {
    $.post('/getPortfolio', {name: ticker}, (resJson) => {
        let data = JSON.parse(resJson);
        $('#stocks').empty();
        // Add stocks
        for (let i = 0; i < data.length; i++) {
            let ticker = data[i]["ticker"];
            let shares = data[i]["shares"];
            if (shares > 0) {
                $('#stocks').append(`<a href="" class="list-group-item list-group-item-action stock">${ticker} ${shares}</a>`);
            }
        }
    });
}

function getCurrentPort() {
    return $('.port.active > .portName')[0].innerText;
}

// Click handler for potfolio
const portfolioClickHandler = (e) => {
    if ($(e.target).hasClass("active")) {
        return;
    }

    $('.port').removeClass("active");
    $(e.target).addClass("active");
    const portName = $(e.target).children('.portName')[0].innerText;
    getStocks(portName);
    graph.update(portName);
}
$('.port').click(portfolioClickHandler);

$('.editPort').click((e) => {
    console.log(e.target);
    console.log("HERE");
});

$('.deletePort').click((e) => {
    console.log(e.target);
    console.log("HERE");
});

function makeNewPort(name) {
   return  $(`<div class="list-group-item list-group-item-action active port">         
              <span class="portName">${name}</span>
              <a class="editPort float-right"><i class="fa fa-pencil" aria-hidden="true"></i></a>
              <a class="deletePort float-right"><i class="fa fa-trash" aria-hidden="true"></i></a>
    </div>`);
}

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
                            if( $('#addButton').prop('disabled')) {
                                $('#addButton').prop('disabled', false);
                                $('#noPort').hide();                                
                                // Initialize graph with new portfolio
                                graph = new UnrealizedGraph(ctx, name);
                            }
                            $('.port').removeClass('active');
                            let newPortInput = $('.newPort');
                            let newPort = makeNewPort(name);
                            newPortInput.replaceWith(newPort);
                            newPort.click(portfolioClickHandler);

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

let ctx, graph;
$(()=> {
    ctx = $('#gains');
    if ($('.port').length == 0) {
        $('#addButton').prop('disabled', true);
        $('#noPort').show();
    } else {
        $('#noPort').hide();        
      let name = getCurrentPort();
      graph = new UnrealizedGraph(ctx, name);
    }
});

