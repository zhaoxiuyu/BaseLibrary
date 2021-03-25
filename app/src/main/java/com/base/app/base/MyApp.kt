package com.base.app.base

import com.base.app.BuildConfig
import com.base.library.base.BApplication
import com.base.library.util.CockroachUtil
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.didichuxing.doraemonkit.DoraemonKit
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : BApplication() {

    override fun onCreate() {
        super.onCreate()
        initMethod()
//        initCockroach()
        DoraemonKit.install(this, "0f0894d53fe597a618cb4e0c31e2f536")

        if (BuildConfig.DEBUG) {
            CrashUtils.init { LogUtils.e(it) } // 可以弹出错误提示框
        } else {
            initCockroach()
        }

    }

    /**
     * todo 不死异常拦截
     * handlerException内部建议手动try{ 异常处理逻辑 }catch(Throwable e){ }
     * 以防handlerException内部再次抛出异常，导致循环调用handlerException
     */
    private fun initCockroach() {
        CockroachUtil.install(object : CockroachUtil.ExceptionHandler {
            override fun handlerException(thread: Thread, throwable: Throwable, info: String) {
                try {
                    // 可以在这里上报bugly
                    LogUtils.e(info)
                } catch (e: Throwable) {
                }
            }
        })
    }

}