package com.helome.monitor

class MonitorMenu implements Comparable {

	String menuName;
	String contollerName;
	String actionName;
	String path;
	MonitorMenu parent;
	int sort;
	static hasMany = [roles: Role,child:MonitorMenu]
	static belongsTo = Role
    static constraints = {
    }
	SortedSet child

	@Override
	public int compareTo(Object o) {
		return sort -o.sort;
	}}
