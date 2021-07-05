package com.base.library.rxhttp

import okhttp3.RequestBody
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.JsonParam
import rxhttp.wrapper.param.Method

/**
 * PostJson 请求加密
 */
@Param(methodName = "postJsonEncrypt")
class PostJsonEncrypt(url: String) : JsonParam(url, Method.POST) {

    override fun getRequestBody(): RequestBody {
        // 获取到所有通过add系列方法添加的参数
        // val mBodyParam = bodyParam
        // 获取到所有通过addQuery系列方法添加的参数
        // val mQueryParam = queryParam
        // 根据上面拿到的参数，自行实现加密逻辑
        // val encryptStr = "加密后的字符串"
        // 发送加密后的字符串
        // return RequestBody.create(null, encryptStr)
        return super.getRequestBody()
    }

}