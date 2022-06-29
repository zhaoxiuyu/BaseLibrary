package com.base.app.sample.function

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.app.base.MyARoute
import com.base.app.databinding.FragmentFunctionBinding
import com.base.library.mvvm.VMFragment

@Route(path = MyARoute.Function_FunctionFragment)
class FunctionFragment : VMFragment<FragmentFunctionBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}