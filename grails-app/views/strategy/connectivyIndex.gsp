<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>宕机通知</title>
    
</head>
<body>
	<ul class="right-menu">
        <li style=" border-right:none;">宕机通知</li>                           
    </ul>
	<div class="alarm-log">
		<div class="zhanghao-but">
			<g:img dir="images" file="notice.png" class="_edit_" />
			<div id="fade" class="black_overlay"></div>
			<div class="dev-new draggable" id="notifyuser_form"></div>
		</div>

		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
				<td class="alarm-table-td">姓名</td>
				<td class="alarm-table-td">电话</td>
				<td class="alarm-table-td">邮箱</td>
				<td class="alarm-table-td">通知方式</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.name}</a></td>
					<td>${row.telephone}</td>
					<td>${row.email}</td>
					<td>${row.notifyway.notifyname}</td>
					<td>
						<g:link action="deleteNotifyUser" params='[nuid:"${row.id}"]'>删除</g:link>
					</td>
				</tr>
			</g:each>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$('._edit_').click(function() {
				if($('.dev-new').children().length > 0) return;
				openWindow('showNotifyUser', {nuid : $(this).attr('data-option')});
			});
			function openWindow(url, data) {
				$.get(url, data || {}, function(data) {
					var _formPage = $(data);
					var notifyuser_form = $('#notifyuser_form');
					notifyuser_form.append(_formPage);
					$('.dev-close').click(function() {
						$(this).parent().hide().children().remove();
					});
					notifyuser_form.show();
				});
			}
		});
	</script>
</body>
</html>