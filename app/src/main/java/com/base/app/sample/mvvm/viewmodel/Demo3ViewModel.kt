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
            changeStateLoading()
            val mArticle = withContext(Dispatchers.IO) {
                mRepository.getArticle()
            }

            val mChapters = async { mRepository.getChapters() }
            val mBanner = async { mRepository.getBanner() }

            val mTriple = Triple(mArticle.data, mChapters.await().data, mBanner.await().data)

            _article.value = mTriple
            changeStateSuccess()
        }
    }

    /**
     * 获取公众号列表
     */
    private val _chaptersLiveData =
        UnPeekLiveData.Builder<BResponse<MutableList<WanChapters>>>().create()
    val chaptersLiveData: ProtectedUnPeekLiveData<BResponse<MutableList<WanChapters>>> get() = _chaptersLiveData

    fun getChapters() {
        viewModelScope.launch(Dispatchers.Main) {
            flow {
                emit(mRepository.getChapters())
            }.onStart { changeStateLoading() }
                .catch { changeStateFail("获取公众号列表失败 : ${it.message}") }
                .onCompletion { changeStateSuccess() }
                .collect { _chaptersLiveData.value = it }
        }
    }

    /**
     * 登录
     */
    fun getLogin(map: Map<String, String>) = liveData {
        flow {
            val login = mRepository.getLogin(map["username"] ?: "", map["password"] ?: "")
            emit(login)
        }.onStart { changeStateLoading() }
            .catch { changeStateFail("登录失败 : ${it.message}") }
            .onCompletion { changeStateSuccess() }
            .collect { emit(it) }
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
            .catch { changeStateMessage("获取缓存 $key 失败") }
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

}