<h4>
	<g:if test="${serverGroupInstance?.id}">
		修改服务器组
	</g:if>
	<g:else>
		新建服务器组
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close" />
<g:form controller="serverGroup" method="post" action="save">
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td><g:hiddenField name="id" value="${serverGroupInstance?.id}"/>${serverGroupInstance?.id}</td>
		</tr>
		<tr>
			<td style="text-align: right;">服务器组名</td>
			<td><g:textField name="name" value="${serverGroupInstance?.name}"/></td>
			<td><span class="new-list">此处填写服务器组的名称</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">服务器</td>
			<td><g:select name="servers" from="${com.helome.monitor.ServerInfo.where{disabled==0}.list()}" multiple="true" 
				value="${serverGroupInstance?.servers?.id }" optionKey="id" optionValue="name" style=" height: 70px;"/></td>
			<td><span class="new-list">此处列表可读取服务器的名称,可选多个服务器.</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">启用</td>
			<td>
				<g:radio checked="${serverGroupInstance?.enabled == 1}" name="enabled" value="1"/>YES&nbsp;&nbsp;&nbsp;
				<g:radio checked="${serverGroupInstance?.enabled == 0}" name="enabled" value="0"/>NO
			</td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>