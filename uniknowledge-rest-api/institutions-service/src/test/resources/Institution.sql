-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: uniknowledge-db:3306
-- Generation Time: May 31, 2018 at 06:32 AM
-- Server version: 5.7.22
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uniknowledge`
--

-- --------------------------------------------------------

--
-- Table structure for table `Institution`
--

CREATE TABLE `Institution` (
  `id` bigint(20) NOT NULL,
  `contactEmail` varchar(255) DEFAULT NULL,
  `institution_name` varchar(100) DEFAULT NULL,
  `logoPictureURL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Institution`
--

INSERT INTO `Institution` (`id`, `contactEmail`, `institution_name`, `logoPictureURL`) VALUES
(1, 'contact@name-1.com', 'name-1', 'logoPictureURL'),
(2, 'contact@name-2.com', 'name-2', 'logoPictureURL'),
(3, 'contact@name-3.com', 'name-3', 'logoPictureURL'),
(4, 'contact@name-4.com', 'name-4', 'logoPictureURL');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Institution`
--
ALTER TABLE `Institution`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Institution`
--
ALTER TABLE `Institution`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
