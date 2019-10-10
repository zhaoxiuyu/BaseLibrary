package com.base.library.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.base.library.database.dao.JournalRecordDao
import com.base.library.database.dao.TestDao
import com.base.library.database.database.DataBase
import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.Utils

object DataBaseUtils {

    private val dataBase = Room.databaseBuilder(
        Utils.getApp(),
        DataBase::class.java, "${SDCardUtils.getSDCardInfo()[0].path}/RoomBaseLibrary"
    )
        .setJournalMode(RoomDatabase.JournalMode.TRUNCATE) // 日志模式,立即写入数据库,默认调用close()关闭方法才会写入
//        .allowMainThreadQueries() // todo 允许主线程查询,仅用于测试
        .build()

    fun getTestDao(): TestDao = dataBase.getTestDao()

    fun getJournalRecordDao(): JournalRecordDao = dataBase.getJournalRecordDao()

    fun close() {
        if (dataBase.isOpen) dataBase.close()
    }

}

