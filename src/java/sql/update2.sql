insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(1,'index','monitor','主菜单','监控概述','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(2,'performance','monitor','主菜单','性能','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(3,'business','monitor','主菜单','业务','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(4,'application','monitor','主菜单','应用','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(5,'db','monitor','主菜单','数据库','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(6,'warnings','monitor','首页','告警监控','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(7,'connectivy','monitor','首页','在线监控','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(8,'messages','monitor','首页','性能监控','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(9,'serverStatus','monitor','首页','服务状态','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(10,'usermanager','user','系统','账户管理','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(11,'emailSetting','user','系统','邮件通知','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(12,'smsSetting','user','系统','短信通知','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(13,'warnStragegyIndex','strategy','策略','告警策略','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(14,'monitorStrategyIndex','strategy','策略','监控策略','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(15,'userView','onDuty','策略','用户视图','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(16,'list','onDuty','策略','值班管理','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(17,'urlchecklist','onDuty','策略','网页检查','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(18,'notifylist','notifyPlot','策略','通知策略','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(19,'userGroupIndex','userGroup','对象','用户组','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(20,'serverlist','serverInfo','对象','服务器','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(21,'serverGroupList','serverGroup','对象','服务器组','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(22,'indicatorList','indicator','对象','采集指标','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(23,'listIndicatorWarnCondition','indicatorWarnCondition','对象','告警条件','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(24,'rolelist','role','对象','角色配置','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(25,'visitRangList','businessAnalysis','日志','业务分析','登录时间分布');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(26,'loginArea','businessAnalysis','日志','业务分析','登录区域');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(27,'returnRate','businessAnalysis','日志','业务分析','用户留存率');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(28,'','','日志','应用分析','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(29,'userlog','log','日志','操作日志','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(30,'','','报表','周期报表','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(31,'','','报表','自定义报表','');





insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(42,0,'returnRate','businessAnalysis','用户留存率',40,1,'/39/40/42');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(43,0,'loginArea','businessAnalysis','登录区域',40,2,'/39/40/43');

insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(44,0,'urlchecklist','onDuty','网页检查',18,6,'/18/44');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(45,0,'saveCheckUrl','onDuty','保存',44,1,'/18/44/45');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(46,0,'deleteCheckUrl','onDuty','删除',44,2,'/18/44/46');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(47,0,'modifyCheckTime','onDuty','配置',44,3,'/18/44/47');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(48,0,'notifylist','notifyPlot','通知策略',18,7,'/18/48');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(49,0,'notifyPlotSave','notifyPlot','保存',48,1,'/18/48/49');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(50,0,'notifyPlotDel','notifyPlot','删除',48,2,'/18/48/50');


delete from role_menus where role_id=1;
delete from `monitor_menu` where id=21;
delete from `monitor_menu` where id=37;
delete from `monitor_menu` where id=38;
delete from `monitor_menu` where id=36;




insert into role_menus(select id,1 from monitor_menu);



alter table `monitor`.`check_url` 
drop column `check_num`;

update indicator set code='concount' where id=6;
update indicator set entity_name='com.helome.monitor.indicator.Concount' where id=6;
