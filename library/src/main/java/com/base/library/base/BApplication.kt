package com.base.library.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.base.library.BuildConfig
import com.base.library.rxhttp.RxHttpDecoder
import com.base.library.rxhttp.RxHttpParamAssembly
import com.base.library.view.loadinghelper.EmptyHelperAdapter
import com.base.library.view.loadinghelper.ErrorHelperAdapter
import com.base.library.view.loadinghelper.LoadingHelperAdapter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.bytedance.boost_multidex.BoostMultiDex
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType
import com.hjq.gson.factory.GsonFactory
import okhttp3.OkHttpClient
import org.litepal.LitePal
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.callback.Function
import rxhttp.wrapper.callback.IConverter
import rxhttp.wrapper.converter.GsonConverter
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.Param
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier


/**
 * 作用: 程序的入口
 */
open class BApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 用于Android低版本设备（4.X及以下，SDK < 21）快速加载多DEX的解决方案
        BoostMultiDex.install(base)
    }

    fun initMethod() {
        // 工具类
        initUtilcode()

        // 数据库
        LitePal.initialize(this)

        // 解耦APP中的状态布局
        LoadingHelper.setDefaultAdapterPool {
            this.register(ViewType.LOADING, LoadingHelperAdapter())
            this.register(ViewType.ERROR, ErrorHelperAdapter())
            this.register(ViewType.EMPTY, EmptyHelperAdapter())
        }

        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

        // 网络请求
        initRxHttp()
    }

    /**
     * 初始化打印日志
     */
    open fun initUtilcode() {
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
        // 目录为 Android/data/{app包名目录}/cache/RxHttpCache
        val cacheDir = File(Utils.getApp().externalCacheDir, "RxHttpCache")

        RxHttpPlugins.init(getOkHttpClient())
            .setDebug(BuildConfig.DEBUG, true)
            .setCache(cacheDir, maxSize(), CacheMode.ONLY_NETWORK, -1)
            //设置一些key，不参与cacheKey的组拼
//            .setExcludeCacheKeys()
            .setResultDecoder(getResultDecoder())
            //设置全局的转换器
            .setConverter(getConverter())
            // 为所有的请求添加公共参数/请求头
            .setOnParamAssembly(getOnParamAssembly())
    }

    /**
     * OkHttpClient
     */
    open fun getOkHttpClient(): OkHttpClient {
        val sslParams = HttpsUtils.getSslSocketFactory()
        return OkHttpClient.Builder()
            .cookieJar(CookieStore())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) // 添加信任证书
            .hostnameVerifier(HostnameVerifier { _, _ -> true }) // 忽略 host 验证
            .build()
    }

    /**
     * 默认最大缓存的大小为10M
     */
    open fun maxSize(): Long {
        return 10 * 1024 * 1024
    }

    /**
     * 设置数据解密/解码器
     */
    open fun getResultDecoder(): Function<String, String> {
        return RxHttpDecoder()
    }

    /**
     * 为所有的请求添加公共参数/请求头
     */
    open fun getOnParamAssembly(): Function<Param<*>, Param<*>> {
        return RxHttpParamAssembly()
    }

    /**
     * 设置全局的转换器
     * 这里的 gson 实体对象使用GsonFactory,因为里面的容错机制比较完善
     */
    open fun getConverter(): IConverter {
        return GsonConverter.create(GsonFactory.getSingletonGson())
    }

}
