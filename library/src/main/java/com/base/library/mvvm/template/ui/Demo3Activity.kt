package com.base.library.mvvm.template.ui

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.base.BActivity
import com.base.library.databinding.BaseActivityTestBinding
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils

class Demo3Activity : BActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }
    private val mBind by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData() {
        mBind.titleBar.title = "MVVM 测试网络请求"

        mBind.article?.setOnClickListener {
            mViewModel.getArticle()
        }
        mBind.chapters?.setOnClickListener {
            mViewModel.getChapters()
        }
        mBind.login?.setOnClickListener {
            val map = mapOf(
                "username" to mBind?.userName?.text.toString(),
                "password" to mBind?.passWord?.text.toString()
            )
            mViewModel.getLogin(map)
        }
    }

    override fun initObserve(): MutableList<BViewModel> {
        mViewModel.articleLiveData.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        mViewModel.chaptersLiveData.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        mViewModel.loginLiveData.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        return mutableListOf(mViewModel)
    }

}