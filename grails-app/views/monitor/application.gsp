<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>监控中心</title>
	<r:require module="indexApplyChart"/>
</head>
<body>
	<g:render template="/layouts/submenu"></g:render>
	<div class="per-content">
		<div class="dev-per-left">
			<span class="dev-per-left-head">应用列表</span>
			<ul class="dev-per-ul">
				<g:each in="${servers}" status="i" var="server">
					<li><a href="javascript:void(0)">${server.name}</a>
						<ul class="pus-tab" style="display: none;">
							<g:each in="${server.services}" var="service">
								<!-- 3:Mysql, 6:Connectivy -->
								<g:if test="${service.support && service.type!=3}">
									<g:each in="${service.ports}" var="port">
										<li><a href="javascript:void(0)" class="chart_request" data-host="${server.name}" data-port="${port}" data-code="${service.type}">${service.name}</a></li>
									</g:each>
								</g:if>
							</g:each>
						</ul>
					</li>
				</g:each>
			</ul>
		</div>
		<div class="dev-per-right">
			<span class="dev-per-right-head" id="title">应用监控</span>
			<div class="dev-per-center">
				<div class="dev-per-cont">
					<div id="cpu_pct" class="per-img-left"></div>
					<div id="thread_count" class="per-img-right"></div>
				</div>
				<div class="dev-per-cont">
					<div id="mem" class="per-img-left"></div>
					<div id="yg" class="per-img-right"></div>
				</div>
				<div class="dev-per-cont">
					<div id="og" class="per-img-left"></div>
					<div id="pg" class="per-img-right"></div>
				</div>
				<div class="dev-per-cont">
					<div id="clzzload" class="per-img-left"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>