package com.base.app.sample.views.fragment

import android.os.Bundle
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentViewpage2Binding
import com.base.library.mvvm.VMFragment
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils

class ViewPage2Fragment : VMFragment<FragmentViewpage2Binding>() {

    private var param1: Int = 0

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getInt(MyConstant.ARG_PARAM1)
        }
    }

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.itemTv.text = "Fragment $param1"
        viewBinding.itemTv.setOnClickListener {
        }
        when (param1) {
            0 -> {
                viewBinding.itemTv.setBackgroundColor(ColorUtils.getColor(R.color.colorAccent))
            }
            1 -> {
                viewBinding.itemTv.setBackgroundColor(ColorUtils.getColor(R.color.color_FFC301))
            }
            2 -> {
                viewBinding.itemTv.setBackgroundColor(ColorUtils.getColor(R.color.purple_500))
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
            ViewPage2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(MyConstant.ARG_PARAM1, param1)
                }
            }
    }

}