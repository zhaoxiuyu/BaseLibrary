package com.base.library.base

import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.base.library.BuildConfig
import com.base.library.R
import com.base.library.custom.GlobalAdapter
import com.base.library.rxhttp.RxHttpDecoder
import com.base.library.rxhttp.RxHttpParamAssembly
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.XPopup
import okhttp3.OkHttpClient
import org.litepal.LitePal
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * 作用: 程序的入口
 */
open class BApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        utilcode()
        LitePal.initialize(this)
        XPopup.setPrimaryColor(ContextCompat.getColor(this, R.color.color_03A9F4))

        Gloading.debug(true)
        Gloading.initDefault(GlobalAdapter())
    }

    /**
     * 初始化打印日志
     */
    open fun utilcode() {
        Utils.init(this)
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG)//总开关
            .setConsoleSwitch(BuildConfig.DEBUG)//控制台开关
            .setLogHeadSwitch(BuildConfig.DEBUG)//控制台开关
            .setGlobalTag("IZXY")//全局 Tag
            .setFilePrefix("AndroidUtilCode") // Log 文件前缀
            .setBorderSwitch(BuildConfig.DEBUG)//边框开关
            .stackDeep = 1 //栈深度
    }

    /**
     * 初始化R下Http
     */
    open fun initRxHttp() {
        val sslParams = HttpsUtils.getSslSocketFactory()
        val client = OkHttpClient.Builder()
            .cookieJar(CookieStore())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) // 添加信任证书
            .hostnameVerifier(HostnameVerifier { _, _ -> true }) // 忽略 host 验证
            .build()

        // 初始化
        RxHttp.init(client, BuildConfig.DEBUG)
        // DEBUG 模式，分段打印内容
        RxHttp.setDebug(BuildConfig.DEBUG, true)
        // 为所有的请求添加公共参数/请求头
        RxHttp.setOnParamAssembly(RxHttpParamAssembly())
        // 设置数据解密/解码器
        RxHttp.setResultDecoder(RxHttpDecoder())
        // 目录为 Android/data/{app包名目录}/cache/RxHttpCache
        val cacheDir = File(Utils.getApp().externalCacheDir, "RxHttpCache")
        // 目录,缓存10M 超过10M根据LRU算法自动清除最近最少使用缓存,默认不缓存,且缓存永久有效
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.ONLY_NETWORK, -1)
    }

}
