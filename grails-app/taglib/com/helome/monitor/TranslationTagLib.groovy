package com.helome.monitor
/**
 * 翻译标签
 */
class TranslationTagLib {

    static namespace = "helo"

    static roleMap = [ROLE_ADMIN:'系统管理员', ROLE_USER: '普通用户']
    //static boolMap = [1: '是', 0:'否']
    //static validateTypeMap = [LOCAL:'本地']

    def translate = { attrs ->
        def writer = out;
        if (attrs.type == "role") {
            writer << roleMap.get(attrs.value)
        } else {
            def name = Dictionary.findName(Integer.valueOf(attrs.value.toString()), attrs.type)
            writer << name
        } //else if (attrs.type == "validateType") {
          //  writer << validateTypeMap.get(attrs.value)
       // } else {
        //    writer << ""
       // }

    }

}
