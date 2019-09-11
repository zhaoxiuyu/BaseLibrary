package com.base.library.database

import androidx.room.Room
import com.base.library.database.dao.TestDao
import com.base.library.database.database.DataBase
import com.blankj.utilcode.util.Utils

object RoomUtils {

    private val dataBase = Room.databaseBuilder(Utils.getApp(), DataBase::class.java, "RoomBaseLibrary")
//        .allowMainThreadQueries() // todo 允许主线程查询,仅用于测试
        .build()

    fun getTestDao(): TestDao = dataBase.getTestDao()

    fun close() {
        if (dataBase.isOpen) dataBase.close()
    }

}

