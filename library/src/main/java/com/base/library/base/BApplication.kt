package com.base.library.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.base.library.BuildConfig
import com.base.library.util.CockroachUtil
import com.base.library.view.loadinghelper.EmptyHelperAdapter
import com.base.library.view.loadinghelper.ErrorHelperAdapter
import com.base.library.view.loadinghelper.LoadingHelperAdapter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.bytedance.boost_multidex.BoostMultiDex
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import org.litepal.LitePal

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

//        if (BuildConfig.DEBUG) {
////            CrashUtils.init { LogUtils.e(it) } // 可以弹出错误提示框
//        } else {
            initCockroach()
//        }

        // 工具类
        initUtilcode()

        // 数据库
        LitePal.initialize(this)

        // 解耦APP中的状态布局
        LoadingStateView.setViewDelegatePool {
            this.register(ViewType.LOADING, LoadingHelperAdapter())
            this.register(ViewType.ERROR, ErrorHelperAdapter())
            this.register(ViewType.EMPTY, EmptyHelperAdapter())
        }

        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

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
     * todo 不死异常拦截
     * handlerException内部建议手动try{ 异常处理逻辑 }catch(Throwable e){ }
     * 以防handlerException内部再次抛出异常，导致循环调用handlerException
     */
    open fun initCockroach() {
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
