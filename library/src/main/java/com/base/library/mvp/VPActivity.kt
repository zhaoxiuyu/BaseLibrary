package com.base.library.mvp

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.base.library.base.BActivity
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 * MVP 模式的基础 Activity
 */
//abstract class VPActivity<T : VPPresenter> : BActivity() {
abstract class VPActivity<VB : ViewBinding> : BActivity(), VPView {

    lateinit var viewBinding: VB

    abstract fun addObserverPresenter(): MutableList<VPPresenter>

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        addObserverPresenter().forEach { lifecycle.addObserver(it) }
        super.onCreate(savedInstanceState)
    }

    override fun registerObserve() {
    }

    override fun loadingEvent(method: String, msg: String) {
        showLoading(msg)
    }

    override fun messageEvent(method: String, msg: String, finish: Boolean) {
        showMessage(msg, finish)
    }

    override fun dismissEvent(method: String) {
        dismissLoading()
    }

    override fun finishEvent() {
        finish()
    }

    override fun startActivityEvent() {
    }

    override fun otherEvent(content: String) {
    }

}