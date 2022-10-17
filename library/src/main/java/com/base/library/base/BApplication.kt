package com.base.library.base

//import com.bytedance.boost_multidex.BoostMultiDex
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.base.library.BuildConfig
import com.base.library.util.CockroachUtil
import com.base.library.view.loadingstateview.EmptyViewDelegate
import com.base.library.view.loadingstateview.ErrorViewDelegate
import com.base.library.view.loadingstateview.LoadingViewDelegate
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.dylanc.loadingstateview.LoadingStateView
import org.litepal.LitePal

/**
 * 作用: 程序的入口
 */
open class BApplication : MultiDexApplication() {

    // 数据库
    open fun initLitePal() {
        LitePal.initialize(this)
    }

    // 解耦APP中的状态布局
    open fun initStateView() {
        LoadingStateView.setViewDelegatePool {
            register(LoadingViewDelegate(), ErrorViewDelegate(), EmptyViewDelegate())
        }
    }

    open fun initARouter() {
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
