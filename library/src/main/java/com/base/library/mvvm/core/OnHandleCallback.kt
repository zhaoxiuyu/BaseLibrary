package com.base.library.mvvm.core

import rxhttp.wrapper.entity.Progress

/**
 * 定义状态的回调
 */
interface OnHandleCallback {

    fun onLoading(msg: String, isSilence: Boolean = false)

    fun onSuccess(
        msg: String,
        url: String? = "",
        isFinish: Boolean = false,
        isSilence: Boolean = false
    )

    fun onError(
        msg: String,
        url: String? = "",
        isFinish: Boolean = false,
        isSilence: Boolean = false
    )

    fun onCompleted(msg: String, url: String? = "")

    fun onProgress(progress: Progress?)

}