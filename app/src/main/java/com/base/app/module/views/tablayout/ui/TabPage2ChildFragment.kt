package com.base.app.module.views.tablayout.ui

import android.os.Bundle
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentTabPage2ChildBinding
import com.base.library.base.BFragment
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils

class TabPage2ChildFragment : BFragment() {

    private val mBind by lazy { FragmentTabPage2ChildBinding.inflate(layoutInflater) }

    private var param1: String? = null

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
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

    override fun initObserve(): Nothing? = null

    override fun onResume() {
        super.onResume()

        LogUtils.d("Fragment1 + onResume = $param1")
    }

    companion object {
        fun newInstance(param1: String) =
            TabPage2ChildFragment().apply {
                arguments = Bundle().apply {
                    putString(MyConstant.ARG_PARAM1, param1)
                }
            }
    }

}