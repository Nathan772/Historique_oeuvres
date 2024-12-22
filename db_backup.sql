-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: historyDB
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `idgenre` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idgenre`),
  UNIQUE KEY `idgenre` (`idgenre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hasgenremanga`
--

DROP TABLE IF EXISTS `hasgenremanga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hasgenremanga` (
  `idHasGenreMan` bigint unsigned NOT NULL AUTO_INCREMENT,
  `idgenre` bigint unsigned NOT NULL,
  `idmanga` bigint unsigned NOT NULL,
  PRIMARY KEY (`idHasGenreMan`),
  UNIQUE KEY `idHasGenreMan` (`idHasGenreMan`),
  KEY `idgenre` (`idgenre`),
  KEY `idmanga` (`idmanga`),
  CONSTRAINT `hasgenremanga_ibfk_1` FOREIGN KEY (`idgenre`) REFERENCES `genre` (`idgenre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasgenremanga_ibfk_2` FOREIGN KEY (`idmanga`) REFERENCES `manga` (`idmanga`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hasgenremanga`
--

LOCK TABLES `hasgenremanga` WRITE;
/*!40000 ALTER TABLE `hasgenremanga` DISABLE KEYS */;
/*!40000 ALTER TABLE `hasgenremanga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hasgenremovie`
--

DROP TABLE IF EXISTS `hasgenremovie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hasgenremovie` (
  `idHasGenreMovie` bigint unsigned NOT NULL AUTO_INCREMENT,
  `idgenre` bigint unsigned NOT NULL,
  `idmovie` bigint unsigned NOT NULL,
  PRIMARY KEY (`idHasGenreMovie`),
  UNIQUE KEY `idHasGenreMovie` (`idHasGenreMovie`),
  KEY `idgenre` (`idgenre`),
  KEY `idmovie` (`idmovie`),
  CONSTRAINT `hasgenremovie_ibfk_1` FOREIGN KEY (`idgenre`) REFERENCES `genre` (`idgenre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasgenremovie_ibfk_2` FOREIGN KEY (`idmovie`) REFERENCES `manga` (`idmanga`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hasgenremovie`
--

LOCK TABLES `hasgenremovie` WRITE;
/*!40000 ALTER TABLE `hasgenremovie` DISABLE KEYS */;
/*!40000 ALTER TABLE `hasgenremovie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manga`
--

DROP TABLE IF EXISTS `manga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manga` (
  `idmanga` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(300) NOT NULL,
  PRIMARY KEY (`idmanga`),
  UNIQUE KEY `idmanga` (`idmanga`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manga`
--

LOCK TABLES `manga` WRITE;
/*!40000 ALTER TABLE `manga` DISABLE KEYS */;
/*!40000 ALTER TABLE `manga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `idmovie` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `year` int unsigned DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `imdbID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idmovie`),
  UNIQUE KEY `idmovie` (`idmovie`),
  UNIQUE KEY `imdbID` (`imdbID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'bla',0,'jean','sss'),(2,'Batman Begins',2005,'Christopher Nolan','tt0372784'),(3,'The Batman',2022,'Matt Reeves','tt1877830'),(4,'American Psycho',2000,'Mary Harron','tt0144084'),(5,'One Piece Film: Red',2022,'Gorô Taniguchi','tt16183464'),(6,'One Piece Film Z',2012,'Tatsuya Nagamine','tt2375379'),(7,'Batman v Superman: Dawn of Justice',2016,'Zack Snyder','tt2975590'),(8,'Naruto Shippuden the Movie: Blood Prison',2011,'Masahiko Murata','tt1999167'),(9,'Challengers',2024,'Luca Guadagnino','tt16426418'),(10,'A Clockwork Orange',1971,'Stanley Kubrick','tt0066921'),(11,'V for Vendetta',2005,'James McTeigue','tt0434409'),(12,'Watchmen',2009,'Zack Snyder','tt0409459'),(13,'Eyes Wide Shut',1999,'Stanley Kubrick','tt0120663'),(14,'Ready Player One',2018,'Steven Spielberg','tt1677720'),(15,'Alita: Battle Angel',2019,'Robert Rodriguez','tt0437086'),(16,'Akira',1988,'Katsuhiro Ôtomo','tt0094625'),(17,'The Lego Batman Movie',2017,'Chris McKay','tt4116284'),(18,'The Adventures of Tintin',2011,'Steven Spielberg','tt0983193'),(19,'The Prestige',2006,'Christopher Nolan','tt0482571'),(20,'Naruto the Movie: Ninja Clash in the Land of Snow',2004,'Tensai Okamura','tt0476680'),(21,'The Last: Naruto the Movie',2014,'Tsuneo Kobayashi','tt3717532'),(22,'Boruto: Naruto the Movie',2015,'Hiroyuki Yamashita, Toshiyuki Tsuru','tt4618398'),(23,'American Beauty',1999,'Sam Mendes','tt0169547');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_SEQ`
--

DROP TABLE IF EXISTS `movie_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_SEQ`
--

LOCK TABLES `movie_SEQ` WRITE;
/*!40000 ALTER TABLE `movie_SEQ` DISABLE KEYS */;
INSERT INTO `movie_SEQ` VALUES (1);
/*!40000 ALTER TABLE `movie_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_seq`
--

DROP TABLE IF EXISTS `movie_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_seq`
--

LOCK TABLES `movie_seq` WRITE;
/*!40000 ALTER TABLE `movie_seq` DISABLE KEYS */;
INSERT INTO `movie_seq` VALUES (601);
/*!40000 ALTER TABLE `movie_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readmanga`
--

DROP TABLE IF EXISTS `readmanga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `readmanga` (
  `idreadman` bigint unsigned NOT NULL AUTO_INCREMENT,
  `iduser` bigint unsigned NOT NULL,
  `idmanga` bigint unsigned NOT NULL,
  `current_state` varchar(200) NOT NULL DEFAULT 'à lire plus tard',
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastChapterRead` int unsigned DEFAULT NULL,
  `lastVolumeRead` int unsigned DEFAULT NULL,
  PRIMARY KEY (`idreadman`),
  UNIQUE KEY `idreadman` (`idreadman`),
  UNIQUE KEY `unique_user_manga` (`iduser`,`idmanga`),
  KEY `idmanga` (`idmanga`),
  CONSTRAINT `readmanga_ibfk_1` FOREIGN KEY (`idmanga`) REFERENCES `manga` (`idmanga`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `readmanga_ibfk_2` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `current_state` CHECK (((`current_state` = _utf8mb4'à lire plus tard') or (`current_state` = _utf8mb4'fini') or (`current_state` = _utf8mb4'en cours de lecture') or (`current_state` = _utf8mb4'à relire') or (`current_state` = _utf8mb4'en pause') or (`current_state` = _utf8mb4'abandon')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readmanga`
--

LOCK TABLES `readmanga` WRITE;
/*!40000 ALTER TABLE `readmanga` DISABLE KEYS */;
/*!40000 ALTER TABLE `readmanga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` bigint unsigned NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `iduser` (`iduser`),
  UNIQUE KEY `pseudo` (`pseudo`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `check_category` CHECK (((`category` = _utf8mb4'admin') or (`category` = _utf8mb4'average')))
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'JulieB','JulieB@gmail.com','666666','average'),(3,'Jennifer','Jennifer@gmail.com','666666','average'),(4,'Helen','Helen@gmail.com','666666','average'),(5,'Rachel','Rachel@gmail.com','666666','average'),(15,'natelesceptique','nw.blackmssiah@gmail.com','666666','admin'),(16,'HughJack','HughJackman@gmail.com','666666','average');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (601);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_seq`
--

DROP TABLE IF EXISTS `users_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_seq`
--

LOCK TABLES `users_seq` WRITE;
/*!40000 ALTER TABLE `users_seq` DISABLE KEYS */;
INSERT INTO `users_seq` VALUES (1);
/*!40000 ALTER TABLE `users_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watch_movie`
--

DROP TABLE IF EXISTS `watch_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `watch_movie` (
  `idwatch_movie` bigint NOT NULL,
  `current_state` varchar(255) DEFAULT NULL,
  `idmovie` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `last_moment` time(6) DEFAULT NULL,
  `last_update` date DEFAULT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`idwatch_movie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watch_movie`
--

LOCK TABLES `watch_movie` WRITE;
/*!40000 ALTER TABLE `watch_movie` DISABLE KEYS */;
INSERT INTO `watch_movie` VALUES (1,NULL,252,5,NULL,NULL,0),(2,NULL,302,0,NULL,NULL,0),(52,NULL,352,402,NULL,NULL,0);
/*!40000 ALTER TABLE `watch_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watch_movie_seq`
--

DROP TABLE IF EXISTS `watch_movie_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `watch_movie_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watch_movie_seq`
--

LOCK TABLES `watch_movie_seq` WRITE;
/*!40000 ALTER TABLE `watch_movie_seq` DISABLE KEYS */;
INSERT INTO `watch_movie_seq` VALUES (301);
/*!40000 ALTER TABLE `watch_movie_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watchmovie`
--

DROP TABLE IF EXISTS `watchmovie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `watchmovie` (
  `idwatchmovie` bigint unsigned NOT NULL AUTO_INCREMENT,
  `iduser` bigint unsigned NOT NULL,
  `idmovie` bigint unsigned NOT NULL,
  `currentState` varchar(255) DEFAULT NULL,
  `lastUpdate` datetime(6) DEFAULT NULL,
  `lastMoment` time NOT NULL DEFAULT '00:00:00',
  PRIMARY KEY (`idwatchmovie`),
  UNIQUE KEY `idwatchmovie` (`idwatchmovie`),
  UNIQUE KEY `unique_user_movie` (`iduser`,`idmovie`),
  KEY `idmovie` (`idmovie`),
  CONSTRAINT `watchmovie_ibfk_1` FOREIGN KEY (`idmovie`) REFERENCES `movie` (`idmovie`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `watchmovie_ibfk_2` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `currentStateConsistent` CHECK (((`currentState` = _utf8mb4'à regarder plus tard') or (`currentState` = _utf8mb4'fini') or (`currentState` = _utf8mb4'à revoir') or (`currentState` = _utf8mb4'en cours de visionnage') or (`currentState` = _utf8mb4'abandon')))
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watchmovie`
--

LOCK TABLES `watchmovie` WRITE;
/*!40000 ALTER TABLE `watchmovie` DISABLE KEYS */;
INSERT INTO `watchmovie` VALUES (53,3,5,'à regarder plus tard','2024-11-02 18:47:34.300000','00:00:00'),(54,3,6,'à regarder plus tard','2024-11-02 18:47:40.693000','00:00:00'),(55,3,3,'à regarder plus tard','2024-11-02 19:15:20.693000','00:00:00'),(68,3,7,'à regarder plus tard','2024-11-06 19:20:11.311000','00:00:00'),(69,3,8,'à regarder plus tard','2024-11-06 20:03:58.703000','00:00:00'),(88,16,2,'à regarder plus tard','2024-11-07 10:54:17.431000','00:00:00'),(89,16,3,'à regarder plus tard','2024-11-07 10:54:21.891000','00:00:00'),(90,16,21,'à regarder plus tard','2024-11-07 10:54:31.384000','00:00:00'),(91,16,22,'à regarder plus tard','2024-11-07 10:54:37.223000','00:00:00'),(92,15,19,'à regarder plus tard','2024-11-07 10:56:24.282000','00:00:00'),(93,15,23,'à regarder plus tard','2024-11-07 10:56:44.878000','00:00:00');
/*!40000 ALTER TABLE `watchmovie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watchmovie_SEQ`
--

DROP TABLE IF EXISTS `watchmovie_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `watchmovie_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watchmovie_SEQ`
--

LOCK TABLES `watchmovie_SEQ` WRITE;
/*!40000 ALTER TABLE `watchmovie_SEQ` DISABLE KEYS */;
INSERT INTO `watchmovie_SEQ` VALUES (1);
/*!40000 ALTER TABLE `watchmovie_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watchmovie_seq`
--

DROP TABLE IF EXISTS `watchmovie_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `watchmovie_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watchmovie_seq`
--

LOCK TABLES `watchmovie_seq` WRITE;
/*!40000 ALTER TABLE `watchmovie_seq` DISABLE KEYS */;
INSERT INTO `watchmovie_seq` VALUES (201);
/*!40000 ALTER TABLE `watchmovie_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-22 13:13:27
