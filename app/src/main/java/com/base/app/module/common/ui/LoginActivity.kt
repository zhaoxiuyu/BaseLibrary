package com.base.app.module.common.ui

import android.content.Intent
import com.base.app.R
import com.base.app.module.common.contract.LoginContract
import com.base.app.module.common.presenter.LoginPresenter
import com.base.library.base.BActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BActivity<LoginPresenter>(), LoginContract.View {

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_login)
        mPresenter = LoginPresenter(this)
    }

    override fun initData() {
        but.setOnClickListener {
            mPresenter?.login("")
        }
    }

    override fun loginSuccess(request: String?) {
        tv.text = "$request"
    }

    override fun loginError(msg: String?) {
        tv.text = "$msg"
    }

}