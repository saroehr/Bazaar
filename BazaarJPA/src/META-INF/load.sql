USE BAZAAR;
INSERT INTO CATEGORY (IDENTIFIER, DESCRIPTION, NAME, PARENT)VALUES ("02b7d2c5-82e1-4808-afbb-eed31d8adeb8", "ROOT", "ROOT", "02b7d2c5-82e1-4808-afbb-eed31d8adeb8") ON DUPLICATE KEY UPDATE IDENTIFIER = "02b7d2c5-82e1-4808-afbb-eed31d8adeb8", DESCRIPTION = "ROOT", NAME = "ROOT", PARENT = "02b7d2c5-82e1-4808-afbb-eed31d8adeb8";