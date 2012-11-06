SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `bpmguard` ;
CREATE SCHEMA IF NOT EXISTS `bpmguard` DEFAULT CHARACTER SET utf8 ;
USE `bpmguard` ;

-- -----------------------------------------------------
-- Table `bpmguard`.`field_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmguard`.`field_data` ;

CREATE  TABLE IF NOT EXISTS `bpmguard`.`field_data` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `process_id` INT NULL ,
  `name` VARCHAR(45) NULL ,
  `data` VARCHAR(45) NULL ,
  `type` VARCHAR(45) NULL ,
  `field_id` INT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmguard`.`global_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmguard`.`global_data` ;

CREATE  TABLE IF NOT EXISTS `bpmguard`.`global_data` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `data` VARCHAR(45) NULL ,
  `type` VARCHAR(45) NULL ,
  `field_id` INT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmguard`.`key_store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmguard`.`key_store` ;

CREATE  TABLE IF NOT EXISTS `bpmguard`.`key_store` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `user_id` VARCHAR(45) NULL ,
  `key` VARCHAR(255) NULL ,
  `user_guid` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmguard`.`server_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmguard`.`server_info` ;

CREATE  TABLE IF NOT EXISTS `bpmguard`.`server_info` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `value` VARCHAR(45) NOT NULL ,
  `data` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `value_UNIQUE` (`value` ASC) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
