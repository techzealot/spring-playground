CREATE TABLE `users`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) DEFAULT NULL,
    `age`  tinyint(4) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO users(`name`, age)
values ('tom', 15);