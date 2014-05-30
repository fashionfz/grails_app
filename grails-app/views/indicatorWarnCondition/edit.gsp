<%@page import="com.helome.monitor.Indicator"%>
<%@page import="com.helome.monitor.Dictionary"%>
<h4>
	<g:if test="${indicatorWarnConditionInstance?.id}">
		修改告警条件
	</g:if>
	<g:else>
		新建告警条件
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close"/>
<g:form method="post" controller="indicatorWarnCondition" action="save">
		<table border="0" class="new-table">
			<tr>
				<td>编号</td>
				<td>
					<g:hiddenField name="id" value="${indicatorWarnConditionInstance?.id}"/>${indicatorWarnConditionInstance?.id}
				</td>
			</tr>
			<tr>
				<td>名称</td>
				<td><g:textField name="name" value="${indicatorWarnConditionInstance?.name}"/></td>
			</tr>
			<tr>
				<td>条件表达式</td>
				<td><g:textField name="expression" value="${indicatorWarnConditionInstance?.expression}"/></td>
			</tr>
			<tr>
				<td>消息模板</td>
				<td><g:textField name="message" value="${indicatorWarnConditionInstance?.message}"/></td>
			</tr>
			<tr>
				<td>备注</td>
				<td><g:passwordField name="remark" value="${indicatorWarnConditionInstance?.remark}"/></td>
			</tr>
			<tr>
				<td>告警方式</td>
				<td>
					<g:select name="type" from="${com.helome.monitor.Dictionary.where{codeType=='alarm_type'}.list() }" 
						optionKey="value" optionValue="name" value="${indicatorWarnConditionInstance?.type}"/>
				</td>
			</tr>
			<tr>
				<td>指标</td>
				<td>
					<g:select name="indicatorId" from="${com.helome.monitor.Indicator.list() }" optionKey="id" optionValue="name" 
						value="${indicatorWarnConditionInstance?.indicator?.id}"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">启用</td>
				<td>
					<g:radio checked="${indicatorWarnConditionInstance?.enabled == 1}" name="enabled" value="1"/>YES&nbsp;&nbsp;&nbsp;
					<g:radio checked="${indicatorWarnConditionInstance?.enabled == 0}" name="enabled" value="0"/>NO
				</td>
			</tr>
		</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" /> 
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>