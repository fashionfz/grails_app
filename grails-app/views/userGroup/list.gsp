<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>用户组</title>

</head>
<body>
	<div class="alarm-log">
		<div class="zhanghao-but">
			<g:img dir="images" file="new.png" class="_edit_" />
			<div id="fade" class="black_overlay"></div>
			<div class="dev-new draggable" id="userGroup_form"></div>
		</div>
		<table border="0" cellpadding="0" cellspacing="0" class="alarm-table">
			<tr>
				<td class="alarm-table-td">用户组</td>
				<td class="alarm-table-td">用户</td>
				<td class="alarm-table-td">类型</td>
				<td class="alarm-table-td">备注</td>
				<td class="alarm-table-td">启用</td>
				<td class="alarm-table-td">操作</td>
			</tr>
			<g:each in="${rows}" status="i" var="row">
				<tr class="bus-table-tr">
					<td><a class="_edit_" data-option="${row.id}" href="javascript:void(0)">${row.name}</a></td>
					<td style="width: 11%; word-break: break-all; word-wrap: break-word;">
						<g:each in="${row.users}" var="user">
							${user.realname}<br>
						</g:each>
					</td>
					<td>
					<g:if test="${row.groupType==1 }">
					内置
					</g:if>
					<g:else>
					用户
					</g:else>
					</td>
					<td>${row.remark}</td>
					<td><helo:translate type="bool" value="${row.enabled}"/></td>
					<td>
					<g:if test="${row.groupType==0 }">
						<g:link action="delete" params='[id:"${row.id}"]'>删除</g:link>
					</g:if>
					</td>
				</tr>
			</g:each>
		</table>
		<g:paginate next="Forward" prev="Back" total="${totalCount}" params="${params}"/>
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
					var userGroup_form = $('#userGroup_form');
					userGroup_form.append(_formPage);
					$('.dev-close').click(function() {
						$(this).parent().hide().children().remove();
					});
					userGroup_form.show();
				});
			}
		});
	</script>
</body>
</html>
