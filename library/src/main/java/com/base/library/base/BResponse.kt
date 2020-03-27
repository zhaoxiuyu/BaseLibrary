package com.base.library.base

import com.lzy.okgo.model.Progress

/**
 * 作用：网络请求返回的数据状态类
 */
class BResponse<T> {

    // 一次操作的状态
    companion object {
        val LOADING: Int = 0 // 加载中
        val SUCCESS: Int = 1 // 成功
        val ERROR: Int = 2 // 错误,异常(联网失败，解析异常)
        val FAIL: Int = 3 // 操作失败(关注失败，登录失败)
        val PROGRESS: Int = 4 // 进度，下载或者上传

        // 加载状态的对象
        fun <T> ResponseLoading(): BResponse<T> {
            return BResponse<T>().apply { state = LOADING }
        }

        // 联网失败
        fun <T> ResponseError(throwable: Throwable?, msg: String, finish: Boolean): BResponse<T> {
            return BResponse<T>().apply {
                state = ERROR
                errorMsg = msg
                error = throwable
                isFinish = finish
            }
        }

        // 进度，下载或者上传
        fun <T> ResponseProgress(pr: Progress?): BResponse<T> {
            return BResponse<T>().apply {
                state = PROGRESS
                progress = pr
            }
        }
    }

    // 当前这个对象的状态
    var state: Int = 0

    // 数据体，失败描述，错误码，错误异常
    var data: T? = null
    var errorMsg: String = ""
    var errorCode: Int = 0
    var error: Throwable? = null

    var loadingMsg: String = "请稍候"

    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面

    // 文件下载 上传需要的字段
    var progress: Progress? = null

    // 定义状态的回调
    interface OnHandleCallback<T> {
        fun onLoading(msg: String)
        fun onSuccess(msg: String, data: T?, isFinish: Boolean = false)
        fun onError(error: Throwable?, isFinish: Boolean = false)
        fun onFailure(msg: String, isFinish: Boolean = false)
        fun onCompleted()
        fun onProgress(progress: Progress?)
    }

    // Activity 回调处理
    fun handler(callback: OnHandleCallback<T>) {
        if (state != LOADING) {
            callback.onCompleted()
        }

        when (state) {
            LOADING -> callback.onLoading(loadingMsg)
            SUCCESS -> callback.onSuccess(errorMsg, data)
            ERROR -> callback.onError(error)
            FAIL -> callback.onFailure(errorMsg)
            PROGRESS -> callback.onProgress(progress)
        }
    }

}