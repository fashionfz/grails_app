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
			$("#id").val($(this).text());
		}else if($(this).attr("name") == "name"){
			$("#name").val($(this).text());
		}else if($(this).attr("name") == "telephone"){
			$("#telephone").val($(this).text());
		}else if($(this).attr("name") == "role"){
			$("#role").val($(this).text());
		}else if($(this).attr("name") == "email"){
			$("#email").val($(this).text());
		}else if($(this).attr("name") == "way"){
			var count=$("#way").get(0).options.length;
			for(var i=0;i<count;i++){
				if($("#way").get(0).options[i].text == $(this).text())  
				{
					$("#way").get(0).options[i].selected = true;          
					break;  
				}  
			}
			
		}
	});
}
function clear(){
	$("#id").val("");
	$("#name").val("");
	$("#telephone").val("");
	$("#role").val("");
	$("#email").val("");
	$("#way").get(0).options[0].selected = true;      
}
 function delUser(id){
	window.location = "deletePerson?id="+id;
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
	<ul class="right-menu">
		<li style="border-right: none;">值班人员管理</li>
	</ul>
<div class="alarm-log">
       <!--弹出设置层开始-->        	
            <div class="zhanghao-but">
            	<img src="../images/new.png" alt="设置" onclick="popup_show2(0)"/>
                <div class="sample_popup" id="popup0" style="visibility: hidden; display: none;">
                    <div class="menu_form_header" id="popup_drag0">
                        <img  class="menu_form_exit" id="popup_exit0" src="../images/close.png"/>编辑值班人员
                    </div>
                    <div class="menu_form_body">
                    	<div class="dev-notice">
							<g:form method="post" controller="role" action="savePerson">
								<table border="0" class="new-table">
									<tr>
										<td style="text-align: right;">姓名</td>
										<td><input type="hidden" id="id" name="id"/><input type="text" id="name" name="name" /></td>
									</tr>
									<tr>
										<td style="text-align: right;">电话</td>
										<td><input type="text" id="telephone" name="telephone" /></td>
									</tr>
									<tr>
										<td style="text-align: right;">角色</td>
										<td><input type="text" id="role" name="role" /></td>
									</tr>
									<tr>
										<td style="text-align: right;">邮箱</td>
										<td><input type="text" id="email" name="email" /></td>
									</tr>								
									<tr>
										<td style="text-align: right;">通知方式</td>
										<td>
											 <g:select style="height:25px" id="way" name="way" from="${com.helome.monitor.Notifyway.list()}" required="" optionKey="id" optionValue="notifyname"/>
										</td>
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
	        <td class="alarm-table-td">姓名</td>
	        <td class="alarm-table-td">电话</td>
	        <td class="alarm-table-td">角色</td>
	        <td class="alarm-table-td">邮箱</td>
	        <td class="alarm-table-td">通知方式</td>                           
	        <td class="alarm-table-td">操作</td>                            
	    </tr>
	</table>
</div>
</body>
</html>