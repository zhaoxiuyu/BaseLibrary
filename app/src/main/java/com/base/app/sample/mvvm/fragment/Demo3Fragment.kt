package com.base.app.sample.mvvm.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.base.app.databinding.FragmentDemo3Binding
import com.base.app.sample.mvvm.viewmodel.Demo3ViewModel
import com.base.library.mvvm.UiChangeState
import com.base.library.mvvm.VMFragment
import com.base.library.view.loadingstateview.LoadingViewDelegate
import com.blankj.utilcode.util.LogUtils
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.loadingstateview.ViewType
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import kotlinx.coroutines.launch

class Demo3Fragment : VMFragment<FragmentDemo3Binding>(), OnReloadListener {

    private val viewModel: Demo3ViewModel by viewModels()

    // 显示加载中、加载失败等缺省页
    private val loadingHelper by lazy { LoadingStateView(viewBinding.article, this) }

    override fun initArgs(mArguments: Bundle?) {}

    override fun initView() {
        setContentView(viewBinding.root, topPadding = viewBinding.ll)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.titleBar.title = "Rx"
        viewBinding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                findNavController().navigateUp()
            }
        })
        viewBinding.article.setOnClickListener {
            viewModel.getArticle()
        }
        viewBinding.chapters.setOnClickListener {
            viewModel.getChapters()
        }
        viewBinding.login.setOnClickListener {
            getLogin()
        }
        viewBinding.getCache.setOnClickListener {
            getCache()
        }
        viewBinding.putCache.setOnClickListener {
            viewModel.putCache("123", "Demo3Fragment 缓存")
        }

        loadingHelper.showLoadingView()
        loadingHelper.updateViewDelegate<LoadingViewDelegate>(ViewType.LOADING) {
            // 更新视图样式
            this.updateMessage()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            loadingHelper.showErrorView()
        }, 3000)

    }

    // 重新请求数据
    override fun onReload() {
//        super.onReload()
        loadingHelper.showContentView()
    }

    override fun registerObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }
        viewModel.uiChangeState.observe(viewLifecycleOwner) {
            when (it) {
                is UiChangeState.Loading -> showLoading()
                is UiChangeState.Success -> dismissLoading()
                is UiChangeState.Fail -> showDialog(it.msg)
                else -> {}
            }
        }
        viewModel.article.observe(viewLifecycleOwner) {
            LogUtils.d("article")
        }
        viewModel.chaptersLiveData.observe(viewLifecycleOwner) {
            LogUtils.d(it.errorCode)
        }
    }

    private fun getLogin() {
        val map = mapOf(
            "username" to viewBinding.userName.text.toString(),
            "password" to viewBinding.passWord.text.toString()
        )
        viewModel.getLogin(map).observe(viewLifecycleOwner) {
            LogUtils.d(it.errorCode)
        }
    }

    private fun getCache() {
        viewModel.getCache("123").observe(viewLifecycleOwner) {
            LogUtils.d(it)
        }
    }

}