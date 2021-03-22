package com.base.module.function.mvvm.repository

import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvvm.core.WanAndroid
import com.base.library.rxhttp.RxRequest
import com.base.library.util.OtherUtils
import com.blankj.utilcode.util.CacheDiskStaticUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rxhttp.async
import rxhttp.flowOn
import rxhttp.onErrorReturn
import rxhttp.onErrorReturnItem
import rxhttp.wrapper.param.toResponse
import javax.inject.Inject

class Demo4Repository @Inject constructor() {

    /**
     * 获取缓存
     */
    suspend fun getCacheFlow(key: String): Flow<String> {
        return flow<String> {
            val cacheValue = CacheDiskStaticUtils.getString(key, "")
            emit(cacheValue ?: "")
        }.flowOn(Dispatchers.IO)
    }

    /**
     * 串行。登录 -> 首页banner
     */
    fun getLoginBanner(u: String, p: String): Flow<BResponse<MutableList<WanAndroid>>> {
        return flow {
            // 登录
            val login = getLogin(u, p)
            // 首页banner
            val banner = getBanner()
            emit(banner)
        }
    }

    /**
     * 并行。公众号 文章列表同步获取
     */
    suspend fun getChaptersInfo(scope: CoroutineScope): Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>> {
        // 公众号列表
        val chapters = getChapters(scope)
        // 首页文章列表
        val articleNew = getArticle(scope)

        // 两个一起返回
        return Pair(chapters.await(), articleNew.await())
    }
//    fun getChaptersInfo(scope: CoroutineScope): Flow<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>> {
//        return flow {
//            // 公众号列表
//            val chapters = getChapters(scope)
//            // 首页文章列表
//            val articleNew = getArticle(scope)
//
//            // 两个一起返回
//            val pair = Pair(chapters.await(), articleNew.await())
//            emit(pair)
//        }
//    }

    // 登录
    suspend fun getLogin(username: String, password: String): BResponse<WanLogin> {
        val map = mapOf("username" to username, "password" to password)
        return RxRequest(BConstant.login)
            .httpPostForm()
            .setDomainTowanandroidIfAbsent()
            .addAll(map)
            .toResponse<WanLogin>()
            .onErrorReturn { getDeftBResponse(WanLogin::class.java, it) }
            .await()
    }

    // 首页banner
    suspend fun getBanner(): BResponse<MutableList<WanAndroid>> {
        return RxRequest(BConstant.banner).httpGet().setDomainTowanandroidIfAbsent()
            .toResponse<MutableList<WanAndroid>>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值，不影响其他请求的执行
            .await()
    }

    // 获取公众号列表
    suspend fun getChapters(scope: CoroutineScope): Deferred<BResponse<MutableList<WanChapters>>> {
        return RxRequest(BConstant.chapters).httpGet().setDomainTowanandroidIfAbsent()
            .toResponse<MutableList<WanChapters>>()
            .onErrorReturn { getDeftBResponses(WanChapters::class.java, it) }
            .async(scope)
    }

    // 首页文章列表
    suspend fun getArticle(scope: CoroutineScope): Deferred<BResponse<WanArticle>> {
        return RxRequest(BConstant.article).httpGet().setDomainTowanandroidIfAbsent()
            .toResponse<WanArticle>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值，不影响其他请求的执行
            .async(scope)
    }

    private fun <T> getDeftBResponse(c: Class<T>, t: Throwable): BResponse<T> {
        return BResponse<T>().apply {
            errorMsg = OtherUtils.getThrowableMessage(t)
        }
    }

    private fun <T> getDeftBResponses(c: Class<T>, t: Throwable): BResponse<MutableList<T>> {
        return BResponse<MutableList<T>>().apply {
            errorMsg = OtherUtils.getThrowableMessage(t)
        }
    }

}