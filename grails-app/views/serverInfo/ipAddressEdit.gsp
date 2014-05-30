<h4>
	<g:if test="${ipaddressInstance?.id}">
		修改服务器IP信息
	</g:if>
	<g:else>
		增加服务器IP信息
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close"/>
<g:form method="post" controller="serverInfo" action="saveIpAddress">
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td>
				<g:hiddenField name="serverId" value="${params.serverId}"/>
				<g:hiddenField name="id" value="${ipaddressInstance?.id}"/>
				${ipaddressInstance?.id}
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">IP类型</td>
			<td><g:select name="type" 
					from="${com.helome.monitor.Dictionary.where{codeType=='iptype'}.list()}" 
					value="${ipaddressInstance?.type}" optionKey="value" optionValue="name" required=""/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">IP地址</td>
			<td><g:textField name="ipAddress" value="${ipaddressInstance?.ipAddress}" required=""/></td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>

