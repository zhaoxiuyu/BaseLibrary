package com.base.common.entitys

// 页面描述
class PageDescribe {

    var name = ""
    var describe = ""
    var cls: Class<*>? = null
    var path: String? = null

    constructor(name: String, describe: String, cls: Class<*>?, path: String? = null) {
        this.name = name
        this.describe = describe
        this.cls = cls
    }

}