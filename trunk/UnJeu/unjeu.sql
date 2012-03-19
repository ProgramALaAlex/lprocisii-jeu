-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Lun 19 Mars 2012 à 13:53
-- Version du serveur: 5.5.16
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `unjeu`
--

-- --------------------------------------------------------

--
-- Structure de la table `apparence`
--

CREATE TABLE IF NOT EXISTS `apparence` (
  `idApparence` int(11) NOT NULL AUTO_INCREMENT,
  `sprite` varchar(255) NOT NULL,
  PRIMARY KEY (`idApparence`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `assoc_amis_joueur_joueur`
--

CREATE TABLE IF NOT EXISTS `assoc_amis_joueur_joueur` (
  `idJoueur1` int(11) NOT NULL,
  `idJoueur2` int(11) NOT NULL,
  UNIQUE KEY `amis` (`idJoueur1`,`idJoueur2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `assoc_drop_monstre_objet`
--

CREATE TABLE IF NOT EXISTS `assoc_drop_monstre_objet` (
  `idMonstre` int(11) NOT NULL,
  `idObjet` int(11) NOT NULL,
  `pourcentage` int(11) NOT NULL,
  UNIQUE KEY `assoc` (`idMonstre`,`idObjet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `guilde`
--

CREATE TABLE IF NOT EXISTS `guilde` (
  `idGuilde` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `totalMonstres` int(11) NOT NULL,
  PRIMARY KEY (`idGuilde`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `inventaire`
--

CREATE TABLE IF NOT EXISTS `inventaire` (
  `idJoueur` int(11) NOT NULL,
  `slot1_idObjet` int(11) NOT NULL,
  `slot1_qte` int(11) NOT NULL,
  `slot2_idObjet` int(11) NOT NULL,
  `slot2_qte` int(11) NOT NULL,
  `slot3_idObjet` int(11) NOT NULL,
  `slot3_qte` int(11) NOT NULL,
  `slot4_idObjet` int(11) NOT NULL,
  `slot4_qte` int(11) NOT NULL,
  `slot5_idObjet` int(11) NOT NULL,
  `slot5_qte` int(11) NOT NULL,
  `slot6_idObjet` int(11) NOT NULL,
  `slot6_qte` int(11) NOT NULL,
  `slot7_idObjet` int(11) NOT NULL,
  `slot7_qte` int(11) NOT NULL,
  `slot8_idObjet` int(11) NOT NULL,
  `slot8_qte` int(11) NOT NULL,
  `slot9_idObjet` int(11) NOT NULL,
  `slot9_qte` int(11) NOT NULL,
  `slot10_idObjet` int(11) NOT NULL,
  `slot10_qte` int(11) NOT NULL,
  `slot11_idObjet` int(11) NOT NULL,
  `slot11_qte` int(11) NOT NULL,
  `slot12_idObjet` int(11) NOT NULL,
  `slot12_qte` int(11) NOT NULL,
  `slot13_idObjet` int(11) NOT NULL,
  `slot13_qte` int(11) NOT NULL,
  `slot14_idObjet` int(11) NOT NULL,
  `slot14_qte` int(11) NOT NULL,
  `slot15_idObjet` int(11) NOT NULL,
  `slot15_qte` int(11) NOT NULL,
  `slot16_idObjet` int(11) NOT NULL,
  `slot16_qte` int(11) NOT NULL,
  PRIMARY KEY (`idJoueur`),
  UNIQUE KEY `idJoueur` (`idJoueur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

CREATE TABLE IF NOT EXISTS `joueur` (
  `idJoueur` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `dateInscription` date NOT NULL,
  `attaque` float NOT NULL,
  `vitesse` float NOT NULL,
  `pvMax` int(11) NOT NULL,
  `pvActuels` int(11) NOT NULL,
  `totalCombats` int(11) NOT NULL,
  `totalMonstres` int(11) NOT NULL,
  `dernierX` int(11) NOT NULL,
  `dernierY` int(11) NOT NULL,
  `idMap` int(11) NOT NULL,
  `idArme` int(11) NOT NULL,
  `idArmure` int(11) NOT NULL,
  `idApparance` int(11) NOT NULL,
  `newsletter` int(11) NOT NULL,
  PRIMARY KEY (`idJoueur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `monstre`
--

CREATE TABLE IF NOT EXISTS `monstre` (
  `idMonstre` int(11) NOT NULL AUTO_INCREMENT,
  `attaque` int(11) NOT NULL,
  `pvMax` int(11) NOT NULL,
  `vitesse` float NOT NULL,
  `sprite` varchar(255) NOT NULL,
  PRIMARY KEY (`idMonstre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `objet`
--

CREATE TABLE IF NOT EXISTS `objet` (
  `idObjet` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `valeur` int(11) NOT NULL,
  `idTypeObjet` int(11) NOT NULL,
  PRIMARY KEY (`idObjet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `typeobjet`
--

CREATE TABLE IF NOT EXISTS `typeobjet` (
  `idType` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
