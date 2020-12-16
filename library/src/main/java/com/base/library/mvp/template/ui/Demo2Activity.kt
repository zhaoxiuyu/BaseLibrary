package com.base.library.mvp.template.ui

import android.content.Intent
import com.base.library.mvp.VPActivity
import com.base.library.mvp.template.contract.CommonContract
import com.base.library.mvp.template.presenter.CommonPresenter

/**
 * 作用: 使用案例,使用通用的P和V
 */
class Demo2Activity : VPActivity<CommonContract.Presenter>(), CommonContract.View {

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
    }

    override fun initData() {
        mPresenter = CommonPresenter(this)
    }

    override fun responseData(data: String) {

    }

}