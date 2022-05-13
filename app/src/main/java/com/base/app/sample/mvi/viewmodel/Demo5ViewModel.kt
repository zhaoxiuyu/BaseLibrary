package com.base.app.sample.mvi.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.base.app.sample.mvi.repository.Demo5Repository
import com.base.app.sample.mvi.state.Demo5State
import com.base.library.mvvm.DataStatus
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*

// 负责管理状态 State
class Demo5ViewModel(mDemo5State: Demo5State) : MavericksViewModel<Demo5State>(mDemo5State) {

    private val mRepository by lazy { Demo5Repository() }

    fun incrementCount() = setState {
        copy(count = count + 1)
    }

    // 登录
    fun getLogin(username: String, password: String) = withState {
        suspend {
//            mRepository.getLogin(username, password)
            mRepository.getLoginStr(username, password)
        }.execute(Dispatchers.Main) { state ->
//            copy(loginAsync = state)
            copy(strAsync = state)
        }.invokeOnCompletion { }
    }

    // 首页banner
    fun getBanner() {
        setState { copy(status = DataStatus.LOADING) }
        viewModelScope({
            LogUtils.d("block")

            delay(1000)
            val mBanner = mRepository.getBanner()
            if (mBanner.isSuccess()) {
                setState { copy(status = DataStatus.LOADED, banners = mBanner) }
            } else {
                setState { copy(status = DataStatus.ERROR) }
            }

        }, {
            LogUtils.d("onError ${it.message}")
        }, {
            LogUtils.d("onStart")
        }, {
            LogUtils.d("onFinally")
        })
    }

    // 获取公众号列表
    fun getChapters() = withState {
        setState { copy(status = DataStatus.LOADING) }
        viewModelScope.launch {
            delay(1000)

            val mChapters = mRepository.getChapters()
            if (mChapters.isSuccess()) {
                setState {
                    copy(
                        status = DataStatus.LOADED,
                        chapters = mChapters.data ?: mutableListOf()
                    )
                }
            } else {
                setState { copy(status = DataStatus.ERROR) }
            }
        }
    }

    // 串行获取
    fun getSerial(username: String, password: String) = withState {
        setState { copy(status = DataStatus.LOADING) }
        viewModelScope.launch(Dispatchers.Main) {
            delay(1000)

            // 调用接口的时候 retrofit 会自动切换到子线程
            val mLogin = mRepository.getLogin(username, password)
            LogUtils.d(mLogin.data?.nickname)

            // 登录失败就不往下执行
            if (!mLogin.isSuccess()) {
                setState { copy(status = DataStatus.ERROR, login = mLogin) }
                return@launch
            }

            val mArticle = mRepository.getArticle()

            if (mArticle.isSuccess()) {
                setState {
                    copy(
                        status = DataStatus.LOADED,
                        login = mLogin,
                        article = mArticle,
                    )
                }
            } else {
                setState { copy(status = DataStatus.ERROR) }
            }
        }
    }

    // 并行获取
    fun getParallel() = withState {
        setState { copy(status = DataStatus.LOADING) }
        viewModelScope.launch(Dispatchers.Main) {
            delay(1000)

            val mChaptersAsync = async { mRepository.getChapters() }
            val mArticleAsync = async { mRepository.getArticle() }

            val mChapters = mChaptersAsync.await()
            val mArticle = mArticleAsync.await()

            if (mChapters.isSuccess() && mArticle.isSuccess()) {
                setState {
                    copy(
                        status = DataStatus.LOADED,
                        chapters = mChapters.data ?: mutableListOf(),
                        article = mArticle,
                    )
                }
            } else {
                setState { copy(status = DataStatus.ERROR) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        LogUtils.d("onCleared")
    }

    fun viewModelScope(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onStart: (() -> Unit)? = null,
        onFinally: (() -> Unit)? = null,
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                onStart?.invoke()
                block()
            } catch (e: Exception) {
                onError?.invoke(e)
                e.printStackTrace()
            } finally {
                onFinally?.invoke()
            }
        }
    }

}