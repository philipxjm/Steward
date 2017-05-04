class Autocorrect {
	constructor($toInsert, correcter) {
		if (correcter) {
			this.correcter = correcter;
		} else {
			this.correcter = 'default';
		}
		this.myId = Autocorrect.idIndex;
		Autocorrect.idIndex++;
		let $input = $('<input class="autoInp formControl" id="inp' + this.myId + '" placeholder="Search for..." type="text" autocomplete="off">');
		let $button = $(`<span class="input-group-btn">
			<button id="searchButton" class="btn btn-secondary" type="button">
			<i class="fa fa-search" aria-hidden="true"></i>
			</button></span>`);
		let $dropdown = $('<ul class="dropdown-menu autoDropdown" id="dropdown' + this.myId + '"></ul>');
		$toInsert.append($input);
		$toInsert.append($button);
		$toInsert.append($dropdown);
		$button.click((e) => {
			let search = $input.val();
			if (search) {
				window.location = "/stock/"+search;
			}
		});		
		// -1 indicates no suggestions highlighted
		this.optInd = -1;
		// Buffer that holds that the user is typing
		this.buf = "";

		// Input element
		const inp = $("#inp" + this.myId);
		// Suggestion box
		const dropdown = $("#dropdown" + this.myId);

		// Keydown event handler
		var ths = this;
		inp.keydown(function(e) {keyDownHandler(e, ths);});

		// On user type
		inp.on("input", function() { updateSuggestions(ths); });
		inp.on("blur", function(e) { blurHandler(ths, e); });
	}

	// Function to update highlighting of suggestions and text in input
	updateUI() {
		const options = $('.option' + this.myId);
		const inp = $("#inp" + this.myId);
		// For each suggestions
		for (let i = 0; i < options.length; i++) {
			// If its currently selected make it blue and set input text
			if (i == this.optInd) {
				options[i].style["background-color"] = "lightblue";
				if (this.optInd != -1) {
					inp.val($(options[i]).data("ticker"));
				}
			} else {
				// Otherwise make it white
				options[i].style["background-color"] = "white";
			}
		}
		// If none highlighted, set the input to what the user was typing
		if (this.optInd == -1) {
			inp.val(this.buf);
		}
	}

	getElement() {
		return $('#inp'+this.myId);
	}
}

/*
To whoever reads this, I am very sorry. I had trouble figuring out the
complexities of closures on `this` so I just brought everything out which is
horrible and ugly but it works?
*/

// Highlights suggestion on hover
function hoverHandler(e, ths) {
	const inp = $("#inp" + ths.myId);
	let txt = $(e.target).data("ticker");

	inp.val(txt);
	ths.optInd = $('.option' + ths.myId).index(e.target);
	ths.updateUI();
}

// Sets input to hovered selection on click
function clickHandler(e, ths) {
	const dropdown = $("#dropdown" + ths.myId);
	let txt = $(e.target).data("ticker");

	const inp = $("#inp" + ths.myId);
	ths.buf = txt;

	// Hide suggestions until user starts typing again
	dropdown.css("display", "none");
	// Focus back on input
	inp.focus();
}

function updateSuggestions(ths) {
	// Set buffer to current input
	let inp = $("#inp" + ths.myId);
	ths.buf = inp.val();
	// Set highlight to none
	ths.optInd = -1;
	if(!inp.val()) {
		$("#dropdown" + ths.myId).css("display", "none");
		return;
	}
	// Request suggestions
	$.post("/suggest", {"input": inp.val().toLowerCase()}, function (data) {
		// Parse JSON
		data = JSON.parse(data);
		const dropdown = $("#dropdown" + ths.myId);
		// Clear suggestions
		dropdown.html("");
		// Add each suggestion as a <li>
		for (var i = 0; i < data.length; i++) {
			let suggest = data[i];
			var newLi = $(`<li class="option`+ths.myId+`">${suggest[0]} - ${suggest[1]}</li>`)
			newLi.data("ticker", suggest[0]);
			// Attach handlers
			newLi.on("mouseenter", function(e) {hoverHandler(e, ths);});
			newLi.on("mousedown", function(e) {clickHandler(e, ths);});
			dropdown.append(newLi);
		}
		// If no suggestions hide 
		if (dropdown.children == 0) {
			dropdown.css("display", "none");
		} else {
			dropdown.css("display", "block")
		}
	});
}

function blurHandler(ths, e) {
	const inp = $('#inp' + ths.myId);
	const options = $('.option' + ths.myId);
	const dropdown = $('#dropdown' + ths.myId);
	// Set input to this option
	if (ths.optInd != -1) {
		ths.buf = $(e.target).data("ticker");
		inp.val(ths.buf);
	}
	// Hide suggestions until user starts typing again
	dropdown.css("display", "none");
}

function keyDownHandler(e, ths) {
	const options = $('.option' + ths.myId);
	const inp = $('#inp' + ths.myId);
	const dropdown = $('#dropdown' + ths.myId);
	// Update buffer
	if (ths.optInd == -1) {
		ths.buf = inp.val();
	}

	switch(e.which) {
		case 38: // Up
			ths.optInd -= 1;
			if (ths.optInd < -1) {
				ths.optInd = options.length - 1;
			}
			break;
		
		case 40: // Down
			ths.optInd += 1;
			if (ths.optInd >= options.length) {
				ths.optInd = -1;
			}
			break;

		case 13: // Return
			// Set input to this option
			if (ths.optInd != -1) {
				ths.buf = $(options[ths.optInd]).data("ticker");
				inp.val(ths.buf);
			}
			// Hide suggestions until user starts typing again
			dropdown.css("display", "none");
			e.preventDefault();
			$('#searchButton').click();			
		case 27:
			dropdown.css("display", "none");
		default: return; // Ignore everything else
	}

	e.preventDefault();
	// Redo highlight
	ths.updateUI();
}
Autocorrect.idIndex = 0