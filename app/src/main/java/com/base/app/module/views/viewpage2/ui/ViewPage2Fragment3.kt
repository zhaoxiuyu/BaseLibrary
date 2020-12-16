package com.base.app.module.views.viewpage2.ui

import android.os.Bundle
import com.base.app.databinding.Fragment123Viewpage2Binding
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.LogUtils

class ViewPage2Fragment3 : VMFragment() {

    private val mBind by lazy { Fragment123Viewpage2Binding.inflate(layoutInflater) }

    override fun initArgs(bundle: Bundle?) = null

    override fun initView() = mBind.root

    override fun initData(bundle: Bundle?) {
        mBind.itemTv.text = "Fragment3"
        mBind.itemTv.setOnClickListener {
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