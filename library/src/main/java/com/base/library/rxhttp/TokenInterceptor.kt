package com.base.library.rxhttp

import com.base.library.base.BConstant
import com.base.library.util.MMKVUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.parse.SimpleParser
import java.io.IOException

/**
 * token 失效，自动刷新 token，然后再次发送请求，用户无感知
 */
class TokenInterceptor : Interceptor {

    //token刷新时间
    var SESSION_KEY_REFRESH_TIME: Long = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)
        val code = originalResponse.header("token_code")

        // token 失效，1、这里根据自己的业务需求写判断条件
        if ("-1" == code) {
//            return handleTokenInvalid(chain, request)
        }
        return originalResponse
    }

    // 处理token失效的问题
//    fun handleTokenInvalid(chain: Interceptor.Chain, request: Request): Response {
//        val mapParam = HashMap<String, String>()
//        val body = request.body
//        if (body is FormBody) {
//            for (i in 0 until body.size) {
//                //2、保存参数
//                mapParam[body.name(i)] = body.value(i)
//            }
//        }
//
//        //同步刷新token
//        //3、发请求前需要add("request_time",System.currentTimeMillis())
//        val requestTime = mapParam.get("request_time")
//        val success: Boolean = refreshToken(requestTime)
//
//        //刷新成功，重新签名
//        val newRequest = if (success) {
//            //4、拿到最新的token,重新发起请求
//            mapParam["token"] = MMKVUtils.getStr(BConstant.LibraryToken)
//
//            RxHttp.postForm(request.url.toString())
//                .addAll(mapParam) //添加参数
//                .buildRequest();
//        } else request
//
//        return chain.proceed(newRequest)
//    }

    private fun refreshToken(value: String?): Boolean {
        var requestTime: Long = 0
        try {
            requestTime = value.toString().toInt().toLong()
        } catch (ignore: Exception) {
        }
        //请求时间小于token刷新时间，说明token已经刷新，则无需再次刷新
        if (requestTime <= SESSION_KEY_REFRESH_TIME) return true;

        synchronized(this) {
            //再次判断是否已经刷新
            if (requestTime <= SESSION_KEY_REFRESH_TIME) return true
            return try {
                // 获取到最新的token，这里需要同步请求token,千万不能异步
                // 5、根据自己的业务修改
                val token = RxHttp.postForm("/refreshToken/...")
                    .execute(SimpleParser[String::class.java])
                SESSION_KEY_REFRESH_TIME = System.currentTimeMillis() / 1000
                //保存最新的token
                MMKVUtils.put(BConstant.LibraryToken, token)
                true
            } catch (e: IOException) {
                false
            }
        }
    }

}