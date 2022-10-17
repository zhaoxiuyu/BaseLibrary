package com.base.common.sample.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.base.common.sample.mvi.event.Demo4Event
import com.base.common.sample.mvi.repository.Demo5Repository
import com.base.library.util.launchSafety
import com.blankj.utilcode.util.LogUtils
import com.kunminx.architecture.domain.dispatch.MviDispatcherKTX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Demo4ViewModel : MviDispatcherKTX<Demo4Event>() {

    private val mRepository by lazy { Demo5Repository() }

    override suspend fun onHandle(event: Demo4Event) {
        when (event) {
            is Demo4Event.WanLoginM -> getLogin(event.map)
            is Demo4Event.WanArticleM -> getArticle()
            is Demo4Event.WanChaptersM -> getChapters()
            is Demo4Event.WanAndroidM -> getWanAndroid()
            else -> {}
        }
    }

    /**
     * 获取首页文章列表
     */
    private suspend fun getArticle() {
        showLoading()
        viewModelScope.launch(Dispatchers.Main) {
            val mArticle = withContext(Dispatchers.IO) {
                mRepository.getArticle()
            }
            val mChapters = async { mRepository.getChapters() }
            val mBanner = async { mRepository.getBanner() }

            val mTriple = Triple(mArticle.data, mChapters.await().data, mBanner.await().data)
            sendResult(Demo4Event.WanArticleM(mTriple))

            dismissLoading()
        }
    }

    /**
     * 获取公众号列表
     */
    private suspend fun getChapters() {
        showLoading()

        val mChapters = mRepository.getChapters()
        sendResult(Demo4Event.WanChaptersM(mChapters.data))

        dismissLoading()
    }

    /**
     * 登录
     */
    private suspend fun getLogin(map: Map<String, String>) {
        viewModelScope.launchSafety(Dispatchers.Main) {
            val login = mRepository.getLogin(map["username"] ?: "", map["password"] ?: "")
            sendResult(Demo4Event.WanLoginM(login.data))
        }.onCatch {
            LogUtils.d(it.message)
        }
    }

    /**
     * 首页banner
     */
    private suspend fun getWanAndroid() {
        flow {
            val banner = mRepository.getBanner()
            emit(banner)
        }
            .onStart { showLoading() }
//            .flowOn(Dispatchers.IO)
            .onCompletion { dismissLoading() }
            .catch { LogUtils.d(it.message) }
            .collect {
                sendResult(Demo4Event.WanAndroidM(it.data))
            }
    }

    private suspend fun showLoading() {
        sendResult(Demo4Event.ShowLoading())
    }

    private suspend fun dismissLoading() {
        sendResult(Demo4Event.DismissLoading)
    }

}
