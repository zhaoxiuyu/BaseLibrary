package com.base.library.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * 保持一定宽高比的图片
 */
class DynamicHeightImageView : AppCompatImageView {

    private var mHeightRatio: Double = 1.0

    constructor(context: Context) : super(context) {

    }

    constructor(
        context: Context, attrs: AttributeSet?
    ) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
    }

    fun setHeightRatio(ratio: Double) {
        if (mHeightRatio != ratio) {
            this.mHeightRatio = ratio
            requestLayout()
        }
    }

    fun getHeightRatio() = mHeightRatio

    // 这两个参数就是父控件告诉子控件可获得的空间以及关于这个空间的约束条件
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mHeightRatio > 0.0) {
            // 子控件拿着这些条件就能正确的提取自身大小
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = width * mHeightRatio.toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}