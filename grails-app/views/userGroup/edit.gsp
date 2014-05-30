<h4>
	<g:if test="${userGroupInstance?.id}">
		修改用户组
	</g:if>
	<g:else>
		添加用户组
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close" />
<g:form controller="userGroup" action="save" method="post" >
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td><g:hiddenField name="id" value="${userGroupInstance?.id}"/>${userGroupInstance?.id}</td>
		</tr>
		<tr>
			<td style="text-align: right;">用户组</td>
			<td><g:textField name="name" value="${userGroupInstance?.name }"/></td>
			<td><span class="new-list">此处填写用户组名字</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">用户</td>
			<td>
				<g:select from="${com.helome.monitor.User.where{disabled==0}.list() }"
					name="users" value="${userGroupInstance?.users?.id }"
					optionKey="id" optionValue="realname" multiple="true" style="height: 70px;"/>
			</td>
			<td><span class="new-list">此处按住Ctrl键可选多个用户.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">备注</td>
			<td><g:textField name="remark" value="${userGroupInstance?.remark }"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">启用</td>
			<td>
				<g:radio checked="${userGroupInstance?.enabled == 1}" name="enabled" value="1"/>YES&nbsp;&nbsp;&nbsp;
				<g:radio checked="${userGroupInstance?.enabled == 0}" name="enabled" value="0"/>NO
			</td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>

