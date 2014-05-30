<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>服务器组</title>

</head>
<body>
	<div class="alarm-log">
		<div class="zhanghao-but">
			<g:img dir="images" file="new.png" class="_edit_" />
			<div id="fade" class="black_overlay"></div>
			<div class="dev-new draggable" id="serverGroup_form"></div>
		</div>

		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
                <td class="alarm-table-td">服务器组</td>
                <td class="alarm-table-td">服务器</td>
                <td class="alarm-table-td">启用</td>                          
                <td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.name}</a></td>
					<td>
						<g:each in="${row.servers}" var="server">
							${server.name} <br/>
						</g:each>
					</td>
					<td><helo:translate type="bool" value="${row.enabled}" /></td>
					<td>
						<g:link action="delete" params='[id:"${row.id}"]'>删除</g:link>
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
					var serverGroup_form = $('#serverGroup_form');
					serverGroup_form.append(_formPage);
					$('.dev-close').click(function() {
						$(this).parent().hide().children().remove();
					});
					serverGroup_form.show();
				});
			}
		});
	</script>
</body>
</html>
