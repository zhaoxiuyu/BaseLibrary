package com.base.app.module.common.ui

import android.os.Bundle
import com.base.app.R
import com.base.library.base.VPFragment
import com.base.library.mvp.VPPresenter
import com.base.library.mvp._VPPresenter
import com.base.library.mvp._VPView
import com.blankj.utilcode.util.LogUtils
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainFragment : VPFragment<VPPresenter>(), _VPView {

    override fun initArgs(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
        setContentView(R.layout.fragment_main)
        mPresenter = _VPPresenter(this)
    }

    override fun initData() {
        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe {
                LogUtils.d("$it")
            }
    }

    override fun bindData(any: Any) {
    }

    override fun bindError(string: String) {
    }
}