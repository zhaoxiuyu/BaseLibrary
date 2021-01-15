package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.base.app.R
import com.base.app.databinding.ActivityDemosBinding
import com.base.library.base.BActivity

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
        setContentView(mBind.root)
//        val binding: ActivityDemosBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_demos)

    }

    override fun initData(savedInstanceState: Bundle?) {
        val mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)

        mBind.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_utils -> {
                    mNavController.navigate(R.id.nav_utils)
//                    showFragment(mUtilsFragment, "mUtilsFragment")
                }
                R.id.nav_views -> {
                    // bundleOf 传参
                    mNavController.navigate(R.id.nav_views, bundleOf("topBarTitle" to "控件类列表"))
//                    showFragment(mViewsFragment, "mViewsFragment")
                }
                R.id.nav_function -> {
                    // bundleOf 会覆盖 nav_graph 中 argument 的默认参数
                    mNavController.navigate(R.id.nav_function, bundleOf("topBarTitle" to "功能列表"))
//                    showFragment(mFunctionFragment, "mFunctionFragment")
                }
            }
            true
        }
//        mBind.bnv.setOnNavigationItemReselectedListener {
//            when (it.itemId) {
//                R.id.nav_utils -> LogUtils.d("重复点击")
//                R.id.nav_views -> LogUtils.d("重复点击")
//                R.id.nav_function -> LogUtils.d("重复点击")
//            }
//        }
//
//        showFragment(mUtilsFragment, "mUtilsFragment")
    }

    override fun initObserve(): Nothing? = null

//    private fun showFragment(fragment: Fragment, tag: String) {
//        val findFragment = FragmentUtils.findFragment(supportFragmentManager, tag)
//        FragmentUtils.hide(supportFragmentManager)
//        if (findFragment != null) {
//            FragmentUtils.show(findFragment)
//        } else {
//            FragmentUtils.add(supportFragmentManager, fragment, R.id.frameLayout, tag)
//        }
//    }

//    @BindingAdapter("onNavigationItemSelected")
//    fun setOnNavigationItemSelectedListener(
//        view: BottomNavigationView,
//        listener: BottomNavigationView.OnNavigationItemSelectedListener
//    ) {
//        view.setOnNavigationItemSelectedListener(listener)
//    }

//    class MyViewModel : BottomNavigationView.OnNavigationItemSelectedListener {
//        override fun onNavigationItemSelected(item: MenuItem): Boolean {
//
//            return true
//        }
//    }

}