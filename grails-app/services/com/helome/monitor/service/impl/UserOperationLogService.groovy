package com.helome.monitor.service.impl

import org.hibernate.Query
import org.hibernate.Session
import org.hibernate.SessionFactory

import com.helome.monitor.UserOperationLog

class UserOperationLogService {

	def SessionFactory sessionFactory;
	
    def List<UserOperationLog> userLog(UserOperationLog log,Date startTime,Date endTime,int offset,int max) {

		Session session = sessionFactory.getCurrentSession();
		
		List<Object> para = new ArrayList<Object>();
		String hql="from UserOperationLog l where 1=1";
		if(startTime!=null){
			hql+=" and l.optTime > ?";
			para.add(startTime);
		}
		if(endTime!=null){
			hql+=" and l.optTime < ?";
			para.add(endTime);
		}
		if(log.clientIp!=null&&!"".equals(log.clientIp)){
			hql+=" and l.clientIp like ?";
			para.add("%"+log.clientIp+"%");
		}
		if(log.optUserName!=null&&!"".equals(log.optUserName)){
			hql+=" and l.optUserName like ?";
			para.add("%"+log.optUserName+"%");
		}
		if(log.remark!=null&&!"".equals(log.remark)){
			hql+=" and l.typeName =?";
			para.add(log.remark);
		}
		if(log.targetName!=null&&!"".equals(log.targetName)){
			hql+=" and l.targetName like ?";
			para.add("%"+log.targetName+"%");
		}
		hql+=" order by l.optTime desc";
		Query query = session.createQuery(hql);
		for(int i=0;i<para.size();i++){
			query.setParameter(i, para.get(i));
		}
		query.setFirstResult(offset);
		query.setMaxResults(max);
		return query.list()
    }
	
	def int userLog(UserOperationLog log,Date startTime,Date endTime) {
		
				Session session = sessionFactory.getCurrentSession();
				
				List<Object> para = new ArrayList<Object>();
				String hql="select count(l.id)  from UserOperationLog l where 1=1";
				if(startTime!=null){
					hql+=" and l.optTime > ?";
					para.add(startTime);
				}
				if(endTime!=null){
					hql+=" and l.optTime < ?";
					para.add(endTime);
				}
				if(log.clientIp!=null&&!"".equals(log.clientIp)){
					hql+=" and l.clientIp like ?";
					para.add("%"+log.clientIp+"%");
				}
				if(log.optUserName!=null&&!"".equals(log.optUserName)){
					hql+=" and l.optUserName like ?";
					para.add("%"+log.optUserName+"%");
				}
				if(log.remark!=null&&!"".equals(log.remark)){
					hql+=" and l.typeName =?";
					para.add(log.remark);
				}
				if(log.targetName!=null&&!"".equals(log.targetName)){
					hql+=" and l.targetName like ?";
					para.add("%"+log.targetName+"%");
				}
				Query query = session.createQuery(hql);
				for(int i=0;i<para.size();i++){
					query.setParameter(i, para.get(i));
				}
				return query.list().get(0);
			}
	
	def List<String> queryLogType(){
		Session session = sessionFactory.getCurrentSession();
		String hql="select m.logTypeName from OperationMap m group by m.logTypeName";
		Query query = session.createQuery(hql);
		return query.list();
	}
}
