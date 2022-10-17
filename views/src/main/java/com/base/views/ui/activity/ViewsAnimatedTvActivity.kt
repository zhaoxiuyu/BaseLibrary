package com.base.views.ui.activity

import android.content.Intent
import android.os.Bundle
import com.base.library.mvvm.VMActivity
import com.base.views.databinding.ViewsActivityAnimatedTvBinding

// 带边框动画效果的 TextView
class ViewsAnimatedTvActivity : VMActivity<ViewsActivityAnimatedTvBinding>() {

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
