<script type="text/javascript">

function  checkName(){
	var id = $("#serverId").val();
	var name = $("#serverName").val();
	if(id==null||id==''){
		$.post("checkServerInfoName?name="+name,function(data){
			if(data.result == 1){
				alert("服务器名称已存在！")
				$("#serverName").val("");
			}
		});
	}
}

</script>
<h4>
	<g:if test="${serverInfoInstance?.id}">
		修改服务器信息
	</g:if>
	<g:else>
		新建服务器信息
	</g:else>
</h4>
<g:img dir="images" file="close.png" width="20" height="18" class="dev-close"/>
<g:form method="post" controller="serverInfo" action="save">
		<table border="0" class="new-table">
			<tr>
				<td>编号</td>
				<td>
					<g:hiddenField name="id" id="serverId" value="${serverInfoInstance?.id}"/>${serverInfoInstance?.id}
				</td>
			</tr>
			<tr>
				<td>服务器名</td>
				<td><g:textField name="name" id="serverName" value="${serverInfoInstance?.name}" onblur="checkName()"/></td>
			</tr>
			<tr>
				<td>操作系统</td>
				<td><g:textField name="os" value="${serverInfoInstance?.os}"/></td>
			</tr>
			<tr>
				<td>登陆账号</td>
				<td><g:textField name="login" value="${serverInfoInstance?.login}"/></td>
			</tr>
			<tr>
				<td>登陆密码</td>
				<td><g:passwordField name="password" value="${serverInfoInstance?.password}"/></td>
			</tr>
			<tr>
				<td>物理位置</td>
				<td><g:textField name="computerRoom" value="${serverInfoInstance?.computerRoom}"/></td>
			</tr>
			<tr>
				<td>监控方式</td>
				<td><g:textField name="monitorType" value="${serverInfoInstance?.monitorType}"/></td>
			</tr>
			<tr>
				<td>连通测试端口</td>
				<td><g:textField name="connectPort" value="${serverInfoInstance?.connectPort}"/></td>
				<td><span class="new-list">连通性测试端口,后期可作为远程登陆端口</span></td>
			</tr>
			<tr>
				<td>性能指标</td>
				<td><g:select name="indicators.id" from="${com.helome.monitor.Indicator.where{type==1}.list()}" multiple="true"
					optionKey="id" optionValue="name" value="${serverInfoInstance?.indicators?.id }" required="" style=" height:70px; "/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">SSH</td>
				<td>
					<g:radio checked="${serverInfoInstance?.allowedSSH == 1}" name="allowedSSH" value="1"/>YES&nbsp;&nbsp;&nbsp;
					<g:radio checked="${serverInfoInstance?.allowedSSH == 0}" name="allowedSSH" value="0"/>NO
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">启用</td>
				<td>
					<g:radio checked="${serverInfoInstance?.enabled == 1}" name="enabled" value="1"/>YES&nbsp;&nbsp;&nbsp;
					<g:radio checked="${serverInfoInstance?.enabled == 0}" name="enabled" value="0"/>NO
				</td>
			</tr>
		</table>
	<span class="new-line"></span>
	<div class="new-but">
		<input type="reset" value="重填" class="reset" /> 
		<input type="submit" value="确定" class="button" />
	</div>
</g:form>

