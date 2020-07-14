-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: market
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `m_board`
--

DROP TABLE IF EXISTS `m_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_board` (
  `board_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `board_uSeqno` int(11) DEFAULT NULL,
  `board_Title` varchar(45) DEFAULT NULL,
  `board_Price` int(11) DEFAULT NULL,
  `board_Content` text,
  `board_Hit` int(11) DEFAULT NULL,
  `board_Sido` varchar(45) DEFAULT NULL,
  `board_Latitude` varchar(45) DEFAULT NULL,
  `board_Longitude` varchar(45) DEFAULT NULL,
  `board_isDone` int(11) DEFAULT NULL,
  `board_InsertDate` date DEFAULT NULL,
  `board_DeleteDate` date DEFAULT NULL,
  PRIMARY KEY (`board_Seqno`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_board`
--

LOCK TABLES `m_board` WRITE;
/*!40000 ALTER TABLE `m_board` DISABLE KEYS */;
INSERT INTO `m_board` VALUES (50,2,'why',136600,'흠 이미지왜저래',31,'대한민국 서울특별시 서초구 서초4동 1304-4','37.5017794','127.02',0,'2020-04-14',NULL),(51,1,'싸게 팝니다!!',9500,'너무싸지요',2,'서울특별시 광진구','37.505','127.01',0,NULL,NULL),(52,2,'이거는 조금 비싼거에요',950000,'조금 비싸지요',1,'서울특별시 서초구','37.505','127.02',1,NULL,NULL),(53,3,'3번이 작성한 판매물품',35000,'3번물품',2,'서울특별시 성수동','37.505','127.021',0,NULL,NULL),(54,4,'4번이 파는 물건',43900,'냉무',1,'서울특별시 강북구','37.505','127.03',1,NULL,NULL),(55,1,'이거 사세요오오',15000,'내용무우우',2,'서울특별시 성북구','37.505','127.05',0,NULL,NULL),(56,2,'이거는 사야해요',53000,'제곧내',10,'서울특별시 성동구','37.505','127.031',0,NULL,NULL),(57,3,'교촌치킨 기프트콘',13000,'존맛탱',13,'서울특별시 관악구','37.505','127.07',1,NULL,NULL),(58,4,'캉골',43000,'캉골물품판다',30,'서울특별시 서초구','37.505','127.011',0,NULL,NULL);
/*!40000 ALTER TABLE `m_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_dm`
--

DROP TABLE IF EXISTS `m_dm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_dm` (
  `dm_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `dm_bSend` int(11) DEFAULT NULL,
  `dm_bReceive` int(11) DEFAULT NULL,
  `dm_Content` text,
  `dm_InsertDate` date DEFAULT NULL,
  `dm_SendDelete` int(11) DEFAULT NULL,
  `dm_ReceiveDelete` int(11) DEFAULT NULL,
  `dm_Title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dm_Seqno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_dm`
--

LOCK TABLES `m_dm` WRITE;
/*!40000 ALTER TABLE `m_dm` DISABLE KEYS */;
INSERT INTO `m_dm` VALUES (1,4,4,'산다',NULL,NULL,NULL,'그 피규어 삼'),(2,2,4,'내가산다',NULL,1,NULL,'그거산다!'),(3,2,4,'제가 삽니다',NULL,NULL,NULL,'그 물건 제가 살게요'),(4,6,4,'제가 살게요',NULL,NULL,NULL,'그 물건 제가 삽니다.'),(5,7,4,'쪽지 주세요.',NULL,NULL,NULL,'제가 살게요'),(6,8,2,'연락 주세요.',NULL,NULL,NULL,'그 피규어 삼');
/*!40000 ALTER TABLE `m_dm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_image`
--

DROP TABLE IF EXISTS `m_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_image` (
  `image_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `image_bSeqno` int(11) DEFAULT NULL,
  `image_Blob` mediumblob,
  `image_String` text,
  PRIMARY KEY (`image_Seqno`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_image`
--

LOCK TABLES `m_image` WRITE;
/*!40000 ALTER TABLE `m_image` DISABLE KEYS */;
INSERT INTO `m_image` VALUES (27,50,NULL,'20180329_195531.jpg'),(28,51,NULL,'FB_IMG_1525597277651.jpg'),(29,52,NULL,'SAM_6449.jpeg'),(30,53,NULL,'img_side.jpg'),(31,54,NULL,'39982nd.jpg'),(32,55,NULL,'81140973_15452703643.jpg'),(33,56,NULL,'20180329_195531.jpg'),(34,57,NULL,'FB_IMG_1525597277651.jpg'),(35,58,NULL,'20171206_192039.jpg');
/*!40000 ALTER TABLE `m_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_likes`
--

DROP TABLE IF EXISTS `m_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_likes` (
  `like_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `like_bSeqno` int(11) DEFAULT NULL,
  `like_uSeqno` int(11) DEFAULT NULL,
  PRIMARY KEY (`like_Seqno`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_likes`
--

LOCK TABLES `m_likes` WRITE;
/*!40000 ALTER TABLE `m_likes` DISABLE KEYS */;
INSERT INTO `m_likes` VALUES (57,54,4),(59,52,2),(60,57,3),(61,56,2),(65,53,3),(74,58,4),(75,58,4);
/*!40000 ALTER TABLE `m_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_reply`
--

DROP TABLE IF EXISTS `m_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_reply` (
  `reply_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `reply_bSeqno` int(11) DEFAULT NULL,
  `reply_uSeqno` varchar(45) DEFAULT NULL,
  `reply_Date` date DEFAULT NULL,
  PRIMARY KEY (`reply_Seqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_reply`
--

LOCK TABLES `m_reply` WRITE;
/*!40000 ALTER TABLE `m_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_searchData`
--

DROP TABLE IF EXISTS `m_searchData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_searchData` (
  `search_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `search_Content` varchar(45) DEFAULT NULL,
  `search_Date` date DEFAULT NULL,
  PRIMARY KEY (`search_Seqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_searchData`
--

LOCK TABLES `m_searchData` WRITE;
/*!40000 ALTER TABLE `m_searchData` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_searchData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_userInfo`
--

DROP TABLE IF EXISTS `m_userInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_userInfo` (
  `user_Seqno` int(11) NOT NULL AUTO_INCREMENT,
  `user_Id` varchar(45) NOT NULL,
  `user_Password` varchar(45) NOT NULL,
  `user_Name` varchar(45) DEFAULT NULL,
  `user_Telno` varchar(45) DEFAULT NULL,
  `user_Email` varchar(45) DEFAULT NULL,
  `user_Address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_Seqno`,`user_Id`),
  UNIQUE KEY `user_Id_UNIQUE` (`user_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_userInfo`
--

LOCK TABLES `m_userInfo` WRITE;
/*!40000 ALTER TABLE `m_userInfo` DISABLE KEYS */;
INSERT INTO `m_userInfo` VALUES (1,'테스터','1234','테스터','01012341234','test@naver.com',NULL),(2,'lschyh','1234','이성일','01012341234','lschyh@naver.com',NULL),(3,'스타터','1234','스타터','01055557777','starter@s.t',NULL),(4,'nUser','1234','김우리','01044453334','nuser@naver.com',NULL),(5,'newUser','1234','홍길동','01044551234','newUser@naver.com',NULL),(6,'hellou','1234','이상희','01044352233','hellou@naver.com',NULL),(7,'qwer123','1234','김유신','01095884444','user@naver.com',NULL),(8,'tjdwn','1234','성팀장','01059598843','tjdwn@naver.com',NULL);
/*!40000 ALTER TABLE `m_userInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-18 20:30:51
