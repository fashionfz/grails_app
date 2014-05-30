<%@ page import="com.helome.monitor.TimeSlot" %>
<%@ page import="com.helome.monitor.Dictionary" %>
<%
List<TimeSlot> list = request.getAttribute("rows");
 %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="layout" content="main" />
<r:require module="datepicker" />
<link rel="stylesheet" type="text/css" href="../css/head.css">
<link rel="stylesheet" type="text/css" href="../css/left.css">
<link rel="stylesheet" type="text/css" href="../css/right.css">
<link rel="stylesheet" type="text/css" href="../css/index.css" />
<link rel="stylesheet" type="text/css" href="../css/tanchu.css" />
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/highcharts.js"></script>
<script type="text/javascript">
$(window).load(function() {
	var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container',
            defaultSeriesType: 'bar',
            <%
			out.println("height:"+list.size()*(60-list.size()));
			%>
        },
        title: {
            text: '${params.prduct}(登录时间分布比率)'
        },
        xAxis: {
            title: {
                text: '',
            },
            <%
			   String str = "categories:['";
			   for(Object bing : list){
				   str = str + bing.obj.timeRang+"','";
			   }
			   str = str.substring(0,str.length()-1);
			   str = str+"]";
			   out.println(str);
			%>
        },
        tooltip: {
            valueSuffix: ''
        },        
        yAxis: {
            min: 0,
            title: {
                text: ''
            },
	        labels: {
	            formatter: function() {
	                return this.value +'%'
	            }
	        },
        },
        legend: {
            backgroundColor: '#FFFFFF',
            y: 100,
            floating: true,
            borderWidth: 0,
            shadow: true
        },
        plotOptions: {
            series: {
                stacking: 'normal',
                pointWidth: 20,
            }      
        },
            series: [{
            name: '使用率(%)',
            <%
			   String data = "data: [";
			   for(Object bing : list){
				   data = data + bing.per+",";
			   }
			   data = data.substring(0,data.length()-1);
			   data = data+"]";
			   out.println(data);
			%>
        }],
        credits:{
        	enabled: false
        }
    });
});
</script>
</head>
<body>
<g:render template="submenu"></g:render>

	<div class="alarm-log">
		<div class="dev-alarm-box">
                <form id="frm" method="post" action="visitRangList">
 					<table border="0" cellpadding="2" cellspacing="0" class="log-bale"> 

                        <tr>
                        	<td style="text-align:right">分析对象</td>
                            <td>
                           		<g:select class="log-table-select" optionKey="code" 
                           		optionValue="code" value="${params.prduct }"  name="prduct" 
                           		from="${Dictionary.where{codeType == 'prduct'} }"/>
                            </td>
                        </tr>
                    	<tr>
                        	<td style="text-align:right">按时间范围</td>                   	
                           <td colspan="2">
                            	<input type="text" name="begin" value="${params.begin }" class="log-time" id="d241">
                                <i class="log-list">到</i>
                            	<input type="text" name="end" value="${params.end }" class="log-time" id="d242">
                            </td>                           
                        </tr>                           
                    </table> 
                    <input type="hidden" id="pageIndex" name="pageIndex">
                    <input type="hidden" id="pageCount" name="pageCount">
                    <div class="system-but"><input type="submit" value="查询" class="btn-default btn-blue"  /> </div>
                </form>              
		</div>
		
		<div class="log-box-img userlog-box">
			<div id="container" style="margin: 0 auto"></div>
		</div>
	</div>
	<script type="text/javascript" src="${resource(dir:'js',file:'widget.js')}"></script>
</body>
</html>