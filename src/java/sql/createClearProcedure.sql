-- 创建MySQL存储过程,用以清除指定表的数据.
DELIMITER $$
use monitor$$
DROP procedure IF EXISTS `clear_data_proc`$$
CREATE DEFINER=`root`@`%` PROCEDURE `clear_data_proc`()
BEGIN
	truncate table monitor.monitor_concount;
	truncate table monitor.monitor_cpu;
	truncate table monitor.monitor_disk;
	truncate table monitor.monitor_ifstat;
	truncate table monitor.monitor_jvm;
	truncate table monitor.monitor_mem;
	truncate table monitor.monitor_mysql_check;
	truncate table monitor.monitor_mysql_lock;
END$$
DELIMITER ;
