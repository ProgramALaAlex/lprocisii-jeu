-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Dim 25 Mars 2012 à 22:59
-- Version du serveur: 5.5.20
-- Version de PHP: 5.3.10

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `apparence`
--

INSERT INTO `apparence` (`idApparence`, `sprite`) VALUES
(0, 'homme'),
(1, 'femme');

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
-- Structure de la table `blacklist`
--

CREATE TABLE IF NOT EXISTS `blacklist` (
  `idJoueur` int(11) NOT NULL,
  `raison` varchar(150) NOT NULL,
  `dateFin` date DEFAULT NULL,
  PRIMARY KEY (`idJoueur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `clef`
--

CREATE TABLE IF NOT EXISTS `clef` (
  `clef` varchar(50) NOT NULL,
  `idJoueur` int(11) NOT NULL,
  PRIMARY KEY (`idJoueur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `clef`
--

INSERT INTO `clef` (`clef`, `idJoueur`) VALUES
('f08868c8-2d32-41da-bc73-6f1ef2ec7bb9', 35);

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
  `inventaire` text NOT NULL,
  PRIMARY KEY (`idJoueur`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=36 ;

--
-- Contenu de la table `joueur`
--

INSERT INTO `joueur` (`idJoueur`, `pseudo`, `mail`, `pass`, `dateInscription`, `attaque`, `vitesse`, `pvMax`, `pvActuels`, `totalCombats`, `totalMonstres`, `dernierX`, `dernierY`, `idMap`, `idArme`, `idArmure`, `idApparance`, `newsletter`, `groupe`, `inventaire`) VALUES
(35, 'test', 'test@gmail.com', 'test', '2012-03-22', 150, 100, 150, 150, 0, 0, 250, 330, 1, 3, 0, 0, 0, 2, '');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `news`
--

INSERT INTO `news` (`id`, `titre`, `contenu`, `date`) VALUES
(3, 'Bienvenue!', 'Voilà une news très importante ! Souvenez-vous de passer à l''heure d''été :) Comment ça c''est sans rapport avec le reste du site ?', '2012-03-25');

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
