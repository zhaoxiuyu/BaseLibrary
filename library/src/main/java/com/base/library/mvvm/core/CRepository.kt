package com.base.library.mvvm.core

import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.rxhttp.RxRequest
import com.base.library.util.OtherUtils
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import rxhttp.*
import rxhttp.wrapper.param.toResponse

class CRepository {

    /**
     * 获取缓存
     */
    suspend fun getCacheFlow(key: String): Flow<String> {
        return flow<String> {
            val cacheValue = CacheDiskStaticUtils.getString(key, "")
            emit(cacheValue ?: "")
        }.flowOn(Dispatchers.IO) // 通过 flowOn 切换到 IO 线程
    }

    suspend fun getLogin2(username: String, password: String): Flow<BResponse<WanLogin>> {
        val map = mapOf("username" to username, "password" to password)

        val request = RxRequest(BConstant.login)
        request.httpPostForm().setDomainTowanandroidIfAbsent().addAll(map)
        return request.getRxHttp()
            .toResponse<WanLogin>()
            .onErrorReturn { getDeftBResponse(WanLogin::class.java, it) } // 出错后返回这个
            .flowOn(Dispatchers.IO)
            .asFlow()
            .transform {
                if (it.isSuccess()) {
                    emit(it)
                } else {
                    LogUtils.d("失败的，不发送了")
                }
            }
    }

    /**
     * 获取首页文章列表
     */
//    suspend fun getArticle(): Flow<BResponse<WanArticle>> {
//        val request = RxRequest(BConstant.article)
//        request.httpGet().setDomainTowanandroidIfAbsent()
//        return request.getRxHttp()
//            .toResponse<WanArticle>()
//            .onErrorReturn { getDeftBResponse(WanArticle::class.java, it) } // 出错后返回这个
//            .flowOn(Dispatchers.IO)
//            .asFlow()
//            .transform {
//                if (it.isSuccess()) {
//                    emit(it)
//                } else {
//                    LogUtils.d("失败的，不发送了")
//                }
//            }
//    }

    /**
     * 公众号列表和文章列表一起同步获取
     */
    suspend fun getChaptersInfo(scope: CoroutineScope): Flow<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>> {
        return flow {
            // 公众号列表
            val chapters = getChapters(scope)
            // 首页文章列表
            val articleNew = getArticleNew(scope)

            // 两个一起返回
            val pair = Pair(chapters.await(), articleNew.await())

            emit(pair)
        }
    }

    /**
     * 登录成功之后获取公众号列表
     */
//    suspend fun getLoginChaptersInfo(
//        username: String,
//        password: String
//    ): Flow<BResponse<MutableList<WanLogin>>> {
//        return flow {
//            // 登录
//            val login = getLogin(username, password)
//
//            val chapters = getChapters(scope)
//
//            emit(login)
//        }
//    }

    // 登录
    private suspend fun getLogin(username: String, password: String): BResponse<WanLogin> {
        val map = mapOf("username" to username, "password" to password)
        return RxRequest(BConstant.login)
            .httpPostForm()
            .setDomainTowanandroidIfAbsent()
            .addAll(map)
            .toResponse<WanLogin>()
            .onErrorReturn { getDeftBResponse(WanLogin::class.java, it) }
            .flowOn(Dispatchers.IO).await()
    }

    // 获取公众号列表
    private suspend fun getChapters(scope: CoroutineScope): Deferred<BResponse<MutableList<WanChapters>>> {
        return RxRequest(BConstant.chapters).httpGet().setDomainTowanandroidIfAbsent()
            .toResponse<MutableList<WanChapters>>()
            .onErrorReturn { getDeftBResponses(WanChapters::class.java, it) }
            .flowOn(Dispatchers.IO).async(scope)
    }

    // 首页文章列表
    suspend fun getArticleNew(scope: CoroutineScope): Deferred<BResponse<WanArticle>> {
        return RxRequest(BConstant.article).httpGet().setDomainTowanandroidIfAbsent()
            .toResponse<WanArticle>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值，不影响其他请求的执行
            .flowOn(Dispatchers.IO).async(scope)
    }

//    // 获取公众号列表
//    private suspend fun getChapters(scope: CoroutineScope): Deferred<BResponse<MutableList<WanChapters>>> {
//        val request = RxRequest(BConstant.chapters)
//        return request.httpGet().setDomainTowanandroidIfAbsent()
//            .toResponse<MutableList<WanChapters>>()
//            .onErrorReturn { getDeftBResponses(WanChapters::class.java, it) }
//            .async(scope)
//    }

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