<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>监控中心</title>
	<r:require module="highchart"/>
</head>
<body>
	<g:render template="/layouts/submenu"></g:render>
	<div class="per-content">
		<div class="dev-per-left">
			<span class="dev-per-left-head">服务器列表</span>
			<ul class="dev-per-ul">
				<g:each in="${servers}" status="i" var="server">
					<li>
						<g:link controller="monitor" action="db" params='[hostname:"${server.name}"]'>${server.name}</g:link>
					</li>
				</g:each>
			</ul>
		</div>
		<div class="dev-database">
			<div class="data-title">
				<h4 class="title">mysql监控</h4>
				<div class="time">
					运行时间：<g:formatNumber number="${runtimeState.day}" maxFractionDigits="0"/> 天 
							<g:formatNumber number="${runtimeState.hour}" maxFractionDigits="0"/> 小时
							<g:formatNumber number="${runtimeState.minute}" maxFractionDigits="0"/> 分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;启动状态：<span class="${runtimeState.runStatus==1?'fettle':''}"></span>
				</div>
			</div>
			<div class="dev-data-box">
				<div class="data-box-one">
					<div id="chart_mysql_cpu" class="data-box-left"></div>
					<div id="chart_mysql_mem" class="data-box-right"></div>
				</div>
				<div class="data-box-two">
					<div id="chart_mysql_allconn" class="data-box-left">当前所有连接数图</div>
					<div id="chart_mysql_deadconn" class="data-box-right">当前死掉连接数图</div>
				</div>
			</div>
			<div class="dev-data-box">
				<div class="data-box-table">
					<h4 class="bus-txt">[锁表信息]</h4>
					<table border="0" cellpadding="0" cellspacing="1" class="bata-table-sql">
						<tr>
							<td class="bata-table-td">时间</td>
							<td class="bata-table-td">锁定表名</td>
							<td class="bata-table-td">等待队列数</td>
						</tr>
						<g:each in="${lockData}" var="lock">
							<tr class="bata-table-tr">
								<td><helo:time value="${lock.time}" sign=":"/></td>
								<td>${lock.dbt}</td>
								<td>${lock.counts}</td>
							</tr>
						</g:each>
					</table>
				</div>
			</div>
			<div class="dev-data-box">
				<div class="data-box-table">
					<h4 class="bus-txt">[SQL执行平均时间最长]</h4>
					<table border="0" cellpadding="0" cellspacing="1" class="bata-table-sql">
						<tr>
							<td style="background: #c2c7ce; height: 25px; color: #fff; width: 15%;">采集时间</td>
							<td style="background: #c2c7ce; height: 25px; color: #fff; width: 15%;">查询平均时间</td>
							<td style="background: #c2c7ce; height: 25px; color: #fff; width: 70%;">SQL语句</td>
						</tr>
						<tr class="bata-table-tr">
							<td><helo:time value="${expensive?.time }" sign=":"/></td>
							<td>${expensive?.slowsqlatime }</td>
							<td>${expensive?.slowsql }</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var highLightHostname = "${params.hostname}";
			var chartData = ${chart};
			console.log(chartData);
			var chart_mysql_cpu,chart_mysql_mem;
			$("ul.dev-per-ul a:contains('"+highLightHostname+"')").parent().addClass('per-list');
			
			var msyql_cpu_opt = $.extend(true, {}, helome.chart.commonAreaOption, {
				title : {
					text : 'CPU占用'
				},
				yAxis : {
					title : {
						text : '百分比(%)'
					}
				},
				chart : {
					renderTo : 'chart_mysql_cpu'
				}
			});
			var mysql_mem_opt = $.extend(true,{},helome.chart.commonAreaOption,{
				title : {
					text : '内存占用'
				},
				yAxis : {
					title : {
						text : 'MB'
					}
				},
				chart : {
					renderTo : 'chart_mysql_mem'
				}
			});
			var mysql_allconn_opt = $.extend(true,{},helome.chart.commonAreaOption,{
				title : {
					text : '当前所有连接数'
				},
				yAxis : {
					title : {
						text : '个'
					}
				},
				chart : {
					renderTo : 'chart_mysql_allconn'
				}
			});
			var mysql_deadconn_opt = $.extend(true,{},helome.chart.commonAreaOption,{
				title : {
					text : '当前死掉连接数'
				},
				yAxis : {
					title : {
						text : '个'
					}
				},
				chart : {
					renderTo : 'chart_mysql_deadconn'
				}
			});

			function initChart(){
				msyql_cpu_opt.series[0] = chartData.cpu;
				msyql_cpu_opt.xAxis.categories = chartData.categories;
				mysql_mem_opt.series[0] = chartData.mem;
				mysql_mem_opt.xAxis.categories = chartData.categories;
				mysql_allconn_opt.series[0] = chartData.allconn;
				mysql_allconn_opt.xAxis.categories = chartData.categories;
				mysql_deadconn_opt.series[0] = chartData.deadconn;
				mysql_deadconn_opt.xAxis.categories = chartData.categories;

				chart_mysql_cpu = new Highcharts.Chart(msyql_cpu_opt);
				chart_mysql_mem = new Highcharts.Chart(mysql_mem_opt);
				chart_mysql_allconn = new Highcharts.Chart(mysql_allconn_opt);
				chart_mysql_deadconn = new Highcharts.Chart(mysql_deadconn_opt);
			}

			initChart();
		 });
	</script>
</body>
</html>