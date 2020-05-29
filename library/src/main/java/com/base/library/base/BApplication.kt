package com.base.library.base

import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.base.library.BuildConfig
import com.base.library.R
import com.base.library.rxhttp.RxHttpManager
import com.base.library.util.CockroachUtil
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit
import com.lxj.xpopup.XPopup
import org.litepal.LitePal

/**
 * 作用: 程序的入口
 */
open class BApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        utilcode()
        LitePal.initialize(this)
        DoraemonKit.install(this, "0f0894d53fe597a618cb4e0c31e2f536")
        XPopup.setPrimaryColor(ContextCompat.getColor(this, R.color.base_sb_pressed))

        RxHttpManager.initRxHttp()
        RxHttpManager.initRxHttpCache()
        RxHttpManager.initParamAssembly()

        initCockroach()

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
