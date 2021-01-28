package com.base.module.function.mvp.ui

import android.content.Intent
import android.os.Bundle
import com.base.library.mvp.VPActivity
import com.base.module.function.mvp.contract.CommonContract
import com.base.module.function.mvp.presenter.CommonPresenter

/**
 * 作用: 使用案例,使用通用的P和V
 */
class Demo2Activity : VPActivity(), CommonContract.View {
//class Demo2Activity : VPActivity<CommonContract.Presenter>(), CommonContract.View {

    private val mPresenter by lazy {
        CommonPresenter(
            this
        )
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
    }

    override fun initData(savedInstanceState: Bundle?) {
        lifecycle.addObserver(mPresenter)
    }

    override fun responseData(data: String) {
    }

}