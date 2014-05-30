<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>告警条件</title>

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
				<td class="alarm-table-td">名称</td>
				<td class="alarm-table-td">告警方式</td>
				<td class="alarm-table-td">指标</td>
				<td class="alarm-table-td">条件表达式</td>
				<td class="alarm-table-td">消息模板</td>
				<td class="alarm-table-td">备注</td>
				<td class="alarm-table-td">启用</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.name}</a></td>
					<td><helo:translate type="alarm_type" value="${row.type}"/></td>
					<td>${row.indicator?.name}</td>
					<td>${row.expression}</td>
					<td>${row.message}</td>
					<td>${row.remark}</td>
					<td><helo:translate type="bool" value="${row.enabled }" /></td>
					<td><g:link action="delete" params='[id:"${row.id}"]'>删除</g:link></td>
				</tr>
			</g:each>
		</table>
		<g:paginate next="Forward" prev="Back" total="${total}" params="${params}" />
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
