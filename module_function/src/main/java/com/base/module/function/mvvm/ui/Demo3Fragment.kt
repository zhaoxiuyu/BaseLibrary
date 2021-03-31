package com.base.module.function.mvvm.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvvm.core.VMFragment
import com.base.library.rxhttp.RxRequest
import com.base.module.function.databinding.BaseActivityTestBinding
import com.base.module.function.mvvm.viewmodel.Demo3ViewModel
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer

@AndroidEntryPoint
class Demo3Fragment : VMFragment<Demo3ViewModel, BaseActivityTestBinding>() {

//    private val viewModel: Demo3ViewModel by viewModels()
//    private val viewBinding by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {}

    override fun initView() {
        setContentViewBar(viewBinding.root)

        setGloading(viewBinding.root)
        getGloadingHolder()?.showLoading()
        Handler().postDelayed({
            getGloadingHolder()?.showLoadSuccess()
        }, 2000)
        setTitleBarOperation("MVVM 测试网络请求", object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                findNavController().navigateUp()
            }
        })
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
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
        viewModel.articleLiveData.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        viewModel.chaptersLiveData.observe(this, Observer {
            LogUtils.d(it.errorCode)
        })
        viewModel.loginLiveData.observe(this, Observer {
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