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
    let elm = $(e.target);
    if(elm.hasClass("deletePort")) {
        deletePortfolio(elm.parent());
        return false;
    }

    if(elm.hasClass("portName")) {
        elm = elm.parent();
    }

    if(elm.hasClass("editPort")) {
        $('#addPort').prop('disabled', true);
        $('#addButton').prop('disabled', true);

        let parent = elm.parent();
        parent.click();
        let oldName = getCurrentPort();
        parent.empty();
        parent.append($('<input id="renamePort" type="text"><p id="portErr" class="text-danger"></p>'));
        let $input = $('#renamePort');
        $input.val(oldName);
        $input.focus();
        $input.keydown((e) => {
            if (e.keyCode == 13) { // Enter
                e.preventDefault();
                let name = $(e.target).val();
                if (oldName == name || !name) {
                                        // Remove
                    finishRename($input, oldName);
                } else {
                    $.post('/renamePortfolio', {old: oldName, new: name}, (res) => {
                        let resData = JSON.parse(res);
                        if (!resData) {
                            $('#portErr')[0].innerText = "That portfolio already exists";
                        } else {
                            finishRename($input, name);
                        }
                    });
                }
            } else if (e.keyCode == 27) { // Escape
                e.preventDefault();
                // Remove
                finishRename($input, oldName);
            }
        });
        return false;
    }

    if (elm.hasClass("active")) {
        return false;
    }

    $('.port').removeClass("active");
    elm.addClass("active");
    const portName = elm.children('.portName')[0].innerText;
    getStocks(portName);
    graph.update(portName);
}
$('.port').click(portfolioClickHandler);

function finishRename($input, name) {
    let elm = makeNewPort(name);
    $input.parent().replaceWith(elm);
    elm.click(portfolioClickHandler);
    elm.click();
    $('#addPort').prop('disabled', false);
    $('#addButton').prop('disabled', false);
}

function deletePortfolio(elm) {
    let name = elm.children('.portName')[0].innerText;
    $.post('/deletePortfolio', {name:name}, (res) => {
        let success = JSON.parse(res);
        if (success) {
          elm.remove();
        }
        let next = $('.port')[0];
        if (!next) {
            if(!$('#noPort')[0]) {
                $('#graphContainer').append($('<h2 id="noPort" class="text-muted">Make a new portfolio!</h2>'));
            }
            $('#noPort').show();
            $('#gains').hide();
            $('#stocks').empty();
        } else {
            $($('.port')[0]).click();
        }
    });
}

function makeNewPort(name) {
   let ret = $(`<div class="list-group-item list-group-item-action port">         
              <span class="portName">${name}</span>
              <a class="actionButton editPort float-right fa fa-pencil" aria-hidden="true"></a>
              <a class="actionButton deletePort float-right fa fa-trash" aria-hidden="true"></a>
          </div>`);
   return ret;
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
                            if( $('#addButton').prop('disabled')) {
                                $('#addButton').prop('disabled', false);
                                $('#noPort').hide(); 
                                $('#gains').show();                               
                                // Initialize graph with new portfolio
                                graph = new UnrealizedGraph(ctx, name);
                            }
                            $('.port').removeClass('active');
                            let newPortInput = $('.newPort');
                            let newPort = makeNewPort(name);
                            newPortInput.replaceWith(newPort);
                            newPort.click(portfolioClickHandler);
                            newPort.click();                            
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

