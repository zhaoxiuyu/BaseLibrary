package com.base.app.sample.views.activity

import android.os.Bundle
import com.base.app.base.MyConstant
import com.base.app.databinding.LayoutMotion1Binding
import com.base.library.mvvm.VMFragment

class MotionLayout1 : VMFragment<LayoutMotion1Binding>() {

    private var param1: Int = 0

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getInt(MyConstant.ARG_PARAM1)
        }
    }

    override fun initView() {
        setContentView(viewBinding.root)
        viewBinding.motionLayout.startState
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

    companion object {
        fun newInstance(param1: Int) =
            MotionLayout1().apply {
                arguments = Bundle().apply {
                    putInt(MyConstant.ARG_PARAM1, param1)
                }
            }
    }

}