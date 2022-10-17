package com.base.views.ui.fragment

import android.os.Bundle
import com.base.common.constant.CommonConstant
import com.base.library.mvvm.VMFragment
import com.base.views.R
import com.base.views.databinding.ViewsFragmentVp2TabBinding
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils

class ViewsVP2TabFragment : VMFragment<ViewsFragmentVp2TabBinding>() {

    private var param1: String? = null

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(CommonConstant.ARG_PARAM1)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.tv.text = "$param1"
        when (param1) {
            "概况" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_FFC301))
            "日租房" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_21B4A7))
            "钟点房" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_018B9E))
            "出租率" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_2771E1))
            "渠道1" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_F0720C))
            "渠道2" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_F34A00))
            "渠道3" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.common_color_CCCCCC))
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
            ViewsVP2TabFragment().apply {
                arguments = Bundle().apply {
                    putString(CommonConstant.ARG_PARAM1, param1)
                }
            }
    }

}