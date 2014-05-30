<%@ page import="com.helome.monitor.MenuTitle" %>
<div class="dev-head">当前位置：
<g:each var="menu" in="${ MenuTitle.where{
	actionName == params.action
	controllerName == params.controller
}}">
<label style="color:#e68e8a">${menu.mainMenuName }&gt;${menu.childMenuName }
<g:if test="${!"".equals(menu.lastMenuName) }">
&gt;${menu.lastMenuName }
</g:if>
</label>
</g:each>
</div>