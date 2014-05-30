<!DOCTYPE html>
<html>
	<head>
	    <%
		   if("1".equals(request.getParameter("flush"))){
	     %>
		<meta http-equiv="refresh" content="5"/>
			   
	    <%
		   }
		 %>
		<meta name="layout" content="main"/>
		<title>监控中心</title>
		<r:require module="highchart"/>
		<script type="text/javascript">
		function flush(){
			window.location = "index?flush=1";
		}
		</script>
	</head>
	<body>
        <g:render template="/layouts/submenu"></g:render>
        <div class="content" ondblclick="flush()">
        	<div class="cont-top">
            	<div class="cont-top-left">
                	<span class="cont-title title-txt">告警指标分布(最近24小时)</span>
                    <div id="alarmchart" class="cont-img"></div>
                </div>
                <div class="cont-top-right">
                	<span class="cont-title title-txt">接收流量趋势图(all Server)</span>
                    <div id="recieveRX" class="cont-img"></div>
                </div>
            </div>  
            
            <div class="cont-top cont-center">
            	<!--告警次数TOP10开始-->
            	<div class="cont-top-left cont-center-left">
                	<span class="cont-title title-txt">告警次数TOP10</span>
                	<table border="0" cellpadding="1" cellspacing="0" rules=rows class="table">
               			<tr class="left-list">
               				<td>排名</td>
               				<td>次数</td>
               				<td>服务器名</td>
               				<td>指标名</td>
               				<td>指标数据</td>
               			</tr>
               			<g:each in="${alarmTop}" status="i" var="top">
               				<tr class="left-tr">
               					<td>${i+1}</td>
               					<td>${top[0]}</td>
               					<td>${top[1]}</td>
               					<td>${top[2]}</td>
               					<td>${top[3]}</td>
               				</tr>
               			</g:each>
                	</table>
                </div>
                <!--告警次数TOP10结束-->
             	<!--TCP连接数TOP10开始-->
                <div class="cont-top-right cont-center-left">
                	<span class="cont-title title-txt">TCP连接数TOP10</span>
                     <table border="0" cellpadding="1" cellspacing="0" rules=rows class="table">
                    	<tr class="left-list" >
                    		<td>No</td>
                    		<td>时间</td>
                        	<td>服务器IP</td>
                            <td>外网IP</td>
                            <%--<td>柱状图</td>--%>
                            <td>连接数</td>                          
                        </tr>
                        <g:each in="${connN?.con_tcp}" status="i" var="top">
               				<tr class="left-tr">
               					<td>${i+1}</td>
               					<td><helo:time value="${top.time}" sign=":" /></td>
               					<td>${top.server_name}</td>
               					<td>${top.foreignip}</td>
               					<%--<td>${top[2]}</td>--%>
               					<td>${top.concount}</td>
               				</tr>
               			</g:each>
                    </table>
                </div>
                <!--TCP连接数TOP10结束-->   
            </div>    
              
              <div class="cont-top">
              <!--最近10条告警信息开始-->
            	<div class="cont-top-left cont-center-left">
                	<span class="cont-title title-txt">最近10条告警信息</span>
                	<table border="0" cellpadding="1" cellspacing="0" rules=rows class="table">
               			<tr class="left-list">
               				<td>No</td>
               				<td>时间</td>
               				<td>服务器名</td>
               				<td>告警内容</td>
               			</tr>
               			<g:each in="${alarmLatest}" status="i" var="latest">
               				<tr class="left-tr">
               					<td>${i+1}</td>
               					<td><g:formatDate format="HH:mm" date="${latest.alarmTime}"/></td>
               					<td>${latest.observed.name}</td>
               					<td>${latest.message}</td>
               				</tr>
               			</g:each>
                	</table>
                </div>
             	<!--最近10条告警信息结束-->
                <!--UDP连接数TOP10开始-->
                <div class="cont-top-right cont-center-left">
                	<span class="cont-title title-txt">UDP连接数TOP10</span>
                     <table border="0" cellpadding="1" cellspacing="0" rules=rows class="table">
                    	<tr  class="left-list" >
                    		<td>No</td>
                    		<td>时间</td>
                        	<td>服务器IP</td>
                            <td>外网IP</td>
                            <td>连接数</td>                          
                        </tr>
                        <g:each in="${connN?.con_udp}" status="i" var="top">
               				<tr class="left-tr">
               					<td>${i+1}</td>
               					<td><helo:time value="${top.time}" sign=":" /></td>
               					<td>${top.server_name}</td>
               					<td>${top.foreignip}</td>
               					<td>${top.concount}</td>
               				</tr>
               			</g:each>
                    </table>
                </div>
                <!--UDP连接数TOP10结束-->   
            </div> 
        </div>
        <script type="text/javascript">
		var alarmPartion = ${alarmPartion};
		var rx = ${rx};
		$(function () {
			var chartOption = {
					chart: {
			        	backgroundColor:'#F5F5F5',
			            plotBackgroundColor: '#f5f5f5',
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        credits: {
			            enabled : false
			    	},
			        exporting:{
			            enabled:false
			        }
			};
			var alarmPieChartOpt = $.extend({
				title: {
		            text: '告警指标<br>分布',
		            verticalAlign: 'middle',
					y:0,
					x:-60
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
						allowPointSelect:true,
						cursor:'pointer',
		                dataLabels: {
		                    enabled: false,
		                    distance: -50,
		                    style: {
		                        fontWeight: 'bold',
		                        color: 'white',
		                        textShadow: '0px 1px 2px black'
		                    }
		                },
						showInLegend: true
		            }
		        },
				legend: {
		                layout: 'vertical',
		                align: 'right',
		                verticalAlign: 'middle',
		                borderWidth: 0,
						title:{
							text:'告警指标名称'
						}
		        },
		        series: [{
		            type: 'pie',
		            name: 'Browser share',
		            innerSize: '50%',
		            data: alarmPartion
		        }]
			},chartOption);
			var recieveRXLineChartOption = {
				title: {
	                text: '接收流量(RX)趋势图'
	            },
	            xAxis: {
	            	tickInterval: 60,
	                labels: {
	                    rotation: 330
	                },
	                categories:rx.categories
	            },
	            yAxis: {
	            	min: 0,
	                title: {
	                    text: 'RX流量(Kbps)'
	                }
	            },
	            tooltip: {
	                valueSuffix: 'Kbps'
	            },
	            legend: {
	                layout: 'horizontal',
	                align: 'center',
	                verticalAlign: 'bottom',
	                borderWidth: 0
	            },
	            series: rx.series};
			recieveRXLineChartOption = $.extend(true,{},helome.chart.commonAreaOption,recieveRXLineChartOption);
		    $('#alarmchart').highcharts(alarmPieChartOpt);
		    $('#recieveRX').highcharts(recieveRXLineChartOption);
		});
		</script>
</body>
</html>
