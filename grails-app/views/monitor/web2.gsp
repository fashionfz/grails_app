<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>监控中心</title>
<r:require module="datepicker" />
<link rel="stylesheet" type="text/css" href="../css/head.css">
<link rel="stylesheet" type="text/css" href="../css/left.css">
<link rel="stylesheet" type="text/css" href="../css/right.css">
<link rel="stylesheet" type="text/css" href="../css/index.css" />
<link rel="stylesheet" type="text/css" href="../css/tanchu.css" />
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/highcharts.js"></script>
<script type="text/javascript">
		$(window).load(function(){

			var chart = new Highcharts.Chart({
		        chart: {
		        	renderTo: 'recieveRX',
		            type: 'spline',
		            height:350
		        },
		        title: {
		            text: '(浏览量（PV）)'
		        },
		        xAxis: {
		            title: {
		                text: ''
		            },
		            categories:${rang}        
		        },
		        yAxis: {
		            title: {
		                text: '访问次数'
		            },
		            labels: {
		                formatter: function() {
		                    return this.value
		                }
		            },
		            min:0
		        },
		        tooltip: {
		            crosshairs: true,
		            shared: true
		        },
		        legend: {
		            backgroundColor: '#FFFFFF',
		            y: 100,
		            floating: true,
		            borderWidth: 0,
		            shadow: true
		        },        
		        plotOptions: {
		            spline: {
		                marker: {
		                    radius: 0,
		                    lineColor: '#666666',
		                    lineWidth: 1
		                }
		            }
		        },
		        series: [{
		            name: '访问次数',
		            color: '#2f7ed8',
		            marker: {
		                symbol: 'circle'
		            },
		            data: ${datas},
		            tooltip: {
			            headerFormat: '<strong>  {point.key} 点</strong><br/>'
			        }
		        }],
		        credits:{
		        	enabled: false
		        }
		    });
		});		
		</script>    	
</head>
<body>
	<g:render template="/layouts/submenu"></g:render>
	<div class="per-content">
        <div class="dev-per-left">
       	    <span class="dev-per-left-head">服务器列表</span>
            <ul id="st" class="dev-per-ul">
            	<li><a href="javascript:void(0)">hi</a></li>
            	<li><a href="javascript:void(0)">helome</a></li>
            </ul>
        </div>
        
		<div class="dev-per-right">
            <span class="dev-per-right-head"></span>
            <div class="dev-per-center">
            	<div class="dev-per-cont">
            	网站概况
            		<table width="800px" border="1">
            			<tr><td>时间</td><td>浏览量（PV）</td><td>访客数（UV）</td><td>IP数</td></tr>
            			<tr><td>今日</td>
	            			<g:each in="${data}" var="obj" status="i">
	            			
	            			<g:if test="${i>0&&i%3==0 }">
	            			 	</tr><tr>
	            			</g:if>
            					<td>${obj.num }</td>
            			</g:each>
            			</tr>
            		</table>
            	曲线图
            	<br />
            	<br />
            	<br />
            	<br />
                    <div id="recieveRX" class="dev-per-cont"></div>
				</div>
           </div>
        </div>        
    </div>
</body>
</html>