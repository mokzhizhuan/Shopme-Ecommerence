-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 20, 2022 at 04:56 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopmedb`
--
CREATE DATABASE IF NOT EXISTS `shopmedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `shopmedb`;

-- --------------------------------------------------------

--
-- Table structure for table `catergories`
--

DROP TABLE IF EXISTS `catergories`;
CREATE TABLE `catergories` (
  `id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `catergories`
--

INSERT INTO `catergories` (`id`, `name`, `parentid`) VALUES
(1, 'Computers', NULL),
(2, 'Electronics', NULL),
(3, 'Desktops', 1),
(4, 'Laptops', 1),
(5, 'Computer Components', 1),
(12, 'Central Processing Unit', 3),
(13, 'Thumbdrive', 3),
(14, 'Phone', 2),
(15, 'iPhone', 14),
(17, 'Samsung Phone', 14),
(21, 'HardDrive', 5),
(26, 'Asus', 1);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `description` varchar(4096) DEFAULT NULL,
  `cost` float NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `discount` float NOT NULL,
  `enabled` bit(1) NOT NULL,
  `height` float NOT NULL,
  `in_stock` bit(1) DEFAULT NULL,
  `length` float NOT NULL,
  `name` varchar(128) NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `weight` float NOT NULL,
  `width` float NOT NULL,
  `catergory_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `description`, `cost`, `created_time`, `discount`, `enabled`, `height`, `in_stock`, `length`, `name`, `updated_time`, `weight`, `width`, `catergory_id`) VALUES
(1, NULL, 456, '2022-07-18 17:52:54.000000', 0, b'1', 0, b'1', 0, 'Samsung Galaxy A31', '2022-07-18 17:52:54.000000', 0, 0, 17),
(2, NULL, 1056, '2022-07-18 17:56:02.000000', 0, b'1', 0, b'1', 0, 'Iphone 13 Pro MAX', '2022-07-18 17:56:02.000000', 0, 0, 15),
(3, NULL, 852, '2022-07-18 22:58:07.000000', 0, b'1', 0, b'1', 0, 'Dell Laptop', '2022-07-18 22:58:07.000000', 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `ID` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `descrption` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`ID`, `name`, `descrption`) VALUES
(1, 'Admin', 'manages everything'),
(2, 'Salesperson', 'manages products price , customers , shipping orders and sales reports'),
(3, 'Editor', 'manages catergories, brands, products, articles and menus'),
(4, 'Shipper', 'view products, view orders and update order status'),
(5, 'Assistant', 'manage questions and reviews');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `photos` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `email`, `enabled`, `first_name`, `last_name`, `password`, `photos`) VALUES
(23, 'gamerdark44@outlook.com', 1, 'Mok', 'Zhi Zhuan', '$2a$10$xCybpAmxUnbVl2p9m5uGGOatLCFnoJ.vTpZ0YneYbx7efozUtJUie', NULL),
(28, 'mateh324@gmail.com', 1, 'jasper', 'goh', '$2a$10$0xrkui7.EIsgMd18Y85sYuXz9Bfn7ExmyrlLb3xoK.HK4Up3mQWEO', NULL),
(29, 'slasher324@gmail.com', 1, 'Mike', 'Jones', '$2a$10$2n4.fd2i.L9upeq1j/4lMu3VuYPe7hDMFuuf2xY9HxJOfx/xTCbA6', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(23, 1),
(28, 2),
(28, 3),
(29, 4),
(29, 5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `catergories`
--
ALTER TABLE `catergories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_oqlcxwjudipb7vy5kwaohbspt` (`name`),
  ADD KEY `FKohiu16ouxy9a8gv5q4axyarvf` (`parentid`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  ADD KEY `FKma7q1l2dfg3lt9k2ibq6wqm6x` (`catergory_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcerhir1a9ffy` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `catergories`
--
ALTER TABLE `catergories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `catergories`
--
ALTER TABLE `catergories`
  ADD CONSTRAINT `FKohiu16ouxy9a8gv5q4axyarvf` FOREIGN KEY (`parentid`) REFERENCES `catergories` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKma7q1l2dfg3lt9k2ibq6wqm6x` FOREIGN KEY (`catergory_id`) REFERENCES `catergories` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcerhir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
