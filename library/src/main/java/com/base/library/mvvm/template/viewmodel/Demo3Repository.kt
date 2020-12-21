package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvvm.core.BRepository
import com.base.library.rxhttp.RxRequest

class Demo3Repository(private val mRepository: BRepository) {

    // 首页文章列表
    fun getArticle(liveData: MutableLiveData<BResponse<WanArticle>>) {
        val request = RxRequest(BConstant.article)
        request.httpGet().setDomainTowanandroidIfAbsent()

        mRepository.getResponse(request, WanArticle::class.java, liveData)
    }

    // 获取公众号列表
    fun getChapters(liveData: MutableLiveData<BResponse<MutableList<WanChapters>>>) {
        val request = RxRequest(BConstant.chapters)
        request.httpGet().setDomainTowanandroidIfAbsent()

        mRepository.getResponseList(request, WanChapters::class.java, liveData, null)
    }

    // 登录
    fun getLogin(map: Map<String, String>, liveData: MutableLiveData<BResponse<WanLogin>>) {
        val request = RxRequest(BConstant.login)
        request.httpPostForm().setDomainTowanandroidIfAbsent().addAll(map)

        mRepository.getResponse(request, WanLogin::class.java,
            call = object : SuccessCall<BResponse<WanLogin>> {
                override fun accept(bResponse: BResponse<WanLogin>) {
                    liveData.value = bResponse
                }
            })
    }

}