-- DROP TABLE IF EXISTS monthly_expense;
-- monthlyexpenses.monthly_expense definition
--CREATE TABLE `monthly_expense` (
--  `id` bigint NOT NULL AUTO_INCREMENT,
--  `amount` double NOT NULL,
--  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
--  `paid_for` varchar(100) NOT NULL,
--  PRIMARY KEY (`id`)
--)

INSERT INTO monthly_expense(paid_for, amount) VALUES ('Coffie', 110001);
INSERT INTO monthly_expense(paid_for, amount) VALUES ('Carbs', 208001);
INSERT INTO monthly_expense(paid_for, amount) VALUES ('Green Tea', 226001);