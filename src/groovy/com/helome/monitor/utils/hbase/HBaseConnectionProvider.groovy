package com.helome.monitor.utils.hbase

import javax.annotation.Resource;

import org.apache.commons.pool.impl.StackObjectPool
import org.apache.hadoop.hbase.client.HConnection;
import org.springframework.stereotype.Component;

/**
 * HBaseConnection提供类<br>
 * 使用完后一定要调用closeConnection方法
 * @author dt
 *
 */
@Component("hbaseConnectionProvider")
class HBaseConnectionProvider {
	
	@Resource(name="hbaseConnectionPoolProvider")
	StackObjectPool hbaseConnectionPoolProvider
	
	int defaultMaxSize = 10
	int defaultMinSize = 3
	
	def initConnections(int sizes){
		int initialSize = 0
		initialSize = Math.max(Math.min(sizes, defaultMaxSize),defaultMinSize)
		int total = initialSize
		while(initialSize-- > 0){
			hbaseConnectionPoolProvider.addObject();
			log.info "Add HBaseConnection TotalCount: ${total-initialSize} "
		}
	}
	
	def acquireConnection(){
		hbaseConnectionPoolProvider.borrowObject()
	}
	
	def closeConnection(HConnection conn){
		if(conn)
			hbaseConnectionPoolProvider.returnObject(conn)
	}
	
}
