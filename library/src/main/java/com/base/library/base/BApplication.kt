package com.base.library.base

import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.base.library.BuildConfig
import com.base.library.R
import com.base.library.rxhttp.RxHttpDecoder
import com.base.library.rxhttp.RxHttpParamAssembly
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.lxj.xpopup.XPopup
import org.litepal.LitePal
import rxhttp.RxHttpPlugins
import rxhttpLibrary.RxHttp
import java.io.File

/**
 * 作用: 程序的入口
 */
open class BApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        utilcode()
        LitePal.initialize(this)
        XPopup.setPrimaryColor(ContextCompat.getColor(this, R.color.color_03A9F4))

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
        // 初始化
        RxHttp.setDebug(BuildConfig.DEBUG)
        // 为所有的请求添加公共参数/请求头
        RxHttp.setOnParamAssembly(RxHttpParamAssembly())
        // 设置数据解密/解码器
        RxHttp.setResultDecoder(RxHttpDecoder())
        // 目录为 Android/data/{app包名目录}/cache/RxHttpCache
        val cacheDir = File(Utils.getApp().externalCacheDir, "RxHttpCache")
        // 目录,缓存大小10M,默认不缓存,且缓存永久有效
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024)
    }

}
