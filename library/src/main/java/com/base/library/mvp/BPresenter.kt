package com.base.library.mvp

import com.base.library.http.BRequest
import com.base.library.interfaces.MyLifecycle

/**
 * 作用: 基于MVP架构的Presenter 代理的基类
 */
interface BPresenter : MyLifecycle {

    /**
     * 数据请求
     */
    fun getData(http: BRequest)

    fun getRetrofit2(http: BRequest)

}
