package com.base.library.mvvm.core

import com.lzy.okgo.model.Progress

/**
 * 定义状态的回调
 */
interface OnHandleCallback {

    fun onLoading(msg: String)

    fun onSuccess(msg: String, data: Any?, isFinish: Boolean = false)

    fun onError(error: Throwable?, message: String, isFinish: Boolean = false)

    fun onCompleted()

    fun onProgress(progress: Progress?)

}