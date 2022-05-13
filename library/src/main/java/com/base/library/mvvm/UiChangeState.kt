package com.base.library.mvvm

sealed class UiChangeState {

    // 未初始化
    object Uninitialized : UiChangeState()

    // 加载
    data class Loading(val msg: String = "请稍候") : UiChangeState()

    // 成功
    data class Success(val msg: String = "") : UiChangeState()

    // 失败
    data class Fail(val msg: String = "") : UiChangeState()

    // 消息
    data class Message(val msg: String) : UiChangeState()

}