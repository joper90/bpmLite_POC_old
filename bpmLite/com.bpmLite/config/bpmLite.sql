SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `bpmLite` ;
CREATE SCHEMA IF NOT EXISTS `bpmLite` DEFAULT CHARACTER SET utf8 ;
USE `bpmLite` ;

-- -----------------------------------------------------
-- Table `bpmLite`.`process`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`process` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`process` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `version` VARCHAR(45) NULL ,
  `unique_guid` VARCHAR(45) NULL ,
  `start_step` INT NULL ,
  `field_ids` VARCHAR(255) NULL ,
  `global_ids` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`step_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`step_data` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`step_data` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `step_id` VARCHAR(45) NULL ,
  `process_id` VARCHAR(45) NULL ,
  `step_type` VARCHAR(255) NULL ,
  `step_details` VARCHAR(45) NULL ,
  `user_list` VARCHAR(45) NULL ,
  `next_id` VARCHAR(45) NULL ,
  `previous_id` VARCHAR(45) NULL ,
  `field_data` VARCHAR(45) NULL ,
  `global_data` VARCHAR(255) NULL ,
  `display_only` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`join_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`join_status` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`join_status` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `step_id` VARCHAR(45) NULL ,
  `step_one` VARCHAR(45) NULL ,
  `step_two` VARCHAR(45) NULL ,
  `step_three` VARCHAR(45) NULL ,
  `step_count_required` INT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`audit` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`audit` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `action` VARCHAR(45) NULL ,
  `user` VARCHAR(45) NULL ,
  `step_id` VARCHAR(45) NULL ,
  `start_time` VARCHAR(45) NULL ,
  `complete_time` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`process_instance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`process_instance` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`process_instance` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `process_id` INT NOT NULL ,
  `current_step_id` INT NOT NULL ,
  `case_id` VARCHAR(45) NOT NULL ,
  `initial_data_set` TINYINT(1) NOT NULL ,
  `guid_callback` VARCHAR(128) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `case_id_UNIQUE` (`case_id` ASC) ,
  UNIQUE INDEX `guid_callback_UNIQUE` (`guid_callback` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`user_skills`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`user_skills` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`user_skills` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `value` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`user_groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`user_groups` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`user_groups` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `description` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`user` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(45) NOT NULL ,
  `unique_key` VARCHAR(255) NOT NULL ,
  `start_time` DATETIME NOT NULL ,
  `tibbr_address` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_key_UNIQUE` (`unique_key` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`assigned_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`assigned_role` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`assigned_role` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `user` INT NOT NULL ,
  `role` VARCHAR(45) NULL ,
  `group_membership` VARCHAR(45) NULL ,
  `user_skills` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`, `user`) ,
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) ,
  UNIQUE INDEX `group_membership_UNIQUE` (`group_membership` ASC) ,
  UNIQUE INDEX `user_skills_UNIQUE` (`user_skills` ASC) ,
  UNIQUE INDEX `user_UNIQUE` (`user` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`roles` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`server_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`server_info` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`server_info` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `value` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`field_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`field_data` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`field_data` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `initial_data` VARCHAR(255) NULL ,
  `field_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `field_id_UNIQUE` (`field_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bpmLite`.`global_mappings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bpmLite`.`global_mappings` ;

CREATE  TABLE IF NOT EXISTS `bpmLite`.`global_mappings` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `global_guard_id` INT NOT NULL ,
  `global_deployed_id` INT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
