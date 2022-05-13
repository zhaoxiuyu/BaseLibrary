package com.base.app.sample.mvi.repository

import com.base.app.entitys.response.WanAndroid
import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.entitys.response.WanLogin
import com.base.app.http.OkHttpUtils
import com.base.library.base.BResponse
import com.base.library.data.BaseRepository
import io.reactivex.rxjava3.core.Observable

class Demo5Repository : BaseRepository() {

    private val appService by lazy { OkHttpUtils.appService }

    private val appServiceRx by lazy { OkHttpUtils.appServiceRx }

    /**
     *  suspend
     */

    // 登录
    suspend fun getLogin(username: String, password: String): BResponse<WanLogin> {
        return appService.getLogin(username, password)
    }

    // 登录
    suspend fun getLoginStr(username: String, password: String): String {
        return appService.getLoginStr(username, password)
    }

    // 首页banner
    suspend fun getBanner(): BResponse<MutableList<WanAndroid>> {
        return appService.getBanner()
    }

    // 首页文章列表
    suspend fun getArticle(): BResponse<WanArticle> {
        return appService.getArticle()
    }

    // 获取公众号列表
    suspend fun getChapters(): BResponse<MutableList<WanChapters>> {
        return appService.getChapters()
    }

    /**
     *  Observable
     */

    // 获取首页文章列表
    fun getArticleObser(): Observable<BResponse<WanArticle>> {
        return appServiceRx.getArticleObser()
    }

    // 获取公众号列表
    fun getChaptersObser(): Observable<BResponse<MutableList<WanChapters>>> {
        return appServiceRx.getChaptersObser()
    }

    // 登录
    fun getLoginObser(username: String, password: String): Observable<BResponse<WanLogin>> {
        return appServiceRx.getLoginObser(username, password)
    }

}