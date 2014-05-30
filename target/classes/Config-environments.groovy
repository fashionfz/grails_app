environments {
	development {
		grails.logging.jul.usebridge = false
		grails.plugin.quartz2.autoStartup = true
	}
	production {
		grails.logging.jul.usebridge = false
		// TODO: grails.serverURL = "http://www.changeme.com"
		grails.plugin.quartz2.autoStartup = false
	}
}

