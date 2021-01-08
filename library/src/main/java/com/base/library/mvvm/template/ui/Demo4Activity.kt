package com.base.library.mvvm.template.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.base.BActivity
import com.base.library.databinding.BaseActivityTestBinding
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.viewmodel.Demo4ViewModel
import com.blankj.utilcode.util.LogUtils

class Demo4Activity : BActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(Demo4ViewModel::class.java) }

    private val mBind by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(mBind.root)
        setTitleBarOperation("MVVM 协程")
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.collectArticle.setOnClickListener { getArticle() }
        mBind.getCache.setOnClickListener { getCache() }
        mBind.parallel.setOnClickListener { getChapters() }
        mBind.collectLogin.setOnClickListener { getChapters() }
        mBind.loginChaptersInfo.setOnClickListener {
            getLoginChaptersInfo(mBind.userName.text.toString(), mBind.passWord.text.toString())
        }
    }

    override fun initObserve(): MutableList<BViewModel>? {
        return null
    }

    // 获取缓存
    private fun getCache() {
        mViewModel.getCache("123").observe(this, Observer {
            LogUtils.d(it)
        })
    }

    // 获取首页文章列表
    private fun getArticle() {
//        mViewModel.getArticle().observe(this, Observer {
//        })
    }

    // 公众号列表和文章列表一起同步获取
    private fun getChapters() {
        mViewModel.getChapters().observe(this, Observer {
        })
    }

    // 登录
    private fun collectLogin(username: String, password: String) {
        mViewModel.collectLogin(username, password).observe(this, Observer {
        })
    }

    private fun getLoginChaptersInfo(username: String, password: String) {
//        mViewModel.getLoginChaptersInfo(username, password).observe(this, Observer {
//        })
    }

}