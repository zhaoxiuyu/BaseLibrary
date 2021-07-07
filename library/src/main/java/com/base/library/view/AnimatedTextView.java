package com.base.library.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.base.library.R;

/**
 * 带动画的TextView
 * Created by santong on 2017/2/21.
 */

public class AnimatedTextView extends AppCompatTextView {
    Context context;
    private int mLineWidth = 5; //线条的宽度
    private int mColor = Color.parseColor("#fe4658"); //线条的颜色
    private float mTopGap = 0f; // 取值 0 ~ 1（0表示没有缺口）
    private float mButtomGap = 0.25f; // 取值 0 ~ 1（0表示没有缺口）
    private int mTime = 1000; //默认为一秒
    private Paint mLinePaint; //线条画笔
    private Path mPath; //路径
    private PathMeasure mPathMeasure; //路径测量
    private boolean isInit;
    private int mWidth;
    private int mHeight;

    private ValueAnimator valueAnimator;

    public AnimatedTextView(Context context) {
        this(context, null);
    }

    public AnimatedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint(attrs);
    }

    //初始化画笔和路径
    private void initPaint(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Animated, 0, 0);
        mColor = a.getInteger(R.styleable.Animated_lineColor, mColor);
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLineWidth);
        mPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    //初始化路径
    public void initPath() {
        mPath.reset();
        mPath.moveTo(mWidth * mTopGap, 0);
        mPath.lineTo(mWidth, 0);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(mWidth * (1 - mButtomGap), mHeight);
        mPathMeasure.setPath(mPath, false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    //设置动画的路径
    private void setAnimationPath(float value) {
        if (value <= (mWidth * (1 - mTopGap))) {
            mPath.reset();
            mPath.moveTo(mWidth / 4 + value, 0);
            mPath.lineTo(mWidth, 0);
            mPath.lineTo(mWidth, mHeight);
            mPath.lineTo(3 * mWidth / 4 - value, mHeight);
        } else if (value > (mWidth * (1 - mTopGap)) &&
                value <= (mWidth * (1 - mTopGap) + mHeight)) {
            mPath.reset();
            mPath.moveTo(mWidth, value - (mWidth * (1 - mTopGap)));
            mPath.lineTo(mWidth, mHeight);
            mPath.lineTo(0, mHeight);
            mPath.lineTo(0, (mWidth * (1 - mButtomGap) + mHeight) - value);
        } else if (value > (mWidth + mHeight) &&
                value <= (mHeight + (2 - mTopGap) * mWidth)) {
            mPath.reset();
            mPath.moveTo(mWidth - (value - (mWidth *
                    (1 - mTopGap) + mHeight)), mHeight);
            mPath.lineTo(0, mHeight);
            mPath.lineTo(0, 0);
            mPath.lineTo(value - (mWidth * (1 - mButtomGap) + mHeight), 0);
        } else if (value > (mHeight + (2 - mTopGap) * mWidth) &&
                value <= (mHeight * 2) + (2 - mTopGap) * mWidth) {
            mPath.reset();
            mPath.moveTo(0, mHeight - (value - (mHeight + (2 - mTopGap) * mWidth)));
            mPath.lineTo(0, 0);
            mPath.lineTo(mWidth, 0);
            mPath.lineTo(mWidth, value - (mHeight + (2 - mButtomGap) * mWidth));
        } else if (value > (mHeight * 2) + (2 - mTopGap) * mWidth &&
                value <= (2 * (mHeight + mWidth))) {
            mPath.reset();
            mPath.moveTo(value - ((mHeight * 2) + (2 - mTopGap) * mWidth), 0);
            mPath.lineTo(mWidth, 0);
            mPath.lineTo(mWidth, mHeight);
            mPath.lineTo(mWidth - (value - ((mHeight * 2) +
                    (2 - mButtomGap) * mWidth)), mHeight);
        }
    }

    //开始化动画
    public void startAnimation() {
        isInit = true;
        valueAnimator = ValueAnimator.ofFloat(0, 2 * mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setAnimationPath((float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    public void stopAnimation() {
        if (valueAnimator != null) {
            valueAnimator.end();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initPath();
        }
        canvas.drawPath(mPath, mLinePaint);
        canvas.save();
        canvas.translate(5, 0);
        super.onDraw(canvas);
    }

    //设置线条的颜色
    public void setLineColor(int color) {
        mColor = color;
    }

    //设置线条宽度
    public void setLineWidth(int width) {
        mLineWidth = width;
    }

    //设置初始化间距
    public void setLineColor(float gap) {
        mTopGap = gap;
    }

    //设置动画播放的时长
    public void setDuration(int time) {
        mTime = time;
    }

}
