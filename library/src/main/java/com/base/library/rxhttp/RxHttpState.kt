package com.base.library.rxhttp

import com.base.library.mvvm.core.OnHandleCallback
import rxhttp.wrapper.entity.Progress

class RxHttpState(var state: Int, var message: String) {

    // 当前这个对象的状态
    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面
    var isSilence = false//是否静默加载
    var progress: Progress? = null
    var throwable: Throwable? = null
    var url: String? = null

    // Activity 回调处理
    fun handler(callback: OnHandleCallback) {
        when (state) {
            LOADING -> callback.onLoading(message, isSilence)
            SUCCESS -> callback.onSuccess(message, url, isFinish, isSilence)
            ERROR -> callback.onError(message, url, isFinish, isSilence)
            COMPLETED -> callback.onCompleted(message, url)
            PROGRESS -> callback.onProgress(progress)
        }
    }

    // 一次操作的状态
    companion object {
        val LOADING: Int = 0 // 加载中
        val SUCCESS: Int = 1 // 成功
        val ERROR: Int = 2 // 失败
        val COMPLETED: Int = 3 // 完成
        val PROGRESS: Int = 4 // 进度，下载或者上传

        // 加载状态的对象
        fun Loading(msg: String = "请稍候...", silence: Boolean = false): RxHttpState =
            RxHttpState(LOADING, msg).apply {
                isSilence = silence
            }

        // 失败
        fun Error(
            msg: String,
            url: String = "",
            finish: Boolean,
            silence: Boolean
        ): RxHttpState =
            RxHttpState(ERROR, msg).apply {
                this.url = url
                isFinish = finish
                isSilence = silence
            }

        // 成功
        fun Success(msg: String, url: String = "", finish: Boolean, silence: Boolean): RxHttpState =
            RxHttpState(SUCCESS, msg).apply {
                this.url = url
                isFinish = finish
                isSilence = silence
            }

        // 完成
        fun Completed(msg: String, url: String = ""): RxHttpState =
            RxHttpState(COMPLETED, msg).apply {
                this.url = url
            }

        // 进度，下载或者上传
        fun Progress(pr: Progress?, msg: String = "进度"): RxHttpState =
            RxHttpState(PROGRESS, msg).apply { progress = pr }
    }

}