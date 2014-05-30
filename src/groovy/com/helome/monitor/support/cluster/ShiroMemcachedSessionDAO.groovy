package com.helome.monitor.support.cluster

import org.apache.shiro.session.Session
import org.apache.shiro.session.mgt.eis.CachingSessionDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.helome.monitor.service.impl.MemcachedService

@Component("shiroSessionDAO")
class ShiroMemcachedSessionDAO extends CachingSessionDAO {
	
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		return null; //should never execute because this implementation relies on parent class to access cache, which
		//is where all sessions reside - it is the cache implementation that determines if the
		//cache is memory only or disk-persistent, etc.
	}

	protected void doUpdate(Session session) {
		//does nothing - parent class persists to cache.
	}

	protected void doDelete(Session session) {
		//does nothing - parent class removes from cache.
	}
	
//	private ConcurrentHashMap getShiroSessionStore(){
//		def store = memcachedService.findWithKey(SHIRO_SESSION_STORE_KEY);
//		if(store == null){
//			store = new ConcurrentHashMap();
//		}
//		return store;
//	}
//	
//	private void mergeShiroSessionStore(store){
//		memcachedService.save(SHIRO_SESSION_STORE_KEY, 30 * 60, store)
//	}
//	
//	@Override
//	public void delete(Session shiroSession) {
//		if (shiroSession == null) {
//			throw new NullPointerException("session argument cannot be null.");
//		}
//		Serializable serializableId = shiroSession.getId();
//		if (serializableId != null) {
//			def store = getShiroSessionStore()
//			store.remove(serializableId)
//			mergeShiroSessionStore(store)
//		}
//	}
//
//	@Override
//	public Collection<Session> getActiveSessions() {
//		getShiroSessionStore()?.values()
//	}
//
//	@Override
//	public void update(Session arg0) throws UnknownSessionException {
//		storeSession(arg0.getId(), arg0)
//	}
//
//	@Override
//	protected Serializable doCreate(Session shiroSession) {
//		Serializable sessionId = generateSessionId(shiroSession);
//		assignSessionId(shiroSession, sessionId);
//		storeSession(sessionId, shiroSession);
//		return sessionId;
//	}
//	
//	@Override
//	protected Session doReadSession(Serializable arg0) {
//		getShiroSessionStore().get(arg0)
//	}
//
//	protected Session storeSession(Serializable sessionId, Session shiroSession) {
//		if (sessionId == null) {
//			throw new NullPointerException("id argument cannot be null.");
//		}
//		def store = getShiroSessionStore()
//		store.putIfAbsent(sessionId, shiroSession);
//		mergeShiroSessionStore(store)
//		
//		shiroSession
//	}
}
