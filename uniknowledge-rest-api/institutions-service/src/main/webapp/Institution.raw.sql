
CREATE TABLE `Institution` (
  `id` bigint(20) NOT NULL,
  `contactEmail` varchar(255) DEFAULT NULL,
  `institution_name` varchar(100) DEFAULT NULL,
  `logoPictureURL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `Institution` (`id`, `contactEmail`, `institution_name`, `logoPictureURL`) VALUES
(1, 'contact@name-1.com', 'name-1', 'logoPictureURL'),
(2, 'contact@name-2.com', 'name-2', 'logoPictureURL'),
(3, 'contact@name-3.com', 'name-3', 'logoPictureURL'),
(4, 'contact@name-4.com', 'name-4', 'logoPictureURL');

