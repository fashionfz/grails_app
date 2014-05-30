<%@ page import="java.lang.Integer"%>
<%@ page import="com.helome.monitor.UserOperationLog" %>
<%@ page import="com.helome.monitor.OperationMap" %>
<%@ page import="com.helome.monitor.User" %>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main"/>
<title>日志---操作日志</title>
<r:require module="datepicker"/>

<script type="text/javascript">
function query(pageIndex){
	var pageCount = $("#pgCount").val();
	var allCount = ${allCount==null?0:allCount};
	if(allCount>0){
		if(pageIndex>0){
			$("#pageIndex").val(pageIndex);
			$("#pageCount").val(pageCount);
			$("#frm").submit();
			//window.location = "userlog?pageIndex="+pageIndex+"&pageCount="+pageCount;
		}else{
			if(allCount%pageCount==0){
				pageIndex = parseInt(allCount/pageCount);
			}else{
				pageIndex = parseInt(allCount/pageCount)+1;
			}
			
			if(pageIndex == 0){
				pageIndex =1;
			}
			$("#pageIndex").val(pageIndex);
			$("#pageCount").val(pageCount);
			$("#frm").submit();
			//window.location = "userlog?pageIndex="+pageIndex+"&pageCount="+pageCount;
		}
		
	}else{
		$("#pageIndex").val(pageIndex);
		$("#pageCount").val(pageCount);
		$("#frm").submit();
	}
}
</script>
</head>
<body>

	<div class="alarm-log">
		<div class="dev-alarm-box">
                <form id="frm" method="post" action="userlog">
 					<table border="0" cellpadding="2" cellspacing="0" class="log-bale"> 
                    	<tr>
                        	<td style="text-align:right">按时间范围</td>                   	
                           <td colspan="2">
                            	<input type="text" name="startTime" value="${params.startTime }" class="log-time" id="d241">
                                <i class="log-list">到</i>
                            	<input type="text" name="endTime" value="${params.endTime }" class="log-time" id="d242">
                            </td>                           
                        </tr>
                        <tr>
                        	<td style="text-align:right">按日志类型</td>
                            <td>
                           		<g:select class="log-table-select" optionKey="value" 
                           		optionValue="value" value="${params.remark}"  name="remark" 
                           		from="${logType }" noSelection="['':'--选择日志类型--']"/>
                            </td>
                        </tr>
                         <tr>
                        	<td style="text-align:right">按登录账号</td>
                            <td>
                            <g:select class="log-table-select" optionKey="username" 
                            optionValue="username" value="${params.optUserName}"  
                            name="optUserName" from="${User.list() }" 
                            noSelection="['':'--选择账号--']"/>                            	
                            </td>
                        </tr>
                        <tr>
                        	<td style="text-align:right">按登录IP</td>
                            <td><input type="text" name="clientIp" value="${params.clientIp }" class="log-text"/></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right">按日志内容</td>
                            <td><input type="text" name="targetName" value="${params.targetName }" class="log-text"/></td>
                        </tr>                             
                    </table> 
                    <input type="hidden" id="pageIndex" name="pageIndex">
                    <input type="hidden" id="pageCount" name="pageCount">
                    <div class="system-but"><input type="button" value="查询" onclick="query(1)" class="btn-default btn-blue"  /> </div>
                </form>              
		</div>
		<g:set var="index" value="${params.int('pageIndex') }"></g:set>
		<div class="log-box-img userlog-box">
                	<div class="page-left">每页显示<input id="pgCount" type="text" disabled="disabled" value="${params.pageCount==null?10:params.pageCount }" style="width: 40px; height: 25px; margin: 0 10px; text-align: center;"/>
                	共 <label style=" color:#F00;">${allCount==null?0:allCount }</label> 条记录 共<label style=" color:#F00;">${pageNum }</label>页</div>
                    <div class="page-right">
								<g:if test="${index == 1}">
								<span>首页</span>
								<span>上页</span>
								</g:if>
								<g:else>
								<span onclick="query(1)">首页</span>
								<span onclick="query(${index<1?1:index-1})">上页</span>
								</g:else>
                             <%
							 
							    int pageNum = request.getAttribute("pageNum");
								if(index<5){
								   for(int i=1;i<=pageNum&&i<8;i++){
								 		if(index==i){
								 			out.println("<a href=\"#\" style=\" background:#51acd3; color: #fff; box-radius: 2px;\">"+i+"</a>");
								 		}else{
										   out.println("<a href=\"#\" onclick=\"query("+i+")\">"+i+"</a>");
								 		}
								   }
								}
								else if(index>(pageNum-4)){
								   for(int i=pageNum-6>0?pageNum-6:1;i<=pageNum;i++){
								 		if(index==i){
								 			out.println("<a href=\"#\" style=\" background:#51acd3; color: #fff; box-radius: 2px;\">"+i+"</a>");
								 		}else{
										   out.println("<a href=\"#\" onclick=\"query("+i+")\">"+i+"</a>");
								 		}
								   }
								}else{
								 	for(int i=index-3;i<index+4;i++){
								 		if(index==i){
								 			out.println("<a href=\"#\" style=\" background:#51acd3; color: #fff; box-radius: 2px;\">"+i+"</a>");
								 		}else{
										   out.println("<a href=\"#\" onclick=\"query("+i+")\">"+i+"</a>");
								 		}
									 }
								}
							 %>
								<g:if test="${index == pageNum}">
								<span>下页</span>
								<span>末页</span>
								</g:if>
								<g:else>
								<span onclick="query(${index<1?1:index+1})">下页</span>
								<span onclick="query(0)">末页</span>
								</g:else>
                    </div>
                    
				<table border="0" cellpadding="0" cellspacing="0" class="alarm-table notice-table">
					<tr class="bus-table-tr">
						<td class="alarm-table-td">操作时间</td>
						<td class="alarm-table-td">日志类型</td>
						<td class="alarm-table-td">用户账号</td>
						<td class="alarm-table-td">用户IP</td>
						<td class="alarm-table-td">操作</td>
						<td class="alarm-table-td">操作结果</td>
						<td class="alarm-table-td">操作内容</td>
						<td class="alarm-table-td">备注</td>
					</tr>
					<g:each var="log" in="${logList}">
						<tr class="bus-table-tr">
							<td style="width:15px;"><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${log.optTime}"/></td>
							<td style="width:15px;word-break: break-all;word-wrap: break-word;">${log.typeName }</td>
							<td style="width:50px;word-break: break-all;word-wrap: break-word;">${log.optUserName }</td>
							<td style="width:25px;word-break: break-all;word-wrap: break-word;">${log.clientIp }</td>
							<td style="width:30px;word-break: break-all;word-wrap: break-word;">${log.operation.optTypeName+log.operation.targetTypeName }</td>
							<td style="width:20px;word-break: break-all;word-wrap: break-word;">${log.opt_result==1?"操作成功":"操作失败" }</td>
							<td style="text-align:left;width:180px;word-break: break-all;word-wrap: break-word;">${log.targetName==""?"/":log.targetName }</td>
							<td style="width:20px;word-break: break-all;word-wrap: break-word;">${log.remark==""?"/":log.remark }</td>
						</tr>	
					</g:each>
				</table>
		</div>
	</div>
	<script type="text/javascript" src="${resource(dir:'js',file:'widget.js')}"></script>
</body>
</html>