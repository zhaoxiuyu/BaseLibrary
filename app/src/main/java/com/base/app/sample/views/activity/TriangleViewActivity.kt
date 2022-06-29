package com.base.app.sample.views.activity

import android.content.Intent
import android.os.Bundle
import com.base.app.databinding.ActivityTriangleViewBinding
import com.base.library.mvvm.VMActivity

// 三角形
class TriangleViewActivity : VMActivity<ActivityTriangleViewBinding>() {

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