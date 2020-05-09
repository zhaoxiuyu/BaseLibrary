package com.base.library.util

import android.os.CountDownTimer

/**
 * 倒计时
 */
class MyCountDownTimer(
    millisInFuture: Long, countDownInterval: Long,
    private val finishTick: MyFinishTick
) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onFinish() {
        finishTick.onFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        finishTick.onTick(millisUntilFinished)
    }

    interface MyFinishTick {
        fun onFinish()

        fun onTick(millisUntilFinished: Long)
    }

}

