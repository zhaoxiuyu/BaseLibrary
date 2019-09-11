package com.base.library.util

private var mLastClickTime: Long = 0
private const val MIN_CLICK_DELAY_TIME = 800

/**
 * 过滤快速点击
 * true 重复点击
 * false 没有重复点击
 */
fun isFastClick(): Boolean {
    val currentTime = System.currentTimeMillis() // 当前时间
    val time = currentTime - mLastClickTime // 两次点击的时间差
    if (time in 1 until MIN_CLICK_DELAY_TIME) return true
    mLastClickTime = currentTime
    return false
}


