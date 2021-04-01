package com.base.app.module.test

import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.rxhttp.RxRequest
import kotlinx.coroutines.flow.Flow
import rxhttp.asFlow
import rxhttp.onErrorReturnItem
import rxhttp.wrapper.param.toResponse
import javax.inject.Inject

class DetailRepository @Inject constructor() {

    fun getArticle(): Flow<BResponse<WanArticle>> {
        return RxRequest(BConstant.article).httpGet()
            .setDomainTowanandroidIfAbsent()
            .toResponse<WanArticle>()
            .onErrorReturnItem(BResponse()) // 如果出错了就给出默认值,不影响其他请求的执行
            .asFlow()
    }

}
