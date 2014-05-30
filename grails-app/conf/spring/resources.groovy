import net.rubyeye.xmemcached.XMemcachedClient
import net.rubyeye.xmemcached.XMemcachedClientBuilder
import net.rubyeye.xmemcached.command.TextCommandFactory
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager
import org.apache.commons.httpclient.params.HttpConnectionManagerParams
import org.apache.commons.pool.impl.StackObjectPool
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.SerializerMessageConverter

import com.helome.monitor.rabbitMQ.recieve.ReciveMessageHandler
import com.helome.monitor.support.poolfactory.HBaseConnectionFactory
// Place your Spring DSL code here
beans = {
	
	xmlns task:"http://www.springframework.org/schema/task"
	xmlns rabbit:"http://www.springframework.org/schema/rabbit"
	
	def config = grailsApplication.config
	
	shiroSecurityManager(DefaultWebSecurityManager){
		sessionManager = ref('defaultWebSessionManager')
		cacheManager = ref('shiroCacheManager')
	}
	
	defaultWebSessionManager(DefaultWebSessionManager){
		sessionDAO = ref('shiroSessionDAO')
	}
	
    credentialMatcher(HashedCredentialsMatcher) {
        hashAlgorithmName = "SHA-256"
    }
	
	httpConnectionParams(HttpConnectionManagerParams){
		maxTotalConnections = '${httpclient.maxTotalConnections}'
		defaultMaxConnectionsPerHost = '${httpclient.defaultMaxConnectionsPerHost}'
		connectionTimeout = '${httpclient.connectionTimeout}'
		soTimeout = '${httpclient.soTimeout}'
	}
	httpConnectionManager(MultiThreadedHttpConnectionManager){ bean->
		bean.destroyMethod = "shutdownAll"
		params = ref("httpConnectionParams")
	}
	
	retryHandler(DefaultHttpMethodRetryHandler, config.httpclient.retryCount, false)
	
	httpclient(HttpClient,ref("httpConnectionManager"))
	
	
	memcachedClientBuilder(XMemcachedClientBuilder
		,[new InetSocketAddress("172.16.4.96", 11000),new InetSocketAddress("172.16.4.96", 11001),new InetSocketAddress("172.16.4.96", 11002)]
		,[10,10,10]){
		connectionPoolSize = '${memcached.connectionPoolSize}'
		failureMode = '${memcached.failureMode}'
		commandFactory = new TextCommandFactory()
		sessionLocator = new KetamaMemcachedSessionLocator()
		transcoder = new SerializingTranscoder()
	}
	memcachedClient(XMemcachedClient){bean->
		bean.factoryMethod = "build"
		bean.factoryBean = "memcachedClientBuilder"
		bean.destroyMethod = "shutdown"
	}
	
	task.'executor'('id':'socketCheckExecutor','queue-capacity':'${thread.maxPoolSize}','keep-alive':'${thread.keepAliveSeconds}','pool-size':'${thread.corePoolSize}','rejection-policy':'DISCARD_OLDEST')
	task.'executor'('id':'messageHandleExecutor','queue-capacity':40,'keep-alive':120,'pool-size':20,'rejection-policy':'DISCARD_OLDEST')
	
	rabbit.'connection-factory'('id':'connectionFactory','username':'${rabbitMQ.username}','password':'${rabbitMQ.password}','host':'${rabbitMQ.host}','port':'${rabbitMQ.port}','requested-heartbeat':'10')
	rabbit.'template'('id':'amqpTemplate','connection-factory':'connectionFactory','exchange':'${rabbitMQ.exchange}')
	rabbit.'admin'('connection-factory':'connectionFactory')
	rabbit.'listener-container'('connection-factory':'connectionFactory','prefetch':1,'transaction-size':1,'auto-startup':false){
		rabbit.'listener'('ref':'messageListener','queue-names':'${rabbitMQ.queueNames}')
	}
	messageListener(MessageListenerAdapter, ref("reciveMessageHandler"), new SerializerMessageConverter())
	reciveMessageHandler(ReciveMessageHandler){
		messageHandleExecutor = ref("messageHandleExecutor")
		messageMetadata = [ref("dealServerData")]
	}
	
	// HBaseConnection Pool
	hbaseConnectionPoolProvider(StackObjectPool,new HBaseConnectionFactory(),5,2)
	
	importBeans('classpath:/jmx/jmx.xml')
}
