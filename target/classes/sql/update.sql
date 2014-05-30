ALTER TABLE `monitor`.`indicator` 
CHANGE COLUMN `entity_name` `entity_name` VARCHAR(255) NULL ;

DROP TABLE `monitor`.`user_permissions`;

ALTER TABLE `monitor`.`server_info` 
DROP FOREIGN KEY `FKD2C4002A88430B77`;
ALTER TABLE `monitor`.`server_info` 
DROP COLUMN `user_id`,
DROP INDEX `FKD2C4002A88430B77` ;



drop table `monitor`.`person_on_duty`;
drop table `monitor`.`notify_user`;


ALTER TABLE `monitor`.`server_info` 
DROP FOREIGN KEY `FKD2C4002A88430B77`;

alter table `monitor`.`check_url` 
drop column `notifyway_id`;

drop table `monitor`.`warning_strategy_user`;

drop table `monitor`.`notifyway`;

alter table `monitor`.`duty_config` 
drop column `notify_type`;



update indicator set entity_name='com.helome.monitor.indicator.Cpu' where code ='cpu';
update indicator set entity_name='com.helome.monitor.indicator.Mem' where code ='mem';
update indicator set entity_name='com.helome.monitor.indicator.Disk' where code ='disk';
update indicator set entity_name='com.helome.monitor.indicator.Ifstat' where code ='ifstat';
update indicator set entity_name='com.helome.monitor.indicator.Concount' where code ='connection';

update indicator set code='concount' where name='Common_Connection';

drop table `monitor`.`monitor_concount`;
drop table `monitor`.`monitor_cpu`;
drop table `monitor`.`monitor_disk`;
drop table `monitor`.`monitor_ifstat`;
drop table `monitor`.`monitor_jvm`;
drop table `monitor`.`monitor_mem`;
drop table `monitor`.`monitor_mysql_check`;
drop table `monitor`.`monitor_mysql_lock`;


alter table `monitor`.`check_url` 
drop column `version`;


delete from role_menus where monitor_menu_id=2;
delete from monitor_menu where id=2;

update monitor_menu set action_name='',contoller_name='' where id=40;

ALTER TABLE `monitor`.`check_url` 
DROP FOREIGN KEY `FK17C715B867AA69D7`;

alter table `monitor`.`check_url` 
drop column `dictionary_id`;

-- End 2014-05-15 --

alter table `monitor`.`check_url` 
drop column `check_num`;

--End 2014-05-20

update duty_config set dictionary_id=39;