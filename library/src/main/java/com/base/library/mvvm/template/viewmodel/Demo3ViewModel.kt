package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.repository.Demo3Repository
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import rxhttp.async
import rxhttp.onErrorReturnItem
import rxhttp.toClass
import rxhttp.tryAwait
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

class Demo3ViewModel : BViewModel() {

    private val mRepository by lazy { Demo3Repository(getRepository()) }

    /**
     * 获取首页文章列表
     */
    val articleLiveData by lazy { MutableLiveData<BResponse<WanArticle>>() }

    fun getArticle() {
        val request = RxRequest(BConstant.article)
        request.httpGet().setDomainTowanandroidIfAbsent()
        getRepository().getResponse(request, WanArticle::class.java, articleLiveData)
    }

    /**
     * 获取公众号列表
     */
    val chaptersLiveData by lazy { MutableLiveData<BResponse<MutableList<WanChapters>>>() }

    fun getChapters() {
        val request = RxRequest(BConstant.chapters)
        request.httpGet().setDomainTowanandroidIfAbsent()
        getRepository().getResponseList(request, WanChapters::class.java, chaptersLiveData, null)
    }

    /**
     * 登录
     */
    val loginLiveData by lazy { MutableLiveData<BResponse<WanLogin>>() }

    fun getLogin(map: Map<String, String>) {
        val request = RxRequest(BConstant.login)
        request.httpPostForm().setDomainTowanandroidIfAbsent().addAll(map)

        getRepository().getResponse(request, WanLogin::class.java,
            call = object : SuccessCall<BResponse<WanLogin>> {
                override fun accept(bResponse: BResponse<WanLogin>) {
                    loginLiveData.value = bResponse
                }
            })

    }

    /**
     * 协程 - 并行获取列表
     * 无论是串行还是并行，如果其中一个出现异常了，协程自动关闭 并自动结束请求，停止剩下的代码走异常回调
     * 如果要互补影响，可以使用onErrorReturn、onErrorReturnItem出异常时给出默认对象
     */
    fun getParallel() {
        rxLifeScope.launch({
            // 串行请求
//            articleLiveData.value = getBanners(this).await()
//            chaptersLiveData.value = getStudents(this).await()

            // 并行请求
            val asyncBanners = getBanners(this)
            val asyncStudents = getStudents(this)

            articleLiveData.value = asyncBanners.await()
            chaptersLiveData.value = asyncStudents.await()

        }, {
            LogUtils.d("出现异常了")
        })
    }

    // 首页文章列表
    private suspend fun getBanners(scope: CoroutineScope): Deferred<BResponse<WanArticle>> {
        val request = RxRequest(BConstant.article)
        return request.httpGet().setDomainTowanandroidIfAbsent()
            .toClass<BResponse<WanArticle>>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值，不影响其他请求的执行
            .async(scope)
    }

    // 获取公众号列表
    private suspend fun getStudents(scope: CoroutineScope): Deferred<BResponse<MutableList<WanChapters>>> {
        val request = RxRequest(BConstant.chapters)
        return request.httpGet().setDomainTowanandroidIfAbsent()
            .toClass<BResponse<MutableList<WanChapters>>>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值，不影响其他请求的执行
            .async(scope)
    }

}