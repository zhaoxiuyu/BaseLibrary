package com.base.app.sample.views.activity

import android.content.Intent
import android.os.Bundle
import com.base.app.databinding.ActivityMotionDemoBinding
import com.base.library.mvvm.VMActivity

/**
 * MotionLayout 动画
 * 参考链接如下：https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 */
class MotionLayoutActivity : VMActivity<ActivityMotionDemoBinding>() {

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(viewBinding.root)
        viewBinding.motionLayout.startState

    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}