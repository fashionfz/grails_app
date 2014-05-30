<%--
  服务器性能监控页面
  User: bin.liu
  Date: 14-2-12
  Time: 上午9:20
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>监控中心</title>
    <r:require modules="highchart"/>
</head>

<body>
	<g:render template="/layouts/submenu"></g:render>
	<div class="per-content">
        <div class="dev-per-left">
       	    <span class="dev-per-left-head">服务器列表</span>
            <ul id="st" class="dev-per-ul">
            	<g:each in="${servers}" var="server">
            		<li><a href="javascript:void(0)">${server.name}</a></li>
            		<g:each in="${server?.ifs}" var="ift">
            			<input type="hidden" id="${server.name.replace('.','')}" value="${ift.ipAddress}"/>
            		</g:each>
            	</g:each>
            </ul>
        </div>
        <div class="dev-per-right">
            <span class="dev-per-right-head"></span>
           <div class="dev-per-center">
            <div class="dev-per-cont">
                <div id="txchart" class="per-img-left">TX流量</div>
                    <div id="rxchart" class="per-img-right">RX流量</div>
                </div>
                <div class="dev-per-cont">
                    <div id="cpuchart" class="per-img-left">CPU监控</div>
                    <div id="memchart" class="per-img-right">内存监控</div>
                </div>
                <div class="dev-per-cont">
                    <div id="tcpConnections" class="per-img-left">TCP连接数</div>
                    <div id="diskchart" class="per-img-right">磁盘空间监控</div>
                </div>
           </div>
        </div>
    </div>
   <script type="text/javascript">

   String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
	    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
	        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
	    } else {  
	        return this.replace(reallyDo, replaceWith);  
	    }  
	}  
        $(document).ready(function() {
            
            var $hostname = $('ul#st li');
            
            $hostname.eq(0).addClass('per-list');
            $hostname.click(function(){
				$(this).addClass('per-list').siblings().removeClass('per-list');
				var name = $(this).children().html();
				var ip = $("#"+name.replace(/\./g,"")).val();
				loadChart(name);
				$('span.dev-per-right-head').html(ip);
            });

            //图表
            var cpuOptions = $.extend(true, {}, helome.chart.commonAreaOption,{
                title: {
                    text: 'cpu使用率曲线'
                },
                xAxis: {
                    //type: 'datetime',
                    tickInterval: 60,
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    title: {
                        text: 'cpu使用率(%)'
                    }
                },
                chart: {
                    renderTo: 'cpuchart'
                },
                series: [{}]
            });
            var memOptions = $.extend(true, {}, helome.chart.commonAreaOption, {
                title: {
                    text: '内存使用曲线'
                },
                xAxis: {
                    //type: 'datetime',
                    tickInterval: 60,
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    title: {
                        text: '内存使用率(%)'
                    }
                },
                chart: {
                    renderTo: 'memchart'
                },
                series: [{}]
            });
            var txOptions = $.extend(true, {}, helome.chart.commonAreaOption, {
                title: {
                    text: 'TX流量'
                },
                xAxis: {
                    //type: 'datetime',
                    tickInterval: 60,
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    min: 0,
                    //tickPixelInterval: 10,
                    title: {
                        text: 'TX流量'
                    },
                    labels: {
                        formatter: function() {
                            return (this.value / 1000) + "M"
                        }
                    }
                },
                tooltip: {
                    valueSuffix: " Kbps"
                },
                chart: {
                    renderTo: 'txchart'
                },
                series: [{}]
            });
            var rxOptions = $.extend(true, {}, helome.chart.commonAreaOption, {
                title: {
                    text: 'RX流量'
                },
                xAxis: {
                    //type: 'datetime',
                    tickInterval: 60,
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'RX流量'
                    },
                    labels: {
                        formatter: function() {
                            return (this.value / 1000) + "M"
                            //return this.value
                        }
                    }
                },
                tooltip: {
                    valueSuffix: " Kbps"
                },
                chart: {
                    renderTo: 'rxchart'
                },
                series: [{}]
            });

            var diskOptions = $.extend(true, {}, helome.chart.commonAreaOption, {
                title: {
                    text: '硬盘使用情况'
                },
                xAxis: {
                    //type: 'datetime',
                    tickInterval: 60,
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    title: {
                        text: '硬盘使用情况(%)'
                    }
                },
                chart: {
                    renderTo: 'diskchart'
                },
                series: [{}]
            });

            var conCountOptions = $.extend(true, {}, helome.chart.commonAreaOption, {
                title: {
                    text: 'TCP连接数'
                },
                xAxis: {
                    //type: 'datetime',
                    tickInterval: 60,
                    labels: {
                        rotation: 330
                    }
                },
                yAxis: {
                    title: {
                        text: '连接数'
                    }
                },
                chart: {
                    renderTo: 'tcpConnections'
                },
                series: [{}]
            });
            
            function loadChart(hostname) {

                $.get('graphicdata', {serverName: hostname,contype:'TCP'},function(data){
                	conCountOptions.xAxis.categories = data.concount_xAxies || [];
                	conCountOptions.series[0] = data.concount_data || [];
                	diskOptions.xAxis.categories = data.disk_xAxies || [];
                	diskOptions.series[0] = data.disk_data || [];
                	memOptions.xAxis.categories = data.mem_xAxies || [];
                	memOptions.series[0] = data.mem_data || [];
                	cpuOptions.xAxis.categories = data.cpu_xAxies || [];
                	cpuOptions.series[0] = data.cpu_data || [];
                	rxOptions.xAxis.categories = data.ifstat_xAxies || [];
                	rxOptions.series[0] = data.rx_data || [];
                	txOptions.xAxis.categories = data.ifstat_xAxies || [];
                	txOptions.series[0] = data.tx_data || [];
                	
                    new Highcharts.Chart(conCountOptions);
                    new Highcharts.Chart(diskOptions);
                    new Highcharts.Chart(memOptions);
                    new Highcharts.Chart(cpuOptions);
                    new Highcharts.Chart(rxOptions);
                    new Highcharts.Chart(txOptions);
                });
            }
            
            //树在加载时默认选择第一个节点
            $('ul.dev-per-ul li:first').trigger('click');
        });
    </script>
</body>
</html>