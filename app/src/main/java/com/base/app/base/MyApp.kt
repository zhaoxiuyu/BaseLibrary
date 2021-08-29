package com.base.app.base

import androidx.lifecycle.ProcessLifecycleOwner
import com.base.library.base.AppLifecycleObserver
import com.base.library.base.BApplication
import com.dylanc.activityresult.launcher.FileProviderUtils

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

}