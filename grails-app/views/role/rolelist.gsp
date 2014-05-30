<%@ page import="com.helome.monitor.Role" %>

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
<script language="javascript" type="text/javascript" src="../js/tanchu.js"></script><!--弹出层效果-->
<script type="text/javascript">
<%
if("1".equals(request.getAttribute("code")))
{
	out.println("alert('删除失败，请先解除与用户以及菜单的关系！');");
}
 %>
</script>
</head>
<body>
<div class="alarm-log">
        <!--弹出设置层开始-->        	
            <div class="zhanghao-but">
            	<img src="../images/new.png" alt="设置" onclick="popup_show2(1)"/>
                <div class="sample_popup" id="popup1" style="visibility: hidden; display: none;">
                    <div class="menu_form_header" id="popup_drag1">
                        <img  class="menu_form_exit" id="popup_exit1" src="../images/close.png"/>角色编辑
                    </div>
                    <div class="menu_form_body">
                    	<div class="dev-notice">
							<g:form method="post" controller="role" action="saveRole">
								<table border="0" class="new-table">
									<tr>
										<td style="text-align: right;">角色名称</td>
										<td><input type="hidden" id="id" name="id"/><input type="text" id="name" name="name" /></td>
									</tr>
									<tr>
										<td style="text-align: right;">角色中文名称</td>
										<td><input type="text" id="realname" name="realname" /></td>
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
		<td class="alarm-table-td">角色名称</td>
		<td class="alarm-table-td">角色中文名称</td>
		<td class="alarm-table-td">操作</td>
	</tr>
	<g:each var="role" in="${Role.where{id>0} }">
		<tr class="bus-table-tr" id="tr${role.id}">
		    <td name="id">${role.id }</td>
			<td name="name">${role.name }</td>
			<td name="realname">${role.realname }</td>
			<td><a href="javascript:void(0)" onclick="editRole(${role.id})">修改</a>
			&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="delRole(${role.id})">删除</a></td>
		</tr>
	</g:each>
</table>
</div>
<script type="text/javascript">
function editRole(id){
 popup_show2(1);
	$("#tr"+id).find("td").each(function(){
		if($(this).attr("name") == "id"){
			$("#id").val($(this).text());
		}else if($(this).attr("name") == "name"){
			$("#name").val($(this).text());
		}else if($(this).attr("name") == "realname"){
			$("#realname").val($(this).text());
		}
	});
}

function delRole(id){
	window.location = "deleteRole?roleid="+id;
}
function clear(){
	$("#id").val("");
	$("#name").val("");
	$("#realname").val("");
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
	 element.style.top = (screen.height-260)/2+'px';
	 drag_element['target'] = 'popup'+id;
	 drag_element.onmousedown = popup_mousedown;
	 exit_element.onclick = popup_exit;
}
</script>
</body>
</html>