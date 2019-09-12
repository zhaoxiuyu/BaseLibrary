package com.base.library.base

import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.base.library.BuildConfig
import com.base.library.R
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit
import com.lxj.xpopup.XPopup
import com.lzy.okgo.OkGo
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.vondear.rxtool.RxTool
import okhttp3.OkHttpClient
import java.util.logging.Level

/**
 * 作用: 程序的入口
 */
class BApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        val startTime = System.currentTimeMillis()//获取开始时间

        DoraemonKit.install(this)
        initAndroidUtilCode()
        RxTool.init(this)
        initHttp()

        //XPopup主题颜色
        XPopup.setPrimaryColor(ContextCompat.getColor(this, R.color.base_sb_pressed))

        LogUtils.d("初始化耗时 : ${System.currentTimeMillis() - startTime}")
    }

    /**
     * 网络请求
     */
    private fun initHttp() {
        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor.setColorLevel(Level.INFO)

        //信任所有证书,不安全有风险
        val sslParams1 = HttpsUtils.getSslSocketFactory()

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)//打印日志
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager)

        //重连次数,默认三次,最差的情况4次(一次原始请求,三次重连请求),不需要可以设置为0
        OkGo.getInstance().init(this).setOkHttpClient(builder.build()).retryCount = 0
    }

    /**
     * 初始化打印日志
     */
    private fun initAndroidUtilCode() {
        Utils.init(this)
        CrashUtils.init { crashInfo, e -> LogUtils.e(crashInfo) }
        LogUtils.getConfig().setLogSwitch(BuildConfig.IS_DEBUG)//总开关
            .setConsoleSwitch(BuildConfig.IS_DEBUG)//控制台开关
            .setGlobalTag("IZXY")//全局 Tag
            .setFilePrefix("AndroidUtilCode") // Log 文件前缀
            .setBorderSwitch(BuildConfig.IS_DEBUG)//边框开关
            .stackDeep = 1 //栈深度
    }

}
