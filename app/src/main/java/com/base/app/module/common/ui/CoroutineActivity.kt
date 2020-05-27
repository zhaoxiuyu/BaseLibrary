package com.base.app.module.common.ui

import android.content.Intent
import androidx.lifecycle.Observer
import com.base.app.R
import com.base.app.module.common.viewmodel.RegisterViewModel
import com.base.library.entitys.response.Banner
import com.base.library.mvvm.core.VMActivity
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ShellUtils
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoroutineActivity : VMActivity() {

    private val vm by viewModel<RegisterViewModel>()

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_coroutine)
    }

    override fun initData() {
        vm.liveBanner.observe(this, Observer {
            it.handler(object : OnCallback<List<Banner>>() {})
        })

        but1.setOnClickListener {
            ShellUtils.execCmd("setprop service.adb.tcp.port 5555", false)
        }
        but3.setOnClickListener {
            ShellUtils.execCmd("start adbd", false)
            tv.text = NetworkUtils.getIPAddress(true)
        }

    }

}

