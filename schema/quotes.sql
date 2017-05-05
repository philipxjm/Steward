BEGIN TRANSACTION;
CREATE TABLE `thirtyMin` (
	`stock`	TEXT NOT NULL,
	`time`	INTEGER NOT NULL,
	`price`	INTEGER NOT NULL,
	UNIQUE (`stock`, `time`)
);
CREATE TABLE `quotes` (
	`stock`	TEXT NOT NULL,
	`time`	INTEGER NOT NULL,
	`price`	REAL NOT NULL,
	UNIQUE (`stock`, `time`)
);
CREATE TABLE "fiveMin" (
	`stock`	TEXT NOT NULL,
	`time`	INTEGER NOT NULL,
	`price`	REAL NOT NULL,
	UNIQUE (`stock`, `time`)
);
COMMIT;
