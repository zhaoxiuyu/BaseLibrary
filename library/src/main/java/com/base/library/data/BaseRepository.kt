package com.base.library.data

import com.blankj.utilcode.util.CacheDiskStaticUtils

open class BaseRepository {

    /**
     * 添加磁盘缓存
     */
    fun putCache(key: String, content: String, time: Int = -1) {
        CacheDiskStaticUtils.put(key, content, time)
    }

    /**
     * 获取磁盘缓存
     */
    fun getCache(key: String, defaultValue: String = ""): String {
        return CacheDiskStaticUtils.getString(key, defaultValue)
    }

}
