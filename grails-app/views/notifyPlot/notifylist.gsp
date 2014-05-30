<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>通知策略</title>

</head>
<body>

	<div class="alarm-log">
		<div class="zhanghao-but">
			<g:img dir="images" file="new.png" class="_edit_" />
			<div id="fade" class="black_overlay"></div>
			<div class="dev-new draggable" id="account_form"></div>
		</div>

		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
				<td class="alarm-table-td">编号</td>
				<td class="alarm-table-td">用户名称</td>
				<td class="alarm-table-td">通知类型</td>
				<td class="alarm-table-td">通知方式</td>
				<td class="alarm-table-td">启用</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.object.id}" href="javascript:void(0)">
					${row.object.id}
					</a></td>
					<td>
					<g:each var="usr" in="${row.object.user }">
						${usr.realname}<br />
					</g:each>					
					
					</td>
					<td>
					<g:each var="module" in="${row.modules }">
					${module.name }
					</g:each>
					</td>
					<td>
					<g:each var="way" in="${row.ways }">
					${way.name }
					</g:each>
					</td>
					<td><helo:translate type="bool" value="${row.object.enabled}"/></td>
					<td>
						<g:link action="notifyPlotDel" params='[id:"${row.object.id}"]'>删除</g:link>
					</td>
				</tr>
			</g:each>
		</table>
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
					var account_form = $('#account_form');
					account_form.append(_formPage);
					$('.dev-close').click(function() {
						$(this).parent().hide().children().remove();
					});
					account_form.show();
				});
			}
		});
	</script>
</body>
</html>
