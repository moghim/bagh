-- MySQL dump 10.13  Distrib 5.5.27, for Win64 (x86)
--
-- Host: localhost    Database: boostandb
-- ------------------------------------------------------
-- Server version	5.5.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `boostandb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `boostandb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `boostandb`;

--
-- Table structure for table `offering`
--

DROP TABLE IF EXISTS `offering`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offering` (
  `id` varchar(45) NOT NULL,
  `professor` varchar(45) DEFAULT NULL,
  `course` varchar(45) DEFAULT NULL,
  `section` int(11) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `remainCapacity` int(11) DEFAULT NULL,
  `examDate` varchar(45) DEFAULT NULL,
  `termId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `link1` (`termId`),
  CONSTRAINT `link1` FOREIGN KEY (`termId`) REFERENCES `term` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offering`
--

LOCK TABLES `offering` WRITE;
/*!40000 ALTER TABLE `offering` DISABLE KEYS */;
INSERT INTO `offering` VALUES ('1','6','1',1,1,10,11,'07-09-2012','1'),('2','1','2',1,1,10,12,'06-09-2012','1'),('3','9','3',1,2,10,12,'05-09-2012','1'),('4','3','4',1,3,10,-1,'04-09-2012','1'),('5','1','7',1,6,10,-2,'28-05-2013','1'),('6','1','2',1,5,10,9,'27-05-2013','2'),('7','9','3',1,4,10,9,'26-05-2013','2'),('8','4','8',1,6,10,10,'25-05-2013','2'),('9','2','1',1,1,10,10,'24-05-2013','2');
/*!40000 ALTER TABLE `offering` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` varchar(45) NOT NULL,
  `program` varchar(45) NOT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('810190420','1','Gholam','Patoobaf'),('810190421','1','Ghamar','Aghrabparast');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studyrecord`
--

DROP TABLE IF EXISTS `studyrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studyrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` float DEFAULT NULL,
  `offering` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `studentId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `link` (`studentId`),
  CONSTRAINT `link` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studyrecord`
--

LOCK TABLES `studyrecord` WRITE;
/*!40000 ALTER TABLE `studyrecord` DISABLE KEYS */;
INSERT INTO `studyrecord` VALUES (38,0,'5','INPROGRESS','810190421'),(40,0,'1','INPROGRESS','810190421');
/*!40000 ALTER TABLE `studyrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `term` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `enrollmentStartDate` varchar(45) DEFAULT NULL,
  `enrollmentEndDate` varchar(45) DEFAULT NULL,
  `addAndDropStartDate` varchar(45) DEFAULT NULL,
  `addAndDropEndDate` varchar(45) DEFAULT NULL,
  `withdrawStartDate` varchar(45) DEFAULT NULL,
  `withdrawEndDate` varchar(45) DEFAULT NULL,
  `submitGradeStartDate` varchar(45) DEFAULT NULL,
  `submitGradeEndDate` varchar(45) DEFAULT NULL,
  `startDate` varchar(45) DEFAULT NULL,
  `endDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
INSERT INTO `term` VALUES ('1','Fall-12','25-03-2013','26-04-2013','27-04-2013','30-04-2013','01-05-2013','20-05-2013','12-06-2013','16-06-2013','20-03-2013','29-05-2013'),('2','Spring-13','25-06-2013','26-07-2013','27-07-2013','30-07-2013','01-08-2013','20-08-2013','15-09-2013','17-09-2013','20-06-2013','29-08-2013');
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-04-25 14:10:03
