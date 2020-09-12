package com.base.library.rxhttp

import okhttp3.RequestBody
import rxhttp.wrapper.annotation.Param
import rxhttp.wrapper.param.FormParam
import rxhttp.wrapper.param.Method

/**
 * PostForm 请求加密
 * 此类中自己声明的所有public方法(构造方法除外)都会在RxHttp$PostEncryptFormParam类中一一生成，
 * 并一一对应调用。如: RxHttp$PostEncryptFormParam.test(int,int)方法内部会调用本类的test(int,int)方法
 */
@Param(methodName = "postFormEncrypt")
class PostFormEncrypt(url: String) : FormParam(url, Method.POST) {

    override fun getRequestBody(): RequestBody {
        // 你可以拿到所有的参数，然后进行加密，添加到请求头里
//        val pair = BConstant.postFormEncrypt(keyValuePairs)
//        addHeader("sign", pair.first)
//        addHeader("sk", pair.second)
        return super.getRequestBody()
    }

    //此方法会在
    fun test2(a: Long, b: Float): PostFormEncrypt? {
        return this
    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

}