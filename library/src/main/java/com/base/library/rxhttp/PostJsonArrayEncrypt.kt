package com.base.library.rxhttp

import okhttp3.RequestBody
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.JsonArrayParam
import rxhttp.wrapper.param.Method

/**
 * 这个类没有生成addAll之类的方法，是个bug，等作者修复
 */
@Param(methodName = "postJsonArrayEncrypt")
class PostJsonArrayEncrypt(url: String) : JsonArrayParam(url, Method.POST) {

    override fun getRequestBody(): RequestBody {
        // 你可以拿到所有的参数，然后进行加密，添加到请求头里
        return super.getRequestBody()
    }

}