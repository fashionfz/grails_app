<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>服务器信息</title>

</head>
<body>

	<div class="alarm-log">
		<div class="zhanghao-but">
			<g:img dir="images" file="new.png" class="_edit_" />
			<div id="fade" class="black_overlay"></div>
			<div class="dev-new draggable" id="server_form"></div>
		</div>

		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
				<td class="alarm-table-td">服务器名</td>
				<td class="alarm-table-td">操作系统</td>
				<td class="alarm-table-td">外网IP</td>
				<td class="alarm-table-td">所属服务器组</td>
				<td class="alarm-table-td">性能指标</td>
				<td class="alarm-table-td">账号</td>
				<td class="alarm-table-td">所属机房</td>
				<td class="alarm-table-td">启用状态</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.name}</a></td>
					<td>${row.os}</td>
					<td>${com.helome.monitor.ServerIf.findByServerInfoAndType(row,2)?.ipAddress}</td>
					<td>
						<g:each in="${row.groups}" var="group">
							${group.name}<br>
						</g:each>
					</td>
					<td>
						<g:each in="${row.indicators}" var="indicator">
							${indicator.name}<br>
						</g:each>
					</td>
					<td>${row.login}</td>
					<td>${row.computerRoom}</td>
					<td><helo:translate type="bool" value="${row.enabled }"/></td>
					<td><g:link action="listIPAddress" params='[id:"${row.id}"]'>IP信息</g:link>&nbsp;&nbsp;&nbsp; 
						<g:link action="listServerApp" params='[id:"${row.id}"]'>应用信息</g:link>&nbsp;&nbsp;&nbsp; 
						<g:link action="delete" params='[id:"${row.id}"]'>删除</g:link>
					</td>
				</tr>
			</g:each>
		</table>
		<g:paginate next="Forward" prev="Back" total="${total}" params="${params}"/>
	</div>
	<script type="text/javascript">
		$(function() {
			$('._edit_').click(function() {
				if($('.dev-new').children().length > 0) return;
				openWindow('edit', {id : $(this).attr('data-option')});
			});
			function openWindow(url, data) {
				$.get(url, data || {}, function(data) {
					var _formPage = $(data);
					var server_form = $('#server_form');
					server_form.append(_formPage);
					$('.dev-close').click(function() {
						$(this).parent().hide().children().remove();
					});
					server_form.show();
				});
			}
		});
	</script>

</body>
</html>
