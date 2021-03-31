package com.base.module.function.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvvm.core.VMFragment
import com.base.module.function.databinding.BaseActivityTestBinding
import com.base.module.function.mvvm.viewmodel.Demo4ViewModel
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Demo4Fragment : VMFragment<Demo4ViewModel, BaseActivityTestBinding>() {
//class Demo4Fragment : BFragment() {

//    @Inject
//    lateinit var viewModel: Demo4ViewModel
//    private val viewModel: Demo4ViewModel by viewModels()
//    private val viewBinding by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            LogUtils.d(it.getString("fujia"))
        }
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
        setTitleBarOperation("MVVM 协程", object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                findNavController().navigateUp()
            }
        })
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
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
        // 公众号 文章 列表同步获取
        viewBinding.parallel.setOnClickListener { viewModel.getParallel() }
    }

    override fun registerObserve() {
        // 公众号 文章 列表同步获取
        viewModel.getParallelLiveData().observe(this, Observer {
            LogUtils.d("${it.first.errorCode} = ${it.second.errorCode}")
        })

        // 登录
        viewModel.getLoginLiveData().observe(this, Observer {
            LogUtils.d("${it.showMsg()}")
            viewBinding.tvInfo.text = "登录状态 ${it.showMsg()}"
        })
    }

    // 获取缓存
    private fun getCache() {
        viewModel.getCache("123").observe(this, Observer {
            LogUtils.d(it)
        })
    }

    // 登录 -> 首页banner
    private fun loginBanner(username: String, password: String) {
        viewModel.getLoginBanner(username, password).observe(this, Observer {
            viewBinding.tvInfo.text = "登录 -> 首页banner 状态 ${it.showMsg()}"
        })
    }

}