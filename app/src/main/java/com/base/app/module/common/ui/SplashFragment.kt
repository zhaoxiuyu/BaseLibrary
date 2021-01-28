package com.base.app.module.common.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.base.app.R
import com.base.app.databinding.FragmentSplashBinding
import com.base.library.base.BFragment
import com.base.library.mvvm.core.BViewModel

/**
 * 启动页
 */
class SplashFragment : BFragment() {

    private val mBind by lazy { FragmentSplashBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainBnv(false)
        mCountDownTimer.start()
    }

    override fun initObserve(): MutableList<BViewModel>? = null

    /**
     * 倒计时
     */
    private val mCountDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onFinish() {
//            findNavController().navigate(R.id.action_splash_to_utils)
        }

        override fun onTick(millisUntilFinished: Long) {
        }
    }

    override fun onDestroyView() {
        mBind.lottie.cancelAnimation()
        mCountDownTimer.cancel()
        super.onDestroyView()
    }

}