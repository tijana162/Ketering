-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 26, 2024 at 08:37 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ketering`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategorije`
--

CREATE TABLE `kategorije` (
  `KategorijaID` int(11) NOT NULL,
  `NazivKategorije` varchar(30) NOT NULL,
  `Program` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategorije`
--

INSERT INTO `kategorije` (`KategorijaID`, `NazivKategorije`, `Program`) VALUES
(0, 'Izbrisana', 'Rezervisan'),
(1, 'torte', 'slatki'),
(2, 'kolaci', 'slatki'),
(3, 'slano', 'slani');

--
-- Triggers `kategorije`
--
DELIMITER $$
CREATE TRIGGER `IzbrisiKategorijeProizvoda` BEFORE DELETE ON `kategorije` FOR EACH ROW UPDATE `proizvodi` SET `KategorijaID` = 0 WHERE OLD.`KategorijaID` = proizvodi.KategorijaID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `KorisnickoIme` varchar(50) NOT NULL,
  `Ime` varchar(30) NOT NULL,
  `Prezime` varchar(30) NOT NULL,
  `Adresa` varchar(30) NOT NULL,
  `Poeni` int(11) NOT NULL,
  `PasswordHash` varchar(50) NOT NULL,
  `RolaID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`KorisnickoIme`, `Ime`, `Prezime`, `Adresa`, `Poeni`, `PasswordHash`, `RolaID`) VALUES
('izbrisani', 'Rezervisan', 'Korisnik', 'Nema', 0, 'Nema', 1),
('janko', 'janko', 'jankovic', 'gt', 3, '81dc9bdb52d04dc20036dbd8313ed055', 2),
('milica', 'milica', 'milic', 'Kralja Petra', 4, '202cb962ac59075b964b07152d234b70', 3),
('pera', 'pera', 'peric', 'bg', 5, '202cb962ac59075b964b07152d234b70', 3),
('tijana', 'tijana', 'bog', 'bg', 0, '4a7d1ed414474e4033ac29ccb8653d9b', 1);

--
-- Triggers `korisnici`
--
DELIMITER $$
CREATE TRIGGER `IzbrisiNarudzbe` BEFORE DELETE ON `korisnici` FOR EACH ROW UPDATE narudzbine SET narudzbine.KorisnickoIme = 'izbrisani' WHERE narudzbine.KorisnickoIme = OLD.KorisnickoIme
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `narudzbine`
--

CREATE TABLE `narudzbine` (
  `NarudzbinaID` int(11) NOT NULL,
  `KorisnickoIme` varchar(50) NOT NULL,
  `DatumKreiranja` date NOT NULL,
  `DatumOstvarivanja` date DEFAULT NULL,
  `Status` int(11) NOT NULL,
  `UkupnaCena` int(11) NOT NULL,
  `Popust` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `narudzbine`
--

INSERT INTO `narudzbine` (`NarudzbinaID`, `KorisnickoIme`, `DatumKreiranja`, `DatumOstvarivanja`, `Status`, `UkupnaCena`, `Popust`) VALUES
(73, 'milica', '2024-06-26', '2024-06-26', 1, 4000, 0),
(74, 'pera', '2024-06-26', NULL, 0, 2600, 0);

-- --------------------------------------------------------

--
-- Table structure for table `proizvodi`
--

CREATE TABLE `proizvodi` (
  `ProizvodID` int(11) NOT NULL,
  `NazivProizvoda` varchar(30) NOT NULL,
  `Opis` varchar(256) NOT NULL,
  `Slika` varchar(50) NOT NULL,
  `CenaPoPorciji` int(11) NOT NULL,
  `KategorijaID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `proizvodi`
--

INSERT INTO `proizvodi` (`ProizvodID`, `NazivProizvoda`, `Opis`, `Slika`, `CenaPoPorciji`, `KategorijaID`) VALUES
(48, 'Bajadere', 'slatki kolaci', 'bajadere.jpg', 600, 2),
(49, 'Vocna torta', 'Torta sa jagodama', 'torta.jpg', 2000, 1),
(50, 'Mafini', 'Ukus po zelji', 'mafini.jpg', 600, 2),
(51, 'Pizza', '42 cm', 'pizza.jpg', 1000, 3),
(52, 'Suhomesnato', 'Serviranje suhomesnatog proizvoda na kilo', 'suhomesnato.jpg', 2000, 3),
(53, 'Raffaelo', 'Kuglice', 'raffaello.jpg', 700, 2),
(54, 'Bombice', 'cokoladne', 'bombice.jpg', 700, 2),
(55, 'Cokoladna torta', 'coko', 'coko.jpg', 2000, 1),
(56, 'Riba', 'Sve vrste', 'riba.jpg', 1000, 3),
(57, 'Rostilj', 'na kilo', 'rostilj.jpg', 1500, 3),
(58, 'Rolovasna piletina', 'na kilo', 'piletina.jpg', 800, 3);

--
-- Triggers `proizvodi`
--
DELIMITER $$
CREATE TRIGGER `IzbrisiStavke` BEFORE DELETE ON `proizvodi` FOR EACH ROW UPDATE stavkenarudzbine SET `ProizvodID` = 0 WHERE OLD.`ProizvodID` = stavkenarudzbine.ProizvodID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `RolaID` int(11) NOT NULL,
  `NazivRole` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`RolaID`, `NazivRole`) VALUES
(1, 'Administrator'),
(2, 'Menadzer'),
(3, 'Klijent');

-- --------------------------------------------------------

--
-- Table structure for table `stavkenarudzbine`
--

CREATE TABLE `stavkenarudzbine` (
  `ProizvodID` int(11) NOT NULL,
  `NarudzbinaID` int(11) NOT NULL,
  `Kolicina` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stavkenarudzbine`
--

INSERT INTO `stavkenarudzbine` (`ProizvodID`, `NarudzbinaID`, `Kolicina`) VALUES
(48, 74, 1),
(52, 73, 2),
(55, 74, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategorije`
--
ALTER TABLE `kategorije`
  ADD PRIMARY KEY (`KategorijaID`);

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`KorisnickoIme`),
  ADD KEY `FK_Korisnik_Rola` (`RolaID`);

--
-- Indexes for table `narudzbine`
--
ALTER TABLE `narudzbine`
  ADD PRIMARY KEY (`NarudzbinaID`) USING BTREE,
  ADD KEY `FK_Narudzbina_Korisnik` (`KorisnickoIme`);

--
-- Indexes for table `proizvodi`
--
ALTER TABLE `proizvodi`
  ADD PRIMARY KEY (`ProizvodID`),
  ADD KEY `FK_Proizvodi_Kategorije` (`KategorijaID`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`RolaID`);

--
-- Indexes for table `stavkenarudzbine`
--
ALTER TABLE `stavkenarudzbine`
  ADD PRIMARY KEY (`ProizvodID`,`NarudzbinaID`) USING BTREE,
  ADD KEY `FK_Stavke_Narudzbe` (`NarudzbinaID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategorije`
--
ALTER TABLE `kategorije`
  MODIFY `KategorijaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `narudzbine`
--
ALTER TABLE `narudzbine`
  MODIFY `NarudzbinaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- AUTO_INCREMENT for table `proizvodi`
--
ALTER TABLE `proizvodi`
  MODIFY `ProizvodID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `RolaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD CONSTRAINT `FK_Korisnik_Rola` FOREIGN KEY (`RolaID`) REFERENCES `role` (`RolaID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `narudzbine`
--
ALTER TABLE `narudzbine`
  ADD CONSTRAINT `FK_Narudzbina_Korisnik` FOREIGN KEY (`KorisnickoIme`) REFERENCES `korisnici` (`KorisnickoIme`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `proizvodi`
--
ALTER TABLE `proizvodi`
  ADD CONSTRAINT `FK_Proizvodi_Kategorije` FOREIGN KEY (`KategorijaID`) REFERENCES `kategorije` (`KategorijaID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `stavkenarudzbine`
--
ALTER TABLE `stavkenarudzbine`
  ADD CONSTRAINT `FK_Stavke_Narudzbe` FOREIGN KEY (`NarudzbinaID`) REFERENCES `narudzbine` (`NarudzbinaID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Stavke_Proizvodi` FOREIGN KEY (`ProizvodID`) REFERENCES `proizvodi` (`ProizvodID`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
