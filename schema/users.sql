CREATE TABLE `UserPortfolios` (
	`PortfolioId`	TEXT NOT NULL UNIQUE,
	`Name`	TEXT,
	`UserId`	TEXT NOT NULL,
	PRIMARY KEY(`PortfolioId`)
);
CREATE TABLE `History` (
	`portfolio`	TEXT NOT NULL UNIQUE,
	`stock`	TEXT NOT NULL UNIQUE,
	`time`	INTEGER NOT NULL,
	`transaction`	INTEGER NOT NULL,
	`quantity`	INTEGER NOT NULL CHECK(Quantity >= 0),
	`price`	REAL NOT NULL,
	FOREIGN KEY(`portfolio`) REFERENCES `UserPortfolios`(`Portfolio`) ON DELETE CASCADE ON UPDATE CASCADE
);