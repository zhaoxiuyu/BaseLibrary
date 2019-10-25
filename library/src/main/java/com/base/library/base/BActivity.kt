package com.base.library.base

import android.os.Bundle
import com.base.library.mvp.BPresenter

abstract class BActivity<T : BPresenter> : BaseActivity() {

    var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter?.let { lifecycle.addObserver(it) }
    }

}