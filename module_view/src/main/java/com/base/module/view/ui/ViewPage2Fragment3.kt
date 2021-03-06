package com.base.module.view.ui

import android.os.Bundle
import com.base.library.base.BFragment
import com.base.module.view.databinding.Fragment123Viewpage2Binding
import com.blankj.utilcode.util.LogUtils

class ViewPage2Fragment3 : BFragment() {

    private val mBind by lazy { Fragment123Viewpage2Binding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.itemTv.text = "Fragment3"
        mBind.itemTv.setOnClickListener {
        }
    }

    override fun initObserve(): Nothing? = null

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment3 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment3 + onResume")
    }

}