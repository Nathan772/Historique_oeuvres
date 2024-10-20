-- DROP TABLE IF EXISTS USER;
-- DROP TABLE IF EXISTS USER2;
-- DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS test_user;

DROP TABLE IF EXISTS hasGenreManga CASCADE;
DROP TABLE IF EXISTS readManga CASCADE;
DROP TABLE IF EXISTS manga CASCADE;
DROP TABLE IF EXISTS user cascade;
DROP TABLE IF EXISTS genre CASCADE;

DROP TABLE IF EXISTS serie CASCADE;
DROP TABLE IF EXISTS watchMovie CASCADE;
DROP TABLE IF EXISTS watchSerie CASCADE;
DROP TABLE IF EXISTS watch CASCADE;






-- remove only the content
-- TRUNCATE TABLE IF EXISTS hasGenreManga CASCADE;
-- TRUNCATE TABLE IF EXISTS readManga CASCADE;
-- TRUNCATE TABLE IF EXISTS user cascade;
-- TRUNCATE TABLE IF EXISTS manga CASCADE;
-- TRUNCATE TABLE IF EXISTS genre CASCADE;


-- TRUNCATE TABLE IF EXISTS serie CASCADE;
-- TRUNCATE TABLE IF EXISTS watchMovie CASCADE;
-- TRUNCATE TABLE IF EXISTS watchSerie CASCADE;
-- TRUNCATE TABLE IF EXISTS watch CASCADE;




-- add constraint : password minsize : 6
-- email like : (qqc)@.com/.fr
CREATE TABLE user(
     iduser SERIAL NOT NULL PRIMARY KEY,
     pseudo VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     password VARCHAR(100) NOT NULL,
     category VARCHAR (30) NOT NULL DEFAULT "average",
     constraint check_category check (category = "admin" OR category ="average")
);

-- -- store all the genre for every kind of piece of art
CREATE TABLE genre(
  idgenre SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(200)
);

CREATE TABLE manga(
  idmanga SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(300) NOT NULL,
  description text NOT NULL
);

-- -- this table enable to store all the genre related to one manga
CREATE TABLE hasGenreManga(
  idHasGenreMan SERIAL NOT NULL PRIMARY KEY,
  idgenre BIGINT UNSIGNED NOT NULL,
  idmanga BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY(idgenre) REFERENCES genre(idgenre) on delete cascade on update cascade,
  FOREIGN KEY(idmanga) REFERENCES manga(idmanga) on delete cascade on update cascade
);




-- link between user and manga
-- si le dernier chapitre et dernier tome ne sont pas spécifié
-- on écrira dans le html "non spécifié"
CREATE TABLE readManga(
  idreadman SERIAL NOT NULL PRIMARY KEY,
  iduser BIGINT UNSIGNED NOT NULL,
  idmanga BIGINT UNSIGNED NOT NULL,
  etat VARCHAR(200),
  dernier_chapitre_lu int unsigned,
  dernier_tome_lu int unsigned,
  FOREIGN KEY (idmanga) REFERENCES manga(idmanga) on delete cascade on update cascade,
  FOREIGN KEY (iduser) REFERENCES user(iduser) on delete cascade on update cascade
  constraint etat check (etat = "en cours de lecture" OR etat ="à relire" OR etat = "en pause" OR etat = "abandon" OR etat = null)
);




 



-- CREATE TABLE serie(
--      idserie SERIAL NOT NULL PRIMARY KEY,
--      title VARCHAR(300) NOT NULL,
--      description VARCHAR(200) NOT NULL,
--      genre
-- );

-- CREATE TABLE watchSerie(
--   idwatch SERIAL NOT NULL PRIMARY KEY,
--   iduser BIGINT UNSIGNED REFERENCES user,
--   idserie BIGINT UNSIGNED REFERENCES serie,
--   FOREIGN KEY (idserie) REFERENCES serie(idserie) on delete cascade,
--   FOREIGN KEY (iduser) REFERENCES user(iduser) on delete cascade
-- );

-- CREATE TABLE watchMovie(
--   idwatch SERIAL NOT NULL PRIMARY KEY,
--   iduser BIGINT UNSIGNED REFERENCES video,
--   idserie BIGINT UNSIGNED REFERENCES user,
--   FOREIGN KEY (idserie) REFERENCES serie(idserie) on delete cascade,
--   FOREIGN KEY (iduser) REFERENCES user(iduser) on delete cascade
-- );




