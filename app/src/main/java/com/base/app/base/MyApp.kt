package com.base.app.base

import com.base.app.entitys.Login
import com.base.library.base.BApplication
import com.base.library.util.CockroachUtil
import com.blankj.utilcode.util.LogUtils
import com.didichuxing.doraemonkit.DoraemonKit
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : BApplication() {

    override fun onCreate() {
        super.onCreate()

        DoraemonKit.install(this, "0f0894d53fe597a618cb4e0c31e2f536")
//        initCockroach()
        initRxHttp()

        // 启动 Koin
        startKoin {
            // 传入Application,在注入的类中如果需要app对象的时候,可以直接使用
            androidContext(this@MyApp)
            // 被注入的模块对象
            modules(vmModule, framModule, factoryModule, singleModule)
        }

    }

    // 注入 ViewModule 对象
    private val vmModule = module {
//        viewModel { RegisterViewModel() }
//        viewModel { FunctionViewModel() }
    }

    // 注入 Fragment 对象
    private val framModule = module {
    }

    // 注入 普通类 对象
    private val factoryModule = module {
        factory { Login() }
    }

    // 注入 单例 对象
    private val singleModule = module {
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

}