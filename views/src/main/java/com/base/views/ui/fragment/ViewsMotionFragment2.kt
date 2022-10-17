package com.base.views.ui.fragment

import android.os.Bundle
import com.base.common.constant.CommonConstant
import com.base.library.mvvm.VMFragment
import com.base.views.databinding.ViewsFragmentMotion2Binding

class ViewsMotionFragment2 : VMFragment<ViewsFragmentMotion2Binding>() {

    private var param1: Int = 0

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getInt(CommonConstant.ARG_PARAM1)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

    companion object {
        fun newInstance(param1: Int) =
            ViewsMotionFragment2().apply {
                arguments = Bundle().apply {
                    putInt(CommonConstant.ARG_PARAM1, param1)
                }
            }
    }

}