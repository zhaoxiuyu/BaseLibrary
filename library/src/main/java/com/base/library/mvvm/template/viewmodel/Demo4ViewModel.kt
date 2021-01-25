package com.base.library.mvvm.template.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.rxLifeScope
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.repository.Demo4Repository
import com.base.library.rxhttp.ResponseState
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.flow.*

/**
 * 使用 ViewModelInject ，数据仓库要在构造方法里面初始化
 *
 */
class Demo4ViewModel @ViewModelInject constructor(val mRepository: Demo4Repository) : BViewModel() {

    /**
     * 如果变量声明方式初始化，ViewModel需要用 @ActivityRetainedScoped
     */
//    @Inject
//    lateinit var mRepository: Demo4Repository

    /**
     * liveData{ }协程代码块,产生一个不可变LiveData
     * emit()方法用来更新LiveData数据
     * collectLatest末端操作符,一段时间内只接受最新的发射过来的数据
     */
    fun getCache(key: String) = liveData<String> {
        mRepository.getCacheFlow(key)
            .catch { LogUtils.e("出异常了 = ${it.message}") }
            .collectLatest { emit(it) }
    }

    /**
     * 登录
     */
    fun collectLogin(username: String, password: String) = liveData {
        mRepository.getLogin2(username, password)
            .onStart { sendState(ResponseState.Loading(BConstant.login)) }
            .onCompletion { sendState(ResponseState.Completed(BConstant.login)) }
            .collectLatest { emit(it) }
    }

    /**
     * 登录 -> 首页banner
     */
    fun getLoginBanner(username: String, password: String) = liveData {
        mRepository.getLoginBanner(username, password)
            .onStart { sendState(ResponseState.Loading(BConstant.login)) }
            .onCompletion { sendState(ResponseState.Completed(BConstant.login)) }
            .collect { emit(it) }
    }

    /**
     * 公众号 文章 列表同步获取
     */
    fun getParallel(): MutableLiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>> {
        val liveData =
            MutableLiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>>()
        rxLifeScope.launch {
            mRepository.getChaptersInfo(this)
                .onStart { sendState(ResponseState.Loading(BConstant.login)) }
                .onCompletion { sendState(ResponseState.Completed(BConstant.login)) }
                .collect { liveData.value = it }
        }
        return liveData
    }

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

}