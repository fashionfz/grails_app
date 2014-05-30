/* 初始化两个系统角色 */
﻿INSERT INTO `monitor`.`role` (`id`, `version`, `name`, `realname`) VALUES ('1', '0', 'ROLE_ADMIN', '系统管理员');
INSERT INTO `monitor`.`role` (`id`, `version`, `name`, `realname`) VALUES ('2', '0', 'ROLE_USER', '普通用户');

-- 初始化一个系统组
INSERT INTO `monitor`.`user_group` (`id`, `version`, `disabled`, `enabled`, `name`, `remark`) VALUES ('1', '0', '0', '1', '系统组', '系统组');
-- 初始化一个系统用户
INSERT INTO `monitor`.`user` (`id`, `version`, `disabled`, `email`, `enabled`, `password_hash`, `phone`, `realname`, `username`, `validate_type`) VALUES ('1', '0', '0', '327722743@qq.com', '1', '29b4ba654ce7bc315797fe98341bde28afc5bfbdc81e089179a0673c2dcd8679', '18683712095', 'admin', 'admin', '1');

-- 初始化用户角色和用户的的映射关系
INSERT INTO `monitor`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '1');
-- 初始化系统用户和系统组的映射关系
INSERT INTO `monitor`.`user_group_user` (`user_group_users_id`,`user_id`) VALUES ('1','1');

-- 初始化字典表数据
INSERT INTO `dictionary` VALUES ('1', '0', 'BOOL0001', 'bool', '是', '1');
INSERT INTO `dictionary` VALUES ('2', '0', 'BOOL0002', 'bool', '否', '0');
INSERT INTO `dictionary` VALUES ('3', '0', 'VALIDATETYPE0001', 'validateType', 'LOCAL', '1');
INSERT INTO `dictionary` VALUES ('4', '0', 'VALIDATETYPE0002', 'validateType', 'RADIUS', '2');
INSERT INTO `dictionary` VALUES ('5', '0', 'VALIDATETYPE0003', 'validateType', 'LDAP', '3');
INSERT INTO `dictionary` VALUES ('6', '0', 'WARNINGVALVE0001', 'warningValve', 'ALL阀值>80%', '1');
INSERT INTO `dictionary` VALUES ('7', '0', 'WARNINGVALVE0002', 'warningValve', 'CPU阀值>80%', '2');
INSERT INTO `dictionary` VALUES ('8', '0', 'WARNINGVALVE0003', 'warningValve', '内存阀值>80%', '3');
INSERT INTO `dictionary` VALUES ('9', '0', 'WARNINGVALVE0004', 'warningValve', '网络吞吐量阀值>80%', '4');
INSERT INTO `dictionary` VALUES ('10', '0', 'WARNINGVALVE0005', 'warningValve', '硬盘阀值>80%', '5');
INSERT INTO `dictionary` VALUES ('11', '0', 'COM0001', 'comport', 'COM1', '1');
INSERT INTO `dictionary` VALUES ('12', '0', 'COM0002', 'comport', 'COM2', '2');
INSERT INTO `dictionary` VALUES ('13', '0', 'COM0003', 'comport', 'COM3', '3');
INSERT INTO `dictionary` VALUES ('14', '0', 'MODEL0001', 'model', 'GSM Model', '1');
INSERT INTO `dictionary` VALUES ('15', '0', 'MODEL0002', 'model', 'CDMA Model', '2');
INSERT INTO `dictionary` VALUES ('16', '0', 'MODEL0003', 'model', 'WCDMA Model', '3');
INSERT INTO `dictionary` VALUES ('17', '0', 'TARGET0001', 'target', 'CPU状态', '1');
INSERT INTO `dictionary` VALUES ('18', '0', 'TARGET0002', 'target', '内存状态', '2');
INSERT INTO `dictionary` VALUES ('19', '0', 'TARGET0003', 'target', '磁盘空间', '3');
INSERT INTO `dictionary` VALUES ('20', '0', 'TARGET0004', 'target', '网络吞吐', '4');
INSERT INTO `dictionary` VALUES ('21', '0', 'ALARM_LEVEL_1', 'alarm_level', '一般', '1');
INSERT INTO `dictionary` VALUES ('22', '0', 'ALARM_LEVEL_2', 'alarm_level', '严重', '2');
INSERT INTO `dictionary` VALUES ('23', '0', 'ALARM_TYPE_1', 'alarm_type', '邮件', '1');
INSERT INTO `dictionary` VALUES ('24', '0', 'ALARM_TYPE_2', 'alarm_type', '短信', '2');
INSERT INTO `dictionary` VALUES ('25', '0', 'IPTYPE0001', 'iptype', '内网IP', '1');
INSERT INTO `dictionary` VALUES ('26', '0', 'IPTYPE0002', 'iptype', '外网IP', '2');
INSERT INTO `dictionary` VALUES ('27', '0', 'IPTYPE0003', 'iptype', 'IDRAC卡IP', '3');
INSERT INTO `dictionary` VALUES ('28', '0', 'OBSERVED_TYPE_001', 'observedType', '主机', '1');
INSERT INTO `dictionary` VALUES ('29', '0', 'OBSERVED_TYPE_002', 'observedType', '应用', '2');
INSERT INTO `dictionary` VALUES ('30', '0', 'SERVICETYPE0001', 'servicetype', 'Nginx', '1');
INSERT INTO `dictionary` VALUES ('31', '0', 'SERVICETYPE0002', 'servicetype', 'Tomcat', '2');
INSERT INTO `dictionary` VALUES ('32', '0', 'SERVICETYPE0003', 'servicetype', 'Mysql', '3');
INSERT INTO `dictionary` VALUES ('33', '0', 'SERVICETYPE0004', 'servicetype', 'Play', '4');
INSERT INTO `dictionary` VALUES ('34', '0', 'SERVICETYPE0005', 'servicetype', 'Memcached', '5');
INSERT INTO `dictionary` VALUES ('35', '0', 'SERVICETYPE0006', 'servicetype', '3rd', '6');
INSERT INTO `dictionary` VALUES ('36', '0', 'INDICATORTYPE001', 'indicatortype', '性能指标', '1');
INSERT INTO `dictionary` VALUES ('37', '0', 'INDICATORTYPE002', 'indicatortype', '应用指标', '2');
INSERT INTO `dictionary` VALUES ('38', '0', 'INDICATORTYPE003', 'indicatortype', '其它指标', '3');
insert into `dictionary` VALUES ('39', '0', 'ALARM_TYPE_3', 'alarm_type', '短信+邮件', '3');
insert into `dictionary` VALUES ('40', '0', 'ERROR_NUM', 'check_url', 'URL报警阀值', '3');
insert into `dictionary` VALUES ('41', '0', 'ALARM_MODULE_1', 'alarm_module', '性能阀值告警', '1');
insert into `dictionary` VALUES ('42', '0', 'ALARM_MODULE_2', 'alarm_module', '宕机通知', '2');
insert into `dictionary` VALUES ('43', '0', 'ALARM_MODULE_3', 'alarm_module', '网页检查通知', '3');
insert into `dictionary` VALUES ('44', '0', 'ALARM_MODULE_4', 'alarm_module', '值班通知', '4');

insert into `dictionary` VALUES ('45', '0', 'hi', 'prduct', 'hi', '1');
insert into `dictionary` VALUES ('46', '0', 'helome', 'prduct', 'helome', '2');

insert into `dictionary` VALUES ('47', '0', 'android', 'platform', 'android', '1');
insert into `dictionary` VALUES ('48', '0', 'iPhone 5S', 'platform', 'iPhone 5S', '2');
insert into `dictionary` VALUES ('49', '0', 'web', 'platform', 'web', '3');

insert into `dictionary` VALUES ('50', '0', 'RETURN_1', 'user_return', '次日留存率', '1');
insert into `dictionary` VALUES ('51', '0', 'RETURN_7', 'user_return', '7日留存率', '7');
insert into `dictionary` VALUES ('52', '0', 'RETURN_30', 'user_return', '30日留存率', '30');




-- 初始化指标数据
INSERT INTO `indicator` VALUES ('1', '3', 'cpu', null, '0', '1', 'Cpu', 'Common_Cpu', 'cpu指标', '1');
INSERT INTO `indicator` VALUES ('2', '3', 'mem', null, '0', '1', 'Mem', 'Common_Mem', '内存指标', '1');
INSERT INTO `indicator` VALUES ('3', '3', 'disk', null, '0', '1', 'Disk', 'Common_Disk', '硬盘指标', '1');
INSERT INTO `indicator` VALUES ('4', '3', 'ifstat', null, '0', '1', 'Ifstat', 'Common_Network_Traffic', '网络吞吐指标', '1');
INSERT INTO `indicator` VALUES ('6', '0', 'connection', null, '0', '1', 'Connection', 'Common_Connection', '链接指标', '1');
INSERT INTO `indicator` VALUES ('7', '0', 'jvm', null, '0', '1', 'com.helome.monitor.indicator.Jvm', 'Common_Jvm', '虚拟机指标', '1');
INSERT INTO `indicator` VALUES ('8', '0', 'mysql_check', null, '0', '1', 'com.helome.monitor.indicator.Mysql_check', 'Common_Mysql_check', 'mysql_check指标', '1');
INSERT INTO `indicator` VALUES ('9', '0', 'mysql_lock', null, '0', '1', 'com.helome.monitor.indicator.Mysql_lock', 'Common_Mysql_lock', 'mysql_lock指标', '1');


-- 初始化通知方式数据
INSERT INTO `notifyway` VALUES ('1', '短信');
INSERT INTO `notifyway` VALUES ('2', '邮件');
INSERT INTO `notifyway` VALUES ('3', '短信+邮件');

-- 菜单权限
/**主菜单*/
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(1,0,'','','主菜单',null,1,'/1');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(2,0,'index','monitor','监控概览',1,1,'/1/2');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(3,0,'performance','monitor','性能',1,2,'/1/3');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(4,0,'business','monitor','业务',1,3,'/1/4');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(5,0,'application','monitor','应用',1,4,'/1/5');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(6,0,'db','monitor','数据库',1,5,'/1/6');

/**首页*/
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(7,0,'','','首页',null,2,'/7');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(8,0,'warnings','monitor','告警监控',7,1,'/7/8');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(9,0,'connectivy','monitor','在线监控',7,2,'/7/9');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(10,0,'messages','monitor','性能监控',7,3,'/7/10');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(11,0,'serverStatus','monitor','服务状态',7,4,'/7/11');


/**系统*/
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(12,0,'','','系统',null,3,'/12');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(13,0,'usermanager','user','账户管理',12,1,'/12/13');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(14,0,'save','user','保存',13,1,'/12/13/14');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(15,0,'delete','user','删除',13,2,'/12/13/15');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(16,0,'emailSetting','user','邮件通知',12,2,'/12/16');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(17,0,'smsSetting','user','短信通知',12,3,'/12/17');

/**策略*/
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(18,0,'','','策略',null,4,'/18');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(19,0,'warnStragegyIndex','strategy','告警策略',18,1,'/18/19');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(20,0,'monitorStrategyIndex','strategy','监控策略',18,2,'/18/20');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(21,0,'connectivyIndex','strategy','宕机通知',18,3,'/18/21');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(22,0,'userView','onDuty','用户视图',18,4,'/18/22');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(23,0,'saveConfigRole','onDuty','配置',22,1,'/18/22/23');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(24,0,'list','onDuty','值班管理',18,5,'/18/24');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(25,0,'saveDutyTable','onDuty','排班',24,1,'/18/24/25');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(26,0,'saveDutyConfig','onDuty','配置',24,2,'/18/24/26');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(27,0,'change','onDuty','调班',24,3,'/18/24/27');
/**策略*/
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(28,0,'','','对象',null,5,'/28');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(29,0,'userGroupIndex','userGroup','用户组',28,1,'/28/29');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(30,0,'serverlist','serverInfo','服务器',28,2,'/28/30');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(31,0,'serverGroupList','serverGroup','服务器组',28,3,'/28/31');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(32,0,'indicatorList','indicator','采集指标',28,4,'/28/32');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(33,0,'listIndicatorWarnCondition','indicatorWarnCondition','告警条件',28,5,'/28/33');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(34,0,'rolelist','role','角色配置',28,6,'/28/34');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(35,0,'saveRole','role','保存',34,1,'/28/34/35');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(36,0,'personlist','role','值班人员',28,7,'/28/36');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(37,0,'savePerson','role','保存',36,1,'/28/36/37');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(38,0,'deletePerson','role','删除',36,2,'/28/36/38');



insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(39,0,'','','日志',null,6,'/39');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(40,0,'','','业务分析',39,1,'/39/40');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(41,0,'userlog','log','操作日志',39,2,'/39/41');

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
insert into role_menus(select id,1 from monitor_menu);

-- 日志类型
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('1','0','signIn','auth','登录日志','登录','系统');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('2','0','signOut','auth','登录日志','注销','系统');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('3','0','save','user','操作日志','新增/修改','用户');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('4','0','delete','user','操作日志','删除','用户');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('5','0','usermanager','user','查询日志','查询','用户');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('6','0','list','onDuty','查询日志','查询','值班');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('7','0','saveDutyTable','onDuty','操作日志','新增','值班');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('8','0','saveDutyConfig','onDuty','操作日志','保存','值班配置');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('9','0','change','onDuty','操作日志','交换','值班');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('10','0','urlchecklist','onDuty','查询日志','查询','网页检测');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('11','0','saveCheckUrl','onDuty','操作日志','新增/修改','网页检测');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('12','0','deleteCheckUrl','onDuty','操作日志','删除','网页检测');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('13','0','modifyCheckTime','onDuty','操作日志','修改','网页检测配置');


insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('14','0','userView','onDuty','查询日志','查询','用户视图');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('15','0','saveConfigRole','onDuty','操作日志','修改','用户视图');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('16','0','notifylist','notifyPlot','查询日志','查询','通知策略');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('17','0','notifyPlotSave','notifyPlot','操作日志','新增/修改','通知策略');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('18','0','notifyPlotDel','notifyPlot','操作日志','删除','通知策略');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('19','0','monitorStrategyIndex','strategy','查询日志','查询','监控策略');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('20','0','monitorStrategySave','strategy','操作日志','新增/修改','监控策略');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('21','0','warnStragegyIndex','strategy','查询日志','查询','告警策略');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('22','0','warnStrategySave','strategy','操作日志','新增/修改','告警策略');


insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('23','0','userGroupIndex','userGroup','查询日志','查询','用户组');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('24','0','save','userGroup','操作日志','新增/修改','用户组');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('25','0','serverGroupList','serverGroup','查询日志','查询','服务器组');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('26','0','save','serverGroup','操作日志','新增/修改','服务器组');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('27','0','delete','serverGroup','操作日志','删除','服务器组');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('28','0','indicatorList','indicator','查询日志','查询','采集指标');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('29','0','saveIndicator','indicator','操作日志','新增/修改','采集指标');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('30','0','deleteIndicator','indicator','操作日志','删除','采集指标');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('31','0','listIndicatorWarnCondition','indicatorWarnCondition','查询日志','查询','告警条件');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('32','0','save','indicatorWarnCondition','操作日志','新增/修改','告警条件');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('33','0','delete','indicatorWarnCondition','操作日志','删除','告警条件');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('34','0','rolelist','role','查询日志','查询','角色');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('35','0','saveRole','role','操作日志','新增/修改','角色');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('36','0','deleteRole','role','操作日志','删除','角色');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('37','0','serverlist','serverInfo','查询日志','查询','服务器');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('38','0','save','serverInfo','操作日志','新增/修改','服务器');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('39','0','delete','serverInfo','操作日志','删除','服务器');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('40','0','listIPAddress','serverInfo','查询日志','查询','服务器IP信息');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('41','0','listServerApp','serverInfo','查询日志','查询','服务器应用信息');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('42','0','saveIpAddress','serverInfo','操作日志','新增/修改','服务器IP信息');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('43','0','ipAddressDelete','serverInfo','操作日志','删除','服务器IP信息');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('44','0','saveServerApp','serverInfo','操作日志','新增/修改','服务器应用信息');
insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('45','0','deleteServerApp','serverInfo','操作日志','删除','服务器应用信息');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('46','0','index','monitor','查询日志','查询','主菜单');

insert into `operation_map` (`id`, `version`, `action_name`, `controller_name`, `log_type_name`, `opt_type_name`, `target_type_name`) values('47','0','error','error','异常日志','异常','系统');

-- 指标列配置
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('1','0','server_name','String','1','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('2','0','userpct','String','1','2','userpct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('3','0','nicepct','String','1','3','nicepct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('4','0','systempct','String','1','4','systempct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('5','0','iowait','String','1','5','iowait','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('6','0','steal','String','1','6','steal','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('7','0','idle','String','1','7','idle','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('8','0','time','Date','1','1','time','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('9','0','time','Date','2','1','time','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('10','0','used','String','2','2','used','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('11','0','free','String','2','3','free','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('12','0','usedpct','String','2','4','usedpct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('13','0','time','Date','3','1','time','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('14','0','used','String','3','2','used','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('15','0','available','String','3','3','available','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('16','0','usedpct','String','3','4','usedpct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('17','0','time','Date','4','1','time','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('18','0','recivebyte','String','4','2','recivebyte','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('19','0','transmitbyte','String','4','3','transmitbyte','1');

insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('20','0','server_name','String','2','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('21','0','server_name','String','3','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('22','0','server_name','String','4','1','服务器名','1');


insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('23','0','server_name','String','6','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('24','0','time','Date','6','2','采集时间','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('25','0','concount','String','6','3','concount','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('26','0','foreignip','String','6','4','foreignip','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('27','0','contype','String','6','5','contype','1');


--jvm(7)
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('28','0','server_name','String','7','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('29','0','time','Date','7','2','采集时间','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('30','0','status','String','7','3','status','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('31','0','cpuPct','String','7','4','cpuPct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('32','0','totalMem','String','7','5','totalMem','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('33','0','youngPct','String','7','6','youngPct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('34','0','oldPct','String','7','7','oldPct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('35','0','permPct','String','7','8','permPct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('36','0','activedThreadCount','String','7','9','activedThreadCount','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('37','0','loadClassCount','String','7','10','loadClassCount','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('38','0','port','String','7','11','port','1');
--mysql_check(8)
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('39','0','server_name','String','8','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('40','0','time','Date','8','2','采集时间','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('41','0','status','String','8','3','status','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('42','0','cpupct','String','8','4','cpupct','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('43','0','mem','String','8','5','mem','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('44','0','threadcount','String','8','6','threadcount','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('45','0','abortedclient','String','8','7','abortedclient','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('46','0','abortedconnects','String','8','8','abortedconnects','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('47','0','uptime','String','8','9','uptime','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('48','0','qps','String','8','10','qps','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('49','0','slowsql','String','8','11','slowsql','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('50','0','slowsqlatime','String','8','12','slowsqlatime','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('51','0','slowsqlttime','String','8','13','slowsqlttime','1');
--mysql_lock(9)
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('52','0','server_name','String','9','1','服务器名','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('53','0','time','Date','9','2','采集时间','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('54','0','dbt','String','9','3','dbt','1');
insert into `server_index_columu` (`id`, `version`, `field_name`, `field_type`, `indicator_id`, `srot`, `label_name`, `status`) values('55','0','counts','String','9','4','counts','1');


--menu_title
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

commit;
