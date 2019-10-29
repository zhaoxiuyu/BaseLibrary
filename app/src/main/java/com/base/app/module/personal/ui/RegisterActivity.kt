package com.base.app.module.personal.ui

import android.content.Intent
import com.base.app.R
import com.base.app.module.personal.viewmodel.RegisterViewModel
import com.base.app.mvvm.MActivity
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : MActivity<RegisterViewModel>() {

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_login)

    }

    override fun initData() {
        but.setOnClickListener {
        }
    }

}