package com.base.app.module.views.viewpage2.ui

import android.os.Bundle
import com.base.app.R
import com.base.library.mvvm.core.VMFragment
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment123_viewpage2.*

class ViewPage2Fragment1 : VMFragment() {

    override fun initArgs(bundle: Bundle?) = null

    override fun getContentView() = R.layout.fragment123_viewpage2

    override fun initData(bundle: Bundle?) {
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