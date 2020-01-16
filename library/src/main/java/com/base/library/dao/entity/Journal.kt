package com.base.library.dao.entity

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.TimeUtils
import org.litepal.crud.LitePalSupport

class Journal : LitePalSupport() {

    var content: String = ""// 内容

    var behavior: String = "" // 行为

    var time: String = TimeUtils.getNowString() // 时间

    var level: String = "I" // 等级

    var packageName: String = AppUtils.getAppPackageName() // 包名

    var version: String = AppUtils.getAppVersionName() // 版本

}