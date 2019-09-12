package com.base.library.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.base.library.http.BRequest
import org.jetbrains.annotations.NotNull

/**
 * 作用: 基于MVP架构的Presenter 代理的基类
 */
interface BPresenter : LifecycleObserver {

    /**
     * 数据请求
     */
    fun getData(http: BRequest)

    /**
     * Activity 创建
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(@NotNull owner: LifecycleOwner)

    /**
     * Activity 运行
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(@NotNull owner: LifecycleOwner)

    /**
     * Activity 销毁
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(@NotNull owner: LifecycleOwner)

    /**
     * Activity 生命周期改变
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(@NotNull owner: LifecycleOwner, @NotNull event: Lifecycle.Event)

}
