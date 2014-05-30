<h4>
	<g:if test="${notifyUserInstance?.id}">
		修改被通知人信息
	</g:if>
	<g:else>
		新建被通知人
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close"/>
<g:form controller="strategy" action="saveNotifyUser" method="post">
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td><g:hiddenField name="nuid" value="${notifyUserInstance?.id }" />${notifyUserInstance?.id}</td>
		</tr>
		<tr>
			<td style="text-align: right;">姓名</td>
			<td><g:textField name="name" value="${notifyUserInstance?.name}" required=""/></td>
			<td><span class="new-list">用户账户可用数字和字母,不能用特殊字符.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">电话</td>
			<td><g:textField name="telephone" value="${notifyUserInstance?.telephone}" required=""/></td>
		</tr>
		<tr>
			<td style="text-align: right;">邮箱</td>
			<td><g:textField name="email" value="${notifyUserInstance?.email}" required=""/></td>
			<td><span class="new-list">密码只能输入字母大小写,数字,-,不允许输入其他特殊字符.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">通知方式</td>
			<td><g:select name="notifyway.id" from="${com.helome.monitor.Notifyway.list()}"
						optionKey="id" optionValue="notifyname"
						value="${notifyUserInstance?.notifyway?.id}" noSelection="['':'--选择--']" required="true"/>
			</td>
			<td><span class="new-list">密码只能输入字母大小写,数字,-,不允许输入其他特殊字符.</span></td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>
