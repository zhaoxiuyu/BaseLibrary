package com.base.common.http

import com.base.library.okhttp.cookie.CookieManager
import com.base.library.okhttp.interceptor.log.LogInterceptor
import com.blankj.utilcode.util.Utils
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object OkHttpUtils {

    val appService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { wanAndroidCreate(OkHttpApi::class.java) }

    val appServiceRx by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { wanAndroidCreateRx(OkHttpApi::class.java) }

    private val wanAndroidRetrofitRx by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(OkHttpContracts.BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private val wanAndroidRetrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(OkHttpContracts.BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // OkHttpServiceCreate.wanAndroidCreate(OkHttpApi::class.java)
    private fun <T> wanAndroidCreate(serviceClass: Class<T>): T =
        wanAndroidRetrofit.create(serviceClass)

    private fun <T> wanAndroidCreateRx(serviceClass: Class<T>): T =
        wanAndroidRetrofitRx.create(serviceClass)

    // OkHttpServiceCreate.wanAndroidCreate<OkHttpApi>()
    private inline fun <reified T> wanAndroidCreate(): T = wanAndroidCreate(T::class.java)

    val mOkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient().newBuilder()
            // 读取超时
            .readTimeout(2000, TimeUnit.MILLISECONDS)
            // 写超时
            .writeTimeout(2000, TimeUnit.MILLISECONDS)
            // 连接超时
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            // 设置回收 HTTP 和 HTTPS 连接的连接池
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            // 统一添加请求参数
//            .addInterceptor(RequestParamsInterceptor())
            // 请求数据加密
//            .addInterceptor(RequestEncryptInterceptor())
            // 日志输出
            .addInterceptor(LogInterceptor())
            // 响应数据解密
//            .addInterceptor(ResponseDecryptInterceptor())
            // 持久化存储Cookie
            .cookieJar(CookieManager(File(Utils.getApp().externalCacheDir, "LibraryCookie")))
            .build()
    }

}