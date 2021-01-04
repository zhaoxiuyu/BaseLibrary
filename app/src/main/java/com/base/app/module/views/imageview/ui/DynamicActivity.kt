package com.base.app.module.views.imageview.ui

import android.content.Intent
import android.os.Bundle
import com.base.app.databinding.ActivityDynamicBinding
import com.base.library.base.BActivity

/**
 * 可设置宽高比的图片
 */
class DynamicActivity : BActivity() {

    private val mBind by lazy { ActivityDynamicBinding.inflate(layoutInflater) }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentViewBar(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        getTitleBar().title = "可设置宽高比的图片"

    }

    override fun initObserve(): Nothing? = null

}