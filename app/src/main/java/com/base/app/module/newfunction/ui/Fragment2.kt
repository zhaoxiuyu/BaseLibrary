package com.base.app.module.newfunction.ui

import android.os.Bundle
import com.base.app.R
import com.base.app.module.newfunction.viewmodel.FunctionViewModel
import com.base.library.base.VMFragment
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.item_vp2.*

class Fragment2 : VMFragment<FunctionViewModel>() {

    override fun initArgs(bundle: Bundle?) {
    }

    override fun initView(bundle: Bundle?) {
        setContentView(R.layout.item_vp2)

    }

    override fun initData() {
        itemTv.text = "Fragment2"
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