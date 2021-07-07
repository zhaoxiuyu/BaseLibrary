package com.base.module.view.ui

import android.content.Intent
import android.os.Bundle
import com.base.library.base.BActivity
import com.base.module.view.databinding.ActivityAnimatedTextviewBinding
import com.dylanc.viewbinding.binding

class AnimatedTextViewActivity : BActivity() {

    private val viewBinding: ActivityAnimatedTextviewBinding by binding()

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        immersionBar()
        addStatusBarTopPadding(viewBinding.root)
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