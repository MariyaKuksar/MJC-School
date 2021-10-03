CREATE TABLE gift_certificate (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  description VARCHAR(100) NOT NULL,
  price DECIMAL NOT NULL,
  duration INT NOT NULL,
  create_date TIMESTAMP NOT NULL,
  last_update_date TIMESTAMP NOT NULL,
  `deleted` BOOLEAN DEFAULT FALSE
  );

CREATE TABLE tag (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL
  );

CREATE TABLE gift_certificate_tag_connection (
   gift_certificate_id BIGINT NOT NULL,
   tag_id BIGINT NOT NULL,
   PRIMARY KEY (`gift_certificate_id`, `tag_id`)
   );
