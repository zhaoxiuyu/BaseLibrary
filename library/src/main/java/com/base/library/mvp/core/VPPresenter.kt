package com.base.library.mvp.core

import com.base.library.entitys.BRequest
import com.base.library.interfaces.MyLifecycle

/**
 * 作用: 基于MVP架构的Presenter 代理的基类
 */
interface VPPresenter : MyLifecycle {

    /**
     * 数据请求
     */
    fun <T> getData(bRequest: BRequest, clas: Class<T>)

    fun <T> getDatas(bRequest: BRequest, clas: Class<T>)

    fun <T> getDataString(bRequest: BRequest)

}
