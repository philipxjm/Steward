
function newPortfolio (int, str) {
	$.post("/addPortfolio", {index : int, name : str});
}

function getPortfolios () {
	$.post("/portfolios", {}, responseJSON => {
		console.log(responseJSON);
	});
}