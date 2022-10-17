package com.base.views.constant

import com.base.common.entitys.PageDescribe
import com.base.views.ui.activity.*

object ViewsMethod {

    // Views 列表数据
    fun getViewsDescribe(): MutableList<PageDescribe> {
        val datas = mutableListOf<PageDescribe>()

        datas.add(PageDescribe("BTitleBar", "自定义操作栏", null))
        datas.add(
            PageDescribe(
                "TabPage2Activity", "导航栏-Fragment沉浸式", ViewsVP2TabActivity::class.java
            )
        )
        datas.add(
            PageDescribe(
                "AnimatedTextView", "带边框动画效果的 TextView", ViewsAnimatedTvActivity::class.java
            )
        )
        datas.add(
            PageDescribe("TriangleView", "三角形气泡", ViewsTriangleActivity::class.java)
        )
        datas.add(
            PageDescribe("MotionLayout", "动画Demo", ViewsMotionActivity::class.java)
        )
        datas.add(
            PageDescribe("ConstraintLayout", "约束布局", ViewsConstraintActivity::class.java)
        )
        return datas
    }

}