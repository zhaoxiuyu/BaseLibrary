package com.base.app.module.newfunction.ui

import android.os.Bundle
import com.base.app.R
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.item_vp2.*

class Fragment3 : VMFragment() {

    override fun initArgs(bundle: Bundle?) = null

    override fun getContentView() = R.layout.item_vp2

    override fun initData(bundle: Bundle?) {
        itemTv.text = "Fragment3"
        itemTv.setOnClickListener {
        }
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment3 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment3 + onResume")
    }

}