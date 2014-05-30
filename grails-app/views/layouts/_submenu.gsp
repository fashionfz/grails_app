<ul class="right-menu">
	<li class="${params.action=="index" ? 'current' : '' }"><g:link action="index" controller="monitor">监控概览</g:link></li>
	<li class="${params.action=="performance" ? 'current' : '' }"><g:link action="performance" controller="monitor">性能</g:link></li>
	<li class="${params.action=="business" ? 'current' : '' }"><g:link action="business" controller="monitor">业务</g:link></li>
	<li class="${params.action=="application" ? 'current' : '' }"><g:link action="application" controller="monitor">应用</g:link></li>
	<li class="${params.action=="db" ? 'current' : '' }"><g:link action="db" controller="monitor">数据库</g:link></li>
	<li class="${params.action=="web" ? 'current' : '' }"><g:link action="web" controller="monitor">网站</g:link></li>
</ul>