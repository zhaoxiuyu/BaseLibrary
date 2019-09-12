package com.base.library.template.ui

import android.content.Intent
import com.base.library.R
import com.base.library.base.BActivity
import com.blankj.utilcode.util.ToastUtils
import com.base.library.template.contract.Demo1Contract
import com.base.library.template.presenter.Demo1Presenter

/**
 * 作用: 使用案例,Activity使用自己定义的Contract和Presenter
 */
class Demo1Activity : BActivity<Demo1Contract.Presenter>(), Demo1Contract.View {

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.base_activity_test)
        mPresenter = Demo1Presenter(this)
    }

    override fun initData() {
    }

    override fun loginSuccess(request: String?) {
        ToastUtils.showLong("登录成功")
    }

    override fun loginError(msg: String?) {
        showDialog(msg, "", cancelListener = null)
    }

}