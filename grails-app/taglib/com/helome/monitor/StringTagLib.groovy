package com.helome.monitor

class StringTagLib {
	
	static namespace = "helo"
	
	def time = { attrs ->
		out << renderField(attrs)
	}
	
	def renderField(attr){
		String result = ""
		if (attr.value){
			def strVal = attr.value as String
			try {
				result = new StringBuffer(strVal.substring(8)).insert(2, attr.sign?:":").toString()
			} catch (Exception e) {
				e.printStackTrace()
				result = attr.value
			}
		}
		result
	}
}
