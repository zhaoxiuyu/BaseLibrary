package com.base.module.utils.entity

class UtilsDescribe {

    var name = ""
    var describe = ""
    var cls: Class<*>? = null

    constructor(name: String, describe: String, cls: Class<*>?) {
        this.name = name
        this.describe = describe
        this.cls = cls
    }

}