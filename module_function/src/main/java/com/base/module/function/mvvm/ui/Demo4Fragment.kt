package com.base.module.function.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvvm.VMFragment
import com.base.module.function.databinding.FragmentDemo4Binding
import com.base.module.function.mvvm.viewmodel.Demo4ViewModel
import com.blankj.utilcode.util.LogUtils

class Demo4Fragment : VMFragment<Demo4ViewModel, FragmentDemo4Binding>() {

//    private val viewModel: Demo4ViewModel by viewModels()

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            LogUtils.d(it.getString("fujia"))
        }
    }

    override fun initView() {
        setContentView(viewBinding.root, topPadding = viewBinding.ll)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.titleBar.title = "协程"
        viewBinding.titleBar.setOnTitleBarListener(object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                findNavController().navigateUp()
            }
        })

        // 添加缓存
        viewBinding.putCache.setOnClickListener {
            viewModel.putCache("123", "我是缓存")
        }
        // 获取缓存
        viewBinding.getCache.setOnClickListener { getCache() }
        // 登录
        viewBinding.collectLogin.setOnClickListener {
            viewModel.collectLogin(
                viewBinding.userName.text.toString(),
                viewBinding.passWord.text.toString()
            )
        }
        // 登录 -> 首页banner
        viewBinding.collectLoginBanner.setOnClickListener {
            loginBanner(viewBinding.userName.text.toString(), viewBinding.passWord.text.toString())
        }
        // 公众号 文章列表同步获取
        viewBinding.parallel.setOnClickListener { viewModel.getParallel() }
    }

    override fun registerObserve() {
        // 公众号 文章 列表同步获取
        viewModel.getParallelLiveData().observe(viewLifecycleOwner) {
            LogUtils.d("${it.first.errorCode} = ${it.second.errorCode}")
        }

        // 登录
        viewModel.getLoginLiveData().observe(viewLifecycleOwner) {
            LogUtils.d("${it.showMsg()}")
            viewBinding.tvInfo.text = "登录状态 ${it.showMsg()}"
        }
    }

    // 获取缓存
    private fun getCache() {
        viewModel.getCache("123").observe(viewLifecycleOwner) {
            LogUtils.d(it)
        }
    }

    // 登录 -> 首页banner
    private fun loginBanner(username: String, password: String) {
        viewModel.getLoginBanner(username, password).observe(viewLifecycleOwner) {
            val info = "${it.first.showMsg()} \n ${it.second.showMsg()}"
            viewBinding.tvInfo.text = "登录 -> 首页banner 状态 $info"
        }
    }

}