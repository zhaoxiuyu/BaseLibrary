package com.base.library.rxhttp

import com.base.library.mvvm.core.OnHandleCallback
import rxhttp.wrapper.entity.Progress

/**
 * 请求状态的回调
 */
class RxHttpState(var state: Int) {

    // 当前这个对象的状态
    var progress: Progress? = null

    var mRequest: RxRequest? = null

    // Activity 回调处理
    fun handler(callback: OnHandleCallback) {
        when (state) {
            LOADING -> mRequest?.let { callback.onLoading(it) }
            SUCCESS -> mRequest?.let { callback.onSuccess(it) }
            ERROR -> mRequest?.let { callback.onError(it) }
            COMPLETED -> callback.onCompleted()
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
        fun Loading(mRequest: RxRequest): RxHttpState {
            val mRxHttpState = RxHttpState(LOADING)
            mRxHttpState.mRequest = mRequest
            return mRxHttpState
        }

        // 成功
        fun Success(mRequest: RxRequest): RxHttpState {
            val mRxHttpState = RxHttpState(SUCCESS)
            mRxHttpState.mRequest = mRequest
            return mRxHttpState
        }

        // 失败
        fun Error(mRequest: RxRequest): RxHttpState {
            val mRxHttpState = RxHttpState(ERROR)
            mRxHttpState.mRequest = mRequest
            return mRxHttpState
        }

        // 完成
        fun Completed(): RxHttpState = RxHttpState(COMPLETED)

        // 进度，下载或者上传
        fun Progress(pr: Progress?): RxHttpState = RxHttpState(PROGRESS).apply { progress = pr }

    }

}