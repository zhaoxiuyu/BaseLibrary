package com.base.library.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.TimeUtils

@Entity
class JournalRecord {

    @PrimaryKey(autoGenerate = true) // 设置主键 并且 自动增长(id系统自动生成,可以写,但没必要)
    var id: Int = 0

    var content: String = ""// 内容

    var behavior: String = "" // 行为

    var time: String = TimeUtils.getNowString() // 时间

    var level: String = "I" // 等级

    var packageName: String = AppUtils.getAppPackageName() // 包名

    var version: String = AppUtils.getAppVersionName() // 版本

}