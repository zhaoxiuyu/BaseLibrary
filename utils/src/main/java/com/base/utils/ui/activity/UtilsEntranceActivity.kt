package com.base.utils.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.common.constant.CommonARoute
import com.base.library.mvvm.VMActivity
import com.base.utils.R
import com.base.utils.databinding.UtilsEntranceActivityBinding
import com.blankj.utilcode.util.FragmentUtils

class UtilsEntranceActivity : VMActivity<UtilsEntranceActivityBinding>() {

    private val mUtilsFragment by lazy {
        ARouter.getInstance().build(CommonARoute.Utils_UtilsFragment).navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(viewBinding.root)
        ARouter.getInstance().inject(this)
        FragmentUtils.add(supportFragmentManager, mUtilsFragment, R.id.fl)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}