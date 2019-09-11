package com.zxy.library.framework.mvp

import com.base.library.entitys.BaseResponse
import com.base.library.http.BRequest

/**
 * 作用: 网络请求监听基类
 */
interface BRequestCallback {
    /**
     * 请求之前调用
     */
    fun beforeRequest()

    /**
     * @param msg 请求错误调用
     */
    fun requestError(throwable: Throwable?, baseHttpDto: BRequest)

    /**
     * 请求完成调用
     */
    fun requestComplete()

    /**
     * @param str 返回成功调用 返回数据
     */
    fun requestSuccess(baseResponse: BaseResponse, baseHttpDto: BRequest)

    /**
     * @param str 返回成功调用 返回数据
     */
    fun requestSuccess(body: String, baseHttpDto: BRequest)

}
