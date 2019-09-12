package com.base.library.view

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.base.library.interfaces.OnSurplusListener
import kotlinx.android.synthetic.main.base_titlebar.view.*

/**
 * 通用的顶部导航栏
 */
class BTitlebar(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        ivLeft.setOnClickListener { (context as Activity).finish() }
    }

    //左侧 返回事件
    fun setIvLeftClick(listener: OnClickListener) {
        ivLeft.visibility = View.VISIBLE
        ivLeft.setOnClickListener(listener)
    }

    //左侧 返回按钮图片
    fun setIvLeftSrc(resId: Int) {
        ivLeft.visibility = View.VISIBLE
        ivLeft.setImageResource(resId)
    }

    //左侧 文字内容 和 点击事件
    fun setLeftCenter(string: String, listener: OnClickListener) {
        tvLeftCenter.visibility = View.VISIBLE
        tvLeftCenter.text = string
        tvLeftCenter.setOnClickListener(listener)
    }

    //中间  标题中间的图片
    fun setIvCenter(resId: Int, listener: OnClickListener) {
        ivCenter.visibility = View.VISIBLE
        ivCenter.setImageResource(resId)
        ivCenter.setOnClickListener(listener)
    }

    //右边 文字内容 和 点击事件
    fun setTvRightCenter(text: String, clickListener: OnClickListener) {
        tvRightCenter.visibility = View.VISIBLE
        tvRightCenter.text = text
        tvRightCenter.setOnClickListener(clickListener)
    }

    //右边 文字内容 和 点击事件
    fun setTvRight(text: String, clickListener: OnClickListener) {
        tvRight.visibility = View.VISIBLE
        tvRight.text = text
        tvRight.setOnClickListener(clickListener)
    }

    /**
     * 倒计时
     */
    private var cdTimer: CountDownTimer? = null
    private var surplusListener: OnSurplusListener? = null

    /**
     * countTime 总倒计时时间,单位:毫秒
     * surplusListener 倒计时结束回调接口
     * finish 倒计时结束是否自动销毁页面
     */
    fun startSurplus(countTime: Int = 30000, surplusListener: OnSurplusListener? = null, finish: Boolean = true) {
        this.surplusListener = surplusListener
        tvRightCenter.visibility = View.VISIBLE
        tvRightCenter.text = "倒计时 ${(countTime / 1000)}"

        cdTimer?.cancel()
        cdTimer = object : CountDownTimer(countTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvRightCenter.text = "倒计时 ${(millisUntilFinished / 1000)} s"
            }

            override fun onFinish() {
                tvRightCenter.text = "倒计时 0 s"
                if (finish) (context as Activity).finish()
                surplusListener?.surplus()
            }
        }
    }

    fun stopSurplus() {
        cdTimer?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cdTimer?.cancel()
        surplusListener = null
    }

}