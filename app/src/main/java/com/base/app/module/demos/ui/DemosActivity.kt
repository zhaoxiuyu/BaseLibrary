package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.app.R
import com.base.app.databinding.ActivityDemosBinding
import com.base.library.base.BActivity
import com.base.module.common.module_function.FunctionARoute
import com.base.module.common.module_utils.UtilsARoute
import com.base.module.common.module_views.ViewsARoute
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * 功能展示
 */
@AndroidEntryPoint
class DemosActivity : BActivity() {

    private val mBind by lazy { ActivityDemosBinding.inflate(layoutInflater) }

    private val mUtilsFragment by lazy {
        ARouter.getInstance().build(UtilsARoute.Utils_UtilsFragment).navigation() as Fragment
    }

    private val mViewsFragment by lazy {
        ARouter.getInstance().build(ViewsARoute.View_ViewsFragment).navigation() as Fragment
    }

    private val mFunctionFragment by lazy {
        ARouter.getInstance().build(FunctionARoute.Function_FunctionFragment)
            .navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_menu_utils -> {
                    showFragment(mUtilsFragment, "mUtilsFragment")
                }
                R.id.nav_menu_views -> {
                    showFragment(mViewsFragment, "mViewsFragment")
                }
                R.id.nav_menu_function -> {
                    showFragment(mFunctionFragment, "mFunctionFragment")
                }
            }
            true
        }
        mBind.bnv.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_menu_utils -> LogUtils.d("重复点击")
                R.id.nav_menu_views -> LogUtils.d("重复点击")
                R.id.nav_menu_function -> LogUtils.d("重复点击")
            }
        }
        showFragment(mUtilsFragment, "mUtilsFragment")
    }

    override fun initObserve(): Nothing? = null

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = FragmentUtils.findFragment(supportFragmentManager, tag)
        FragmentUtils.hide(supportFragmentManager)
        if (findFragment != null) {
            FragmentUtils.show(findFragment)
        } else {
            FragmentUtils.add(supportFragmentManager, fragment, R.id.frameLayout, tag)
        }
    }

}