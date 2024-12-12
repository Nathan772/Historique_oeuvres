DROP TABLE IF EXISTS USER_TABLE;
DROP TABLE IF EXISTS MOVIE;

CREATE TABLE `user_table`(
     `iduser` bigint(20) unsigned NOT NULL PRIMARY KEY,
     `pseudo` VARCHAR(100) NOT NULL UNIQUE,
     `email` VARCHAR(100) NOT NULL UNIQUE,
     `password` VARCHAR(100) NOT NULL,
     `category` VARCHAR (30) NOT NULL DEFAULT 'average',
     constraint `check_category` check (`category` = 'admin' OR `category` = 'average')
);



CREATE TABLE `movie`(
  `idmovie` bigint(20) unsigned PRIMARY KEY NOT NULL,
  `title` VARCHAR(300) NOT NULL,
  `yearOfRelease` int unsigned,
  `director` VARCHAR(300) NOT NULL,
  `imdbID` VARCHAR(300) NOT NULL UNIQUE
);