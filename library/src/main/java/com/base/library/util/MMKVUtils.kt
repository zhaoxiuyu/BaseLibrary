package com.base.library.util

import com.blankj.utilcode.util.Utils
import com.tencent.mmkv.MMKV

/**
 * MMKV 统一存储工具类,以后方便替换成其他的存储方式,只需要替换实现即可
 */
object MMKVUtils {

    private val mmkv by lazy { MMKV.defaultMMKV() }

    init {
        MMKV.initialize(Utils.getApp())
    }

    fun put(key: String, value: Any) {
        when (value) {
            is Boolean -> mmkv?.encode(key, value)
            is String -> mmkv?.encode(key, value)
            is Int -> mmkv?.encode(key, value)
            is Long -> mmkv?.encode(key, value)
            is Float -> mmkv?.encode(key, value)
            is ByteArray -> mmkv?.encode(key, value)
        }
    }

    fun getBool(key: String, default: Boolean = false) = mmkv?.decodeBool(key, default) ?: default

    fun getStr(key: String, default: String = "") = mmkv?.decodeString(key, default) ?: default

    fun getInt(key: String, default: Int = 0) = mmkv?.decodeInt(key, default) ?: default

    fun getLong(key: String, default: Long = 0) = mmkv?.decodeLong(key, default) ?: default

    fun getFloat(key: String, default: Float = 0.0f) = mmkv?.decodeFloat(key, default) ?: default

    fun getBytes(key: String) = mmkv?.decodeBytes(key)

    fun removeValueForKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun removeValuesForKeys(keys: Array<String>) {
        mmkv?.removeValuesForKeys(keys)
    }

}