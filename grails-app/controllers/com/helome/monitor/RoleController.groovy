package com.helome.monitor

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * 
 * @ClassName: RoleController
 * @Description: 角色配置管理
 * @author bin.deng@helome.com
 * @date 2014年3月21日 下午3:55:58
 *
 */
class RoleController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 
	  * rolelist
	  *
	  * @Title: rolelist
	  * @Description: 角色列表主页面
	  * @return
	  * @throws
	 */
	def rolelist(){
		render(view:'rolelist',model:[code:params.code]);
	}
	/**
	 * 
	  * saveRole
	  *
	  * @Title: saveRole
	  * @Description: 保存角色
	  * @return
	  * @throws
	 */
	def saveRole(){
		String pid = params.id;
		Role role;
		if(pid!=null&&!"".equals(pid)){
			role = Role.get(Long.parseLong(pid));
		}else{
			role = new Role();
		}
		role.setName(params.name);
		role.setRealname(params.realname);
		role.save(flush:true);
		redirect(action:'rolelist');
	}
	/**
	 * 
	  * personlist
	  *
	  * @Title: personlist
	  * @Description: 值班人员列表主页面
	  * @return
	  * @throws
	 */
	def personlist() {
	}
	
	/**
	 * 
	  * savePerson
	  *
	  * @Title: savePerson
	  * @Description: 保存值班人员
	  * @return
	  * @throws
	 */
	def savePerson() {
//		String pid = params.id;
//		String name = URLDecoder.decode(params.name);
//		String telephone = URLDecoder.decode(params.telephone);
//		String role = URLDecoder.decode(params.role);
//		String email = URLDecoder.decode(params.email);
//		String way = params.way;
//		def person;
//		if(null != pid && !"".equals(pid)){
//			person = PersonOnDuty.get(Long.parseLong(pid));
//		}else{
//		    person =new PersonOnDuty();
//		}
//		person.setName(name);
//		person.setTelephone(telephone);
//		person.setRole(role);
//		person.setEmail(email);
//		Notifyway notifyWay = new Notifyway();
//		notifyWay.setId(Integer.parseInt(way));
//		person.setNotifyway(notifyWay);
//		person.save(flush:true);
		redirect(action:'personlist')
	}
	
	/**
	  * deleteRole(这里用一句话描述这个方法的作用)
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: deleteRole
	  * @Description: TODO
	  * @return
	  * @throws
	  */
	def deleteRole(){
		long id = params.long('roleid');
		Role role = Role.get(id);
		params.code=0;
		try{
			role.delete(flush:true);
		}catch(Exception e){
			log.info(e.getMessage());
			params.code=1;
		}
		//更新缓存
//		queryAllMenu();
		redirect(action:'rolelist',params: params);
	}
	
	/**
	 * 
	  * deletePerson
	  *
	  * @Title: deletePerson
	  * @Description: 删除值班人员
	  * @return
	  * @throws
	 */
	def deletePerson(){
//		def person = PersonOnDuty.get(params.id)
//		person.delete(flush:true);
		render(view:'personlist')
	}
}
