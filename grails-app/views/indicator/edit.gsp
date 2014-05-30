<%@page import="com.helome.monitor.Dictionary"%>
<h4>
	<g:if test="${indicatorInstance?.id}">
		修改用户组
	</g:if>
	<g:else>
		添加用户组
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close" />
<g:form controller="indicator" action="save" method="post" >
	<table border="0" class="new-table">
		<tr>
			<td>编号</td>
			<td><g:hiddenField name="id" value="${indicatorInstance?.id}"/>${indicatorInstance?.id}</td>
		</tr>
		<tr>
			<td style="text-align: right;">名称</td>
			<td>
				<g:textField name="name" value="${indicatorInstance?.name }"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">编码</td>
			<td><g:textField name="code" value="${indicatorInstance?.code }"/></td>
			<td><span class="new-list">暂时无用</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">指标命令</td>
			<td><g:textField name="command" value="${indicatorInstance?.command }"/></td>
			<td><span class="new-list">暂时无用</span></td>
		</tr>
		<tr>
			<td style="text-align: right;">备注</td>
			<td><g:textField name="remark" value="${indicatorInstance?.remark }"/></td>
		</tr>
		<tr>
			<td style="text-align: right;">指标类型</td>
			<td>
				<g:select from="${com.helome.monitor.Dictionary.where{codeType=='indicatortype'}.list() }"
					name="type" value="${indicatorInstance?.type }"
					optionKey="value" optionValue="name" />
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">启用</td>
			<td>
				<g:radio checked="${indicatorInstance?.enabled == 1}" name="enabled" value="1"/>YES&nbsp;&nbsp;&nbsp;
				<g:radio checked="${indicatorInstance?.enabled == 0}" name="enabled" value="0"/>NO
			</td>
		</tr>
	</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" />
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>

