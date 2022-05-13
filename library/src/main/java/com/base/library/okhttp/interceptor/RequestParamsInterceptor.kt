package com.base.library.okhttp.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 统一添加请求参数
 */
public class RequestParamsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // 所有请求添加请求头
//        request = request.newBuilder()
//            .addHeader("myHeader1", "myHeader1")
//            .build()

        // Form 表单 添加所有参数
//        if (request.body is FormBody) {
//            // 构造一个新的表单
//            val builder = FormBody.Builder()
//
//            // 将以前的参数添加
//            val body = request.body as FormBody
//            for (i in 0 until body.size) {
//                builder.add(body.encodedName(i), body.encodedValue(i))
//            }
//            // 追加新的参数
//            builder.add("myHeader2", "myHeader2-FormBody")
//            // 构建一个新的请求体
//            request = request.newBuilder().post(builder.build()).build()
//        }

        // Get 请求追加参数
//        if (request.method == "GET") {
//            val url = request.url
//            val newUrl = url.newBuilder()
//                .addEncodedQueryParameter("GET_Key1", "GET_Key1")
//                .build()
//            request = request.newBuilder().url(newUrl).build()
//        }

//        val body = request.body // 得到请求体
//        val buffer = Buffer() // 创建缓存
//        body?.writeTo(buffer) // 将请求体内容,写入缓存
//        val parameterStr = buffer.readUtf8() // 读取参数字符串
        // 如果是json串就解析 重新加参数 如果是字符串就进行修改 根据自己的业务逻辑来加

        // 对应请求头按照自己的传输方式 定义
//        val requestBody =
//            RequestBody.create("text/x-markdown; charset=utf-8".toMediaTypeOrNull(), parameterStr)
//        request = request.newBuilder().patch(requestBody).build()

        return chain.proceed(request)
    }

}