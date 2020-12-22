package com.base.library.mvp.core

import android.graphics.Bitmap
import com.base.library.entitys.BResponse
import com.base.library.interfaces.MyLifecycle
import com.base.library.rxhttp.RxRequest

/**
 * 作用: 基于MVP架构的Presenter 代理的基类
 */
interface VPPresenter {

    fun <T> getResponse(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<BResponse<T>>? = null
    )

    fun <T> getResponseList(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<BResponse<MutableList<T>>>? = null
    )

    fun <T> getDataClass(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<T>? = null
    )

    fun <T> getDataList(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<MutableList<T>>? = null
    )

    fun getDataString(
        request: RxRequest,
        call: SuccessCall<String>? = null
    )

    fun getDataBitmap(
        request: RxRequest,
        call: SuccessCall<Bitmap>? = null
    )

    fun <T> getDataMap(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<Map<T, T>>? = null
    )

    fun <T> success(
        req: RxRequest,
        res: T,
        call: SuccessCall<T>? = null
    )

}
