package com.base.app.base

import com.base.app.entitys.Login
import com.base.app.module.common.viewmodel.RegisterViewModel
import com.base.app.module.newfunction.viewmodel.FunctionViewModel
import com.base.library.base.BApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : BApplication() {

    override fun onCreate() {
        super.onCreate()

        // 启动 Koin
        startKoin {
            // 传入Application,在注入的类中如果需要app对象的时候,可以直接使用
            androidContext(this@MyApp)
            // 被注入的模块对象
            modules(vmModule, framModule, factoryModule, singleModule, vmLibraryModule)
        }

    }

    // 注入 ViewModule 对象
    private val vmModule = module {
        viewModel { RegisterViewModel() }
        viewModel { FunctionViewModel() }
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

}