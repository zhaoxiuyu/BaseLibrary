package com.base.library.interfaces

import androidx.lifecycle.LifecycleOwner

/**
 * 通用的生命周期管理
 */
interface MyLifecycle : MyLifecycleObserver {

//    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
//    }

    override fun onCreate(owner: LifecycleOwner) {
    }

//    override fun onStart(owner: LifecycleOwner) {
//    }
//
//    override fun onResume(owner: LifecycleOwner) {
//    }
//
//    override fun onPause(owner: LifecycleOwner) {
//    }
//
//    override fun onStop(owner: LifecycleOwner) {
//    }

    override fun onDestroy(owner: LifecycleOwner) {
    }

}