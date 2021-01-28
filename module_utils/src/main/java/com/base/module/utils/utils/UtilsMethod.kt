package com.base.module.utils.utils

import com.base.module.utils.entity.UtilsDescribe

object UtilsMethod {

    fun getUtilsDescribe(): MutableList<UtilsDescribe> {
        val datas = mutableListOf<UtilsDescribe>()

        datas.add(UtilsDescribe("AppStoreUtils", "应用商店相关", null))
        datas.add(UtilsDescribe("BatteryUtils", "电池相关", null))
        datas.add(UtilsDescribe("ClipboardUtils", "剪贴板相关", null))
        datas.add(UtilsDescribe("CoordinateUtils", "坐标转换相关", null))
        datas.add(UtilsDescribe("CountryUtils", "国家相关", null))
        datas.add(UtilsDescribe("DangerousUtils", "危险相关", null))
        datas.add(UtilsDescribe("LocationUtils", "定位相关", null))
        datas.add(UtilsDescribe("PinyinUtils", "拼音相关", null))
        datas.add(UtilsDescribe("MathUtils", "数学计算类", null))
        datas.add(UtilsDescribe("ViewUtils", "View相关", null))
        datas.add(UtilsDescribe("SoundPoolUtils", "短音频 + 震动", null))
        datas.add(UtilsDescribe("MMKVUtils", "MMKV工具类", null))
        datas.add(UtilsDescribe("RxCacheUtils", "使用RxJava进行磁盘缓存相关", null))
        datas.add(UtilsDescribe("JsonTool", "Json解析,拼装类", null))
        datas.add(UtilsDescribe("EncryptUtils", "加密解密相关的工具类", null))

        return datas
    }

}