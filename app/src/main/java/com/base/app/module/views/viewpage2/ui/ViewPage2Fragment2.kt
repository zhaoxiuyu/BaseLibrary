package com.base.app.module.views.viewpage2.ui

import android.os.Bundle
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment123_viewpage2.*

class ViewPage2Fragment2 : VMFragment() {

    override fun initArgs(bundle: Bundle?) = null

    override fun getContentView() = R.layout.fragment123_viewpage2

    override fun initData(bundle: Bundle?) {
        itemTv.text = "Fragment2"
        itemTv.setOnClickListener {
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