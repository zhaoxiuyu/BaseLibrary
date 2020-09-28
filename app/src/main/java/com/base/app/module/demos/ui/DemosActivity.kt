package com.base.app.module.demos.ui

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.base.app.R
import com.base.library.mvvm.core.VMActivity
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_demos.*

/**
 * 功能展示
 */
class DemosActivity : VMActivity() {

    private val mUtilsFragment by lazy { UtilsFragment.newInstance("", "") }
    private val mViewsFragment by lazy { ViewsFragment.newInstance("", "") }
    private val mFunctionFragment by lazy { FunctionFragment.newInstance("", "") }

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        setContentViewBar(R.layout.activity_demos)
    }

    override fun lazyData() {
        getBTitleBar()?.getIvLeft()?.visibility = View.GONE

        bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_utils -> {
                    getBTitleBar()?.setTvCenterText("工具类")
                    showFragment(mUtilsFragment, "mUtilsFragment")
                }
                R.id.nav_views -> {
                    getBTitleBar()?.setTvCenterText("控件")
                    showFragment(mViewsFragment, "mViewsFragment")
                }
                R.id.nav_function -> {
                    getBTitleBar()?.setTvCenterText("测试功能")
                    showFragment(mFunctionFragment, "mFunctionFragment")
                }
            }
            true
        }
        bnv.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_utils -> LogUtils.d("重复点击")
                R.id.nav_views -> LogUtils.d("重复点击")
                R.id.nav_function -> LogUtils.d("重复点击")
            }
        }

        showFragment(mUtilsFragment, "mUtilsFragment")
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