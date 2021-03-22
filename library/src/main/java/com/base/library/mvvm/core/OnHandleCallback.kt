package com.base.library.mvvm.core

import rxhttp.wrapper.entity.Progress

/**
 * 定义状态的回调
 */
interface OnHandleCallback {

    fun onLoading(method: String, msg: String)

    fun onSuccess(method: String, msg: String, clickFinish: Boolean)

    fun onError(method: String, msg: String, clickFinish: Boolean)

    fun onCompleted(method: String)

    fun onProgress(progress: Progress?)

}