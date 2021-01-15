package com.base.app.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.R
import com.base.app.entitys.UtilsDescribe
import com.base.app.module.demos.ui.CoroutinesActivity
import com.base.app.module.demos.ui.FlowActivity
import com.base.app.module.views.imageview.ui.DynamicActivity
import com.base.app.module.views.tablayout.ui.TabLayoutActivity
import com.base.library.mvp.template.ui.Demo1Activity
import com.base.library.mvvm.template.ui.Demo3Activity
import com.base.library.mvvm.template.ui.Demo4Fragment
import com.ckr.decoration.DividerLinearItemDecoration

object MethodDatas {

    fun getStrs(count: Int = 10): MutableList<String> {
        val strs = mutableListOf<String>()
        for (index in 1..count) {
            strs.add("$index")
        }
        return strs
    }

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

    fun getViewsDescribe(): MutableList<UtilsDescribe> {
        val datas = mutableListOf<UtilsDescribe>()

        datas.add(UtilsDescribe("DynamicHeightImageView", "可设置宽高比的图片", DynamicActivity::class.java))
        datas.add(UtilsDescribe("BTitleBar", "自定义操作栏", null))
        datas.add(UtilsDescribe("TabLayout", "导航栏-Fragment沉浸式", TabLayoutActivity::class.java))

        return datas
    }

    fun getFunctionDescribe(): MutableList<UtilsDescribe> {
        val datas = mutableListOf<UtilsDescribe>()
        datas.add(UtilsDescribe("Demo3Activity", "测试 MVVM 网络请求", Demo3Activity::class.java))
        datas.add(UtilsDescribe("Demo4Fragment", "测试 MVVM 协程请求", Demo4Fragment::class.java))
        datas.add(UtilsDescribe("Demo1Activity", "测试 MVP 网络请求", Demo1Activity::class.java))
        datas.add(UtilsDescribe("协程", "练手 协程 相关操作", CoroutinesActivity::class.java))
        datas.add(UtilsDescribe("异步流", "练手 异步流 相关操作", FlowActivity::class.java))
        return datas
    }

    /**
     * 线性
     * 高度0.3
     * 去掉头尾分割线
     */
    fun getDividerLinear(mContext: Context): DividerLinearItemDecoration {
        val builder = DividerLinearItemDecoration.Builder(mContext, LinearLayoutManager.VERTICAL)
        builder.setDivider(R.drawable.bg_divider_demos_recyclerview)
        builder.removeHeaderDivider(true)
        builder.removeFooterDivider(true)
        return builder.build()
    }

}