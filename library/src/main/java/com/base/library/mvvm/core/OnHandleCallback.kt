package com.base.library.mvvm.core

import com.base.library.rxhttp.RxRequest
import rxhttp.wrapper.entity.Progress

/**
 * 定义状态的回调
 */
interface OnHandleCallback {

    fun onLoading(method: String, msg: String)

    fun onSuccess(mRequest: RxRequest)

    fun onError(mRequest: RxRequest)

    fun onCompleted(method: String)

    fun onProgress(progress: Progress?)

}