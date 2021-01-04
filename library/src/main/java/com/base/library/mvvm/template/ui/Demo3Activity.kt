package com.base.library.mvvm.template.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.base.BActivity
import com.base.library.databinding.BaseActivityTestBinding
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.functions.Consumer

class Demo3Activity : BActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }
    private val mBind by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentViewBar(mBind.root)
        setGloading(mBind.root)

        getGloadingHolder()?.showLoading()

        Handler().postDelayed({
            getGloadingHolder()?.showLoadSuccess()
        }, 2000)

    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitleBarOperation("MVVM 测试网络请求")

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
        mBind.parallel.setOnClickListener {
            mViewModel.getParallel()
        }
        mBind.putCache.setOnClickListener {
            mViewModel.putCache("123", "456")
        }
        mBind.getCache.setOnClickListener {
            mViewModel.getCache("123")
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
        mViewModel.cacheLiveData.observe(this, Observer {
            LogUtils.d(it)
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