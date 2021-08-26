package com.base.library.data.local

import com.blankj.utilcode.util.CacheDiskStaticUtils

/**
 * 本地数据源
 */
class LocalDataSourceImpl private constructor() {

    companion object {
        val getInstance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = LocalDataSourceImpl()
    }

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