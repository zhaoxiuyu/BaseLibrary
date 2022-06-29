package com.base.app.sample.views.activity

import android.os.Bundle
import com.base.app.base.MyConstant
import com.base.app.databinding.LayoutMotion2Binding
import com.base.library.mvvm.VMFragment

class MotionLayout2 : VMFragment<LayoutMotion2Binding>() {

    private var param1: Int = 0

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getInt(MyConstant.ARG_PARAM1)
        }
    }

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

    companion object {
        fun newInstance(param1: Int) =
            MotionLayout2().apply {
                arguments = Bundle().apply {
                    putInt(MyConstant.ARG_PARAM1, param1)
                }
            }
    }

}