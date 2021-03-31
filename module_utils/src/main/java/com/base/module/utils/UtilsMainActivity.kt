package com.base.module.utils

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.library.base.BActivity
import com.base.module.common.module_utils.UtilsARoute
import com.base.module.utils.databinding.ActivityUtilsMainBinding
import com.blankj.utilcode.util.FragmentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UtilsMainActivity : BActivity() {

    private val mBind by lazy { ActivityUtilsMainBinding.inflate(layoutInflater) }

    private val mUtilsFragment by lazy {
        ARouter.getInstance().build(UtilsARoute.Utils_UtilsFragment).navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        FragmentUtils.add(supportFragmentManager, mUtilsFragment, R.id.flContent)
    }

    override fun registerObserve() {
    }

}