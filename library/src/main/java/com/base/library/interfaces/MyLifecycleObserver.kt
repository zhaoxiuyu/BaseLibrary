package com.base.library.interfaces

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 通用的生命周期管理
 */
interface MyLifecycleObserver : LifecycleObserver {

//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner)

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onStart(owner: LifecycleOwner)
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun onResume(owner: LifecycleOwner)
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    fun onPause(owner: LifecycleOwner)
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun onStop(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner)

}