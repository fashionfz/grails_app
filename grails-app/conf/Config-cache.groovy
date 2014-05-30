grails.cache.config={
	cache { name 'grailsBlocksCache' }
	cache { name 'grailsTemplatesCache' }
	cache {
		name 'unreachablehost'
		timeToIdleSeconds 30*60
	}
	cache {
		name 'repeatcount'
		timeToIdleSeconds 5*60
	}
}