package com.base.app.sample.mvi.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.base.app.databinding.ActivityDemo1Binding
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
            LogUtils.d("viewBinding.article.setOnClickListener")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
        /**
         * 开启协程
         */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }

        //封装后的代码
//        viewModel.container.run {
//            uiStateFlow.collectState(this@Demo4Activity) {
//                collectPartial(Demo4ViewModel.FriendUiState::friendBeanList) {
//                    //do someThing
//                }
//                collectPartial(Demo4ViewModel.FriendUiState::refreshing) { refreshing ->
//                    //do someThing
//                }
//                collectPartial(Demo4ViewModel.FriendUiState::loadMore) { loadMore ->
//                    //do someThing
//                }
//                collectPartial(Demo4ViewModel.FriendUiState::noMoreData) { noMoreData ->
//                    //do someThing
//                }
//            }
//        }

//        viewModel.container.collectSingleEvent(this@Demo4Activity) { event ->
//            when (event) {
//                is Demo4ViewModel.SearchSingleEvent.ToastEventSearch -> {
//                    toast(event.message)
//                }
//            }
//        }
    }

}