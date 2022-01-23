package com.base.module.function.mvvm.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvvm.VMFragment
import com.base.module.function.databinding.FragmentDemo3Binding
import com.base.module.function.mvvm.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils
import com.dylanc.loadingstateview.LoadingStateView

class Demo3Fragment : VMFragment<Demo3ViewModel, FragmentDemo3Binding>() {

    private val loadingHelper by lazy { LoadingStateView(viewBinding.article) }

    override fun initArgs(mArguments: Bundle?) {}

    override fun initView() {
        setContentView(viewBinding.root, topPadding = viewBinding.ll)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.titleBar.title = "Rx"
        viewBinding.titleBar.setOnTitleBarListener(object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
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
            val map = mapOf(
                "username" to viewBinding.userName.text.toString(),
                "password" to viewBinding.passWord.text.toString()
            )
            viewModel.getLogin(map)
        }
        viewBinding.getCache.setOnClickListener {
            viewModel.getCache("123")
        }
        viewBinding.putCache.setOnClickListener {
            viewModel.putCache("123", "Demo3Fragment 缓存")
        }

        loadingHelper.setOnReloadListener {
            loadingHelper.showContentView()
        }
        loadingHelper.showLoadingView()

        Handler(Looper.getMainLooper()).postDelayed({
            loadingHelper.showErrorView()
        }, 3000)

    }

    override fun registerObserve() {
        viewModel.articleLiveData.observe(viewLifecycleOwner) {
            LogUtils.d(it.errorCode)
        }
        viewModel.chaptersLiveData.observe(viewLifecycleOwner) {
            LogUtils.d(it.errorCode)
        }
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            LogUtils.d(it.errorCode)
        }
        viewModel.getCacheLiveData.observe(viewLifecycleOwner) {
            LogUtils.d(it)
        }
        viewModel.putCacheLiveData.observe(viewLifecycleOwner) {
            LogUtils.d(it)
        }
    }

}