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
        // 你可以拿到所有的参数，然后进行加密，添加到请求头里
//        val pair = BConstant.postJsonEncrypt(params)
//        addHeader("sign", pair.first)
//        addHeader("sk", pair.second)
        return super.getRequestBody()
    }

}