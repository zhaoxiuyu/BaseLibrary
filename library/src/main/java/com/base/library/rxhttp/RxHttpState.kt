package com.base.library.rxhttp

import com.base.library.mvvm.core.OnHandleCallback
import rxhttp.wrapper.entity.Progress

class RxHttpState(var state: Int, var message: String) {

    // 当前这个对象的状态
    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面
    var progress: Progress? = null
    var throwable: Throwable? = null

    // Activity 回调处理
    fun handler(callback: OnHandleCallback) {
        if (state != LOADING) {
            callback.onCompleted()
        }

        when (state) {
            LOADING -> callback.onLoading(message)
            SUCCESS -> callback.onSuccess(message)
            ERROR -> callback.onError(throwable, message)
            PROGRESS -> callback.onProgress(progress)
        }
    }

    // 一次操作的状态
    companion object {
        val LOADING: Int = 0 // 加载中
        val SUCCESS: Int = 1 // 成功
        val ERROR: Int = 2 // 失败
        val PROGRESS: Int = 4 // 进度，下载或者上传

        // 加载状态的对象
        fun Loading(msg: String = "请稍候..."): RxHttpState = RxHttpState(LOADING, msg)

        // 失败
        fun Error(throwable: Throwable?, msg: String, finish: Boolean): RxHttpState =
            RxHttpState(ERROR, msg).apply { isFinish = finish }

        // 成功
        fun Success(msg: String, finish: Boolean): RxHttpState =
            RxHttpState(SUCCESS, msg).apply { isFinish = finish }

        // 进度，下载或者上传
        fun Progress(pr: Progress?, msg: String = "进度"): RxHttpState =
            RxHttpState(PROGRESS, msg).apply { progress = pr }
    }

}