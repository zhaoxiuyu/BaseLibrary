package com.base.app.sample.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.base.app.sample.mvi.event.Demo4Event
import com.base.app.sample.mvi.repository.Demo5Repository
import com.base.library.util.launchSafety
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.kunminx.architecture.domain.dispatch.MviDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Demo4ViewModel : MviDispatcher<Demo4Event>() {

    private val mRepository by lazy { Demo5Repository() }

    override fun input(event: Demo4Event?) {
        when (event?.eventId) {
            Demo4Event.EVENT_LOGIN -> getLogin(event, event.param.map)
            Demo4Event.EVENT_CHAPTERS -> getChapters(event)
            Demo4Event.EVENT_ARTICLE -> getArticle(event)
        }
    }

    /**
     * 获取首页文章列表
     */
    private fun getArticle(event: Demo4Event?) {
        viewModelScope.launch(Dispatchers.Main) {
            showLoading()

            val mArticle = withContext(Dispatchers.IO) {
                mRepository.getArticle()
            }
            val mChapters = async { mRepository.getChapters() }
            val mBanner = async { mRepository.getBanner() }

            event?.result?.wanArticle = mArticle.data
            event?.result?.wanChapters = mChapters.await().data
            event?.result?.wanAndroid = mBanner.await().data
            sendResult(event)

            dismissLoading()
        }
    }

    /**
     * 获取公众号列表
     */
    private fun getChapters(event: Demo4Event?) {
        showLoading()
        viewModelScope.launchSafety(Dispatchers.Main) {
            val mChapters = mRepository.getChapters()
            mChapters
        }.onCatch {
            LogUtils.d("onCatch : ${it.message} ${ThreadUtils.isMainThread()}")
        }.onComplete {
            LogUtils.d("onSuccess : ${it?.message} ${ThreadUtils.isMainThread()}")
            event?.result?.wanChapters = it?.data
            sendResult(event)
            dismissLoading()
        }
    }

    /**
     * 登录
     */
    private fun getLogin(event: Demo4Event?, map: Map<String, String>) {
        viewModelScope.launchSafety(Dispatchers.Main) {
            val login = mRepository.getLogin(map["username"] ?: "", map["password"] ?: "")
            login
        }.onComplete {
            event?.result?.wanLogin = it?.data
            sendResult(event)
        }
    }

    private fun showLoading() {
        sendResult(Demo4Event(Demo4Event.EVENT_SHOW_LOADING))
    }

    private fun dismissLoading() {
        sendResult(Demo4Event(Demo4Event.EVENT_DISMISS_LOADING))
    }

}
