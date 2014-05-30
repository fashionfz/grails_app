<%@page import="com.helome.monitor.utils.MonitorProxy" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>告警信息</title>
    <r:require module="datepicker"/>
</head>
<body>
	<div class="alarm-log">
		<div class="dev-alarm-box">
			<g:form url="[action:'messages',controller:'monitor']" method="get">
				<div class="dev-xingneng">
					<i class="dev-xingneng-txt">按时间范围</i>
					<input type="text" name="startDate" value="${params.startDate}" class="log-time">
					<i class="log-list">—</i>
					<input type="text" name="endDate" value="${params.endDate}" class="log-time">
				</div>
				<table class="alaerm-table-one" border="0">
					<tr>
						<td>按服务器名</td>
						<td><g:select name="hostname"
								from="${com.helome.monitor.ServerInfo.list()}"
								value="${params.hostname}" class="log-table-select"
								optionKey="name" optionValue="name"
								noSelection="['':'--选择服务器--']" /></td>
					</tr>
					<tr>
						<td>按监控指标</td>
						<td><g:select name="indicatorname"
								from="${com.helome.monitor.Indicator.list()}"
								value="${params.indicatorname}" class="log-table-select"
								optionKey="id" optionValue="name"
								noSelection="['':'--选择指标--']" /></td>
						<td><g:submitButton name="submit" value="查询" class="btn-default btn-blue" /></td>
					</tr>
				</table>
			</g:form>
		</div>
		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table notice-table">
			<tr class="bus-table-tr">
				<g:each var="cln" in="${columu }">
					<td class="alarm-table-td">${cln.labelName }</td>
				</g:each>
			</tr>
			
			<g:each var="item" in="${rows.obj }">
				<tr class="bus-table-tr">
					<g:each var="cln" in="${columu }">
						<td>${MonitorProxy.getter(item,cln.fieldName) }</td>
					</g:each>		
		    	</tr>	
			</g:each>
		</table>
		<g:paginate next="Forward" prev="Back" total="${rows.totalCount}" params="${params}"/>
	</div>
	<script type="text/javascript" src="${resource(dir:'js',file:'widget.js')}"></script>
</body>
</html>