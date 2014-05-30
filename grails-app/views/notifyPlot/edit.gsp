<h4>
	<g:if test="${notifyPlot?.id}">
		修改通知策略
	</g:if>
	<g:else>
		新建通知策略
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close"/>
<g:form method="post" controller="notifyPlot" action="notifyPlotSave">
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td><g:hiddenField name="id" value="${notifyPlot?.id}"/>${notifyPlot?.id}</td>
		</tr>
		<tr>
			<td style="text-align: right;">用户名称</td>
			<td>
			<g:select name="user" from="${com.helome.monitor.User.list()}" value="${notifyPlot?.user?.id }" multiple="true" required="" optionKey="id" optionValue="realname" style="height: 70px;"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">通知类型</td>
			<td>
				<g:select name="notifyCapability" from="${com.helome.monitor.Dictionary.where{ codeType == 'alarm_module'}.list()}" value="${notifyPlot?.notifyCapability?.value }" multiple="true" required="" optionKey="value" optionValue="name" style="height: 70px;"/>
			</td>
			<td><span class="new-list"></span></td>
		</tr>
		<tr>
			<td style="text-align: right;">通知方式</td>
			<td>
			<g:select name="notifyWay" from="${com.helome.monitor.Dictionary.where{ codeType == 'alarm_type'}.list()}" value="${notifyPlot?.notifyWay }" optionKey="value" optionValue="name"/>
			</td>
			<td><span class="new-list"></span></td>
		</tr>
		<tr>
			<td style="text-align: right;">启用</td>
			<td>
				<g:radio checked="${notifyPlot==null?true:notifyPlot.enabled == 1}" name="enabled" value="1"/>YES&nbsp;&nbsp;&nbsp;
				<g:radio checked="${notifyPlot?.enabled == 0}" name="enabled" value="0"/>NO
			</td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>