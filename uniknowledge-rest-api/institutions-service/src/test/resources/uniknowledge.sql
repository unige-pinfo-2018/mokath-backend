-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: uniknowledge-db:3306
-- Generation Time: May 31, 2018 at 02:37 PM
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
-- Table structure for table `Answer`
--

CREATE TABLE `Answer` (
  `id` bigint(20) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `text` text,
  `validated` bit(1) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Answer`
--

INSERT INTO `Answer` (`id`, `timestamp`, `text`, `validated`, `author_id`, `question_id`) VALUES
(1, '2018-05-29 05:36:59', 'ANSWER-1 1 1 dkasf ui. Thjdba sah ! 1 1 contactsdfklacom.', b'0', 1, 1),
(2, '2018-05-29 05:36:59', 'ANSWER-2 1 1 dkasf ui. Thjdba sah ! 1 1 contactsdfklacom.', b'0', 1, 1),
(3, '2018-05-29 05:36:59', 'ANSWER-3 1 1 dkasf ui. Thjdba sah ! 1 1 contactsdfklacom.', b'0', 2, 1),
(4, '2018-05-29 05:36:59', 'ANSWER-4 1 1 dkasf ui. Thjdba sah ! 1 1 contactsdfklacom.', b'0', 3, 1),
(5, '2018-05-29 05:37:00', 'ANSWER-5 2 2 dkasf ui. ThjdEWIWD, CEWOL UINFEOF CEWI ba sah ! 2 2 contactsdfklacom.', b'0', 1, 2),
(6, '2018-05-29 05:37:00', 'ANSWER-6 2 2 dkasf ui. ThjdEWIWD, CEWOL UINFEOF CEWI ba sah ! 2 2 contactsdfklacom.', b'0', 1, 2);

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

-- --------------------------------------------------------

--
-- Table structure for table `Institution_domains`
--

CREATE TABLE `Institution_domains` (
  `Institution_id` bigint(20) NOT NULL,
  `domains` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Institution_domains`
--

INSERT INTO `Institution_domains` (`Institution_id`, `domains`) VALUES
(1, 'tekitiA'),
(1, 'Chemistry'),
(2, 'tekitiA'),
(2, 'Chemistry'),
(3, 'tekitiA'),
(3, 'Chemistry'),
(4, 'tekitiA'),
(4, 'Chemistry');

-- --------------------------------------------------------

--
-- Table structure for table `Question`
--

CREATE TABLE `Question` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `popularity` int(11) DEFAULT NULL,
  `nb_answers` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Question`
--

INSERT INTO `Question` (`id`, `created`, `text`, `title`, `author_id`, `popularity`, `nb_answers`) VALUES
(1, '2018-05-29 05:36:57', '1 1 dkasfhr rufhlf ui. Thjdba sah ! 1 1 contactsdfklacom.', 'Title-1', 1, NULL, NULL),
(2, '2018-05-29 05:36:57', '2 2 dkasfhr rufhlf ui. Thjdba sah ! 2 2 contactsdfklacom.', 'Title-2', 2, NULL, NULL),
(4, '2018-05-29 05:36:58', '4 4 dkasfhr rufhlf ui. Thjdba sah ! 4 4 contactsdfklacom.', 'Title-4', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Question_domains`
--

CREATE TABLE `Question_domains` (
  `Question_id` bigint(20) NOT NULL,
  `domains` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Question_domains`
--

INSERT INTO `Question_domains` (`Question_id`, `domains`) VALUES
(1, 'tekitiA'),
(1, 'Chemistry'),
(1, 'truc'),
(2, 'tekitiA'),
(2, 'Chemistry'),
(2, 'truc'),
(4, 'tekitiA'),
(4, 'Chemistry'),
(4, 'truc');

-- --------------------------------------------------------

--
-- Table structure for table `Token`
--

CREATE TABLE `Token` (
  `signingKey` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Token`
--

INSERT INTO `Token` (`signingKey`, `token`, `user_id`) VALUES
('CnYBcm9tYT5vix5KktVle1cZZxPqINqc8uAOxFuINDU=', 'eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1Mjc2MTQ5NjgsImF1ZCI6IjEiLCJleHAiOjE1Mjc3MDEzNjh9.fHgKdP2n1UN0lEGxoxjeMjXfimvgM322n2H6XAO5YmI', 1);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(128) NOT NULL,
  `points_earned` bigint(20) NOT NULL,
  `profile_picture_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `institution_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `email`, `first_name`, `last_name`, `password`, `points_earned`, `profile_picture_url`, `username`, `institution_id`) VALUES
(1, 'testUser22@meail.com', 'TestFirstName22', 'TestLastName22', '10000:889Q3AWK+76FArSc62tLHw==:J5YRd0PVv3rSnZ30vJByZHVQE2j+g+l+bKG7Sat2VVam68wS0gXuLV8QkZx5JmJumGDoxGFWyRkQvuvrXRCPJQ==', 30, 'https://example.com/avatar', 'testUser22', NULL),
(2, 'testUser2@meail.com', 'TestFirstName2', 'TestLastName2', '10000:LNCIY2qLm4tow+sEri+uJQ==:Ph4vbVC2zUUojkjK7CwjegE/ZRWYzgoGL8G8ihRp6WjbYHixQzn3jxb71iTZdg0rhv/cHeSgNsUN1ClEAYsFTw==', 4, 'https://example.com/avatar', 'testUser2', 1),
(3, 'testUser3@meail.com', 'TestFirstName3', 'TestLastName3', '10000:8hIr5MWTQlaoJvkrih9ypw==:DRGGtG7gW+MNTm8gOKSu2vqYB2PeiD3jJL757ZgYsQrffcxnKQyxX+BeB+zc6zrrq129y+2wG+MIXz5gjotwUQ==', 4, 'https://example.com/avatar', 'testUser3', 2),
(4, 'testUser4@meail.com', 'TestFirstName4', 'TestLastName4', '10000:TJ3kO1avVqQSUdDOc5gCmg==:DdxUcN0G92YJCKqliQEDR/2812nmnIqql2aAuqku42xHiXQ4ajeFkiprtFp7hhF8+v+2E31blpM4Q7sKMEdWww==', 0, 'https://example.com/avatar', 'testUser4', 2),
(5, 'testUser5@meail.com', 'TestFirstName5', 'TestLastName5', '10000:Sqzm//0mx+GyHRCvq4sTrg==:+wL2sajbt7Biul7vdCzFiGrwa39MdJXgH0mQGhOCrNTznW1d+aNo4ONQQO1+kfjMtr0p946dUgvTLC7JGUDXTA==', 0, 'https://example.com/avatar', 'testUser5', 2),
(6, 'testUser6@meail.com', 'TestFirstName6', 'TestLastName6', '10000:zmhn9o71C2drJA57KAagWA==:aFjm1UP8dHXb/pBycEKmpT4LwPKTQvHks53jM0P/RVlshkGm87SAP7vjorJcfSQCi2ZUWZnn3aL3AiTp5plTkw==', 0, 'https://example.com/avatar', 'testUser6', 3),
(7, 'testUser7@meail.com', 'TestFirstName7', 'TestLastName7', '10000:9C3COrHDR0k1++shN9tDxw==:TWf36w8DKQiCcT0hZew4QRsi3Va+oHJbbGhUnPrkc+IJUtogaNI71xkLItwhRJLXreICbZyikbGhYT3j1z5EWQ==', 0, 'https://example.com/avatar', 'testUser7', 4),
(8, 'testUser8@meail.com', 'TestFirstName8', 'TestLastName8', '10000:jwjeS7+5duQElcP6CoaUJw==:P8e2e4R+l4FLIu6KzuUWFCHlnU69sol/c43OrcS+xbOYMQnOLm2NKgYr1v1XoErRiygJu8GPZU7wNraCz9cgPw==', 0, 'https://example.com/avatar', 'testUser8', 4),
(9, 'testUser9@meail.com', 'TestFirstName9', 'TestLastName9', '10000:AhG2MpQeayAl8+f/m0rLaw==:exEmNm4NUW+0Mhh7QOu/dMUzJ+8eGUCZfXN7ercq34XihFXkSa5e8HRfTRBaUgTV0tGa69LrYvQGsJRM321JVw==', 0, 'https://example.com/avatar', 'testUser9', 4);

-- --------------------------------------------------------

--
-- Table structure for table `user_follow_question`
--

CREATE TABLE `user_follow_question` (
  `user_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_follow_question`
--

INSERT INTO `user_follow_question` (`user_id`, `question_id`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_upvote_answer`
--

CREATE TABLE `user_upvote_answer` (
  `user_id` bigint(20) NOT NULL,
  `answer_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_upvote_question`
--

CREATE TABLE `user_upvote_question` (
  `user_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Answer`
--
ALTER TABLE `Answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmirbn6grnv21wquxkplqcr8rm` (`author_id`),
  ADD KEY `FKfiomvt17psxodcis3d8nmopx8` (`question_id`);

--
-- Indexes for table `Institution`
--
ALTER TABLE `Institution`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Institution_domains`
--
ALTER TABLE `Institution_domains`
  ADD KEY `FKs3vg2l21i9s89oiclkbvk7f4f` (`Institution_id`);

--
-- Indexes for table `Question`
--
ALTER TABLE `Question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9788yosql586568o45lxvkfwu` (`author_id`);

--
-- Indexes for table `Question_domains`
--
ALTER TABLE `Question_domains`
  ADD KEY `FK22meh6bphyq8muvgibxyv4kx0` (`Question_id`);

--
-- Indexes for table `Token`
--
ALTER TABLE `Token`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1hqyrbk8lwqw5xbviheff9vyx` (`institution_id`);

--
-- Indexes for table `user_follow_question`
--
ALTER TABLE `user_follow_question`
  ADD PRIMARY KEY (`user_id`,`question_id`),
  ADD KEY `FK2xxx6h7btf3y2q18t9yo13ndg` (`question_id`);

--
-- Indexes for table `user_upvote_answer`
--
ALTER TABLE `user_upvote_answer`
  ADD PRIMARY KEY (`user_id`,`answer_id`),
  ADD KEY `FK1k2fasnibke1q2ur5tmxb6wev` (`answer_id`);

--
-- Indexes for table `user_upvote_question`
--
ALTER TABLE `user_upvote_question`
  ADD PRIMARY KEY (`user_id`,`question_id`),
  ADD KEY `FKrxl94pprlmm8u5xopwrrbfuht` (`question_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Answer`
--
ALTER TABLE `Answer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Institution`
--
ALTER TABLE `Institution`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `Question`
--
ALTER TABLE `Question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Answer`
--
ALTER TABLE `Answer`
  ADD CONSTRAINT `FKfiomvt17psxodcis3d8nmopx8` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`),
  ADD CONSTRAINT `FKmirbn6grnv21wquxkplqcr8rm` FOREIGN KEY (`author_id`) REFERENCES `User` (`id`);

--
-- Constraints for table `Institution_domains`
--
ALTER TABLE `Institution_domains`
  ADD CONSTRAINT `FKs3vg2l21i9s89oiclkbvk7f4f` FOREIGN KEY (`Institution_id`) REFERENCES `Institution` (`id`);

--
-- Constraints for table `Question`
--
ALTER TABLE `Question`
  ADD CONSTRAINT `FK9788yosql586568o45lxvkfwu` FOREIGN KEY (`author_id`) REFERENCES `User` (`id`);

--
-- Constraints for table `Question_domains`
--
ALTER TABLE `Question_domains`
  ADD CONSTRAINT `FK22meh6bphyq8muvgibxyv4kx0` FOREIGN KEY (`Question_id`) REFERENCES `Question` (`id`);

--
-- Constraints for table `Token`
--
ALTER TABLE `Token`
  ADD CONSTRAINT `FKiiyr9nhulmfrvje08nvravy02` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Constraints for table `User`
--
ALTER TABLE `User`
  ADD CONSTRAINT `FK1hqyrbk8lwqw5xbviheff9vyx` FOREIGN KEY (`institution_id`) REFERENCES `Institution` (`id`);

--
-- Constraints for table `user_follow_question`
--
ALTER TABLE `user_follow_question`
  ADD CONSTRAINT `FK2xxx6h7btf3y2q18t9yo13ndg` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`),
  ADD CONSTRAINT `FK6q9fagipf9p2nbhevlv4sb07s` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Constraints for table `user_upvote_answer`
--
ALTER TABLE `user_upvote_answer`
  ADD CONSTRAINT `FK1k2fasnibke1q2ur5tmxb6wev` FOREIGN KEY (`answer_id`) REFERENCES `Answer` (`id`),
  ADD CONSTRAINT `FKbnbxdkg65k84d4axkwijqj8am` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Constraints for table `user_upvote_question`
--
ALTER TABLE `user_upvote_question`
  ADD CONSTRAINT `FKrxl94pprlmm8u5xopwrrbfuht` FOREIGN KEY (`question_id`) REFERENCES `Question` (`id`),
  ADD CONSTRAINT `FKtkkcqje1g7ntcpeyt3vo7fj79` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
