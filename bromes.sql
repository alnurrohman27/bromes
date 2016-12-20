-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2016 at 06:32 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bromes`
--

-- --------------------------------------------------------

--
-- Table structure for table `acara`
--

CREATE TABLE `acara` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `tanggal` timestamp NULL DEFAULT NULL,
  `tempat` varchar(100) DEFAULT NULL,
  `keterangan` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `acara`
--

INSERT INTO `acara` (`id`, `nama`, `tanggal`, `tempat`, `keterangan`) VALUES
(1, 'Pengajian', '2016-12-19 21:57:00', 'Balai RT', 'Pengajian di RT');

-- --------------------------------------------------------

--
-- Table structure for table `hak_akses`
--

CREATE TABLE `hak_akses` (
  `id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hak_akses`
--

INSERT INTO `hak_akses` (`id`) VALUES
('admin'),
('message'),
('smartphone'),
('user');

-- --------------------------------------------------------

--
-- Table structure for table `kirim_acara`
--

CREATE TABLE `kirim_acara` (
  `id` int(11) NOT NULL,
  `id_acara` int(11) NOT NULL,
  `no_hp` varchar(13) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kontak`
--

CREATE TABLE `kontak` (
  `no_hp` varchar(13) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `hak_akses` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kontak`
--

INSERT INTO `kontak` (`no_hp`, `nama`, `email`, `alamat`, `hak_akses`) VALUES
('081225395946', 'Adian', 'adian273645@gmail.com', 'Purworejo', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `pesan`
--

CREATE TABLE `pesan` (
  `id` int(11) NOT NULL,
  `id_acara` int(11) NOT NULL,
  `no_hp` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `versi`
--

CREATE TABLE `versi` (
  `id` int(11) NOT NULL,
  `keterangan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `versi`
--

INSERT INTO `versi` (`id`, `keterangan`) VALUES
(1, 'Versi Awal');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acara`
--
ALTER TABLE `acara`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hak_akses`
--
ALTER TABLE `hak_akses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kirim_acara`
--
ALTER TABLE `kirim_acara`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_KA` (`id_acara`),
  ADD KEY `FK_KK` (`no_hp`);

--
-- Indexes for table `kontak`
--
ALTER TABLE `kontak`
  ADD PRIMARY KEY (`no_hp`),
  ADD KEY `FK_KH` (`hak_akses`);

--
-- Indexes for table `pesan`
--
ALTER TABLE `pesan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_PA` (`id_acara`) USING BTREE,
  ADD KEY `FK_PK` (`no_hp`);

--
-- Indexes for table `versi`
--
ALTER TABLE `versi`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acara`
--
ALTER TABLE `acara`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `kirim_acara`
--
ALTER TABLE `kirim_acara`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pesan`
--
ALTER TABLE `pesan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `versi`
--
ALTER TABLE `versi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `kirim_acara`
--
ALTER TABLE `kirim_acara`
  ADD CONSTRAINT `FK_KA` FOREIGN KEY (`id_acara`) REFERENCES `acara` (`id`),
  ADD CONSTRAINT `FK_KK` FOREIGN KEY (`no_hp`) REFERENCES `kontak` (`no_hp`);

--
-- Constraints for table `kontak`
--
ALTER TABLE `kontak`
  ADD CONSTRAINT `FK_KH` FOREIGN KEY (`hak_akses`) REFERENCES `hak_akses` (`id`);

--
-- Constraints for table `pesan`
--
ALTER TABLE `pesan`
  ADD CONSTRAINT `FK_PA` FOREIGN KEY (`id_acara`) REFERENCES `acara` (`id`),
  ADD CONSTRAINT `FK_PK` FOREIGN KEY (`no_hp`) REFERENCES `kontak` (`no_hp`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
