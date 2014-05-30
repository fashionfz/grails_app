<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>账户信息</title>
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
				<td class="alarm-table-td">用户账号</td>
				<td class="alarm-table-td">真实姓名</td>
				<td class="alarm-table-td">验证方式</td>
				<td class="alarm-table-td">邮箱地址</td>
				<td class="alarm-table-td">手机号</td>
				<td class="alarm-table-td">启用</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.username}</a></td>
					<td>${row.realname}</td>
					<td><helo:translate type="validateType" value="${row.validateType}"/></td>
					<td>${row.email}</td>
					<td>${row.phone}</td>
					<td><helo:translate type="bool" value="${row.enabled}"/></td>
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
