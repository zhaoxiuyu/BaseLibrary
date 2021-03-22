package com.base.library.rxhttp

import com.base.library.mvvm.core.OnHandleCallback
import rxhttp.wrapper.entity.Progress

/**
 * 请求状态的回调
 */
class ResponseState(private val state: Int) {

    // 当前这个对象的状态
    var progress: Progress? = null

    var method: String = ""
    var msg: String = ""

    // 请求成功 或 失败，点击确定按钮，是否销毁当前页面
    var clickFinish = false

    // Activity 回调处理
    fun handler(callback: OnHandleCallback) {
        when (state) {
            LOADING -> callback.onLoading(method, msg)
            SUCCESS -> callback.let { callback.onSuccess(method, msg, clickFinish) }
            ERROR -> callback.let { callback.onError(method, msg, clickFinish) }
            COMPLETED -> callback.onCompleted(method)
            PROGRESS -> callback.onProgress(progress)
        }
    }

    // 一次操作的状态
    companion object {
        const val LOADING: Int = 0
        const val SUCCESS: Int = 1
        const val ERROR: Int = 2
        const val COMPLETED: Int = 3
        const val PROGRESS: Int = 4

        // 加载状态的对象
        fun Loading(method: String, msg: String = "正在加载...") = getStateEntity(method, msg, LOADING)

        // 成功
        fun Success(method: String, msg: String = "成功", clickFinish: Boolean = false) =
            getStateEntity(method, msg, SUCCESS, clickFinish)

        // 失败
        fun Error(method: String, msg: String = "失败", clickFinish: Boolean = false) =
            getStateEntity(method, msg, ERROR, clickFinish)

        // 完成
        fun Completed(method: String, msg: String = "完成") = getStateEntity(method, msg, COMPLETED)

        // 进度，下载或者上传
        fun Progress(pr: Progress?): ResponseState = ResponseState(PROGRESS).apply { progress = pr }

        // 获取状态对象
        fun getStateEntity(
            method: String,
            msg: String = "",
            identifier: Int,
            clickFinish: Boolean = false
        ): ResponseState {
            val mResponseState = ResponseState(identifier)
            mResponseState.method = method
            mResponseState.msg = msg
            mResponseState.clickFinish = clickFinish
            return mResponseState
        }
    }

}