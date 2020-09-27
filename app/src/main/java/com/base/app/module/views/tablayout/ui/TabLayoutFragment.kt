package com.base.app.module.views.tablayout.ui

import android.os.Bundle
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.library.mvvm.core.VMFragment
import com.base.library.mvvm.core.VMViewModel
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_tab_layout.*

class TabLayoutFragment : VMFragment() {

    private var param1: String? = null

    override fun initArgs(bundle: Bundle?): VMViewModel? {
        arguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
        }
        return null
    }

    override fun getContentView() = R.layout.fragment_tab_layout

    override fun initData(bundle: Bundle?) {
        tv.text = "$param1"
        tv.setOnClickListener {
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

    companion object {
        fun newInstance(param1: String) =
            TabLayoutFragment().apply {
                arguments = Bundle().apply {
                    putString(MyConstant.ARG_PARAM1, param1)
                }
            }
    }

}