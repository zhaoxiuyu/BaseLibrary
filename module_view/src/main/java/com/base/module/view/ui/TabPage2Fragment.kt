package com.base.module.view.ui

import android.os.Bundle
import com.base.library.base.BFragment
import com.base.module.view.R
import com.base.module.view.ViewsConstant
import com.base.module.view.databinding.FragmentTabPage2Binding
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils

class TabPage2Fragment : BFragment() {

    private val mBind by lazy { FragmentTabPage2Binding.inflate(layoutInflater) }

    private var param1: String? = null

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(ViewsConstant.ARG_PARAM1)
        }
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.tv.text = "$param1"
        when (param1) {
            "概况" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_21B4A7))
            "日租房" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_018B9E))
            "钟点房" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.colorAccent))
            "出租率" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_FFC301))
            "渠道1" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_2771E1))
            "渠道2" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_F0720C))
            "渠道3" -> mBind.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_F34A00))
        }
    }

    override fun registerObserve() {
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment1 + onResume = $param1")
    }

    companion object {
        fun newInstance(param1: String) =
            TabPage2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ViewsConstant.ARG_PARAM1, param1)
                }
            }
    }

}