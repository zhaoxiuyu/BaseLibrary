package com.base.app.base

import com.base.app.entitys.PageDescribe
import com.base.app.sample.mvi.activity.Demo5Activity
import com.base.app.sample.mvp.activity.Demo1Activity
import com.base.app.sample.views.activity.AnimatedTextViewActivity
import com.base.app.sample.views.activity.MotionLayoutActivity
import com.base.app.sample.views.activity.TriangleViewActivity
import com.base.app.sample.views.activity.ViewPage2TabActivity

/**
 * 测试用的静态方法
 */
object MyMethod {

    // Views 列表数据
    fun getViewsDescribe(): MutableList<PageDescribe> {
        val datas = mutableListOf<PageDescribe>()

        datas.add(PageDescribe("BTitleBar", "自定义操作栏", null))
        datas.add(
            PageDescribe(
                "TabPage2Activity", "导航栏-Fragment沉浸式", ViewPage2TabActivity::class.java
            )
        )
        datas.add(
            PageDescribe(
                "AnimatedTextView", "带边框动画效果的 TextView", AnimatedTextViewActivity::class.java
            )
        )
        datas.add(
            PageDescribe("TriangleView", "三角形气泡", TriangleViewActivity::class.java)
        )
        datas.add(
            PageDescribe("MotionLayout", "动画Demo", MotionLayoutActivity::class.java)
        )
        return datas
    }

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

    // Function 列表数据
    fun getFunctionDescribe(): MutableList<PageDescribe> {
        val datas = mutableListOf<PageDescribe>()

        datas.add(
            PageDescribe("Demo1", "MVP", Demo1Activity::class.java)
        )
        datas.add(PageDescribe("Demo3", "MVVM", null))
        datas.add(PageDescribe("Demo5", "MVI", Demo5Activity::class.java))
        datas.add(PageDescribe("协程", "协程 相关操作", null))
        datas.add(PageDescribe("异步流", "异步流 相关操作", null))

        return datas
    }

    // 根据传入的数量，返回数据量
    fun getStrs(count: Int = 10): MutableList<String> {
        val strs = mutableListOf<String>()
        for (index in 1..count) {
            strs.add("$index")
        }
        return strs
    }

}