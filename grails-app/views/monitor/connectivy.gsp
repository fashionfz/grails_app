<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>服务器连通情况</title>
</head>
<body>
	<div class="dev-monitor">
		<div class="dev-monitor-left">
			<ul class="dev-mon-ul">
				<g:each in="${servers}" var="server">
					<li><g:img dir="images" file="host-computer.png" height="97"/>
						<span class="dev-mon-icon">${server.ip}</span>
						<span>${server.hostname }</span>
					</li>
				</g:each>
			</ul>
		</div>
		<div class="dev-monitor-right">
        	<span class="dev-mon-span">无法连通主机IP</span>
            <div class="dev-mon-txt">
            	<ul id="cantConnectUl" class="dev-mon-r-ul">
            		<g:each in="${cantConnect}" var="cc">
            			<li>${cc.ip}</li>
            		</g:each>
                </ul>
            </div>
        </div>
	</div>
	<script type="text/javascript">
		$(function(){
			// 清除不能连接ul里面的子节点
			function clearUnConnectivyList(){
				$('#cantConnectUl').children().remove();
			}
			// 向不能连接ul里面添加子节点
			function addUnConnectivyToList(data){
				var $cantConnectUl = $('#cantConnectUl');
				for(var i in data){
					$cantConnectUl.append('<li>'+data[i].ip+'</li>');
				}
			}
			// 重置所有服务器状态为正常
			function resetAllServerStatusOn(){
				$('li > span').removeClass('dev-mon-icon-list');
			}
			// 改变服务器状态与不能连接ul里面的一致
			function changeServerStatusAssociationCant(){
				var $cantconnect_li = $('#cantConnectUl').children('li');
				var ipaddr;
				$cantconnect_li.each(function(index){
					ipaddr = $(this).text();
					$("span:contains('"+ipaddr+"')").addClass('dev-mon-icon-list');
				});
			}
			function actionChain(data){
				clearUnConnectivyList();
				addUnConnectivyToList(data);
				resetAllServerStatusOn();
				changeServerStatusAssociationCant();
			}
			function ajaxRequestCantConnectServer(){
				$.get('connectivyData',{},function(data){
					actionChain(data);
				},'json');
			}
			changeServerStatusAssociationCant();
			setInterval(ajaxRequestCantConnectServer, 5000);
		});
	</script>
</body>
</html>