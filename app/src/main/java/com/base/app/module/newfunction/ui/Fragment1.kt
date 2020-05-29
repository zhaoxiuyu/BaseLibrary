package com.base.app.module.newfunction.ui

import android.os.Bundle
import com.base.app.R
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.item_vp2.*

class Fragment1 : VMFragment() {

    override fun initArgs(bundle: Bundle?) = null
    override fun initView(bundle: Bundle?) {
        setContentView(R.layout.item_vp2)
    }

    override fun initData() {

        itemTv.text = "Fragment1"
        itemTv.setOnClickListener {
        }
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment1 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment1 + onResume")
    }

}