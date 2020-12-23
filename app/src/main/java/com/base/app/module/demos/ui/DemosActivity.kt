package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.base.app.R
import com.base.app.databinding.ActivityDemosBinding
import com.base.library.base.BActivity
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils

/**
 * 功能展示
 */
class DemosActivity : BActivity() {

    private val mBind by lazy { ActivityDemosBinding.inflate(layoutInflater) }

    private val mUtilsFragment by lazy { UtilsFragment.newInstance("", "") }
    private val mViewsFragment by lazy { ViewsFragment.newInstance("", "") }
    private val mFunctionFragment by lazy { FunctionFragment.newInstance("", "") }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentViewBar(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_utils -> {
                    getTitleBar().title = "工具类"
                    showFragment(mUtilsFragment, "mUtilsFragment")
                }
                R.id.nav_views -> {
                    getTitleBar().title = "控件"
                    showFragment(mViewsFragment, "mViewsFragment")
                }
                R.id.nav_function -> {
                    getTitleBar().title = "测试功能"
                    showFragment(mFunctionFragment, "mFunctionFragment")
                }
            }
            true
        }
        mBind.bnv.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.nav_utils -> LogUtils.d("重复点击")
                R.id.nav_views -> LogUtils.d("重复点击")
                R.id.nav_function -> LogUtils.d("重复点击")
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