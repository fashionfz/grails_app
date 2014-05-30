-- 创建一个MYSQL的schedule 用于清除当天采集入库的数据.注：日期需要修改为创建当天的系统日期
﻿CREATE EVENT clear_event ON SCHEDULE EVERY 1 DAY STARTS "2014-03-12 23:59:30"
DO
CALL clear_data_proc();