package com.helome.monitor

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

/**
 * 字段控制器
 */
class DictionaryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [dictionaryInstanceList: Dictionary.list(params), dictionaryInstanceTotal: Dictionary.count()]
    }

    def create() {
        [dictionaryInstance: new Dictionary(params)]
    }

    def save() {
        def dictionaryInstance = new Dictionary(params)
        if (!dictionaryInstance.save(flush: true)) {
            render(view: "create", model: [dictionaryInstance: dictionaryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), dictionaryInstance.id])
        redirect(action: "show", id: dictionaryInstance.id)
    }

    def show(Long id) {
        def dictionaryInstance = Dictionary.get(id)
        if (!dictionaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), id])
            redirect(action: "list")
            return
        }

        [dictionaryInstance: dictionaryInstance]
    }

    def edit(Long id) {
        def dictionaryInstance = Dictionary.get(id)
        if (!dictionaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), id])
            redirect(action: "list")
            return
        }

        [dictionaryInstance: dictionaryInstance]
    }

    def update(Long id, Long version) {
        def dictionaryInstance = Dictionary.get(id)
        if (!dictionaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dictionaryInstance.version > version) {
                dictionaryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'dictionary.label', default: 'Dictionary')] as Object[],
                        "Another user has updated this Dictionary while you were editing")
                render(view: "edit", model: [dictionaryInstance: dictionaryInstance])
                return
            }
        }

        dictionaryInstance.properties = params

        if (!dictionaryInstance.save(flush: true)) {
            render(view: "edit", model: [dictionaryInstance: dictionaryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), dictionaryInstance.id])
        redirect(action: "show", id: dictionaryInstance.id)
    }

    def delete(Long id) {
        def dictionaryInstance = Dictionary.get(id)
        if (!dictionaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), id])
            redirect(action: "list")
            return
        }

        try {
            dictionaryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dictionary.label', default: 'Dictionary'), id])
            redirect(action: "show", id: id)
        }
    }

    /**
     * 通过编码类型查询，通常在下拉菜单是使用
     * @param codeType
     */
    def listByCodeType(String codeType) {
        def list = Dictionary.where{codeType == codeType}.list();
        render list as JSON
    }

}
