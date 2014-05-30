class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:"auth", action:"login")
        "404"(controller: "error", action: "notFound")
        "403"(controller: "error", action: "forbidden")
		"500"(controller: 'error', action: 'error')
	}
}
