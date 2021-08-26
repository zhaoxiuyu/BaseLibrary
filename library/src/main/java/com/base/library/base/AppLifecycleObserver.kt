package com.base.library.base

import androidx.lifecycle.LifecycleOwner
import com.base.library.interfaces.MyLifecycle
import com.blankj.utilcode.util.LogUtils

/**
 * 监听应用生命周期，APP 是否在前台，在 Application 里面初始化：
 * ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
 */
class AppLifecycleObserver : MyLifecycle {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        LogUtils.d("应用在前台")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        LogUtils.d("应用在后台")
    }

}