package com.base.app.sample.mvi.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.base.app.databinding.ActivityDemo1Binding
import com.base.app.sample.mvi.event.Demo4Event
import com.base.app.sample.mvi.viewmodel.Demo4ViewModel
import com.base.library.mvvm.VMActivity
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.launch

class Demo4Activity : VMActivity<ActivityDemo1Binding>() {

    private val viewModel: Demo4ViewModel by viewModels()

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
        viewBinding.article.setOnClickListener {
            viewModel.input(Demo4Event(Demo4Event.EVENT_ARTICLE))
        }
        viewBinding.chapters.setOnClickListener {
            viewModel.input(Demo4Event(Demo4Event.EVENT_CHAPTERS))
        }
        viewBinding.login.setOnClickListener {
            val map = mapOf(
                "username" to viewBinding.userName.text.toString(),
                "password" to viewBinding.passWord.text.toString()
            )
            viewModel.input(Demo4Event(Demo4Event.EVENT_LOGIN).setLoginMap(map))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }

        viewModel.output(this) { event ->
            when (event.eventId) {
                Demo4Event.EVENT_LOGIN -> {
                    LogUtils.d(event.result.wanLogin.toString())
                }
                Demo4Event.EVENT_CHAPTERS -> {
                    LogUtils.d(event.result.wanChapters?.size)
                }
                Demo4Event.EVENT_ARTICLE -> {
                    val wanArticleSize = event.result.wanArticle?.size
                    val wanChaptersSize = event.result.wanChapters?.size
                    val wanAndroidSize = event.result.wanAndroid?.size

                    LogUtils.d("$wanArticleSize ; $wanChaptersSize ; $wanAndroidSize")
                }
                Demo4Event.EVENT_SHOW_LOADING -> showLoading(event.result.loadingMsg)
                Demo4Event.EVENT_DISMISS_LOADING -> dismissLoading()
            }
        }
    }

}