<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>服务器IP信息</title>

</head>
<body>
	<ul class="right-menu">
		<li style="border-right: none;">${serverInfoInstance?.name}</li>
	</ul>

	<div class="alarm-log">
		<div class="zhanghao-but">
			<g:img dir="images" file="new.png" class="_edit_" />
			<div id="fade" class="black_overlay"></div>
			<div class="dev-new draggable" id="server_form"></div>
		</div>

		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
				<td class="alarm-table-td">IP类型</td>
				<td class="alarm-table-td">IP地址</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${serverInfoInstance?.ifs}" var="row">
				<tr class="bus-table-tr">
					<td><helo:translate type="iptype" value="${row.type}"/></td>
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.ipAddress}</a></td>
					<td>
						<g:link action="ipAddressDelete" params='[id:"${row.id}",serverId:"${serverInfoInstance?.id}"]'>删除</g:link>
					</td>
				</tr>
			</g:each>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$('._edit_').click(function() {
				if($('.dev-new').children().length > 0) return;
				openWindow('../ipAddressEdit', {id : $(this).attr('data-option'),serverId: ${serverInfoInstance?.id}});
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
