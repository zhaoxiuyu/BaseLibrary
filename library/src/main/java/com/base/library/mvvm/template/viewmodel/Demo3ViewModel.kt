package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvvm.core.VMViewModel

class Demo3ViewModel : VMViewModel() {

    // 首页文章列表
    val article by lazy { MutableLiveData<BResponse<WanArticle>>() }

    fun getArticle() {
        val request = BRequest(BConstant.article).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getData(request, article, WanArticle::class.java)
    }

    // 获取公众号列表
    val chapters by lazy { MutableLiveData<BResponse<MutableList<WanChapters>>>() }

    fun getChapters() {
        val request = BRequest(BConstant.chapters).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getDatas(request, chapters, WanChapters::class.java)
    }

    // 登录
    val login by lazy { MutableLiveData<BResponse<WanLogin>>() }

    fun getLogin(map: Map<String, String>) {
        val request = BRequest(BConstant.login, BRequest.PostForm).apply {
            params = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getData(request, login, WanLogin::class.java)
    }

    /**
     * ------------------------------------------------------------------------------
     */

    fun getArticle2() {
        val request = BRequest(BConstant.article).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getData(request, WanArticle::class.java, object : SuccessCall<BResponse<WanArticle>> {
            override fun accept(bResponse: BResponse<WanArticle>) {

            }
        })
    }

    fun getChapters2() {
        val request = BRequest(BConstant.chapters).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getDatas(request,
            WanArticle::class.java,
            object : SuccessCall<BResponse<MutableList<WanArticle>>> {
                override fun accept(bResponse: BResponse<MutableList<WanArticle>>) {
                }
            })
    }

    fun getLogin2(map: Map<String, String>) {
        val request = BRequest(BConstant.login, BRequest.PostForm).apply {
            params = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getData(request, WanLogin::class.java, object : SuccessCall<BResponse<WanLogin>> {
            override fun accept(bResponse: BResponse<WanLogin>) {
            }
        })
    }

    /**
     * ------------------------------------------------------------------------------
     */

    fun getString(map: Map<String, String>) {
        val request = BRequest(BConstant.login, BRequest.PostForm).apply {
            params = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getDataString(request, object : SuccessCall<String> {
            override fun accept(bResponse: String) {
            }
        })
    }

    val str by lazy { MutableLiveData<String>() }

    fun getString2(map: Map<String, String>) {
        val request = BRequest(BConstant.login, BRequest.PostForm).apply {
            params = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getDataString(request, str)
    }

}