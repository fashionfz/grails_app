insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(1,'index','monitor','���˵�','��ظ���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(2,'performance','monitor','���˵�','����','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(3,'business','monitor','���˵�','ҵ��','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(4,'application','monitor','���˵�','Ӧ��','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(5,'db','monitor','���˵�','���ݿ�','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(6,'warnings','monitor','��ҳ','�澯���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(7,'connectivy','monitor','��ҳ','���߼��','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(8,'messages','monitor','��ҳ','���ܼ��','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(9,'serverStatus','monitor','��ҳ','����״̬','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(10,'usermanager','user','ϵͳ','�˻�����','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(11,'emailSetting','user','ϵͳ','�ʼ�֪ͨ','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(12,'smsSetting','user','ϵͳ','����֪ͨ','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(13,'warnStragegyIndex','strategy','����','�澯����','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(14,'monitorStrategyIndex','strategy','����','��ز���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(15,'userView','onDuty','����','�û���ͼ','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(16,'list','onDuty','����','ֵ�����','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(17,'urlchecklist','onDuty','����','��ҳ���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(18,'notifylist','notifyPlot','����','֪ͨ����','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(19,'userGroupIndex','userGroup','����','�û���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(20,'serverlist','serverInfo','����','������','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(21,'serverGroupList','serverGroup','����','��������','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(22,'indicatorList','indicator','����','�ɼ�ָ��','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(23,'listIndicatorWarnCondition','indicatorWarnCondition','����','�澯����','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(24,'rolelist','role','����','��ɫ����','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(25,'visitRangList','businessAnalysis','��־','ҵ�����','��¼ʱ��ֲ�');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(26,'loginArea','businessAnalysis','��־','ҵ�����','��¼����');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(27,'returnRate','businessAnalysis','��־','ҵ�����','�û�������');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(28,'','','��־','Ӧ�÷���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(29,'userlog','log','��־','������־','');


insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(30,'','','����','���ڱ���','');
insert into `menu_title` (`id`, `action_name`, `controller_name`, `main_menu_name`, `child_menu_name`,`last_menu_name`) values(31,'','','����','�Զ��屨��','');





insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(42,0,'returnRate','businessAnalysis','�û�������',40,1,'/39/40/42');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(43,0,'loginArea','businessAnalysis','��¼����',40,2,'/39/40/43');

insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(44,0,'urlchecklist','onDuty','��ҳ���',18,6,'/18/44');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(45,0,'saveCheckUrl','onDuty','����',44,1,'/18/44/45');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(46,0,'deleteCheckUrl','onDuty','ɾ��',44,2,'/18/44/46');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(47,0,'modifyCheckTime','onDuty','����',44,3,'/18/44/47');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(48,0,'notifylist','notifyPlot','֪ͨ����',18,7,'/18/48');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(49,0,'notifyPlotSave','notifyPlot','����',48,1,'/18/48/49');
insert into `monitor_menu` (`id`, `version`, `action_name`, `contoller_name`, `menu_name`, `parent_id`, `sort`,`path`) values(50,0,'notifyPlotDel','notifyPlot','ɾ��',48,2,'/18/48/50');


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
