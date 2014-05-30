package com.helome.monitor.io

import org.apache.commons.lang.StringUtils;

class PropertiesFile {
	
	private static Properties prop = new Properties();
	
	public void load(InputStream input){
		prop.load(input);
	}
	
	public String get(String key){
		return prop.get(key,StringUtils.EMPTY);
	}
	
	/**
	 * 设置一对键值对<br>
	 * 返回对应键之前的值,null是可能的
	 * @param key
	 * @param value
	 * @return 
	 */
	public Object set(String key,String value){
		return prop.setProperty(key, value);
	}
	
	/**
	 * 把最新的Properties回写到文件
	 * @param file
	 */
	public void flush(File file){
		FileWriter fw = new FileWriter(file, false);
		prop.store(fw, null);
		fw.close();
	}
}
