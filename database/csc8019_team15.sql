
DROP DATABASE if exists `csc8019_team15`;
CREATE DATABASE `csc8019_team15`;

USE `csc8019_team15`;

-- csc8019_team15.cuisine definition
DROP TABLE IF EXISTS `cuisine`;
CREATE TABLE `cuisine` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.menu definition
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.operation_hour definition
DROP TABLE IF EXISTS `operation_hour`;
CREATE TABLE `operation_hour` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `day_of_week` varchar(9) NOT NULL,
  `opening_time` time DEFAULT NULL,
  `closing_time` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.`user` definition
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` char(68) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.restaurant definition
DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `rating` double DEFAULT NULL,
  `cuisine_id` bigint unsigned DEFAULT NULL,
  `website_link` varchar(255) DEFAULT NULL,
  `menu_link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cuisine_id` (`cuisine_id`),
  CONSTRAINT `restaurant_ibfk_1` FOREIGN KEY (`cuisine_id`) REFERENCES `cuisine` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.restaurant_menu definition
DROP TABLE IF EXISTS `restaurant_menu`;
CREATE TABLE `restaurant_menu` (
  `restaurant_id` bigint unsigned NOT NULL,
  `menu_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`restaurant_id`,`menu_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `restaurant_menu_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE CASCADE,
  CONSTRAINT `restaurant_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.restaurant_operation_hour definition
DROP TABLE IF EXISTS `restaurant_operation_hour`;
CREATE TABLE `restaurant_operation_hour` (
  `restaurant_id` bigint unsigned NOT NULL,
  `operation_hour_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`restaurant_id`,`operation_hour_id`),
  KEY `operation_hour_id` (`operation_hour_id`),
  CONSTRAINT `restaurant_operation_hour_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE CASCADE,
  CONSTRAINT `restaurant_operation_hour_ibfk_2` FOREIGN KEY (`operation_hour_id`) REFERENCES `operation_hour` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- csc8019_team15.image definition
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint unsigned DEFAULT NULL,
  `image_link` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- csc8019_team15.review definition
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `restaurant_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;