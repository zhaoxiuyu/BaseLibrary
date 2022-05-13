package com.base.app.sample.views.fragment

import android.os.Bundle
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentViewpage2TabBinding
import com.base.library.mvvm.VMFragment
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils

class ViewPage2TabFragment : VMFragment<FragmentViewpage2TabBinding>() {

    private var param1: String? = null

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
        }
    }

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.tv.text = "$param1"
        when (param1) {
            "概况" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_21B4A7))
            "日租房" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_018B9E))
            "钟点房" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.colorAccent))
            "出租率" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_FFC301))
            "渠道1" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_2771E1))
            "渠道2" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_F0720C))
            "渠道3" -> viewBinding.tv.setBackgroundColor(ColorUtils.getColor(R.color.color_F34A00))
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
            ViewPage2TabFragment().apply {
                arguments = Bundle().apply {
                    putString(MyConstant.ARG_PARAM1, param1)
                }
            }
    }

}