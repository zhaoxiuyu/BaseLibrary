package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvvm.core.VMViewModel
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.LogUtils

class Demo3ViewModel : VMViewModel() {

    // 首页文章列表
    val article by lazy { MutableLiveData<BResponse<WanArticle>>() }

    fun getArticle() {
        val request = RxRequest(BConstant.article).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponse(request, WanArticle::class.java, article, null)
    }

    // 获取公众号列表
    val chapters by lazy { MutableLiveData<BResponse<MutableList<WanChapters>>>() }

    fun getChapters() {
        val request = RxRequest(BConstant.chapters).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponseList(request, WanChapters::class.java, chapters, null)
    }

    // 登录
    val login by lazy { MutableLiveData<BResponse<WanLogin>>() }

    fun getLogin(map: Map<String, String>) {
        val request = RxRequest(BConstant.login, RxRequest.PostForm).apply {
            paramMap = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponse(request, WanLogin::class.java,
            call = object : SuccessCall<BResponse<WanLogin>> {
                override fun accept(bResponse: BResponse<WanLogin>) {
                    LogUtils.d("SuccessCall")
                    login.value = bResponse
                }
            })
    }

    fun getArticle2() {
        val request = RxRequest(BConstant.article).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponse(request, WanArticle::class.java, liveData = null,
            call = object : SuccessCall<BResponse<WanArticle>> {
                override fun accept(bResponse: BResponse<WanArticle>) {

                }
            })
    }

    fun getChapters2() {
        val request = RxRequest(BConstant.chapters).build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponseList(request, WanArticle::class.java, liveData = null,
            call = object : SuccessCall<BResponse<MutableList<WanArticle>>> {
                override fun accept(bResponse: BResponse<MutableList<WanArticle>>) {
                }
            })
    }

    fun getLogin2(map: Map<String, String>) {
        val request = RxRequest(BConstant.login, RxRequest.PostForm).apply {
            paramMap = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponse(request, WanLogin::class.java, liveData = null,
            call = object : SuccessCall<BResponse<WanLogin>> {
                override fun accept(bResponse: BResponse<WanLogin>) {
                }
            })
    }

    /**
     * ------------------------------------------------------------------------------
     */

    fun getString(map: Map<String, String>) {
        val request = RxRequest(BConstant.login, RxRequest.PostForm).apply {
            paramMap = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getDataString(request, call = object : SuccessCall<String> {
            override fun accept(bResponse: String) {
            }
        })
    }

    val str by lazy { MutableLiveData<String>() }

    fun getString2(map: Map<String, String>) {
        val request = RxRequest(BConstant.login, RxRequest.PostForm).apply {
            paramMap = map
        }.build()
        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getDataString(request, liveData = str)
    }

}