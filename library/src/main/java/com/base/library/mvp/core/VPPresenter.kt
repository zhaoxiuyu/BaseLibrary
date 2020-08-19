package com.base.library.mvp.core

import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.interfaces.MyLifecycle

/**
 * 作用: 基于MVP架构的Presenter 代理的基类
 */
interface VPPresenter : MyLifecycle {

    fun <T> getData(bRequest: BRequest, clas: Class<T>, sc: SuccessCall<BResponse<T>>)

    fun <T> getDatas(bRequest: BRequest, clas: Class<T>, sc: SuccessCall<BResponse<MutableList<T>>>)

    fun getDataString(bRequest: BRequest, sc: SuccessCall<String>)

    fun <T> success(req: BRequest, res: BResponse<T>, sc: SuccessCall<BResponse<T>>)

}
