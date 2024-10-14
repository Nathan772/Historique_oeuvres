
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS test_user;
DROP TABLE IF EXISTS video;

CREATE TABLE user(
     id SERIAL NOT NULL PRIMARY KEY,
     pseudo VARCHAR(100),
     password VARCHAR(100),
     email VARCHAR(100)
     );
