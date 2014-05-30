package com.helome.monitor.service;

import com.helome.monitor.Alarm;
import groovy.lang.Binding;

import java.util.Map;

public interface IAlarmService {
	void save(Alarm alarm);
    Binding query(Map conditions);
    void sendAlarmMessage(Alarm alarm);
}
