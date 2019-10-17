package com.base.library.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Embedded



/**
 * 实体类，表示数据库表的数据
 */
@Entity
class Test {

    @PrimaryKey(autoGenerate = true) // 设置主键 并且 自动增长(id系统自动生成,可以写,但没必要)
    var id: Int = 0

    @ColumnInfo(name = "word") // 数据表中字段名称,不设置默认就是变量名
    var word: String = ""

    var msg: String = ""

    @Ignore // 标注不需要添加到数据表中的属性
    var age: String = ""

//    @Embedded // 实体类中引用其他实体类
//    private val address: Test? = null

}