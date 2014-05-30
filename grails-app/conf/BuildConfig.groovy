grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
grails.project.war.file = "target/${appName}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolver="ivy"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        excludes "ehcache-core"//,"spring-core","spring-beans"
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {

        inherits true // Whether to inherit repository definitions from plugins

		mavenLocal()
		mavenCentral()
        grailsPlugins()
        grailsHome()
        grailsCentral()

    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
		//runtime (group:'org.apache.hadoop',name:'hadoop-mapreduce-client',version:'2.2.0')
		compile 'org.springframework.amqp:spring-rabbit:1.2.1.RELEASE'
		compile 'com.googlecode.xmemcached:xmemcached:1.4.2'
		compile 'org.slf4j:slf4j-api:1.7.5'
        runtime 'mysql:mysql-connector-java:5.1.22'
		runtime 'commons-cli:commons-cli:1.2'
		runtime 'commons-httpclient:commons-httpclient:3.1'
		compile('org.apache.hadoop:hadoop-mapreduce-client:2.2.0'){
			exclude 'slf4j-log4j12'
		}
		compile('org.apache.hbase:hbase-common:0.96.1.1-hadoop2'){
			exclude 'slf4j-log4j12'
		}
		compile('org.apache.hbase:hbase-client:0.96.1.1-hadoop2'){
			exclude 'slf4j-log4j12'
		}
		compile 'org.hamcrest:hamcrest-core:1.3'
		compile('org.apache.hadoop:hadoop-common:2.2.0'){
			exclude 'slf4j-log4j12'
		}
		compile('org.apache.hadoop:hadoop-mapreduce-client-core:2.2.0'){
			exclude 'slf4j-log4j12'
		}
		build 'com.google.protobuf:protobuf-java:2.5.0'
    }

    plugins {
        runtime(":hibernate:$grailsVersion")
        runtime ":jquery:1.8.3"
        runtime ":resources:1.2"
		build ":tomcat:$grailsVersion"
		runtime ":database-migration:1.3.2"
		compile(':cache-ehcache:1.0.1')
		compile ":hawk-eventing:0.5.1"
		compile(':shiro:1.2.0'){
			exclude "quartz"
		}
		compile ':mail:1.0.1'
		compile ':quartz2:2.1.6.2'
//		compile(':events-push:1.0.M7'){
//			exclude "resources"
//		}
    }
}
