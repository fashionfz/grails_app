<h4>
	<g:if test="${serverAppInstance?.id}">
		修改服务器应用信息
	</g:if>
	<g:else>
		增加服务器应用信息
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close"/>
<g:form method="post" controller="serverInfo" action="saveServerApp">
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td>
				<g:hiddenField name="serverId" value="${params.serverId}"/>
				<g:hiddenField name="id" value="${serverAppInstance?.id}"/>
				${serverAppInstance?.id}
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">应用类型</td>
			<td><g:select name="type" 
					from="${com.helome.monitor.Dictionary.where{codeType=='servicetype'}.list()}" 
					value="${serverAppInstance?.type}" optionKey="value" optionValue="name" required=""/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">应用名</td>
			<td><g:textField name="name" value="${serverAppInstance?.name}" required=""/></td>
		</tr>
		<tr>
			<td style="text-align: right;">安装目录</td>
			<td><g:textField name="contentPath" value="${serverAppInstance?.contentPath}" required=""/></td>
			<td><span class="new-list">应用安装Home目录</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">主端口</td>
			<td><g:textField name="port" value="${join('in':serverAppInstance?.ports?:[])}" required=""/></td>
			<td><span class="new-list">多个端口以英文半角逗号隔开</span></td>
		</tr>
		<%--<tr>
			<td style="text-align: right;">应用指标</td>
			<td><g:select name="indicator" 
					from="${com.helome.monitor.Indicator.where{type==2}.list()}" multiple="true"
					value="${serverAppInstance?.indicators?.id}" optionKey="id" optionValue="name"/>
			</td>
		</tr> --%>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>

