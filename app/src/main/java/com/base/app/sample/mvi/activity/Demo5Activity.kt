package com.base.app.sample.mvi.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import com.base.app.databinding.ActivityDemo5Binding
import com.base.app.sample.mvi.state.Demo5State
import com.base.app.sample.mvi.viewmodel.Demo5ViewModel
import com.base.library.mvvm.DataStatus
import com.base.library.mvvm.VMActivity
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.launch

/**
 * todo Async Loading 状态如何判断显示 加载框
 * todo MavericksState 参数是什么意思
 * todo 需要验证一下错误拦截
 */
class Demo5Activity : VMActivity<ActivityDemo5Binding>(), MavericksView {

    private val mDemo5ViewModel: Demo5ViewModel by viewModel()

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(viewBinding.root, topPadding = viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }

        viewBinding.button1.setOnClickListener {
            mDemo5ViewModel.getLogin("zxy_it@yeah.net", "111111")
        }
        viewBinding.button2.setOnClickListener {
            mDemo5ViewModel.getBanner()
        }
        viewBinding.button3.setOnClickListener {
            mDemo5ViewModel.getChapters()
        }
        viewBinding.button4.setOnClickListener {
            mDemo5ViewModel.getSerial("zxy_it@yeah.net", "111111")
        }
        viewBinding.button5.setOnClickListener {
            mDemo5ViewModel.getParallel()
        }
        viewBinding.button6.setOnClickListener {
            mDemo5ViewModel.incrementCount()
        }
        viewBinding.button8.setOnClickListener { }
        viewBinding.button9.setOnClickListener { }
    }

    override fun registerObserve() {
        // 数据状态
        mDemo5ViewModel.onEach(Demo5State::status) {
            when (it) {
                DataStatus.LOADING -> showLoading()
                else -> dismissLoading()
            }
        }
        // 监听 count 数字
        mDemo5ViewModel.onEach(Demo5State::count) {
            LogUtils.d("onEach count $it")
            viewBinding.msg.text = "$it"
        }
        // 首页banner
        mDemo5ViewModel.onEach(Demo5State::banners) {
            LogUtils.d("onEach banners")
            viewBinding.msg.text = "${it.data.toString()}"
        }
        // 获取公众号列表
        mDemo5ViewModel.onEach(Demo5State::chapters) {
            LogUtils.d("onEach chapters")
            viewBinding.msg.text = "$it"
        }
        // 首页文章列表
        mDemo5ViewModel.onEach(Demo5State::article) {
            LogUtils.d("onEach article")
            viewBinding.msg.text = "$it"
        }
        // 测试数据
        mDemo5ViewModel.onEach(Demo5State::str) {
            LogUtils.d("onEach str")
            viewBinding.msg.text = "$it"
        }
        // 监听异步属性
        mDemo5ViewModel.onAsync(
            Demo5State::loginAsync,
            onSuccess = {
                viewBinding.msg.text = "${it.data?.toString()}"
                LogUtils.d("onAsync loginAsync onSuccess")
            },
            onFail = {
                viewBinding.msg.text = "${it.message}"
                LogUtils.d("onAsync loginAsync onFail")
            })
        // 监听异步属性 测试数据
        mDemo5ViewModel.onAsync(
            Demo5State::strAsync,
            onSuccess = {
                viewBinding.msg.text = "$it"
                LogUtils.d("onAsync strAsync onSuccess")
            },
            onFail = {
                viewBinding.msg.text = "${it.message}"
                LogUtils.d("onAsync strAsync onFail = ${it.message}")
            })
    }

    /**
     * 在 Activity 中 invalidate 是失效的，只有在 Fragment 中才会生效
     * 如果在 Activity 中需要监听属性变化，可以使用 onEach onAsync
     */
    override fun invalidate() = withState(mDemo5ViewModel) {
    }

}