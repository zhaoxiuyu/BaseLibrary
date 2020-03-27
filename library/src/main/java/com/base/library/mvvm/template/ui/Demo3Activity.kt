package com.base.library.mvvm.template.ui

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.R
import com.base.library.base.VMActivity
import com.base.library.entitys.Banner
import com.base.library.entitys.Chapters
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.base_activity_test.*

class Demo3Activity : VMActivity<Demo3ViewModel>() {

//    private val vm by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.base_activity_test)
    }

    override fun initData() {
        start.setOnClickListener {
            vm?.getChapters()?.observe(this, Observer { it1 ->
                // 重写回调(非必需)，根据不同的状态进行处理,下面这个对成功提示框单独做定制修改
                it1.handler(object : OnCallback<List<Chapters>>() {
                    override fun onSuccess(msg: String, data: List<Chapters>?, isFinish: Boolean) {
                        showDialog()
                    }
                })

                LogUtils.d("公众号列表 打印出来")
                it1.data?.forEach {
                    LogUtils.d(it.name)
                }
            })
        }
        qt.setOnClickListener {
            vm?.getBanner()?.observe(this, Observer { it1 ->
                it1.handler(object : OnCallback<List<Banner>>() {})

                LogUtils.d("首页banner 打印出来")
                it1.data?.forEach {
                    LogUtils.d(it.title)
                }
            })
        }
    }

}