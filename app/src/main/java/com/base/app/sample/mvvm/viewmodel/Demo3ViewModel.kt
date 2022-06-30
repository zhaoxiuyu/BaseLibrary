package com.base.app.sample.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.base.app.entitys.response.WanAndroid
import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.sample.mvi.repository.Demo5Repository
import com.base.library.base.BResponse
import com.base.library.mvvm.BViewModel
import com.base.library.util.launchSafety
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 使用 ViewModelInject ，数据仓库要在构造方法里面初始化
 */
class Demo3ViewModel : BViewModel() {

    /**
     * 发送事件
     */
    private val _events = UnPeekLiveData.Builder<Demo3Event>().create()
    val events: ProtectedUnPeekLiveData<Demo3Event> get() = _events

    /**
     * 存储数据
     * mWanArticle.value = WanArticle()
     * if (mWanArticle.value != null)
     */
    val mWanArticle: MutableLiveData<WanArticle> = MutableLiveData<WanArticle>()

    private val mRepository by lazy { Demo5Repository() }

    /**
     * 获取首页文章列表
     */
    private val _article =
        UnPeekLiveData.Builder<
                Triple<WanArticle?, MutableList<WanChapters>?, MutableList<WanAndroid>?>>().create()
    val article: ProtectedUnPeekLiveData<
            Triple<WanArticle?, MutableList<WanChapters>?, MutableList<WanAndroid>?>>
        get() = _article

    fun getArticle() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(Demo3Event.ShowLoading())

            val mArticle = withContext(Dispatchers.IO) {
                mRepository.getArticle()
            }
            val mChapters = async { mRepository.getChapters() }
            val mBanner = async { mRepository.getBanner() }

            val wanArticle = mArticle.data
            val wanChapters = mChapters.await().data
            val wanAndroid = mBanner.await().data

            val mTriple = Triple(wanArticle, wanChapters, wanAndroid)
            _article.value = mTriple

            sendEvent(Demo3Event.DismissLoading())
        }
    }

    /**
     * 获取公众号列表
     */
    private val _chaptersLiveData =
        UnPeekLiveData.Builder<BResponse<MutableList<WanChapters>>>().create()
    val chaptersLiveData: ProtectedUnPeekLiveData<BResponse<MutableList<WanChapters>>> get() = _chaptersLiveData

    fun getChapters() {
        sendEvent(Demo3Event.ShowLoading())
        viewModelScope.launchSafety(Dispatchers.Main) {
            val mChapters = mRepository.getChapters()
            mChapters
        }.onCatch {
            LogUtils.d("onCatch : ${it.message} ${ThreadUtils.isMainThread()}")
            sendEvent(Demo3Event.ShowToast("获取公众号列表失败 : ${it.message}"))
        }.onComplete {
            LogUtils.d("onSuccess : ${it?.message} ${ThreadUtils.isMainThread()}")
            _chaptersLiveData.value = it
            sendEvent(Demo3Event.DismissLoading())
        }
    }

    /**
     * 登录
     */
    fun getLogin(map: Map<String, String>) = liveData {
        flow {
            val login = mRepository.getLogin(map["username"] ?: "", map["password"] ?: "")
            emit(login)
        }.onStart { sendEvent(Demo3Event.ShowLoading()) }
            .catch { sendEvent(Demo3Event.ShowToast("登录失败 : ${it.message}")) }
            .onCompletion { sendEvent(Demo3Event.DismissLoading()) }
            .collect {
                emit(it)
            }
    }

    /**
     * 获取缓存
     * liveData{ }协程代码块,产生一个不可变LiveData
     * emit()方法用来更新LiveData数据
     * collectLatest末端操作符,一段时间内只接受最新的发射过来的数据
     */
    fun getCache(key: String) = liveData<String> {
        flow { emit(mRepository.getCache(key)) }
            .flowOn(Dispatchers.IO)
            .catch { sendEvent(Demo3Event.ShowToast("获取缓存 $key 失败")) }
            .collectLatest { emit(it) }
    }

    /**
     * 添加缓存
     */
    fun putCache(key: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.putCache(key, content)
        }
    }

    fun sendEvent(event: Demo3Event) {
        _events.value = event
    }

    /**
     * 一次性事件
     */
    sealed class Demo3Event {
        data class ShowSnackbar(val message: String = "") : Demo3Event()
        data class ShowToast(val message: String = "") : Demo3Event()
        data class ShowDialog(val title: String = "提示", val message: String = "") : Demo3Event()
        data class ShowLoading(val message: String = "") : Demo3Event()
        data class DismissLoading(val message: String = "") : Demo3Event()
        data class StartActivity(val message: String = "") : Demo3Event()
    }

}