package com.base.module.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.library.base.BActivity
import com.base.module.common.module_views.ViewsARoute
import com.base.module.view.databinding.ActivityViewMainBinding
import com.blankj.utilcode.util.FragmentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewMainActivity : BActivity() {

    private val mBind by lazy { ActivityViewMainBinding.inflate(layoutInflater) }

    private val mViewsFragment by lazy {
        ARouter.getInstance().build(ViewsARoute.View_ViewsFragment).navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        FragmentUtils.add(supportFragmentManager, mViewsFragment, R.id.flContent)
    }

    override fun registerObserve() {
    }

}