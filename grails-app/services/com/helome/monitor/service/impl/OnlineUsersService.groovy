package com.helome.monitor.service.impl

import com.helome.monitor.OnlineUsers
import com.helome.monitor.service.IOnlineUsersService
import org.apache.commons.collections.CollectionUtils

/**
 * Created by helome on 14-3-5.
 */
class OnlineUsersService implements IOnlineUsersService {
    @Override
    List<OnlineUsers> queryLatest() {

        def latestDate = OnlineUsers.createCriteria().get {
            projections {
                max "time"
            }
        } as Date

        def results = OnlineUsers.where { time == latestDate }.list();

        return results
    }

    @Override
    int findOnlineUserCount() {
        def latestDate = OnlineUsers.createCriteria().get {
            projections {
                max "time"
            }
        } as Date

        def result = OnlineUsers.executeQuery("select sum(amount) from OnlineUsers where time='" + latestDate + "'");
        if (!CollectionUtils.isEmpty(result))
            return result.get(0)?:0

        return 0
    }

    @Override
    List<OnlineUsers> queryHighestLevel() {

        def results = OnlineUsers.executeQuery("select product, device, max(amount) from OnlineUsers group by product, device")
        return results
    }
}
