package com.base.module.function.mvvm.repository

import com.base.library.data.http.HttpDataSourceImpl
import com.base.library.data.local.LocalDataSourceImpl
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanAndroid
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.util.RxHttpUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rxhttp.async
import rxhttp.onErrorReturn
import rxhttp.onErrorReturnItem
import rxhttp.wrapper.param.toResponse

class Demo4Repository {

    private val mHttpData = HttpDataSourceImpl.getInstance
    private val mLocalData = LocalDataSourceImpl.getInstance

    /**
     * 登录
     */
    suspend fun getLogin(username: String, password: String): BResponse<WanLogin> {
        return mHttpData.getLogin(mapOf("username" to username, "password" to password))
            .toResponse<WanLogin>()
            .onErrorReturn { RxHttpUtils.getDeftBResponse(WanLogin::class.java, it) }
            .await()
    }

    /**
     * 首页banner
     */
    suspend fun getBanner(): BResponse<MutableList<WanAndroid>> {
        return mHttpData.getBanner().toResponse<MutableList<WanAndroid>>()
            .onErrorReturnItem(RxHttpUtils.getDeftBResponses(WanAndroid::class.java)) // 如果出错了就给出默认值，不影响其他请求的执行
            .await()
    }

    /**
     * 获取公众号列表
     */
    suspend fun getChapters(scope: CoroutineScope): Deferred<BResponse<MutableList<WanChapters>>> {
        return mHttpData.getChapters().toResponse<MutableList<WanChapters>>()
            .onErrorReturn { RxHttpUtils.getDeftBResponses(WanChapters::class.java, it) }
            .async(scope)
    }

    /**
     * 首页文章列表
     */
    suspend fun getArticle(scope: CoroutineScope): Deferred<BResponse<WanArticle>> {
        return mHttpData.getArticle().toResponse<WanArticle>()
            .onErrorReturnItem(RxHttpUtils.getDeftBResponse(WanArticle::class.java)) // 如果出错了就给出默认值，不影响其他请求的执行
            .async(scope)
    }

    /**
     * 获取缓存
     */
    fun getCacheFlow(key: String): Flow<String> {
        return flow { emit(mLocalData.getCache(key)) }.flowOn(Dispatchers.IO)
    }

    /**
     * 添加缓存
     */
    fun putCacheFlow(key: String, content: String) {
        mLocalData.putCache(key, content)
    }

}