package com.base.app.module.test

import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import kotlinx.coroutines.flow.Flow
import rxhttp.asFlow
import rxhttp.onErrorReturnItem
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

class DetailRepository {

    fun getArticle(): Flow<BResponse<WanArticle>> {
        return RxHttp.getParamEncrypt(BConstant.article)
            .setDomainTowanandroidIfAbsent()
            .toResponse<WanArticle>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值,不影响其他请求的执行
            .asFlow()
    }

    fun getArticle2(): Flow<BResponse<WanArticle>> {
        return RxHttp.getParamEncrypt(BConstant.article)
            .setDomainTowanandroidIfAbsent()
            .toFlow<BResponse<WanArticle>>()
    }

}
