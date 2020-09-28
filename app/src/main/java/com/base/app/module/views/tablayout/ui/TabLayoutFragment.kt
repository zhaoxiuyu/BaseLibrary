package com.base.app.module.views.tablayout.ui

import android.os.Bundle
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.library.mvvm.core.VMFragment
import com.base.library.mvvm.core.VMViewModel
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
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
        when (param1) {
            "概况" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.color_21B4A7))
            "日租房" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.color_018B9E))
            "钟点房" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.colorAccent))
            "出租率" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.color_FFC301))
            "渠道1" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.color_2771E1))
            "渠道2" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.color_F0720C))
            "渠道3" -> tv.setBackgroundColor(ColorUtils.getColor(R.color.color_F34A00))
        }
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).titleBar(tv).init()
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