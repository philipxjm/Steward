// Gets stocks for portfolio
function getStocks(name, callback) {
    let data = {name: name, isPool: !activeTabIsPort};
    log('/getPortfolioStocks', data);
    $.post('/getPortfolioStocks', data, (resJson) => {
        let data = JSON.parse(resJson);
        // Empty out old stocks
        $('#stocks').empty();

        // Add stocks
        for (let i = 0; i < data.length; i++) {
            let ticker = data[i]["ticker"];
            let shares = data[i]["shares"];
            let currPrice = data[i]["currPrice"].price;
            let dailyChange = data[i]["change"].dailyChange;
            let color = 'down';
            if (dailyChange > 0) {
                color = 'up';
            }
            let shareText = 'share';
            if (shares > 1) {
                shareText += 's';
            }
            // Don't show stocks if they don't have any shares
            if (shares > 0) {
                $('#stocks').append(`
                <a href="/stock/${ticker}" class="list-group-item list-group-item-action stock">
                    <div class="float-left">
                        <div class="fullWidth">${ticker} ${shares} ${shareText}</div>
                        <div>$${currPrice} <span class="${color}">(${dailyChange}%)</span></div>
                    </div>
                    <!--
                    <div class="checkboxDiv">
                        <label id="customCheck" class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input">
                            <span class="custom-control-indicator"></span>
                        </label>
                    </div>-->
                </a>`);
            }
        }
        if (callback) {
            callback();
        }
    });
}

// Gets name of active portfolio
function getCurrentPort() {
    if (activeTabIsPort) {
        return $('.port.active > .portName').text();
    } else {
        return $('.pool.active > .portName').text();
    }
}

// Click handler for portfolio
const portfolioClickHandler = (e) => {
    let elm = $(e.target);

    // Delete portfolio
    if(elm.hasClass("deletePort")) {
        deletePortfolio(elm.parent());
        if ($('.ports').length == 0) {
            $('#portGraph').hide();
        }
        return false;
    }

    // If text look at parent
    if(elm.hasClass("portName")) {
        elm = elm.parent();
    }

    // Edit name
    if(elm.hasClass("editPort")) {
        $('#addPort').prop('disabled', true);
        $('.disabler').prop('disabled', true);

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
                if (oldName == name || !name) { // Remove
                    finishRename($input, oldName);
                } else {
                    let data = {old: oldName, new: name};
                    log('/renamePortfolio', data);
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

    // Don't do anything if it's already active
    if (elm.hasClass("active")) {
        return false;
    }

    // Switch active
    $('.port').removeClass("active");
    elm.addClass("active");
    const portName = getCurrentPort();
    getStocks(portName);
    portGraph.update(portName);
}
$('.port').click(portfolioClickHandler);

// Tidy's up when rename of portfolio is done
function finishRename($input, name) {
    let elm = makeNewPort(name);
    $input.parent().replaceWith(elm);
    elm.click(portfolioClickHandler);
    elm.click();
    $('#addPort').prop('disabled', false);
    $('.disabler').prop('disabled', false);
}

// Delete portfolio for elm
function deletePortfolio(elm) {
    let name = elm.children('.portName')[0].innerText;
    let data = {name:name};
    log('/deletePortfolio', data);
    $.post('/deletePortfolio', data, (res) => {
        let success = JSON.parse(res);
        if (success) {
          elm.remove();
        }
        let next = $('.port')[0];
        if (!next) {
            showEmptyMessage(true);
            $('#noPort').show();
            $('#portGraph').hide();
            $('#stocks').empty();
            $('.disabler').prop('disabled', true);            
        } else {
            $($('.port')[0]).click();
             $('#portGraph').show();
            portGraph.update(getCurrentPort())
        }
    });
}

// Returns new jQuery obj for the name of a portfolio
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
        $('#ports').append('<div class="list-group-item list-group-item-action port newPort"><input class="form-control" id="newPort" type="text"><p id="portErr" class="text-danger"></p></div>');
        let inputDiv = $('#newPort').parent();
        $('#newPort').keydown((e) => {
            if (e.keyCode == 13) { // Enter
                e.preventDefault();
                let name = $(e.target).val();
                if (name) {
                    let data = {name: name};
                    log('/newPortfolio', data);
                    $.post('/newPortfolio', data, (res) => {
                        let resData = JSON.parse(res);
                        if (!resData) {
                            $('#portErr')[0].innerText = "That portfolio already exists";
                        } else {
                            if( $('.disabler').prop('disabled')) {
                                $('.disabler').prop('disabled', false);
                                $('#noPort').hide(); 
                                $('#gains').show();      
                                // Initialize graph with new portfolio
                                if (!portGraph) {
                                    portGraph = new UnrealizedGraph(portCtx, name);
                                }
                            }
                            $('.port').removeClass('active');
                            let newPortInput = $('.newPort');
                            let newPort = makeNewPort(name);
                            newPortInput.replaceWith(newPort);
                            newPort.click(portfolioClickHandler);
                            newPort.click();
                            $('#stocks').empty();
                            $('#portGraph').show();
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

// Global vars for graph and ctx
let poolCtx, poolGraph, portCtx, portGraph;

// Initialize graph
$(()=> {
    portCtx = $('#portGraph');
    poolCtx = $('#poolGraph');

    if(window.location.href.endsWith("#pool")) {
        $('#clickForPool').click();
    } else {
        loadUpDashType(true);
    }
    $('.port').removeClass("active");
    $('#ports > .port').first().click();
});

// Sets html for empty msg and shows
function showEmptyMessage(port) {
    if (port) {
        $('#noPort').html("<h2>You don't have any portfolios!</h2><p>Create a new " +
        "portfolio to keep track of your holdings or any stocks you have an " +
        "eye on. Check our <a href = '/watchlist'>Watchlist</a> for an " +
        "idea of which stocks to buy. </p>");
    } else {
        $('#poolInfo').hide();
        console.log($('#noPort').html());
        $('#noPort').html("<h2>You're not in any pools!</h2><p>Want to face off against your friends? Create a new pool, or join a friend's. Start buying and see who can make the most cash.</p>");        
    }
    $('#noPort').show();
}

function loadUpDashType(port) { 
    if (port) {
        $('#modalCB').hide();
        $('#modalB').hide();
        // Show checkbox & time for past action
        $('#pastActionLabel').show();
        // Hide poolInfo (leaderboard, etc.)
        $('#poolInfo').hide();  
        // Hide pool & show port Graph
        $('#poolGraph').hide();
        $('#portGraph').hide();
        // If no ports shown empty message, hide graph, and disable buttons
        if ($('.port').length == 0) {
            $('.disabler').prop('disabled', true);
            $('#portGraph').hide();
            showEmptyMessage(true);
        } else {
            // Clear the empty msg
            $('#noPort').html('');
            // Reenable buttons
            $('.disabler').prop('disabled', false);  
            // Show graph
            $('#portGraph').show();    
            let name = getCurrentPort();
            if (!portGraph) {
                portGraph = new UnrealizedGraph(portCtx, name);
            }
            // Click active to update dash center
            $('#ports > .port').first().click();
        }
        history.pushState({}, "", "#");
    } else {
        $('#modalCB').show();
        $('#modalB').show();
        // Hide checkbox & time for past action
        $('#pastActionLabel').hide();
        $('#time').hide();
        // Hide poolInfo (leaderboard, etc.)
        $('#poolInfo').show();
        // Show pool & hide port Graph
        $('#poolGraph').hide();
        $('#portGraph').hide(); 
        // If no pools shown empty message, hide graph, and disable buttons
        if (($('.pool').length - $('.newPool').length) == 0) {
            $('.disabler').prop('disabled', true);
            $('#poolGraph').hide();
            showEmptyMessage(false);
        } else {
            // Clear the empty msg
            $('#noPort').html('');
            // Reenable buttons
            $('.disabler').prop('disabled', false);  
            // Show graph
            $('#poolGraph').show(); 
            let name = getCurrentPort();
            if (!poolGraph) {
                let poolId = $('#pools > .pool').first().attr("poolId");
                poolGraph = new BalanceGraph(poolCtx, name, poolId);
            }
            $('#pools > .pool').first().click();
        }         
        history.pushState({}, "", "#pool");
    }
}

let activeTabIsPort = true;
// Called on tab switch
$('.tabToggle').click((e) => {
    let port = (e.target.innerText == "Portfolios");
    console.log("ATP: " + activeTabIsPort);
    if (port == activeTabIsPort) {
        return;
    }
    activeTabIsPort = port;
    $('#stocks').empty();
    if (activeTabIsPort) {
        $('.port').removeClass('active');
    } else {
        $('.pool').removeClass('active')
    }
    loadUpDashType(port);
});

// Hide leaderboard initially
$('#poolInfo').hide();

// Hide extra modal info
$('#modalCB').hide();
$('#modalB').hide();

// Transaction hist
$('#historyButton').click((e) => {
    let data = { port : getCurrentPort(), isPool : !activeTabIsPort };

    $.post('/getTransactionHistory', data, (res) => {
        let resData = JSON.parse(res);
        let l = [];
        for (let key of Object.keys(resData)) {
            l.push(resData[key]);
        }
        l.sort((a,b) => a.time > b.time);

    });
});