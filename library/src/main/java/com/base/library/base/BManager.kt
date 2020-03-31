package com.base.library.base

import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object BManager {

    private lateinit var mServiceAPI: BServiceAPI
    private lateinit var mOkHttpClient: OkHttpClient

    init {
        initClient()
        initBaseUrl(url3) // 初始化 url 默认从MMKV中获取
    }

    /**
     * 初始化 OKHttp
     */
    private fun initClient() {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        clientBuilder.readTimeout(10, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        clientBuilder.retryOnConnectionFailure(false) // 是否重连
        clientBuilder.followRedirects(true) // 允许重定向

//        // https 支持
//        clientBuilder.hostnameVerifier { hostname, session ->
//            return@hostnameVerifier true
//        }

        // 添加日志打印
        val logging = HttpLoggingInterceptor("OkHttp")
        logging.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        logging.setColorLevel(Level.INFO)
        clientBuilder.addInterceptor(logging)

        mOkHttpClient = clientBuilder.build()
    }

    /**
     * 初始化 Retrofit
     */
    private fun initBaseUrl(host: String) {
        val retrofit = Retrofit.Builder().baseUrl(host).client(mOkHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create()) // 使用String
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava
            .build()
        mServiceAPI = retrofit.create(BServiceAPI::class.java)
    }

    fun getServiceAPI(): BServiceAPI {
        return mServiceAPI
    }



}