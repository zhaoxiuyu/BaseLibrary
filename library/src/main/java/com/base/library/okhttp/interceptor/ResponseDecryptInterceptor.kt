package com.base.library.okhttp.interceptor

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.nio.charset.Charset

/**
 * 对响应数据进行解密
 */
public class ResponseDecryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        if (response.isSuccessful) {
            val responseBody = response.body
            if (responseBody != null) {
                /*开始解密*/
                try {
                    val source = responseBody.source()
                    source.request(Long.MAX_VALUE)
                    val buffer = source.buffer()
                    var charset = Charset.forName("UTF-8")
                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }
                    val bodyString = buffer.clone().readString(charset)
                    val responseData = "这里调解密的方法"
                    /*将解密后的明文返回*/
                    val newResponseBody = ResponseBody.create(contentType, responseData.trim())
                    response = response.newBuilder().body(newResponseBody).build()
                } catch (e: Exception) {
                    /*异常说明解密失败 信息被篡改 直接返回 */
                    LogUtils.e("解密异常====》${e}")
                    return response
                }
            } else {
                LogUtils.i("响应体为空")
            }
        }
        return response
    }

}