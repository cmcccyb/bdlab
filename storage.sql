/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.35-log : Database - storage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`storage` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `storage`;

/*Table structure for table `clientdetails` */

DROP TABLE IF EXISTS `clientdetails`;

CREATE TABLE `clientdetails` (
  `appId` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `resourceIds` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appSecret` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `scope` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `grantTypes` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `redirectUrl` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authorities` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `autoApproveScopes` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `clientdetails` */

/*Table structure for table `concept` */

DROP TABLE IF EXISTS `concept`;

CREATE TABLE `concept` (
  `conceptId` int(11) NOT NULL AUTO_INCREMENT,
  `conceptDescribe` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `conceptName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `chineseName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appType` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`conceptId`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `concept` */

insert  into `concept`(`conceptId`,`conceptDescribe`,`conceptName`,`chineseName`,`appType`,`appName`) values (92,'stepCount','stepCount','简要运动','WSYD','AppA'),(93,'stepDetail','stepDetail','详细运动','WSYD','AppA'),(94,'stepEffective','stepEffective','有效步数','WSYD','AppA');

/*Table structure for table `encounter` */

DROP TABLE IF EXISTS `encounter`;

CREATE TABLE `encounter` (
  `encounterId` int(11) NOT NULL AUTO_INCREMENT,
  `encounterDatetime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `encounterType` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `locationId` int(11) DEFAULT NULL,
  `patientId` int(11) DEFAULT NULL,
  PRIMARY KEY (`encounterId`),
  KEY `FKD24A89135ACAC5A9` (`patientId`),
  KEY `encounterTimeIndex` (`encounterDatetime`),
  CONSTRAINT `FKD24A89135ACAC5A9` FOREIGN KEY (`patientId`) REFERENCES `patient` (`patientId`) ON DELETE CASCADE,
  CONSTRAINT `FK_PATIENT` FOREIGN KEY (`patientId`) REFERENCES `patient` (`patientId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `encounter` */

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `locationId` int(11) NOT NULL AUTO_INCREMENT,
  `locationName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `locationUrl` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `locationCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`locationId`),
  UNIQUE KEY `NewIndex1` (`locationCode`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `location` */

insert  into `location`(`locationId`,`locationName`,`locationUrl`,`locationCode`) values (1,'泰心系统','http://218.206.179.49:8080/Hospital/storageAccept.json?method=storageAcceptData','WSXHX'),(2,'心电系统','http://localhost:8080/ECG/insertEcgData.json','CMECG'),(3,'我尚运动','http://phr.cmri.cn/activity/DataGatePlatformInteract.do?action=JBQUploadData','WSYD'),(4,'睡眠系统','http://10.2.44.171:8080/SleepManage/service/insertSleep.json','CMCC_WSSM'),(5,'我尚健康','http://localhost:8080/CD','CMCC_WSJK'),(6,'中国移动研究院','http://10.2.57.4:8080/data_v2/DataGatePlatformInteract.do?action=JBQUploadData','CMRI'),(7,'testNmae','testurl',NULL),(8,'sda',NULL,NULL);

/*Table structure for table `observation` */

DROP TABLE IF EXISTS `observation`;

CREATE TABLE `observation` (
  `obsId` int(11) NOT NULL AUTO_INCREMENT,
  `obsDatetime` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `value` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `conceptId` int(11) NOT NULL DEFAULT '0',
  `patientId` int(11) DEFAULT NULL,
  `collectDate` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiveDateTime` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`obsId`,`obsDatetime`,`conceptId`),
  KEY `FK6AB4800CA1DB97AF` (`conceptId`),
  KEY `NewIndex1` (`patientId`),
  CONSTRAINT `FK_OBSERVATION_CONCEPT` FOREIGN KEY (`conceptId`) REFERENCES `concept` (`conceptId`) ON DELETE CASCADE,
  CONSTRAINT `FK_observation_patient` FOREIGN KEY (`patientId`) REFERENCES `patient` (`patientId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `observation` */

/*Table structure for table `patient` */

DROP TABLE IF EXISTS `patient`;

CREATE TABLE `patient` (
  `patientId` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `deviceId` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `appType` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`patientId`),
  KEY `appType` (`appType`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `patient` */

insert  into `patient`(`patientId`,`name`,`phone`,`deviceId`,`appType`) values (1,'test0','p0','null','AppA'),(6,'test4','p4','null','AppA'),(7,'test34','p34','null','AppA'),(8,'test2','p2','null','AppA'),(9,'test2','p2','null','AppA'),(10,'test72','p72','null','AppA'),(11,'test8','p8','null','AppA'),(12,'test12','p12','null','AppA'),(13,'test35','p35','null','AppA'),(14,'test11','p11','null','AppA'),(15,'test61','p61','null','AppA'),(16,'test3','p3','null','AppA'),(17,'test81','p81','null','AppA'),(18,'test15','p15','null','AppA'),(19,'test63','p63','null','AppA'),(20,'test59','p59','null','AppA'),(21,'test85','p85','null','AppA'),(22,'test57','p57','null','AppA'),(23,'test10','p10','null','AppA'),(24,'test95','p95','null','AppA'),(25,'test18','p18','null','AppA'),(26,'test9','p9','null','AppA'),(27,'test52','p52','null','AppA'),(28,'test37','p37','null','AppA'),(29,'test53','p53','null','AppA'),(30,'test49','p49','null','AppA'),(31,'test65','p65','null','AppA'),(32,'test92','p92','null','AppA'),(33,'test46','p46','null','AppA'),(34,'test97','p97','null','AppA'),(35,'test33','p33','null','AppA'),(36,'test77','p77','null','AppA'),(37,'test29','p29','null','AppA'),(38,'test19','p19','null','AppA'),(39,'test26','p26','null','AppA'),(40,'test17','p17','null','AppA'),(41,'test69','p69','null','AppA'),(42,'test96','p96','null','AppA'),(43,'test45','p45','null','AppA'),(44,'test60','p60','null','AppA'),(45,'test51','p51','null','AppA'),(46,'test1','15712804071','null','AppA'),(47,'test64','p64','null','AppA'),(48,'test38','p38','null','AppA'),(49,'test80','p80','null','AppA'),(50,'test98','p98','null','AppA'),(51,'test7','p7','null','AppA'),(52,'test62','p62','null','AppA'),(53,'test87','p87','null','AppA'),(54,'test75','p75','null','AppA'),(55,'test56','p56','null','AppA'),(56,'test86','p86','null','AppA'),(57,'test84','p84','null','AppA'),(58,'test25','p25','null','AppA'),(59,'test43','p43','null','AppA'),(60,'test24','p24','null','AppA'),(61,'test47','p47','null','AppA'),(62,'test71','p71','null','AppA'),(63,'test31','p31','null','AppA'),(64,'test23','p23','null','AppA'),(65,'test66','p66','null','AppA'),(66,'test58','p58','null','AppA'),(67,'test36','p36','null','AppA'),(68,'test91','p91','null','AppA'),(69,'test32','p32','null','AppA'),(70,'test93','p93','null','AppA'),(71,'test70','p70','null','AppA'),(72,'test73','p73','null','AppA'),(73,'test55','p55','null','AppA'),(74,'test22','p22','null','AppA'),(75,'test74','p74','null','AppA'),(76,'test83','p83','null','AppA'),(77,'test6','p6','null','AppA'),(78,'test40','p40','null','AppA'),(79,'test42','p42','null','AppA'),(80,'test39','p39','null','AppA'),(81,'test44','p44','null','AppA'),(82,'test30','p30','null','AppA'),(83,'test54','p54','null','AppA'),(84,'test21','p21','null','AppA'),(85,'test48','p48','null','AppA'),(86,'test79','p79','null','AppA'),(87,'test68','p68','null','AppA'),(88,'test94','p94','null','AppA'),(89,'test76','p76','null','AppA'),(90,'test14','p14','null','AppA'),(91,'test20','p20','null','AppA'),(92,'test27','p27','null','AppA'),(93,'test82','p82','null','AppA'),(94,'test78','p78','null','AppA'),(95,'test99','p99','null','AppA'),(96,'test16','p16','null','AppA'),(97,'test28','p28','null','AppA'),(98,'test5','p5','null','AppA'),(99,'test50','p50','null','AppA'),(100,'test90','p90','null','AppA'),(101,'test88','p88','null','AppA'),(102,'test41','p41','null','AppA'),(103,'test13','p13','null','AppA'),(104,'test89','p89','null','AppA'),(105,'test67','p67','null','AppA');

/*Table structure for table `patient_location` */

DROP TABLE IF EXISTS `patient_location`;

CREATE TABLE `patient_location` (
  `plid` int(10) NOT NULL AUTO_INCREMENT,
  `patientId` int(50) NOT NULL,
  `locationId` int(11) NOT NULL,
  `remark` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`plid`),
  KEY `FK_patient_location_patient` (`patientId`),
  KEY `FK_patient_location_location` (`locationId`),
  CONSTRAINT `FK_patient_location_location` FOREIGN KEY (`locationId`) REFERENCES `location` (`locationId`) ON DELETE CASCADE,
  CONSTRAINT `FK_patient_location_patients` FOREIGN KEY (`patientId`) REFERENCES `patient` (`patientId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `patient_location` */

insert  into `patient_location`(`plid`,`patientId`,`locationId`,`remark`) values (1,1,3,NULL);

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
