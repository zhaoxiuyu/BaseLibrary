package com.base.library.util

import com.blankj.utilcode.util.Utils
import com.tencent.mmkv.MMKV

/**
 * MMKV 统一存储工具类,以后方便替换成其他的存储方式,只需要替换实现即可
 */
object MMKVUtils {

    private val mmkv: MMKV

    init {
        MMKV.initialize(Utils.getApp())
        mmkv = MMKV.defaultMMKV()
    }

    fun put(key: String, value: Any) {
        when (value) {
            is Boolean -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is ByteArray -> mmkv.encode(key, value)
        }
    }

    fun getBool(key: String) = mmkv.decodeBool(key, false)

    fun getStr(key: String) = mmkv.decodeString(key, "")

    fun getInt(key: String) = mmkv.decodeInt(key, 0)

    fun getLong(key: String) = mmkv.decodeLong(key, 0)

    fun getFloat(key: String) = mmkv.decodeFloat(key, 0f)

    fun getBytes(key: String) = mmkv.decodeBytes(key)

    fun removeValueForKey(key: String) {
        mmkv.removeValueForKey(key)
    }

    fun removeValuesForKeys(keys: Array<String>) {
        mmkv.removeValuesForKeys(keys)
    }

}