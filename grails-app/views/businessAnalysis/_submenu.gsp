<ul class="right-menu">
	<li class="${params.action=="visitRangList" ? 'current' : '' }"><g:link action="visitRangList" controller="businessAnalysis">登录时间分布</g:link></li>
	<li class="${params.action=="loginArea" ? 'current' : '' }"><g:link action="loginArea" controller="businessAnalysis">登录区域</g:link></li>
	<li class="${params.action=="returnRate" ? 'current' : '' }"><g:link action="returnRate" controller="businessAnalysis">用户留存率</g:link></li>
</ul>