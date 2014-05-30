package com.helome.monitor.support;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.helome.monitor.bean.HostBean;
import com.helome.monitor.service.impl.ServerInfoService;

/**
 * Socket连接监测
 * @author hadoop
 */
@Component
public class SocketChecker {
	
	private Logger log = Logger.getLogger(SocketChecker.class);
	
	@Autowired
	private ServerInfoService serverInfoService;
	
	/**
	 * 轮询间隔时间(ms)
	 */
	@Value("${checker.interval}")
	private long waitMillionSeconds;
	
	/**
	 * 连接失败重复阀值
	 */
	@Value("${checker.repeatcount}")
	private int repeatCount;
	
	/**
	 * HostBean对应的报警重复次数的映射
	 */
	@Value("#{grailsCacheManager.getCache(T(com.helome.monitor.Constant).CACHE_REPEATCOUNT_NAME)}")
	private Cache repeatCountCache;
	
	/**
	 * 网络不可达的HostBean
	 */
	@Value("#{grailsCacheManager.getCache(T(com.helome.monitor.Constant).CACHE_UNREACHABLE_NAME)}")
	private Cache unReachableHost;
	
	@Resource(name="socketCheckExecutor")
	private ThreadPoolTaskExecutor checkExecutor;
	
	/**
	 * HostBean对应Socket的映射
	 */
	private Map<HostBean,Socket> socketMap;
	
	/**
	 * 检查指定对象是否超过了重复阀值
	 * @param hostBean
	 * @return
	 */
	private boolean repeatCheck(HostBean hostBean){
		AtomicInteger hasRepeatCount = null;
		if(repeatCountCache.get(hostBean) == null){
			hasRepeatCount = new AtomicInteger(1);
			repeatCountCache.put(hostBean, hasRepeatCount);
		}else{
			hasRepeatCount = (AtomicInteger) repeatCountCache.get(hostBean).get();
			hasRepeatCount.incrementAndGet();
			repeatCountCache.put(hostBean,hasRepeatCount);
		}
		return hasRepeatCount.intValue() >= repeatCount;
	}
	
	/**
	 * 重置指定对象的警告重复次数
	 * @param hostBean
	 */
	private void resetRepeat(HostBean hostBean){
		repeatCountCache.evict(hostBean);
	}
	
	/**
	 * 轮询向目标端口发送心跳数据包
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void doCheck() throws IOException {
		if(CollectionUtils.isEmpty(socketMap)){
			initSocket();
		}
		for(HostBean hostBean : getHosts()){
			Socket socket = socketMap.get(hostBean);
			checkExecutor.execute(new SocketSender(socket,hostBean));
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<HostBean> getHosts(){
		return (List<HostBean>) serverInfoService.listHostBean();
	}
	
	/**
	 * 初始化HostBean与Socket对象的映射集合
	 * @return
	 */
	public void initSocket(){
		socketMap = new HashMap<HostBean, Socket>();
		for(HostBean hostBean : getHosts()){
			Socket socket = connect(hostBean);
			socketMap.put(hostBean, socket);
		}
	}
	
	/**
	 * 连接目标端口,如果ConnectException,则执行连接失败相关逻辑
	 * @param hostBean
	 * @return
	 */
	public Socket connect(HostBean hostBean){
		Socket socket = null;
		try {
			socket = new Socket(hostBean.getIp(),Integer.parseInt(hostBean.getPort()));
			socket.setKeepAlive(true);
			socket.setSoTimeout(10000);
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			if(e instanceof ConnectException){
				log.warn("警告,目标服务器("+hostBean.getIp()+")无法正常连接");
				unReachableHost.put(hostBean.getIp(), hostBean);
				//MODIFY 
				//notifyService.notifyByEmail(hostBean.getIp()+" 无法正常连接!");
				if(repeatCheck(hostBean)){
					new WarnMessageSender(hostBean).run();
					resetRepeat(hostBean);
				}else{
					log.info("["+hostBean.getIp()+"] Repeat Warn Count: "+repeatCountCache.get(hostBean).get()+" Less Than Configured "+repeatCount);
				}
			}
		}
		return socket;
	}

	public long getWaitMillionSeconds() {
		return waitMillionSeconds;
	}

	public int getRepeatCount() {
		return repeatCount;
	}
	
	class SocketSender implements Runnable{
		
		private Socket socket;
		private HostBean hostBean;
		
		public SocketSender(Socket socket,HostBean hostBean) {
			this.socket = socket;
			this.hostBean = hostBean;
		}

		@Override
		public void run() {
			try {
				socket.sendUrgentData(0xff);
				log.debug("目标: "+hostBean.getIp()+" 正常!");
				repeatCountCache.evict(hostBean);
				unReachableHost.evict(hostBean.getIp());
			} catch (Exception e) {
				if(socket != null)
					try {
						socket.close();
					} catch (IOException e1) {}
				log.debug("目标: "+hostBean.getIp()+" 重连!!!");
				socket = connect(hostBean);
				socketMap.put(hostBean, socket);
			}
		}
		
	}
}
