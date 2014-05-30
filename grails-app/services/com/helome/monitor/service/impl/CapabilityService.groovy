

package com.helome.monitor.service.impl

import java.lang.reflect.Field
import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hbase.Cell
import org.apache.hadoop.hbase.CellUtil
import org.apache.hadoop.hbase.client.HConnection
import org.apache.hadoop.hbase.client.HTableInterface
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.client.ResultScanner
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.filter.Filter
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp
import org.apache.hadoop.hbase.util.Bytes
import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.Indicator
import com.helome.monitor.utils.MonitorProxy
import com.helome.monitor.utils.hbase.HBaseConnectionProvider
import com.helome.monitor.Constant.IndicatorNameMeta;

class CapabilityService {

	@Autowired
	HBaseConnectionProvider hbaseConnectionProvider
	
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	def HTableInterface getTable(HConnection conn,String tableName) {
		
		if (StringUtils.isEmpty(tableName))
			return null;

		try {
			return conn.getTable(getBytes(IndicatorNameMeta.TABLENAME_PREFIX+tableName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* 转换byte数组 */
	def byte[] getBytes(String str) {
		if (str == null)
			str = "";

		return Bytes.toBytes(str);
	}
			
    def queryMonitorMessages(Map params,Indicator index,Date start,Date end) {
		
		HConnection conn = hbaseConnectionProvider.acquireConnection()
		
		Binding binding = new Binding();
    	List<Object> list = new ArrayList<Object>();

		ResultScanner scanner = null;
		// 为分页创建的封装类对象，下面有给出具体属性
		try {
			// 获取最大返回结果数量
			if (params.max == null || params.max == 0L)
				params.max = 2;

			if (params.offset == null || params.offset == 0)
				params.offset = 1;

			// 计算起始页和结束页
			Integer firstPage = params.int('offset');

			Integer endPage = firstPage + params.int('max');

			// 从表池中取出HBASE表对象
			HTableInterface table = getTable(conn,index.code);
			// 获取筛选对象
			Scan scan = getScan(params.hostname,df.format(start), df.format(end));
			// 缓存1000条数据
			scan.setCaching(100000);
			scan.setCacheBlocks(true);
			scanner = table.getScanner(scan);
			int i = 0;
			def fields = MonitorProxy.getFields(index.entityName);
			// 遍历扫描器对象， 并将需要查询出来的数据row key取出
			String cellName="";
			String value ="";
			Date time=null;
			for (Result result : scanner) {
				if (i >= firstPage && i < endPage) {
					Object obj = MonitorProxy.getObject(index.entityName);
					for(Cell cell : result.rawCells()) {
						cellName = new String(CellUtil.cloneQualifier(cell))
						value = new String(CellUtil.cloneValue(cell))
						if(cellName.equals("time")){
							time = df.parse(value);
							value = df2.format(time);
						}
						for(Field field : fields) {
							if(field.getName().equals(cellName)){
								MonitorProxy.setter(obj, cellName, value, field.getType())
							}
						}
					}
					list.add(obj);
				}
				i++;
			}
			
			binding.obj = list;
			binding.totalCount=i;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeScanner(scanner);
			hbaseConnectionProvider.closeConnection(conn);
		}

		return binding;
	}

	def int getTotalPage(int pageSize, int totalCount) {
		int n = totalCount / pageSize;
		if (totalCount % pageSize == 0) {
			return n;
		} else {
			return ((int) n) + 1;
		}
	}

	// 获取扫描器对象
	def Scan getScan(String hostname,String beginTime, String endTime) {
		Scan scan = new Scan();
		List<Filter> filters = new LinkedList<Filter>();
		Filter filter = new SingleColumnValueFilter("indicator".getBytes(), "time".getBytes(), CompareOp.GREATER_OR_EQUAL, beginTime.getBytes());
		filters.add(filter);
		filter = new SingleColumnValueFilter("indicator".getBytes(), "time".getBytes(), CompareOp.LESS_OR_EQUAL, endTime.getBytes());
		filters.add(filter);
		
		if(hostname){
			filter = new SingleColumnValueFilter("indicator".getBytes(), "server_name".getBytes(), CompareOp.EQUAL, hostname.getBytes());
			filters.add(filter);
		}
		
		FilterList filterList = new FilterList(filters);
		scan.setFilter(filterList);
		return scan;
	}

	def void closeScanner(ResultScanner scanner) {
		if (scanner != null)
			scanner.close();
	}
}
