package com.base.app.module.newfunction.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.base.app.R
import com.base.app.module.newfunction.viewmodel.FunctionViewModel
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.item_vp2.*

class Fragment2 : VMFragment<FunctionViewModel>() {

    override fun initArgs(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
        setContentView(R.layout.item_vp2)

    }

    override fun initData() {
        LogUtils.d("fragment = ${getSharedViewModel()}")

        itemTv.text = "Fragment2"
        itemTv.setOnClickListener {
            getSharedViewModel()?.getEventLiveData()?.value = Triple("Fragment2", true, "测试")
        }
        getSharedViewModel()?.getEventLiveData()?.observe(this, Observer {
            ToastUtils.showLong("我看看收到没")
        })
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment2 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment2 + onResume")
    }

}