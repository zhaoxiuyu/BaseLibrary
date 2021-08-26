package com.base.module.function.mvp.ui

import android.content.Intent
import android.os.Bundle
import com.base.library.base.BConstant
import com.base.library.mvp.VPActivity
import com.base.module.function.mvp.contract.CommonContract
import com.base.module.function.mvp.presenter.CommonPresenter
import rxhttp.wrapper.param.RxHttp

/**
 * 作用: 使用案例,使用通用的P和V
 */
class Demo2Activity : VPActivity<CommonContract.Presenter>(), CommonContract.View {

    override fun createPresenter(): CommonContract.Presenter {
        return CommonPresenter(this)
    }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    /**
     * 获取公众号列表
     */
    private fun getChapters() {
        val mRxHttp = RxHttp.getParamEncrypt(BConstant.chapters).setDomainTowanandroidIfAbsent()
        mPresenter?.requestData(mRxHttp)
    }

    override fun responseSuccess(data: String) {

    }

    override fun responseError(msg: String) {

    }

}