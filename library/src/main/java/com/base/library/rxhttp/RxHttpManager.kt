package com.base.library.rxhttp

import com.base.library.BuildConfig
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.param.RxHttp
import java.io.File

object RxHttpManager {

    init {
        LogUtils.d("初始化 RxHttp ${BuildConfig.DEBUG}")
    }

    /**
     * true 能看到请求日志
     */
    fun initRxHttp() {
        RxHttp.setDebug(BuildConfig.DEBUG)
    }

    /**
     * 全局缓存设置
     */
    fun initRxHttpCache() {
        // 目录为 Android/data/{app包名目录}/cache/RxHttpCache
        val cacheDir = File(Utils.getApp().externalCacheDir, "RxHttpCache")
        // 目录,缓存大小10M,默认不缓存,且缓存永久有效
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024)
    }

    /**
     * 全局公共参数/请求头
     */
    fun initParamAssembly() {
        // 此方法在子线程中执行,每次请求的发起线程
        RxHttp.setOnParamAssembly {
            val method = it.method
            if (method.isGet) {

            } else if (method.isPost) {

            }
            // 公共参数
//            it.add("", "")

            // 公共请求头
//            it.addHeader("", "")
            return@setOnParamAssembly it
        }
    }

}