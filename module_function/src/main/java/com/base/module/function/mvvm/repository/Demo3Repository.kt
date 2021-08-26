package com.base.module.function.mvvm.repository

import com.base.library.base.BConstant
import com.base.library.data.local.LocalDataSourceImpl
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import io.reactivex.rxjava3.core.Observable
import rxhttp.wrapper.param.RxHttp

class Demo3Repository {

    private val mLocalData = LocalDataSourceImpl.getInstance

    /**
     * 获取首页文章列表
     */
    fun getArticle(): Observable<BResponse<WanArticle>> {
        val rxHttp = RxHttp.getParamEncrypt(BConstant.article).setDomainTowanandroidIfAbsent()
        return rxHttp.asResponse(WanArticle::class.java)
    }

    /**
     * 获取公众号列表
     */
    fun getChapters(): Observable<BResponse<MutableList<WanChapters>>> {
        val rxHttp = RxHttp.getParamEncrypt(BConstant.chapters).setDomainTowanandroidIfAbsent()
        return rxHttp.asResponseList(WanChapters::class.java)
    }

    /**
     * 登录
     */
    fun getLogin(map: Map<String, String>): Observable<BResponse<WanLogin>> {
        val rxHttp = RxHttp.getParamEncrypt(BConstant.login).setDomainTowanandroidIfAbsent()
            .addAll(map)
        return rxHttp.asResponse(WanLogin::class.java)
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