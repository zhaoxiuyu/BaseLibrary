package com.base.module.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.base.library.base.BActivity
import com.base.module.view.R

/**
 * MotionLayout 动画
 * 参考链接如下：https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 */
class MotionLayoutActivity : BActivity() {

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_motion_demo)
        val motionLayout = findViewById<MotionLayout>(R.id.motionLayout)
        motionLayout.startState

    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}