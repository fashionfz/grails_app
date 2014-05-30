package com.helome.monitor

/**
 * 监控指标
 */
class Indicator implements java.io.Serializable{

    /**
     * 指标名称
     */
    String name
    /**
     * 编码
     */
    String code
    /**
     * 指标命令
     */
    String command
    /**
     * 备注
     */
    String remark
    /**
     * 指标类型
     * 1--性能指标,2--应用指标，3--自定义指标
     */
    Integer type
    /**
     * 启用状态
     */
    Integer enabled = 1
    /**
     * 逻辑删除标识
     */
    Integer disabled = 0

	/**
	 * 对应的实体名称
	 */
	String entityName;
	
    /**
     * 多对多:含有多个服务器
     */
    static hasMany = [servers: ServerInfo]
    /**
     * 由服务器和应用来维护关系
     */
    static belongsTo = [ServerInfo]
	
    static constraints = {
        name size: 1..50, nullable: false, blank: false
//        code nullable: false, blank: false, unique: true
        command nullable: true
        remark nullable: true
        type nullable: false
		entityName nullable:true
    }
	static namedQueries = {
		listAvilableIndicator{
			eq 'disabled',0
		}
	}
}
