-- phpMyAdmin SQL Dump
-- version 4.1.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 19, 2014 at 11:38 AM
-- Server version: 5.5.35
-- PHP Version: 5.5.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `user@easyproperty.tk`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_info`
--

CREATE TABLE IF NOT EXISTS `account_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Address` text NOT NULL,
  `Number` varchar(20) NOT NULL,
  `Rating` float NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Username` (`Username`,`Email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `account_info`
--

INSERT INTO `account_info` (`ID`, `Name`, `Username`, `Password`, `Email`, `Address`, `Number`, `Rating`) VALUES
(1, 'Fahad Ali', 'fab', 'fab123', 'fahadalibhatti@hotmail.com', 'Office # 2, Cantt Complex, Cantt Bazaar, Malir Cantt', '03422764775', 3.83333),
(21, 'Dawer Rafi', 'dawer', 'daw123', 'dawer@gmail.com', 'Shop 9, Shaheen Heights, Numaish Churangi, Karachi', '03456336974', 3.5),
(22, 'asad', 'asad', 'asad', 'a.rehman.uni@gmail.com', 'A-54 erum garden Block 13-D gulshan e iqbal', '03323234121', 4.5),
(23, 'Seehum', 'Seehum', 'Seehum', 'ice_code@hotmail.com', 'Malir Cantt , Cantt bazaaar ', '0333572628', 0),
(24, 'Uzair', 'Uzair', 'Uzair', 'uzair_awesome@hotmail.com', 'B=39 , golimaar, machar colony', '03343614121', 0),
(25, 'Meeran', 'Meeran', 'Meeran', 'mahreen_Khan@hotmail.com', 'Wajid Suqare Baitul muqaram , road no 220', '03323448643', 0);

-- --------------------------------------------------------

--
-- Table structure for table `account_property`
--

CREATE TABLE IF NOT EXISTS `account_property` (
  `account_ID` int(10) NOT NULL,
  `property_ID` int(10) NOT NULL,
  PRIMARY KEY (`account_ID`,`property_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `photos`
--

CREATE TABLE IF NOT EXISTS `photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) NOT NULL,
  `agent_id` int(11) NOT NULL,
  `property_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `photos`
--

INSERT INTO `photos` (`id`, `url`, `agent_id`, `property_id`) VALUES
(1, 'easyproperty.tk.hostinghood.com/uploads/storage/sdcard0/WhatsApp/Media/WhatsApp Images/IMG-20140603-WA0002.jpg', 1, 3),
(4, 'easyproperty.tk.hostinghood.com/uploads/1_3_Screenshot_2014-06-07-00-00-49.png', 1, 3),
(5, 'easyproperty.tk.hostinghood.com/uploads/1_3_Screenshot_2014-06-07-00-00-49.png', 1, 3),
(8, 'easyproperty.tk.hostinghood.com/uploads/1_1_719px-WC-2014-Brasil.svg.png', 1, 1),
(9, 'easyproperty.tk.hostinghood.com/uploads/1_1_719px-WC-2014-Brasil.svg.png', 1, 1),
(11, 'easyproperty.tk.hostinghood.com/uploads/1_132_719px-WC-2014-Brasil.svg.png', 1, 132),
(12, 'easyproperty.tk.hostinghood.com/uploads/1_132_JPG.jpg', 1, 132),
(13, 'easyproperty.tk.hostinghood.com/uploads/1_127_IMG_20140616_130455.jpg', 1, 127),
(14, 'easyproperty.tk.hostinghood.com/uploads/22_134_IMG_20140616_130455.jpg', 22, 134),
(15, 'easyproperty.tk.hostinghood.com/uploads/1_131_IMG_20140609_170050.jpg', 1, 131),
(16, 'easyproperty.tk.hostinghood.com/uploads/1_127_Screenshot_2014-06-15-13-11-57.png', 1, 127),
(17, 'easyproperty.tk.hostinghood.com/uploads/1_132_Embroidered Abaya Designs 2013 (1).jpg', 1, 132),
(18, 'easyproperty.tk.hostinghood.com/uploads/1_132_264.jpg', 1, 132),
(19, 'easyproperty.tk.hostinghood.com/uploads/1_131_IMG_20140701_111740296.jpg', 1, 131),
(20, 'easyproperty.tk.hostinghood.com/uploads/1_132_Screenshot_2014-07-29-06-32-47.png', 1, 132);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `pid` int(255) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `price` int(255) NOT NULL,
  `description` text NOT NULL,
  UNIQUE KEY `pid` (`pid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`pid`, `name`, `price`, `description`) VALUES
(16, 'baqwas', 2580, 'chutyapa'),
(17, 'Treat ', 12, 'Treat');

-- --------------------------------------------------------

--
-- Table structure for table `Property_info`
--

CREATE TABLE IF NOT EXISTS `Property_info` (
  `Property_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Propert_Name` text NOT NULL,
  `No_BedRooms` int(11) NOT NULL,
  `No_Bath` int(11) NOT NULL,
  `Description` text NOT NULL,
  `type` tinyint(1) NOT NULL,
  `price` varchar(20) NOT NULL,
  `Area` text NOT NULL,
  `size` char(10) NOT NULL,
  `account_ID` int(11) NOT NULL,
  PRIMARY KEY (`Property_ID`),
  UNIQUE KEY `Property_ID` (`Property_ID`),
  KEY `account_ID` (`account_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=135 ;

--
-- Dumping data for table `Property_info`
--

INSERT INTO `Property_info` (`Property_ID`, `Propert_Name`, `No_BedRooms`, `No_Bath`, `Description`, `type`, `price`, `Area`, `size`, `account_ID`) VALUES
(1, 'Bungalow', 5, 5, 'A great house in DOHS II', 1, '19750000', 'Orangi Town', '313 sq yar', 1),
(124, 'Testing Edit Spinner', 2, 2, 'This Is A Test Value', 1, '12000', 'North Karachi', '200', 1),
(125, 'Flat', 4, 2, 'Flat for a couple', 1, '45000', 'Numaish', '210 sq ft', 21),
(126, 'banglow', 4, 4, 'very nice', 0, '7000000', 'golimaar', '250\n\n', 1),
(127, 'SD-267', 5, 5, 'House in Cantt', 0, '7655444', 'Malir Cantt', '6423se', 1),
(128, 'SD-2632', 5, 5, 'House in Cantt', 0, '7655444', 'Malir Cantt', '6423se', 21),
(129, 'SD-26322451', 5, 5, 'House in Cantt 1', 0, ' 234235', 'Malir Cantt', '6423se', 21),
(130, 'SD-1', 5, 6, 'House in Cantt 2', 1, ' 234235000', 'Malir Cantt', '562sq', 21),
(131, 'Falcon 5', 6, 6, 'House in Cantt 3', 1, '90880', 'Malir Cantt', '65sq', 1),
(132, 'Falcon 24', 1, 1, 'House in Cantt 1', 0, '10', 'Malir Cantt', '1sq', 1),
(133, 'A-57, block 13-D , Erum Garden , Gulshan E Iqbal', 1, 4, 'Luxury Appartments , Low cost', 1, '30,00000', 'Lyari', '220 sq yar', 22),
(134, 'viwe lake', 5, 3, 'halaat theek hain yahan k', 0, '790000', 'mauripur', '550 sq yar', 22);

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE IF NOT EXISTS `reviews` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `review_text` varchar(500) NOT NULL,
  `rating` int(11) NOT NULL,
  `agent_review_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=44 ;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`ID`, `review_text`, `rating`, `agent_review_ID`) VALUES
(1, 'A good agent', 4, 1),
(35, 'He is very cooperative', 4, 21),
(36, 'he is best\n', 3, 21),
(37, 'lelz', 3, 1),
(38, 'dhakkan', 1, 1),
(39, 'good', 5, 1),
(40, 'beat\n', 5, 1),
(41, 'ced', 5, 1),
(42, 'ghee', 5, 22),
(43, 'fahd', 4, 22);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
