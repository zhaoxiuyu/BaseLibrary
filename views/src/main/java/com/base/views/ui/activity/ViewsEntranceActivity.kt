package com.base.views.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.common.constant.CommonARoute
import com.base.library.mvvm.VMActivity
import com.base.views.R
import com.base.views.databinding.ViewsActivityEntranceBinding
import com.blankj.utilcode.util.FragmentUtils

class ViewsEntranceActivity : VMActivity<ViewsActivityEntranceBinding>() {

    private val mViewsFragment by lazy {
        ARouter.getInstance().build(CommonARoute.View_ViewsFragment).navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(viewBinding.root)
        ARouter.getInstance().inject(this)
        FragmentUtils.add(supportFragmentManager, mViewsFragment, R.id.fl)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}