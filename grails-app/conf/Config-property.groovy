//HTTP CLIENT
httpclient.maxTotalConnections=50
httpclient.defaultMaxConnectionsPerHost=5
httpclient.connectionTimeout=10000
httpclient.soTimeout=10000
httpclient.retryCount=3

//Set Ping Avaliable Timeout (ms)
ping.timeout=5000

//checker interval millionSeconds
checker.interval=10000
checker.repeatcount=5

//ThreadPool Config
thread.corePoolSize=15
thread.maxPoolSize=40
thread.keepAliveSeconds=120

memcached.connectionPoolSize=5
memcached.failureMode=true

rabbitMQ.host="172.16.4.97"
rabbitMQ.port="5672"
rabbitMQ.username="guest"
rabbitMQ.password="guest"
rabbitMQ.exchange="monitorCenter"
rabbitMQ.queueNames="monitorBaseDataQueue"

zookeeper.connectString="172.16.4.94:2181,172.16.4.95:2181"