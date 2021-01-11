package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvvm.core.BRepository
import com.base.library.mvvm.core.BViewModel
import com.base.library.rxhttp.RxRequest

class Demo3ViewModel : BViewModel() {

    private val mBRepository by lazy { BRepository() }

    override fun getRepository() = mBRepository

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

}