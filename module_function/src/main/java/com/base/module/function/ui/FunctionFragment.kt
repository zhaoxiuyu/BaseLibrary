package com.base.module.function.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.library.base.BFragment
import com.base.module.common.module_function.FunctionARoute
import com.base.module.function.databinding.FragmentFunctionBinding

@Route(path = FunctionARoute.Function_FunctionFragment)
class FunctionFragment : BFragment() {

    private val viewBinding by lazy { FragmentFunctionBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}