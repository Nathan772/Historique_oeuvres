-- DROP TABLE IF EXISTS USER;
-- DROP TABLE IF EXISTS USER2;
-- DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS test_user;


-- manga
DROP TABLE IF EXISTS hasgenremanga CASCADE;
DROP TABLE IF EXISTS hasgenremovie CASCADE;
DROP TABLE IF EXISTS readmanga CASCADE;
DROP TABLE IF EXISTS manga CASCADE;


-- serie
DROP TABLE IF EXISTS watchSerie CASCADE;
DROP TABLE IF EXISTS serie CASCADE;

-- movies 

DROP TABLE IF EXISTS watchmovie CASCADE;
DROP TABLE IF EXISTS watchmovie CASCADE;
DROP TABLE IF EXISTS movie CASCADE;

-- other
DROP TABLE IF EXISTS user cascade;
DROP TABLE IF EXISTS users cascade;
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

-- les noms des tables doivent être entièrement en majuscule pour éviter les conflits avec spring
CREATE TABLE user(
     iduser SERIAL NOT NULL PRIMARY KEY,
     pseudo VARCHAR(100) NOT NULL UNIQUE,
     email VARCHAR(100) NOT NULL UNIQUE,
     password VARCHAR(100) NOT NULL,
     category VARCHAR (30) NOT NULL DEFAULT "average",
     constraint check_category check (category = "admin" OR category ="average")
);

-- store all the genre for every kind of piece of art
CREATE TABLE genre(
  idgenre SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(200)
);

CREATE TABLE manga(
  idmanga SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(300) NOT NULL
);

CREATE TABLE movie(
  idmovie SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(300) NOT NULL,
  year int unsigned,
  director VARCHAR(300) NOT NULL,
  imdbID VARCHAR(300) NOT NULL UNIQUE
);


-- -- this table enable to store all the genre related to one movie
CREATE TABLE hasgenremovie(
  idHasGenreMovie SERIAL NOT NULL PRIMARY KEY,
  idgenre BIGINT UNSIGNED NOT NULL,
  idmovie BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY(idgenre) REFERENCES genre(idgenre) on delete cascade on update cascade,
  FOREIGN KEY(idmovie) REFERENCES manga(idmanga) on delete cascade on update cascade
);


-- -- this table enable to store all the genre related to one manga
CREATE TABLE hasgenremanga(
  idHasGenreMan SERIAL NOT NULL PRIMARY KEY,
  idgenre BIGINT UNSIGNED NOT NULL,
  idmanga BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY(idgenre) REFERENCES genre(idgenre) on delete cascade on update cascade,
  FOREIGN KEY(idmanga) REFERENCES manga(idmanga) on delete cascade on update cascade
);



-- link between movie and user
-- en cours de visionnage




CREATE TABLE watchmovie(
  idwatchmovie SERIAL NOT NULL PRIMARY KEY,
  iduser BIGINT UNSIGNED NOT NULL,
  idmovie BIGINT UNSIGNED NOT NULL,
  currentState VARCHAR(200) NOT NULL DEFAULT 'à regarder plus tard',
  -- enable to know the last time you update your statut regarding your 
  -- interest for a movie
  lastUpdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  lastMoment TIME NOT NULL DEFAULT '0:00:00',
  FOREIGN KEY (idmovie) REFERENCES movie(idmovie) on delete cascade on update cascade,
  FOREIGN KEY (iduser) REFERENCES user(iduser) on delete cascade on update cascade,
  constraint currentStateConsistent check (currentState = 'à regarder plus tard' OR currentState = 'fini' OR currentState = 'à revoir' OR currentState = 'en cours de visionnage'
 OR currentState  = 'abandon'),
 constraint unique_user_movie unique (iduser, idmovie)
);








	




-- link between user and manga
-- si le dernier chapitre et dernier tome ne sont pas spécifié
-- on écrira dans le html "non spécifié"
CREATE TABLE readmanga(
  idreadman SERIAL NOT NULL PRIMARY KEY,
  iduser BIGINT UNSIGNED NOT NULL,
  idmanga BIGINT UNSIGNED NOT NULL,
  current_state VARCHAR(200) NOT NULL DEFAULT 'à lire plus tard',
  lastUpdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  lastChapterRead int unsigned,
  lastVolumeRead int unsigned,
  FOREIGN KEY (idmanga) REFERENCES manga(idmanga) on delete cascade on update cascade,
  FOREIGN KEY (iduser) REFERENCES user(iduser) on delete cascade on update cascade,
  constraint current_state check (current_state = 'à lire plus tard' OR current_state = 'fini' OR current_state = 'en cours de lecture' OR current_state = 'à relire' OR current_state = 'en pause' OR current_state = 'abandon'),
  constraint unique_user_manga unique (iduser, idmanga)
  
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


INSERT INTO user(pseudo, email, password)
VALUES ('mikeTyson', 'mt@gmail.com', 'bestBoxer'),
       ('margotRobbie', 'mr@gmail.com', 'greatActress'),
       ('michaelJackson', 'mj@gmail.com', 'greatestSinger'),
       ('celineDion', 'cd@gmail.com', 'amazingVoice')




