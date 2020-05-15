package com.base.library.mvvm.template.ui

import android.content.Intent
import androidx.lifecycle.Observer
import com.base.library.R
import com.base.library.mvvm.core.VMActivity
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.base_activity_test.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Demo3Activity : VMActivity() {

    private val vm by viewModel<Demo3ViewModel>()

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.base_activity_test)
    }

    override fun initData() {
        start.setOnClickListener { vm?.getChapters() }
        qt.setOnClickListener { vm?.getBanner() }

        vm?.liveChapters?.observe(this, Observer { br ->
            LogUtils.d("公众号列表 打印出来")
            br.data?.forEach {
                LogUtils.d(it.name)
            }
        })
        vm?.liveBanner?.observe(this, Observer { br ->
            LogUtils.d("首页banner 打印出来")
            br.data?.forEach {
                LogUtils.d(it.title)
            }
        })
    }

}