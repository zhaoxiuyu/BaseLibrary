package com.base.module.view.ui

import android.os.Bundle
import com.base.library.base.BFragment
import com.base.module.view.ViewsConstant
import com.base.module.view.databinding.Fragment123Viewpage2Binding
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils

class ViewPage2Fragment2 : BFragment() {

    private val mBind by lazy { Fragment123Viewpage2Binding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.itemTv.text = "Fragment2"
        mBind.itemTv.setOnClickListener {
            val triple = Triple<String, Boolean, Any>("Fragment2", true, "Fragment2")
            BusUtils.post(ViewsConstant.TAG_NO_PARAM, triple)
        }
    }

    override fun initObserve(): Nothing? = null

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment2 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment2 + onResume")
    }

}