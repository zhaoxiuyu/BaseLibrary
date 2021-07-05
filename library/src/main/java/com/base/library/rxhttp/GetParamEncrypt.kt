package com.base.library.rxhttp

import okhttp3.HttpUrl
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.Method
import rxhttp.wrapper.param.NoBodyParam

/**
 * GetParam 请求加密
 * 你可以拿到所有的参数，然后进行加密，添加到请求头里
 */
@Param(methodName = "getParamEncrypt")
class GetParamEncrypt(url: String) : NoBodyParam(url, Method.GET) {

    override fun getHttpUrl(): HttpUrl {
//        val paramsBuilder = StringBuilder() //存储加密后的参数
//        for (pair in keyValuePairs) {
//            //这里遍历所有添加的参数，可对参数进行加密操作
//            val key = pair.key
//            val value = pair.value.toString()
//            //加密逻辑自己写
//        }
//        val simpleUrl = simpleUrl //拿到请求Url
//
//        //将加密后的参数和url组拼成HttpUrl对象并返回
//        return if (paramsBuilder.isEmpty())
//            simpleUrl.toHttpUrl()
//        else
//            "$simpleUrl?$paramsBuilder".toHttpUrl()
        return super.getHttpUrl()
    }

}