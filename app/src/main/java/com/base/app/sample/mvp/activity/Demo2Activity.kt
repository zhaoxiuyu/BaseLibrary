package com.base.app.sample.mvp.activity

import android.content.Intent
import android.os.Bundle
import com.base.app.databinding.ActivityDemo1Binding
import com.base.app.sample.mvp.contract.CommonContract
import com.base.app.sample.mvp.presenter.CommonPresenter
import com.base.library.mvp.VPActivity
import com.base.library.mvp.VPPresenter

/**
 * 作用: 使用案例,使用通用的P和V
 */
class Demo2Activity : VPActivity<ActivityDemo1Binding>(), CommonContract.View {

    private val mPresenter by lazy { CommonPresenter(this) }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun addObserverPresenter(): MutableList<VPPresenter> {
        return mutableListOf(mPresenter)
    }

    override fun initView() {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun responseSuccess(data: String) {
    }

    override fun responseError(msg: String) {
    }

}