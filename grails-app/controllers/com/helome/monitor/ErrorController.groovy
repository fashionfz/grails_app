package com.helome.monitor
/**
 * 错误页面跳转
 */
class ErrorController {

    def forbidden = {
        // Optional custom error handler code
    }

    def notFound = {
        // Optional custom error handler code
    }
	
	def error = {
		render(view:"/error")
	 }

}
