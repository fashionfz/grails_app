<div id="left-body">
	<h2 class="main-menu"><g:link action="index" controller="monitor">主菜单</g:link></h2>
    <ul class="menu-left">
    	<li><g:img file="${params.controller=="monitor" ? 'left-bg.png' : 'left-show.png'}" id="bgimg1" border="0" style=" float:left;"/>
    		<a href="javascript:" onClick="list('1');"><g:img file="${params.controller=="monitor" ? 'show.png' : 'hide.png'}" id="img1" />首页</a>
        	<ul class="tab" id="subtree1" style="display: ${params.controller=="monitor" ? 'block' : 'none'};">
            	<li><g:link action="warnings" controller="monitor" class="${params.action=="warnings" ? 'cuttents' : ''}">告警监控</g:link></li>
               	<li><g:link action="connectivy" controller="monitor" class="${params.action=="connectivy" ? 'cuttents' : ''}">在线监控</g:link></li>
                <li><g:link action="messages" controller="monitor" class="${params.action=="messages" ? 'cuttents' : ''}">性能监控</g:link></li>
                <li><g:link action="serverStatus" controller="monitor" class="${params.action=="serverStatus" ? 'cuttents' : ''}">服务状态</g:link></li>
            </ul>
        </li>
        <li><g:img file="${params.controller=="user" ? 'left-bg.png' : 'left-show.png'}" id="bgimg2" border="0" style=" float:left;"/>
        	<a href="javascript:" onClick="list('2');"><g:img file="${params.controller=="user" ? 'show.png' : 'hide.png'}" id="img2" />系统</a>
        	<ul class="tab" id="subtree2" style="display: ${params.controller=="user" ? 'block' : 'none'};">
            	<li><g:link action="usermanager" controller="user" class="${params.action=="usermanager" ? 'cuttents' : ''}">账户管理</g:link></li>
                <li><g:link action="emailSetting" controller="user" class="${params.action=="emailSetting" ? 'cuttents' : ''}">邮件通知</g:link></li>
                <li><g:link action="smsSetting" controller="user" class="${params.action=="smsSetting" ? 'cuttents' : ''}">短信通知</g:link></li>
            </ul>
        </li>
        <li><g:img file="${params.controller in ["strategy","onDuty","notifyPlot"] ? 'left-bg.png' : 'left-show.png'}" id="bgimg3" border="0" style=" float:left;"/>
        	<a href="javascript:"  onClick="list('3');"><g:img file="${params.controller in ["strategy","onDuty","notifyPlot"] ? 'show.png' : 'hide.png'}" id="img3" />策略</a>
        	<ul class="tab" id="subtree3" style="display: ${params.controller in ["strategy","onDuty","notifyPlot"] ? 'block' : 'none'};">
            	<li><g:link action="warnStragegyIndex" controller="strategy" class="${params.action=="warnStragegyIndex" ? 'cuttents' : ''}">告警策略</g:link></li>
                <li><g:link action="monitorStrategyIndex" controller="strategy" class="${params.action=="monitorStrategyIndex" ? 'cuttents' : ''}">监控策略</g:link></li>
                <!-- <li><g:link action="connectivyIndex" controller="strategy" class="${params.action in ["connectivyIndex","showNotifyUser"] ? 'cuttents' : ''}">宕机通知</g:link></li> -->
                <li><g:link action="userView" controller="onDuty" class="${params.action=="userView" ? 'cuttents' : ''}">用户视图</g:link></li>
                <li><g:link action="list" controller="onDuty" class="${params.action=="list" ? 'cuttents' : ''}">值班管理</g:link></li>
                <li><g:link action="urlchecklist" controller="onDuty" class="${params.action=="urlchecklist" ? 'cuttents' : ''}">网页检查</g:link></li>
                <li><g:link action="notifylist" controller="notifyPlot" class="${params.action=="notifylist" ? 'cuttents' : ''}">通知策略</g:link></li>
            </ul>
        </li>
        <li><g:img file="${params.controller in ["userGroup","serverInfo","serverGroup","indicator","role","indicatorWarnCondition"] ? 'left-bg.png' : 'left-show.png'}" id="bgimg4" border="0" style=" float:left;"/>
        	<a href="javascript:" onClick="list('4');"><g:img file="${params.controller in ["userGroup","serverInfo","serverGroup","indicator","role","indicatorWarnCondition"] ? 'show.png' : 'hide.png'}" id="img4" />对象</a>
        	<ul class="tab" id="subtree4" style="display: ${params.controller in ["userGroup","serverInfo","serverGroup","indicator","role","indicatorWarnCondition"] ? 'block' : 'none'};">
            	<li><g:link action="userGroupIndex" controller="userGroup" class="${params.action=="userGroupIndex" ? 'cuttents' : ''}">用户组</g:link></li>
                <li><g:link action="serverlist" controller="serverInfo" class="${params.action in ["serverlist","listIPAddress","listServerApp"] ? 'cuttents' : ''}">服务器</g:link></li>
                <li><g:link action="serverGroupList" controller="serverGroup" class="${params.action=="serverGroupList" ? 'cuttents' : ''}">服务器组</g:link></li>
                <li><g:link action="indicatorList" controller="indicator" class="${params.action=="indicatorList" ? 'cuttents' : ''}">采集指标</g:link></li>
                <li><g:link action="listIndicatorWarnCondition" controller="indicatorWarnCondition" class="${params.action=="listIndicatorWarnCondition" ? 'cuttents' : ''}">告警条件</g:link></li>
                <li><g:link action="rolelist" controller="role" class="${params.action=="rolelist" ? 'cuttents' : ''}">角色配置</g:link></li>
               <!-- <li><g:link action="personlist" controller="role" class="${params.action=="personlist" ? 'cuttents' : ''}">值班人员</g:link></li> --> 
            </ul>
        </li>
        <li><g:img file="${params.controller in ["log","businessAnalysis"] ? 'left-bg.png' : 'left-show.png'}" id="bgimg5" border="0" style=" float:left;"/><a  href="javascript:" onClick="list('5');"><g:img file="hide.png" id="img5" />日志</a>
        	<ul class="tab" id="subtree5" style="display: ${params.controller in ["log","businessAnalysis"] ? 'block' : 'none'};">
            	<li><g:link action="visitRangList" controller="businessAnalysis" class="${params.action=="visitRangList"||params.action=="loginArea"||params.action=="returnRate" ? 'cuttents' : ''}">业务分析</g:link></li>
                <li><a href="#">应用分析</a></li>
                <li><g:link action="userlog" controller="log" class="${params.action=="userlog" ? 'cuttents' : ''}">操作日志</g:link></li>
            </ul>
        </li>
        <li><g:img file="left-show.png" id="bgimg6" border="0" style=" float:left;"/><a  href="javascript:" onClick="list('6');"><g:img file="hide.png" id="img6" />报表</a>
        	<ul class="tab" id="subtree6" style="display: none;">
            	<li><a href="#">周期报表</a></li>
                <li><a href="#">自定义报表</a></li>
            </ul>
        </li>
    </ul>
</div>