CREATE TABLE `users`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) DEFAULT NULL,
    `age`  tinyint(4) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE app_user
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    age     INT NULL,
    name    VARCHAR(255) NOT NULL,
    role_id BIGINT       NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `role`
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

INSERT INTO users(`name`, age)
values ('tom', 15);

INSERT INTO role(name, description)
values ('admin', '系统管理员');

INSERT INTO app_user(name, age, role_id)
values ('tom', 18, 1);