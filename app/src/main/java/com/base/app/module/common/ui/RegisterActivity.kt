package com.base.app.module.common.ui

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.base.app.R
import com.base.library.mvp.ViewModelFactory
import com.base.app.module.common.presenter.RegisterViewModel
import com.base.library.base.MActivity
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : MActivity() {

    private var viewModel: RegisterViewModel? = null

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_login)
        viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory(this, RegisterViewModel::class.java)
            )
                .get(RegisterViewModel::class.java)

    }

    override fun initData() {
        but.setOnClickListener {
            viewModel?.getBanner()?.observe(this, Observer {
                tv.text = "$it"
            })
        }
    }

}