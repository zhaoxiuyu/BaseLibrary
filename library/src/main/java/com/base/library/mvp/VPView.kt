package com.base.library.mvp

/**
 * 定义状态的回调，在 BActivity BFragment 中处理
 */
interface VPView {

    fun loadingEvent(method: String = "", msg: String = "请稍后...")

    fun messageEvent(method: String = "", msg: String = "", finish: Boolean = false)

    fun dismissEvent(method: String = "")

    fun finishEvent()

    fun startActivityEvent()

    fun otherEvent(content: String)

}