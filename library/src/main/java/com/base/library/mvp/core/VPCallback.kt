package com.base.library.mvp.core

import com.base.library.entitys.BaseResponse
import com.base.library.entitys.BRequest

/**
 * 作用: 网络请求监听基类
 */
interface VPCallback {

    /**
     * 请求之前调用
     */
    fun beforeRequest()

    /**
     * 返回成功调用 返回数据
     */
    fun requestSuccess(body: String, bRequest: BRequest)

    /**
     * 返回成功调用 返回数据
     */
    fun requestSuccess(response: BaseResponse, bRequest: BRequest)

    /**
     * 请求错误调用
     */
    fun requestError(throwable: Throwable?, bRequest: BRequest)

    /**
     * 可以用来保存日志
     */
    fun other(content: String, behavior: String, level: String)

}
