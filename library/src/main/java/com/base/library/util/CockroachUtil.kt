package com.base.library.util

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ThrowableUtils
import com.blankj.utilcode.util.TimeUtils

/**
 * 异常拦截
 */
object CockroachUtil {

    private var sExceptionHandler: ExceptionHandler? = null
    private var sUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    private var sInstalled = false//标记位，避免重复安装卸载

    /**
     * 当主线程或子线程抛出异常时会调用
     * exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     */
    fun install(exceptionHandler: ExceptionHandler) {
        if (sInstalled) return
        sInstalled = true
        sExceptionHandler = exceptionHandler

        Handler(Looper.getMainLooper()).post(Runnable {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    //   Binder.clearCallingIdentity();
                    if (e is QuitCockroachException) return@Runnable
                    sExceptionHandler?.handlerException(Looper.getMainLooper().thread, e, getInfo(e))
                }
            }
        })
    }

    fun uninstall() {
        if (!sInstalled) return
        sInstalled = false
        sExceptionHandler = null
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler)
        Handler(Looper.getMainLooper()).post {
            //主线程抛出异常，迫使 while (true) {}结束
            throw QuitCockroachException("Quit CockroachUtil.....")
        }
    }

    private fun getInfo(e: Throwable): String {
        val time = TimeUtils.getNowString()
        val sb = StringBuilder()
        val head = "************* Log Head ****************" +
                "\nTime Of Crash      : " + time +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nApp VersionName    : " + AppUtils.getAppVersionName() +
                "\nApp VersionCode    : " + AppUtils.getAppVersionCode() +
                "\n************* Log Head ****************\n\n"
        sb.append(head).append(ThrowableUtils.getFullStackTrace(e))
        return sb.toString()
    }

    interface ExceptionHandler {
        fun handlerException(thread: Thread, throwable: Throwable, info: String)
    }

    class QuitCockroachException(message: String) : RuntimeException(message)

}