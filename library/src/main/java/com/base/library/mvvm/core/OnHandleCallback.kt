package com.base.library.mvvm.core

import com.base.library.rxhttp.RxRequest
import rxhttp.wrapper.entity.Progress

/**
 * 定义状态的回调
 */
interface OnHandleCallback {

    fun onSuccess(mRequest: RxRequest)

    fun onError(mRequest: RxRequest)

    fun onLoading(mRequest: RxRequest)

    fun onCompleted()

    fun onProgress(progress: Progress?)

}