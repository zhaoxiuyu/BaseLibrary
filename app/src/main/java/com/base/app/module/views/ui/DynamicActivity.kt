package com.base.app.module.views.ui

import android.content.Intent
import android.widget.TextView
import com.base.app.R
import com.base.library.mvvm.core.VMActivity

/**
 * 可设置宽高比的图片
 */
class DynamicActivity : VMActivity() {

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        super.initView()
        setContentViewBar(R.layout.activity_dynamic)
    }

    override fun lazyData() {
        getBar().setTvCenterText("可设置宽高比的图片")

        val tv = TextView(this)

    }

}