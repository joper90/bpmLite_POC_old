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
  `case_id` VARCHAR(45) NOT NULL ,
  `process_id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `data` VARCHAR(45) NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `field_id` INT NOT NULL ,
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
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmguard`.`key_store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmguard`.`key_store` ;

CREATE  TABLE IF NOT EXISTS `bpmguard`.`key_store` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `user_id` VARCHAR(45) NOT NULL ,
  `user_guid` VARCHAR(45) NOT NULL ,
  `key_state` VARCHAR(45) NOT NULL ,
  `process_id` INT NOT NULL ,
  `step_id` INT NOT NULL ,
  `case_id` VARCHAR(25) NOT NULL ,
  `field_ids` VARCHAR(255) NULL ,
  `global_ids` VARCHAR(225) NULL ,
  `order_list` VARCHAR(255) NULL ,
  `display_ids` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `user_guid_UNIQUE` (`user_guid` ASC) )
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
