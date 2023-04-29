DROP DATABASE if exists `csc8019_team15`;
CREATE DATABASE `csc8019_team15`;

USE `csc8019_team15`;

DROP TABLE IF EXISTS `cuisine`;
CREATE TABLE `cuisine` (
     `id` BIGINT UNSIGNED AUTO_INCREMENT,
    `name` VARCHAR(50),
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `address_line1` VARCHAR(255) NOT NULL,
  `address_line2` VARCHAR(255),
  `address_line3` VARCHAR(255),
  `average_cost` DOUBLE,
  `phone_number` VARCHAR(255),
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `rating` DOUBLE,
  `cuisine_id` BIGINT UNSIGNED,
  `images_link` VARCHAR(255) NOT NULL,
  `menu` VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`cuisine_id`) REFERENCES `cuisine` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `restaurant_operation_hours`;
CREATE TABLE `restaurant_operation_hours` (
  `id` INT UNSIGNED AUTO_INCREMENT,
  `day_of_week` INT NOT NULL,
  `opening_time` TIME,
  `closing_time` TIME,
  `restaurant_id` BIGINT UNSIGNED,
  PRIMARY KEY(`id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` CHAR(68),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
	`id` BIGINT AUTO_INCREMENT,
    `comment` VARCHAR(255),
    `rating` INT,
    `user_id` BIGINT UNSIGNED,
    `restaurant_id` BIGINT UNSIGNED,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL, 
    FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


	




