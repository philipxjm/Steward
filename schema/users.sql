BEGIN TRANSACTION;
CREATE TABLE "UserPortfolios" (
	`PortfolioId`	TEXT NOT NULL UNIQUE,
	`Name` TEXT,
	`UserId` TEXT NOT NULL,
	`PoolId` TEXT,
	PRIMARY KEY(`PortfolioId`),
-- 	you made this change recently, if things break try to remove this line!
	FOREIGN KEY (`UserId`) REFERENCES `Users`(`UserId`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PoolId`) REFERENCES `Pools`(`PoolId`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE "Pools" (
	`PoolId` TEXT NOT NULL UNIQUE,
	`Name` TEXT NOT NULL,
	`Balance` INTEGER NOT NULL,
	`Start` INTEGER NOT NULL,
	`End` INTEGER,
	PRIMARY KEY(`PoolId`)
);
CREATE TABLE "History" (
	`portfolio`	TEXT NOT NULL,
	`stock`	TEXT NOT NULL,
	`time`	INTEGER NOT NULL,
	`trans`	INTEGER NOT NULL,
	`price`	REAL NOT NULL,
	FOREIGN KEY(`portfolio`) REFERENCES `UserPortfolios`(`PortfolioId`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE "Balances" (
	`portfolio`	TEXT NOT NULL UNIQUE,
	`balance`	REAL NOT NULL DEFAULT 1000000,
	PRIMARY KEY(`portfolio`),
	FOREIGN KEY(`portfolio`) REFERENCES `UserPortfolios`(`PortfolioId`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE "Users" (
	`UserId`	TEXT,
	`Name`	TEXT,
	`Pic`	TEXT,
	`Email`	TEXT,
	PRIMARY KEY(`UserId`)
);
CREATE TABLE "Leaderboards" (
	`pool`	TEXT NOT NULL,
	`portfolio`	TEXT NOT NULL,
	`score`	REAL NOT NULL,
	FOREIGN KEY(`pool`) REFERENCES `Pools`(`PoolId`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`portfolio`) REFERENCES `UserPortfolios`(`PortfolioID`) ON DELETE CASCADE ON UPDATE CASCADE,
	UNIQUE (`pool`, `portfolio`)
);
COMMIT;
