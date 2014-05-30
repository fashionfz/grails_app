package com.helome.monitor.support.poolfactory

import org.apache.commons.pool.BasePoolableObjectFactory
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.security.User
import org.springframework.stereotype.Component;

import com.helome.monitor.utils.hadoop.UserUtil;

class HBaseConnectionFactory extends BasePoolableObjectFactory {
	
	Configuration hadoopConfig
	User user
	
	public HBaseConnectionFactory() {
		hadoopConfig = HBaseConfiguration.create()
		hadoopConfig.addResource(Thread.currentThread().getContextClassLoader().getResourceAsStream("./hbasecnf/hbase-site.xml"))
//		hadoopConfig.addResource(Thread.currentThread().getContextClassLoader().getResourceAsStream("./hbasecnf/core-site.xml"))
//		hadoopConfig.addResource(Thread.currentThread().getContextClassLoader().getResourceAsStream("./hbasecnf/hdfs-site.xml"))
		user = UserUtil.createUserForTesting(hadoopConfig, "hbase", ["supergroup"] as String[])
	}

	@Override
	public Object makeObject() throws Exception {
		return HConnectionManager.createConnection(hadoopConfig, user)
	}

	@Override
	public void destroyObject(HConnection obj) throws Exception {
		obj?.close()
	}
}
