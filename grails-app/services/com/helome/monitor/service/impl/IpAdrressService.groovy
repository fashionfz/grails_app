package com.helome.monitor.service.impl

import net.rubyeye.xmemcached.XMemcachedClient

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.grails.web.json.JSONTokener
import org.springframework.beans.factory.annotation.Autowired

import com.helome.monitor.LoginArea

class IpAdrressService {

	@Autowired
	XMemcachedClient memcachedClient
	@Autowired
	HttpClient httpClient;
	
	public static final String IP_SEARCH_URL="http://ip.taobao.com/service/getIpInfo.php?ip=";
	
    def getAreaByIp(LoginArea login) {
		JSONObject json = null;
		String responseContent = memcachedClient.get(login.ip);
		
		if(responseContent==null||"".equals(responseContent)){
			InputStream responseStream = null;
			BufferedReader rd = null;
			
			GetMethod method = new GetMethod(IP_SEARCH_URL+login.ip);
			httpClient.executeMethod(method);
			responseStream = method.getResponseBodyAsStream();
			
			rd = new BufferedReader(new InputStreamReader(responseStream,"utf-8"));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf=System.getProperty("line.separator");
			
			
			while (tempLine != null)
			{
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			memcachedClient.add(login.ip, 0, responseContent)
			method.releaseConnection();
		}
		
		json = new JSONObject(new JSONTokener(responseContent));
		String code = json.getString("code");
		if("0".equals(code)){
			JSONObject main = json.get("data");
			login.country = main.getString("country");//国家
			login.area = main.getString("area");
			login.region = main.getString("region");//省
			login.city = main.getString("city");//市
			login.county = main.getString("county");
			login.isp = main.getString("isp");//运营商
			login.status=1;
			login.save();
		}else{
			login.status=2;
			login.save();
		}
    }
	
	
	def convert(){
		int i=0
		def q = LoginArea.where{
			status == 0
		}
		q.each {LoginArea login ->
			if(i%8==0){
				Thread.sleep(1000);
			}
			getAreaByIp(login);
			i++;
		}
	}
}
