-- DROP TABLE IF EXISTS USER;
-- DROP TABLE IF EXISTS USER2;
-- DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS test_user;


-- manga
DROP TABLE IF EXISTS hasGenreManga CASCADE;
DROP TABLE IF EXISTS readManga CASCADE;
DROP TABLE IF EXISTS manga CASCADE;


-- serie
DROP TABLE IF EXISTS watchSerie CASCADE;
DROP TABLE IF EXISTS serie CASCADE;

-- movies 
DROP TABLE IF EXISTS watchMovie CASCADE;
DROP TABLE IF EXISTS hasGenreMovie CASCADE;
DROP TABLE IF EXISTS movie CASCADE;

--other
DROP TABLE IF EXISTS user cascade;
DROP TABLE IF EXISTS genre CASCADE;






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

CREATE TABLE movie(
  idmovie SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(300) NOT NULL,
  description text NOT NULL
);

-- -- this table enable to store all the genre related to one movie
CREATE TABLE hasGenreMovie(
  idHasGenreMovie SERIAL NOT NULL PRIMARY KEY,
  idgenre BIGINT UNSIGNED NOT NULL,
  idmovie BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY(idgenre) REFERENCES genre(idgenre) on delete cascade on update cascade,
  FOREIGN KEY(idmovie) REFERENCES manga(idmanga) on delete cascade on update cascade
);




-- -- this table enable to store all the genre related to one manga
CREATE TABLE hasGenreManga(
  idHasGenreMan SERIAL NOT NULL PRIMARY KEY,
  idgenre BIGINT UNSIGNED NOT NULL,
  idmanga BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY(idgenre) REFERENCES genre(idgenre) on delete cascade on update cascade,
  FOREIGN KEY(idmanga) REFERENCES manga(idmanga) on delete cascade on update cascade
);



-- link between movie and user
-- en cours de visionnage

CREATE TABLE watchMovie(
  idwatchMovie SERIAL NOT NULL PRIMARY KEY,
  iduser BIGINT UNSIGNED NOT NULL,
  idmovie BIGINT UNSIGNED NOT NULL,
  current_state VARCHAR(200),
  last_moment TIME NOT NULL DEFAULT '0:00:00',
  FOREIGN KEY (idmovie) REFERENCES serie(idmovie) on delete cascade on update cascade,
  FOREIGN KEY (iduser) REFERENCES user(iduser) on delete on update cascade
  constraint current_state check (current_state = "à regarder plus tard" OR current_state = "fini" current_state ="à revoir" OR current_state = "en cours de visionnage" OR current_state = "abandon")
);

-- link between user and manga
-- si le dernier chapitre et dernier tome ne sont pas spécifié
-- on écrira dans le html "non spécifié"
CREATE TABLE readManga(
  idreadman SERIAL NOT NULL PRIMARY KEY,
  iduser BIGINT UNSIGNED NOT NULL,
  idmanga BIGINT UNSIGNED NOT NULL,
  current_state VARCHAR(200) NOT NULL DEFAULT 'à lire plus tard',
  last_chapter_read int unsigned,
  last_volume_read int unsigned,
  FOREIGN KEY (idmanga) REFERENCES manga(idmanga) on delete cascade on update cascade,
  FOREIGN KEY (iduser) REFERENCES user(iduser) on delete cascade on update cascade
  constraint current_state check (current_state = "à lire plus tard" OR current_state = "fini" OR current_state = "en cours de lecture" OR current_state ="à relire" OR current_state = "en pause" OR current_state = "abandon")
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




