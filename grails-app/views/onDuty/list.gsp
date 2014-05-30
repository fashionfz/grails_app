<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.helome.monitor.DutyMain" %>
<%@ page import="com.helome.monitor.DutyTable" %>
<%@ page import="com.helome.monitor.DutyConfig" %>
<%@ page import="com.helome.monitor.Dictionary" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="layout" content="main"/>
<title>策略---值班管理</title>
<r:require module="datepicker"/>
<link rel="stylesheet" type="text/css" href="../css/head.css">
<link rel="stylesheet" type="text/css" href="../css/left.css">
<link rel="stylesheet" type="text/css" href="../css/right.css">
<link rel="stylesheet" type="text/css" href="../css/index.css"/>
<link rel="stylesheet" type="text/css" href="../css/tanchu.css"/>
<style type="text/css">
     .td1{ background-color:#ff5c36;}  
     .td2{ background-color:yellow;}  
     .sl1{background-color:red;}
</style>
<script type="text/javascript" src="../js/tanchu.js"></script><!--弹出层效果-->
<script type="text/javascript" src="../js/onduty.js"></script>
<script type="text/javascript" src="../js/onduty_set.js"></script>
</head>
<body>
    <!--告警监控开始-->
    <div class="alarm-log">    
        <!--弹出设置层开始-->        	
            <div class="zhanghao-but list-but">
            	<img src="../images/set-up.png" alt="设置" onclick="popup_show(1)"/>
                <div class="sample_popup" id="popup1" style="visibility: hidden; display: none;">
                    <div class="menu_form_header" id="popup_drag1">
                        <img  class="menu_form_exit" id="popup_exit1" src="../images/close.png"/>设置
                    </div>
                    <div class="menu_form_body">
                    <div class="dev-notice">
                        <form id="frm" action="saveDutyConfig" method="post">
                            <div class="tacti-box-one">
                                <table border="0" >
                                    <tr>
                                        <td align="right" width="20%">换班通知<input type="hidden" name="notifyName1" value="换班通知"></td>
                                        <td><input type="hidden" name="notifyWay1" value="39">
                                        	<input type="hidden" name="notify1" value="1">
                                        <!-- <select class="dev-not-select" name="notifyWay1">
                                            <option value ="1">短信通知</option>
                                            <option value ="2">邮件通知</option>
                                            <option value ="3">短信+邮件</option> 
                                        </select>
                                        <input type="radio" checked="checked" name="notify1" value="1"/>打开&nbsp;&nbsp;&nbsp;<input type="radio" name="notify1" value="0"/>关闭
                                         -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">内容</td>
                                        <td rowspan="3"><textarea name="content1" clos="1" rows="2" style="width: 400px;">【换班通知】:#name#现在开始值班,如有需要请电话联系。#name# #phone#.----监控中心</textarea></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tacti-box-one">
                                <table border="0" >
                                    <g:each var="config" in="${DutyConfig.where{name=='次日通知'} }">
                                    <tr>
                                        <td align="right" width="20%">次日通知<input type="hidden" name="notifyName2" value="次日通知"></td>
                                        <td>
             
                                            <g:select optionKey="id" optionValue="name" name="notifyWay2" from="${ Dictionary.where{codeType == 'alarm_type'}}" value="${config?.dictionary?.id }"/>
                                        <input type="radio" checked="checked" name="notify2" value="1"/>打开&nbsp;&nbsp;&nbsp;<input type="radio" name="notify2" value="0"/>关闭</td>
                                    </tr>
                                    <tr>
                                        <td align="right">内容</td>
                                        <td rowspan="3"><textarea name="content2" clos="1" rows="2" style="width: 400px;">【次日通知】:#name#,您好!按照排班计划,明日#timeRang#由您值班,请保持手机畅通，携带电脑..----监控中心</textarea></td>
                                    </tr>
                                    </g:each>
                                </table>
                            </div>
                            <div class="tacti-box-one">
                                <table border="0" >
                                    <tr>
                                        <td align="right" width="20%">值班通知<input type="hidden" name="notifyName3" value="值班通知"></td>
                                        <td>
                                        	<input type="hidden" name="notifyWay3" value="39">
                                        	<input type="hidden" name="notify3" value="1">
                                        	<!--
                                        <select class="dev-not-select" name="notifyWay3">
                                            <option value ="1">短信通知</option>
                                            <option value ="2">邮件通知</option>
                                            <option value ="3">短信+邮件</option>
                                        </select>
                                        <input type="radio" checked="checked" name="notify3" value="1"/>打开&nbsp;&nbsp;&nbsp;<input type="radio" name="notify3" value="0"/>关闭
                                          -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">内容</td>
                                        <td rowspan="3"><textarea name="content3" clos="1" rows="2" style="width: 400px;">【值班通知】:今日值班安排 “#content#”。.----监控中心</textarea></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tacti-box-one">
                                <table border="0" >
                                    <tr>
                                        <td align="right" width="20%">调班通知<input type="hidden" name="notifyName4" value="调班通知"></td>
                                        <td>
                                            <input type="hidden" name="notifyWay4" value="39">
                                        	<input type="hidden" name="notify4" value="1">
                                        	<!-- 
                                        <select class="dev-not-select" name="notifyWay4">
                                            <option value ="1">短信通知</option>
                                            <option value ="2">邮件通知</option>
                                            <option value="3">短信+邮件</option> 
                                        </select>
                                        <input type="radio" checked="checked" name="notify4" value="1"/>打开&nbsp;&nbsp;&nbsp;<input type="radio" name="notify4" value="0"/>关闭
                                         -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">内容</td>
                                        <td rowspan="3"><textarea name="content4" clos="1" rows="2" style="width: 400px;">【调班通知】:管理员#user#调整了值班安排,班次#name1# #date1# #timerang1#与#name2# #date2# #timerang2#对调.</textarea></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="new-but"><input type="button" value="确定"  class="button" onclick="saveConfig()"/></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>            
        <!--弹出设置层结束-->
        <!--弹出排班层开始-->        	
            <div class="zhanghao-but list-but">
            	<img src="../images/scheduling.png" alt="排班"  onclick="popup_show(2)"/>
                <div class="sample_popup" id="popup2" style="visibility: hidden; display: none;">
                    <div class="menu_form_header" id="popup_drag2">
                        <img class="menu_form_exit" id="popup_exit2" src="../images/close.png" alt="关闭"/>排班
                    </div>
                <div class="menu_form_body">
                <div class="dev-notice">
                    <div class="tacti-box-one">
                        <div class="dev-xingneng">
                            <i class="dev-xingneng-txt">排班时间</i><input id="startDate" type="text" class="log-time" onblur="checkBeginDate()">
                                <i class="log-list">至</i>
                                <input type="text"  id="endDate" class="log-time">                                    
                    	</div>
                    </div>
                    <div class="scheduling-all">
                    	<div class="sch-all-left">
                        	<ul>
                                <li>值班人员</li>                                   
                                	<li><input id="dutynames" name="dutynames" type="text" onblur="writeUser()"/></li>   
                                	<li>值班顺序</li>
                                	<div id="listuser">
                                	<ul>
                                	</ul>
                                	</div>
                            </ul>
                        </div>
                        <div class="sch-all-right">
                        	<p>每天值班班次<input id="timeRangNum" type="text" value="1" class="input-tact"/>班次</p>
                            <table>
                            	<tr>
                                	<td>工作日时段</td>
                                    <td>节假日时段</td>
                                </tr>
                                <tr>
                                	<td><table><tr><td><input type="text" id="workrang" name="workrang" value="18:00-23:59" class="input-time"/><button class="but-time" onclick="addTimeRang()"></button></td></tr></table></td>
                                    <td><div id="listSleepRang"><input type="text" id="timeRang" name="timeRang" value="00:00-23:59" class="input-time"/><button class="but-time"></button></div></td>
                                </tr>
                                <tr>
                                	<td><table id="listTimeRang"></table></td>
                                </tr>
                                <tr>
                                	<td colspan="2">
                                    	每个班次值班人数<input type="text" value="1" class="input-tact"/>人
                                    </td>
                                </tr>
                            </table>
                            <p>节假日处理</p>
                            <p style="margin:1px 0 20px 0;">
                            	请将当年的节假日录入，则排班时,属于节假日按照周末处理。每天1人值班，如有运维组有自行安排,则需要自行修改排班时间.
                            </p>								
                            <p>
                            	<input type="text" id="special" value="2014-06-04" class="input-tactlist"/>
                            </p>                                
                        </div>                              
                    </div>
                    <div class="new-but sch-but"><input type="button" value="生成排班"  class="button" onclick="createDuty()"/></div>
                    <form method="post" action="#">
                        <div  class="div-tact-bottom">
                            <table  border="0" cellpadding="0" cellspacing="0" style="text-align: center;">
                                <tr>
                                    <td >值班日期</td>
                                    <td >值班时间段</td>
                                    <td >值班人员</td>
                                    <td >同班人员</td>                     
								</tr>
							    <tr>
							    	<td colspan="4">
							    		<table width="100%" id="dutyTable"></table>
							    	</td>
							    </tr>								
                            </table>
                            <span class="sapn-list">
                                时间范围:默认工作日为18:00-09:00,默认节假日为00:00-23:59可自行进行修改时间范围.红色为节假日时间范围.
                            </span>
                         </div>
                        <div class="new-but sch-but"><input type="button" value="完成"  class="button" onclick="saveDutyTable()"/></div>
                    </form>
                    </div>
                </div>
                </div>
            </div>            
        <!--弹出排班层结束-->
         <!--弹出调班层开始-->        	
            <div class="zhanghao-but list-but">
            	<img src="../images/transfer-classes.png" alt="排班"  onclick="popup_show(3)"/>
                <div class="sample_popup" id="popup3" style="visibility: hidden; display: none;">
                    <div class="menu_form_header" id="popup_drag3">
                        <img  class="menu_form_exit" id="popup_exit3" src="../images/close.png"/> 调班
                    </div>
                    <div class="menu_form_body">
                        <div class="dev-notice">
                            <form method="post" action="#">
                               <table border="0" cellpadding="1" cellspacing="0" class="set-up set-up2">
                                    <tr>
                                        <td width="14%">时间段</td>
                                        <td>星期一</td>
                                        <td>星期二</td>
                                        <td>星期三</td>
                                        <td>星期四</td>
                                        <td>星期五</td>
                                        <td>星期六</td>
                                        <td>星期日</td>
                                    </tr>
										<tr>
											<td>
										    	<table width="100%" rules="rows" border="0" style="border-bottom:1px solid #dedede;">
										    	<%
												    String csses="background: #6bc04b; color: #fff;/background: #ffb81d; color: #fff;/background: #00a7cf; color: #fff;/background: #6bc04b; color: #fff;"
													String[] cssArray =csses.split("/");
													int rangCount=0;
													int trHight=0;
													DutyMain main = request.getAttribute("main");
													int mainId=0;
								                    if(main!=null){
								                    	mainId = main.getId();
														rangCount = main.getRangCount();
														trHight = 60/main.getRangCount();
														String timeRang = main.getTimeRang();
														String[] rangArray = timeRang.split(",");
														for(int i=0;i<main.getWeekCount();i++){
															for(int j=0;j<main.getRangCount();j++){
																out.print("<tr height=\""+trHight+"px\"><td style=\""+cssArray[j]+"\">"+rangArray[j]+"</td></tr>");
															}
														}
														
								                    }
												 %>
										    	</table>
											</td>
											<td colspan="7">
												<table id="dutyContent" width="100%" rules="rows" border="0">
												
												<%
												    int i=0;	
													boolean flag=true;
													SimpleDateFormat df = new SimpleDateFormat("MM-dd");
													List<DutyTable> list = (List<DutyTable>)request.getAttribute("dutys");
													if(list!=null){
														for(int m=0;m<list.size();m=m+rangCount)
														{
															DutyTable tab = list.get(m);
															if(i%7==0){
																out.print("<tr height=\""+trHight*rangCount+"px\">");
															}
													        if(flag){
													            int week = tab.getWeek()==0?7:tab.getWeek();
														        for(int x=0;x<week-1;x++){
														           out.print("<td>&nbsp;</td>");
																   i=x+1;
														        }
						                                        flag=false;
													        }
															out.print("<td><table class='set-up set-up2' rules='rows'>");
															for(int u=0;u<rangCount;u++){
																tab = list.get(m+u);
																out.print("<tr height=\""+(trHight-3)+"px\"><td id=\""+tab.getId()+"\" onclick='selectTab(this)'>"+df.format(tab.getDutyDate())+"<br />"+tab.getDutyUser()+"</td></tr>");
															}
															out.print("</table></td>");
															i++;
															if(i%7==0){
																out.print("</tr>");
															}
														}
														
														
													if(i%7>0){
														for(int j=0;j<(7-i%7);j++){
															 out.print("<td>&nbsp;</td>");
														}
														 out.print("</tr>");
													}
												}
											  %>
											</table>
										</td>
									</tr>
								</table>
                                <span class="sapn-list">黄色区域为2人互相调班.</span>
                                <div class="new-but"><input type="button" value="完成调班" onclick="change()"  class="button"/></div>
                            </form>
                        </div>
                    </div>
               </div>
           </div>            
        <!--弹出调班层结束-->
          <div class="scheduling">
          		<strong>排班表</strong>
                 	<g:select id="mainId" optionKey="id" optionValue="name" value="${mainId}"  name="enabled" from="${DutyMain.where {id > 0}}"/>
            		<input type="button" value="查看" onclick="query()">
                <table border="0" cellpadding="1" cellspacing="0" class="set-up set-up1" bordercolor="#dedede">
                	<tr>
                    	<td width="10%">时间段</td>
                        <td>星期一</td>
                        <td>星期二</td>
                        <td>星期三</td>
                        <td>星期四</td>
                        <td>星期五</td>
                        <td>星期六</td>
                        <td>星期日</td>
                    </tr>
						<tr>
							<td>
						    	<table width="100%" rules="rows" border="0" style="border-bottom:1px solid #dedede;">
						    	<%
				                    if(main!=null){
										out.print("<input type=\"hidden\" id=\"queryMainId\" value=\""+main.getId()+"\"/>");
										String timeRang = main.getTimeRang();
										String[] rangArray = timeRang.split(",");
										for(int h=0;h<main.getWeekCount();h++){
											for(int j=0;j<main.getRangCount();j++){
												out.print("<tr height=\""+trHight+"px\"><td style=\""+cssArray[j]+"\">"+rangArray[j]+"</td></tr>");
											}
										}
										
				                    }
								 %>
						    	</table>
							</td>
							<td colspan="7">
								<table id="dutyContent" width="100%" rules="rows" border="0">
								
								<%
								
								    Date cur = new Date();
								    int p=0;	
									boolean flag2=true;
									if(list!=null){
										for(int m=0;m<list.size();m=m+rangCount)
										{
											DutyTable tab = list.get(m);
											if(p%7==0){
												out.print("<tr height=\""+trHight*rangCount+"px\">");
											}
									        if(flag2){
									            int week = tab.getWeek()==0?7:tab.getWeek();
										        for(int x=0;x<week-1;x++){
										           out.print("<td width='14%'>&nbsp;</td>");
												   p=x+1;
										        }
		                                        flag2=false;
									        }
											out.print("<td width='14%'><table name='item' class='set-up set-up2' rules='rows'>");
											for(int u=0;u<rangCount;u++){
												tab = list.get(m+u);
												if(df.format(cur).equals(df.format(tab.getDutyDate()))){
													out.print("<tr height=\""+(trHight-3)+"px\"><td><font color='#950b07'>"+df.format(tab.getDutyDate())+"<br />"+tab.getDutyUser()+"</font></td></tr>");
												}else{
													out.print("<tr height=\""+(trHight-3)+"px\"><td>"+df.format(tab.getDutyDate())+"<br />"+tab.getDutyUser()+"</td></tr>");
												}
											}
											out.print("</table></td>");
											p++;
											if(p%7==0){
												out.print("</tr>");
											}
										}
										
										
									if(p%7>0){
										for(int j=0;j<(7-p%7);j++){
											 out.print("<td>&nbsp;</td>");
										}
										 out.print("</tr>");
									}
								}
							  %>
							</table>
						</td>
					</tr>
				</table>
             
                <table class="set-text set-text1">
                	<tr>
                    	<td>突出显示</td>
                        <td> <input type="text" id="queryName" class="text-name"/></td>
                        <td><img src="../images/color.png" alt="颜色标注" onclick="queryByName()"  class="set-img set-img1"/></td>
                    </tr>
                </table>
            </div>
      </div>        
     
    <!--告警监控结束-->
    <script type="text/javascript" src="${resource(dir:'js',file:'widget.js')}"></script>
</body>  
</html>
