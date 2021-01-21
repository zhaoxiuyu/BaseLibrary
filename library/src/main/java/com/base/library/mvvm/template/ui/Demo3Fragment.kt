package com.base.library.mvvm.template.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.base.library.base.BFragment
import com.base.library.databinding.BaseActivityTestBinding
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer

class Demo3Fragment : BFragment() {

    private val mViewModel: Demo3ViewModel by viewModels()

    private val mBind by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {}

    override fun initView() {
        setContentViewBar(mBind.root)

        setGloading(mBind.root)
        getGloadingHolder()?.showLoading()
        Handler().postDelayed({
            getGloadingHolder()?.showLoadSuccess()
        }, 2000)

    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitleBarOperation("MVVM 测试网络请求", object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                findNavController().navigateUp()
            }
        })

        mBind.article.setOnClickListener {
            mViewModel.getArticle()
        }
        mBind.chapters.setOnClickListener {
            mViewModel.getChapters()
        }
        mBind.login.setOnClickListener {
            val map = mapOf(
                "username" to mBind.userName.text.toString(),
                "password" to mBind.passWord.text.toString()
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