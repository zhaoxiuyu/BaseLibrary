package com.base.function.ui.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.common.constant.CommonARoute
import com.base.function.databinding.FunctionFragmentBinding
import com.base.library.mvvm.VMFragment

@Route(path = CommonARoute.Function_FunctionFragment)
class FunctionFragment : VMFragment<FunctionFragmentBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}