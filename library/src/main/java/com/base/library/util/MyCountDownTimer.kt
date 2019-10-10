package com.base.library.util

import android.os.CountDownTimer

/**
 * 倒计时
 */
class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long, finishTick: MyFinishTick) :
    CountDownTimer(millisInFuture, countDownInterval) {

    private var finishTick: MyFinishTick = finishTick

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

