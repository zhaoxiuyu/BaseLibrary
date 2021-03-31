package com.base.app.module.common.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.base.app.databinding.ActivityMainBinding
import com.base.app.module.common.viewmodel.MainViewModel
import com.base.library.base.BActivity
import com.base.library.mvvm.core.BViewModel
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 首页
 */
class MainActivity : BActivity() {

    private val mMainViewModel: MainViewModel by viewModels()

    private val mBind by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
//        val mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        mBind.bnv.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_menu_utils -> {
//                    val mNavOptions =
//                        NavOptions.Builder().setPopUpTo(R.id.nav_graph_utils, true).build()
//                    mNavController.navigate(R.id.nav_graph_utils, null, mNavOptions)
//                }
//                R.id.nav_menu_views -> {
//                    val mNavOptions =
//                        NavOptions.Builder().setPopUpTo(R.id.nav_graph_views, true).build()
//                    mNavController.navigate(R.id.nav_graph_views, null, mNavOptions)
//                }
//                R.id.nav_menu_function -> {
//                    val mNavOptions =
//                        NavOptions.Builder().setPopUpTo(R.id.nav_graph_function, true).build()
//                    mNavController.navigate(R.id.nav_graph_function, null, mNavOptions)
//                }
//            }
//            true
//        }
//
//        mBind.bnv.setOnNavigationItemReselectedListener {
//            when (it.itemId) {
//                R.id.nav_utils -> LogUtils.d("重复点击")
//                R.id.nav_views -> LogUtils.d("重复点击")
//                R.id.nav_function -> LogUtils.d("重复点击")
//            }
//        }
    }

    override fun registerObserve() {
    }

    /**
     * 只有首页的三个模块才显示底部导航栏，其他的页面都隐藏掉
     */
    fun getMainBnv(isShow: Boolean = true): BottomNavigationView {
        val visible = if (isShow) View.VISIBLE else View.GONE
        if (visible != mBind.bnv.visibility) mBind.bnv.visibility = visible
        return mBind.bnv
    }

    /**
     * 双击退出首页
     */
    private var mPressedTime: Long = 0L
    fun backMain(msg: String = "") {
        val mNowTime = System.currentTimeMillis()
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            ToastUtils.showShort("$msg 再按一次退出程序")
            mPressedTime = mNowTime
        } else {
            finish()
        }
    }

}