package com.base.library.mvvm.template.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.base.library.base.BFragment
import com.base.library.databinding.BaseActivityTestBinding
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.viewmodel.Demo4ViewModel
import com.blankj.utilcode.util.LogUtils

class Demo4Fragment : BFragment() {

    private val mViewModel: Demo4ViewModel by viewModels()

//    private val mViewModel by lazy { ViewModelProvider(this).get(Demo4ViewModel::class.java) }

    private val mBind by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            LogUtils.d(it.getString("fujia"))
        }
    }

    override fun initView() {
        setContentViewBar(mBind.root)
        getTitleBar().title = "MVVM 协程"
        getTitleBar().setOnTitleBarListener(object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                v?.let {
                    Navigation.findNavController(v).navigateUp()
                }
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        // 获取缓存
        mBind.getCache.setOnClickListener { getCache() }

        // 登录
        mBind.collectLogin.setOnClickListener {
            collectLogin(mBind.userName.text.toString(), mBind.passWord.text.toString())
        }
        // 登录 -> 首页banner
        mBind.collectLoginBanner.setOnClickListener {
            loginBanner(mBind.userName.text.toString(), mBind.passWord.text.toString())
        }
        // 公众号 文章 列表同步获取
        mBind.parallel.setOnClickListener { parallel() }
    }

    override fun initObserve(): MutableList<BViewModel>? {
        return mutableListOf(mViewModel)
    }

    // 获取缓存
    private fun getCache() {
        mViewModel.getCache("123").observe(this, Observer {
            LogUtils.d(it)
        })
    }

    // 登录
    private fun collectLogin(username: String, password: String) {
        mViewModel.collectLogin(username, password).observe(this, Observer {
            mBind.tvInfo.text = "登录状态 ${it.showMsg()}"
        })
    }

    // 登录 -> 首页banner
    private fun loginBanner(username: String, password: String) {
        mViewModel.getLoginBanner(username, password).observe(this, Observer {
            mBind.tvInfo.text = "登录 -> 首页banner 状态 ${it.showMsg()}"
        })
    }

    // 公众号 文章 列表同步获取
    private fun parallel() {
        mViewModel.getParallel().observe(this, Observer {
            LogUtils.d("${it.first.errorCode} = ${it.second.errorCode}")
        })
    }

}