package com.base.common.sample.mvp.activity

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.common.constant.CommonARoute
import com.base.common.databinding.CommonSampleActivityBinding
import com.base.common.sample.mvp.contract.CommonContract
import com.base.common.sample.mvp.presenter.CommonPresenter
import com.base.library.mvp.VPActivity
import com.base.library.mvp.VPPresenter

/**
 * 作用: 使用案例,使用通用的P和V
 */

@Route(path = CommonARoute.Demo2Activity)
class Demo2Activity : VPActivity<CommonSampleActivityBinding>(), CommonContract.View {

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