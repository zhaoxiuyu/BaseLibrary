package com.base.library.entitys

import com.base.library.mvvm.core.OnHandleCallback
import com.lzy.okgo.model.Progress

/**
 * 作用：网络请求返回的数据状态类
 */
class BResponse<T> {

    // 当前这个对象的状态
    var state: Int = 0

    // 数据体，失败描述，错误码，错误异常
    var data: T? = null
    var message: String = ""
    var errorCode: Int = 0
    var error: Throwable? = null

    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面

    // 文件下载 上传需要的字段
    var progress: Progress? = null

    // Activity 回调处理
    fun handler(callback: OnHandleCallback) {
        if (state != LOADING) {
            callback.onCompleted()
        }

        when (state) {
            LOADING -> callback.onLoading(message)
            SUCCESS -> callback.onSuccess(message, data)
            ERROR -> callback.onError(error, message)
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
        fun <T> ResponseLoading(msg: String = "请稍候..."): BResponse<T> {
            return BResponse<T>().apply {
                state = LOADING
                message = msg
            }
        }

        // 失败
        fun <T> ResponseError(throwable: Throwable?, msg: String, finish: Boolean): BResponse<T> {
            return BResponse<T>().apply {
                state = ERROR
                message = msg
                error = throwable
                isFinish = finish
            }
        }

        // 成功
        fun <T> ResponseSuccess(msg: String, finish: Boolean): BResponse<T> {
            return BResponse<T>().apply {
                state = SUCCESS
                message = msg
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

}