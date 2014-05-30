<%@ page import="com.helome.monitor.ServerGroup; com.helome.monitor.ServerInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>告警信息</title>
</head>
<body>
	<div class="alarm-log">
		<div class="dev-alarm-box">
			<g:form url="[action:'warnings',controller:'monitor']" method="get" id="conditionForm" name="conditionForm">
				<table class="alaerm-table-one">
					<tr>
						<td>按服务器组</td>
						<td><g:select name="servergroup" id="servergroup"
								from="${ServerGroup.where{disabled == 0}.list()}" optionKey="id"
								optionValue="name" class="log-table-select"
								value="${params.servergroup}" noSelection="['':'--选择服务器组--']" />
						</td>
						<td><g:submitButton name="submit" value="查询"
								class="btn-default btn-blue" /></td>
					</tr>
					<tr>
						<td>按服务器名</td>
						<td><g:select name="serverId" id="serverId"
								from="${ServerInfo.where{disabled == 0}.list()}"
								value="${params.serverId}" class="log-table-select"
								optionKey="id" optionValue="name" noSelection="['':'--选择服务器--']" />
						</td>
						<td><g:submitButton name="reset" type="reset" value="重置"
								class="btn-default btn-blue" /></td>
					</tr>
				</table>
			</g:form>
		</div>
		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
				<td class="alarm-table-td">时间</td>
				<td class="alarm-table-td">服务器组</td>
				<td class="alarm-table-td">服务器名</td>
				<td class="alarm-table-td">IP</td>
				<td class="alarm-table-td">类型</td>
				<td class="alarm-table-td">内容</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td>${row.alarmTime}</td>
					<td>${row.serverGroupName}</td>
					<td>${row.serverName}</td>
					<td>${row.serverIp}</td>
					<td>${row.indicatorName}</td>
					<td>${row.expressionValue }</td>
				</tr>
			</g:each>
		</table>
		<g:paginate next="Forward" prev="Back" total="${total}" params="${params}"/>
	</div>
</body>
</html>