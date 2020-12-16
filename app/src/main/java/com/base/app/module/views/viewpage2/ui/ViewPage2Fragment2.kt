package com.base.app.module.views.viewpage2.ui

import android.os.Bundle
import android.view.View
import com.base.app.base.MyConstant
import com.base.app.databinding.Fragment123Viewpage2Binding
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils

class ViewPage2Fragment2 : VMFragment() {

    private val mBind by lazy { Fragment123Viewpage2Binding.inflate(layoutInflater) }

    override fun initArgs(bundle: Bundle?) = null

    override fun initView() = mBind.root

    override fun initData(bundle: Bundle?) {
        mBind.itemTv.text = "Fragment2"
        mBind.itemTv.setOnClickListener {
            val triple = Triple<String, Boolean, Any>("Fragment2", true, "Fragment2")
            BusUtils.post(MyConstant.TAG_NO_PARAM, triple)
        }
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