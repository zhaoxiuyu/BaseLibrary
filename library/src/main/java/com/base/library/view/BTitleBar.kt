package com.base.library.view

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.base.library.interfaces.OnSurplusListener
import kotlinx.android.synthetic.main.base_activity_layout.view.*

/**
 * 简单顶部导航栏
 */
class BTitleBar : FrameLayout {

    constructor(context: Context) : super(context) {}

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        iv_left.setOnClickListener { (context as Activity).finish() }
    }

    /**
     * -------------------------------- 左侧 ImageView --------------------------------
     */
    fun getIvLeft(): ImageView = iv_left

    fun setIvLeftResource(resource: Int? = null, listener: OnClickListener? = null) {
        iv_left.visibility = View.VISIBLE
        resource?.let { iv_left.setImageResource(resource) }
        listener?.let { iv_left.setOnClickListener(listener) }
    }

    /**
     * -------------------------------- 左侧 TextView --------------------------------
     */

    fun getTvLeft(): TextView = tv_left

    fun setTvLeftText(text: String? = null, listener: OnClickListener? = null) {
        tv_left.visibility = View.VISIBLE
        text?.let { tv_left.text = text }
        listener?.let { tv_left.setOnClickListener(listener) }
    }

    /**
     * -------------------------------- 中间 ImageView --------------------------------
     */

    fun getIvCenter(): ImageView = iv_center

    fun setIvCenterResource(resource: Int? = null, listener: OnClickListener? = null) {
        iv_center.visibility = View.VISIBLE
        resource?.let { iv_center.setImageResource(resource) }
        listener?.let { iv_center.setOnClickListener(listener) }
    }

    /**
     * -------------------------------- 中间 TextView --------------------------------
     */

    fun getTvCenter(): TextView = tv_center

    fun setTvCenterText(text: String? = null, listener: OnClickListener? = null) {
        tv_center.visibility = View.VISIBLE
        text?.let { tv_center.text = text }
        listener?.let { tv_center.setOnClickListener(listener) }
    }

    /**
     * -------------------------------- 右侧 ImageView --------------------------------
     */

    fun getIvEnd(): ImageView = iv_end

    fun setIvEndResource(resource: Int? = null, listener: OnClickListener? = null) {
        iv_end.visibility = View.VISIBLE
        resource?.let { iv_end.setImageResource(resource) }
        listener?.let { iv_end.setOnClickListener(listener) }
    }

    /**
     * -------------------------------- 右侧 TextView --------------------------------
     */

    fun getTvEnd(): TextView = tv_end

    fun setTvEndText(text: String? = null, listener: OnClickListener? = null) {
        tv_end.visibility = View.VISIBLE
        text?.let { tv_end.text = text }
        listener?.let { tv_end.setOnClickListener(listener) }
    }

    /**
     * -------------------------------- 倒计时 --------------------------------
     */
    private var cdTimer: CountDownTimer? = null
    private var surplusListener: OnSurplusListener? = null

    fun setOnSurplusListener(surplus: OnSurplusListener? = null) {
        this.surplusListener = surplus
    }

    /**
     * countTime 总倒计时时间,单位:毫秒
     * surplusListener 倒计时结束回调接口
     * finish 倒计时结束是否自动销毁页面
     */
    fun startSurplus(countTime: Int = 30000, finish: Boolean = true) {
        tv_end.visibility = View.VISIBLE
        tv_end.text = "倒计时 ${(countTime / 1000)}"

        cdTimer?.cancel()
        cdTimer = object : CountDownTimer(countTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tv_end.text = "倒计时 ${(millisUntilFinished / 1000)} s"
            }

            override fun onFinish() {
                tv_end.text = "倒计时 0 s"
                if (finish) (context as Activity).finish()
                surplusListener?.surplus()
            }
        }
        cdTimer?.start()
    }

    fun stopSurplus() {
        cdTimer?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopSurplus()
        surplusListener = null
    }

}