package com.helome.monitor.service.impl

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.client.HConnection
import org.apache.hadoop.hbase.client.HTableInterface
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.Constant
import com.helome.monitor.utils.hbase.HBaseConnectionProvider

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

class HbaseService {
	
	@Autowired
	HBaseConnectionProvider hbaseConnectionProvider
	
	def createTable(String table, String... familys){
		HConnection conn = hbaseConnectionProvider.acquireConnection()
		HBaseAdmin hBaseAdmin = null
		try {
			hBaseAdmin = new HBaseAdmin(conn)
			if(hBaseAdmin.tableExists(table)) {
				hBaseAdmin.disableTable(table);
				hBaseAdmin.deleteTable(table);
				log.info "$table is exist,detele...."
			}
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(table));
			familys.every {
				tableDescriptor.addFamily(new HColumnDescriptor(it))// "indicator"
			}
			hBaseAdmin.createTable(tableDescriptor); 
			log.info "Table: $table Create Success!"
		} catch (Exception e) {
			e.printStackTrace()
		} finally {
			hBaseAdmin?.close()
			hbaseConnectionProvider.closeConnection(conn)
		}
		
	}
	def queryConcountCondition(String startRowKey,String conType){
		def result = []
		HConnection conn = hbaseConnectionProvider.acquireConnection()
		HTableInterface	htable = conn.getTable(Constant.IndicatorNameMeta.INDICATOR_NAME_CONCOUNT)
		
		byte[] family = Constant.COLUMN_FAMILY.getBytes()
		
		List<Filter> filters = new LinkedList<Filter>();
		Filter filter = new SingleColumnValueFilter(family, "time".getBytes(), CompareOp.GREATER_OR_EQUAL, startRowKey.getBytes());
		filters << filter
		filter = new SingleColumnValueFilter(family, "contype".getBytes(), CompareOp.EQUAL, conType.getBytes())
		filters << filter
		
		Scan scan = new Scan();
		scan.setCacheBlocks(true);
		scan.setCaching(100000);
		FilterList filterList = new FilterList(filters)
		scan.setFilter(filterList)
//		scan.setStartRow(startRowKey?.getBytes());
		ResultScanner results = htable.getScanner(scan)
		try {
			for (Result rowResult : results) {
				def mapper = [:]
				for(Cell cell : rowResult.rawCells()) {
					mapper.put(new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell)));
				}
				result << mapper
			}
		} catch (Exception e) {
			e.printStackTrace()
		}finally{
			results.close();
			htable.close();
			hbaseConnectionProvider.closeConnection(conn);
		}
		result
	}
	
	def saveMonitorBaseData(String table,List records){
		if(StringUtils.isBlank(table) || CollectionUtils.isEmpty(records)) {
			log.info "params (table: $table, records: $records) is invalide"
			return
		}
		
		HConnection conn = hbaseConnectionProvider.acquireConnection()
		HTableInterface	htable = conn.getTable(table)
		Put rowPut = null 
		List<Put> putList = new LinkedList<Put>();
		records?.each { Map record ->
			rowPut = new Put(record.time?.substring(8).bytes)
			record.entrySet()?.each {Map.Entry entry ->
				if(!entry.key?.equals("class")){
					rowPut.add(Constant.COLUMN_FAMILY.bytes, entry.key?.bytes, entry.value?.bytes)
				}
			}
			putList.add(rowPut)
		}
		htable.put(putList);
		htable.close();
		hbaseConnectionProvider.closeConnection(conn);
	}
}
