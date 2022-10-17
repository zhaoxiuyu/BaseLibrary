package com.base.common.sample.mvi.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.text.buildSpannedString
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.common.constant.CommonARoute
import com.base.common.databinding.CommonSampleActivityBinding
import com.base.common.sample.mvi.event.Demo4Event
import com.base.common.sample.mvi.viewmodel.Demo4ViewModel
import com.base.library.mvvm.VMActivity
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.launch

@Route(path = CommonARoute.Demo4Activity)
class Demo4Activity : VMActivity<CommonSampleActivityBinding>() {

    private val viewModel: Demo4ViewModel by viewModels()

    override fun initArgs(mIntent: Intent?) {

        // 匿名函数
        val strLeng = stringLengthFunc("Android")

        // 函数作为参数
        stringMapper("Android", stringLengthFunc)
        stringMapper("Android", ::stringLengthInner2)
        stringMapper("Android") { input ->
            input.length
        }
    }

    private fun stringLengthInner2(input: String) = input.length

    val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    fun stringMapper(input: String, mapper: (String) -> Int): Int {
        return mapper(input)
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
        viewBinding.article.setOnClickListener {
            viewModel.input(Demo4Event.WanArticleM())
        }
        viewBinding.chapters.setOnClickListener {
            viewModel.input(Demo4Event.WanChaptersM())
        }
        viewBinding.banner.setOnClickListener {
            viewModel.input(Demo4Event.WanAndroidM())
        }
        viewBinding.login.setOnClickListener {
            val map = mapOf(
                "username" to viewBinding.userName.text.toString(),
                "password" to viewBinding.passWord.text.toString()
            )
            viewModel.input(Demo4Event.WanLoginM().setMap(map))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val build = buildSpannedString {
            //操作各种Span
        }
    }

    override fun registerObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }

        viewModel.output(this) { event ->
            when (event) {
                is Demo4Event.WanLoginM -> LogUtils.d(event.wanLogin.toString())
                is Demo4Event.WanArticleM -> {
                    val wanArticleSize = event.mTriple?.first?.size
                    val wanChaptersSize = event.mTriple?.second?.size
                    val wanAndroidSize = event.mTriple?.third?.size
                    LogUtils.d("$wanArticleSize ; $wanChaptersSize ; $wanAndroidSize")
                }
                is Demo4Event.WanChaptersM -> LogUtils.d(event.wanChapters?.size)
                is Demo4Event.WanAndroidM -> LogUtils.d(event.wanAndroid?.size)
                is Demo4Event.ShowLoading -> showLoading(event.msg)
                is Demo4Event.DismissLoading -> dismissLoading()
            }
        }
    }

}