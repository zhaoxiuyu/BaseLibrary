package com.base.app.entitys

// 页面描述
class PageDescribe {

    var name = ""
    var describe = ""
    var cls: Class<*>? = null

    constructor(name: String, describe: String, cls: Class<*>?) {
        this.name = name
        this.describe = describe
        this.cls = cls
    }

}