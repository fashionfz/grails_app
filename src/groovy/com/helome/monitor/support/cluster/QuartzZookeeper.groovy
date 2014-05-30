package com.helome.monitor.support.cluster

import java.util.concurrent.CountDownLatch

import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.StringUtils
import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import org.apache.zookeeper.Watcher.Event.KeeperState
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.ZooKeeper.States
import org.apache.zookeeper.data.Stat
import org.quartz.Scheduler

class QuartzZookeeper implements Runnable {

	String groupid="monitor"
	String subNode="quartz"
	
	// "172.16.4.94:2181,172.16.4.95:2181"
	String connectString
	Scheduler scheduler
	String param
	String createdPath
	
	
	int localNo
	ZooKeeper zk
	
	
	final CountDownLatch connectedLatch = new CountDownLatch(1)
	
	public QuartzZookeeper(String connectString, Scheduler scheduler, String param) {
		this.scheduler = scheduler;
		this.param = param;
		this.connectString = connectString;
	}
	
	private void start(){
		try {
			zk = new ZooKeeper(connectString, 5000, new Watcher(){
				@Override
				public void process(WatchedEvent event) {
					if(event.getState() == KeeperState.SyncConnected){
						connectedLatch.countDown();
					}
					update()
				}
			});
		
			if(States.CONNECTING == zk.getState()){
				connectedLatch.await();
			}
			zk.addAuthInfo("digest", (groupid+":"+subNode).getBytes())
			createdPath = zk.create("/"+groupid+"/"+subNode, param.getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL_SEQUENTIAL)
			localNo = Integer.parseInt(createdPath.substring(groupid.length()+subNode.length() + 2));
		} catch (Exception e) {
			e.printStackTrace()
		}
	}
	
	private void update(){
		Stat stat = new Stat();
		List<Integer> noList = new ArrayList<Integer>();
		try {
			List<String> subList = zk.getChildren("/"+groupid, true);
			for(String subNode : subList){
				if(StringUtils.isNotBlank(subNode))
					noList.add(Integer.parseInt(subNode.substring(this.subNode.length())));
				// 获取每个子节点下关联的server地址
//				byte[] data = zk.getData("/monitor/" + subNode, false, state);
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
		
		log.info "Children Suffix Number: $noList"
		
		int min = 0
		if(CollectionUtils.isNotEmpty(noList)){
			min = Collections.min(noList)
		}
		
		quartzSchdulerChangeStatu(min)
	}
	
	private void quartzSchdulerChangeStatu(int min){
		try {
			boolean isStart = scheduler.isStarted()
			if(min == localNo){
				if(!isStart || scheduler.isInStandbyMode()){
					log.info "Scheduler Start!!!"
					scheduler.start();
				}
			}else{
				if(isStart && !scheduler.isInStandbyMode()){
					log.info "Scheduler Standby!!!"
					scheduler.standby();
				}
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}
	
	@Override
	public void run() {
		start()
	}

}
