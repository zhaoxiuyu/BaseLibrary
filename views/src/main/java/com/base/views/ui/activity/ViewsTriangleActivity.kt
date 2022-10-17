package com.base.views.ui.activity

import android.content.Intent
import android.os.Bundle
import com.base.library.mvvm.VMActivity
import com.base.views.databinding.ViewsActivityTriangleBinding

// 三角形
class ViewsTriangleActivity : VMActivity<ViewsActivityTriangleBinding>() {

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}