package com.base.library.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.base.library.database.dao.TestDao
import com.base.library.database.entity.Test

/**
 * 数据库持有者,应用程序持久化 关系型数据的底层连接主要访问点
 * entities 数据库表的实体
 * version 数据库版本号
 * exportSchema 未知
 */
@Database(
    entities = [Test::class],
    version = 1
)
abstract class DataBase : RoomDatabase() {

    abstract fun getTestDao(): TestDao

}