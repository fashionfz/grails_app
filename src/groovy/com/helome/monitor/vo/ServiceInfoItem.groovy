package com.helome.monitor.vo

/**
 * 应用Vo
 * User: bin.liu
 * Date: 14-1-22
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
class ServiceInfoItem {
    Long id
    Integer type
    String name
    String contentPath
    Integer[] ports
    String indicatorIds = ''
    String monitoringIndicatorIds = ''
}
