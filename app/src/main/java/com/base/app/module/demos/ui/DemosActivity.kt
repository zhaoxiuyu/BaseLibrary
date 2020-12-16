package com.base.app.module.demos.ui

import android.content.Intent
import androidx.fragment.app.Fragment
import com.base.app.R
import com.base.app.databinding.ActivityDemosBinding
import com.base.library.mvvm.core.VMActivity
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils

/**
 * 功能展示
 */
class DemosActivity : VMActivity() {

    private val mBind by lazy { ActivityDemosBinding.inflate(layoutInflater) }

    private val mUtilsFragment by lazy { UtilsFragment.newInstance("", "") }
    private val mViewsFragment by lazy { ViewsFragment.newInstance("", "") }
    private val mFunctionFragment by lazy { FunctionFragment.newInstance("", "") }

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        setContentView(mBind.root)

        val sb = StringBuilder()
        for (i in 1..10) {
            val startTime1 = System.currentTimeMillis() //获取开始时间
            ActivityDemosBinding.inflate(layoutInflater)
            val endTime1 = System.currentTimeMillis() //获取结束时间
            sb.append("ms2 = ${(endTime1 - startTime1)} \n")
        }
        LogUtils.d(sb)
    }

    override fun initData() {
        mBind.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_utils -> {
                    mBind.titleBar.title = "工具类"
                    showFragment(mUtilsFragment, "mUtilsFragment")
                }
                R.id.nav_views -> {
                    mBind.titleBar.title = "控件"
                    showFragment(mViewsFragment, "mViewsFragment")
                }
                R.id.nav_function -> {
                    mBind.titleBar.title = "测试功能"
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