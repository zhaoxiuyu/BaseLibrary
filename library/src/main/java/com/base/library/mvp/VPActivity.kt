package com.base.library.mvp

import com.base.library.base.BActivity

/**
 * MVP 模式的基础 Activity
 */
abstract class VPActivity<T : VPPresenter> : BActivity() {
//    abstract class VPActivity : BActivity(), VPView {

    abstract fun createPresenter(): T

    var mPresenter: T? = null

    override fun initParadigm() {
        mPresenter = createPresenter()
        mPresenter?.let { lifecycle.addObserver(it) }
    }

    override fun registerObserve() {
    }

}