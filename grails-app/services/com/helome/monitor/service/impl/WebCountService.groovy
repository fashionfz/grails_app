package com.helome.monitor.service.impl

import org.hibernate.SQLQuery
import org.hibernate.Session
import org.hibernate.SessionFactory

class WebCountService {

	def SessionFactory sessionFactory;
	
    def queryWebCount(long begin,long end,String prduct) {
		
		String sql="select sum(num) from monitor_count where viste_time >? and viste_time <? and prduct = ? group by prduct,count_type order by count_type asc"
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, begin);
		query.setParameter(1, end);
		query.setParameter(2, prduct);
		return query.list();
    }
}
