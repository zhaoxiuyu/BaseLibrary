package com.base.app.module.test

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.mvvm.core.BRepository
import com.base.library.mvvm.core.BViewModel
import com.base.library.rxhttp.RxRequest

class DetailViewModel : BViewModel() {

    private val mBResponse by lazy { BRepository() }

    override fun getRepository() = mBResponse

    /**
     * 获取首页文章列表
     */
    val articleLiveData by lazy { MutableLiveData<BResponse<WanArticle>>() }

    fun getArticle() {
        val request = RxRequest(BConstant.article)
        request.httpGet().setDomainTowanandroidIfAbsent()
        getRepository().getResponse(request, WanArticle::class.java, articleLiveData)
    }

}