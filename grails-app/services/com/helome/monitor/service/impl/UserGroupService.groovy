package com.helome.monitor.service.impl

import com.helome.monitor.User
import com.helome.monitor.UserGroup;

class UserGroupService {
	
	static transactional = true
	
    def deleteGroupRelationWithUser(User user) {
		UserGroup.where {
			users.id == user.id
		}.list()?.each {UserGroup userGroup ->
			userGroup.removeFromUsers(user)
		}
    }
}
