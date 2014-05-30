package com.helome.monitor.service

import com.helome.monitor.OnlineUsers

/**
 * Created by helome on 14-3-5.
 */
public interface IOnlineUsersService {

    public List<OnlineUsers> queryLatest()

    public int findOnlineUserCount()

    public List<OnlineUsers> queryHighestLevel()
}