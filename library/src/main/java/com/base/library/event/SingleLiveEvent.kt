package com.base.library.event

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 一个生命周期感知的可观察对象，在订阅后只发送新的更新，用于以下事件：navigation Snackbar 消息
 *
 * 这避免了事件的常见问题：在配置更改（如旋转）时，如果观察者处于活动状态，则可以发出更新。
 * 此LiveData仅在显式调用setValue（）或call（）时调用observable。
 *
 * 只有一个观察者会收到更改通知
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            LogUtils.d("已注册多个观察员，但只有一个观察员会收到更改通知.")
        }

        super.observe(owner, { t ->
            /**
             * 如果当前值等于预定值就更新成给定值
             */
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    /**
     * 用于T为空的情况，使呼叫更干净.
     */
    @MainThread
    fun call() {
        value = null
    }

}