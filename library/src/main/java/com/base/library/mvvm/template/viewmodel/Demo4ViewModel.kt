package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.rxLifeScope
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvvm.core.BViewModel
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import rxhttp.async
import rxhttp.onErrorReturnItem
import rxhttp.wrapper.param.toResponse

class Demo4ViewModel : BViewModel() {

    /**
     * liveData{ }协程代码块,产生一个不可变LiveData
     * emit()方法用来更新LiveData数据
     * collectLatest末端操作符,一段时间内只接受最新的发射过来的数据
     */
    fun getCache(key: String) = liveData<String> {
        cRepository.getCacheFlow(key)
            .catch { LogUtils.e("出异常了 = ${it.message}") }
            .collectLatest { emit(it) }
    }

    /**
     * 登录
     */
    fun collectLogin(username: String, password: String) = liveData {
        cRepository.getLogin2(username, password).collectLatest { emit(it) }
    }

    /**
     * 获取首页文章列表
     */
//    fun getArticle() = liveData<BResponse<WanArticle>> {
//        cRepository.getArticle()
//            .onStart { }
//            .catch { }
//            .collectLatest { emit(it) }
//    }

    /**
     * 公众号列表和文章列表一起同步获取
     */
//    fun getChapters() = liveData {
//        cRepository.getChaptersInfo().collect { emit(it) }
//    }

    fun getChapters(): MutableLiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>> {
        val liveData =
            MutableLiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>>()
        rxLifeScope.launch {
            cRepository.getChaptersInfo(this).collect {
                liveData.value = it
            }
        }
        return liveData
    }

    /**
     * 登录成功之后获取公众号列表
     */
//    fun getLoginChaptersInfo(username: String, password: String) = liveData {
//        cRepository.getLoginChaptersInfo(username, password).collectLatest { emit(it) }
//    }

    /**
     * 协程 - 并行获取列表
     * 无论是串行还是并行，如果其中一个出现异常了，协程自动关闭 并自动结束请求，停止剩下的代码走异常回调
     * 如果要互补影响，可以使用onErrorReturn、onErrorReturnItem出异常时给出默认对象
     */
//    fun getParallel() {
//        rxLifeScope.launch({
//            // 串行请求
////            articleLiveData.value = getBanners(this).await()
////            chaptersLiveData.value = getStudents(this).await()
//
//            // 并行请求
////            val asyncBanners = cRepository.getArticle(this)
////            val asyncStudents = cRepository.getChapters(this)
//
////            articleLiveData.value = asyncBanners.await()
////            chaptersLiveData.value = asyncStudents.await()
//        }, {
//            LogUtils.d("出现异常了")
//        })
//    }

    // 首页文章列表
    suspend fun getArticle(scope: CoroutineScope): Deferred<BResponse<WanArticle>> {
        val request = RxRequest(BConstant.article)
        return request.httpGet().setDomainTowanandroidIfAbsent()
            .toResponse<WanArticle>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值，不影响其他请求的执行
            .async(scope)
    }

}