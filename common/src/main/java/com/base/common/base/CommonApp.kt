package com.base.common.base

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.base.common.http.OkHttpUtils
import com.base.common.interfaces.AppAutoServiceInterface
import com.base.library.base.AppLifecycleObserver
import com.base.library.base.BApplication
import com.base.library.okhttp.cookie.ICookieJar
import com.dylanc.activityresult.launcher.FileProviderUtils
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.util.*

open class CommonApp : BApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 用于Android低版本设备（4.X及以下，SDK < 21）快速加载多DEX的解决方案
        // BoostMultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()

        initUtilcode()
        initLitePal()
        initStateView()
        initARouter()
        initCockroach()
        initModule(this)
        cookieManager()

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())

        // ActivityResultLauncher 和 RxTool 的FileProvider冲突，https://github.com/DylanCaiCoding/ActivityResultLauncher/issues/2
        FileProviderUtils.authority = "$packageName.fileProvider"

    }

    fun initModule(mContext: Context?) {
        val spi = ServiceLoader.load(AppAutoServiceInterface::class.java).toList()
        spi.forEach {
            it.onCreate(mContext)
        }
    }

    fun cookieManager() {
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