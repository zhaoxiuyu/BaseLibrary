package com.base.library.custom

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.base.library.R
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.ColorUtils

class GlobalStatusView(mContext: Context, retryTask: Runnable?) : LinearLayout(mContext),
    View.OnClickListener {

    var mImage: ImageView? = null
    var mText: TextView? = null
    var mRetryTask: Runnable? = retryTask

    init {
        LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true)

        orientation = VERTICAL
        gravity = Gravity.CENTER
        setBackgroundColor(ColorUtils.getColor(R.color._xpopup_light_color))
    }

    /**
     * 初始化LoadingView
     * 如果需要支持点击重试，在适当的时机给对应的控件添加点击事件
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        mImage = findViewById(R.id.image)
        mText = findViewById(R.id.text)
    }

    /**
     * 设置当前的加载状态：加载中、加载失败、空数据等
     * 其中，加载失败可判断当前是否联网，可现实无网络的状态
     * 属于加载失败状态下的一个分支,可自行决定是否实现
     */
    fun setStatus(status: Int) {
        var show = true
        var click: OnClickListener? = null
        when (status) {
            Gloading.STATUS_LOAD_SUCCESS -> {
                show = false
            }
            Gloading.STATUS_LOADING -> {
                mText?.text = "加载中..."
                show = true
            }
            Gloading.STATUS_LOAD_FAILED -> {
                mText?.text = "加载失败"
                show = true
                click = this
            }
            Gloading.STATUS_EMPTY_DATA -> {
                mText?.text = "加载为空"
                show = true
            }
        }
        visibility = if (show) View.VISIBLE else View.GONE
        setOnClickListener(click)
    }

    override fun onClick(v: View?) {
        mRetryTask?.run()
    }

}