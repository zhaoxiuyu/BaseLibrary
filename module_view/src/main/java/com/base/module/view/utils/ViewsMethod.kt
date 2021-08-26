package com.base.module.view.utils

import com.base.module.view.entity.ViewsDescribe
import com.base.module.view.ui.AnimatedTextViewActivity
import com.base.module.view.ui.MotionLayoutActivity
import com.base.module.view.ui.TabPage2Activity
import com.base.module.view.ui.TriangleViewActivity

object ViewsMethod {

    fun getViewsDescribe(): MutableList<ViewsDescribe> {
        val datas = mutableListOf<ViewsDescribe>()

        datas.add(ViewsDescribe("DynamicHeightImageView", "可设置宽高比的图片", null))
        datas.add(ViewsDescribe("BTitleBar", "自定义操作栏", null))
        datas.add(
            ViewsDescribe("TabPage2Activity", "导航栏-Fragment沉浸式", TabPage2Activity::class.java)
        )
        datas.add(
            ViewsDescribe(
                "AnimatedTextView", "带边框动画效果的 TextView", AnimatedTextViewActivity::class.java
            )
        )
        datas.add(ViewsDescribe("TriangleView", "三角形气泡", TriangleViewActivity::class.java))
        datas.add(ViewsDescribe("MotionLayout", "动画Demo", MotionLayoutActivity::class.java))
        return datas
    }

    fun getStrs(count: Int = 10): MutableList<String> {
        val strs = mutableListOf<String>()
        for (index in 1..count) {
            strs.add("$index")
        }
        return strs
    }

}