package com.base.library.mvp

import com.base.library.base.BFragment

abstract class VPFragment<T : VPPresenter> : BFragment() {
//abstract class VPFragment<T : VPPresenter> : BFragment(), VPView {

    abstract fun createPresenter(): T

    var mPresenter: T? = null

    override fun initParadigm() {
        mPresenter = createPresenter()
        mPresenter?.let { lifecycle.addObserver(it) }
    }

    override fun registerObserve() {
    }

}