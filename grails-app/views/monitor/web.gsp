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
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	var seriesOptions = [],
		yAxisOptions = [],
		seriesCounter = 0,
		names = ['PV', 'UV', 'IP'],
		colors = Highcharts.getOptions().colors;

	$.each(names, function(i, name) {

		$.getJSON('/monitor-webapp/monitor/queryCountData?type='+(i+1)+'&prduct=${params.prduct}',	function(data) {

			seriesOptions[i] = {
				name: name,
				data: data
			};
			seriesCounter++;

			if (seriesCounter == names.length) {
				createChart();
			}
		});
	});



	// create the chart when all data is loaded
	function createChart() {

		$('#container').highcharts('StockChart', {

		    rangeSelector: {
				inputEnabled: $('#container').width() > 480,
		        selected: 4
		    },

			title : {
				text : '网站访问统计'
			},
	        yAxis: {
	            min:0
	        },		    
		    series: seriesOptions,
	        credits:{
	        	enabled: false
	        }
		});
	}

});



function loadCount(prduct){
	if('hi' == prduct){
		window.location.href ="web?prduct="+prduct+"&remark=网站IP：8.8.8.8   http://www.51chat.com  "
	}
	else {
		window.location.href ="web?prduct="+prduct+"&remark=网站IP：8.8.8.8   http://www.helome.com  "
	}
}
		</script>	
</head>
<body>
<script src="../js/highstock.js"></script>
<script src="../js/modules/exporting.js"></script>
	<g:render template="/layouts/submenu"></g:render>
	<div class="per-content">
        <div class="dev-per-left">
       	    <span class="dev-per-left-head">网站列表</span>
            <ul id="st" class="dev-per-ul">
            	<li><a href="javascript:loadCount('hi')">hi</a></li>
            	<li><a href="javascript:loadCount('helome')">helome</a></li>
            </ul>
        </div>
        
		<div class="dev-per-right">
            <span class="dev-per-right-head">${params.remark }</span>
            <div class="dev-per-center">
            	<div class="dev-per-cont">
            	网站概况
            		<table border="0" cellpadding="2" cellspacing="0" class="alarm-table notice-table">
            			<tr class="bus-table-tr"><td>时间</td><td>浏览量（PV）</td><td>访客数（UV）</td><td>IP数</td></tr>
            			<tr class="bus-table-tr"><td>今日</td>
	            			<g:each in="${today}" var="obj">
            					<td>${obj }</td>
            				</g:each>
            			</tr>
            			<tr class="bus-table-tr"><td>昨日</td>
	            			<g:each in="${zuotian}" var="obj">
            					<td>${obj }</td>
            				</g:each>
            			</tr>            			
            		</table>
				</div>
				 <br/>           	
            	<p>曲线图</p>
				<div class="dev-per-center" style="margin-top:10px; width:940px; margin-left:-36px;">
					<div id="container"></div>
				</div>
           </div>
        </div>        
    </div>
</body>
</html>