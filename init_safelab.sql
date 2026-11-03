-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: safelab_db
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `alert_metrics`
--

DROP TABLE IF EXISTS `alert_metrics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert_metrics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `alert_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK16k31xthd04glkyhdm8svu1hx` (`alert_id`),
  CONSTRAINT `FK16k31xthd04glkyhdm8svu1hx` FOREIGN KEY (`alert_id`) REFERENCES `alerts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert_metrics`
--

LOCK TABLES `alert_metrics` WRITE;
/*!40000 ALTER TABLE `alert_metrics` DISABLE KEYS */;
INSERT INTO `alert_metrics` VALUES (1,'2026-10-07 02:31:18.051000','2026-10-07 02:31:18.051000','currentValue','8.5°C',1),(2,'2026-10-07 02:31:18.055000','2026-10-07 02:31:18.055000','threshold','8.0°C',1),(3,'2026-10-07 02:31:18.056000','2026-10-07 02:31:18.056000','exceededBy','0.5°C',1),(4,'2026-10-07 02:31:18.056000','2026-10-07 02:31:18.056000','sensorType','NTC Thermistor',1),(5,'2026-10-07 02:31:18.057000','2026-10-07 02:31:18.057000','lastCalibration','2026-07-07',1),(6,'2026-10-07 02:31:18.058000','2026-10-07 02:31:18.058000','signalStrength','98%',1),(7,'2026-10-07 02:31:18.059000','2026-10-07 02:31:18.059000','networkStatus','ONLINE',1),(8,'2026-10-07 02:31:18.059000','2026-10-07 02:31:18.059000','automationRuleName','Cooling Boost Regulation',1),(9,'2026-10-07 02:31:18.060000','2026-10-07 02:31:18.060000','automationRuleStatus','Running',1),(10,'2026-10-07 02:31:18.061000','2026-10-07 02:31:18.061000','automationRuleDesc','Increases cooling flow when temp limit breaches.',1),(11,'2026-10-07 02:31:18.064000','2026-10-07 02:31:18.064000','currentValue','-170.0°C',2),(12,'2026-10-07 02:31:18.066000','2026-10-07 02:31:18.066000','threshold','-180.0°C',2),(13,'2026-10-07 02:31:18.066000','2026-10-07 02:31:18.066000','exceededBy','10.0°C',2),(14,'2026-10-07 02:31:18.067000','2026-10-07 02:31:18.067000','sensorType','Cryogenic RTD Probe',2),(15,'2026-10-07 02:31:18.068000','2026-10-07 02:31:18.068000','lastCalibration','2026-08-07',2),(16,'2026-10-07 02:31:18.068000','2026-10-07 02:31:18.068000','networkStatus','ONLINE',2),(17,'2026-10-07 02:31:18.070000','2026-10-07 02:31:18.070000','currentValue','1050 ppm',3),(18,'2026-10-07 02:31:18.071000','2026-10-07 02:31:18.071000','threshold','1000 ppm',3),(19,'2026-10-07 02:31:18.072000','2026-10-07 02:31:18.072000','exceededBy','50 ppm',3),(20,'2026-10-07 02:31:18.073000','2026-10-07 02:31:18.073000','sensorType','NIDR CO2 Sensor',3),(21,'2026-10-07 02:31:18.075000','2026-10-07 02:31:18.075000','currentValue','3.5 mm/s',4),(22,'2026-10-07 02:31:18.076000','2026-10-07 02:31:18.076000','threshold','3.0 mm/s',4),(23,'2026-10-07 02:31:18.077000','2026-10-07 02:31:18.077000','exceededBy','0.5 mm/s',4),(24,'2026-10-07 02:31:18.079000','2026-10-07 02:31:18.079000','currentValue','42.5%',5),(25,'2026-10-07 02:31:18.080000','2026-10-07 02:31:18.080000','exceededValue','62.0%',5),(26,'2026-10-07 02:31:18.080000','2026-10-07 02:31:18.080000','threshold','60.0%',5),(27,'2026-10-07 02:31:18.083000','2026-10-07 02:31:18.083000','currentValue','45.0 Pa',6),(28,'2026-10-07 02:31:18.084000','2026-10-07 02:31:18.084000','threshold','50.0 Pa',6),(29,'2026-10-07 02:31:18.085000','2026-10-07 02:31:18.085000','exceededBy','-5.0 Pa',6),(30,'2026-10-07 02:31:18.086000','2026-10-07 02:31:18.086000','sensorType','Differential Pressure Transducer',6),(31,'2026-10-07 02:31:18.087000','2026-10-07 02:31:18.087000','lastCalibration','2026-09-07',6),(32,'2026-10-07 02:31:18.087000','2026-10-07 02:31:18.087000','networkStatus','ONLINE',6),(33,'2026-10-07 02:31:18.089000','2026-10-07 02:31:18.089000','currentValue','75.0 AQI',7),(34,'2026-10-07 02:31:18.090000','2026-10-07 02:31:18.090000','threshold','50.0 AQI',7),(35,'2026-10-07 02:31:18.091000','2026-10-07 02:31:18.091000','exceededBy','25.0 AQI',7),(36,'2026-10-07 02:31:18.092000','2026-10-07 02:31:18.092000','sensorType','Particulate Matter Laser Counter',7),(37,'2026-10-07 02:31:18.093000','2026-10-07 02:31:18.093000','lastCalibration','2026-06-07',7),(38,'2026-10-07 02:31:18.094000','2026-10-07 02:31:18.094000','networkStatus','ONLINE',7),(39,'2026-10-07 02:31:18.096000','2026-10-07 02:31:18.096000','currentValue','120.0 AQI',8),(40,'2026-10-07 02:31:18.097000','2026-10-07 02:31:18.097000','threshold','50.0 AQI',8),(41,'2026-10-07 02:31:18.098000','2026-10-07 02:31:18.098000','exceededBy','70.0 AQI',8),(42,'2026-10-07 02:31:18.098000','2026-10-07 02:31:18.098000','sensorType','Metal Oxide Gas Sensor',8),(43,'2026-10-07 02:31:18.099000','2026-10-07 02:31:18.099000','lastCalibration','2026-07-07',8),(44,'2026-10-07 02:31:18.100000','2026-10-07 02:31:18.100000','networkStatus','ONLINE',8),(45,'2026-10-07 02:31:18.102000','2026-10-07 02:31:18.102000','currentValue','850.0 hPa',9),(46,'2026-10-07 02:31:18.103000','2026-10-07 02:31:18.103000','threshold','900.0 hPa',9),(47,'2026-10-07 02:31:18.104000','2026-10-07 02:31:18.104000','exceededBy','-50.0 hPa',9),(48,'2026-10-07 02:31:18.105000','2026-10-07 02:31:18.105000','sensorType','Barometric Pressure Sensor',9),(49,'2026-10-07 02:31:18.106000','2026-10-07 02:31:18.106000','lastCalibration','2026-09-07',9),(50,'2026-10-07 02:31:18.107000','2026-10-07 02:31:18.107000','networkStatus','ONLINE',9),(51,'2026-10-07 02:31:18.110000','2026-10-07 02:31:18.110000','currentValue','38.5°C',10),(52,'2026-10-07 02:31:18.111000','2026-10-07 02:31:18.111000','threshold','37.5°C',10),(53,'2026-10-07 02:31:18.112000','2026-10-07 02:31:18.112000','exceededBy','1.0°C',10),(54,'2026-10-07 02:31:18.112000','2026-10-07 02:31:18.112000','sensorType','RTD Probe',10),(55,'2026-10-07 02:31:18.113000','2026-10-07 02:31:18.113000','lastCalibration','2026-08-07',10),(56,'2026-10-07 02:31:18.115000','2026-10-07 02:31:18.115000','networkStatus','ONLINE',10),(57,'2026-10-07 02:31:18.118000','2026-10-07 02:31:18.118000','currentValue','3.2 mm/s',11),(58,'2026-10-07 02:31:18.118000','2026-10-07 02:31:18.118000','threshold','3.0 mm/s',11),(59,'2026-10-07 02:31:18.119000','2026-10-07 02:31:18.119000','exceededBy','0.2 mm/s',11),(60,'2026-10-07 02:31:18.120000','2026-10-07 02:31:18.120000','sensorType','Piezoelectric Accelerometer',11),(61,'2026-10-07 02:31:18.120000','2026-10-07 02:31:18.120000','lastCalibration','2026-08-07',11),(62,'2026-10-07 02:31:18.122000','2026-10-07 02:31:18.122000','currentValue','940.0 hPa',12),(63,'2026-10-07 02:31:18.123000','2026-10-07 02:31:18.123000','threshold','950.0 hPa',12),(64,'2026-10-07 02:31:18.124000','2026-10-07 02:31:18.124000','exceededBy','-10.0 hPa',12),(65,'2026-10-07 02:31:18.127000','2026-10-07 02:31:18.127000','currentValue','26.5°C',13),(66,'2026-10-07 02:31:18.128000','2026-10-07 02:31:18.128000','threshold','25.0°C',13),(67,'2026-10-07 02:31:18.129000','2026-10-07 02:31:18.129000','exceededBy','1.5°C',13),(68,'2026-10-07 02:31:18.131000','2026-10-07 02:31:18.131000','duration','15 minutes',14),(69,'2026-10-07 02:31:18.133000','2026-10-07 02:31:18.133000','backupStatus','DISENGAGED',14),(70,'2026-10-07 02:31:18.134000','2026-10-07 02:31:18.134000','currentState','STABLE',14),(71,'2026-10-07 02:31:18.136000','2026-10-07 02:31:18.136000','currentValue','1.5%',15),(72,'2026-10-07 02:31:18.138000','2026-10-07 02:31:18.138000','exceededValue','4.8%',15),(73,'2026-10-07 02:31:18.139000','2026-10-07 02:31:18.139000','threshold','2.0%',15),(74,'2026-10-07 02:31:18.142000','2026-10-07 02:31:18.142000','currentValue','5.2°C',16),(75,'2026-10-07 02:31:18.143000','2026-10-07 02:31:18.143000','threshold','8.0°C',16),(76,'2026-10-07 02:31:18.146000','2026-10-07 02:31:18.146000','currentValue','1.2 mm/s',17),(77,'2026-10-07 02:31:18.147000','2026-10-07 02:31:18.147000','threshold','3.0 mm/s',17),(78,'2026-10-07 02:31:18.149000','2026-10-07 02:31:18.149000','currentValue','1012 hPa',18),(79,'2026-10-07 02:31:18.151000','2026-10-07 02:31:18.151000','threshold','950 hPa',18);
/*!40000 ALTER TABLE `alert_metrics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alerts`
--

DROP TABLE IF EXISTS `alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alerts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lab_name` varchar(255) DEFAULT NULL,
  `severity` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `time_ago` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint DEFAULT NULL,
  `sensor_configuration_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcblqfqrly6l8k9nt3gjieomdn` (`laboratory_id`),
  KEY `FKo46g2krvten67mk0xk7cf8n4d` (`sensor_configuration_id`),
  CONSTRAINT `FKcblqfqrly6l8k9nt3gjieomdn` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`),
  CONSTRAINT `FKo46g2krvten67mk0xk7cf8n4d` FOREIGN KEY (`sensor_configuration_id`) REFERENCES `sensor_configurations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerts`
--

LOCK TABLES `alerts` WRITE;
/*!40000 ALTER TABLE `alerts` DISABLE KEYS */;
INSERT INTO `alerts` VALUES (1,'2026-10-07 02:31:18.046000','2026-10-07 02:31:18.210000','Sensor T-01 registered 8.5°C in Refrigerator B2, exceeding the maximum limit of 8.0°C.','Bio-Safety Lab 04','CRITICAL','ACTIVE','2 hours ago','Temperature Threshold Exceeded',1,1),(2,'2026-10-07 02:31:18.063000','2026-10-07 02:31:18.210000','Sensor T-04 registered -170.0°C in LN2 Tank Cryo-01, warmer than the critical limit of -180.0°C.','Cryogenic Storage Room','CRITICAL','ACTIVE','3 hours ago','Cryo LN2 Temperature Critical Alert',3,4),(3,'2026-10-07 02:31:18.069000','2026-10-07 02:31:18.210000','Sensor C-03 registered 1050 ppm, warning threshold is 1000 ppm.','Chemical Synthesis Lab','WARNING','ACTIVE','4 hours ago','CO2 Level Warning',2,3),(4,'2026-10-07 02:31:18.073000','2026-10-07 02:31:18.210000','Sensor V-08 registered 3.5 mm/s, warning threshold is 3.0 mm/s.','Chemical Synthesis Lab','WARNING','ACTIVE','6 hours ago','Reactor Vibration Spike',2,7),(5,'2026-10-07 02:31:18.078000','2026-10-07 02:31:18.210000','Sensor H-02 registered 62.0% (Limit: 60.0%) but returned to normal range (42.5%).','Bio-Safety Lab 04','WARNING','RESOLVED','1 day ago','Humidity Levels Restored',1,2),(6,'2026-10-07 02:31:18.082000','2026-10-07 02:31:18.210000','Sensor P-11 registered 45.0 Pa in HEPA Filtration Unit H-1, below the minimum differential pressure of 50.0 Pa.','Clean Room Class 100','CRITICAL','ACTIVE','5 hours ago','HEPA Filter Pressure Failure',6,11),(7,'2026-10-07 02:31:18.088000','2026-10-07 02:31:18.210000','Sensor A-12 registered 75.0 AQI in Clean Room Class 100, exceeding the strict room limit of 50.0 AQI.','Clean Room Class 100','CRITICAL','ACTIVE','4 hours ago','Clean Room Air Quality Hazard',6,12),(8,'2026-10-07 02:31:18.095000','2026-10-07 02:31:18.210000','Sensor A-19 registered 120.0 AQI in Fume Hood F-09, indicating a potential volatile organic compound leak.','Toxicology Isolation Suite','CRITICAL','ACTIVE','3 hours ago','Toxic Gas Air Quality Spike',11,19),(9,'2026-10-07 02:31:18.101000','2026-10-07 02:31:18.210000','Sensor P-20 registered 850.0 hPa, warmer/higher than negative pressure critical threshold of 900.0 hPa.','Toxicology Isolation Suite','CRITICAL','ACTIVE','4 hours ago','Negative Pressure Containment Loss',11,20),(10,'2026-10-07 02:31:18.108000','2026-10-07 02:31:18.210000','Sensor T-18 registered 38.5°C in Anaerobic Chamber AC-1, exceeding warning threshold of 37.5°C.','Microbiology Incubator Zone','WARNING','ACTIVE','10 hours ago','Incubator Temperature Deviation',10,18),(11,'2026-10-07 02:31:18.116000','2026-10-07 02:31:18.211000','Sensor V-21 registered 3.2 mm/s on Optical Table OT-04, exceeding warning limit of 3.0 mm/s.','Laser Optics & Physics Lab','WARNING','ACTIVE','12 hours ago','Laser Bench Vibration Warning',12,21),(12,'2026-10-07 02:31:18.121000','2026-10-07 02:31:18.211000','Sensor P-14 registered 940.0 hPa in Radiation Facility, below warning threshold of 950.0 hPa.','Radiation Research Facility','WARNING','ACTIVE','8 hours ago','Low Room Pressure Warning',7,14),(13,'2026-10-07 02:31:18.126000','2026-10-07 02:31:18.211000','Sensor T-17 registered 26.5°C on Sequencer Seq-02, exceeding warning limit of 25.0°C.','Genetics Sequencing Center','WARNING','ACTIVE','9 hours ago','Sequencing Room Temp Warning',9,17),(14,'2026-10-07 02:31:18.130000','2026-10-07 02:31:18.211000','Primary power grid connection lost but secondary backup generator triggered; main power successfully restored.','Molecular Biology Lab','CRITICAL','RESOLVED','18 hours ago','Power Interruption Restored',5,9),(15,'2026-10-07 02:31:18.135000','2026-10-07 02:31:18.211000','O2 concentration exceeded warning threshold of 2.0% but returned to stable 1.5% after chamber flush.','Microbiology Incubator Zone','WARNING','RESOLVED','1 day ago','O2 Concentration Normalized',10,18),(16,'2026-10-07 02:31:18.140000','2026-10-07 02:31:18.211000','Temperature anomaly resolved. Ambient temp returned to normal range (5.2°C).','Bio-Safety Lab 04','CRITICAL','RESOLVED','1 day ago','Incubator Temp Alarm Resolved',1,1),(17,'2026-10-07 02:31:18.145000','2026-10-07 02:31:18.211000','Vibration anomaly on Reactor R-02 resolved; current levels stable at 1.2 mm/s.','Chemical Synthesis Lab','WARNING','RESOLVED','1 day ago','Vibration Peak Dampened',2,7),(18,'2026-10-07 02:31:18.148000','2026-10-07 02:31:18.211000','Pressure sensor fluctuation resolved. Ambient pressure normalized to 1012 hPa.','Cryogenic Storage Room','WARNING','RESOLVED','2 days ago','Cryo Room Pressure Restored',3,5);
/*!40000 ALTER TABLE `alerts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_trails`
--

DROP TABLE IF EXISTS `audit_trails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_trails` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `target_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_audit_trails_target_id` (`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_trails`
--

LOCK TABLES `audit_trails` WRITE;
/*!40000 ALTER TABLE `audit_trails` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_trails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `automation_rules`
--

DROP TABLE IF EXISTS `automation_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `automation_rules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `actions` text,
  `active` bit(1) NOT NULL,
  `auto_resolve` bit(1) DEFAULT NULL,
  `execution_limit_mins` int DEFAULT NULL,
  `last_triggered` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `trigger_duration` int DEFAULT NULL,
  `trigger_metric` varchar(255) DEFAULT NULL,
  `trigger_operator` varchar(255) DEFAULT NULL,
  `trigger_unit` varchar(255) DEFAULT NULL,
  `trigger_value` double DEFAULT NULL,
  `workspace_id` bigint NOT NULL,
  `specific_lab_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8xv9byc1d52g5tuo38wym02ex` (`specific_lab_id`),
  CONSTRAINT `FK8xv9byc1d52g5tuo38wym02ex` FOREIGN KEY (`specific_lab_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `automation_rules`
--

LOCK TABLES `automation_rules` WRITE;
/*!40000 ALTER TABLE `automation_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `automation_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calibration_records`
--

DROP TABLE IF EXISTS `calibration_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calibration_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `calibrated_at` datetime(6) DEFAULT NULL,
  `certificate_id` varchar(255) DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `sensor_configuration_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk9m2ie61cwalgu1w251iiaohs` (`sensor_configuration_id`),
  CONSTRAINT `FKk9m2ie61cwalgu1w251iiaohs` FOREIGN KEY (`sensor_configuration_id`) REFERENCES `sensor_configurations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calibration_records`
--

LOCK TABLES `calibration_records` WRITE;
/*!40000 ALTER TABLE `calibration_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `calibration_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compliance_reports`
--

DROP TABLE IF EXISTS `compliance_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compliance_reports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `digital_signature` varchar(255) DEFAULT NULL,
  `generated_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `period_end` datetime(6) DEFAULT NULL,
  `period_start` datetime(6) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa3lgcjesk82wwvjlaijqst98r` (`laboratory_id`),
  CONSTRAINT `FKa3lgcjesk82wwvjlaijqst98r` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compliance_reports`
--

LOCK TABLES `compliance_reports` WRITE;
/*!40000 ALTER TABLE `compliance_reports` DISABLE KEYS */;
INSERT INTO `compliance_reports` VALUES (1,'2026-10-07 02:31:18.180000','2026-10-07 02:31:18.180000',NULL,'2026-10-02 02:31:18.180468','Environmental Compliance Audit','May 2026','2026-09-07 02:31:18.180468','2026-08-28 02:31:18.180468','2.4 MB','READY','Safety Compliance',1),(2,'2026-10-07 02:31:18.184000','2026-10-07 02:31:18.184000',NULL,'2026-10-05 02:31:18.184112','Ventilation and HEPA Log','June 2026','2026-10-02 02:31:18.184112','2026-09-17 02:31:18.184112','1.8 MB','READY','System Log',1),(3,'2026-10-07 02:31:18.185000','2026-10-07 02:31:18.185000',NULL,'2026-10-06 02:31:18.185151','Chemical Storage Safety Assessment','Q2 2026','2026-10-07 02:31:18.185151','2026-07-07 02:31:18.185151','4.1 MB','READY','Safety Compliance',2);
/*!40000 ALTER TABLE `compliance_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corrective_actions`
--

DROP TABLE IF EXISTS `corrective_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corrective_actions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `action_taken` text,
  `performed_at` datetime(6) DEFAULT NULL,
  `technician_id` bigint NOT NULL,
  `incident_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKif88lacnc0satc22j6g9j4xvw` (`incident_id`),
  CONSTRAINT `FKrkyih4ga1hxcm5ps2t3mgsiup` FOREIGN KEY (`incident_id`) REFERENCES `incidents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corrective_actions`
--

LOCK TABLES `corrective_actions` WRITE;
/*!40000 ALTER TABLE `corrective_actions` DISABLE KEYS */;
/*!40000 ALTER TABLE `corrective_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment_thresholds`
--

DROP TABLE IF EXISTS `equipment_thresholds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment_thresholds` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `current_value` double DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `max_threshold` double DEFAULT NULL,
  `min_threshold` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `warning_at` double DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK56sf80jfq4nk9hrrlw2fwg1q5` (`laboratory_id`),
  CONSTRAINT `FK56sf80jfq4nk9hrrlw2fwg1q5` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment_thresholds`
--

LOCK TABLES `equipment_thresholds` WRITE;
/*!40000 ALTER TABLE `equipment_thresholds` DISABLE KEYS */;
INSERT INTO `equipment_thresholds` VALUES (1,'2026-10-07 02:31:16.872000','2026-10-07 02:31:16.872000',-80.2,'biotech',-75,-86,'ULT Freezer F-07','normal','°C',-78,1),(2,'2026-10-07 02:31:16.875000','2026-10-07 02:31:16.875000',8.5,'biotech',8,2,'Refrigerator B2','critical','°C',7,1),(3,'2026-10-07 02:31:16.877000','2026-10-07 02:31:16.877000',1050,'biotech',1200,400,'CO2 Incubator CM-01','warning','ppm',1000,2),(4,'2026-10-07 02:31:16.879000','2026-10-07 02:31:16.879000',3.5,'industrial',3,0,'Reactor R-02','warning','mm/s',2,2),(5,'2026-10-07 02:31:16.882000','2026-10-07 02:31:16.882000',-170,'biotech',-180,-200,'LN2 Tank Cryo-01','critical','°C',-185,3),(6,'2026-10-07 02:31:16.883000','2026-10-07 02:31:16.883000',150,'analytical',350,30,'GC-MS Column Oven','normal','°C',300,4),(7,'2026-10-07 02:31:16.886000','2026-10-07 02:31:16.886000',94.8,'biotech',98,4,'PCR Thermocycler T-12','normal','°C',95,5),(8,'2026-10-07 02:31:16.887000','2026-10-07 02:31:16.887000',45,'industrial',150,50,'HEPA Filtration Unit H-1','critical','Pa',120,6),(9,'2026-10-07 02:31:16.889000','2026-10-07 02:31:16.889000',12,'analytical',100,0,'Gamma Counter GC-03','normal','cpm',80,7),(10,'2026-10-07 02:31:16.891000','2026-10-07 02:31:16.891000',43,'industrial',50,10,'Granulator G-08','warning','rpm',40,8),(11,'2026-10-07 02:31:16.893000','2026-10-07 02:31:16.893000',26.5,'biotech',25,18,'Sequencer Seq-02','warning','°C',22,9),(12,'2026-10-07 02:31:16.894000','2026-10-07 02:31:16.894000',4.8,'biotech',5,0,'Anaerobic Chamber AC-1','warning','%O2',2,10),(13,'2026-10-07 02:31:16.896000','2026-10-07 02:31:16.896000',0.2,'industrial',0.8,0.3,'Fume Hood F-09','critical','m/s',0.5,11),(14,'2026-10-07 02:31:16.897000','2026-10-07 02:31:16.897000',3.2,'industrial',3,0,'Optical Table OT-04','warning','mm/s',2,12);
/*!40000 ALTER TABLE `equipment_thresholds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_settings`
--

DROP TABLE IF EXISTS `general_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_settings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `setting_key` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKitw0rldwqms701br3h4v6acj7` (`setting_key`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_settings`
--

LOCK TABLES `general_settings` WRITE;
/*!40000 ALTER TABLE `general_settings` DISABLE KEYS */;
INSERT INTO `general_settings` VALUES (1,'2026-10-07 02:31:16.658000','2026-10-07 02:31:16.658000','alert_thresholds','Deviation for warning visual state','tempWarningLevel','2c'),(2,'2026-10-07 02:31:16.662000','2026-10-07 02:31:16.662000','alert_thresholds','Deviation for critical push notification','tempCriticalLevel','5c'),(3,'2026-10-07 02:31:16.665000','2026-10-07 02:31:16.665000','reports','Daily snapshot summary report','dailySummary','false'),(4,'2026-10-07 02:31:16.667000','2026-10-07 02:31:16.667000','reports','Weekly trend and compliance report','weeklyReport','false'),(5,'2026-10-07 02:31:16.668000','2026-10-07 02:31:16.668000','notifications','Instant alert for connection loss','instantSensorAlerts','true'),(6,'2026-10-07 02:31:16.671000','2026-10-07 02:31:16.671000','general','Coordinator notification phone number','coordinatorPhone','+1 (555) 000-0000'),(7,'2026-10-07 02:31:16.673000','2026-10-07 02:31:16.673000','general','Silence alerts during quiet hours','quietHoursEnabled','true'),(8,'2026-10-07 02:31:16.674000','2026-10-07 02:31:16.674000','general','Start of quiet hours','quietHoursStart','10:00 PM'),(9,'2026-10-07 02:31:16.677000','2026-10-07 02:31:16.677000','general','End of quiet hours','quietHoursEnd','06:00 AM'),(10,'2026-10-07 02:31:18.262000','2026-10-07 02:31:18.262000','max_seeded_ids','Maximum seeded ID for users','max_seeded_user_id','1'),(11,'2026-10-07 02:31:18.277000','2026-10-07 02:31:18.277000','max_seeded_ids','Maximum seeded ID for user_profiles','max_seeded_profile_id','1'),(12,'2026-10-07 02:31:18.294000','2026-10-07 02:31:18.294000','max_seeded_ids','Maximum seeded ID for user_workspace_accesses','max_seeded_workspace_access_id','1'),(13,'2026-10-07 02:31:18.305000','2026-10-07 02:31:18.305000','max_seeded_ids','Maximum seeded ID for laboratories','max_seeded_laboratory_id','12'),(14,'2026-10-07 02:31:18.316000','2026-10-07 02:31:18.316000','max_seeded_ids','Maximum seeded ID for workspaces','max_seeded_workspace_id','1'),(15,'2026-10-07 02:31:18.326000','2026-10-07 02:31:18.326000','max_seeded_ids','Maximum seeded ID for sensor_configurations','max_seeded_sensor_id','22'),(16,'2026-10-07 02:31:18.336000','2026-10-07 02:31:18.336000','max_seeded_ids','Maximum seeded ID for sensor_readings','max_seeded_reading_id','1029'),(17,'2026-10-07 02:31:18.348000','2026-10-07 02:31:18.348000','max_seeded_ids','Maximum seeded ID for alerts','max_seeded_alert_id','18'),(18,'2026-10-07 02:31:18.358000','2026-10-07 02:31:18.358000','max_seeded_ids','Maximum seeded ID for history_records','max_seeded_history_id','25'),(19,'2026-10-07 02:31:18.367000','2026-10-07 02:31:18.367000','max_seeded_ids','Maximum seeded ID for lab_user_accesses','max_seeded_lab_access_id','12'),(20,'2026-10-07 02:31:18.377000','2026-10-07 02:31:18.377000','max_seeded_ids','Maximum seeded ID for notification_preferences','max_seeded_notification_pref_id','3'),(21,'2026-10-07 02:31:18.386000','2026-10-07 02:31:18.386000','max_seeded_ids','Maximum seeded ID for storage_units','max_seeded_storage_unit_id','0'),(22,'2026-10-07 02:31:18.396000','2026-10-07 02:31:18.396000','max_seeded_ids','Maximum seeded ID for mitigation_systems','max_seeded_mitigation_system_id','0'),(23,'2026-10-07 02:31:18.406000','2026-10-07 02:31:18.406000','max_seeded_ids','Maximum seeded ID for lab_metric_subscriptions','max_seeded_metric_sub_id','35'),(24,'2026-10-07 02:31:18.417000','2026-10-07 02:31:18.417000','max_seeded_ids','Maximum seeded ID for lab_metrics','max_seeded_metric_id','27'),(25,'2026-10-07 02:31:18.432000','2026-10-07 02:31:18.432000','max_seeded_ids','Maximum seeded ID for lab_activities','max_seeded_activity_id','0'),(26,'2026-10-07 02:31:18.441000','2026-10-07 02:31:18.441000','max_seeded_ids','Maximum seeded ID for lab_schedules','max_seeded_schedule_id','0'),(27,'2026-10-07 02:31:18.450000','2026-10-07 02:31:18.450000','max_seeded_ids','Maximum seeded ID for equipment_thresholds','max_seeded_equipment_threshold_id','14'),(28,'2026-10-07 02:31:18.462000','2026-10-07 02:31:18.462000','max_seeded_ids','Maximum seeded ID for calibration_records','max_seeded_calibration_record_id','0'),(29,'2026-10-07 02:31:18.475000','2026-10-07 02:31:18.475000','max_seeded_ids','Maximum seeded ID for alert_metrics','max_seeded_alert_metric_id','79'),(30,'2026-10-07 02:31:18.484000','2026-10-07 02:31:18.484000','max_seeded_ids','Maximum seeded ID for compliance_reports','max_seeded_compliance_report_id','3');
/*!40000 ALTER TABLE `general_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_records`
--

DROP TABLE IF EXISTS `history_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `occurred_at` datetime(6) DEFAULT NULL,
  `severity` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgmr7o3j4dn1h2np2mbytj0yl8` (`laboratory_id`),
  CONSTRAINT `FKgmr7o3j4dn1h2np2mbytj0yl8` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_records`
--

LOCK TABLES `history_records` WRITE;
/*!40000 ALTER TABLE `history_records` DISABLE KEYS */;
INSERT INTO `history_records` VALUES (1,'2026-10-07 02:31:18.152000','2026-10-07 02:31:18.152000','Sensor T-01 registered 8.5°C (Limit: 8.0°C) in Refrigerator B2.','incident','Temperature Critical Alarm','2026-10-07 00:31:18.151537','Critical','Active',1),(2,'2026-10-07 02:31:18.155000','2026-10-07 02:31:18.155000','Automation: Cooling Boost Regulation activated for zone Bio-Safety Lab 04.','automation','Automation rule triggered','2026-10-07 00:32:18.155192','Info','Active',1),(3,'2026-10-07 02:31:18.156000','2026-10-07 02:31:18.156000','Sensor T-04 registered -170.0°C in LN2 Tank Cryo-01.','incident','Cryo LN2 Critical Alert','2026-10-06 23:31:18.156818','Critical','Active',3),(4,'2026-10-07 02:31:18.157000','2026-10-07 02:31:18.157000','CO2 level exceeded warning threshold (1050 ppm).','incident','CO2 Warning Active','2026-10-06 22:31:18.157356','Warning','Active',2),(5,'2026-10-07 02:31:18.158000','2026-10-07 02:31:18.158000','Vibration level exceeded warning limit on Reactor R-02 (3.5 mm/s).','incident','Vibration Spike Detected','2026-10-06 20:31:18.158415','Warning','Active',2),(6,'2026-10-07 02:31:18.159000','2026-10-07 02:31:18.159000','Humidity levels returned to normal range (42.5%).','incident','Sensor H-02 Warning Resolved','2026-10-06 02:31:18.159478','Info','Resolved',1),(7,'2026-10-07 02:31:18.161000','2026-10-07 02:31:18.161000','Technician validated secondary cooling pressure gauge. Value: 3.2 bar (Normal).','observation','Manual safety audit logged','2026-10-06 14:31:18.161059','Info','Normal',1),(8,'2026-10-07 02:31:18.162000','2026-10-07 02:31:18.162000','Analytical GC-MS column successfully calibrated by supervisor.','maintenance','HPLC Column Calibrated','2026-10-05 22:31:18.162104','Info','Completed',4),(9,'2026-10-07 02:31:18.163000','2026-10-07 02:31:18.163000','Cryo room safety valve inspection successfully completed.','maintenance','Pressure check completed','2026-10-05 02:31:18.163142','Info','Completed',3),(10,'2026-10-07 02:31:18.164000','2026-10-07 02:31:18.164000','Assigned Operator role access permissions to SafeLab Operator.','system','Workspace access updated','2026-10-05 01:31:18.164702','Info','Completed',2),(11,'2026-10-07 02:31:18.165000','2026-10-07 02:31:18.165000','Sensor P-11 registered 45.0 Pa (Limit: 50.0 Pa) in Clean Room.','incident','HEPA Filter Pressure Failure','2026-10-06 21:31:18.165737','Critical','Active',6),(12,'2026-10-07 02:31:18.166000','2026-10-07 02:31:18.166000','Sensor A-12 registered 75.0 AQI (Limit: 50.0 AQI).','incident','Clean Room Air Quality Hazard','2026-10-06 22:31:18.166250','Critical','Active',6),(13,'2026-10-07 02:31:18.167000','2026-10-07 02:31:18.167000','Sensor A-19 registered 120.0 AQI (Limit: 50.0 AQI) in Fume Hood.','incident','Toxic Gas Air Quality Spike','2026-10-06 23:31:18.167290','Critical','Active',11),(14,'2026-10-07 02:31:18.167000','2026-10-07 02:31:18.167000','Sensor P-20 registered 850.0 hPa (Limit: 900.0 hPa).','incident','Negative Pressure Containment Loss','2026-10-06 22:31:18.167806','Critical','Active',11),(15,'2026-10-07 02:31:18.168000','2026-10-07 02:31:18.168000','Sensor T-18 registered 38.5°C (Limit: 37.5°C) in AC-1.','incident','Incubator Temperature Deviation','2026-10-06 16:31:18.168322','Warning','Active',10),(16,'2026-10-07 02:31:18.169000','2026-10-07 02:31:18.169000','Sensor V-21 registered 3.2 mm/s on Optical Table.','incident','Laser Bench Vibration Warning','2026-10-06 14:31:18.168841','Warning','Active',12),(17,'2026-10-07 02:31:18.169000','2026-10-07 02:31:18.169000','Sensor P-14 registered 940.0 hPa in Radiation Facility.','incident','Low Room Pressure Warning','2026-10-06 18:31:18.169884','Warning','Active',7),(18,'2026-10-07 02:31:18.170000','2026-10-07 02:31:18.170000','Sensor T-17 registered 26.5°C (Limit: 25.0°C).','incident','Sequencing Room Temp Warning','2026-10-06 17:31:18.170982','Warning','Active',9),(19,'2026-10-07 02:31:18.172000','2026-10-07 02:31:18.172000','Backup generator disengaged, grid power restored successfully.','incident','Power Interruption Resolved','2026-10-06 08:31:18.171504','Info','Resolved',5),(20,'2026-10-07 02:31:18.173000','2026-10-07 02:31:18.173000','Oxygen levels returned to normal 1.5%.','incident','O2 Concentration Normalized','2026-10-06 02:31:18.172594','Info','Resolved',10),(21,'2026-10-07 02:31:18.174000','2026-10-07 02:31:18.174000','Temperature returned to normal range (5.2°C).','incident','Incubator Temp Alarm Resolved','2026-10-06 02:31:18.174186','Info','Resolved',1),(22,'2026-10-07 02:31:18.175000','2026-10-07 02:31:18.175000','Vibration level stabilized at 1.2 mm/s on Reactor.','incident','Vibration Peak Dampened','2026-10-06 02:31:18.175243','Info','Resolved',2),(23,'2026-10-07 02:31:18.176000','2026-10-07 02:31:18.176000','Flashing pressure resolved, stabilized at 1012 hPa.','incident','Cryo Room Pressure Restored','2026-10-05 02:31:18.176291','Info','Resolved',3),(24,'2026-10-07 02:31:18.177000','2026-10-07 02:31:18.177000','Routine safety review completed on PCR Thermocycler T-12.','maintenance','PCR Thermocycler Inspection','2026-10-06 12:31:18.177860','Info','Completed',5),(25,'2026-10-07 02:31:18.178000','2026-10-07 02:31:18.178000','HEPA primary stage filters scheduled for replacement next week.','maintenance','HEPA Filter Replacement','2026-10-05 02:31:18.178894','Info','Scheduled',6);
/*!40000 ALTER TABLE `history_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iam_roles`
--

DROP TABLE IF EXISTS `iam_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iam_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9g3wd8l1lahhddlxak3bvg3i9` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iam_roles`
--

LOCK TABLES `iam_roles` WRITE;
/*!40000 ALTER TABLE `iam_roles` DISABLE KEYS */;
INSERT INTO `iam_roles` VALUES (1,'ROLE_ADMIN'),(4,'ROLE_LAB_OPERATOR'),(3,'ROLE_QUALITY_AUDITOR'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `iam_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidents`
--

DROP TABLE IF EXISTS `incidents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `escalation_max_mins` int DEFAULT NULL,
  `escalation_next_role` varchar(255) DEFAULT NULL,
  `opened_at` datetime(6) DEFAULT NULL,
  `resolved_at` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgcitify3u3ddjdtviqnwdmehv` (`laboratory_id`),
  CONSTRAINT `FKgcitify3u3ddjdtviqnwdmehv` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidents`
--

LOCK TABLES `incidents` WRITE;
/*!40000 ALTER TABLE `incidents` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_activities`
--

DROP TABLE IF EXISTS `lab_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_activities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `timestamp` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn3rhxymbp3cs42g90hid8d0x9` (`laboratory_id`),
  CONSTRAINT `FKn3rhxymbp3cs42g90hid8d0x9` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_activities`
--

LOCK TABLES `lab_activities` WRITE;
/*!40000 ALTER TABLE `lab_activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `lab_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_alerts`
--

DROP TABLE IF EXISTS `lab_alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_alerts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `alert_id` bigint DEFAULT NULL,
  `severity` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `time_ago` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKil65mgmxnkqh9pvtd3dxol9eh` (`laboratory_id`),
  CONSTRAINT `FKil65mgmxnkqh9pvtd3dxol9eh` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_alerts`
--

LOCK TABLES `lab_alerts` WRITE;
/*!40000 ALTER TABLE `lab_alerts` DISABLE KEYS */;
/*!40000 ALTER TABLE `lab_alerts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_metric_subscriptions`
--

DROP TABLE IF EXISTS `lab_metric_subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_metric_subscriptions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `active` bit(1) NOT NULL,
  `max_threshold` double DEFAULT NULL,
  `min_threshold` double DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  `metric_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi52jaxaji9clr6pxcxbd3e041` (`laboratory_id`,`metric_type_id`),
  KEY `FKkf92y11yre369pnxhvld8lquj` (`metric_type_id`),
  CONSTRAINT `FKkf92y11yre369pnxhvld8lquj` FOREIGN KEY (`metric_type_id`) REFERENCES `metric_types` (`id`),
  CONSTRAINT `FKtf8yb3n11c0hpnt79txb1h7a7` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_metric_subscriptions`
--

LOCK TABLES `lab_metric_subscriptions` WRITE;
/*!40000 ALTER TABLE `lab_metric_subscriptions` DISABLE KEYS */;
INSERT INTO `lab_metric_subscriptions` VALUES (1,'2026-10-07 02:31:16.741000','2026-10-07 02:31:16.741000',_binary '',8,2,1,1),(2,'2026-10-07 02:31:16.747000','2026-10-07 02:31:16.747000',_binary '',60,30,1,2),(3,'2026-10-07 02:31:16.749000','2026-10-07 02:31:16.749000',_binary '',50,0,1,4),(4,'2026-10-07 02:31:16.755000','2026-10-07 02:31:16.755000',_binary '',25,15,2,1),(5,'2026-10-07 02:31:16.757000','2026-10-07 02:31:16.757000',_binary '',1000,400,2,3),(6,'2026-10-07 02:31:16.759000','2026-10-07 02:31:16.759000',_binary '',65,30,2,2),(7,'2026-10-07 02:31:16.763000','2026-10-07 02:31:16.763000',_binary '',-180,-200,3,1),(8,'2026-10-07 02:31:16.766000','2026-10-07 02:31:16.766000',_binary '',1050,950,3,6),(9,'2026-10-07 02:31:16.770000','2026-10-07 02:31:16.770000',_binary '',24,18,4,1),(10,'2026-10-07 02:31:16.773000','2026-10-07 02:31:16.773000',_binary '',55,25,4,2),(11,'2026-10-07 02:31:16.777000','2026-10-07 02:31:16.777000',_binary '',26,18,5,1),(12,'2026-10-07 02:31:16.779000','2026-10-07 02:31:16.779000',_binary '',60,30,5,2),(13,'2026-10-07 02:31:16.781000','2026-10-07 02:31:16.781000',_binary '',50,0,5,4),(14,'2026-10-07 02:31:16.786000','2026-10-07 02:31:16.786000',_binary '',23,19,6,1),(15,'2026-10-07 02:31:16.788000','2026-10-07 02:31:16.788000',_binary '',50,40,6,2),(16,'2026-10-07 02:31:16.790000','2026-10-07 02:31:16.790000',_binary '',150,50,6,6),(17,'2026-10-07 02:31:16.792000','2026-10-07 02:31:16.792000',_binary '',50,0,6,4),(18,'2026-10-07 02:31:16.797000','2026-10-07 02:31:16.797000',_binary '',24,18,7,1),(19,'2026-10-07 02:31:16.799000','2026-10-07 02:31:16.799000',_binary '',1050,950,7,6),(20,'2026-10-07 02:31:16.800000','2026-10-07 02:31:16.800000',_binary '',3,0,7,5),(21,'2026-10-07 02:31:16.806000','2026-10-07 02:31:16.806000',_binary '',25,15,8,1),(22,'2026-10-07 02:31:16.809000','2026-10-07 02:31:16.809000',_binary '',50,30,8,2),(23,'2026-10-07 02:31:16.810000','2026-10-07 02:31:16.810000',_binary '',1000,400,8,3),(24,'2026-10-07 02:31:16.815000','2026-10-07 02:31:16.815000',_binary '',25,18,9,1),(25,'2026-10-07 02:31:16.816000','2026-10-07 02:31:16.816000',_binary '',60,30,9,2),(26,'2026-10-07 02:31:16.822000','2026-10-07 02:31:16.822000',_binary '',37.5,35,10,1),(27,'2026-10-07 02:31:16.824000','2026-10-07 02:31:16.824000',_binary '',95,80,10,2),(28,'2026-10-07 02:31:16.826000','2026-10-07 02:31:16.826000',_binary '',6,4,10,3),(29,'2026-10-07 02:31:16.831000','2026-10-07 02:31:16.831000',_binary '',22,18,11,1),(30,'2026-10-07 02:31:16.832000','2026-10-07 02:31:16.832000',_binary '',50,30,11,2),(31,'2026-10-07 02:31:16.834000','2026-10-07 02:31:16.834000',_binary '',1020,900,11,6),(32,'2026-10-07 02:31:16.835000','2026-10-07 02:31:16.835000',_binary '',50,0,11,4),(33,'2026-10-07 02:31:16.841000','2026-10-07 02:31:16.841000',_binary '',22,19,12,1),(34,'2026-10-07 02:31:16.843000','2026-10-07 02:31:16.843000',_binary '',3,0,12,5),(35,'2026-10-07 02:31:16.844000','2026-10-07 02:31:16.844000',_binary '',50,0,12,4);
/*!40000 ALTER TABLE `lab_metric_subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_metrics`
--

DROP TABLE IF EXISTS `lab_metrics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_metrics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `object_type` varchar(255) DEFAULT NULL,
  `sparkline` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `threshold` double DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrhqdyldcme5o549chqrkqoxne` (`laboratory_id`),
  CONSTRAINT `FKrhqdyldcme5o549chqrkqoxne` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_metrics`
--

LOCK TABLES `lab_metrics` WRITE;
/*!40000 ALTER TABLE `lab_metrics` DISABLE KEYS */;
INSERT INTO `lab_metrics` VALUES (1,'2026-10-07 02:31:16.951000','2026-10-07 02:31:16.951000','device_thermostat','temperature','Refrigerator B2','4.8,4.9,5.0,5.2,5.5,5.8,7.0,8.5','CRITICAL',8,'°C','8.50',1),(2,'2026-10-07 02:31:16.956000','2026-10-07 02:31:16.956000','water_drop','humidity','Ambient','43.5,44.0,44.5,45.0,45.2','NORMAL',NULL,'%','45.20',1),(3,'2026-10-07 02:31:16.958000','2026-10-07 02:31:16.958000','air','air_quality','Ambient','20.0,21.0,22.0','NORMAL',NULL,'AQI','22.00',1),(4,'2026-10-07 02:31:16.960000','2026-10-07 02:31:16.960000','device_thermostat','temperature','Ambient','20.5,21.0,21.3','NORMAL',NULL,'°C','21.30',2),(5,'2026-10-07 02:31:16.962000','2026-10-07 02:31:16.962000','co2','co2','CO2 Incubator CM-01','550.0,600.0,750.0,900.0,1050.0','WARNING',1000,'ppm','1050.00',2),(6,'2026-10-07 02:31:16.964000','2026-10-07 02:31:16.964000','graphic_eq','vibration','Reactor R-02','1.1,1.2,1.3,3.5','WARNING',3,'mm/s','3.50',2),(7,'2026-10-07 02:31:16.966000','2026-10-07 02:31:16.966000','water_drop','humidity','Ambient','46.0,47.2,48.5','NORMAL',NULL,'%','48.50',2),(8,'2026-10-07 02:31:16.969000','2026-10-07 02:31:16.969000','device_thermostat','temperature','LN2 Tank Cryo-01','-192.0,-190.0,-185.0,-180.0,-170.0','CRITICAL',-180,'°C','-170.00',3),(9,'2026-10-07 02:31:16.971000','2026-10-07 02:31:16.971000','compress','pressure','Ambient','1008.0,1010.0,1012.0','NORMAL',NULL,'hPa','1012.00',3),(10,'2026-10-07 02:31:16.973000','2026-10-07 02:31:16.973000','device_thermostat','temperature','GC-MS Column Oven','21.5,21.7,21.8','NORMAL',NULL,'°C','21.80',4),(11,'2026-10-07 02:31:16.974000','2026-10-07 02:31:16.974000','water_drop','humidity','Ambient','39.5,40.0,40.2','NORMAL',NULL,'%','40.20',4),(12,'2026-10-07 02:31:16.977000','2026-10-07 02:31:16.977000','device_thermostat','temperature','Ambient','21.8,22.0,22.1','NORMAL',NULL,'°C','22.10',5),(13,'2026-10-07 02:31:16.978000','2026-10-07 02:31:16.978000','water_drop','humidity','Ambient','44.5,45.0,45.2','NORMAL',NULL,'%','45.00',5),(14,'2026-10-07 02:31:16.981000','2026-10-07 02:31:16.981000','compress','pressure','HEPA Filtration Unit H-1','110.0,90.0,70.0,45.0','CRITICAL',50,'Pa','45.00',6),(15,'2026-10-07 02:31:16.983000','2026-10-07 02:31:16.983000','air','air_quality','Ambient','15.0,30.0,55.0,75.0','CRITICAL',50,'AQI','75.00',6),(16,'2026-10-07 02:31:16.985000','2026-10-07 02:31:16.985000','device_thermostat','temperature','Ambient','19.8,20.1,20.0','NORMAL',NULL,'°C','20.00',7),(17,'2026-10-07 02:31:16.988000','2026-10-07 02:31:16.988000','compress','pressure','Ambient','960.0,950.0,940.0','WARNING',950,'hPa','940.00',7),(18,'2026-10-07 02:31:16.990000','2026-10-07 02:31:16.990000','device_thermostat','temperature','Ambient','22.0,24.0,26.5,28.5','CRITICAL',25,'°C','28.50',8),(19,'2026-10-07 02:31:16.993000','2026-10-07 02:31:16.993000','co2','co2','Granulator G-08','800.0,950.0,1100.0','WARNING',1000,'ppm','1100.00',8),(20,'2026-10-07 02:31:16.995000','2026-10-07 02:31:16.995000','device_thermostat','temperature','Sequencer Seq-02','22.0,23.5,25.0,26.5','WARNING',25,'°C','26.50',9),(21,'2026-10-07 02:31:16.996000','2026-10-07 02:31:16.996000','water_drop','humidity','Ambient','49.0,50.0,50.2','NORMAL',NULL,'%','50.00',9),(22,'2026-10-07 02:31:16.998000','2026-10-07 02:31:16.998000','device_thermostat','temperature','Anaerobic Chamber AC-1','37.0,37.5,38.5','WARNING',37.5,'°C','38.50',10),(23,'2026-10-07 02:31:16.999000','2026-10-07 02:31:16.999000','co2','co2','Ambient','4.8,4.9,5.0','NORMAL',NULL,'%','5.00',10),(24,'2026-10-07 02:31:17.002000','2026-10-07 02:31:17.002000','air','air_quality','Fume Hood F-09','40.0,65.0,90.0,120.0','CRITICAL',50,'AQI','120.00',11),(25,'2026-10-07 02:31:17.004000','2026-10-07 02:31:17.004000','compress','pressure','Ambient','960.0,910.0,850.0','CRITICAL',900,'hPa','850.00',11),(26,'2026-10-07 02:31:17.006000','2026-10-07 02:31:17.006000','device_thermostat','temperature','Ambient','20.9,21.0,21.1','NORMAL',NULL,'°C','21.00',12),(27,'2026-10-07 02:31:17.008000','2026-10-07 02:31:17.008000','graphic_eq','vibration','Optical Table OT-04','1.0,1.5,2.4,3.2','WARNING',3,'mm/s','3.20',12);
/*!40000 ALTER TABLE `lab_metrics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_schedules`
--

DROP TABLE IF EXISTS `lab_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_schedules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `active` bit(1) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `time_range` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKawug710fyvqjks0y7lnlhif10` (`laboratory_id`),
  CONSTRAINT `FKawug710fyvqjks0y7lnlhif10` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_schedules`
--

LOCK TABLES `lab_schedules` WRITE;
/*!40000 ALTER TABLE `lab_schedules` DISABLE KEYS */;
/*!40000 ALTER TABLE `lab_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_user_accesses`
--

DROP TABLE IF EXISTS `lab_user_accesses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_user_accesses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `laboratory_id` bigint NOT NULL,
  `user_profile_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcaghoi3h26eb9aao13t4too6t` (`laboratory_id`),
  KEY `FKnw56u4cv09u0a23eci5l3aso9` (`user_profile_id`),
  CONSTRAINT `FKcaghoi3h26eb9aao13t4too6t` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`),
  CONSTRAINT `FKnw56u4cv09u0a23eci5l3aso9` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_user_accesses`
--

LOCK TABLES `lab_user_accesses` WRITE;
/*!40000 ALTER TABLE `lab_user_accesses` DISABLE KEYS */;
INSERT INTO `lab_user_accesses` VALUES (1,'2026-10-07 02:31:16.848000','2026-10-07 02:31:16.848000',1,1),(2,'2026-10-07 02:31:16.851000','2026-10-07 02:31:16.851000',2,1),(3,'2026-10-07 02:31:16.852000','2026-10-07 02:31:16.852000',3,1),(4,'2026-10-07 02:31:16.854000','2026-10-07 02:31:16.854000',4,1),(5,'2026-10-07 02:31:16.856000','2026-10-07 02:31:16.856000',5,1),(6,'2026-10-07 02:31:16.858000','2026-10-07 02:31:16.858000',6,1),(7,'2026-10-07 02:31:16.859000','2026-10-07 02:31:16.859000',7,1),(8,'2026-10-07 02:31:16.861000','2026-10-07 02:31:16.861000',8,1),(9,'2026-10-07 02:31:16.863000','2026-10-07 02:31:16.863000',9,1),(10,'2026-10-07 02:31:16.864000','2026-10-07 02:31:16.864000',10,1),(11,'2026-10-07 02:31:16.867000','2026-10-07 02:31:16.867000',11,1),(12,'2026-10-07 02:31:16.869000','2026-10-07 02:31:16.869000',12,1);
/*!40000 ALTER TABLE `lab_user_accesses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laboratories`
--

DROP TABLE IF EXISTS `laboratories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laboratories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `active` bit(1) NOT NULL,
  `building` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `floor` varchar(255) DEFAULT NULL,
  `is_live` bit(1) DEFAULT NULL,
  `lab_code` varchar(255) NOT NULL,
  `last_update` datetime(6) DEFAULT NULL,
  `maintenance_days_left` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `next_maintenance` date DEFAULT NULL,
  `notif_critical_only` bit(1) DEFAULT NULL,
  `notif_email` bit(1) DEFAULT NULL,
  `notif_push` bit(1) DEFAULT NULL,
  `notif_sms` bit(1) DEFAULT NULL,
  `overall_status` varchar(255) DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `workspace_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj26kepl1twmwawge3gfb0hh6o` (`lab_code`),
  KEY `FKgddhy3ahkevp52o6oys858knl` (`workspace_id`),
  CONSTRAINT `FKgddhy3ahkevp52o6oys858knl` FOREIGN KEY (`workspace_id`) REFERENCES `workspaces` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laboratories`
--

LOCK TABLES `laboratories` WRITE;
/*!40000 ALTER TABLE `laboratories` DISABLE KEYS */;
INSERT INTO `laboratories` VALUES (1,'2026-10-07 02:31:16.730000','2026-10-07 02:31:18.201000',_binary '','Building A','High pathogen containment research unit','Floor 2',_binary '','LAB-BSL4-01',NULL,45,'Bio-Safety Lab 04','2026-11-20',_binary '\0',_binary '',_binary '\0',_binary '','Operational','204','Operational','Biohazard Level 3',1),(2,'2026-10-07 02:31:16.752000','2026-10-07 02:31:18.201000',_binary '','Building B','Organic compound development and analysis','Floor 1',_binary '','LAB-CHEM-02',NULL,15,'Chemical Synthesis Lab','2026-10-21',_binary '\0',_binary '',_binary '',_binary '\0','Warning','102','Warning','Chemical Synthesis',1),(3,'2026-10-07 02:31:16.761000','2026-10-07 02:31:18.202000',_binary '','Building A','Ultra-low temperature biological sample repository','Floor 1',_binary '','LAB-CRYO-03',NULL,60,'Cryogenic Storage Room','2026-12-05',_binary '',_binary '',_binary '',_binary '','Critical','105','Critical','Cryo Storage',1),(4,'2026-10-07 02:31:16.768000','2026-10-07 02:31:18.203000',_binary '','Building C','Spectrometry and chromatography services','Floor 3',_binary '','LAB-ANAL-04',NULL,90,'Analytical Testing Unit','2027-01-04',_binary '\0',_binary '',_binary '\0',_binary '\0','Operational','310','Operational','Analytical Labs',1),(5,'2026-10-07 02:31:16.775000','2026-10-07 02:31:18.203000',_binary '','Building A','DNA/RNA extraction and amplification research','Floor 3',_binary '','LAB-MOLB-05',NULL,30,'Molecular Biology Lab','2026-11-05',_binary '\0',_binary '',_binary '\0',_binary '','Operational','302','Operational','Molecular Biology',1),(6,'2026-10-07 02:31:16.784000','2026-10-07 02:31:18.203000',_binary '','Building C','Particulate-free environment for sensitive material synthesis','Floor 1',_binary '','LAB-CLEAN-06',NULL,10,'Clean Room Class 100','2026-10-16',_binary '\0',_binary '',_binary '',_binary '','Operational','112','Operational','Micro-fabrication',1),(7,'2026-10-07 02:31:16.794000','2026-10-07 02:31:18.203000',_binary '','Building D','Shielded laboratory for isotope tracking and radiation physics','Floor B1',_binary '','LAB-RAD-07',NULL,25,'Radiation Research Facility','2026-10-31',_binary '',_binary '',_binary '',_binary '\0','Warning','015','Warning','Nuclear/Radiology',1),(8,'2026-10-07 02:31:16.803000','2026-10-07 02:31:18.205000',_binary '','Building B','Solid-dosage form blending, granulation and compression','Floor 2',_binary '','LAB-PHARM-08',NULL,5,'Pharmaceutical Formulation Unit','2026-10-11',_binary '',_binary '',_binary '',_binary '','Critical','205','Critical','Pharmaceutics',1),(9,'2026-10-07 02:31:16.812000','2026-10-07 02:31:18.205000',_binary '','Building A','High-throughput next generation sequencing services','Floor 2',_binary '','LAB-GENE-09',NULL,40,'Genetics Sequencing Center','2026-11-15',_binary '\0',_binary '',_binary '\0',_binary '','Warning','218','Warning','Genomics',1),(10,'2026-10-07 02:31:16.819000','2026-10-07 02:31:18.205000',_binary '','Building B','Controlled culture growing environmental chambers','Floor 3',_binary '','LAB-MICRO-10',NULL,50,'Microbiology Incubator Zone','2026-11-25',_binary '\0',_binary '',_binary '',_binary '\0','Warning','304','Warning','Microbiology',1),(11,'2026-10-07 02:31:16.828000','2026-10-07 02:31:18.206000',_binary '','Building D','Handling high-potency API and containment materials','Floor 2',_binary '','LAB-TOX-11',NULL,12,'Toxicology Isolation Suite','2026-10-18',_binary '',_binary '',_binary '',_binary '','Critical','220','Critical','Toxicology',1),(12,'2026-10-07 02:31:16.838000','2026-10-07 02:31:18.206000',_binary '','Building C','Ultra-precise vibration isolated optical platforms','Floor 2',_binary '','LAB-PHYS-12',NULL,75,'Laser Optics & Physics Lab','2026-12-20',_binary '\0',_binary '',_binary '\0',_binary '','Operational','210','Operational','Physics Labs',1);
/*!40000 ALTER TABLE `laboratories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `metric_types`
--

DROP TABLE IF EXISTS `metric_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metric_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `active` bit(1) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `metric_key` varchar(255) NOT NULL,
  `unit` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqjmo9cmh6df6wv3neqisoxflp` (`metric_key`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metric_types`
--

LOCK TABLES `metric_types` WRITE;
/*!40000 ALTER TABLE `metric_types` DISABLE KEYS */;
INSERT INTO `metric_types` VALUES (1,'2026-10-07 02:31:16.370000','2026-10-07 02:31:16.370000',_binary '','ENVIRONMENTAL','Temperature','device_thermostat','temperature','°C'),(2,'2026-10-07 02:31:16.397000','2026-10-07 02:31:16.397000',_binary '','ENVIRONMENTAL','Humidity','water_drop','humidity','%'),(3,'2026-10-07 02:31:16.399000','2026-10-07 02:31:16.399000',_binary '','ENVIRONMENTAL','CO₂ Level','co2','co2','ppm'),(4,'2026-10-07 02:31:16.401000','2026-10-07 02:31:16.401000',_binary '','ENVIRONMENTAL','Air Quality','air','air_quality','AQI'),(5,'2026-10-07 02:31:16.404000','2026-10-07 02:31:16.404000',_binary '','SAFETY','Vibration','graphic_eq','vibration','mm/s'),(6,'2026-10-07 02:31:16.405000','2026-10-07 02:31:16.405000',_binary '','ENVIRONMENTAL','Pressure','compress','pressure','hPa'),(7,'2026-10-07 02:31:16.407000','2026-10-07 02:31:16.407000',_binary '','EQUIPMENT','Lighting','lightbulb','lighting','lux'),(8,'2026-10-07 02:31:16.408000','2026-10-07 02:31:16.408000',_binary '','EQUIPMENT','Ventilation Flow','hvac','ventilation_flow','m³/h'),(9,'2026-10-07 02:31:16.409000','2026-10-07 02:31:16.409000',_binary '','EQUIPMENT','Air Conditioning','ac_unit','air_conditioning','°C');
/*!40000 ALTER TABLE `metric_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mitigation_systems`
--

DROP TABLE IF EXISTS `mitigation_systems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mitigation_systems` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `last_activated_at` datetime(6) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `storage_unit_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi6g0ldqibuheeb0a1ifmp3p6t` (`storage_unit_id`),
  CONSTRAINT `FKids2jnb2owsp6yekhcievk4lp` FOREIGN KEY (`storage_unit_id`) REFERENCES `storage_units` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mitigation_systems`
--

LOCK TABLES `mitigation_systems` WRITE;
/*!40000 ALTER TABLE `mitigation_systems` DISABLE KEYS */;
/*!40000 ALTER TABLE `mitigation_systems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_preferences`
--

DROP TABLE IF EXISTS `notification_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_preferences` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `threshold` varchar(255) DEFAULT NULL,
  `user_profile_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi2fhq19415gsgn6q3daq86jt` (`user_profile_id`),
  CONSTRAINT `FKi2fhq19415gsgn6q3daq86jt` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_preferences`
--

LOCK TABLES `notification_preferences` WRITE;
/*!40000 ALTER TABLE `notification_preferences` DISABLE KEYS */;
INSERT INTO `notification_preferences` VALUES (1,'2026-10-07 02:31:16.685000','2026-10-07 02:31:16.685000','Email','All notifications via email',_binary '','All',1),(2,'2026-10-07 02:31:16.689000','2026-10-07 02:31:16.689000','In-App','Realtime alerts in the application',_binary '','All',1),(3,'2026-10-07 02:31:16.692000','2026-10-07 02:31:16.692000','SMS','Urgent alerts on mobile devices',_binary '','Critical Only',1);
/*!40000 ALTER TABLE `notification_preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pending_invitations`
--

DROP TABLE IF EXISTS `pending_invitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pending_invitations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `laboratory_ids` text,
  `sent_at` datetime(6) DEFAULT NULL,
  `workspace_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3sxki40cnspdofyn3k9047ff4` (`role_id`),
  CONSTRAINT `FK3sxki40cnspdofyn3k9047ff4` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pending_invitations`
--

LOCK TABLES `pending_invitations` WRITE;
/*!40000 ALTER TABLE `pending_invitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `pending_invitations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_audit_links`
--

DROP TABLE IF EXISTS `report_audit_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_audit_links` (
  `report_id` bigint NOT NULL,
  `audit_id` bigint NOT NULL,
  KEY `FKbmwxs6hwmvfgyfw4xmkiaik98` (`audit_id`),
  KEY `FK60lfc886extnwld8irlfyinqq` (`report_id`),
  CONSTRAINT `FK60lfc886extnwld8irlfyinqq` FOREIGN KEY (`report_id`) REFERENCES `compliance_reports` (`id`),
  CONSTRAINT `FKbmwxs6hwmvfgyfw4xmkiaik98` FOREIGN KEY (`audit_id`) REFERENCES `audit_trails` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_audit_links`
--

LOCK TABLES `report_audit_links` WRITE;
/*!40000 ALTER TABLE `report_audit_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_audit_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `is_granted` bit(1) DEFAULT NULL,
  `last_audit_date` date DEFAULT NULL,
  `permission` varchar(255) NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn5fotdgk8d1xvo8nav9uv3muc` (`role_id`),
  CONSTRAINT `FKn5fotdgk8d1xvo8nav9uv3muc` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2026-10-07 02:31:16.596000','2026-10-07 02:31:16.596000','Administrator role','Administrator'),(2,'2026-10-07 02:31:16.622000','2026-10-07 02:31:16.622000','Operator role','Operator');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_configurations`
--

DROP TABLE IF EXISTS `sensor_configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor_configurations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `calibration_date` date DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `last_connected` datetime(6) DEFAULT NULL,
  `max_threshold` double DEFAULT NULL,
  `min_threshold` double DEFAULT NULL,
  `sensor_name` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `warning_threshold` double DEFAULT NULL,
  `equipment_threshold_id` bigint DEFAULT NULL,
  `laboratory_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtmkwd0h7lbiq3ffhif0eerkva` (`equipment_threshold_id`),
  KEY `FKh5ax50taero3bh2h173tk0x4c` (`laboratory_id`),
  CONSTRAINT `FKh5ax50taero3bh2h173tk0x4c` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`),
  CONSTRAINT `FKtmkwd0h7lbiq3ffhif0eerkva` FOREIGN KEY (`equipment_threshold_id`) REFERENCES `equipment_thresholds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_configurations`
--

LOCK TABLES `sensor_configurations` WRITE;
/*!40000 ALTER TABLE `sensor_configurations` DISABLE KEYS */;
INSERT INTO `sensor_configurations` VALUES (1,'2026-10-07 02:31:16.899000','2026-10-07 02:31:16.899000','2026-07-06',_binary '','2026-10-07 02:29:16.899255',NULL,NULL,'Sensor T-01','ONLINE','temperature','MAC-T01-REF',NULL,2,1),(2,'2026-10-07 02:31:16.904000','2026-10-07 02:31:16.904000','2026-06-06',_binary '','2026-10-07 02:26:16.904766',NULL,NULL,'Sensor H-02','ONLINE','humidity','MAC-H02-AMB',NULL,NULL,1),(3,'2026-10-07 02:31:16.907000','2026-10-07 02:31:16.907000','2026-09-06',_binary '','2026-10-07 02:30:16.907037',NULL,NULL,'Sensor C-03','ONLINE','co2','MAC-C03-INC',NULL,3,2),(4,'2026-10-07 02:31:16.909000','2026-10-07 02:31:16.909000','2026-08-06',_binary '','2026-10-07 02:28:16.909115',NULL,NULL,'Sensor T-04','ONLINE','temperature','MAC-T04-CRYO',NULL,5,3),(5,'2026-10-07 02:31:16.911000','2026-10-07 02:31:16.911000','2026-04-06',_binary '','2026-10-07 02:21:16.910666',NULL,NULL,'Sensor P-05','ONLINE','pressure','MAC-P05-PRES',NULL,NULL,3),(6,'2026-10-07 02:31:16.913000','2026-10-07 02:31:16.913000','2026-05-06',_binary '','2026-10-07 02:24:16.913442',NULL,NULL,'Sensor A-07','ONLINE','air_quality','MAC-A07-AIR',NULL,NULL,2),(7,'2026-10-07 02:31:16.916000','2026-10-07 02:31:16.916000','2026-09-06',_binary '','2026-10-07 02:27:16.915551',NULL,NULL,'Sensor V-08','ONLINE','vibration','MAC-V08-VIB',NULL,4,2),(8,'2026-10-07 02:31:16.918000','2026-10-07 02:31:16.918000','2026-02-06',_binary '','2026-10-05 02:31:16.918706',NULL,NULL,'Sensor T-06','OFFLINE','temperature','MAC-T06-OFFLINE',NULL,6,4),(9,'2026-10-07 02:31:16.920000','2026-10-07 02:31:16.920000','2026-08-06',_binary '','2026-10-07 02:28:16.920801',NULL,NULL,'Sensor T-09','ONLINE','temperature','MAC-T09-MOLB',NULL,7,5),(10,'2026-10-07 02:31:16.923000','2026-10-07 02:31:16.923000','2026-07-06',_binary '','2026-10-07 02:26:16.923407',NULL,NULL,'Sensor H-10','ONLINE','humidity','MAC-H10-MOLB',NULL,NULL,5),(11,'2026-10-07 02:31:16.926000','2026-10-07 02:31:16.926000','2026-09-06',_binary '','2026-10-07 02:30:16.925511',NULL,NULL,'Sensor P-11','ONLINE','pressure','MAC-P11-CLEAN',NULL,8,6),(12,'2026-10-07 02:31:16.928000','2026-10-07 02:31:16.928000','2026-06-06',_binary '','2026-10-07 02:29:16.928153',NULL,NULL,'Sensor A-12','ONLINE','air_quality','MAC-A12-CLEAN',NULL,NULL,6),(13,'2026-10-07 02:31:16.930000','2026-10-07 02:31:16.930000','2026-05-06',_binary '','2026-10-07 02:25:16.930322',NULL,NULL,'Sensor T-13','ONLINE','temperature','MAC-T13-RAD',NULL,9,7),(14,'2026-10-07 02:31:16.933000','2026-10-07 02:31:16.933000','2026-08-06',_binary '','2026-10-07 02:27:16.933082',NULL,NULL,'Sensor P-14','ONLINE','pressure','MAC-P14-RAD',NULL,NULL,7),(15,'2026-10-07 02:31:16.935000','2026-10-07 02:31:16.935000','2026-07-06',_binary '','2026-10-07 02:29:16.935182',NULL,NULL,'Sensor T-15','ONLINE','temperature','MAC-T15-PHARM',NULL,NULL,8),(16,'2026-10-07 02:31:16.937000','2026-10-07 02:31:16.937000','2026-06-06',_binary '','2026-10-07 02:28:16.936741',NULL,NULL,'Sensor C-16','ONLINE','co2','MAC-C16-PHARM',NULL,10,8),(17,'2026-10-07 02:31:16.939000','2026-10-07 02:31:16.939000','2026-09-06',_binary '','2026-10-07 02:30:16.939361',NULL,NULL,'Sensor T-17','ONLINE','temperature','MAC-T17-GENE',NULL,11,9),(18,'2026-10-07 02:31:16.941000','2026-10-07 02:31:16.941000','2026-08-06',_binary '','2026-10-07 02:29:16.940922',NULL,NULL,'Sensor T-18','ONLINE','temperature','MAC-T18-MICRO',NULL,12,10),(19,'2026-10-07 02:31:16.943000','2026-10-07 02:31:16.943000','2026-07-06',_binary '','2026-10-07 02:30:16.943027',NULL,NULL,'Sensor A-19','ONLINE','air_quality','MAC-A19-TOX',NULL,13,11),(20,'2026-10-07 02:31:16.945000','2026-10-07 02:31:16.945000','2026-09-06',_binary '','2026-10-07 02:29:16.945107',NULL,NULL,'Sensor P-20','ONLINE','pressure','MAC-P20-TOX',NULL,NULL,11),(21,'2026-10-07 02:31:16.947000','2026-10-07 02:31:16.947000','2026-08-06',_binary '','2026-10-07 02:27:16.947189',NULL,NULL,'Sensor V-21','ONLINE','vibration','MAC-V21-PHYS',NULL,14,12),(22,'2026-10-07 02:31:16.949000','2026-10-07 02:31:16.949000','2026-04-06',_binary '','2026-10-07 02:21:16.948767',NULL,NULL,'Sensor T-22','ONLINE','temperature','MAC-T22-PHYS',NULL,NULL,12);
/*!40000 ALTER TABLE `sensor_configurations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_readings`
--

DROP TABLE IF EXISTS `sensor_readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor_readings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `metric_value` double NOT NULL,
  `recorded_at` datetime(6) NOT NULL,
  `laboratory_id` bigint NOT NULL,
  `metric_type_id` bigint NOT NULL,
  `sensor_configuration_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgibvih7aqyns424ysl9pe0lv1` (`laboratory_id`),
  KEY `FKmku9sv58d6nl2fmgda1sno1c6` (`metric_type_id`),
  KEY `FKbpa29jk9pgf4yefw9t5aukx0k` (`sensor_configuration_id`),
  CONSTRAINT `FKbpa29jk9pgf4yefw9t5aukx0k` FOREIGN KEY (`sensor_configuration_id`) REFERENCES `sensor_configurations` (`id`),
  CONSTRAINT `FKgibvih7aqyns424ysl9pe0lv1` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`),
  CONSTRAINT `FKmku9sv58d6nl2fmgda1sno1c6` FOREIGN KEY (`metric_type_id`) REFERENCES `metric_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1030 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_readings`
--

LOCK TABLES `sensor_readings` WRITE;
/*!40000 ALTER TABLE `sensor_readings` DISABLE KEYS */;
INSERT INTO `sensor_readings` VALUES (1,'2026-10-07 02:31:17.011000','2026-10-07 02:31:17.011000',5.014972393229848,'2026-10-05 02:31:17.010462',1,1,1),(2,'2026-10-07 02:31:17.014000','2026-10-07 02:31:17.014000',52.72526221239423,'2026-10-05 02:31:17.010462',1,2,2),(3,'2026-10-07 02:31:17.016000','2026-10-07 02:31:17.016000',573.8509828165528,'2026-10-05 02:31:17.010462',2,3,3),(4,'2026-10-07 02:31:17.018000','2026-10-07 02:31:17.018000',-185.1717111172536,'2026-10-05 02:31:17.010462',3,1,4),(5,'2026-10-07 02:31:17.019000','2026-10-07 02:31:17.019000',1001.0460246939699,'2026-10-05 02:31:17.010462',3,6,5),(6,'2026-10-07 02:31:17.021000','2026-10-07 02:31:17.021000',25.755282250858784,'2026-10-05 02:31:17.010462',2,4,6),(7,'2026-10-07 02:31:17.023000','2026-10-07 02:31:17.023000',1.9244626896052992,'2026-10-05 02:31:17.010462',2,5,7),(8,'2026-10-07 02:31:17.024000','2026-10-07 02:31:17.024000',22.96565777654928,'2026-10-05 02:31:17.010462',5,1,9),(9,'2026-10-07 02:31:17.026000','2026-10-07 02:31:17.026000',40.07656072102937,'2026-10-05 02:31:17.010462',5,2,10),(10,'2026-10-07 02:31:17.028000','2026-10-07 02:31:17.028000',110.0383539116416,'2026-10-05 02:31:17.010462',6,6,11),(11,'2026-10-07 02:31:17.030000','2026-10-07 02:31:17.030000',13.70091321893123,'2026-10-05 02:31:17.010462',6,4,12),(12,'2026-10-07 02:31:17.032000','2026-10-07 02:31:17.032000',20.27465194305592,'2026-10-05 02:31:17.010462',7,1,13),(13,'2026-10-07 02:31:17.033000','2026-10-07 02:31:17.033000',987.3850982816552,'2026-10-05 02:31:17.010462',7,6,14),(14,'2026-10-07 02:31:17.035000','2026-10-07 02:31:17.035000',19.480365287572493,'2026-10-05 02:31:17.010462',8,1,15),(15,'2026-10-07 02:31:17.036000','2026-10-07 02:31:17.036000',546.3427081999565,'2026-10-05 02:31:17.010462',8,3,16),(16,'2026-10-07 02:31:17.038000','2026-10-07 02:31:17.038000',20.308982118882952,'2026-10-05 02:31:17.010462',9,1,17),(17,'2026-10-07 02:31:17.040000','2026-10-07 02:31:17.040000',36.67165746440995,'2026-10-05 02:31:17.010462',10,1,18),(18,'2026-10-07 02:31:17.042000','2026-10-07 02:31:17.042000',22.921461150289968,'2026-10-05 02:31:17.010462',11,4,19),(19,'2026-10-07 02:31:17.043000','2026-10-07 02:31:17.043000',987.9366786384916,'2026-10-05 02:31:17.010462',11,6,20),(20,'2026-10-07 02:31:17.045000','2026-10-07 02:31:17.045000',1.2746519430559191,'2026-10-05 02:31:17.010462',12,5,21),(21,'2026-10-07 02:31:17.047000','2026-10-07 02:31:17.047000',20.007656072102936,'2026-10-05 02:31:17.010462',12,1,22),(22,'2026-10-07 02:31:17.048000','2026-10-07 02:31:17.048000',4.425696538682449,'2026-10-05 03:31:17.010462',1,1,1),(23,'2026-10-07 02:31:17.050000','2026-10-07 02:31:17.050000',52.99447510998334,'2026-10-05 03:31:17.010462',1,2,2),(24,'2026-10-07 02:31:17.052000','2026-10-07 02:31:17.052000',603.7163138180036,'2026-10-05 03:31:17.010462',2,3,3),(25,'2026-10-07 02:31:17.053000','2026-10-07 02:31:17.053000',-185.0034530562604,'2026-10-05 03:31:17.010462',3,1,4),(26,'2026-10-07 02:31:17.055000','2026-10-07 02:31:17.055000',1001.0009209092308,'2026-10-05 03:31:17.010462',3,6,5),(27,'2026-10-07 02:31:17.057000','2026-10-07 02:31:17.057000',28.333928928046028,'2026-10-05 03:31:17.010462',2,4,6),(28,'2026-10-07 02:31:17.058000','2026-10-07 02:31:17.058000',1.998465622383517,'2026-10-05 03:31:17.010462',2,5,7),(29,'2026-10-07 02:31:17.060000','2026-10-07 02:31:17.060000',22.999309388747918,'2026-10-05 03:31:17.010462',5,1,9),(30,'2026-10-07 02:31:17.061000','2026-10-07 02:31:17.061000',40.001534789823964,'2026-10-05 03:31:17.010462',5,2,10),(31,'2026-10-07 02:31:17.062000','2026-10-07 02:31:17.062000',110.000767424359,'2026-10-05 03:31:17.010462',6,6,11),(32,'2026-10-07 02:31:17.063000','2026-10-07 02:31:17.063000',15.185791923954133,'2026-10-05 03:31:17.010462',6,4,12),(33,'2026-10-07 02:31:17.065000','2026-10-07 02:31:17.065000',19.960371487297305,'2026-10-05 03:31:17.010462',7,1,13),(34,'2026-10-07 02:31:17.066000','2026-10-07 02:31:17.066000',990.3716313818004,'2026-10-05 03:31:17.010462',7,6,14),(35,'2026-10-07 02:31:17.067000','2026-10-07 02:31:17.067000',20.074316769581653,'2026-10-05 03:31:17.010462',8,1,15),(36,'2026-10-07 02:31:17.069000','2026-10-07 02:31:17.069000',527.133502417283,'2026-10-05 03:31:17.010462',8,3,16),(37,'2026-10-07 02:31:17.071000','2026-10-07 02:31:17.071000',19.894359473711777,'2026-10-05 03:31:17.010462',9,1,17),(38,'2026-10-07 02:31:17.072000','2026-10-07 02:31:17.072000',36.47523217956082,'2026-10-05 03:31:17.010462',10,1,18),(39,'2026-10-07 02:31:17.073000','2026-10-07 02:31:17.073000',25.297267078326612,'2026-10-05 03:31:17.010462',11,4,19),(40,'2026-10-07 02:31:17.075000','2026-10-07 02:31:17.075000',986.9384494492977,'2026-10-05 03:31:17.010462',11,6,20),(41,'2026-10-07 02:31:17.076000','2026-10-07 02:31:17.076000',1.0396285127026939,'2026-10-05 03:31:17.010462',12,5,21),(42,'2026-10-07 02:31:17.078000','2026-10-07 02:31:17.078000',20.000153478982398,'2026-10-05 03:31:17.010462',12,1,22),(43,'2026-10-07 02:31:17.080000','2026-10-07 02:31:17.080000',3.8481515668921595,'2026-10-05 04:31:17.010462',1,1,1),(44,'2026-10-07 02:31:17.082000','2026-10-07 02:31:17.082000',52.549565355552836,'2026-10-05 04:31:17.010462',1,2,2),(45,'2026-10-07 02:31:17.084000','2026-10-07 02:31:17.084000',633.4334871150369,'2026-10-05 04:31:17.010462',2,3,3),(46,'2026-10-07 02:31:17.085000','2026-10-07 02:31:17.085000',-185.28152165277947,'2026-10-05 04:31:17.010462',3,1,4),(47,'2026-10-07 02:31:17.086000','2026-10-07 02:31:17.086000',1001.0757079563984,'2026-10-05 04:31:17.010462',3,6,5),(48,'2026-10-07 02:31:17.089000','2026-10-07 02:31:17.089000',32.23982898750533,'2026-10-05 04:31:17.010462',2,4,6),(49,'2026-10-07 02:31:17.090000','2026-10-07 02:31:17.090000',1.8769763233401364,'2026-10-05 04:31:17.010462',2,5,7),(50,'2026-10-07 02:31:17.092000','2026-10-07 02:31:17.092000',22.943695669444104,'2026-10-05 04:31:17.010462',5,1,9),(51,'2026-10-07 02:31:17.093000','2026-10-07 02:31:17.093000',40.12578189297918,'2026-10-05 04:31:17.010462',5,2,10),(52,'2026-10-07 02:31:17.095000','2026-10-07 02:31:17.095000',110.06308996366536,'2026-10-05 04:31:17.010462',6,6,11),(53,'2026-10-07 02:31:17.097000','2026-10-07 02:31:17.097000',16.654074389745244,'2026-10-05 04:31:17.010462',6,4,12),(54,'2026-10-07 02:31:17.099000','2026-10-07 02:31:17.099000',19.652347502342487,'2026-10-05 04:31:17.010462',7,1,13),(55,'2026-10-07 02:31:17.100000','2026-10-07 02:31:17.100000',993.3433487115037,'2026-10-05 04:31:17.010462',7,6,14),(56,'2026-10-07 02:31:17.101000','2026-10-07 02:31:17.101000',20.661629755898097,'2026-10-05 04:31:17.010462',8,1,15),(57,'2026-10-07 02:31:17.102000','2026-10-07 02:31:17.102000',512.4547825311572,'2026-10-05 04:31:17.010462',8,3,16),(58,'2026-10-07 02:31:17.104000','2026-10-07 02:31:17.104000',19.613800799890303,'2026-10-05 04:31:17.010462',9,1,17),(59,'2026-10-07 02:31:17.106000','2026-10-07 02:31:17.106000',36.28271718896406,'2026-10-05 04:31:17.010462',10,1,18),(60,'2026-10-07 02:31:17.107000','2026-10-07 02:31:17.107000',27.64651902359239,'2026-10-05 04:31:17.010462',11,4,19),(61,'2026-10-07 02:31:17.109000','2026-10-07 02:31:17.109000',985.784397643882,'2026-10-05 04:31:17.010462',11,6,20),(62,'2026-10-07 02:31:17.110000','2026-10-07 02:31:17.110000',1.3476524976575148,'2026-10-05 04:31:17.010462',12,5,21),(63,'2026-10-07 02:31:17.111000','2026-10-07 02:31:17.111000',20.01257818929792,'2026-10-05 04:31:17.010462',12,1,22),(64,'2026-10-07 02:31:17.112000','2026-10-07 02:31:17.112000',3.373519129842486,'2026-10-05 05:31:17.010462',1,1,1),(65,'2026-10-07 02:31:17.114000','2026-10-07 02:31:17.114000',51.43027541241297,'2026-10-05 05:31:17.010462',1,2,2),(66,'2026-10-07 02:31:17.116000','2026-10-07 02:31:17.116000',661.8177727862635,'2026-10-05 05:31:17.010462',2,3,3),(67,'2026-10-07 02:31:17.117000','2026-10-07 02:31:17.117000',-185.9810778672419,'2026-10-05 05:31:17.010462',3,1,4),(68,'2026-10-07 02:31:17.119000','2026-10-07 02:31:17.119000',1001.2696385880188,'2026-10-05 05:31:17.010462',3,6,5),(69,'2026-10-07 02:31:17.121000','2026-10-07 02:31:17.121000',36.69529509155659,'2026-10-05 05:31:17.010462',2,4,6),(70,'2026-10-07 02:31:17.122000','2026-10-07 02:31:17.122000',1.5897396099684076,'2026-10-05 05:31:17.010462',2,5,7),(71,'2026-10-07 02:31:17.123000','2026-10-07 02:31:17.123000',22.803784426551623,'2026-10-05 05:31:17.010462',5,1,9),(72,'2026-10-07 02:31:17.124000','2026-10-07 02:31:17.124000',40.44434869057662,'2026-10-05 05:31:17.010462',5,2,10),(73,'2026-10-07 02:31:17.126000','2026-10-07 02:31:17.126000',110.22469882334903,'2026-10-05 05:31:17.010462',6,6,11),(74,'2026-10-07 02:31:17.127000','2026-10-07 02:31:17.127000',17.97460331654946,'2026-10-05 05:31:17.010462',6,4,12),(75,'2026-10-07 02:31:17.128000','2026-10-07 02:31:17.128000',19.39921020258266,'2026-10-05 05:31:17.010462',7,1,13),(76,'2026-10-07 02:31:17.130000','2026-10-07 02:31:17.130000',996.1817772786263,'2026-10-05 05:31:17.010462',7,6,14),(77,'2026-10-07 02:31:17.131000','2026-10-07 02:31:17.131000',21.189841326619785,'2026-10-05 05:31:17.010462',8,1,15),(78,'2026-10-07 02:31:17.133000','2026-10-07 02:31:17.133000',503.21920024887385,'2026-10-05 05:31:17.010462',8,3,16),(79,'2026-10-07 02:31:17.134000','2026-10-07 02:31:17.134000',19.5013251199804,'2026-10-05 05:31:17.010462',9,1,17),(80,'2026-10-07 02:31:17.136000','2026-10-07 02:31:17.136000',36.124506376614164,'2026-10-05 05:31:17.010462',10,1,18),(81,'2026-10-07 02:31:17.138000','2026-10-07 02:31:17.138000',29.759365306479136,'2026-10-05 05:31:17.010462',11,4,19),(82,'2026-10-07 02:31:17.140000','2026-10-07 02:31:17.140000',984.5004407378062,'2026-10-05 05:31:17.010462',11,6,20),(83,'2026-10-07 02:31:17.142000','2026-10-07 02:31:17.142000',1.6007897974173408,'2026-10-05 05:31:17.010462',12,5,21),(84,'2026-10-07 02:31:17.144000','2026-10-07 02:31:17.144000',20.04443486905766,'2026-10-05 05:31:17.010462',12,1,22),(85,'2026-10-07 02:31:17.145000','2026-10-07 02:31:17.145000',3.076733253122814,'2026-10-05 06:31:17.010462',1,1,1),(86,'2026-10-07 02:31:17.148000','2026-10-07 02:31:17.148000',49.73658811765778,'2026-10-05 06:31:17.010462',1,2,2),(87,'2026-10-07 02:31:17.153000','2026-10-07 02:31:17.153000',687.7375789337642,'2026-10-05 06:31:17.010462',2,3,3),(88,'2026-10-07 02:31:17.156000','2026-10-07 02:31:17.156000',-187.0396324264639,'2026-10-05 06:31:17.010462',3,1,4),(89,'2026-10-07 02:31:17.158000','2026-10-07 02:31:17.158000',1001.5807751133258,'2026-10-05 06:31:17.010462',3,6,5),(90,'2026-10-07 02:31:17.159000','2026-10-07 02:31:17.159000',40.813218118144356,'2026-10-05 06:31:17.010462',2,4,6),(91,'2026-10-07 02:31:17.161000','2026-10-07 02:31:17.161000',1.207081047432323,'2026-10-05 06:31:17.010462',2,5,7),(92,'2026-10-07 02:31:17.162000','2026-10-07 02:31:17.162000',22.592073514707224,'2026-10-05 06:31:17.010462',5,1,9),(93,'2026-10-07 02:31:17.163000','2026-10-07 02:31:17.163000',40.94453492969172,'2026-10-05 06:31:17.010462',5,2,10),(94,'2026-10-07 02:31:17.165000','2026-10-07 02:31:17.165000',110.48397926110484,'2026-10-05 06:31:17.010462',6,6,11),(95,'2026-10-07 02:31:17.166000','2026-10-07 02:31:17.166000',19.029419788202254,'2026-10-05 06:31:17.010462',6,4,12),(96,'2026-10-07 02:31:17.168000','2026-10-07 02:31:17.168000',19.240924401665502,'2026-10-05 06:31:17.010462',7,1,13),(97,'2026-10-07 02:31:17.169000','2026-10-07 02:31:17.169000',998.7737578933765,'2026-10-05 06:31:17.010462',7,6,14),(98,'2026-10-07 02:31:17.171000','2026-10-07 02:31:17.171000',21.6117679152809,'2026-10-05 06:31:17.010462',8,1,15),(99,'2026-10-07 02:31:17.173000','2026-10-07 02:31:17.173000',500.00097934492965,'2026-10-05 06:31:17.010462',8,3,16),(100,'2026-10-07 02:31:17.174000','2026-10-07 02:31:17.174000',19.57057062466923,'2026-10-05 06:31:17.010462',9,1,17),(101,'2026-10-07 02:31:17.175000','2026-10-07 02:31:17.175000',36.025577751040935,'2026-10-05 06:31:17.010462',10,1,18),(102,'2026-10-07 02:31:17.177000','2026-10-07 02:31:17.177000',31.447071661123605,'2026-10-05 06:31:17.010462',11,4,19),(103,'2026-10-07 02:31:17.178000','2026-10-07 02:31:17.178000',983.1154136351338,'2026-10-05 06:31:17.010462',11,6,20),(104,'2026-10-07 02:31:17.179000','2026-10-07 02:31:17.179000',1.7590755983344992,'2026-10-05 06:31:17.010462',12,5,21),(105,'2026-10-07 02:31:17.181000','2026-10-07 02:31:17.181000',20.094453492969173,'2026-10-05 06:31:17.010462',12,1,22),(106,'2026-10-07 02:31:17.183000','2026-10-07 02:31:17.183000',3.004649900937606,'2026-10-05 07:31:17.010462',1,1,1),(107,'2026-10-07 02:31:17.185000','2026-10-07 02:31:17.185000',47.61979551310154,'2026-10-05 07:31:17.010462',1,2,2),(108,'2026-10-07 02:31:17.187000','2026-10-07 02:31:17.187000',710.159564681117,'2026-10-05 07:31:17.010462',2,3,3),(109,'2026-10-07 02:31:17.188000','2026-10-07 02:31:17.188000',-188.36262780431153,'2026-10-05 07:31:17.010462',3,1,4),(110,'2026-10-07 02:31:17.190000','2026-10-07 02:31:17.190000',1002.0060087590066,'2026-10-05 07:31:17.010462',3,6,5),(111,'2026-10-07 02:31:17.191000','2026-10-07 02:31:17.191000',43.773695728097756,'2026-10-05 07:31:17.010462',2,4,6),(112,'2026-10-07 02:31:17.193000','2026-10-07 02:31:17.193000',1.5773112024753568,'2026-10-05 07:31:17.010462',2,5,7),(113,'2026-10-07 02:31:17.194000','2026-10-07 02:31:17.194000',22.327474439137692,'2026-10-05 07:31:17.010462',5,1,9),(114,'2026-10-07 02:31:17.195000','2026-10-07 02:31:17.195000',41.606399763399935,'2026-10-05 07:31:17.010462',5,2,10),(115,'2026-10-07 02:31:17.196000','2026-10-07 02:31:17.196000',110.83834063250545,'2026-10-05 07:31:17.010462',6,6,11),(116,'2026-10-07 02:31:17.198000','2026-10-07 02:31:17.198000',19.724300190799305,'2026-10-05 07:31:17.010462',6,4,12),(117,'2026-10-07 02:31:17.200000','2026-10-07 02:31:17.200000',19.202479947166722,'2026-10-05 07:31:17.010462',7,1,13),(118,'2026-10-07 02:31:17.201000','2026-10-07 02:31:17.201000',1001.0159564681117,'2026-10-05 07:31:17.010462',7,6,14),(119,'2026-10-07 02:31:17.202000','2026-10-07 02:31:17.202000',21.889720076319723,'2026-10-05 07:31:17.010462',8,1,15),(120,'2026-10-07 02:31:17.203000','2026-10-07 02:31:17.203000',503.0002132079321,'2026-10-05 07:31:17.010462',8,3,16),(121,'2026-10-07 02:31:17.204000','2026-10-07 02:31:17.204000',19.8131409797632,'2026-10-05 07:31:17.010462',9,1,17),(122,'2026-10-07 02:31:17.205000','2026-10-07 02:31:17.205000',36.001549966979205,'2026-10-05 07:31:17.010462',10,1,18),(123,'2026-10-07 02:31:17.207000','2026-10-07 02:31:17.207000',32.55888030527888,'2026-10-05 07:31:17.010462',11,4,19),(124,'2026-10-07 02:31:17.207000','2026-10-07 02:31:17.207000',981.6604210586496,'2026-10-05 07:31:17.010462',11,6,20),(125,'2026-10-07 02:31:17.209000','2026-10-07 02:31:17.209000',1.7975200528332769,'2026-10-05 07:31:17.010462',12,5,21),(126,'2026-10-07 02:31:17.210000','2026-10-07 02:31:17.210000',20.160639976339993,'2026-10-05 07:31:17.010462',12,1,22),(127,'2026-10-07 02:31:17.211000','2026-10-07 02:31:17.211000',3.1686494496277433,'2026-10-05 08:31:17.010462',1,1,1),(128,'2026-10-07 02:31:17.212000','2026-10-07 02:31:17.212000',45.268984377769094,'2026-10-05 08:31:17.010462',1,2,2),(129,'2026-10-07 02:31:17.215000','2026-10-07 02:31:17.215000',728.1898362132421,'2026-10-05 08:31:17.010462',2,3,3),(130,'2026-10-07 02:31:17.217000','2026-10-07 02:31:17.217000',-189.83188476389432,'2026-10-05 08:31:17.010462',3,1,4),(131,'2026-10-07 02:31:17.217000','2026-10-07 02:31:17.217000',1002.5410907310369,'2026-10-05 08:31:17.010462',3,6,5),(132,'2026-10-07 02:31:17.218000','2026-10-07 02:31:17.218000',44.987279672435015,'2026-10-05 08:31:17.010462',2,4,6),(133,'2026-10-07 02:31:17.219000','2026-10-07 02:31:17.219000',1.8693245108288448,'2026-10-05 08:31:17.010462',2,5,7),(134,'2026-10-07 02:31:17.220000','2026-10-07 02:31:17.220000',22.033623047221138,'2026-10-05 08:31:17.010462',5,1,9),(135,'2026-10-07 02:31:17.221000','2026-10-07 02:31:17.221000',42.40355672941657,'2026-10-05 08:31:17.010462',5,2,10),(136,'2026-10-07 02:31:17.222000','2026-10-07 02:31:17.222000',111.28424227586412,'2026-10-05 08:31:17.010462',6,6,11),(137,'2026-10-07 02:31:17.223000','2026-10-07 02:31:17.223000',19.997172927505023,'2026-10-05 08:31:17.010462',6,4,12),(138,'2026-10-07 02:31:17.225000','2026-10-07 02:31:17.225000',19.289946373134796,'2026-10-05 08:31:17.010462',7,1,13),(139,'2026-10-07 02:31:17.226000','2026-10-07 02:31:17.226000',1002.8189836213242,'2026-10-05 08:31:17.010462',7,6,14),(140,'2026-10-07 02:31:17.227000','2026-10-07 02:31:17.227000',21.99886917100201,'2026-10-05 08:31:17.010462',8,1,15),(141,'2026-10-07 02:31:17.228000','2026-10-07 02:31:17.228000',512.030424002833,'2026-10-05 08:31:17.010462',8,3,16),(142,'2026-10-07 02:31:17.229000','2026-10-07 02:31:17.229000',20.199623420116325,'2026-10-05 08:31:17.010462',9,1,17),(143,'2026-10-07 02:31:17.230000','2026-10-07 02:31:17.230000',36.05621648320925,'2026-10-05 08:31:17.010462',10,1,18),(144,'2026-10-07 02:31:17.231000','2026-10-07 02:31:17.231000',32.99547668400804,'2026-10-05 08:31:17.010462',11,4,19),(145,'2026-10-07 02:31:17.232000','2026-10-07 02:31:17.232000',980.1681390048435,'2026-10-05 08:31:17.010462',11,6,20),(146,'2026-10-07 02:31:17.234000','2026-10-07 02:31:17.234000',1.7100536268652036,'2026-10-05 08:31:17.010462',12,5,21),(147,'2026-10-07 02:31:17.235000','2026-10-07 02:31:17.235000',20.240355672941657,'2026-10-05 08:31:17.010462',12,1,22),(148,'2026-10-07 02:31:17.237000','2026-10-07 02:31:17.237000',3.5428399764780747,'2026-10-05 09:31:17.010462',1,1,1),(149,'2026-10-07 02:31:17.238000','2026-10-07 02:31:17.238000',42.89414566907358,'2026-10-05 09:31:17.010462',1,2,2),(150,'2026-10-07 02:31:17.239000','2026-10-07 02:31:17.239000',741.1095835019659,'2026-10-05 09:31:17.010462',2,3,3),(151,'2026-10-07 02:31:17.240000','2026-10-07 02:31:17.240000',-191.316158956829,'2026-10-05 09:31:17.010462',3,1,4),(152,'2026-10-07 02:31:17.241000','2026-10-07 02:31:17.241000',1003.1806746672271,'2026-10-05 09:31:17.010462',3,6,5),(153,'2026-10-07 02:31:17.242000','2026-10-07 02:31:17.242000',44.21233835476204,'2026-10-05 09:31:17.010462',2,4,6),(154,'2026-10-07 02:31:17.244000','2026-10-07 02:31:17.244000',1.9974638354230394,'2026-10-05 09:31:17.010462',2,5,7),(155,'2026-10-07 02:31:17.245000','2026-10-07 02:31:17.245000',21.736768208634196,'2026-10-05 09:31:17.010462',5,1,9),(156,'2026-10-07 02:31:17.247000','2026-10-07 02:31:17.247000',43.30422569508082,'2026-10-05 09:31:17.010462',5,2,10),(157,'2026-10-07 02:31:17.248000','2026-10-07 02:31:17.248000',111.81722888935589,'2026-10-05 09:31:17.010462',6,6,11),(158,'2026-10-07 02:31:17.250000','2026-10-07 02:31:17.250000',19.823663089433047,'2026-10-05 09:31:17.010462',6,4,12),(159,'2026-10-07 02:31:17.251000','2026-10-07 02:31:17.251000',19.48951465412164,'2026-10-05 09:31:17.010462',7,1,13),(160,'2026-10-07 02:31:17.252000','2026-10-07 02:31:17.252000',1004.1109583501966,'2026-10-05 09:31:17.010462',7,6,14),(161,'2026-10-07 02:31:17.253000','2026-10-07 02:31:17.253000',21.92946523577322,'2026-10-05 09:31:17.010462',8,1,15),(162,'2026-10-07 02:31:17.254000','2026-10-07 02:31:17.254000',526.5301569595205,'2026-10-05 09:31:17.010462',8,3,16),(163,'2026-10-07 02:31:17.255000','2026-10-07 02:31:17.255000',20.683155181947125,'2026-10-05 09:31:17.010462',9,1,17),(164,'2026-10-07 02:31:17.256000','2026-10-07 02:31:17.256000',36.180946658826024,'2026-10-05 09:31:17.010462',10,1,18),(165,'2026-10-07 02:31:17.258000','2026-10-07 02:31:17.258000',32.71786094309287,'2026-10-05 09:31:17.010462',11,4,19),(166,'2026-10-07 02:31:17.259000','2026-10-07 02:31:17.259000',978.6720809114748,'2026-10-05 09:31:17.010462',11,6,20),(167,'2026-10-07 02:31:17.260000','2026-10-07 02:31:17.260000',1.5104853458783603,'2026-10-05 09:31:17.010462',12,5,21),(168,'2026-10-07 02:31:17.261000','2026-10-07 02:31:17.261000',20.33042256950808,'2026-10-05 09:31:17.010462',12,1,22),(169,'2026-10-07 02:31:17.262000','2026-10-07 02:31:17.262000',4.068145025002402,'2026-10-05 10:31:17.010462',1,1,1),(170,'2026-10-07 02:31:17.263000','2026-10-07 02:31:17.263000',40.70741665599652,'2026-10-05 10:31:17.010462',1,2,2),(171,'2026-10-07 02:31:17.264000','2026-10-07 02:31:17.264000',748.4037369935072,'2026-10-05 10:31:17.010462',2,3,3),(172,'2026-10-07 02:31:17.265000','2026-10-07 02:31:17.265000',-192.68286459000217,'2026-10-05 10:31:17.010462',3,1,4),(173,'2026-10-07 02:31:17.266000','2026-10-07 02:31:17.266000',1003.9183700563049,'2026-10-05 10:31:17.010462',3,6,5),(174,'2026-10-07 02:31:17.266000','2026-10-07 02:31:17.266000',41.603167082440805,'2026-10-05 10:31:17.010462',2,4,6),(175,'2026-10-07 02:31:17.267000','2026-10-07 02:31:17.267000',1.9303562005821022,'2026-10-05 10:31:17.010462',2,5,7),(176,'2026-10-07 02:31:17.268000','2026-10-07 02:31:17.268000',21.463427081999566,'2026-10-05 10:31:17.010462',5,1,9),(177,'2026-10-07 02:31:17.270000','2026-10-07 02:31:17.270000',44.27249983095693,'2026-10-05 10:31:17.010462',5,2,10),(178,'2026-10-07 02:31:17.271000','2026-10-07 02:31:17.271000',112.43197504692071,'2026-10-05 10:31:17.010462',6,6,11),(179,'2026-10-07 02:31:17.273000','2026-10-07 02:31:17.273000',19.219269793662463,'2026-10-05 10:31:17.010462',6,4,12),(180,'2026-10-07 02:31:17.274000','2026-10-07 02:31:17.274000',19.769677346667947,'2026-10-05 10:31:17.010462',7,1,13),(181,'2026-10-07 02:31:17.275000','2026-10-07 02:31:17.275000',1004.8403736993507,'2026-10-05 10:31:17.010462',7,6,14),(182,'2026-10-07 02:31:17.276000','2026-10-07 02:31:17.276000',21.687707917464984,'2026-10-05 10:31:17.010462',8,1,15),(183,'2026-10-07 02:31:17.277000','2026-10-07 02:31:17.277000',545.597888911063,'2026-10-05 10:31:17.010462',8,3,16),(184,'2026-10-07 02:31:17.279000','2026-10-07 02:31:17.279000',21.20510582731175,'2026-10-05 10:31:17.010462',9,1,17),(185,'2026-10-07 02:31:17.279000','2026-10-07 02:31:17.279000',36.35604834166747,'2026-10-05 10:31:17.010462',10,1,18),(186,'2026-10-07 02:31:17.283000','2026-10-07 02:31:17.283000',31.750831669859938,'2026-10-05 10:31:17.010462',11,4,19),(187,'2026-10-07 02:31:17.284000','2026-10-07 02:31:17.284000',977.2058450180108,'2026-10-05 10:31:17.010462',11,6,20),(188,'2026-10-07 02:31:17.285000','2026-10-07 02:31:17.285000',1.2303226533320522,'2026-10-05 10:31:17.010462',12,5,21),(189,'2026-10-07 02:31:17.286000','2026-10-07 02:31:17.286000',20.42724998309569,'2026-10-05 10:31:17.010462',12,1,22),(190,'2026-10-07 02:31:17.288000','2026-10-07 02:31:17.288000',4.661630478449164,'2026-10-05 11:31:17.010462',1,1,1),(191,'2026-10-07 02:31:17.289000','2026-10-07 02:31:17.289000',38.904131328647736,'2026-10-05 11:31:17.010462',1,2,2),(192,'2026-10-07 02:31:17.289000','2026-10-07 02:31:17.289000',749.7815018061908,'2026-10-05 11:31:17.010462',2,3,3),(193,'2026-10-07 02:31:17.290000','2026-10-07 02:31:17.290000',-193.80991791959516,'2026-10-05 11:31:17.010462',3,1,4),(194,'2026-10-07 02:31:17.291000','2026-10-07 02:31:17.291000',1004.7468060897924,'2026-10-05 11:31:17.010462',3,6,5),(195,'2026-10-07 02:31:17.292000','2026-10-07 02:31:17.292000',37.679266976706764,'2026-10-05 11:31:17.010462',2,4,6),(196,'2026-10-07 02:31:17.293000','2026-10-07 02:31:17.293000',1.6844318957756808,'2026-10-05 11:31:17.010462',2,5,7),(197,'2026-10-07 02:31:17.295000','2026-10-07 02:31:17.295000',21.238016416080967,'2026-10-05 11:31:17.010462',5,1,9),(198,'2026-10-07 02:31:17.296000','2026-10-07 02:31:17.296000',45.269777102813244,'2026-10-05 11:31:17.010462',5,2,10),(199,'2026-10-07 02:31:17.297000','2026-10-07 02:31:17.297000',113.12233840816026,'2026-10-05 11:31:17.010462',6,6,11),(200,'2026-10-07 02:31:17.298000','2026-10-07 02:31:17.298000',18.23798169326938,'2026-10-05 11:31:17.010462',6,4,12),(201,'2026-10-07 02:31:17.300000','2026-10-07 02:31:17.300000',20.086202921839554,'2026-10-05 11:31:17.010462',7,1,13),(202,'2026-10-07 02:31:17.301000','2026-10-07 02:31:17.301000',1004.9781501806191,'2026-10-05 11:31:17.010462',7,6,14),(203,'2026-10-07 02:31:17.302000','2026-10-07 02:31:17.302000',21.295192677307753,'2026-10-05 11:31:17.010462',8,1,15),(204,'2026-10-07 02:31:17.303000','2026-10-07 02:31:17.303000',568.0480806377726,'2026-10-05 11:31:17.010462',8,3,16),(205,'2026-10-07 02:31:17.304000','2026-10-07 02:31:17.304000',21.702186452898168,'2026-10-05 11:31:17.010462',9,1,17),(206,'2026-10-07 02:31:17.305000','2026-10-07 02:31:17.305000',36.55387682614972,'2026-10-05 11:31:17.010462',10,1,18),(207,'2026-10-07 02:31:17.306000','2026-10-07 02:31:17.306000',30.180770709231005,'2026-10-05 11:31:17.010462',11,4,19),(208,'2026-10-07 02:31:17.307000','2026-10-07 02:31:17.307000',975.8023598216014,'2026-10-05 11:31:17.010462',11,6,20),(209,'2026-10-07 02:31:17.308000','2026-10-07 02:31:17.308000',1.086202921839554,'2026-10-05 11:31:17.010462',12,5,21),(210,'2026-10-07 02:31:17.309000','2026-10-07 02:31:17.309000',20.526977710281326,'2026-10-05 11:31:17.010462',12,1,22),(211,'2026-10-07 02:31:17.310000','2026-10-07 02:31:17.310000',5.2295980332806975,'2026-10-05 12:31:17.010462',1,1,1),(212,'2026-10-07 02:31:17.311000','2026-10-07 02:31:17.311000',37.645371794682596,'2026-10-05 12:31:17.010462',1,2,2),(213,'2026-10-07 02:31:17.312000','2026-10-07 02:31:17.312000',745.187950804723,'2026-10-05 12:31:17.010462',2,3,3),(214,'2026-10-07 02:31:17.313000','2026-10-07 02:31:17.313000',-194.59664262832337,'2026-10-05 12:31:17.010462',3,1,4),(215,'2026-10-07 02:31:17.315000','2026-10-07 02:31:17.315000',1005.6577053086874,'2026-10-05 12:31:17.010462',3,6,5),(216,'2026-10-07 02:31:17.316000','2026-10-07 02:31:17.316000',33.22190928876884,'2026-10-05 12:31:17.010462',2,4,6),(217,'2026-10-07 02:31:17.317000','2026-10-07 02:31:17.317000',1.3199017677303617,'2026-10-05 12:31:17.010462',2,5,7),(218,'2026-10-07 02:31:17.318000','2026-10-07 02:31:17.318000',21.080671474335325,'2026-10-05 12:31:17.010462',5,1,9),(219,'2026-10-07 02:31:17.319000','2026-10-07 02:31:17.319000',46.25629921291127,'2026-10-05 12:31:17.010462',5,2,10),(220,'2026-10-07 02:31:17.320000','2026-10-07 02:31:17.320000',113.88142109057281,'2026-10-05 12:31:17.010462',6,6,11),(221,'2026-10-07 02:31:17.321000','2026-10-07 02:31:17.321000',16.967454331739454,'2026-10-05 12:31:17.010462',6,4,12),(222,'2026-10-07 02:31:17.322000','2026-10-07 02:31:17.322000',20.389118951083038,'2026-10-05 12:31:17.010462',7,1,13),(223,'2026-10-07 02:31:17.323000','2026-10-07 02:31:17.323000',1004.5187950804723,'2026-10-05 12:31:17.010462',7,6,14),(224,'2026-10-07 02:31:17.323000','2026-10-07 02:31:17.323000',20.786981732695782,'2026-10-05 12:31:17.010462',8,1,15),(225,'2026-10-07 02:31:17.325000','2026-10-07 02:31:17.325000',592.4848879538191,'2026-10-05 12:31:17.010462',8,3,16),(226,'2026-10-07 02:31:17.326000','2026-10-07 02:31:17.326000',22.114123759055506,'2026-10-05 12:31:17.010462',9,1,17),(227,'2026-10-07 02:31:17.326000','2026-10-07 02:31:17.326000',36.7431993444269,'2026-10-05 12:31:17.010462',10,1,18),(228,'2026-10-07 02:31:17.327000','2026-10-07 02:31:17.327000',28.14792693078313,'2026-10-05 12:31:17.010462',11,4,19),(229,'2026-10-07 02:31:17.328000','2026-10-07 02:31:17.328000',974.4931445740236,'2026-10-05 12:31:17.010462',11,6,20),(230,'2026-10-07 02:31:17.329000','2026-10-07 02:31:17.329000',1.3891189510830384,'2026-10-05 12:31:17.010462',12,5,21),(231,'2026-10-07 02:31:17.330000','2026-10-07 02:31:17.330000',20.62562992129113,'2026-10-05 12:31:17.010462',12,1,22),(232,'2026-10-07 02:31:17.331000','2026-10-07 02:31:17.331000',5.682378101062975,'2026-10-05 13:31:17.010462',1,1,1),(233,'2026-10-07 02:31:17.332000','2026-10-07 02:31:17.332000',37.04357929436809,'2026-10-05 13:31:17.010462',1,2,2),(234,'2026-10-07 02:31:17.333000','2026-10-07 02:31:17.333000',734.8062143717441,'2026-10-05 13:31:17.010462',2,3,3),(235,'2026-10-07 02:31:17.339000','2026-10-07 02:31:17.339000',-194.97276294101994,'2026-10-05 13:31:17.010462',3,1,4),(236,'2026-10-07 02:31:17.343000','2026-10-07 02:31:17.343000',1006.641966309098,'2026-10-05 13:31:17.010462',3,6,5),(237,'2026-10-07 02:31:17.345000','2026-10-07 02:31:17.345000',29.11857976599663,'2026-10-05 13:31:17.010462',2,4,6),(238,'2026-10-07 02:31:17.347000','2026-10-07 02:31:17.347000',1.47398449477569,'2026-10-05 13:31:17.010462',2,5,7),(239,'2026-10-07 02:31:17.348000','2026-10-07 02:31:17.348000',21.005447411796013,'2026-10-05 13:31:17.010462',5,1,9),(240,'2026-10-07 02:31:17.350000','2026-10-07 02:31:17.350000',47.19273663787195,'2026-10-05 13:31:17.010462',5,2,10),(241,'2026-10-07 02:31:17.351000','2026-10-07 02:31:17.351000',114.70163859091507,'2026-10-05 13:31:17.010462',6,6,11),(242,'2026-10-07 02:31:17.352000','2026-10-07 02:31:17.352000',15.521180134328484,'2026-10-05 13:31:17.010462',6,4,12),(243,'2026-10-07 02:31:17.357000','2026-10-07 02:31:17.357000',20.630601653900253,'2026-10-05 13:31:17.010462',7,1,13),(244,'2026-10-07 02:31:17.358000','2026-10-07 02:31:17.358000',1003.4806214371744,'2026-10-05 13:31:17.010462',7,6,14),(245,'2026-10-07 02:31:17.359000','2026-10-07 02:31:17.359000',20.208472053731395,'2026-10-05 13:31:17.010462',8,1,15),(246,'2026-10-07 02:31:17.360000','2026-10-07 02:31:17.360000',617.3889485380433,'2026-10-05 13:31:17.010462',8,3,16),(247,'2026-10-07 02:31:17.361000','2026-10-07 02:31:17.361000',22.390968463085215,'2026-10-05 13:31:17.010462',9,1,17),(248,'2026-10-07 02:31:17.362000','2026-10-07 02:31:17.362000',36.894126033687655,'2026-10-05 13:31:17.010462',10,1,18),(249,'2026-10-07 02:31:17.363000','2026-10-07 02:31:17.363000',25.833888214925576,'2026-10-05 13:31:17.010462',11,4,19),(250,'2026-10-07 02:31:17.364000','2026-10-07 02:31:17.364000',973.3076014272374,'2026-10-05 13:31:17.010462',11,6,20),(251,'2026-10-07 02:31:17.366000','2026-10-07 02:31:17.366000',1.630601653900253,'2026-10-05 13:31:17.010462',12,5,21),(252,'2026-10-07 02:31:17.366000','2026-10-07 02:31:17.366000',20.719273663787195,'2026-10-05 13:31:17.010462',12,1,22),(253,'2026-10-07 02:31:17.367000','2026-10-07 02:31:17.367000',5.948486664823916,'2026-10-05 14:31:17.010462',1,1,1),(254,'2026-10-07 02:31:17.368000','2026-10-07 02:31:17.368000',37.15251015946807,'2026-10-05 14:31:17.010462',1,2,2),(255,'2026-10-07 02:31:17.368000','2026-10-07 02:31:17.368000',719.050179577373,'2026-10-05 14:31:17.010462',2,3,3),(256,'2026-10-07 02:31:17.369000','2026-10-07 02:31:17.369000',-194.90468115033246,'2026-10-05 14:31:17.010462',3,1,4),(257,'2026-10-07 02:31:17.370000','2026-10-07 02:31:17.370000',1007.6897546804618,'2026-10-05 14:31:17.010462',3,6,5),(258,'2026-10-07 02:31:17.371000','2026-10-07 02:31:17.371000',26.186275096377653,'2026-10-05 14:31:17.010462',2,4,6),(259,'2026-10-07 02:31:17.372000','2026-10-07 02:31:17.372000',1.8007897974173408,'2026-10-05 14:31:17.010462',2,5,7),(260,'2026-10-07 02:31:17.372000','2026-10-07 02:31:17.372000',21.01906376993351,'2026-10-05 14:31:17.010462',5,1,9),(261,'2026-10-07 02:31:17.373000','2026-10-07 02:31:17.373000',48.04175657266127,'2026-10-05 14:31:17.010462',5,2,10),(262,'2026-10-07 02:31:17.374000','2026-10-07 02:31:17.374000',115.57479556705148,'2026-10-05 14:31:17.010462',6,6,11),(263,'2026-10-07 02:31:17.375000','2026-10-07 02:31:17.375000',14.028350467723318,'2026-10-05 14:31:17.010462',6,4,12),(264,'2026-10-07 02:31:17.376000','2026-10-07 02:31:17.376000',20.77252622123942,'2026-10-05 14:31:17.010462',7,1,13),(265,'2026-10-07 02:31:17.377000','2026-10-07 02:31:17.377000',1001.9050179577373,'2026-10-05 14:31:17.010462',7,6,14),(266,'2026-10-07 02:31:17.378000','2026-10-07 02:31:17.378000',19.611340187089326,'2026-10-05 14:31:17.010462',8,1,15),(267,'2026-10-07 02:31:17.379000','2026-10-07 02:31:17.379000',641.2118485241757,'2026-10-05 14:31:17.010462',8,3,16),(268,'2026-10-07 02:31:17.379000','2026-10-07 02:31:17.379000',22.499151878251507,'2026-10-05 14:31:17.010462',9,1,17),(269,'2026-10-07 02:31:17.380000','2026-10-07 02:31:17.380000',36.98282888827464,'2026-10-05 14:31:17.010462',10,1,18),(270,'2026-10-07 02:31:17.381000','2026-10-07 02:31:17.381000',23.44536074835731,'2026-10-05 14:31:17.010462',11,4,19),(271,'2026-10-07 02:31:17.381000','2026-10-07 02:31:17.381000',972.2723551244401,'2026-10-05 14:31:17.010462',11,6,20),(272,'2026-10-07 02:31:17.382000','2026-10-07 02:31:17.382000',1.772526221239422,'2026-10-05 14:31:17.010462',12,5,21),(273,'2026-10-07 02:31:17.383000','2026-10-07 02:31:17.383000',20.80417565726613,'2026-10-05 14:31:17.010462',12,1,22),(274,'2026-10-07 02:31:17.383000','2026-10-07 02:31:17.383000',5.985911033542306,'2026-10-05 15:31:17.010462',1,1,1),(275,'2026-10-07 02:31:17.384000','2026-10-07 02:31:17.384000',37.96243392022664,'2026-10-05 15:31:17.010462',1,2,2),(276,'2026-10-07 02:31:17.385000','2026-10-07 02:31:17.385000',698.5479898078183,'2026-10-05 15:31:17.010462',2,3,3),(277,'2026-10-07 02:31:17.385000','2026-10-07 02:31:17.385000',-194.39847879985834,'2026-10-05 15:31:17.010462',3,1,4),(278,'2026-10-07 02:31:17.389000','2026-10-07 02:31:17.389000',1008.7906012677246,'2026-10-05 15:31:17.010462',3,6,5),(279,'2026-10-07 02:31:17.390000','2026-10-07 02:31:17.390000',25.00883413320266,'2026-10-05 15:31:17.010462',2,4,6),(280,'2026-10-07 02:31:17.391000','2026-10-07 02:31:17.391000',1.980500804374526,'2026-10-05 15:31:17.010462',2,5,7),(281,'2026-10-07 02:31:17.391000','2026-10-07 02:31:17.391000',21.12030424002833,'2026-10-05 15:31:17.010462',5,1,9),(282,'2026-10-07 02:31:17.392000','2026-10-07 02:31:17.392000',48.769511271716524,'2026-10-05 15:31:17.010462',5,2,10),(283,'2026-10-07 02:31:17.393000','2026-10-07 02:31:17.393000',116.4921677231038,'2026-10-05 15:31:17.010462',6,6,11),(284,'2026-10-07 02:31:17.393000','2026-10-07 02:31:17.393000',12.622315360020037,'2026-10-05 15:31:17.010462',6,4,12),(285,'2026-10-07 02:31:17.394000','2026-10-07 02:31:17.394000',20.792485884555898,'2026-10-05 15:31:17.010462',7,1,13),(286,'2026-10-07 02:31:17.395000','2026-10-07 02:31:17.395000',999.8547989807819,'2026-10-05 15:31:17.010462',7,6,14),(287,'2026-10-07 02:31:17.396000','2026-10-07 02:31:17.396000',19.048926144008014,'2026-10-05 15:31:17.010462',8,1,15),(288,'2026-10-07 02:31:17.397000','2026-10-07 02:31:17.397000',662.4723953754192,'2026-10-05 15:31:17.010462',8,3,16),(289,'2026-10-07 02:31:17.398000','2026-10-07 02:31:17.398000',22.42555627060151,'2026-10-05 15:31:17.010462',9,1,17),(290,'2026-10-07 02:31:17.399000','2026-10-07 02:31:17.399000',36.99530367784744,'2026-10-05 15:31:17.010462',10,1,18),(291,'2026-10-07 02:31:17.399000','2026-10-07 02:31:17.399000',21.19570457603206,'2026-10-05 15:31:17.010462',11,4,19),(292,'2026-10-07 02:31:17.400000','2026-10-07 02:31:17.400000',971.4106550657341,'2026-10-05 15:31:17.010462',11,6,20),(293,'2026-10-07 02:31:17.401000','2026-10-07 02:31:17.401000',1.7924858845558962,'2026-10-05 15:31:17.010462',12,5,21),(294,'2026-10-07 02:31:17.402000','2026-10-07 02:31:17.402000',20.87695112717165,'2026-10-05 15:31:17.010462',12,1,22),(295,'2026-10-07 02:31:17.403000','2026-10-07 02:31:17.403000',5.788742722284745,'2026-10-05 16:31:17.010462',1,1,1),(296,'2026-10-07 02:31:17.404000','2026-10-07 02:31:17.404000',39.40100249925166,'2026-10-05 16:31:17.010462',1,2,2),(297,'2026-10-07 02:31:17.405000','2026-10-07 02:31:17.405000',674.1170026707913,'2026-10-05 16:31:17.010462',2,3,3),(298,'2026-10-07 02:31:17.405000','2026-10-07 02:31:17.405000',-193.4993734379677,'2026-10-05 16:31:17.010462',3,1,4),(299,'2026-10-07 02:31:17.406000','2026-10-07 02:31:17.406000',1009.933506775678,'2026-10-05 16:31:17.010462',3,6,5),(300,'2026-10-07 02:31:17.407000','2026-10-07 02:31:17.407000',25.82069219585707,'2026-10-05 16:31:17.010462',2,4,6),(301,'2026-10-07 02:31:17.408000','2026-10-07 02:31:17.408000',1.9691179935036454,'2026-10-05 16:31:17.010462',2,5,7),(302,'2026-10-07 02:31:17.409000','2026-10-07 02:31:17.409000',21.300125312406458,'2026-10-05 16:31:17.010462',5,1,9),(303,'2026-10-07 02:31:17.410000','2026-10-07 02:31:17.410000',49.34698745174912,'2026-10-05 16:31:17.010462',5,2,10),(304,'2026-10-07 02:31:17.411000','2026-10-07 02:31:17.411000',117.44458897973168,'2026-10-05 16:31:17.010462',6,6,11),(305,'2026-10-07 02:31:17.412000','2026-10-07 02:31:17.412000',11.428671739863999,'2026-10-05 16:31:17.010462',6,4,12),(306,'2026-10-07 02:31:17.413000','2026-10-07 02:31:17.413000',20.687329451885198,'2026-10-05 16:31:17.010462',7,1,13),(307,'2026-10-07 02:31:17.414000','2026-10-07 02:31:17.414000',997.4117002670791,'2026-10-05 16:31:17.010462',7,6,14),(308,'2026-10-07 02:31:17.415000','2026-10-07 02:31:17.415000',18.5714686959456,'2026-10-05 16:31:17.010462',8,1,15),(309,'2026-10-07 02:31:17.416000','2026-10-07 02:31:17.416000',679.8487112623491,'2026-10-05 16:31:17.010462',8,3,16),(310,'2026-10-07 02:31:17.417000','2026-10-07 02:31:17.417000',22.179105444211558,'2026-10-05 16:31:17.010462',9,1,17),(311,'2026-10-07 02:31:17.418000','2026-10-07 02:31:17.418000',36.929580907428246,'2026-10-05 16:31:17.010462',10,1,18),(312,'2026-10-07 02:31:17.419000','2026-10-07 02:31:17.419000',19.285874783782397,'2026-10-05 16:31:17.010462',11,4,19),(313,'2026-10-07 02:31:17.420000','2026-10-07 02:31:17.420000',970.7418531767227,'2026-10-05 16:31:17.010462',11,6,20),(314,'2026-10-07 02:31:17.421000','2026-10-07 02:31:17.421000',1.6873294518851973,'2026-10-05 16:31:17.010462',12,5,21),(315,'2026-10-07 02:31:17.422000','2026-10-07 02:31:17.422000',20.934698745174913,'2026-10-05 16:31:17.010462',12,1,22),(316,'2026-10-07 02:31:17.423000','2026-10-07 02:31:17.423000',5.388110272060837,'2026-10-05 17:31:17.010462',1,1,1),(317,'2026-10-07 02:31:17.424000','2026-10-07 02:31:17.424000',41.33971284979743,'2026-10-05 17:31:17.010462',1,2,2),(318,'2026-10-07 02:31:17.425000','2026-10-07 02:31:17.425000',646.7312045270068,'2026-10-05 17:31:17.010462',2,3,3),(319,'2026-10-07 02:31:17.425000','2026-10-07 02:31:17.425000',-192.2876794688766,'2026-10-05 17:31:17.010462',3,1,4),(320,'2026-10-07 02:31:17.426000','2026-10-07 02:31:17.426000',1011.1070516702811,'2026-10-05 17:31:17.010462',3,6,5),(321,'2026-10-07 02:31:17.427000','2026-10-07 02:31:17.427000',28.460203639109714,'2026-10-05 17:31:17.010462',2,4,6),(322,'2026-10-07 02:31:17.428000','2026-10-07 02:31:17.428000',1.7694282738952984,'2026-10-05 17:31:17.010462',2,5,7),(323,'2026-10-07 02:31:17.429000','2026-10-07 02:31:17.429000',21.542464106224678,'2026-10-05 17:31:17.010462',5,1,9),(324,'2026-10-07 02:31:17.430000','2026-10-07 02:31:17.430000',49.751162959792644,'2026-10-05 17:31:17.010462',5,2,10),(325,'2026-10-07 02:31:17.431000','2026-10-07 02:31:17.431000',118.42254305856751,'2026-10-05 17:31:17.010462',6,6,11),(326,'2026-10-07 02:31:17.432000','2026-10-07 02:31:17.432000',10.554044236873196,'2026-10-05 17:31:17.010462',6,4,12),(327,'2026-10-07 02:31:17.433000','2026-10-07 02:31:17.433000',20.47365881176578,'2026-10-05 17:31:17.010462',7,1,13),(328,'2026-10-07 02:31:17.434000','2026-10-07 02:31:17.434000',994.6731204527007,'2026-10-05 17:31:17.010462',7,6,14),(329,'2026-10-07 02:31:17.435000','2026-10-07 02:31:17.435000',18.221617694749277,'2026-10-05 17:31:17.010462',8,1,15),(330,'2026-10-07 02:31:17.436000','2026-10-07 02:31:17.436000',692.260421023934,'2026-10-05 17:31:17.010462',8,3,16),(331,'2026-10-07 02:31:17.437000','2026-10-07 02:31:17.437000',21.78968268912274,'2026-10-05 17:31:17.010462',9,1,17),(332,'2026-10-07 02:31:17.438000','2026-10-07 02:31:17.438000',36.79603675735361,'2026-10-05 17:31:17.010462',10,1,18),(333,'2026-10-07 02:31:17.439000','2026-10-07 02:31:17.439000',17.886470778997115,'2026-10-05 17:31:17.010462',11,4,19),(334,'2026-10-07 02:31:17.440000','2026-10-07 02:31:17.440000',970.2809693059818,'2026-10-05 17:31:17.010462',11,6,20),(335,'2026-10-07 02:31:17.440000','2026-10-07 02:31:17.440000',1.4736588117657796,'2026-10-05 17:31:17.010462',12,5,21),(336,'2026-10-07 02:31:17.441000','2026-10-07 02:31:17.441000',20.975116295979266,'2026-10-05 17:31:17.010462',12,1,22),(337,'2026-10-07 02:31:17.442000','2026-10-07 02:31:17.442000',4.847264737652308,'2026-10-05 18:31:17.010462',1,1,1),(338,'2026-10-07 02:31:17.443000','2026-10-07 02:31:17.443000',43.605385750216165,'2026-10-05 18:31:17.010462',1,2,2),(339,'2026-10-07 02:31:17.444000','2026-10-07 02:31:17.444000',617.4823807275741,'2026-10-05 18:31:17.010462',2,3,3),(340,'2026-10-07 02:31:17.445000','2026-10-07 02:31:17.445000',-190.8716339061149,'2026-10-05 18:31:17.010462',3,1,4),(341,'2026-10-07 02:31:17.446000','2026-10-07 02:31:17.446000',1012.2995102788691,'2026-10-05 18:31:17.010462',3,6,5),(342,'2026-10-07 02:31:17.446000','2026-10-07 02:31:17.446000',32.40182643786244,'2026-10-05 18:31:17.010462',2,4,6),(343,'2026-10-07 02:31:17.447000','2026-10-07 02:31:17.447000',1.4303226533320523,'2026-10-05 18:31:17.010462',2,5,7),(344,'2026-10-07 02:31:17.448000','2026-10-07 02:31:17.448000',21.82567321877702,'2026-10-05 18:31:17.010462',5,1,9),(345,'2026-10-07 02:31:17.449000','2026-10-07 02:31:17.449000',49.965924593790966,'2026-10-05 18:31:17.010462',5,2,10),(346,'2026-10-07 02:31:17.450000','2026-10-07 02:31:17.450000',119.4162585657242,'2026-10-05 18:31:17.010462',6,6,11),(347,'2026-10-07 02:31:17.451000','2026-10-07 02:31:17.451000',10.076560721029365,'2026-10-05 18:31:17.010462',6,4,12),(348,'2026-10-07 02:31:17.452000','2026-10-07 02:31:17.452000',20.18520786008123,'2026-10-05 18:31:17.010462',7,1,13),(349,'2026-10-07 02:31:17.453000','2026-10-07 02:31:17.453000',991.7482380727574,'2026-10-05 18:31:17.010462',7,6,14),(350,'2026-10-07 02:31:17.453000','2026-10-07 02:31:17.453000',18.030624288411747,'2026-10-05 18:31:17.010462',8,1,15),(351,'2026-10-07 02:31:17.454000','2026-10-07 02:31:17.454000',698.9358246623382,'2026-10-05 18:31:17.010462',8,3,16),(352,'2026-10-07 02:31:17.455000','2026-10-07 02:31:17.455000',21.304507295728126,'2026-10-05 18:31:17.010462',9,1,17),(353,'2026-10-07 02:31:17.456000','2026-10-07 02:31:17.456000',36.61575491255077,'2026-10-05 18:31:17.010462',10,1,18),(354,'2026-10-07 02:31:17.457000','2026-10-07 02:31:17.457000',17.122497153646982,'2026-10-05 18:31:17.010462',11,4,19),(355,'2026-10-07 02:31:17.458000','2026-10-07 02:31:17.458000',970.0383539116416,'2026-10-05 18:31:17.010462',11,6,20),(356,'2026-10-07 02:31:17.459000','2026-10-07 02:31:17.459000',1.1852078600812312,'2026-10-05 18:31:17.010462',12,5,21),(357,'2026-10-07 02:31:17.460000','2026-10-07 02:31:17.460000',20.996592459379098,'2026-10-05 18:31:17.010462',12,1,22),(358,'2026-10-07 02:31:17.461000','2026-10-07 02:31:17.461000',4.251593736827536,'2026-10-05 19:31:17.010462',1,1,1),(359,'2026-10-07 02:31:17.462000','2026-10-07 02:31:17.462000',45.995635388056506,'2026-10-05 19:31:17.010462',1,2,2),(360,'2026-10-07 02:31:17.463000','2026-10-07 02:31:17.463000',587.5365895773756,'2026-10-05 19:31:17.010462',2,3,3),(361,'2026-10-07 02:31:17.464000','2026-10-07 02:31:17.464000',-189.37772788246468,'2026-10-05 19:31:17.010462',3,1,4),(362,'2026-10-07 02:31:17.465000','2026-10-07 02:31:17.465000',1013.4989679491995,'2026-10-05 19:31:17.010462',3,6,5),(363,'2026-10-07 02:31:17.466000','2026-10-07 02:31:17.466000',36.86076065001809,'2026-10-05 19:31:17.010462',2,4,6),(364,'2026-10-07 02:31:17.467000','2026-10-07 02:31:17.467000',1.3651739855502372,'2026-10-05 19:31:17.010462',2,5,7),(365,'2026-10-07 02:31:17.468000','2026-10-07 02:31:17.468000',22.124454423507064,'2026-10-05 19:31:17.010462',5,1,9),(366,'2026-10-07 02:31:17.470000','2026-10-07 02:31:17.470000',49.98271048511609,'2026-10-05 19:31:17.010462',5,2,10),(367,'2026-10-07 02:31:17.471000','2026-10-07 02:31:17.471000',120.4158066243329,'2026-10-05 19:31:17.010462',6,6,11),(368,'2026-10-07 02:31:17.472000','2026-10-07 02:31:17.472000',10.038873372736983,'2026-10-05 19:31:17.010462',6,4,12),(369,'2026-10-07 02:31:17.473000','2026-10-07 02:31:17.473000',19.867516659641353,'2026-10-05 19:31:17.010462',7,1,13),(370,'2026-10-07 02:31:17.474000','2026-10-07 02:31:17.474000',988.7536589577376,'2026-10-05 19:31:17.010462',7,6,14),(371,'2026-10-07 02:31:17.474000','2026-10-07 02:31:17.474000',18.015549349094794,'2026-10-05 19:31:17.010462',8,1,15),(372,'2026-10-07 02:31:17.475000','2026-10-07 02:31:17.475000',699.4598779111176,'2026-10-05 19:31:17.010462',8,3,16),(373,'2026-10-07 02:31:17.476000','2026-10-07 02:31:17.476000',20.782408999817157,'2026-10-05 19:31:17.010462',9,1,17),(374,'2026-10-07 02:31:17.477000','2026-10-07 02:31:17.477000',36.417197912275846,'2026-10-05 19:31:17.010462',10,1,18),(375,'2026-10-07 02:31:17.478000','2026-10-07 02:31:17.478000',17.062197396379176,'2026-10-05 19:31:17.010462',11,4,19),(376,'2026-10-07 02:31:17.478000','2026-10-07 02:31:17.478000',970.0194556124112,'2026-10-05 19:31:17.010462',11,6,20),(377,'2026-10-07 02:31:17.479000','2026-10-07 02:31:17.479000',1.1324833403586476,'2026-10-05 19:31:17.010462',12,5,21),(378,'2026-10-07 02:31:17.480000','2026-10-07 02:31:17.480000',20.998271048511608,'2026-10-05 19:31:17.010462',12,1,22),(379,'2026-10-07 02:31:17.480000','2026-10-07 02:31:17.480000',3.6951406229993475,'2026-10-05 20:31:17.010462',1,1,1),(380,'2026-10-07 02:31:17.481000','2026-10-07 02:31:17.481000',48.29694788193405,'2026-10-05 20:31:17.010462',1,2,2),(381,'2026-10-07 02:31:17.482000','2026-10-07 02:31:17.482000',558.0876752701611,'2026-10-05 20:31:17.010462',2,3,3),(382,'2026-10-07 02:31:17.483000','2026-10-07 02:31:17.483000',-187.93940757379121,'2026-10-05 20:31:17.010462',3,1,4),(383,'2026-10-07 02:31:17.484000','2026-10-07 02:31:17.484000',1014.6934400967184,'2026-10-05 20:31:17.010462',3,6,5),(384,'2026-10-07 02:31:17.485000','2026-10-07 02:31:17.485000',40.94920663309892,'2026-10-05 20:31:17.010462',2,4,6),(385,'2026-10-07 02:31:17.486000','2026-10-07 02:31:17.486000',1.7202302721256935,'2026-10-05 20:31:17.010462',2,5,7),(386,'2026-10-07 02:31:17.487000','2026-10-07 02:31:17.487000',22.412118485241756,'2026-10-05 20:31:17.010462',5,1,9),(387,'2026-10-07 02:31:17.488000','2026-10-07 02:31:17.488000',49.80085143325183,'2026-10-05 20:31:17.010462',5,2,10),(388,'2026-10-07 02:31:17.488000','2026-10-07 02:31:17.488000',121.41120008059868,'2026-10-05 20:31:17.010462',6,6,11),(389,'2026-10-07 02:31:17.489000','2026-10-07 02:31:17.489000',10.444348690576614,'2026-10-05 20:31:17.010462',6,4,12),(390,'2026-10-07 02:31:17.490000','2026-10-07 02:31:17.490000',19.570741665599652,'2026-10-05 20:31:17.010462',7,1,13),(391,'2026-10-07 02:31:17.491000','2026-10-07 02:31:17.491000',985.8087675270161,'2026-10-05 20:31:17.010462',7,6,14),(392,'2026-10-07 02:31:17.491000','2026-10-07 02:31:17.491000',18.177739476230645,'2026-10-05 20:31:17.010462',8,1,15),(393,'2026-10-07 02:31:17.492000','2026-10-07 02:31:17.492000',693.7999976774739,'2026-10-05 20:31:17.010462',8,3,16),(394,'2026-10-07 02:31:17.493000','2026-10-07 02:31:17.493000',20.286694608006012,'2026-10-05 20:31:17.010462',9,1,17),(395,'2026-10-07 02:31:17.494000','2026-10-07 02:31:17.494000',36.231713540999785,'2026-10-05 20:31:17.010462',10,1,18),(396,'2026-10-07 02:31:17.495000','2026-10-07 02:31:17.495000',17.710957904922584,'2026-10-05 20:31:17.010462',11,4,19),(397,'2026-10-07 02:31:17.496000','2026-10-07 02:31:17.496000',970.2246988233491,'2026-10-05 20:31:17.010462',11,6,20),(398,'2026-10-07 02:31:17.496000','2026-10-07 02:31:17.496000',1.429258334400348,'2026-10-05 20:31:17.010462',12,5,21),(399,'2026-10-07 02:31:17.497000','2026-10-07 02:31:17.497000',20.980085143325184,'2026-10-05 20:31:17.010462',12,1,22),(400,'2026-10-07 02:31:17.498000','2026-10-07 02:31:17.498000',3.2657571075469383,'2026-10-05 21:31:17.010462',1,1,1),(401,'2026-10-07 02:31:17.499000','2026-10-07 02:31:17.499000',50.303753840657464,'2026-10-05 21:31:17.010462',1,2,2),(402,'2026-10-07 02:31:17.499000','2026-10-07 02:31:17.499000',530.3096730879365,'2026-10-05 21:31:17.010462',2,3,3),(403,'2026-10-07 02:31:17.500000','2026-10-07 02:31:17.500000',-186.68515384958908,'2026-10-05 21:31:17.010462',3,1,4),(404,'2026-10-07 02:31:17.501000','2026-10-07 02:31:17.501000',1015.8709919505678,'2026-10-05 21:31:17.010462',3,6,5),(405,'2026-10-07 02:31:17.501000','2026-10-07 02:31:17.501000',43.85313109812439,'2026-10-05 21:31:17.010462',2,4,6),(406,'2026-10-07 02:31:17.502000','2026-10-07 02:31:17.502000',1.9479160444197463,'2026-10-05 21:31:17.010462',2,5,7),(407,'2026-10-07 02:31:17.503000','2026-10-07 02:31:17.503000',22.662969230082183,'2026-10-05 21:31:17.010462',5,1,9),(408,'2026-10-07 02:31:17.504000','2026-10-07 02:31:17.504000',49.4275975847066,'2026-10-05 21:31:17.010462',5,2,10),(409,'2026-10-07 02:31:17.504000','2026-10-07 02:31:17.504000',122.39249329213982,'2026-10-05 21:31:17.010462',6,6,11),(410,'2026-10-07 02:31:17.506000','2026-10-07 02:31:17.506000',11.256766772013007,'2026-10-05 21:31:17.010462',6,4,12),(411,'2026-10-07 02:31:17.506000','2026-10-07 02:31:17.506000',19.341737124025034,'2026-10-05 21:31:17.010462',7,1,13),(412,'2026-10-07 02:31:17.507000','2026-10-07 02:31:17.507000',983.0309673087936,'2026-10-05 21:31:17.010462',7,6,14),(413,'2026-10-07 02:31:17.508000','2026-10-07 02:31:17.508000',18.502706708805203,'2026-10-05 21:31:17.010462',8,1,15),(414,'2026-10-07 02:31:17.508000','2026-10-07 02:31:17.508000',682.3080879011505,'2026-10-05 21:31:17.010462',8,3,16),(415,'2026-10-07 02:31:17.509000','2026-10-07 02:31:17.509000',19.877471757850728,'2026-10-05 21:31:17.010462',9,1,17),(416,'2026-10-07 02:31:17.510000','2026-10-07 02:31:17.510000',36.088585702515644,'2026-10-05 21:31:17.010462',10,1,18),(417,'2026-10-07 02:31:17.511000','2026-10-07 02:31:17.511000',19.01082683522081,'2026-10-05 21:31:17.010462',11,4,19),(418,'2026-10-07 02:31:17.511000','2026-10-07 02:31:17.511000',970.6494742244155,'2026-10-05 21:31:17.010462',11,6,20),(419,'2026-10-07 02:31:17.512000','2026-10-07 02:31:17.512000',1.6582628759749665,'2026-10-05 21:31:17.010462',12,5,21),(420,'2026-10-07 02:31:17.513000','2026-10-07 02:31:17.513000',20.94275975847066,'2026-10-05 21:31:17.010462',12,1,22),(421,'2026-10-07 02:31:17.513000','2026-10-07 02:31:17.513000',3.0312334062730244,'2026-10-05 22:31:17.010462',1,1,1),(422,'2026-10-07 02:31:17.514000','2026-10-07 02:31:17.514000',51.83679126470624,'2026-10-05 22:31:17.010462',1,2,2),(423,'2026-10-07 02:31:17.515000','2026-10-07 02:31:17.515000',505.31000431915186,'2026-10-05 22:31:17.010462',2,3,3),(424,'2026-10-07 02:31:17.516000','2026-10-07 02:31:17.516000',-185.7270054595586,'2026-10-05 22:31:17.010462',3,1,4),(425,'2026-10-07 02:31:17.517000','2026-10-07 02:31:17.517000',1017.0198578018709,'2026-10-05 22:31:17.010462',3,6,5),(426,'2026-10-07 02:31:17.518000','2026-10-07 02:31:17.518000',44.994345855010046,'2026-10-05 22:31:17.010462',2,4,6),(427,'2026-10-07 02:31:17.518000','2026-10-07 02:31:17.518000',1.9924858845558964,'2026-10-05 22:31:17.010462',2,5,7),(428,'2026-10-07 02:31:17.519000','2026-10-07 02:31:17.519000',22.85459890808828,'2026-10-05 22:31:17.010462',5,1,9),(429,'2026-10-07 02:31:17.520000','2026-10-07 02:31:17.520000',48.87782939255125,'2026-10-05 22:31:17.010462',5,2,10),(430,'2026-10-07 02:31:17.521000','2026-10-07 02:31:17.521000',123.34988150155905,'2026-10-05 22:31:17.010462',6,6,11),(431,'2026-10-07 02:31:17.521000','2026-10-07 02:31:17.521000',12.403556729416572,'2026-10-05 22:31:17.010462',6,4,12),(432,'2026-10-07 02:31:17.522000','2026-10-07 02:31:17.522000',19.216657816678946,'2026-10-05 22:31:17.010462',7,1,13),(433,'2026-10-07 02:31:17.523000','2026-10-07 02:31:17.523000',980.5310004319152,'2026-10-05 22:31:17.010462',7,6,14),(434,'2026-10-07 02:31:17.524000','2026-10-07 02:31:17.524000',18.96142269176663,'2026-10-05 22:31:17.010462',8,1,15),(435,'2026-10-07 02:31:17.525000','2026-10-07 02:31:17.525000',665.6986598718789,'2026-10-05 22:31:17.010462',8,3,16),(436,'2026-10-07 02:31:17.526000','2026-10-07 02:31:17.526000',19.60436059184287,'2026-10-05 22:31:17.010462',9,1,17),(437,'2026-10-07 02:31:17.527000','2026-10-07 02:31:17.527000',36.01041113542434,'2026-10-05 22:31:17.010462',10,1,18),(438,'2026-10-07 02:31:17.528000','2026-10-07 02:31:17.528000',20.845690767066515,'2026-10-05 22:31:17.010462',11,4,19),(439,'2026-10-07 02:31:17.529000','2026-10-07 02:31:17.529000',971.2842422758641,'2026-10-05 22:31:17.010462',11,6,20),(440,'2026-10-07 02:31:17.529000','2026-10-07 02:31:17.529000',1.7833421833210537,'2026-10-05 22:31:17.010462',12,5,21),(441,'2026-10-07 02:31:17.530000','2026-10-07 02:31:17.530000',20.887782939255125,'2026-10-05 22:31:17.010462',12,1,22),(442,'2026-10-07 02:31:17.531000','2026-10-07 02:31:17.531000',3.0285956549002626,'2026-10-05 23:31:17.010462',1,1,1),(443,'2026-10-07 02:31:17.532000','2026-10-07 02:31:17.532000',52.75911848676069,'2026-10-05 23:31:17.010462',1,2,2),(444,'2026-10-07 02:31:17.533000','2026-10-07 02:31:17.533000',484.0853268666019,'2026-10-05 23:31:17.010462',2,3,3),(445,'2026-10-07 02:31:17.534000','2026-10-07 02:31:17.534000',-185.15055094577457,'2026-10-05 23:31:17.010462',3,1,4),(446,'2026-10-07 02:31:17.534000','2026-10-07 02:31:17.534000',1018.128558562806,'2026-10-05 23:31:17.010462',3,6,5),(447,'2026-10-07 02:31:17.536000','2026-10-07 02:31:17.536000',44.14562843198418,'2026-10-05 23:31:17.010462',2,4,6),(448,'2026-10-07 02:31:17.537000','2026-10-07 02:31:17.537000',1.8430275412412969,'2026-10-05 23:31:17.010462',2,5,7),(449,'2026-10-07 02:31:17.537000','2026-10-07 02:31:17.537000',22.969889810845086,'2026-10-05 23:31:17.010462',5,1,9),(450,'2026-10-07 02:31:17.538000','2026-10-07 02:31:17.538000',48.173464379713174,'2026-10-05 23:31:17.010462',5,2,10),(451,'2026-10-07 02:31:17.539000','2026-10-07 02:31:17.539000',124.27379880233829,'2026-10-05 23:31:17.010462',6,6,11),(452,'2026-10-07 02:31:17.540000','2026-10-07 02:31:17.540000',13.782279231321045,'2026-10-05 23:31:17.010462',6,4,12),(453,'2026-10-07 02:31:17.541000','2026-10-07 02:31:17.541000',19.215251015946805,'2026-10-05 23:31:17.010462',7,1,13),(454,'2026-10-07 02:31:17.542000','2026-10-07 02:31:17.542000',978.4085326866602,'2026-10-05 23:31:17.010462',7,6,14),(455,'2026-10-07 02:31:17.542000','2026-10-07 02:31:17.542000',19.51291169252842,'2026-10-05 23:31:17.010462',8,1,15),(456,'2026-10-07 02:31:17.544000','2026-10-07 02:31:17.544000',645.0044073780617,'2026-10-05 23:31:17.010462',8,3,16),(457,'2026-10-07 02:31:17.545000','2026-10-07 02:31:17.545000',19.500477088154724,'2026-10-05 23:31:17.010462',9,1,17),(458,'2026-10-07 02:31:17.546000','2026-10-07 02:31:17.546000',36.009531884966755,'2026-10-05 23:31:17.010462',10,1,18),(459,'2026-10-07 02:31:17.546000','2026-10-07 02:31:17.546000',23.05164677011367,'2026-10-05 23:31:17.010462',11,4,19),(460,'2026-10-07 02:31:17.547000','2026-10-07 02:31:17.547000',972.1147474557381,'2026-10-05 23:31:17.010462',11,6,20),(461,'2026-10-07 02:31:17.548000','2026-10-07 02:31:17.548000',1.7847489840531932,'2026-10-05 23:31:17.010462',12,5,21),(462,'2026-10-07 02:31:17.550000','2026-10-07 02:31:17.550000',20.817346437971317,'2026-10-05 23:31:17.010462',12,1,22),(463,'2026-10-07 02:31:17.552000','2026-10-07 02:31:17.552000',3.2582602963715193,'2026-10-06 00:31:17.010462',1,1,1),(464,'2026-10-07 02:31:17.552000','2026-10-07 02:31:17.552000',52.98834676299684,'2026-10-06 00:31:17.010462',1,2,2),(465,'2026-10-07 02:31:17.553000','2026-10-07 02:31:17.553000',467.48180164197703,'2026-10-06 00:31:17.010462',2,3,3),(466,'2026-10-07 02:31:17.554000','2026-10-07 02:31:17.554000',-185.00728327312697,'2026-10-06 00:31:17.010462',3,1,4),(467,'2026-10-07 02:31:17.555000','2026-10-07 02:31:17.555000',1019.1860164618575,'2026-10-06 00:31:17.010462',3,6,5),(468,'2026-10-07 02:31:17.556000','2026-10-07 02:31:17.556000',41.47596338653877,'2026-10-06 00:31:17.010462',2,4,6),(469,'2026-10-07 02:31:17.557000','2026-10-07 02:31:17.557000',1.5361336294613128,'2026-10-06 00:31:17.010462',2,5,7),(470,'2026-10-07 02:31:17.558000','2026-10-07 02:31:17.558000',22.998543345374603,'2026-10-06 00:31:17.010462',5,1,9),(471,'2026-10-07 02:31:17.558000','2026-10-07 02:31:17.558000',47.34258335650188,'2026-10-06 00:31:17.010462',5,2,10),(472,'2026-10-07 02:31:17.559000','2026-10-07 02:31:17.559000',125.15501371821465,'2026-10-06 00:31:17.010462',6,6,11),(473,'2026-10-07 02:31:17.560000','2026-10-07 02:31:17.560000',15.269777102813249,'2026-10-06 00:31:17.010462',6,4,12),(474,'2026-10-07 02:31:17.561000','2026-10-07 02:31:17.561000',19.337738824731478,'2026-10-06 00:31:17.010462',7,1,13),(475,'2026-10-07 02:31:17.561000','2026-10-07 02:31:17.561000',976.7481801641977,'2026-10-06 00:31:17.010462',7,6,14),(476,'2026-10-07 02:31:17.562000','2026-10-07 02:31:17.562000',20.1079108411253,'2026-10-06 00:31:17.010462',8,1,15),(477,'2026-10-07 02:31:17.564000','2026-10-07 02:31:17.564000',621.5119988087815,'2026-10-06 00:31:17.010462',8,3,16),(478,'2026-10-07 02:31:17.564000','2026-10-07 02:31:17.564000',19.578417596803334,'2026-10-06 00:31:17.010462',9,1,17),(479,'2026-10-07 02:31:17.565000','2026-10-07 02:31:17.565000',36.086086765457175,'2026-10-06 00:31:17.010462',10,1,18),(480,'2026-10-07 02:31:17.566000','2026-10-07 02:31:17.566000',25.4316433645012,'2026-10-06 00:31:17.010462',11,4,19),(481,'2026-10-07 02:31:17.566000','2026-10-07 02:31:17.566000',973.1223384081603,'2026-10-06 00:31:17.010462',11,6,20),(482,'2026-10-07 02:31:17.567000','2026-10-07 02:31:17.567000',1.662261175268523,'2026-10-06 00:31:17.010462',12,5,21),(483,'2026-10-07 02:31:17.568000','2026-10-07 02:31:17.568000',20.73425833565019,'2026-10-06 00:31:17.010462',12,1,22),(484,'2026-10-07 02:31:17.569000','2026-10-07 02:31:17.569000',3.6839683336659452,'2026-10-06 01:31:17.010462',1,1,1),(485,'2026-10-07 02:31:17.570000','2026-10-07 02:31:17.570000',52.50399981419791,'2026-10-06 01:31:17.010462',1,2,2),(486,'2026-10-07 02:31:17.571000','2026-10-07 02:31:17.571000',456.16135880052923,'2026-10-06 01:31:17.010462',2,3,3),(487,'2026-10-07 02:31:17.572000','2026-10-07 02:31:17.572000',-185.3100001161263,'2026-10-06 01:31:17.010462',3,1,4),(488,'2026-10-07 02:31:17.573000','2026-10-07 02:31:17.573000',1020.1816657292475,'2026-10-06 01:31:17.010462',3,6,5),(489,'2026-10-07 02:31:17.574000','2026-10-07 02:31:17.574000',37.51689650071754,'2026-10-06 01:31:17.010462',2,4,6),(490,'2026-10-07 02:31:17.574000','2026-10-07 02:31:17.574000',1.2530575178809604,'2026-10-06 01:31:17.010462',2,5,7),(491,'2026-10-07 02:31:17.575000','2026-10-07 02:31:17.575000',22.93799997677474,'2026-10-06 01:31:17.010462',5,1,9),(492,'2026-10-07 02:31:17.576000','2026-10-07 02:31:17.576000',46.41831092731613,'2026-10-06 01:31:17.010462',5,2,10),(493,'2026-10-07 02:31:17.576000','2026-10-07 02:31:17.576000',125.98472144103957,'2026-10-06 01:31:17.010462',6,6,11),(494,'2026-10-07 02:31:17.577000','2026-10-07 02:31:17.577000',16.73317658917513,'2026-10-06 01:31:17.010462',6,4,12),(495,'2026-10-07 02:31:17.578000','2026-10-07 02:31:17.578000',19.564783111288506,'2026-10-06 01:31:17.010462',7,1,13),(496,'2026-10-07 02:31:17.579000','2026-10-07 02:31:17.579000',975.616135880053,'2026-10-06 01:31:17.010462',7,6,14),(497,'2026-10-07 02:31:17.579000','2026-10-07 02:31:17.579000',20.69327063567005,'2026-10-06 01:31:17.010462',8,1,15),(498,'2026-10-07 02:31:17.581000','2026-10-07 02:31:17.581000',596.6820783452443,'2026-10-06 01:31:17.010462',8,3,16),(499,'2026-10-07 02:31:17.581000','2026-10-07 02:31:17.581000',19.828731474591375,'2026-10-06 01:31:17.010462',9,1,17),(500,'2026-10-07 02:31:17.582000','2026-10-07 02:31:17.582000',36.22798944455531,'2026-10-06 01:31:17.010462',10,1,18),(501,'2026-10-07 02:31:17.583000','2026-10-07 02:31:17.583000',27.773082542680207,'2026-10-06 01:31:17.010462',11,4,19),(502,'2026-10-07 02:31:17.584000','2026-10-07 02:31:17.584000',974.2843868125766,'2026-10-06 01:31:17.010462',11,6,20),(503,'2026-10-07 02:31:17.585000','2026-10-07 02:31:17.585000',1.4352168887114958,'2026-10-06 01:31:17.010462',12,5,21),(504,'2026-10-07 02:31:17.586000','2026-10-07 02:31:17.586000',20.641831092731614,'2026-10-06 01:31:17.010462',12,1,22),(505,'2026-10-07 02:31:17.586000','2026-10-07 02:31:17.586000',4.238509828165528,'2026-10-06 02:31:17.010462',1,1,1),(506,'2026-10-07 02:31:17.587000','2026-10-07 02:31:17.587000',51.34934291079322,'2026-10-06 02:31:17.010462',1,2,2),(507,'2026-10-07 02:31:17.588000','2026-10-07 02:31:17.588000',450.5753086746239,'2026-10-06 02:31:17.010462',2,3,3),(508,'2026-10-07 02:31:17.589000','2026-10-07 02:31:17.589000',-186.03166068075424,'2026-10-06 02:31:17.010462',3,1,4),(509,'2026-10-07 02:31:17.590000','2026-10-07 02:31:17.590000',1021.1055581666138,'2026-10-06 02:31:17.010462',3,6,5),(510,'2026-10-07 02:31:17.591000','2026-10-07 02:31:17.591000',33.05670093544665,'2026-10-06 02:31:17.010462',2,4,6),(511,'2026-10-07 02:31:17.592000','2026-10-07 02:31:17.592000',1.629258334400348,'2026-10-06 02:31:17.010462',2,5,7),(512,'2026-10-07 02:31:17.593000','2026-10-07 02:31:17.593000',22.79366786384915,'2026-10-06 02:31:17.010462',5,1,9),(513,'2026-10-07 02:31:17.593000','2026-10-07 02:31:17.593000',45.437494917197235,'2026-10-06 02:31:17.010462',5,2,10),(514,'2026-10-07 02:31:17.594000','2026-10-07 02:31:17.594000',126.75463180551151,'2026-10-06 02:31:17.010462',6,6,11),(515,'2026-10-07 02:31:17.595000','2026-10-07 02:31:17.595000',18.041756572661278,'2026-10-06 02:31:17.010462',6,4,12),(516,'2026-10-07 02:31:17.595000','2026-10-07 02:31:17.595000',19.860538575021614,'2026-10-06 02:31:17.010462',7,1,13),(517,'2026-10-07 02:31:17.596000','2026-10-07 02:31:17.596000',975.0575308674624,'2026-10-06 02:31:17.010462',7,6,14),(518,'2026-10-07 02:31:17.597000','2026-10-07 02:31:17.597000',21.21670262906451,'2026-10-06 02:31:17.010462',8,1,15),(519,'2026-10-07 02:31:17.598000','2026-10-07 02:31:17.598000',572.0584501801075,'2026-10-06 02:31:17.010462',8,3,16),(520,'2026-10-07 02:31:17.600000','2026-10-07 02:31:17.600000',20.221067018824975,'2026-10-06 02:31:17.010462',9,1,17),(521,'2026-10-07 02:31:17.601000','2026-10-07 02:31:17.601000',36.412836609388506,'2026-10-06 02:31:17.010462',10,1,18),(522,'2026-10-07 02:31:17.601000','2026-10-07 02:31:17.601000',29.86681051625804,'2026-10-06 02:31:17.010462',11,4,19),(523,'2026-10-07 02:31:17.602000','2026-10-07 02:31:17.602000',975.5747955670515,'2026-10-06 02:31:17.010462',11,6,20),(524,'2026-10-07 02:31:17.603000','2026-10-07 02:31:17.603000',1.1394614249783852,'2026-10-06 02:31:17.010462',12,5,21),(525,'2026-10-07 02:31:17.604000','2026-10-07 02:31:17.604000',20.543749491719723,'2026-10-06 02:31:17.010462',12,1,22),(526,'2026-10-07 02:31:17.605000','2026-10-07 02:31:17.605000',4.834334871150369,'2026-10-06 03:31:17.010462',1,1,1),(527,'2026-10-07 02:31:17.605000','2026-10-07 02:31:17.605000',49.627518115105595,'2026-10-06 03:31:17.010462',1,2,2),(528,'2026-10-07 02:31:17.606000','2026-10-07 02:31:17.606000',450.94634945498035,'2026-10-06 03:31:17.010462',2,3,3),(529,'2026-10-07 02:31:17.606000','2026-10-07 02:31:17.606000',-187.107801178059,'2026-10-06 03:31:17.010462',3,1,4),(530,'2026-10-07 02:31:17.607000','2026-10-07 02:31:17.607000',1021.9484625461206,'2026-10-06 03:31:17.010462',3,6,5),(531,'2026-10-07 02:31:17.608000','2026-10-07 02:31:17.608000',28.98342747591898,'2026-10-06 03:31:17.010462',2,4,6),(532,'2026-10-07 02:31:17.608000','2026-10-07 02:31:17.608000',1.9003617397507429,'2026-10-06 03:31:17.010462',2,5,7),(533,'2026-10-07 02:31:17.609000','2026-10-07 02:31:17.609000',22.5784397643882,'2026-10-06 03:31:17.010462',5,1,9),(534,'2026-10-07 02:31:17.610000','2026-10-07 02:31:17.610000',44.43923736532473,'2026-10-06 03:31:17.010462',5,2,10),(535,'2026-10-07 02:31:17.611000','2026-10-07 02:31:17.611000',127.4570521217672,'2026-10-06 03:31:17.010462',6,6,11),(536,'2026-10-07 02:31:17.612000','2026-10-07 02:31:17.612000',19.078625500626785,'2026-10-06 03:31:17.010462',6,4,12),(537,'2026-10-07 02:31:17.613000','2026-10-07 02:31:17.613000',20.178311931280195,'2026-10-06 03:31:17.010462',7,1,13),(538,'2026-10-07 02:31:17.614000','2026-10-07 02:31:17.614000',975.094634945498,'2026-10-06 03:31:17.010462',7,6,14),(539,'2026-10-07 02:31:17.615000','2026-10-07 02:31:17.615000',21.631450200250715,'2026-10-06 03:31:17.010462',8,1,15),(540,'2026-10-07 02:31:17.615000','2026-10-07 02:31:17.615000',549.1720922500741,'2026-10-06 03:31:17.010462',8,3,16),(541,'2026-10-07 02:31:17.616000','2026-10-07 02:31:17.616000',20.70785175010326,'2026-10-06 03:31:17.010462',9,1,17),(542,'2026-10-07 02:31:17.617000','2026-10-07 02:31:17.617000',36.611444957050125,'2026-10-06 03:31:17.010462',10,1,18),(543,'2026-10-07 02:31:17.617000','2026-10-07 02:31:17.617000',31.52580080100286,'2026-10-06 03:31:17.010462',11,4,19),(544,'2026-10-07 02:31:17.618000','2026-10-07 02:31:17.618000',976.9645848729157,'2026-10-06 03:31:17.010462',11,6,20),(545,'2026-10-07 02:31:17.619000','2026-10-07 02:31:17.619000',1.1783119312801968,'2026-10-06 03:31:17.010462',12,5,21),(546,'2026-10-07 02:31:17.620000','2026-10-07 02:31:17.620000',20.443923736532472,'2026-10-06 03:31:17.010462',12,1,22),(547,'2026-10-07 02:31:17.621000','2026-10-07 02:31:17.621000',5.377375789337643,'2026-10-06 04:31:17.010462',1,1,1),(548,'2026-10-07 02:31:17.621000','2026-10-07 02:31:17.621000',47.49233090810702,'2026-10-06 04:31:17.010462',1,2,2),(549,'2026-10-07 02:31:17.622000','2026-10-07 02:31:17.622000',457.2596889165726,'2026-10-06 04:31:17.010462',2,3,3),(550,'2026-10-07 02:31:17.623000','2026-10-07 02:31:17.623000',-188.4422931824331,'2026-10-06 04:31:17.010462',3,1,4),(551,'2026-10-07 02:31:17.624000','2026-10-07 02:31:17.624000',1022.7019568458351,'2026-10-06 04:31:17.010462',3,6,5),(552,'2026-10-07 02:31:17.625000','2026-10-07 02:31:17.625000',26.108088473746392,'2026-10-06 04:31:17.010462',2,4,6),(553,'2026-10-07 02:31:17.626000','2026-10-07 02:31:17.626000',1.9999921652405628,'2026-10-06 04:31:17.010462',2,5,7),(554,'2026-10-07 02:31:17.627000','2026-10-07 02:31:17.627000',22.311541363513378,'2026-10-06 04:31:17.010462',5,1,9),(555,'2026-10-07 02:31:17.627000','2026-10-07 02:31:17.627000',43.4633356501079,'2026-10-06 04:31:17.010462',5,2,10),(556,'2026-10-07 02:31:17.628000','2026-10-07 02:31:17.628000',128.0849640381959,'2026-10-06 04:31:17.010462',6,6,11),(557,'2026-10-07 02:31:17.629000','2026-10-07 02:31:17.629000',19.751162959792648,'2026-10-06 04:31:17.010462',6,4,12),(558,'2026-10-07 02:31:17.630000','2026-10-07 02:31:17.630000',20.46793375431341,'2026-10-06 04:31:17.010462',7,1,13),(559,'2026-10-07 02:31:17.630000','2026-10-07 02:31:17.630000',975.7259688916572,'2026-10-06 04:31:17.010462',7,6,14),(560,'2026-10-07 02:31:17.631000','2026-10-07 02:31:17.631000',21.90046518391706,'2026-10-06 04:31:17.010462',8,1,15),(561,'2026-10-07 02:31:17.632000','2026-10-07 02:31:17.632000',529.4459674429609,'2026-10-06 04:31:17.010462',8,3,16),(562,'2026-10-07 02:31:17.632000','2026-10-07 02:31:17.632000',21.230060793056797,'2026-10-06 04:31:17.010462',9,1,17),(563,'2026-10-07 02:31:17.633000','2026-10-07 02:31:17.633000',36.79245859644588,'2026-10-06 04:31:17.010462',10,1,18),(564,'2026-10-07 02:31:17.634000','2026-10-07 02:31:17.634000',32.60186073566824,'2026-10-06 04:31:17.010462',11,4,19),(565,'2026-10-07 02:31:17.635000','2026-10-07 02:31:17.635000',978.4225430585675,'2026-10-06 04:31:17.010462',11,6,20),(566,'2026-10-07 02:31:17.636000','2026-10-07 02:31:17.636000',1.4679337543134094,'2026-10-06 04:31:17.010462',12,5,21),(567,'2026-10-07 02:31:17.637000','2026-10-07 02:31:17.637000',20.34633356501079,'2026-10-06 04:31:17.010462',12,1,22),(568,'2026-10-07 02:31:17.637000','2026-10-07 02:31:17.637000',5.781898362132421,'2026-10-06 05:31:17.010462',1,1,1),(569,'2026-10-07 02:31:17.638000','2026-10-07 02:31:17.638000',45.134511203874794,'2026-10-06 05:31:17.010462',1,2,2),(570,'2026-10-07 02:31:17.639000','2026-10-07 02:31:17.639000',469.26363413796173,'2026-10-06 05:31:17.010462',2,3,3),(571,'2026-10-07 02:31:17.640000','2026-10-07 02:31:17.640000',-189.91593049757824,'2026-10-06 05:31:17.010462',3,1,4),(572,'2026-10-07 02:31:17.641000','2026-10-07 02:31:17.641000',1023.3585123997865,'2026-10-06 05:31:17.010462',3,6,5),(573,'2026-10-07 02:31:17.641000','2026-10-07 02:31:17.641000',25.003180587698154,'2026-10-06 05:31:17.010462',2,4,6),(574,'2026-10-07 02:31:17.642000','2026-10-07 02:31:17.642000',1.903756607977336,'2026-10-06 05:31:17.010462',2,5,7),(575,'2026-10-07 02:31:17.643000','2026-10-07 02:31:17.643000',22.01681390048435,'2026-10-06 05:31:17.010462',5,1,9),(576,'2026-10-07 02:31:17.644000','2026-10-07 02:31:17.644000',42.548695893296504,'2026-10-06 05:31:17.010462',5,2,10),(577,'2026-10-07 02:31:17.645000','2026-10-07 02:31:17.645000',128.63209366648874,'2026-10-06 05:31:17.010462',6,6,11),(578,'2026-10-07 02:31:17.645000','2026-10-07 02:31:17.645000',19.999293181917075,'2026-10-06 05:31:17.010462',6,4,12),(579,'2026-10-07 02:31:17.646000','2026-10-07 02:31:17.646000',20.683679126470626,'2026-10-06 05:31:17.010462',7,1,13),(580,'2026-10-07 02:31:17.647000','2026-10-07 02:31:17.647000',976.9263634137961,'2026-10-06 05:31:17.010462',7,6,14),(581,'2026-10-07 02:31:17.648000','2026-10-07 02:31:17.648000',21.99971727276683,'2026-10-06 05:31:17.010462',8,1,15),(582,'2026-10-07 02:31:17.648000','2026-10-07 02:31:17.648000',514.1065506573408,'2026-10-06 05:31:17.010462',8,3,16),(583,'2026-10-07 02:31:17.649000','2026-10-07 02:31:17.649000',21.724373912483905,'2026-10-06 05:31:17.010462',9,1,17),(584,'2026-10-07 02:31:17.650000','2026-10-07 02:31:17.650000',36.92729945404414,'2026-10-06 05:31:17.010462',10,1,18),(585,'2026-10-07 02:31:17.651000','2026-10-07 02:31:17.651000',32.99886909106732,'2026-10-06 05:31:17.010462',11,4,19),(586,'2026-10-07 02:31:17.651000','2026-10-07 02:31:17.651000',979.9159275263285,'2026-10-06 05:31:17.010462',11,6,20),(587,'2026-10-07 02:31:17.652000','2026-10-07 02:31:17.652000',1.6836791264706243,'2026-10-06 05:31:17.010462',12,5,21),(588,'2026-10-07 02:31:17.653000','2026-10-07 02:31:17.653000',20.25486958932965,'2026-10-06 05:31:17.010462',12,1,22),(589,'2026-10-07 02:31:17.653000','2026-10-07 02:31:17.653000',5.984037369935073,'2026-10-06 06:31:17.010462',1,1,1),(590,'2026-10-07 02:31:17.654000','2026-10-07 02:31:17.654000',42.76467601440859,'2026-10-06 06:31:17.010462',1,2,2),(591,'2026-10-07 02:31:17.655000','2026-10-07 02:31:17.655000',486.47962570381077,'2026-10-06 06:31:17.010462',2,3,3),(592,'2026-10-07 02:31:17.656000','2026-10-07 02:31:17.656000',-191.39707749099463,'2026-10-06 06:31:17.010462',3,1,4),(593,'2026-10-07 02:31:17.657000','2026-10-07 02:31:17.657000',1023.9115691219082,'2026-10-06 06:31:17.010462',3,6,5),(594,'2026-10-07 02:31:17.658000','2026-10-07 02:31:17.658000',25.88869738115323,'2026-10-06 06:31:17.010462',2,4,6),(595,'2026-10-07 02:31:17.658000','2026-10-07 02:31:17.658000',1.6352168887114957,'2026-10-06 06:31:17.010462',2,5,7),(596,'2026-10-07 02:31:17.659000','2026-10-07 02:31:17.659000',21.720584501801074,'2026-10-06 06:31:17.010462',5,1,9),(597,'2026-10-07 02:31:17.660000','2026-10-07 02:31:17.660000',41.73178189568194,'2026-10-06 06:31:17.010462',5,2,10),(598,'2026-10-07 02:31:17.660000','2026-10-07 02:31:17.660000',129.09297426825682,'2026-10-06 06:31:17.010462',6,6,11),(599,'2026-10-07 02:31:17.661000','2026-10-07 02:31:17.661000',19.80085143325183,'2026-10-06 06:31:17.010462',6,4,12),(600,'2026-10-07 02:31:17.662000','2026-10-07 02:31:17.662000',20.791486597298707,'2026-10-06 06:31:17.010462',7,1,13),(601,'2026-10-07 02:31:17.663000','2026-10-07 02:31:17.663000',978.6479625703811,'2026-10-06 06:31:17.010462',7,6,14),(602,'2026-10-07 02:31:17.664000','2026-10-07 02:31:17.664000',21.92034057330073,'2026-10-06 06:31:17.010462',8,1,15),(603,'2026-10-07 02:31:17.665000','2026-10-07 02:31:17.665000',504.10757253368615,'2026-10-06 06:31:17.010462',8,3,16),(604,'2026-10-07 02:31:17.666000','2026-10-07 02:31:17.666000',22.130853381514957,'2026-10-06 06:31:17.010462',9,1,17),(605,'2026-10-07 02:31:17.667000','2026-10-07 02:31:17.667000',36.99467912331169,'2026-10-06 06:31:17.010462',10,1,18),(606,'2026-10-07 02:31:17.668000','2026-10-07 02:31:17.668000',32.68136229320293,'2026-10-06 06:31:17.010462',11,4,19),(607,'2026-10-07 02:31:17.668000','2026-10-07 02:31:17.668000',981.4112000805986,'2026-10-06 06:31:17.010462',11,6,20),(608,'2026-10-07 02:31:17.669000','2026-10-07 02:31:17.669000',1.7914865972987055,'2026-10-06 06:31:17.010462',12,5,21),(609,'2026-10-07 02:31:17.670000','2026-10-07 02:31:17.670000',20.173178189568194,'2026-10-06 06:31:17.010462',12,1,22),(610,'2026-10-07 02:31:17.671000','2026-10-07 02:31:17.671000',5.95187950804723,'2026-10-06 07:31:17.010462',1,1,1),(611,'2026-10-07 02:31:17.672000','2026-10-07 02:31:17.672000',40.5945156592189,'2026-10-06 07:31:17.010462',1,2,2),(612,'2026-10-07 02:31:17.672000','2026-10-07 02:31:17.672000',508.2213163585921,'2026-10-06 07:31:17.010462',2,3,3),(613,'2026-10-07 02:31:17.674000','2026-10-07 02:31:17.674000',-192.75342771298818,'2026-10-06 07:31:17.010462',3,1,4),(614,'2026-10-07 02:31:17.674000','2026-10-07 02:31:17.674000',1024.355601052249,'2026-10-06 07:31:17.010462',3,6,5),(615,'2026-10-07 02:31:17.675000','2026-10-07 02:31:17.675000',28.588327328703983,'2026-10-06 07:31:17.010462',2,4,6),(616,'2026-10-07 02:31:17.676000','2026-10-07 02:31:17.676000',1.2601208963694475,'2026-10-06 07:31:17.010462',2,5,7),(617,'2026-10-07 02:31:17.676000','2026-10-07 02:31:17.676000',21.449314457402362,'2026-10-06 07:31:17.010462',5,1,9),(618,'2026-10-07 02:31:17.677000','2026-10-07 02:31:17.677000',41.04516144042792,'2026-10-06 07:31:17.010462',5,2,10),(619,'2026-10-07 02:31:17.678000','2026-10-07 02:31:17.678000',129.46300087687413,'2026-10-06 07:31:17.010462',6,6,11),(620,'2026-10-07 02:31:17.679000','2026-10-07 02:31:17.679000',19.173563924195797,'2026-10-06 07:31:17.010462',6,4,12),(621,'2026-10-07 02:31:17.680000','2026-10-07 02:31:17.680000',20.77433573762519,'2026-10-06 07:31:17.010462',7,1,13),(622,'2026-10-07 02:31:17.680000','2026-10-07 02:31:17.680000',980.8221316358593,'2026-10-06 07:31:17.010462',7,6,14),(623,'2026-10-07 02:31:17.681000','2026-10-07 02:31:17.681000',21.66942556967832,'2026-10-06 07:31:17.010462',8,1,15),(624,'2026-10-07 02:31:17.682000','2026-10-07 02:31:17.682000',500.0707211024622,'2026-10-06 07:31:17.010462',8,3,16),(625,'2026-10-07 02:31:17.683000','2026-10-07 02:31:17.683000',22.40021170516877,'2026-10-06 07:31:17.010462',9,1,17),(626,'2026-10-07 02:31:17.684000','2026-10-07 02:31:17.684000',36.98395983601574,'2026-10-06 07:31:17.010462',10,1,18),(627,'2026-10-07 02:31:17.685000','2026-10-07 02:31:17.685000',31.677702278713276,'2026-10-06 07:31:17.010462',11,4,19),(628,'2026-10-07 02:31:17.686000','2026-10-07 02:31:17.686000',982.8747801234255,'2026-10-06 07:31:17.010462',11,6,20),(629,'2026-10-07 02:31:17.687000','2026-10-07 02:31:17.687000',1.7743357376251891,'2026-10-06 07:31:17.010462',12,5,21),(630,'2026-10-07 02:31:17.688000','2026-10-07 02:31:17.688000',20.10451614404279,'2026-10-06 07:31:17.010462',12,1,22),(631,'2026-10-07 02:31:17.688000','2026-10-07 02:31:17.688000',5.69050179577373,'2026-10-06 08:31:17.010462',1,1,1),(632,'2026-10-07 02:31:17.689000','2026-10-07 02:31:17.689000',38.817884099552096,'2026-10-06 08:31:17.010462',1,2,2),(633,'2026-10-07 02:31:17.690000','2026-10-07 02:31:17.690000',533.6219335057722,'2026-10-06 08:31:17.010462',2,3,3),(634,'2026-10-07 02:31:17.691000','2026-10-07 02:31:17.691000',-193.86382243777993,'2026-10-06 08:31:17.010462',3,1,4),(635,'2026-10-07 02:31:17.691000','2026-10-07 02:31:17.691000',1024.6861715705384,'2026-10-06 08:31:17.010462',3,6,5),(636,'2026-10-07 02:31:17.692000','2026-10-07 02:31:17.692000',32.56455846264209,'2026-10-06 08:31:17.010462',2,4,6),(637,'2026-10-07 02:31:17.693000','2026-10-07 02:31:17.693000',1.5296947881934053,'2026-10-06 08:31:17.010462',2,5,7),(638,'2026-10-07 02:31:17.695000','2026-10-07 02:31:17.695000',21.22723551244401,'2026-10-06 08:31:17.010462',5,1,9),(639,'2026-10-07 02:31:17.696000','2026-10-07 02:31:17.696000',40.516207918329265,'2026-10-06 08:31:17.010462',5,2,10),(640,'2026-10-07 02:31:17.696000','2026-10-07 02:31:17.696000',129.73847630878194,'2026-10-06 08:31:17.010462',6,6,11),(641,'2026-10-07 02:31:17.697000','2026-10-07 02:31:17.697000',18.17346437971317,'2026-10-06 08:31:17.010462',6,4,12),(642,'2026-10-07 02:31:17.698000','2026-10-07 02:31:17.698000',20.634934291079322,'2026-10-06 08:31:17.010462',7,1,13),(643,'2026-10-07 02:31:17.698000','2026-10-07 02:31:17.698000',983.3621933505772,'2026-10-06 08:31:17.010462',7,6,14),(644,'2026-10-07 02:31:17.699000','2026-10-07 02:31:17.699000',21.269385751885267,'2026-10-06 08:31:17.010462',8,1,15),(645,'2026-10-07 02:31:17.700000','2026-10-07 02:31:17.700000',502.2469882334903,'2026-10-06 08:31:17.010462',8,3,16),(646,'2026-10-07 02:31:17.703000','2026-10-07 02:31:17.703000',22.499787954575122,'2026-10-06 08:31:17.010462',9,1,17),(647,'2026-10-07 02:31:17.704000','2026-10-07 02:31:17.704000',36.896833931924576,'2026-10-06 08:31:17.010462',10,1,18),(648,'2026-10-07 02:31:17.704000','2026-10-07 02:31:17.704000',30.07754300754107,'2026-10-06 08:31:17.010462',11,4,19),(649,'2026-10-07 02:31:17.705000','2026-10-07 02:31:17.705000',984.2737988023383,'2026-10-06 08:31:17.010462',11,6,20),(650,'2026-10-07 02:31:17.706000','2026-10-07 02:31:17.706000',1.6349342910793225,'2026-10-06 08:31:17.010462',12,5,21),(651,'2026-10-07 02:31:17.707000','2026-10-07 02:31:17.707000',20.051620791832928,'2026-10-06 08:31:17.010462',12,1,22),(652,'2026-10-07 02:31:17.708000','2026-10-07 02:31:17.708000',5.2411700267079135,'2026-10-06 09:31:17.010462',1,1,1),(653,'2026-10-07 02:31:17.708000','2026-10-07 02:31:17.708000',37.59348254137814,'2026-10-06 09:31:17.010462',1,2,2),(654,'2026-10-07 02:31:17.709000','2026-10-07 02:31:17.709000',561.6688346959752,'2026-10-06 09:31:17.010462',2,3,3),(655,'2026-10-07 02:31:17.710000','2026-10-07 02:31:17.710000',-194.62907341163867,'2026-10-06 09:31:17.010462',3,1,4),(656,'2026-10-07 02:31:17.711000','2026-10-07 02:31:17.711000',1024.8999777254296,'2026-10-06 09:31:17.010462',3,6,5),(657,'2026-10-07 02:31:17.712000','2026-10-07 02:31:17.712000',37.02570012076944,'2026-10-06 09:31:17.010462',2,4,6),(658,'2026-10-07 02:31:17.713000','2026-10-07 02:31:17.713000',1.8387896900987921,'2026-10-06 09:31:17.010462',2,5,7),(659,'2026-10-07 02:31:17.713000','2026-10-07 02:31:17.713000',21.074185317672267,'2026-10-06 09:31:17.010462',5,1,9),(660,'2026-10-07 02:31:17.714000','2026-10-07 02:31:17.714000',40.1660090371027,'2026-10-06 09:31:17.010462',5,2,10),(661,'2026-10-07 02:31:17.715000','2026-10-07 02:31:17.715000',129.91664810452468,'2026-10-06 09:31:17.010462',6,6,11),(662,'2026-10-07 02:31:17.717000','2026-10-07 02:31:17.717000',16.8898887135649,'2026-10-06 09:31:17.010462',6,4,12),(663,'2026-10-07 02:31:17.717000','2026-10-07 02:31:17.717000',20.395290680910886,'2026-10-06 09:31:17.010462',7,1,13),(664,'2026-10-07 02:31:17.718000','2026-10-07 02:31:17.718000',986.1668834695976,'2026-10-06 09:31:17.010462',7,6,14),(665,'2026-10-07 02:31:17.718000','2026-10-07 02:31:17.718000',20.75595548542596,'2026-10-06 09:31:17.010462',8,1,15),(666,'2026-10-07 02:31:17.719000','2026-10-07 02:31:17.719000',510.50106417714164,'2026-10-06 09:31:17.010462',8,3,16),(667,'2026-10-07 02:31:17.720000','2026-10-07 02:31:17.720000',22.417508054001342,'2026-10-06 09:31:17.010462',9,1,17),(668,'2026-10-07 02:31:17.721000','2026-10-07 02:31:17.721000',36.747056675569304,'2026-10-06 09:31:17.010462',10,1,18),(669,'2026-10-07 02:31:17.721000','2026-10-07 02:31:17.721000',28.023821941703844,'2026-10-06 09:31:17.010462',11,4,19),(670,'2026-10-07 02:31:17.722000','2026-10-07 02:31:17.722000',985.5768371739142,'2026-10-06 09:31:17.010462',11,6,20),(671,'2026-10-07 02:31:17.723000','2026-10-07 02:31:17.723000',1.3952906809108871,'2026-10-06 09:31:17.010462',12,5,21),(672,'2026-10-07 02:31:17.724000','2026-10-07 02:31:17.724000',20.01660090371027,'2026-10-06 09:31:17.010462',12,1,22),(673,'2026-10-07 02:31:17.725000','2026-10-07 02:31:17.725000',4.67482380727574,'2026-10-06 10:31:17.010462',1,1,1),(674,'2026-10-07 02:31:17.726000','2026-10-07 02:31:17.726000',37.030683129313275,'2026-10-06 10:31:17.010462',1,2,2),(675,'2026-10-07 02:31:17.727000','2026-10-07 02:31:17.727000',591.243878485863,'2026-10-06 10:31:17.010462',2,3,3),(676,'2026-10-07 02:31:17.728000','2026-10-07 02:31:17.728000',-194.9808230441792,'2026-10-06 10:31:17.010462',3,1,4),(677,'2026-10-07 02:31:17.729000','2026-10-07 02:31:17.729000',1024.994883236498,'2026-10-06 10:31:17.010462',3,6,5),(678,'2026-10-07 02:31:17.729000','2026-10-07 02:31:17.729000',41.08351314532254,'2026-10-06 10:31:17.010462',2,4,6),(679,'2026-10-07 02:31:17.730000','2026-10-07 02:31:17.730000',1.9914865972987055,'2026-10-06 10:31:17.010462',2,5,7),(680,'2026-10-07 02:31:17.731000','2026-10-07 02:31:17.731000',21.00383539116416,'2026-10-06 10:31:17.010462',5,1,9),(681,'2026-10-07 02:31:17.732000','2026-10-07 02:31:17.732000',40.008526121026236,'2026-10-06 10:31:17.010462',5,2,10),(682,'2026-10-07 02:31:17.733000','2026-10-07 02:31:17.733000',129.99573603041506,'2026-10-06 10:31:17.010462',6,6,11),(683,'2026-10-07 02:31:17.735000','2026-10-07 02:31:17.735000',15.437494917197231,'2026-10-06 10:31:17.010462',6,4,12),(684,'2026-10-07 02:31:17.735000','2026-10-07 02:31:17.735000',20.093239363880397,'2026-10-06 10:31:17.010462',7,1,13),(685,'2026-10-07 02:31:17.736000','2026-10-07 02:31:17.736000',989.1243878485863,'2026-10-06 10:31:17.010462',7,6,14),(686,'2026-10-07 02:31:17.737000','2026-10-07 02:31:17.737000',20.17499796687889,'2026-10-06 10:31:17.010462',8,1,15),(687,'2026-10-07 02:31:17.738000','2026-10-07 02:31:17.738000',524.3197504692072,'2026-10-06 10:31:17.010462',8,3,16),(688,'2026-10-07 02:31:17.738000','2026-10-07 02:31:17.738000',22.163348817765375,'2026-10-06 10:31:17.010462',9,1,17),(689,'2026-10-07 02:31:17.739000','2026-10-07 02:31:17.739000',36.55827460242525,'2026-10-06 10:31:17.010462',10,1,18),(690,'2026-10-07 02:31:17.740000','2026-10-07 02:31:17.740000',25.69999186751557,'2026-10-06 10:31:17.010462',11,4,19),(691,'2026-10-07 02:31:17.740000','2026-10-07 02:31:17.740000',986.7546318055115,'2026-10-06 10:31:17.010462',11,6,20),(692,'2026-10-07 02:31:17.741000','2026-10-07 02:31:17.741000',1.093239363880395,'2026-10-06 10:31:17.010462',12,5,21),(693,'2026-10-07 02:31:17.742000','2026-10-07 02:31:17.742000',20.000852612102623,'2026-10-06 10:31:17.010462',12,1,22),(694,'2026-10-07 02:31:17.743000','2026-10-07 02:31:17.743000',4.080876752701611,'2026-10-06 11:31:17.010462',1,1,1),(695,'2026-10-07 02:31:17.744000','2026-10-07 02:31:17.744000',37.179759058679224,'2026-10-06 11:31:17.010462',1,2,2),(696,'2026-10-07 02:31:17.745000','2026-10-07 02:31:17.745000',621.1680012089801,'2026-10-06 11:31:17.010462',2,3,3),(697,'2026-10-07 02:31:17.746000','2026-10-07 02:31:17.746000',-194.88765058832547,'2026-10-06 11:31:17.010462',3,1,4),(698,'2026-10-07 02:31:17.746000','2026-10-07 02:31:17.746000',1024.9699398392486,'2026-10-06 11:31:17.010462',3,6,5),(699,'2026-10-07 02:31:17.747000','2026-10-07 02:31:17.747000',43.93006344689077,'2026-10-06 11:31:17.010462',2,4,6),(700,'2026-10-07 02:31:17.748000','2026-10-07 02:31:17.748000',1.9503999814197912,'2026-10-06 11:31:17.010462',2,5,7),(701,'2026-10-07 02:31:17.748000','2026-10-07 02:31:17.748000',21.022469882334903,'2026-10-06 11:31:17.010462',5,1,9),(702,'2026-10-07 02:31:17.749000','2026-10-07 02:31:17.749000',40.050037516997776,'2026-10-06 11:31:17.010462',5,2,10),(703,'2026-10-07 02:31:17.750000','2026-10-07 02:31:17.750000',129.97494986604053,'2026-10-06 11:31:17.010462',6,6,11),(704,'2026-10-07 02:31:17.751000','2026-10-07 02:31:17.751000',13.946021002846102,'2026-10-06 11:31:17.010462',6,4,12),(705,'2026-10-07 02:31:17.751000','2026-10-07 02:31:17.751000',19.77646760144086,'2026-10-06 11:31:17.010462',7,1,13),(706,'2026-10-07 02:31:17.752000','2026-10-07 02:31:17.752000',992.116800120898,'2026-10-06 11:31:17.010462',7,6,14),(707,'2026-10-07 02:31:17.753000','2026-10-07 02:31:17.753000',19.57840840113844,'2026-10-06 11:31:17.010462',8,1,15),(708,'2026-10-07 02:31:17.755000','2026-10-07 02:31:17.755000',542.8438681257657,'2026-10-06 11:31:17.010462',8,3,16),(709,'2026-10-07 02:31:17.756000','2026-10-07 02:31:17.756000',21.76812821586276,'2026-10-06 11:31:17.010462',9,1,17),(710,'2026-10-07 02:31:17.756000','2026-10-07 02:31:17.756000',36.360292250900535,'2026-10-06 11:31:17.010462',10,1,18),(711,'2026-10-07 02:31:17.757000','2026-10-07 02:31:17.757000',23.31363360455376,'2026-10-06 11:31:17.010462',11,4,19),(712,'2026-10-07 02:31:17.758000','2026-10-07 02:31:17.758000',987.7807319688792,'2026-10-06 11:31:17.010462',11,6,20),(713,'2026-10-07 02:31:17.759000','2026-10-07 02:31:17.759000',1.2235323985591406,'2026-10-06 11:31:17.010462',12,5,21),(714,'2026-10-07 02:31:17.759000','2026-10-07 02:31:17.759000',20.00500375169978,'2026-10-06 11:31:17.010462',12,1,22),(715,'2026-10-07 02:31:17.760000','2026-10-07 02:31:17.760000',3.5531000431915185,'2026-10-06 12:31:17.010462',1,1,1),(716,'2026-10-07 02:31:17.760000','2026-10-07 02:31:17.760000',38.027393820691294,'2026-10-06 12:31:17.010462',1,2,2),(717,'2026-10-07 02:31:17.761000','2026-10-07 02:31:17.761000',650.2482225233857,'2026-10-06 12:31:17.010462',2,3,3),(718,'2026-10-07 02:31:17.762000','2026-10-07 02:31:17.762000',-194.35787886206793,'2026-10-06 12:31:17.010462',3,1,4),(719,'2026-10-07 02:31:17.762000','2026-10-07 02:31:17.762000',1024.8253967598616,'2026-10-06 12:31:17.010462',3,6,5),(720,'2026-10-07 02:31:17.763000','2026-10-07 02:31:17.763000',44.99858636383415,'2026-10-06 12:31:17.010462',2,4,6),(721,'2026-10-07 02:31:17.765000','2026-10-07 02:31:17.765000',1.7255892789750313,'2026-10-06 12:31:17.010462',2,5,7),(722,'2026-10-07 02:31:17.766000','2026-10-07 02:31:17.766000',21.12842422758641,'2026-10-06 12:31:17.010462',5,1,9),(723,'2026-10-07 02:31:17.767000','2026-10-07 02:31:17.767000',40.28888829665671,'2026-10-06 12:31:17.010462',5,2,10),(724,'2026-10-07 02:31:17.768000','2026-10-07 02:31:17.768000',129.8544972998846,'2026-10-06 12:31:17.010462',6,6,11),(725,'2026-10-07 02:31:17.769000','2026-10-07 02:31:17.769000',12.548695893296504,'2026-10-06 12:31:17.010462',6,4,12),(726,'2026-10-07 02:31:17.770000','2026-10-07 02:31:17.770000',19.494986689702145,'2026-10-06 12:31:17.010462',7,1,13),(727,'2026-10-07 02:31:17.770000','2026-10-07 02:31:17.770000',995.0248222523386,'2026-10-06 12:31:17.010462',7,6,14),(728,'2026-10-07 02:31:17.771000','2026-10-07 02:31:17.771000',19.0194783573186,'2026-10-06 12:31:17.010462',8,1,15),(729,'2026-10-07 02:31:17.772000','2026-10-07 02:31:17.772000',564.9216772310381,'2026-10-06 12:31:17.010462',8,3,16),(730,'2026-10-07 02:31:17.772000','2026-10-07 02:31:17.772000',21.279768554133863,'2026-10-06 12:31:17.010462',9,1,17),(731,'2026-10-07 02:31:17.773000','2026-10-07 02:31:17.773000',36.18436668106384,'2026-10-06 12:31:17.010462',10,1,18),(732,'2026-10-07 02:31:17.774000','2026-10-07 02:31:17.774000',21.077913429274403,'2026-10-06 12:31:17.010462',11,4,19),(733,'2026-10-07 02:31:17.775000','2026-10-07 02:31:17.775000',988.6320936664887,'2026-10-06 12:31:17.010462',11,6,20),(734,'2026-10-07 02:31:17.776000','2026-10-07 02:31:17.776000',1.5050133102978567,'2026-10-06 12:31:17.010462',12,5,21),(735,'2026-10-07 02:31:17.778000','2026-10-07 02:31:17.778000',20.02888882966567,'2026-10-06 12:31:17.010462',12,1,22),(736,'2026-10-07 02:31:17.778000','2026-10-07 02:31:17.778000',3.1748180164197706,'2026-10-06 13:31:17.010462',1,1,1),(737,'2026-10-07 02:31:17.779000','2026-10-07 02:31:17.779000',39.49787072652821,'2026-10-06 13:31:17.010462',1,2,2),(738,'2026-10-07 02:31:17.780000','2026-10-07 02:31:17.780000',677.3252057732196,'2026-10-06 13:31:17.010462',2,3,3),(739,'2026-10-07 02:31:17.781000','2026-10-07 02:31:17.781000',-193.43883079591987,'2026-10-06 13:31:17.010462',3,1,4),(740,'2026-10-07 02:31:17.782000','2026-10-07 02:31:17.782000',1024.5626982250062,'2026-10-06 13:31:17.010462',3,6,5),(741,'2026-10-07 02:31:17.783000','2026-10-07 02:31:17.783000',44.07633279098414,'2026-10-06 13:31:17.010462',2,4,6),(742,'2026-10-07 02:31:17.784000','2026-10-07 02:31:17.784000',1.3720959904702523,'2026-10-06 13:31:17.010462',2,5,7),(743,'2026-10-07 02:31:17.784000','2026-10-07 02:31:17.784000',21.312233840816027,'2026-10-06 13:31:17.010462',5,1,9),(744,'2026-10-07 02:31:17.785000','2026-10-07 02:31:17.785000',40.71555623315526,'2026-10-06 13:31:17.010462',5,2,10),(745,'2026-10-07 02:31:17.787000','2026-10-07 02:31:17.787000',129.63558185417193,'2026-10-06 13:31:17.010462',6,6,11),(746,'2026-10-07 02:31:17.787000','2026-10-07 02:31:17.787000',11.3703384789993,'2026-10-06 13:31:17.010462',6,4,12),(747,'2026-10-07 02:31:17.788000','2026-10-07 02:31:17.788000',19.293236275423876,'2026-10-06 13:31:17.010462',7,1,13),(748,'2026-10-07 02:31:17.789000','2026-10-07 02:31:17.789000',997.7325205773219,'2026-10-06 13:31:17.010462',7,6,14),(749,'2026-10-07 02:31:17.789000','2026-10-07 02:31:17.789000',18.54813539159972,'2026-10-06 13:31:17.010462',8,1,15),(750,'2026-10-07 02:31:17.790000','2026-10-07 02:31:17.790000',589.1804865469892,'2026-10-06 13:31:17.010462',8,3,16),(751,'2026-10-07 02:31:17.791000','2026-10-07 02:31:17.791000',20.75748567546947,'2026-10-06 13:31:17.010462',9,1,17),(752,'2026-10-07 02:31:17.792000','2026-10-07 02:31:17.792000',36.05827267213992,'2026-10-06 13:31:17.010462',10,1,18),(753,'2026-10-07 02:31:17.793000','2026-10-07 02:31:17.793000',19.19254156639888,'2026-10-06 13:31:17.010462',11,4,19),(754,'2026-10-07 02:31:17.793000','2026-10-07 02:31:17.793000',989.2895971500387,'2026-10-06 13:31:17.010462',11,6,20),(755,'2026-10-07 02:31:17.794000','2026-10-07 02:31:17.794000',1.7067637245761227,'2026-10-06 13:31:17.010462',12,5,21),(756,'2026-10-07 02:31:17.795000','2026-10-07 02:31:17.795000',20.071555623315525,'2026-10-06 13:31:17.010462',12,1,22),(757,'2026-10-07 02:31:17.808000','2026-10-07 02:31:17.808000',3.005753086746239,'2026-10-06 14:31:17.010462',1,1,1),(758,'2026-10-07 02:31:17.809000','2026-10-07 02:31:17.809000',41.45983645364118,'2026-10-06 14:31:17.010462',1,2,2),(759,'2026-10-07 02:31:17.810000','2026-10-07 02:31:17.810000',701.3194770826726,'2026-10-06 14:31:17.010462',2,3,3),(760,'2026-10-07 02:31:17.810000','2026-10-07 02:31:17.810000',-192.21260221647427,'2026-10-06 14:31:17.010462',3,1,4),(761,'2026-10-07 02:31:17.811000','2026-10-07 02:31:17.811000',1024.1844690316068,'2026-10-06 14:31:17.010462',3,6,5),(762,'2026-10-07 02:31:17.812000','2026-10-07 02:31:17.812000',41.34692875942635,'2026-10-06 14:31:17.010462',2,4,6),(763,'2026-10-07 02:31:17.812000','2026-10-07 02:31:17.812000',1.4235323985591406,'2026-10-06 14:31:17.010462',2,5,7),(764,'2026-10-07 02:31:17.813000','2026-10-07 02:31:17.813000',21.557479556705147,'2026-10-06 14:31:17.010462',5,1,9),(765,'2026-10-07 02:31:17.813000','2026-10-07 02:31:17.813000',41.31303142229377,'2026-10-06 14:31:17.010462',5,2,10),(766,'2026-10-07 02:31:17.814000','2026-10-07 02:31:17.814000',129.32039085967227,'2026-10-06 14:31:17.010462',6,6,11),(767,'2026-10-07 02:31:17.815000','2026-10-07 02:31:17.815000',10.516207918329265,'2026-10-06 14:31:17.010462',6,4,12),(768,'2026-10-07 02:31:17.816000','2026-10-07 02:31:17.816000',19.20306831293133,'2026-10-06 14:31:17.010462',7,1,13),(769,'2026-10-07 02:31:17.817000','2026-10-07 02:31:17.817000',1000.1319477082673,'2026-10-06 14:31:17.010462',7,6,14),(770,'2026-10-07 02:31:17.818000','2026-10-07 02:31:17.818000',18.206483167331704,'2026-10-06 14:31:17.010462',8,1,15),(771,'2026-10-07 02:31:17.819000','2026-10-07 02:31:17.819000',614.1120008059867,'2026-10-06 14:31:17.010462',8,3,16),(772,'2026-10-07 02:31:17.820000','2026-10-07 02:31:17.820000',20.26460876798895,'2026-10-06 14:31:17.010462',9,1,17),(773,'2026-10-07 02:31:17.820000','2026-10-07 02:31:17.820000',36.00191769558208,'2026-10-06 14:31:17.010462',10,1,18),(774,'2026-10-07 02:31:17.821000','2026-10-07 02:31:17.821000',17.82593266932682,'2026-10-06 14:31:17.010462',11,4,19),(775,'2026-10-07 02:31:17.822000','2026-10-07 02:31:17.822000',989.738476308782,'2026-10-06 14:31:17.010462',11,6,20),(776,'2026-10-07 02:31:17.823000','2026-10-07 02:31:17.823000',3.2,'2026-10-06 14:31:17.010462',12,5,21),(777,'2026-10-07 02:31:17.823000','2026-10-07 02:31:17.823000',20.131303142229378,'2026-10-06 14:31:17.010462',12,1,22),(778,'2026-10-07 02:31:17.824000','2026-10-07 02:31:17.824000',3.072596889165726,'2026-10-06 15:31:17.010462',1,1,1),(779,'2026-10-07 02:31:17.824000','2026-10-07 02:31:17.824000',43.738034446854016,'2026-10-06 15:31:17.010462',1,2,2),(780,'2026-10-07 02:31:17.825000','2026-10-07 02:31:17.825000',721.2744605729386,'2026-10-06 15:31:17.010462',2,3,3),(781,'2026-10-07 02:31:17.826000','2026-10-07 02:31:17.826000',-190.78872847071625,'2026-10-06 15:31:17.010462',3,1,4),(782,'2026-10-07 02:31:17.827000','2026-10-07 02:31:17.827000',1023.6944883207373,'2026-10-06 15:31:17.010462',3,6,5),(783,'2026-10-07 02:31:17.827000','2026-10-07 02:31:17.827000',37.353814429544514,'2026-10-06 15:31:17.010462',2,4,6),(784,'2026-10-07 02:31:17.828000','2026-10-07 02:31:17.828000',1.7644322604563136,'2026-10-06 15:31:17.010462',2,5,7),(785,'2026-10-07 02:31:17.829000','2026-10-07 02:31:17.829000',21.842254305856752,'2026-10-06 15:31:17.010462',5,1,9),(786,'2026-10-07 02:31:17.830000','2026-10-07 02:31:17.830000',42.05749441372327,'2026-10-06 15:31:17.010462',5,2,10),(787,'2026-10-07 02:31:17.830000','2026-10-07 02:31:17.830000',128.91207360061435,'2026-10-06 15:31:17.010462',6,6,11),(788,'2026-10-07 02:31:17.831000','2026-10-07 02:31:17.831000',10.062601150455675,'2026-10-06 15:31:17.010462',6,4,12),(789,'2026-10-07 02:31:17.832000','2026-10-07 02:31:17.832000',19.238718340888386,'2026-10-06 15:31:17.010462',7,1,13),(790,'2026-10-07 02:31:17.833000','2026-10-07 02:31:17.833000',1002.1274460572938,'2026-10-06 15:31:17.010462',7,6,14),(791,'2026-10-07 02:31:17.835000','2026-10-07 02:31:17.835000',18.02504046018227,'2026-10-06 15:31:17.010462',8,1,15),(792,'2026-10-07 02:31:17.836000','2026-10-07 02:31:17.836000',638.1660992052332,'2026-10-06 15:31:17.010462',8,3,16),(793,'2026-10-07 02:31:17.837000','2026-10-07 02:31:17.837000',19.86090141129374,'2026-10-06 15:31:17.010462',9,1,17),(794,'2026-10-07 02:31:17.838000','2026-10-07 02:31:17.838000',36.02419896305524,'2026-10-06 15:31:17.010462',10,1,18),(795,'2026-10-07 02:31:17.839000','2026-10-07 02:31:17.839000',17.10016184072908,'2026-10-06 15:31:17.010462',11,4,19),(796,'2026-10-07 02:31:17.840000','2026-10-07 02:31:17.840000',989.9686502845392,'2026-10-06 15:31:17.010462',11,6,20),(797,'2026-10-07 02:31:17.841000','2026-10-07 02:31:17.841000',3.2,'2026-10-06 15:31:17.010462',12,5,21),(798,'2026-10-07 02:31:17.842000','2026-10-07 02:31:17.842000',20.205749441372326,'2026-10-06 15:31:17.010462',12,1,22),(799,'2026-10-07 02:31:17.842000','2026-10-07 02:31:17.842000',3.364796257038108,'2026-10-06 16:31:17.010462',1,1,1),(800,'2026-10-07 02:31:17.843000','2026-10-07 02:31:17.843000',46.12896006447894,'2026-10-06 16:31:17.010462',1,2,2),(801,'2026-10-07 02:31:17.844000','2026-10-07 02:31:17.844000',736.3946140238522,'2026-10-06 16:31:17.010462',2,3,3),(802,'2026-10-07 02:31:17.845000','2026-10-07 02:31:17.845000',-189.29439995970066,'2026-10-06 16:31:17.010462',3,1,4),(803,'2026-10-07 02:31:17.846000','2026-10-07 02:31:17.846000',1023.0976518176948,'2026-10-06 16:31:17.010462',3,6,5),(804,'2026-10-07 02:31:17.847000','2026-10-07 02:31:17.847000',32.892042005692204,'2026-10-06 16:31:17.010462',2,4,6),(805,'2026-10-07 02:31:17.848000','2026-10-07 02:31:17.848000',1.9671394197305108,'2026-10-06 16:31:17.010462',2,5,7),(806,'2026-10-07 02:31:17.849000','2026-10-07 02:31:17.849000',22.14112000805987,'2026-10-06 16:31:17.010462',5,1,9),(807,'2026-10-07 02:31:17.850000','2026-10-07 02:31:17.850000',42.91926581726429,'2026-10-06 16:31:17.010462',5,2,10),(808,'2026-10-07 02:31:17.851000','2026-10-07 02:31:17.851000',128.41470984807896,'2026-10-06 16:31:17.010462',6,6,11),(809,'2026-10-07 02:31:17.851000','2026-10-07 02:31:17.851000',10.050037516997772,'2026-10-06 16:31:17.010462',6,4,12),(810,'2026-10-07 02:31:17.852000','2026-10-07 02:31:17.852000',19.39455800375366,'2026-10-06 16:31:17.010462',7,1,13),(811,'2026-10-07 02:31:17.853000','2026-10-07 02:31:17.853000',1003.6394614023852,'2026-10-06 16:31:17.010462',7,6,14),(812,'2026-10-07 02:31:17.853000','2026-10-07 02:31:17.853000',18.02001500679911,'2026-10-06 16:31:17.010462',8,1,15),(813,'2026-10-07 02:31:17.854000','2026-10-07 02:31:17.854000',659.8472144103956,'2026-10-06 16:31:17.010462',8,3,16),(814,'2026-10-07 02:31:17.855000','2026-10-07 02:31:17.855000',19.595314969063807,'2026-10-06 16:31:17.010462',9,1,17),(815,'2026-10-07 02:31:17.856000','2026-10-07 02:31:17.856000',38.5,'2026-10-06 16:31:17.010462',10,1,18),(816,'2026-10-07 02:31:17.857000','2026-10-07 02:31:17.857000',17.08006002719644,'2026-10-06 16:31:17.010462',11,4,19),(817,'2026-10-07 02:31:17.858000','2026-10-07 02:31:17.858000',989.9749498660406,'2026-10-06 16:31:17.010462',11,6,20),(818,'2026-10-07 02:31:17.859000','2026-10-07 02:31:17.859000',3.2,'2026-10-06 16:31:17.010462',12,5,21),(819,'2026-10-07 02:31:17.860000','2026-10-07 02:31:17.860000',20.29192658172643,'2026-10-06 16:31:17.010462',12,1,22),(820,'2026-10-07 02:31:17.861000','2026-10-07 02:31:17.861000',3.8362193350577214,'2026-10-06 17:31:17.010462',1,1,1),(821,'2026-10-07 02:31:17.862000','2026-10-07 02:31:17.862000',48.41903904187064,'2026-10-06 17:31:17.010462',1,2,2),(822,'2026-10-07 02:31:17.863000','2026-10-07 02:31:17.863000',746.0771446317293,'2026-10-06 17:31:17.010462',2,3,3),(823,'2026-10-07 02:31:17.863000','2026-10-07 02:31:17.863000',-187.86310059883084,'2026-10-06 17:31:17.010462',3,1,4),(824,'2026-10-07 02:31:17.864000','2026-10-07 02:31:17.864000',1022.3999229155298,'2026-10-06 17:31:17.010462',3,6,5),(825,'2026-10-07 02:31:17.865000','2026-10-07 02:31:17.865000',28.849976234744254,'2026-10-06 17:31:17.010462',2,4,6),(826,'2026-10-07 02:31:17.866000','2026-10-07 02:31:17.866000',1.9820240941320777,'2026-10-06 17:31:17.010462',2,5,7),(827,'2026-10-07 02:31:17.867000','2026-10-07 02:31:17.867000',22.42737988023383,'2026-10-06 17:31:17.010462',5,1,9),(828,'2026-10-07 02:31:17.867000','2026-10-07 02:31:17.867000',43.86398952653457,'2026-10-06 17:31:17.010462',5,2,10),(829,'2026-10-07 02:31:17.868000','2026-10-07 02:31:17.868000',127.83326909627483,'2026-10-06 17:31:17.010462',6,6,11),(830,'2026-10-07 02:31:17.872000','2026-10-07 02:31:17.872000',10.479639289914696,'2026-10-06 17:31:17.010462',6,4,12),(831,'2026-10-07 02:31:17.874000','2026-10-07 02:31:17.874000',19.64598364536412,'2026-10-06 17:31:17.010462',7,1,13),(832,'2026-10-07 02:31:17.874000','2026-10-07 02:31:17.874000',1004.6077144631729,'2026-10-06 17:31:17.010462',7,6,14),(833,'2026-10-07 02:31:17.875000','2026-10-07 02:31:17.875000',18.19185571596588,'2026-10-06 17:31:17.010462',8,1,15),(834,'2026-10-07 02:31:17.876000','2026-10-07 02:31:17.876000',677.8073196887922,'2026-10-06 17:31:17.010462',8,3,16),(835,'2026-10-07 02:31:17.877000','2026-10-07 02:31:17.877000',26.5,'2026-10-06 17:31:17.010462',9,1,17),(836,'2026-10-07 02:31:17.877000','2026-10-07 02:31:17.877000',38.5,'2026-10-06 17:31:17.010462',10,1,18),(837,'2026-10-07 02:31:17.878000','2026-10-07 02:31:17.878000',17.767422863863512,'2026-10-06 17:31:17.010462',11,4,19),(838,'2026-10-07 02:31:17.879000','2026-10-07 02:31:17.879000',989.7572335782666,'2026-10-06 17:31:17.010462',11,6,20),(839,'2026-10-07 02:31:17.880000','2026-10-07 02:31:17.880000',3.2,'2026-10-06 17:31:17.010462',12,5,21),(840,'2026-10-07 02:31:17.880000','2026-10-07 02:31:17.880000',20.386398952653458,'2026-10-06 17:31:17.010462',12,1,22),(841,'2026-10-07 02:31:17.881000','2026-10-07 02:31:17.881000',4.4124387848586295,'2026-10-06 18:31:17.010462',1,1,1),(842,'2026-10-07 02:31:17.882000','2026-10-07 02:31:17.882000',50.403705444409205,'2026-10-06 18:31:17.010462',1,2,2),(843,'2026-10-07 02:31:17.884000','2026-10-07 02:31:17.884000',749.9360404562258,'2026-10-06 18:31:17.010462',2,3,3),(844,'2026-10-07 02:31:17.884000','2026-10-07 02:31:17.884000',-186.62268409724425,'2026-10-06 18:31:17.010462',3,1,4),(845,'2026-10-07 02:31:17.885000','2026-10-07 02:31:17.885000',1021.6082730907942,'2026-10-06 18:31:17.010462',3,6,5),(846,'2026-10-07 02:31:17.886000','2026-10-07 02:31:17.886000',26.03241583665853,'2026-10-06 18:31:17.010462',2,4,6),(847,'2026-10-07 02:31:17.887000','2026-10-07 02:31:17.887000',1.8054419962463426,'2026-10-06 18:31:17.010462',2,5,7),(848,'2026-10-07 02:31:17.888000','2026-10-07 02:31:17.888000',22.67546318055115,'2026-10-06 18:31:17.010462',5,1,9),(849,'2026-10-07 02:31:17.888000','2026-10-07 02:31:17.888000',44.854002388493555,'2026-10-06 18:31:17.010462',5,2,10),(850,'2026-10-07 02:31:17.889000','2026-10-07 02:31:17.889000',127.17356090899523,'2026-10-06 18:31:17.010462',6,6,11),(851,'2026-10-07 02:31:17.890000','2026-10-07 02:31:17.890000',11.313031422293772,'2026-10-06 18:31:17.010462',6,4,12),(852,'2026-10-07 02:31:17.890000','2026-10-07 02:31:17.890000',19.953300685257936,'2026-10-06 18:31:17.010462',7,1,13),(853,'2026-10-07 02:31:17.891000','2026-10-07 02:31:17.891000',940,'2026-10-06 18:31:17.010462',7,6,14),(854,'2026-10-07 02:31:17.892000','2026-10-07 02:31:17.892000',18.52521256891751,'2026-10-06 18:31:17.010462',8,1,15),(855,'2026-10-07 02:31:17.893000','2026-10-07 02:31:17.893000',690.9297426825682,'2026-10-06 18:31:17.010462',8,3,16),(856,'2026-10-07 02:31:17.894000','2026-10-07 02:31:17.894000',26.5,'2026-10-06 18:31:17.010462',9,1,17),(857,'2026-10-07 02:31:17.895000','2026-10-07 02:31:17.895000',38.5,'2026-10-06 18:31:17.010462',10,1,18),(858,'2026-10-07 02:31:17.896000','2026-10-07 02:31:17.896000',19.100850275670037,'2026-10-06 18:31:17.010462',11,4,19),(859,'2026-10-07 02:31:17.897000','2026-10-07 02:31:17.897000',989.3203908596722,'2026-10-06 18:31:17.010462',11,6,20),(860,'2026-10-07 02:31:17.898000','2026-10-07 02:31:17.898000',3.2,'2026-10-06 18:31:17.010462',12,5,21),(861,'2026-10-07 02:31:17.899000','2026-10-07 02:31:17.899000',20.485400238849355,'2026-10-06 18:31:17.010462',12,1,22),(862,'2026-10-07 02:31:17.900000','2026-10-07 02:31:17.900000',5.002482225233857,'2026-10-06 19:31:17.010462',1,1,1),(863,'2026-10-07 02:31:17.900000','2026-10-07 02:31:17.900000',51.90567493319099,'2026-10-06 19:31:17.010462',1,2,2),(864,'2026-10-07 02:31:17.902000','2026-10-07 02:31:17.902000',747.817459498269,'2026-10-06 19:31:17.010462',2,3,3),(865,'2026-10-07 02:31:17.902000','2026-10-07 02:31:17.902000',-185.68395316675563,'2026-10-06 19:31:17.010462',3,1,4),(866,'2026-10-07 02:31:17.903000','2026-10-07 02:31:17.903000',1020.7306122468523,'2026-10-06 19:31:17.010462',3,6,5),(867,'2026-10-07 02:31:17.904000','2026-10-07 02:31:17.904000',25.000353415286583,'2026-10-06 19:31:17.010462',2,4,6),(868,'2026-10-07 02:31:17.905000','2026-10-07 02:31:17.905000',1.4806265821516957,'2026-10-06 19:31:17.010462',2,5,7),(869,'2026-10-07 02:31:17.906000','2026-10-07 02:31:17.906000',22.863209366648874,'2026-10-06 19:31:17.010462',5,1,9),(870,'2026-10-07 02:31:17.907000','2026-10-07 02:31:17.907000',45.849835714501204,'2026-10-06 19:31:17.010462',5,2,10),(871,'2026-10-07 02:31:17.908000','2026-10-07 02:31:17.908000',126.4421768723769,'2026-10-06 19:31:17.010462',6,6,11),(872,'2026-10-07 02:31:17.908000','2026-10-07 02:31:17.908000',12.475769477000712,'2026-10-06 19:31:17.010462',6,4,12),(873,'2026-10-07 02:31:17.910000','2026-10-07 02:31:17.910000',20.267990520124723,'2026-10-06 19:31:17.010462',7,1,13),(874,'2026-10-07 02:31:17.911000','2026-10-07 02:31:17.911000',940,'2026-10-06 19:31:17.010462',7,6,14),(875,'2026-10-07 02:31:17.912000','2026-10-07 02:31:17.912000',18.990307790800284,'2026-10-06 19:31:17.010462',8,1,15),(876,'2026-10-07 02:31:17.912000','2026-10-07 02:31:17.912000',1100,'2026-10-06 19:31:17.010462',8,3,16),(877,'2026-10-07 02:31:17.913000','2026-10-07 02:31:17.913000',26.5,'2026-10-06 19:31:17.010462',9,1,17),(878,'2026-10-07 02:31:17.915000','2026-10-07 02:31:17.915000',38.5,'2026-10-06 19:31:17.010462',10,1,18),(879,'2026-10-07 02:31:17.915000','2026-10-07 02:31:17.915000',20.96123116320114,'2026-10-06 19:31:17.010462',11,4,19),(880,'2026-10-07 02:31:17.916000','2026-10-07 02:31:17.916000',988.6742322559402,'2026-10-06 19:31:17.010462',11,6,20),(881,'2026-10-07 02:31:17.917000','2026-10-07 02:31:17.917000',3.2,'2026-10-06 19:31:17.010462',12,5,21),(882,'2026-10-07 02:31:17.918000','2026-10-07 02:31:17.918000',20.58498357145012,'2026-10-06 19:31:17.010462',12,1,22),(883,'2026-10-07 02:31:17.919000','2026-10-07 02:31:17.919000',5.5131947708267255,'2026-10-06 20:31:17.010462',1,1,1),(884,'2026-10-07 02:31:17.920000','2026-10-07 02:31:17.920000',52.79078104702556,'2026-10-06 20:31:17.010462',1,2,2),(885,'2026-10-07 02:31:17.921000','2026-10-07 02:31:17.921000',739.8058628950839,'2026-10-06 20:31:17.010462',2,3,3),(886,'2026-10-07 02:31:17.922000','2026-10-07 02:31:17.922000',-185.13076184560902,'2026-10-06 20:31:17.010462',3,1,4),(887,'2026-10-07 02:31:17.923000','2026-10-07 02:31:17.923000',1019.7757096807404,'2026-10-06 20:31:17.010462',3,6,5),(888,'2026-10-07 02:31:17.924000','2026-10-07 02:31:17.924000',25.959278579829387,'2026-10-06 20:31:17.010462',2,4,6),(889,'2026-10-07 02:31:17.925000','2026-10-07 02:31:17.925000',3.5,'2026-10-06 20:31:17.010462',2,5,7),(890,'2026-10-07 02:31:17.926000','2026-10-07 02:31:17.926000',22.973847630878197,'2026-10-06 20:31:17.010462',5,1,9),(891,'2026-10-07 02:31:17.927000','2026-10-07 02:31:17.927000',46.811788772383366,'2026-10-06 20:31:17.010462',5,2,10),(892,'2026-10-07 02:31:17.928000','2026-10-07 02:31:17.928000',125.64642473395035,'2026-10-06 20:31:17.010462',6,6,11),(893,'2026-10-07 02:31:17.929000','2026-10-07 02:31:17.929000',13.863989526534565,'2026-10-06 20:31:17.010462',6,4,12),(894,'2026-10-07 02:31:17.930000','2026-10-07 02:31:17.930000',20.540370544440922,'2026-10-06 20:31:17.010462',7,1,13),(895,'2026-10-07 02:31:17.931000','2026-10-07 02:31:17.931000',940,'2026-10-06 20:31:17.010462',7,6,14),(896,'2026-10-07 02:31:17.931000','2026-10-07 02:31:17.931000',28.5,'2026-10-06 20:31:17.010462',8,1,15),(897,'2026-10-07 02:31:17.932000','2026-10-07 02:31:17.932000',1100,'2026-10-06 20:31:17.010462',8,3,16),(898,'2026-10-07 02:31:17.933000','2026-10-07 02:31:17.933000',26.5,'2026-10-06 20:31:17.010462',9,1,17),(899,'2026-10-07 02:31:17.934000','2026-10-07 02:31:17.934000',38.5,'2026-10-06 20:31:17.010462',10,1,18),(900,'2026-10-07 02:31:17.934000','2026-10-07 02:31:17.934000',23.182383242455305,'2026-10-06 20:31:17.010462',11,4,19),(901,'2026-10-07 02:31:17.935000','2026-10-07 02:31:17.935000',987.8332690962749,'2026-10-06 20:31:17.010462',11,6,20),(902,'2026-10-07 02:31:17.936000','2026-10-07 02:31:17.936000',3.2,'2026-10-06 20:31:17.010462',12,5,21),(903,'2026-10-07 02:31:17.938000','2026-10-07 02:31:17.938000',20.681178877238338,'2026-10-06 20:31:17.010462',12,1,22),(904,'2026-10-07 02:31:17.939000','2026-10-07 02:31:17.939000',5.8639461402385225,'2026-10-06 21:31:17.010462',1,1,1),(905,'2026-10-07 02:31:17.940000','2026-10-07 02:31:17.940000',52.97995989283243,'2026-10-06 21:31:17.010462',1,2,2),(906,'2026-10-07 02:31:17.940000','2026-10-07 02:31:17.940000',726.2206477211845,'2026-10-06 21:31:17.010462',2,3,3),(907,'2026-10-07 02:31:17.941000','2026-10-07 02:31:17.941000',-185.01252506697972,'2026-10-06 21:31:17.010462',3,1,4),(908,'2026-10-07 02:31:17.942000','2026-10-07 02:31:17.942000',1018.7531064632504,'2026-10-06 21:31:17.010462',3,6,5),(909,'2026-10-07 02:31:17.943000','2026-10-07 02:31:17.943000',28.71826377277261,'2026-10-06 21:31:17.010462',2,4,6),(910,'2026-10-07 02:31:17.943000','2026-10-07 02:31:17.943000',3.5,'2026-10-06 21:31:17.010462',2,5,7),(911,'2026-10-07 02:31:17.944000','2026-10-07 02:31:17.944000',22.997494986604053,'2026-10-06 21:31:17.010462',5,1,9),(912,'2026-10-07 02:31:17.945000','2026-10-07 02:31:17.945000',47.7015115293407,'2026-10-06 21:31:17.010462',5,2,10),(913,'2026-10-07 02:31:17.945000','2026-10-07 02:31:17.945000',45,'2026-10-06 21:31:17.010462',6,6,11),(914,'2026-10-07 02:31:17.946000','2026-10-07 02:31:17.946000',15.353686008338515,'2026-10-06 21:31:17.010462',6,4,12),(915,'2026-10-07 02:31:17.947000','2026-10-07 02:31:17.947000',20.727437941460547,'2026-10-06 21:31:17.010462',7,1,13),(916,'2026-10-07 02:31:17.948000','2026-10-07 02:31:17.948000',940,'2026-10-06 21:31:17.010462',7,6,14),(917,'2026-10-07 02:31:17.948000','2026-10-07 02:31:17.948000',28.5,'2026-10-06 21:31:17.010462',8,1,15),(918,'2026-10-07 02:31:17.949000','2026-10-07 02:31:17.949000',1100,'2026-10-06 21:31:17.010462',8,3,16),(919,'2026-10-07 02:31:17.950000','2026-10-07 02:31:17.950000',26.5,'2026-10-06 21:31:17.010462',9,1,17),(920,'2026-10-07 02:31:17.951000','2026-10-07 02:31:17.951000',38.5,'2026-10-06 21:31:17.010462',10,1,18),(921,'2026-10-07 02:31:17.951000','2026-10-07 02:31:17.951000',25.565897613341622,'2026-10-06 21:31:17.010462',11,4,19),(922,'2026-10-07 02:31:17.952000','2026-10-07 02:31:17.952000',986.8163876002334,'2026-10-06 21:31:17.010462',11,6,20),(923,'2026-10-07 02:31:17.953000','2026-10-07 02:31:17.953000',3.2,'2026-10-06 21:31:17.010462',12,5,21),(924,'2026-10-07 02:31:17.954000','2026-10-07 02:31:17.954000',20.77015115293407,'2026-10-06 21:31:17.010462',12,1,22),(925,'2026-10-07 02:31:17.955000','2026-10-07 02:31:17.955000',5.999360404562258,'2026-10-06 22:31:17.010462',1,1,1),(926,'2026-10-07 02:31:17.955000','2026-10-07 02:31:17.955000',52.45631268773781,'2026-10-06 22:31:17.010462',1,2,2),(927,'2026-10-07 02:31:17.956000','2026-10-07 02:31:17.956000',1050,'2026-10-06 22:31:17.010462',2,3,3),(928,'2026-10-07 02:31:17.957000','2026-10-07 02:31:17.957000',-185.33980457016386,'2026-10-06 22:31:17.010462',3,1,4),(929,'2026-10-07 02:31:17.958000','2026-10-07 02:31:17.958000',1017.6730201077038,'2026-10-06 22:31:17.010462',3,6,5),(930,'2026-10-07 02:31:17.959000','2026-10-07 02:31:17.959000',32.72797905306913,'2026-10-06 22:31:17.010462',2,4,6),(931,'2026-10-07 02:31:17.960000','2026-10-07 02:31:17.960000',3.5,'2026-10-06 22:31:17.010462',2,5,7),(932,'2026-10-07 02:31:17.961000','2026-10-07 02:31:17.961000',22.932039085967226,'2026-10-06 22:31:17.010462',5,1,9),(933,'2026-10-07 02:31:17.961000','2026-10-07 02:31:17.961000',48.48353354673583,'2026-10-06 22:31:17.010462',5,2,10),(934,'2026-10-07 02:31:17.962000','2026-10-07 02:31:17.962000',45,'2026-10-06 22:31:17.010462',6,6,11),(935,'2026-10-07 02:31:17.962000','2026-10-07 02:31:17.962000',75,'2026-10-06 22:31:17.010462',6,4,12),(936,'2026-10-07 02:31:17.963000','2026-10-07 02:31:17.963000',20.799658882433203,'2026-10-06 22:31:17.010462',7,1,13),(937,'2026-10-07 02:31:17.964000','2026-10-07 02:31:17.964000',940,'2026-10-06 22:31:17.010462',7,6,14),(938,'2026-10-07 02:31:17.965000','2026-10-07 02:31:17.965000',28.5,'2026-10-06 22:31:17.010462',8,1,15),(939,'2026-10-07 02:31:17.966000','2026-10-07 02:31:17.966000',1100,'2026-10-06 22:31:17.010462',8,3,16),(940,'2026-10-07 02:31:17.966000','2026-10-07 02:31:17.966000',26.5,'2026-10-06 22:31:17.010462',9,1,17),(941,'2026-10-07 02:31:17.967000','2026-10-07 02:31:17.967000',38.5,'2026-10-06 22:31:17.010462',10,1,18),(942,'2026-10-07 02:31:17.968000','2026-10-07 02:31:17.968000',27.898862035813387,'2026-10-06 22:31:17.010462',11,4,19),(943,'2026-10-07 02:31:17.969000','2026-10-07 02:31:17.969000',850,'2026-10-06 22:31:17.010462',11,6,20),(944,'2026-10-07 02:31:17.969000','2026-10-07 02:31:17.969000',3.2,'2026-10-06 22:31:17.010462',12,5,21),(945,'2026-10-07 02:31:17.970000','2026-10-07 02:31:17.970000',20.848353354673584,'2026-10-06 22:31:17.010462',12,1,22),(946,'2026-10-07 02:31:17.971000','2026-10-07 02:31:17.971000',5.89805862895084,'2026-10-06 23:31:17.010462',1,1,1),(947,'2026-10-07 02:31:17.972000','2026-10-07 02:31:17.972000',51.266615277019866,'2026-10-06 23:31:17.010462',1,2,2),(948,'2026-10-07 02:31:17.973000','2026-10-07 02:31:17.973000',1050,'2026-10-06 23:31:17.010462',2,3,3),(949,'2026-10-07 02:31:17.973000','2026-10-07 02:31:17.973000',-170,'2026-10-06 23:31:17.010462',3,1,4),(950,'2026-10-07 02:31:17.974000','2026-10-07 02:31:17.974000',1016.546242479936,'2026-10-06 23:31:17.010462',3,6,5),(951,'2026-10-07 02:31:17.975000','2026-10-07 02:31:17.975000',37.190066870930416,'2026-10-06 23:31:17.010462',2,4,6),(952,'2026-10-07 02:31:17.976000','2026-10-07 02:31:17.976000',3.5,'2026-10-06 23:31:17.010462',2,5,7),(953,'2026-10-07 02:31:17.977000','2026-10-07 02:31:17.977000',22.783326909627483,'2026-10-06 23:31:17.010462',5,1,9),(954,'2026-10-07 02:31:17.978000','2026-10-07 02:31:17.978000',49.126678074548394,'2026-10-06 23:31:17.010462',5,2,10),(955,'2026-10-07 02:31:17.978000','2026-10-07 02:31:17.978000',45,'2026-10-06 23:31:17.010462',6,6,11),(956,'2026-10-07 02:31:17.979000','2026-10-07 02:31:17.979000',75,'2026-10-06 23:31:17.010462',6,4,12),(957,'2026-10-07 02:31:17.980000','2026-10-07 02:31:17.980000',20.74563126877378,'2026-10-06 23:31:17.010462',7,1,13),(958,'2026-10-07 02:31:17.980000','2026-10-07 02:31:17.980000',940,'2026-10-06 23:31:17.010462',7,6,14),(959,'2026-10-07 02:31:17.981000','2026-10-07 02:31:17.981000',28.5,'2026-10-06 23:31:17.010462',8,1,15),(960,'2026-10-07 02:31:17.981000','2026-10-07 02:31:17.981000',1100,'2026-10-06 23:31:17.010462',8,3,16),(961,'2026-10-07 02:31:17.982000','2026-10-07 02:31:17.982000',26.5,'2026-10-06 23:31:17.010462',9,1,17),(962,'2026-10-07 02:31:17.983000','2026-10-07 02:31:17.983000',38.5,'2026-10-06 23:31:17.010462',10,1,18),(963,'2026-10-07 02:31:17.984000','2026-10-07 02:31:17.984000',120,'2026-10-06 23:31:17.010462',11,4,19),(964,'2026-10-07 02:31:17.984000','2026-10-07 02:31:17.984000',850,'2026-10-06 23:31:17.010462',11,6,20),(965,'2026-10-07 02:31:17.985000','2026-10-07 02:31:17.985000',3.2,'2026-10-06 23:31:17.010462',12,5,21),(966,'2026-10-07 02:31:17.986000','2026-10-07 02:31:17.986000',20.912667807454838,'2026-10-06 23:31:17.010462',12,1,22),(967,'2026-10-07 02:31:17.987000','2026-10-07 02:31:17.987000',8.5,'2026-10-07 00:31:17.010462',1,1,1),(968,'2026-10-07 02:31:17.987000','2026-10-07 02:31:17.987000',49.51713978716028,'2026-10-07 00:31:17.010462',1,2,2),(969,'2026-10-07 02:31:17.989000','2026-10-07 02:31:17.989000',1050,'2026-10-07 00:31:17.010462',2,3,3),(970,'2026-10-07 02:31:17.989000','2026-10-07 02:31:17.989000',-170,'2026-10-07 00:31:17.010462',3,1,4),(971,'2026-10-07 02:31:17.990000','2026-10-07 02:31:17.990000',1015.3840319695407,'2026-10-07 00:31:17.010462',3,6,5),(972,'2026-10-07 02:31:17.991000','2026-10-07 02:31:17.991000',41.21609968270664,'2026-10-07 00:31:17.010462',2,4,6),(973,'2026-10-07 02:31:17.992000','2026-10-07 02:31:17.992000',3.5,'2026-10-07 00:31:17.010462',2,5,7),(974,'2026-10-07 02:31:17.993000','2026-10-07 02:31:17.993000',22.564642473395036,'2026-10-07 00:31:17.010462',5,1,9),(975,'2026-10-07 02:31:17.993000','2026-10-07 02:31:17.993000',49.60530497001442,'2026-10-07 00:31:17.010462',5,2,10),(976,'2026-10-07 02:31:17.994000','2026-10-07 02:31:17.994000',45,'2026-10-07 00:31:17.010462',6,6,11),(977,'2026-10-07 02:31:17.995000','2026-10-07 02:31:17.995000',75,'2026-10-07 00:31:17.010462',6,4,12),(978,'2026-10-07 02:31:17.996000','2026-10-07 02:31:17.996000',20.573884872719617,'2026-10-07 00:31:17.010462',7,1,13),(979,'2026-10-07 02:31:17.996000','2026-10-07 02:31:17.996000',940,'2026-10-07 00:31:17.010462',7,6,14),(980,'2026-10-07 02:31:17.998000','2026-10-07 02:31:17.998000',28.5,'2026-10-07 00:31:17.010462',8,1,15),(981,'2026-10-07 02:31:17.998000','2026-10-07 02:31:17.998000',1100,'2026-10-07 00:31:17.010462',8,3,16),(982,'2026-10-07 02:31:17.999000','2026-10-07 02:31:17.999000',26.5,'2026-10-07 00:31:17.010462',9,1,17),(983,'2026-10-07 02:31:18.000000','2026-10-07 02:31:18.000000',38.5,'2026-10-07 00:31:17.010462',10,1,18),(984,'2026-10-07 02:31:18.001000','2026-10-07 02:31:18.001000',120,'2026-10-07 00:31:17.010462',11,4,19),(985,'2026-10-07 02:31:18.002000','2026-10-07 02:31:18.002000',850,'2026-10-07 00:31:17.010462',11,6,20),(986,'2026-10-07 02:31:18.003000','2026-10-07 02:31:18.003000',3.2,'2026-10-07 00:31:17.010462',12,5,21),(987,'2026-10-07 02:31:18.004000','2026-10-07 02:31:18.004000',20.960530497001443,'2026-10-07 00:31:17.010462',12,1,22),(988,'2026-10-07 02:31:18.005000','2026-10-07 02:31:18.005000',8.5,'2026-10-07 01:31:17.010462',1,1,1),(989,'2026-10-07 02:31:18.005000','2026-10-07 02:31:18.005000',47.364161653290715,'2026-10-07 01:31:17.010462',1,2,2),(990,'2026-10-07 02:31:18.007000','2026-10-07 02:31:18.007000',1050,'2026-10-07 01:31:17.010462',2,3,3),(991,'2026-10-07 02:31:18.008000','2026-10-07 02:31:18.008000',-170,'2026-10-07 01:31:17.010462',3,1,4),(992,'2026-10-07 02:31:18.008000','2026-10-07 02:31:18.008000',1014.1980009997619,'2026-10-07 01:31:17.010462',3,6,5),(993,'2026-10-07 02:31:18.009000','2026-10-07 02:31:18.009000',44.00447102352677,'2026-10-07 01:31:17.010462',2,4,6),(994,'2026-10-07 02:31:18.010000','2026-10-07 02:31:18.010000',3.5,'2026-10-07 01:31:17.010462',2,5,7),(995,'2026-10-07 02:31:18.011000','2026-10-07 02:31:18.011000',22.29552020666134,'2026-10-07 01:31:17.010462',5,1,9),(996,'2026-10-07 02:31:18.012000','2026-10-07 02:31:18.012000',49.90033288920621,'2026-10-07 01:31:17.010462',5,2,10),(997,'2026-10-07 02:31:18.012000','2026-10-07 02:31:18.012000',45,'2026-10-07 01:31:17.010462',6,6,11),(998,'2026-10-07 02:31:18.013000','2026-10-07 02:31:18.013000',75,'2026-10-07 01:31:17.010462',6,4,12),(999,'2026-10-07 02:31:18.014000','2026-10-07 02:31:18.014000',20.31153467384692,'2026-10-07 01:31:17.010462',7,1,13),(1000,'2026-10-07 02:31:18.015000','2026-10-07 02:31:18.015000',940,'2026-10-07 01:31:17.010462',7,6,14),(1001,'2026-10-07 02:31:18.016000','2026-10-07 02:31:18.016000',28.5,'2026-10-07 01:31:17.010462',8,1,15),(1002,'2026-10-07 02:31:18.017000','2026-10-07 02:31:18.017000',1100,'2026-10-07 01:31:17.010462',8,3,16),(1003,'2026-10-07 02:31:18.018000','2026-10-07 02:31:18.018000',26.5,'2026-10-07 01:31:17.010462',9,1,17),(1004,'2026-10-07 02:31:18.018000','2026-10-07 02:31:18.018000',38.5,'2026-10-07 01:31:17.010462',10,1,18),(1005,'2026-10-07 02:31:18.019000','2026-10-07 02:31:18.019000',120,'2026-10-07 01:31:17.010462',11,4,19),(1006,'2026-10-07 02:31:18.019000','2026-10-07 02:31:18.019000',850,'2026-10-07 01:31:17.010462',11,6,20),(1007,'2026-10-07 02:31:18.020000','2026-10-07 02:31:18.020000',3.2,'2026-10-07 01:31:17.010462',12,5,21),(1008,'2026-10-07 02:31:18.021000','2026-10-07 02:31:18.021000',20.99003328892062,'2026-10-07 01:31:17.010462',12,1,22),(1009,'2026-10-07 02:31:18.022000','2026-10-07 02:31:18.022000',8.5,'2026-10-07 02:31:17.010462',1,1,1),(1010,'2026-10-07 02:31:18.022000','2026-10-07 02:31:18.022000',45,'2026-10-07 02:31:17.010462',1,2,2),(1011,'2026-10-07 02:31:18.023000','2026-10-07 02:31:18.023000',1050,'2026-10-07 02:31:17.010462',2,3,3),(1012,'2026-10-07 02:31:18.024000','2026-10-07 02:31:18.024000',-170,'2026-10-07 02:31:17.010462',3,1,4),(1013,'2026-10-07 02:31:18.025000','2026-10-07 02:31:18.025000',1013,'2026-10-07 02:31:17.010462',3,6,5),(1014,'2026-10-07 02:31:18.028000','2026-10-07 02:31:18.028000',45,'2026-10-07 02:31:17.010462',2,4,6),(1015,'2026-10-07 02:31:18.029000','2026-10-07 02:31:18.029000',3.5,'2026-10-07 02:31:17.010462',2,5,7),(1016,'2026-10-07 02:31:18.030000','2026-10-07 02:31:18.030000',22,'2026-10-07 02:31:17.010462',5,1,9),(1017,'2026-10-07 02:31:18.031000','2026-10-07 02:31:18.031000',50,'2026-10-07 02:31:17.010462',5,2,10),(1018,'2026-10-07 02:31:18.032000','2026-10-07 02:31:18.032000',45,'2026-10-07 02:31:17.010462',6,6,11),(1019,'2026-10-07 02:31:18.033000','2026-10-07 02:31:18.033000',75,'2026-10-07 02:31:17.010462',6,4,12),(1020,'2026-10-07 02:31:18.034000','2026-10-07 02:31:18.034000',20,'2026-10-07 02:31:17.010462',7,1,13),(1021,'2026-10-07 02:31:18.035000','2026-10-07 02:31:18.035000',940,'2026-10-07 02:31:17.010462',7,6,14),(1022,'2026-10-07 02:31:18.036000','2026-10-07 02:31:18.036000',28.5,'2026-10-07 02:31:17.010462',8,1,15),(1023,'2026-10-07 02:31:18.037000','2026-10-07 02:31:18.037000',1100,'2026-10-07 02:31:17.010462',8,3,16),(1024,'2026-10-07 02:31:18.038000','2026-10-07 02:31:18.038000',26.5,'2026-10-07 02:31:17.010462',9,1,17),(1025,'2026-10-07 02:31:18.038000','2026-10-07 02:31:18.038000',38.5,'2026-10-07 02:31:17.010462',10,1,18),(1026,'2026-10-07 02:31:18.039000','2026-10-07 02:31:18.039000',120,'2026-10-07 02:31:17.010462',11,4,19),(1027,'2026-10-07 02:31:18.040000','2026-10-07 02:31:18.040000',850,'2026-10-07 02:31:17.010462',11,6,20),(1028,'2026-10-07 02:31:18.041000','2026-10-07 02:31:18.041000',3.2,'2026-10-07 02:31:17.010462',12,5,21),(1029,'2026-10-07 02:31:18.041000','2026-10-07 02:31:18.041000',21,'2026-10-07 02:31:17.010462',12,1,22);
/*!40000 ALTER TABLE `sensor_readings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_units`
--

DROP TABLE IF EXISTS `storage_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_units` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `loc_rack` varchar(255) DEFAULT NULL,
  `loc_room` varchar(255) DEFAULT NULL,
  `loc_shelf` varchar(255) DEFAULT NULL,
  `threshold_max` double DEFAULT NULL,
  `threshold_min` double DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `laboratory_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7uku6f36yl6vtgkc1lt58jjcj` (`laboratory_id`),
  CONSTRAINT `FK7uku6f36yl6vtgkc1lt58jjcj` FOREIGN KEY (`laboratory_id`) REFERENCES `laboratories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_units`
--

LOCK TABLES `storage_units` WRITE;
/*!40000 ALTER TABLE `storage_units` DISABLE KEYS */;
/*!40000 ALTER TABLE `storage_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_iam_roles`
--

DROP TABLE IF EXISTS `user_iam_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_iam_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKcj44n194m9ai7ut87duww19r5` (`role_id`),
  CONSTRAINT `FKc6tu40dmeq2yokf995uy9p38j` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKcj44n194m9ai7ut87duww19r5` FOREIGN KEY (`role_id`) REFERENCES `iam_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_iam_roles`
--

LOCK TABLES `user_iam_roles` WRITE;
/*!40000 ALTER TABLE `user_iam_roles` DISABLE KEYS */;
INSERT INTO `user_iam_roles` VALUES (2,3),(2,4);
/*!40000 ALTER TABLE `user_iam_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profiles`
--

DROP TABLE IF EXISTS `user_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profiles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `access_tier` varchar(255) DEFAULT NULL,
  `auto_generate_shift_report` bit(1) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `default_start_shift` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `employee_id` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `professional_title` varchar(255) DEFAULT NULL,
  `shift_duration` varchar(255) DEFAULT NULL,
  `system_state` varchar(255) DEFAULT NULL,
  `workspace_id` bigint NOT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdqltqkaw58m11jbov0udx8xqg` (`email`),
  UNIQUE KEY `UKfoa3s5kqpu66ylh45gr0qgop7` (`employee_id`),
  KEY `FKdcc9vwbwo1ij7wemxpko8edyr` (`role_id`),
  CONSTRAINT `FKdcc9vwbwo1ij7wemxpko8edyr` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profiles`
--

LOCK TABLES `user_profiles` WRITE;
/*!40000 ALTER TABLE `user_profiles` DISABLE KEYS */;
INSERT INTO `user_profiles` VALUES (1,'2026-10-07 02:31:16.601000','2026-10-07 02:31:16.641000','Tier 2',_binary '\0',NULL,'08:00 AM','admin@safelab.com','EMP-00001','SafeLab Operator',NULL,NULL,'Lab Operator','8 Hours','Active',1,2),(2,'2026-10-07 02:49:00.718000','2026-10-07 02:49:00.718000','Tier 1',_binary '\0',NULL,'08:00 AM','farmaceutico.calidad@safelab.pe','EMP-F83DD878','Farmaceutico',NULL,NULL,'Administrator','8 Hours','Active',2,1);
/*!40000 ALTER TABLE `user_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_workspace_accesses`
--

DROP TABLE IF EXISTS `user_workspace_accesses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_workspace_accesses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `role_id` bigint NOT NULL,
  `user_profile_id` bigint NOT NULL,
  `workspace_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiujyuv65wlxyfjsgkut6tmu0g` (`role_id`),
  KEY `FKlblh5os20vmwjh2xk5na2rxg7` (`user_profile_id`),
  KEY `FKhcsurl3tqlrgjr5447jx8guji` (`workspace_id`),
  CONSTRAINT `FKhcsurl3tqlrgjr5447jx8guji` FOREIGN KEY (`workspace_id`) REFERENCES `workspaces` (`id`),
  CONSTRAINT `FKiujyuv65wlxyfjsgkut6tmu0g` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKlblh5os20vmwjh2xk5na2rxg7` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_workspace_accesses`
--

LOCK TABLES `user_workspace_accesses` WRITE;
/*!40000 ALTER TABLE `user_workspace_accesses` DISABLE KEYS */;
INSERT INTO `user_workspace_accesses` VALUES (1,'2026-10-07 02:31:16.605000','2026-10-07 02:31:16.605000',1,1,1),(2,'2026-10-07 02:49:00.725000','2026-10-07 02:49:00.725000',1,2,2);
/*!40000 ALTER TABLE `user_workspace_accesses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `confirmation_code` varchar(10) DEFAULT NULL,
  `confirmed` bit(1) NOT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `password` varchar(120) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-10-07 02:31:16.558000','2026-10-07 02:31:16.558000',NULL,_binary '','admin@safelab.com','SafeLab Operator','$2a$10$UYoEvbn1dzKCyrsus3O0ROuWWelTQwWu.rslj7/OpPSiS5egDAx4S'),(2,'2026-10-07 02:49:00.692000','2026-10-07 02:49:00.692000',NULL,_binary '','farmaceutico.calidad@safelab.pe','Farmaceutico','$2a$10$gY/d7dJHb4K/gkQy6cqgseGDDcC3i5YluxCj40PaKYXqMfcGppDtq');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workspaces`
--

DROP TABLE IF EXISTS `workspaces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workspaces` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqwe1kdanlhwy3c46d7qlhudv4` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workspaces`
--

LOCK TABLES `workspaces` WRITE;
/*!40000 ALTER TABLE `workspaces` DISABLE KEYS */;
INSERT INTO `workspaces` VALUES (1,'2026-10-07 02:31:16.587000','2026-10-07 02:31:16.587000','604b0545-6bbe-4767-82d4-a24307522c6b','Workspace of SafeLab Operator'),(2,'2026-10-07 02:49:00.713000','2026-10-07 02:49:00.713000','6d94bb74-e544-4bbc-9af0-f7978afb421d','Workspace of Farmaceutico');
/*!40000 ALTER TABLE `workspaces` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-07  4:12:22
