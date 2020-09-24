package com.base.app.entitys

import android.app.Activity

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