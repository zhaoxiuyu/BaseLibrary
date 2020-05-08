package com.base.library.http

import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object HttpManager {

    private lateinit var mServiceAPI: HttpServiceAPI
    private lateinit var mOkHttpClient: OkHttpClient

    init {
        initClient()
        initBaseUrl()
    }

    /**
     * 初始化 OKHttp
     */
    private fun initClient() {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        clientBuilder.readTimeout(10, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        clientBuilder.retryOnConnectionFailure(true) // 是否重连
        clientBuilder.followRedirects(true) // 允许重定向

        // 添加日志打印
        val logging = HttpLoggingInterceptor("OkHttp")
        logging.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        logging.setColorLevel(Level.INFO)
        clientBuilder.addInterceptor(logging)

        clientBuilder.addInterceptor(BaseUrlInterceptor())

        mOkHttpClient = clientBuilder.build()
    }

    /**
     * 初始化 Retrofit
     */
    private fun initBaseUrl() {
        val retrofit = Retrofit.Builder().baseUrl(HttpConstant.url3).client(mOkHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create()) // 使用 String
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用 RxJava
            .build()
        mServiceAPI = retrofit.create(HttpServiceAPI::class.java)
    }

    /**
     * 主要是根据Header判断是否重新设置BaseUrl
     */
    class BaseUrlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request() // 获取 request
            val oidUrl = request.url() // 获取原有的 HttpUrl 实例
            val builder = request.newBuilder() // 获取 request 的创建者 builder

            val header = request.headers("urlName") // 从 request 中获取 header
            if (header != null && header.size > 0) {
                // 如果有这个header，就将这个header删除，这个header只用做更换 BaseUrl 的标识
                builder.removeHeader("urlName")
                val headerValue = header[0] // 获得新的 BaseUrl
                LogUtils.d("headerValue = $headerValue")

                if ("default" != headerValue) {
                    val httpUrl = HttpUrl.parse(headerValue)
                    val newUrl = if (httpUrl != null) {
                        oidUrl.newBuilder()
                            .scheme(httpUrl.scheme())
                            .host(httpUrl.host())
                            .port(httpUrl.port()).build()
                    } else {
                        oidUrl
                    }
                    return chain.proceed(builder.url(newUrl).build())
                }
            }
            return chain.proceed(request)
        }
    }

    /**
     * 获取 service
     */
    fun getServiceAPI(): HttpServiceAPI {
        return mServiceAPI
    }

}