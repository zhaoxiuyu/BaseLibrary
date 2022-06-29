package com.base.app.sample.views.activity

import android.content.Intent
import android.os.Bundle
import com.base.app.databinding.ActivityAnimatedTextviewBinding
import com.base.library.mvvm.VMActivity

// 带边框动画效果的 TextView
class AnimatedTextViewActivity : VMActivity<ActivityAnimatedTextviewBinding>() {

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
        viewBinding.mAnimatedTextView.setOnClickListener {
            viewBinding.mAnimatedTextView.startAnimation()
        }
        viewBinding.butStop.setOnClickListener {
            viewBinding.mAnimatedTextView.stopAnimation()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}