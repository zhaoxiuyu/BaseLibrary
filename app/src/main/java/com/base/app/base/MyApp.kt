package com.base.app.base

import androidx.lifecycle.ProcessLifecycleOwner
import com.base.app.http.OkHttpUtils
import com.base.library.base.AppLifecycleObserver
import com.base.library.base.BApplication
import com.base.library.okhttp.cookie.ICookieJar
import com.dylanc.activityresult.launcher.FileProviderUtils
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class MyApp : BApplication() {

    override fun onCreate() {
        super.onCreate()

        initMethod()
//        DoKit.Builder(this)
//            .productId("0f0894d53fe597a618cb4e0c31e2f536")
//            .build()

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())

        // ActivityResultLauncher 和 RxTool 的FileProvider冲突，https://github.com/DylanCaiCoding/ActivityResultLauncher/issues/2
        FileProviderUtils.authority = "$packageName.fileProvider"

    }

    private fun cookieManager() {
        // 手动管理 cookie
        val cookieJar = OkHttpUtils.mOkHttpClient.cookieJar as ICookieJar
        val httpUrl = "https://www.wanandroid.com/".toHttpUrlOrNull()

        // 手动添加 cookie
        val cookieStr = "JSESSIONID=6B1B904EC42DFFFFDB3AEA299DA13AC2; Path=/; Secure; HttpOnly"
        val cookie = httpUrl?.let { Cookie.parse(it, cookieStr) } //通过parse方法构造Cookie对象，也可以通过构造器构造
        cookieJar.saveCookie(httpUrl, cookie)
        // 手动读取cookie
        cookieJar.loadCookie(httpUrl)
        // 移除url对应的cookie
        cookieJar.removeCookie(httpUrl)
        // 移除所有cookie
        cookieJar.removeAllCookie()
    }

}