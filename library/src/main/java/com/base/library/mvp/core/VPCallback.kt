package com.base.library.mvp.core

import com.base.library.entitys.BRequest

/**
 * 作用: 网络请求监听基类
 */
interface VPCallback {

    /**
     * 请求之前调用
     */
    fun doOnSubscribe(silence: Boolean)

    /**
     * 请求结束
     */
    fun doFinally()

    /**
     * 可以用来保存日志
     */
    fun other(content: String, behavior: String, level: String)

    /**
     * 失败
     */
    fun error(bRequest: BRequest, throwable: Throwable?)

}
