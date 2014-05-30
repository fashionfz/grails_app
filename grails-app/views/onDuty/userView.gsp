<%@ page import="com.helome.monitor.Role" %>
<%@ page import="com.helome.monitor.MonitorMenu" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="layout" content="main"/>
<title>对象---用户视图</title>
<script type="text/javascript" src="../js/userview.js"></script>
</head>
<body>
<div class="alarm-log">
	请选择配置的角色：<g:select style="height:25px" optionKey="id" value="${roleId }" optionValue="realname" id="roleId" name="roleselect" from="${Role.where{name != 'ROLE_ADMIN'} }" onchange="changeRole()"/>
	<g:set var="role" value="${roleId==null?Role.where{name != 'ROLE_ADMIN'}.list().get(0):Role.get(roleId) }"></g:set>
	<!-- 一级 -->
	<g:each var="menu" in="${MonitorMenu.where{parent == null} }" status="i">
		<div style="height:0px"></div>
	<g:if test="${role!=null && role.menus.contains(menu) }">
	<g:checkBox id="box${menu.id }" onclick="selectMenu(this,${menu.id },'${menu.path }')" style="display:inline" name="${menu.id }" value="1"/><div style="display:inline" id="imgDiv${menu.id }"><img src="../images/hide.png" onclick="show(this,${menu.id })" /></div><p style="display:inline" >${menu.menuName }</p>
	</g:if>
	<g:else>
	<g:checkBox id="box${menu.id }" onclick="selectMenu(this,${menu.id },'${menu.path }')" style="display:inline" name="${menu.id }"/><div style="display:inline" id="imgDiv${menu.id }"><img src="../images/hide.png" onclick="show(this,${menu.id })" /></div><p style="display:inline" >${menu.menuName }</p>
	</g:else>
	<!-- 二级 -->
	<div id="div${menu.id }" style="display:none"/>
	<g:each var="children" in="${menu.child }">
	<div style="height:0px"></div>
	<g:if test="${role!=null && role.menus.contains(children) }">
	&nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox id="box${children.id }" onclick="selectMenu(this,${children.id },'${children.path }')" style="display:inline" name="${children.id }" value="1"/><div style="display:inline" id="imgDiv${children.id }"><img src="../images/hide.png" onclick="show(this,${children.id })" /></div><p style="display:inline">${children.menuName }</p>
	</g:if>
	<g:else>
	&nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox id="box${children.id }" onclick="selectMenu(this,${children.id },'${children.path }')" style="display:inline" name="${children.id }"/><div style="display:inline" id="imgDiv${children.id }"><img src="../images/hide.png" onclick="show(this,${children.id })" /></div><p style="display:inline">${children.menuName }</p>
	</g:else>
	<!-- 三级 -->
	<div id="div${children.id }" style="display:none"/>
	<g:each var="child2" in="${children.child }">
	<div style="height:0px"></div>
	<g:if test="${role!=null && role.menus.contains(child2) }">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox id="box${child2.id }" onclick="selectMenu(this,${child2.id },'${child2.path }')" name="${child2.id }" value="1"/>${child2.menuName }
	</g:if>
	<g:else>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:checkBox id="box${child2.id }" onclick="selectMenu(this,${child2.id },'${child2.path }')" name="${child2.id }"/>${child2.menuName }
	<br />
	</g:else>	
	</g:each>
	</div>
	</g:each>
	</div>
	</g:each>
	<div class="new-but"><input type="button" value="确定"  class="button" onclick="saveConfig()"/></div>
</div>
</body>
</html>