package com.base.library.mvvm.template.ui

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.R
import com.base.library.mvvm.core.VMActivity
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.base_activity_test.*

class Demo3Activity : VMActivity() {

    private val vmDemo3 by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }

    override fun initArgs(intent: Intent?) = vmDemo3

    override fun initView() {
        super.initView()
        initContentView(R.layout.base_activity_test)
    }

    override fun initData() {
        article.setOnClickListener { vmDemo3.getArticle() }
        chapters.setOnClickListener { vmDemo3.getChapters() }
        login.setOnClickListener {
            val map = mapOf(
                "username" to userName.text.toString(),
                "password" to passWord.text.toString()
            )
            vmDemo3.getLogin(map)
        }

        vmDemo3.article.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        vmDemo3.chapters.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        vmDemo3.login.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
    }

}