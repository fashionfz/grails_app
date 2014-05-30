<%@ page import="com.helome.monitor.CheckUrl" %>
<%@ page import="com.helome.monitor.Dictionary" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="layout" content="main"/>
<link rel="stylesheet" type="text/css" href="../css/head.css">
<link rel="stylesheet" type="text/css" href="../css/left.css">
<link rel="stylesheet" type="text/css" href="../css/right.css">
<link rel="stylesheet" type="text/css" href="../css/index.css"/>
<link rel="stylesheet" type="text/css" href="../css/tanchu.css"/>
<script language="javascript" type="text/javascript" src="../js/tanchu.js"></script><!--弹出层效果-->
<script type="text/javascript">
/**
 * 删除值班人员
 * @param id
 */
 function editPerson(id){
	 popup_show2(0);
	$("#tr"+id).find("td").each(function(){
		if($(this).attr("name") == "id"){
			$("#id").val(id);
		}else if($(this).attr("name") == "name"){
			$("#name").val($(this).text());
		}else if($(this).attr("name") == "url"){
			$("#url").val($(this).text());
		}else if($(this).attr("name") == "timeOut"){
			$("#timeOut").val($(this).text());
		}
	});
}

 function delUser(id){
	window.location = "deleteCheckUrl?id="+id;
}

 function clear(){
	$("#id").val("");
	$("#name").val("");
	$("#url").val("");
	$("#timeOut").val("");       
 }

 function popup_show2(id)
 {
	 clear();
	 element = document.getElementById('popup'+id);
	 drag_element = document.getElementById('popup_drag'+id);
	 exit_element = document.getElementById('popup_exit'+id);
	 element.style.position = "absolute";
	 element.style.visibility = "visible";
	 element.style.display = "block";
	 element.style.left = (screen.width-710)/2+'px';
	 element.style.top = (screen.height-370)/2+'px';
	 drag_element['target'] = 'popup'+id;
	 drag_element.onmousedown = popup_mousedown;
	 exit_element.onclick = popup_exit;
 }
</script>
</head>
<body>
<div class="alarm-log">
       <!--弹出设置层开始-->        	
            <div class="zhanghao-but">
            	<img src="../images/new.png" alt="设置" onclick="popup_show2(0)"/>
                <div class="sample_popup" id="popup0" style="visibility: hidden; display: none;">
                    <div class="menu_form_header" id="popup_drag0">
                        <img  class="menu_form_exit" id="popup_exit0" src="../images/close.png"/>URL监控
                    </div>
                    <div class="menu_form_body">
                    	<div class="dev-notice">
							<g:form method="post" controller="onDuty" action="saveCheckUrl">
								<table border="0" class="new-table">
									<tr>
										<td style="text-align: right;">名称</td>
										<td><input type="hidden" id="id" name="id"/><input type="text" id="name" name="name" /></td>
									</tr>
									<tr>
										<td style="text-align: right;">地址</td>
										<td><input type="text" id="url" name="url" /></td>
									</tr>
									<tr>
										<td style="text-align: right;">超时</td>
										<td><input type="text" id="timeOut" name="timeOut" /></td>
									</tr>
								</table>
								<span class="new-line"></span>
								<div class="new-but">
									<input type="reset" value="重填" class="reset" />
									<input type="submit" value="确定" class="button" />
								</div>
							</g:form>
                        </div>
                    </div>
                </div>
            </div>            
        <!--弹出设置层结束-->       

	<table  border="0" cellpadding="0" cellspacing="0" class="alarm-table notice-table">
	    <tr class="bus-table-tr">
	    	<td class="alarm-table-td">编号</td>
	        <td class="alarm-table-td">名称</td>
	        <td class="alarm-table-td">地址</td>
	        <td class="alarm-table-td">超时</td>
	        <td class="alarm-table-td">失败连续次数</td>                          
	        <td class="alarm-table-td">操作</td>                            
	    </tr>
	    <g:each var="url" in="${result}" status="i">
	 	<tr class="bus-table-tr" id="tr${url.obj.id}">
	 		<td name="id">${i+1}</td>
	         <td name="name">${url.obj.name}</td>
	         <td name="url">${url.obj.url }</td>
	         <td name="timeOut">${url.obj.timeOut }</td>
	         <td name="checkNum">${url.checkNum }</td> 
			<td>
				<a class="_edit_"  href="javascript:void(0)" onclick="editPerson(${url.obj.id})">编辑</a>&nbsp;&nbsp;&nbsp; 
				<a class="_edit_"  href="javascript:void(0)" onclick="delUser(${url.obj.id})">删除</a>
			</td>         
	     </tr>                                        
	    </g:each>
	</table>
</div>
</body>
</html>