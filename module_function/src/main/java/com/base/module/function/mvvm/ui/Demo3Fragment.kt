package com.base.module.function.mvvm.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.base.library.mvvm.core.VMFragment
import com.base.library.rxhttp.RxRequest
import com.base.module.function.databinding.BaseActivityTestBinding
import com.base.module.function.mvvm.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils
import com.dylanc.loadinghelper.LoadingHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer

class Demo3Fragment : VMFragment<Demo3ViewModel, BaseActivityTestBinding>() {

    private val loadingHelper by lazy { LoadingHelper(viewBinding.article) }

    override fun initArgs(mArguments: Bundle?) {}

    override fun initView() {
        setContentView(viewBinding.root, topPadding = viewBinding.ll)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadingHelper.setOnReloadListener {
            loadingHelper.showContentView()
        }
        loadingHelper.showLoadingView()

        Handler(Looper.getMainLooper()).postDelayed({
            loadingHelper.showErrorView()
        }, 3000)

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
    }

    override fun registerObserve() {
        viewModel.articleLiveData.observe(this, {
            LogUtils.d(it.errorCode)
        })
        viewModel.chaptersLiveData.observe(this, {
            LogUtils.d(it.errorCode)
        })
        viewModel.loginLiveData.observe(this, {
            LogUtils.d(it.errorCode)
        })
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    private fun <T> transformer(bRequest: RxRequest): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doFinally { }
        }
    }

}