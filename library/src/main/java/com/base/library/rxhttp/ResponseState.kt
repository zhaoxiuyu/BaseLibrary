package com.base.library.rxhttp

import com.base.library.mvvm.core.OnHandleCallback
import rxhttp.wrapper.entity.Progress

/**
 * 请求状态的回调
 */
class ResponseState(var state: Int) {

    // 当前这个对象的状态
    var progress: Progress? = null

    var mRequest: RxRequest? = null

    var method: String = ""
    var msg: String = ""

    // Activity 回调处理
    fun handler(callback: OnHandleCallback) {
        when (state) {
            LOADING -> callback.onLoading(method, msg)
//            LOADING -> mRequest?.let { callback.onLoading(method, msg) }
            SUCCESS -> mRequest?.let { callback.onSuccess(it) }
            ERROR -> mRequest?.let { callback.onError(it) }
            COMPLETED -> callback.onCompleted(method)
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
        fun Loading(method: String, msg: String = "正在加载..."): ResponseState {
            val mRxHttpState = ResponseState(LOADING)
            mRxHttpState.method = method
            mRxHttpState.msg = msg
            return mRxHttpState
        }

        // 成功
        fun Success(mRequest: RxRequest): ResponseState {
            val mRxHttpState = ResponseState(SUCCESS)
            mRxHttpState.mRequest = mRequest
            return mRxHttpState
        }

        // 失败
        fun Error(mRequest: RxRequest): ResponseState {
            val mRxHttpState = ResponseState(ERROR)
            mRxHttpState.mRequest = mRequest
            return mRxHttpState
        }

        // 完成
        fun Completed(method: String): ResponseState =
            ResponseState(COMPLETED).apply { this.method = method }

        // 进度，下载或者上传
        fun Progress(pr: Progress?): ResponseState = ResponseState(PROGRESS).apply { progress = pr }

    }

}