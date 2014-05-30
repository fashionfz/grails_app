dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    username = "root"
	password = "123456"
	properties{
		initialSize = 5
		maxActive = 50
		minIdle = 5
		maxIdle = 25
		maxWait = 10000
		minEvictableIdleTimeMillis=1800000
		timeBetweenEvictionRunsMillis=1800000
		numTestsPerEvictionRun=3
	}
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
	cache.provider_class='org.hibernate.cache.EhCacheProvider'
    cache.region.factory_class = 'grails.plugin.cache.ehcache.hibernate.BeanEhcacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
			username = "root"
			password = "Mi123456"
            dbCreate = "update"
            url = "jdbc:mysql://172.16.4.150:3306/monitor?useUnicode=yes&charactorEncoding=UTF-8"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/cuit?useUnicode=yes&charactorEncoding=UTF-8"
        }
    }
    production {
        dataSource {
			username = "root"
			password = "Mi123456"
            dbCreate = "update"
            url = "jdbc:mysql://localhost:4040/monitor?useUnicode=yes&charactorEncoding=UTF-8"
        }
    }
}
