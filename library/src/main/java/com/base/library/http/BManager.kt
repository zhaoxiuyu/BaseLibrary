package com.base.library.http

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SDCardUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object BManager {

    //超时时间
    private const val TIME_OUT = 20.0

    //设缓存有效期为两天
    private const val CACHE_STALE_SEC = 60 * 60 * 24 * 2

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private const val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_SEC"

    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    private const val CACHE_CONTROL_NETWORK = "max-age=0"

    lateinit var mBaseHttpService: BService
    private lateinit var mOkHttpClient: OkHttpClient

    init {
        initOkHttpClient()
        initBaseUrl("") // todo
    }

    private fun initOkHttpClient() {
        val paths = SDCardUtils.getSDCardInfo()
        if (!paths.isNullOrEmpty()) {
            val cacheFile = File(paths[0].path, "HttpCache") // 指定缓存路径

            val cache = Cache(cacheFile, 1024 * 1024 * 50) // 指定缓存大小

//                val cacheControlInterceptor = Interceptor { chain ->
//                    var request = chain.request()
//
//                    LogUtils.d(request.toString())
//                    LogUtils.d(request.body()?.toString())
//
//                    if (!NetworkUtils.isConnected()) {
//                        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
//                        LogUtils.e("没有网络")
//                    }
//                    val originalResponse = chain.proceed(request)
//                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                    if (NetworkUtils.isConnected()) {
//                        val cacheControl = request.cacheControl().toString()
//                        originalResponse.newBuilder().header("Cache-Control", cacheControl)
//                            .removeHeader("Pragma").build()
//                    } else {
//                        originalResponse.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached,$CACHE_STALE_SEC")
//                            .removeHeader("Pragma").build()
//                    }
//                }

            val logging = HttpLoggingInterceptor("")
            logging.setColorLevel(Level.ALL)
            mOkHttpClient = OkHttpClient.Builder().cache(cache)
//                    .addNetworkInterceptor(cacheControlInterceptor)
                .addInterceptor(logging)
                .retryOnConnectionFailure(false)//是否重连
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS).build()
        }
    }

    fun initBaseUrl(host: String) {
        val retrofit = Retrofit.Builder().baseUrl(host).client(mOkHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        mBaseHttpService = retrofit.create(BService::class.java)
    }

    //根据网络状况获取缓存策略
    private fun getcacheControl(): String {
        return if (NetworkUtils.isConnected()) CACHE_CONTROL_NETWORK else CACHE_CONTROL_CACHE
    }

}