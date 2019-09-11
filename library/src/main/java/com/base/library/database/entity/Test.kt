package com.base.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Test {

    @PrimaryKey(autoGenerate = true) // 设置主键 并且 自动增长(id系统自动生成,可以写,但没必要)
    var id: Int = 0

    @ColumnInfo(name = "word") // 设置别名,不设置默认就是变量名
    var word: String = ""

    var msg: String = ""

}