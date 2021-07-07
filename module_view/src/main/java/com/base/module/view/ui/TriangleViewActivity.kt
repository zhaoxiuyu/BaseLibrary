package com.base.module.view.ui

import android.content.Intent
import android.os.Bundle
import com.base.library.base.BActivity
import com.base.module.view.databinding.ActivityTriangleViewBinding
import com.dylanc.viewbinding.binding

class TriangleViewActivity : BActivity() {

    private val viewBinding: ActivityTriangleViewBinding by binding()

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        immersionBar()
        addStatusBarTopPadding(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}