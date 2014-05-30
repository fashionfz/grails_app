<%@ page import="com.helome.monitor.TimeSlot" %>
<%@ page import="com.helome.monitor.Dictionary" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.helome.monitor.Constant" %>
<%@ page import="com.helome.monitor.utils.ReturnStyle" %>


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
$(window).load(function(){

	var chart = new Highcharts.Chart({
        chart: {
        	renderTo: 'container',
            type: 'spline',
            height:350
        },
        title: {
            text: '${params.prduct}(次日留存率)'
        },
        xAxis: {
            title: {
                text: ''
            },
            <%
			   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			   List<TimeSlot> list = request.getAttribute("rows");
			   String str = "categories:['";
			   for(Object bing : list){
				   str = str + df.format(bing.obj.returnDate)+"','";
			   }
			   str = str.substring(0,str.length()-1);
			   str = str+"]";
			   out.println(str);
			%>            
        },
        yAxis: {
            title: {
                text: '留存率(%)'
            },
            labels: {
                formatter: function() {
                    return this.value +'%'
                }
            },
            min:0,
            max:100
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
                    radius: 4,
                    lineColor: '#666666',
                    lineWidth: 1
                }
            }
        },
        series: [{
            name: '次日留存率',
            color: '#2f7ed8',
            marker: {
                symbol: 'circle'
            },
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
                <form id="frm" method="post" action="returnRate">
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
                        	<td style="text-align:right">留存率</td>
                            <td>
                           		<g:select class="log-table-select" optionKey="value" 
                           		optionValue="name" value="${params.type }"  name="type" 
                           		from="${Dictionary.where{codeType == 'user_return'} }"/>
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
			<div id="container" style="height: 350px; margin: 0 auto"></div>
		</div>
		
		
		
		<table class="alarm-table notice-table">
		    <tr class="bus-table-tr">
		    <td class="alarm-table-td">首次使用日</td>
		    <td class="alarm-table-td">新增用户</td>
		    <td colspan="9"></td>
		    </tr>
		    
		    <g:if test="${params.type == '1' }">
			    <tr class="bus-table-tr">
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td class="alarm-table-td">1天后</td>
			    <td class="alarm-table-td">2天后</td>
			    <td class="alarm-table-td">3天后</td>
			    <td class="alarm-table-td">4天后</td>
			    <td class="alarm-table-td">5天后</td>
			    <td class="alarm-table-td">6天后</td>
			    <td class="alarm-table-td">7天后</td>
			    <td class="alarm-table-td">14天后</td>
			    <td class="alarm-table-td">30天后</td>
			    </tr>		    
		    </g:if>
		    <g:elseif test="${params.type == '7' }">
			    <tr class="bus-table-tr">
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td class="alarm-table-td">+1周</td>
			    <td class="alarm-table-td">+2周</td>
			    <td class="alarm-table-td">+3周</td>
			    <td class="alarm-table-td">+4周</td>
			    <td class="alarm-table-td">+5周</td>
			    <td class="alarm-table-td">+6周</td>
			    <td class="alarm-table-td">+7周</td>
			    <td class="alarm-table-td">+8周</td>
			    </tr>		    
		    </g:elseif>
		    <g:elseif test="${params.type == '30' }">
			    <tr class="bus-table-tr">
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td class="alarm-table-td">+1月</td>
			    <td class="alarm-table-td">+2月</td>
			    <td class="alarm-table-td">+3月</td>
			    <td class="alarm-table-td">+4月</td>
			    <td class="alarm-table-td">+5月</td>
			    <td class="alarm-table-td">+6月</td>
			    </tr>		    
		    </g:elseif>		    
		    
		<%
			Map map = request.getAttribute("map");
			Map regist = request.getAttribute("regist");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar xx = Calendar.getInstance();
			Date bgstr = request.getAttribute("begin");
			String style = ReturnStyle.getName(request.getParameter("type"));
			
			if(bgstr!=null&&!"".equals(bgstr)){
				xx.setTime(bgstr);
				Calendar yy = Calendar.getInstance();
				Date edstr = request.getAttribute("end");
				yy.setTime(edstr);
			    for(Calendar d=xx;d.getTime()<=yy.getTime();d.add(Calendar.DATE,1)){
			        
					out.print("<tr class='bus-table-tr'>");
					out.print("<td >"+df.format(d.getTime())+"</td>");
					if(regist.get(df.format(d.getTime()))==null){
						out.print("<td>0</td>");
					}else{
						out.print("<td>"+regist.get(df.format(d.getTime()))+"</td>");
					}
					if("day".equals(style)){
						for(String type : Constant.RETURN_RATE_DAY){
							if(map.get(df.format(d.getTime())+style+type)==null){
								out.print("<td>&nbsp;</td>");
							}else{
								out.print("<td class='alarm-table-td'>"+map.get(df.format(d.getTime())+style+type)+"</td>");
							}
						}
						out.print("</tr>");
					}else if("week".equals(style)){
						for(String type : Constant.RETURN_RATE_WEEK){
							if(map.get(df.format(d.getTime())+style+type)==null){
								out.print("<td>&nbsp;</td>");
							}else{
								out.print("<td class='alarm-table-td'>"+map.get(df.format(d.getTime())+style+type)+"</td>");
							}
						}
						out.print("</tr>");
					}else if("month".equals(style)){
						for(String type : Constant.RETURN_RATE_MONTH){
							if(map.get(df.format(d.getTime())+style+type)==null){
								out.print("<td>&nbsp;</td>");
							}else{
								out.print("<td class='alarm-table-td'>"+map.get(df.format(d.getTime())+style+type)+"</td>");
							}
						}
						out.print("</tr>");
					}
			    }
					 
			}
         %>
		</table>
	</div>
	<script type="text/javascript" src="${resource(dir:'js',file:'widget.js')}"></script>
</body>
</html>