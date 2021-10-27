package com.base.module.function.mvvm.repository

import com.base.library.data.BaseRepository
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import io.reactivex.rxjava3.core.Observable

class Demo3Repository : BaseRepository() {

    /**
     * 获取首页文章列表
     */
    fun getArticle(): Observable<BResponse<WanArticle>> {
        return mHttpData.getArticle().asResponse(WanArticle::class.java)
    }

    /**
     * 获取公众号列表
     */
    fun getChapters(): Observable<BResponse<MutableList<WanChapters>>> {
        return mHttpData.getChapters().asResponseList(WanChapters::class.java)
    }

    /**
     * 登录
     */
    fun getLogin(map: Map<String, String>): Observable<BResponse<WanLogin>> {
        return mHttpData.getLogin(map).asResponse(WanLogin::class.java)
    }

    /**
     * 添加缓存
     */
    fun putCache(key: String, content: String, time: Int = -1) {
        mLocalData.putCache(key, content, time)
    }

    /**
     * 获取缓存
     */
    fun getCache(key: String, defaultValue: String = ""): String {
        return mLocalData.getCache(key, defaultValue)
    }

}
