DROP IF EXISTS `sanidad`;
CREATE DATABASE `sanidad` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- sanidad.asegurado definition

CREATE TABLE `asegurado` (
  `idAsegurado` int(25) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) DEFAULT NULL,
  `edad` int(2) DEFAULT NULL,
  PRIMARY KEY (`idAsegurado`)
) ENGINE=I;

-- sanidad.visitacentro definition

CREATE TABLE `visitacentro` (
  `codigoCentro` int(5) DEFAULT NULL,
  `idAsegurado` int(25) DEFAULT NULL,
  `idVisita` int(25) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idVisita`),
  KEY `visitaCentro_FK` (`idAsegurado`),
  CONSTRAINT `visitaCentro_FK` FOREIGN KEY (`idAsegurado`) REFERENCES `asegurado` (`idAsegurado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;