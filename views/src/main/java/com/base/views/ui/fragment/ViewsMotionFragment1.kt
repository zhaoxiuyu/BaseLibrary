package com.base.views.ui.fragment

import android.os.Bundle
import com.base.common.constant.CommonConstant
import com.base.library.mvvm.VMFragment
import com.base.views.databinding.ViewsFragmentMotion1Binding

class ViewsMotionFragment1 : VMFragment<ViewsFragmentMotion1Binding>() {

    private var param1: Int = 0

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getInt(CommonConstant.ARG_PARAM1)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.motionLayout.startState
    }

    override fun registerObserve() {
    }

    companion object {
        fun newInstance(param1: Int) =
            ViewsMotionFragment1().apply {
                arguments = Bundle().apply {
                    putInt(CommonConstant.ARG_PARAM1, param1)
                }
            }
    }

}