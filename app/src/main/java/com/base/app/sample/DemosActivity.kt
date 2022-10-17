package com.base.app.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.base.app.R
import com.base.app.databinding.ActivityDemosBinding
import com.base.common.constant.CommonARoute
import com.base.library.mvvm.VMActivity
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils

/**
 * 功能展示
 */
class DemosActivity : VMActivity<ActivityDemosBinding>() {
// class DemosActivity : VMActivity<DemosViewModel, ActivityDemosBinding>() {

    private val mDemosViewModel: DemosViewModel by viewModels()

    private val mUtilsFragment by lazy {
        ARouter.getInstance().build(CommonARoute.Utils_UtilsFragment).navigation() as Fragment
    }

    private val mViewsFragment by lazy {
        ARouter.getInstance().build(CommonARoute.View_ViewsFragment).navigation() as Fragment
    }

    private val mFunctionFragment by lazy {
        ARouter.getInstance().build(CommonARoute.Function_FunctionFragment)
            .navigation() as Fragment
    }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.bnv.setOnNavigationItemSelectedListener {
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
        viewBinding.bnv.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_menu_utils -> LogUtils.d("重复点击")
                R.id.nav_menu_views -> LogUtils.d("重复点击")
                R.id.nav_menu_function -> LogUtils.d("重复点击")
            }
        }
        showFragment(mUtilsFragment, "mUtilsFragment")
    }

    override fun registerObserve() {
    }

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