package com.base.function.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.common.constant.CommonARoute
import com.base.function.R
import com.base.function.databinding.FunctionEntranceActivityBinding
import com.base.library.mvvm.VMActivity
import com.blankj.utilcode.util.FragmentUtils

class FunctionEntranceActivity : VMActivity<FunctionEntranceActivityBinding>() {

    private val mFunctionFragment by lazy {
        ARouter.getInstance().build(CommonARoute.Function_FunctionFragment)
            .navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(viewBinding.root)
        ARouter.getInstance().inject(this)
        FragmentUtils.add(supportFragmentManager, mFunctionFragment, R.id.fl)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}