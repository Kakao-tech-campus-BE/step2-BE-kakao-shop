CREATE SCHEMA IF NOT EXISTS `kakao` DEFAULT CHARACTER SET utf8mb4;

USE `kakao`;


DROP TABLE IF EXISTS `user_tb`;
CREATE TABLE `user_tb` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `email` varchar(100) NOT NULL,
                           `password` varchar(256) NOT NULL,
                           `username` varchar(45) NOT NULL,
                           `roles` varchar(30) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



DROP TABLE IF EXISTS `product_tb`;
CREATE TABLE `product_tb` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `product_name` varchar(100) NOT NULL,
                              `description` varchar(1000) DEFAULT NULL,
                              `image` varchar(500) DEFAULT NULL,
                              `price` int(11) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



DROP TABLE IF EXISTS `cart_tb`;
CREATE TABLE `cart_tb` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `user_id` int(11) NOT NULL,
                                `option_id` int(11) NOT NULL,
                                `quantity` int(11) NOT NULL,
                                `price` int(11) NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uk_cart_option_user` (`option_id`, `user_id`),
                                KEY `cart_user_id_idx` (`user_id`),
                                KEY `cart_option_id_idx` (`option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



DROP TABLE IF EXISTS `option_tb`;
CREATE TABLE `option_tb` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `product_id` int(11) DEFAULT NULL,
                             `option_name` varchar(100) NOT NULL,
                             `price` int(11) NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `option_product_id_idx` (`product_id`),
                             CONSTRAINT `option_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



DROP TABLE IF EXISTS `order_tb`;
CREATE TABLE `order_tb` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `user_id` int(11) NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `order_user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



DROP TABLE IF EXISTS `item_tb`;
CREATE TABLE `item_tb` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `option_id` int(11) NOT NULL,
                                 `quantity` int(11) NOT NULL,
                                 `price` int(11) NOT NULL,
                                 `order_id` int(11) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `item_option_id_idx` (`option_id`),
                                 CONSTRAINT `item_option_id` FOREIGN KEY (`option_id`) REFERENCES `option_tb` (`id`),
                                 KEY `item_order_id_idx` (`order_id`),
                                 CONSTRAINT `item_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_tb` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;