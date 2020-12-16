package com.base.library.mvvm.template.ui

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.databinding.BaseActivityTestBinding
import com.base.library.mvvm.core.VMActivity
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils

class Demo3Activity : VMActivity() {

    private val vmDemo3 by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }

    private val mBind by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(intent: Intent?) = vmDemo3

    override fun initView() {
        super.initView()
        setContentView(mBind.root)
    }

    override fun initData() {
        mBind.titleBar.title = "MVVM 测试网络请求"

        mBind?.article?.setOnClickListener { vmDemo3.getArticle() }
        mBind?.chapters?.setOnClickListener { vmDemo3.getChapters() }
        mBind?.login?.setOnClickListener {
            val map = mapOf(
                "username" to mBind?.userName?.text.toString(),
                "password" to mBind?.passWord?.text.toString()
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