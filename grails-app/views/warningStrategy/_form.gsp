<%@ page import="com.helome.monitor.WarningStrategy" %>
<%@ page import="com.helome.monitor.Dictionary" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
 %>
 
 <div style="width:300px; float:left;">
        <label for="name">
            策略名称：*
        </label>
        <g:if test="${warningStrategyInstance}">
        	<input type="text" name="name" id="name" value="${warningStrategyInstance?.name}"/>
        </g:if>
        <g:else>
        	<input type="text" name="name" id="name" value="${df.format(new Date())}"/>
		</g:else>
    </div>

	<div style="overflow: hidden; margin-left:10px;">
    <div style="width:300px; float:left;">
        <label for="enabled">
            启用：*
        </label>
        <g:select optionKey="value" optionValue="name" id="enabled" name="enabled" from="${Dictionary.where { codeType == 'bool' }.list()}" value="${warningStrategyInstance?.enabled}"/>
    </div>


<g:hiddenField name="userIds" id="users"/>
<g:hiddenField name="serverConditions" id="serverConditions"/>

<p class="line" style="margin-top:0;"></p>
</div>
<!--<p class="line" style="margin-top:0;"></p>-->

<div style="width:200px; margin-top:20px;">
    服务器告警策略： <br/>
    <ul id="st" class="easyui-tree" style="width: 350px;"></ul>
</div>
<script type="text/javascript">
    $(function(){
        //用户树
//        var usersTreeData = getUsersTree();
//        $('#ut').tree({
//            data: usersTreeData,
//            checkbox: true,
//            lines: true
//        });
        //服务器告警策略树
        var serversTreeData = getServersTree();
        $('#st').tree({
            data: serversTreeData,
            checkbox: true,
            lines: true
        });
    });
    //获取服务器
//    function getUsersTree(){
//        var result;
//        $Q.ajax({
//            url: "usersTree?id=${warningStrategyInstance?.id?:0}",
 //           dataType: 'json',
 //           async: false,
//            success: function(data){
 //               result = data;
 //           }
 //       });

 //       return result;
 //   }
    //获取服务器
    function getServersTree(){
        var result;
        $Q.ajax({
            url: "serversTree?id=${warningStrategyInstance?.id?:0}",
            dataType: 'json',
            async: false,
            success: function(data){
                result = data;
            }
        });

        return result;
    }
</script>
%{--
<div class="fieldcontain ${hasErrors(bean: warningStrategyInstance, field: 'disabled', 'error')} required">
	<label for="disabled">
		<g:message code="warningStrategy.disabled.label" default="Disabled" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="disabled" type="number" value="${warningStrategyInstance.disabled}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: warningStrategyInstance, field: 'enabled', 'error')} required">
	<label for="enabled">
		<g:message code="warningStrategy.enabled.label" default="Enabled" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="enabled" type="number" value="${warningStrategyInstance.enabled}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: warningStrategyInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="warningStrategy.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${warningStrategyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: warningStrategyInstance, field: 'serverWarnConditions', 'error')} ">
	<label for="serverWarnConditions">
		<g:message code="warningStrategy.serverWarnConditions.label" default="Server Warn Conditions" />
		
	</label>
	<g:select name="serverWarnConditions" from="${com.helome.monitor.ServerInfoWarnCondition.list()}" multiple="multiple" optionKey="id" size="5" value="${warningStrategyInstance?.serverWarnConditions*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: warningStrategyInstance, field: 'users', 'error')} ">
	<label for="users">
		<g:message code="warningStrategy.users.label" default="Users" />
		
	</label>
	<g:select name="users" from="${com.helome.monitor.User.list()}" multiple="multiple" optionKey="id" size="5" value="${warningStrategyInstance?.users*.id}" class="many-to-many"/>
</div>
--}%

