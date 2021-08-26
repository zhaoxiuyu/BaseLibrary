package com.base.library.event

import com.base.library.mvvm.OnHandleCallback

/**
 * 请求状态的回调
 */
class EventState(private val state: Int) {

    // 方法名 或者请求的 url
    var method: String = ""

    // 描述信息
    var msg: String = ""

    // 点击确定按钮,是否销毁当前页面
    var finish = false

    // Activity/Fragment 回调处理
    fun handler(callback: OnHandleCallback) {
        when (state) {
            DIALOGLOADING -> callback.loadingEvent(method, msg)
            DIALOGMESSAGE -> callback.messageEvent(method, msg, finish)
            DIALOGDISMISS -> callback.dismissEvent(method)
        }
    }

    // 一次操作的状态
    companion object {
        // 加载
        const val DIALOGLOADING: Int = 1

        // 消息
        const val DIALOGMESSAGE: Int = 2

        // 消除
        const val DIALOGDISMISS: Int = 3

        // 获取状态对象
        fun getStateEntity(
            method: String = "",
            msg: String = "",
            state: Int,
            finish: Boolean = false
        ): EventState {
            val mResponseState = EventState(state)
            mResponseState.method = method
            mResponseState.msg = msg
            mResponseState.finish = finish
            return mResponseState
        }
    }

}