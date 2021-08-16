-- MySQL Script generated by MySQL Workbench
-- Thu Aug 12 20:26:30 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gift_certificates_system
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gift_certificates_system
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gift_certificates_system` DEFAULT CHARACTER SET utf8 ;
USE `gift_certificates_system` ;

-- -----------------------------------------------------
-- Table `gift_certificates_system`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `price` DECIMAL NOT NULL,
  `duration` INT NOT NULL,
  `create_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_system`.`gift_certificate_tag_connection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate_tag_connection` (
  `gift_certificate_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  PRIMARY KEY (`gift_certificate_id`, `tag_id`),
  INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id` ASC) VISIBLE,
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
    FOREIGN KEY (`gift_certificate_id`)
    REFERENCES `gift_certificates_system`.`gift_certificate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gift_certificate_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `gift_certificates_system`.`tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('1', 'Woman', 'gift certificate for women', '50', '365', '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156');
INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('2', 'Man', 'gift certificate for men', '60', '180', '2021-07-25 23:15:18', '2021-07-25 23:15:18');
INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('3', 'Child', 'gift certificate for children', '30', '50', '2021-08-04 07:56:47', '2021-08-04 07:56:47');
INSERT INTO `gift_certificates_system`.`gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('4', 'Happy Birhday', 'gift certificate', '80', '360', '2021-08-07 06:18:08', '2021-08-07 06:18:08');
INSERT INTO `gift_certificates_system`.`tag` (`id`, `name`) VALUES ('1', 'woman');
INSERT INTO `gift_certificates_system`.`tag` (`id`, `name`) VALUES ('2', 'man');
INSERT INTO `gift_certificates_system`.`tag` (`id`, `name`) VALUES ('3', 'relax');
INSERT INTO `gift_certificates_system`.`gift_certificate_tag_connection` (`gift_certificate_id`, `tag_id`) VALUES ('1', '1');
INSERT INTO `gift_certificates_system`.`gift_certificate_tag_connection` (`gift_certificate_id`, `tag_id`) VALUES ('1', '3');