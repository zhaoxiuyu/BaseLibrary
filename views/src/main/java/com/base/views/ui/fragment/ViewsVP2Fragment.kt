package com.base.views.ui.fragment

import android.os.Bundle
import android.view.View
import com.base.common.constant.CommonConstant
import com.base.library.mvvm.VMFragment
import com.base.views.R
import com.base.views.databinding.ViewsFragmentVp2Binding
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils

class ViewsVP2Fragment : VMFragment<ViewsFragmentVp2Binding>() {

    private var param1: Int = 0

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getInt(CommonConstant.ARG_PARAM1)
        }
    }

    override fun getRootView(): View = viewBinding.root

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.itemTv.text = "Fragment $param1"
        viewBinding.itemTv.setOnClickListener {
        }
        when (param1) {
            0 -> {
                viewBinding.itemTv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_FFC301))
            }
            1 -> {
                viewBinding.itemTv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_21B4A7))
            }
            2 -> {
                viewBinding.itemTv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_2771E1))
            }
        }
    }

    override fun registerObserve() {
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("Fragment $param1 + onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("Fragment $param1 + onResume")
    }

    companion object {
        fun newInstance(param1: Int) =
            ViewsVP2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(CommonConstant.ARG_PARAM1, param1)
                }
            }
    }

}