-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Jeu 22 Mars 2012 à 02:02
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
-- Structure de la table `clef`
--

CREATE TABLE IF NOT EXISTS `clef` (
  `clef` varchar(50) NOT NULL,
  `idJoueur` int(11) NOT NULL,
  PRIMARY KEY (`clef`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `clef`
--

INSERT INTO `clef` (`clef`, `idJoueur`) VALUES
('4d464745-3942-4d4e-ad6f-41a0b67ccf59', 35),
('5139a993-c619-439d-b5f5-c16e5789fe17', 35);

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

--
-- Contenu de la table `inventaire`
--

INSERT INTO `inventaire` (`idJoueur`, `slot1_idObjet`, `slot1_qte`, `slot2_idObjet`, `slot2_qte`, `slot3_idObjet`, `slot3_qte`, `slot4_idObjet`, `slot4_qte`, `slot5_idObjet`, `slot5_qte`, `slot6_idObjet`, `slot6_qte`, `slot7_idObjet`, `slot7_qte`, `slot8_idObjet`, `slot8_qte`, `slot9_idObjet`, `slot9_qte`, `slot10_idObjet`, `slot10_qte`, `slot11_idObjet`, `slot11_qte`, `slot12_idObjet`, `slot12_qte`, `slot13_idObjet`, `slot13_qte`, `slot14_idObjet`, `slot14_qte`, `slot15_idObjet`, `slot15_qte`, `slot16_idObjet`, `slot16_qte`) VALUES
(35, -1, 0, 2, 1, -1, 0, -1, 0, 1, 1, 3, 1, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0);

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
  `groupe` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idJoueur`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=36 ;

--
-- Contenu de la table `joueur`
--

INSERT INTO `joueur` (`idJoueur`, `pseudo`, `mail`, `pass`, `dateInscription`, `attaque`, `vitesse`, `pvMax`, `pvActuels`, `totalCombats`, `totalMonstres`, `dernierX`, `dernierY`, `idMap`, `idArme`, `idArmure`, `idApparance`, `newsletter`, `groupe`) VALUES
(35, 'test', 'test@gmail.com', 'test', '2012-03-22', 150, 100, 150, 150, 0, 0, 250, 330, 1, 3, 0, 0, 0, 1);

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
-- Structure de la table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre` text NOT NULL,
  `contenu` text NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
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
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`idObjet`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `objet`
--

INSERT INTO `objet` (`idObjet`, `nom`, `valeur`, `idTypeObjet`, `url`) VALUES
(1, 'Potion', 50, 1, 'images/icone/attaque.gif'),
(2, 'Super potion', 150, 1, 'images/icone/force.gif'),
(3, 'Masse', 20, 2, 'images/icone/the.gif');

-- --------------------------------------------------------

--
-- Structure de la table `typeobjet`
--

CREATE TABLE IF NOT EXISTS `typeobjet` (
  `idType` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `typeobjet`
--

INSERT INTO `typeobjet` (`idType`, `nom`) VALUES
(1, 'potion'),
(2, 'arme'),
(3, 'armure');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
