/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 13-10-29
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    //日期插件
    $(".log-time").on("mouseenter", function () {
        $(this).dateinput({
            format: "yyyy-MM-dd HH:mm",             //format: "yyyy-MM-dd HH:mm",
            showTime: true
        });
    });
});