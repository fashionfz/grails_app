package com.helome.monitor

/**
 * 字典表
 * 包含布尔, 验证类型，警告阀值等内容
 */
class Dictionary {
    /**
     * 编码
     */
    String code
    /**
     * 中文名称
     */
    String name
    /**
     * 值
     */
    Integer value
    /**
     * 类型：
     * 布尔 -- bool
     * 验证类型 -- validateType
     * 告警阀值 -- warningValve
     * COM接口 -- comport
     * SM卡模型 -- model
     * 指标 -- target
     */
    String codeType

	static mapping = {
		cache true
	}
	
    static constraints = {
        code unique: true, nullable: false
    }

    /**
     * 查找字典中类型对应的值的中文名称
     * @param value 值
     * @param codeType 字典类型
     * @return 中文名称
     */
    static String findName(value, codeType) {
       return Dictionary.find {
               value == value && codeType == codeType
       }?.name
    }

}
