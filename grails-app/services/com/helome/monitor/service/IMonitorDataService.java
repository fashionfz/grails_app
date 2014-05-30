package com.helome.monitor.service;

import groovy.lang.Binding;

import java.util.Date;
import java.util.Map;

import com.helome.monitor.ServerInfo;
import com.helome.monitor.indicators.QueryBean;

public interface IMonitorDataService{
	public Binding queryCollectionFromDB(QueryBean baseData,String entityName,Map metaParam);
	
	public Binding queryMonitorMessages(QueryBean baseData,String entityName,Map metaParam);
	
	public void clearBaseData();
}