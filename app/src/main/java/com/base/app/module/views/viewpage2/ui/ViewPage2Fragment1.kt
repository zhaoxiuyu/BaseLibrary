package com.base.app.module.views.viewpage2.ui

import android.os.Bundle
import com.base.app.databinding.Fragment123Viewpage2Binding
import com.base.library.base.BFragment
import com.blankj.utilcode.util.LogUtils

class ViewPage2Fragment1 : BFragment() {

    private val mBind by lazy { Fragment123Viewpage2Binding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.itemTv.text = "Fragment1"
        mBind.itemTv.setOnClickListener {
        }
    }

    override fun initObserve(): Nothing? = null

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment1 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment1 + onResume")
    }

}