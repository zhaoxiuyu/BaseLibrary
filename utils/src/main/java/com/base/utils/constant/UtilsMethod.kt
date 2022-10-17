package com.base.utils.constant

import com.base.common.entitys.PageDescribe

/**
 * 测试用的静态方法
 */
object UtilsMethod {

    // Utils 列表数据
    fun getUtilsDescribe(): MutableList<PageDescribe> {
        val datas = mutableListOf<PageDescribe>()

        datas.add(PageDescribe("AppStoreUtils", "应用商店相关", null))
        datas.add(PageDescribe("BatteryUtils", "电池相关", null))
        datas.add(PageDescribe("ClipboardUtils", "剪贴板相关", null))
        datas.add(PageDescribe("CoordinateUtils", "坐标转换相关", null))
        datas.add(PageDescribe("CountryUtils", "国家相关", null))
        datas.add(PageDescribe("DangerousUtils", "危险相关", null))
        datas.add(PageDescribe("LocationUtils", "定位相关", null))
        datas.add(PageDescribe("PinyinUtils", "拼音相关", null))
        datas.add(PageDescribe("MathUtils", "数学计算类", null))
        datas.add(PageDescribe("ViewUtils", "View相关", null))
        datas.add(PageDescribe("SoundPoolUtils", "短音频 + 震动", null))
        datas.add(PageDescribe("MMKVUtils", "MMKV工具类", null))
        datas.add(PageDescribe("RxCacheUtils", "使用RxJava进行磁盘缓存相关", null))
        datas.add(PageDescribe("JsonTool", "Json解析,拼装类", null))
        datas.add(PageDescribe("EncryptUtils", "加密解密相关的工具类", null))

        return datas
    }

}