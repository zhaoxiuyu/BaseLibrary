package com.base.library.util

import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import com.base.library.interfaces.MyLifecycle

/**
 * 自动在onDestroy的时候移除Handler的消息，避免内存泄漏
 */
class HandlerLifecycle(private val owner: LifecycleOwner) : Handler(), MyLifecycle {

    init {
        addObserver()
    }

    private fun addObserver() {
        owner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        removeCallbacksAndMessages(null)
        owner.lifecycle.removeObserver(this)
    }

}