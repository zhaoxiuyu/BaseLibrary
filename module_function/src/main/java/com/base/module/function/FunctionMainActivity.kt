package com.base.module.function

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.library.base.BActivity
import com.base.module.common.module_function.FunctionARoute
import com.base.module.function.databinding.ActivityFunctionMainBinding
import com.blankj.utilcode.util.FragmentUtils

class FunctionMainActivity : BActivity() {

    private val mBind by lazy { ActivityFunctionMainBinding.inflate(layoutInflater) }

    private val mFunctionFragment by lazy {
        ARouter.getInstance().build(FunctionARoute.Function_FunctionFragment)
            .navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        FragmentUtils.add(supportFragmentManager, mFunctionFragment, R.id.flContent)
    }

    override fun registerObserve() {
    }

}