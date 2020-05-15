package com.base.library.base

import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.base.library.BuildConfig
import com.base.library.R
import com.base.library.mvvm.template.viewmodel.Demo3ViewModel
import com.base.library.util.CockroachUtil
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonInterceptor
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonWeakNetworkInterceptor
import com.lxj.xpopup.XPopup
import com.lzy.okgo.OkGo
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.litepal.LitePal
import java.util.logging.Level

/**
 * 作用: 程序的入口
 */
open class BApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        utilcode()
//        RxTool.init(this)
        LitePal.initialize(this)
        DoraemonKit.install(this, "0f0894d53fe597a618cb4e0c31e2f536")
        XPopup.setPrimaryColor(ContextCompat.getColor(this, R.color.base_sb_pressed))

        if (BuildConfig.DEBUG) {
            initHttp(getLoggingInterceptor())
        } else {
            initHttp(null)
            initCockroach()
        }

    }

    // 注入 ViewModule 对象
    val vmLibraryModule = module {
        viewModel { Demo3ViewModel() }
    }

    /**
     * todo
     * 不死异常拦截
     * handlerException内部建议手动try{ 异常处理逻辑 }catch(Throwable e){ }
     * 以防handlerException内部再次抛出异常，导致循环调用handlerException
     */
    private fun initCockroach() {
        CockroachUtil.install(object : CockroachUtil.ExceptionHandler {
            override fun handlerException(thread: Thread, throwable: Throwable, info: String) {
                try {
                    LogUtils.e(info)
                } catch (e: Throwable) {
                }
            }
        })
    }

    /**
     * 网络请求
     */
    private fun initHttp(logging: HttpLoggingInterceptor?) {
        //信任所有证书,不安全有风险
        val sslParams1 = HttpsUtils.getSslSocketFactory()

        val builder = OkHttpClient.Builder()
        logging?.let { builder.addInterceptor(it) }//打印日志
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager)
        builder.addNetworkInterceptor(DoraemonWeakNetworkInterceptor()) //用于模拟弱网的拦截器
        builder.addInterceptor(DoraemonInterceptor()).build()  //网络请求监控的拦截器

        //重连次数,默认三次,最差的情况4次(一次原始请求,三次重连请求),不需要可以设置为0
        OkGo.getInstance().init(this).setOkHttpClient(builder.build()).retryCount = 0
    }

    // 获取打印 日志拦截器
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor.setColorLevel(Level.INFO)
        return loggingInterceptor
    }

    /**
     * 初始化打印日志
     */
    private fun utilcode() {
        Utils.init(this)
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG)//总开关
            .setConsoleSwitch(BuildConfig.DEBUG)//控制台开关
            .setLogHeadSwitch(BuildConfig.DEBUG)//控制台开关
            .setGlobalTag("IZXY")//全局 Tag
            .setFilePrefix("AndroidUtilCode") // Log 文件前缀
            .setBorderSwitch(BuildConfig.DEBUG)//边框开关
            .stackDeep = 1 //栈深度
    }

}
