<%--
  Created by IntelliJ IDEA.
  User: helome
  Date: 14-2-12
  Time: 上午9:34
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	    <%
		   if("1".equals(request.getParameter("flush"))){
	     %>
		<meta http-equiv="refresh" content="5"/>
			   
	    <%
		   }
		 %>
    <meta name="layout" content="main">
    <title>业务信息</title>
    <r:require module="highchart"/>
		<script type="text/javascript">
		function flush(){
			window.location = "business?flush=1";
		}
		</script>
</head>
<body>
    <g:render template="/layouts/submenu"/>
    <div class="per-content" ondblclick="flush()"> 
        <div class="dev-bus">
            <span class="dev-txt">在线用户数统计</span>
            <div class="bus-box">
                <div class="bus-box-left">
                    <h4 class="bus-txt">[当前在线总数:${onlineUserCount}]</h4>
                    <table border="0" cellpadding="0" cellspacing="1" class="bus-table">
                        <tr>
                            <td class="bus-table-td">产品</td>
                            <td class="bus-table-td">平台</td>
                            <td class="bus-table-td">用户数</td>
                        </tr>
                        <g:each in="${onlineUsers}" var="record">
                            <tr class="bus-table-tr">
                                <td>${record.product == 'null'?'All' : record.product}</td>
                                <td>${record.device == 'null'?'All' : record.device}</td>
                                <td>${record.amount}</td>
                            </tr>
                        </g:each>
                    </table>
                </div>
                <div class="bus-box-right">
                    <h4 class="bus-txt">[历史峰值]</h4>
                    <table border="0" cellpadding="0" cellspacing="1" class="bus-table">
                        <tr>
                            <td style=" background:#b0d3c1; height: 25px; color: #fff;">产品</td>
                            <td style=" background:#b0d3c1; height: 25px;color: #fff;">平台</td>
                            <td style=" background:#b0d3c1; height: 25px;color: #fff;">用户数</td>
                        </tr>
                        <g:each in="${highestLevel}" var="record">
                            <tr class="bus-table-tr">
                                <td>${record[0] == 'null'?'All' : record[0]}</td>
                                <td>${record[1] == 'null'?'All' : record[1]}</td>
                                <td>${record[2]}</td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </div>
        </div>

        <!--活跃用户趋势分析开始-->
        <div class="dev-bus">
            <span class="dev-txt">活跃用户趋势分析</span>
            <div class="bus-box">
                <div class="bus-box-left">
                    <div id="hi_active_line" class="cont-img"></div>
                </div>
                <div class="bus-box-right">
                    <div id="helome_active_line" class="cont-img"></div>
                </div>
            </div>
        </div>
        <!--活跃用户趋势分析结束-->
        <!--新增用户趋势分析开始-->
        <div class="dev-bus">
            <span class="dev-txt">新增用户趋势分析</span>
            <div class="bus-box">
                <div class="bus-box-left">
                    <div id="hi_register_line" class="cont-img"></div>
                </div>
                <div class="bus-box-right">
                    <div id="helome_register_line" class="cont-img"></div>
                </div>
            </div>
        </div>
        <!--新增用户趋势分析结束-->
    </div>

    <script type="text/javascript">
        $(document).ready(function() {

            var options = {
                xAxis: {
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    title: {
                        text: '用户数'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                credits: {
                    enabled : false//去掉图标右下角highcharts官方链接
                },
                chart: {
                    type:'line',
                    zoomType: 'x'
                },
                series: [{}]
            };

            var hiActiveLineOptions = $.extend(true, {}, options, {
                title: {
                    text: 'Hi'
                },
                chart: {
                    renderTo: 'hi_active_line'
                }
            });

            var helomeActiveLineOptions = $.extend(true, {}, options,{
                title: {
                    text: 'Helome'
                },
                chart: {
                    renderTo: 'helome_active_line'
                }
            });

            var hiRegisterLineOption = $.extend(true, {}, options, {
                title: {
                    text: 'Hi'
                },
                chart: {
                    renderTo: 'hi_register_line'
                }
            });

            var helomeRegisterLineOption = $.extend(true, {}, options, {
                title: {
                    text: 'Helome'
                },
                chart: {
                    renderTo: 'helome_register_line'
                }
            });

            $.get('activeUsersLine', {product: 'hi'},function(data){
                hiActiveLineOptions.xAxis.categories = data.xAxies;
                hiActiveLineOptions.series[0] = data;
                new Highcharts.Chart(hiActiveLineOptions);
            });

            $.get('activeUsersLine', {product: 'helome'},function(data){
                helomeActiveLineOptions.xAxis.categories = data.xAxies;
                helomeActiveLineOptions.series[0] = data;
                new Highcharts.Chart(helomeActiveLineOptions);
            });

            $.get('registerUsersLine', {product: 'hi'},function(data){
                hiRegisterLineOption.xAxis.categories = data.xAxies;
                hiRegisterLineOption.series[0] = data;
                new Highcharts.Chart(hiRegisterLineOption);
            });

            $.get('registerUsersLine', {product: 'helome'},function(data){
                helomeRegisterLineOption.xAxis.categories = data.xAxies;
                helomeRegisterLineOption.series[0] = data;
                new Highcharts.Chart(helomeRegisterLineOption);
            });

        });
    </script>
</body>
</html>